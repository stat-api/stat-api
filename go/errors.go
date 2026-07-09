package statapi

import (
	"encoding/json"
	"fmt"
)

// APIError is the base error for every non-2xx response (and transport-level
// failures, reported with Status 0). Body is the raw server envelope; Path is
// the request path that produced it. Every typed error below wraps an APIError,
// so errors.As(err, new(*APIError)) always recovers it:
//
//	var apiErr *statapi.APIError
//	if errors.As(err, &apiErr) {
//		log.Printf("status=%d body=%s", apiErr.Status, apiErr.Body)
//	}
type APIError struct {
	Status int
	Body   string
	Path   string
}

func (e *APIError) Error() string {
	detail := e.Body
	if detail == "" {
		detail = fmt.Sprintf("HTTP %d", e.Status)
	}
	return fmt.Sprintf("statapi: %d on %s: %s", e.Status, e.Path, detail)
}

// AuthenticationError is a 401 — the API key is missing, malformed, or rejected.
type AuthenticationError struct{ *APIError }

func (e *AuthenticationError) Unwrap() error { return e.APIError }

// PlanRequiredError is a 402 — the endpoint requires a paid plan the caller
// does not hold.
type PlanRequiredError struct{ *APIError }

func (e *PlanRequiredError) Unwrap() error { return e.APIError }

// ValidationError is a 400 — a malformed request or an unsatisfied
// required-filter set.
type ValidationError struct{ *APIError }

func (e *ValidationError) Unwrap() error { return e.APIError }

// NotFoundError is a 404 — no resource exists at the requested path or id.
type NotFoundError struct{ *APIError }

func (e *NotFoundError) Unwrap() error { return e.APIError }

// QuotaExceededError is a 429 — the caller's monthly record quota is exhausted.
// It is never retried (the quota is a monthly budget). Limit and Used are parsed
// from the response body, falling back to the X-Quota-* headers; ResetsAt and
// UpgradeURL come from the body.
type QuotaExceededError struct {
	*APIError
	Limit      *int64
	Used       *int64
	ResetsAt   string
	UpgradeURL string
}

func (e *QuotaExceededError) Unwrap() error { return e.APIError }

// errorForStatus maps an HTTP status onto the matching typed error, all wrapping
// a shared APIError.
func errorForStatus(status int, body, path string, quota Quota) error {
	base := &APIError{Status: status, Body: body, Path: path}
	switch status {
	case 400:
		return &ValidationError{APIError: base}
	case 401:
		return &AuthenticationError{APIError: base}
	case 402:
		return &PlanRequiredError{APIError: base}
	case 404:
		return &NotFoundError{APIError: base}
	case 429:
		return newQuotaExceededError(base, quota)
	default:
		return base
	}
}

func newQuotaExceededError(base *APIError, quota Quota) *QuotaExceededError {
	e := &QuotaExceededError{APIError: base}

	var payload struct {
		Limit      *int64 `json:"limit"`
		Used       *int64 `json:"used"`
		ResetsAt   string `json:"resets_at"`
		UpgradeURL string `json:"upgrade_url"`
	}
	_ = json.Unmarshal([]byte(base.Body), &payload)
	e.Limit = payload.Limit
	e.Used = payload.Used
	e.ResetsAt = payload.ResetsAt
	e.UpgradeURL = payload.UpgradeURL

	// The X-Quota-* headers are the authoritative counts; use them when the body
	// omitted a value.
	if e.Limit == nil && quota.Present {
		limit := quota.Limit
		e.Limit = &limit
	}
	if e.Used == nil && quota.Present {
		used := quota.Used
		e.Used = &used
	}
	return e
}
