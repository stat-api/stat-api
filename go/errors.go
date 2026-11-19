package statapi

import (
	"encoding/json"
	"fmt"
	"net/http"
	"strings"
)

// APIError is the base error for every non-2xx response, for a 202 Accepted
// (*NotReadyError), and for transport-level failures (reported with Status 0). Body is the raw server envelope; Path is
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

// defaultNotReadyMessage is NotReadyError.Message when the 202 body carries none.
const defaultNotReadyMessage = "the data is not ready yet"

// NotReadyError is a 202 Accepted — the request was accepted but the data is not
// ready yet (for example a contest whose lineup file is still queued to load). A
// 202 holds NO data, so the SDK returns this error instead of handing the body
// back as the route's data. It is never retried inside the SDK: a 202 is not a
// transient failure, the caller decides when to ask again. The embedded
// APIError.Body is the raw 202 body (JSON text).
//
// RetryAfterSeconds is the body's retry_after_seconds, else the Retry-After
// header (seconds form), else nil. EstimatedReadyAt is an RFC 3339 instant, ""
// when the server gives none (State "waiting_for_contest"). State is the body's
// status ("queued" or "waiting_for_contest"). Message is the server's plain
// sentence.
type NotReadyError struct {
	*APIError
	RetryAfterSeconds *int64
	EstimatedReadyAt  string
	State             string
	Message           string
}

func (e *NotReadyError) Unwrap() error { return e.APIError }

func (e *NotReadyError) Error() string {
	return fmt.Sprintf("statapi: %d on %s: %s", e.Status, e.Path, e.Message)
}

// errorForStatus maps an HTTP status onto the matching typed error, all wrapping
// a shared APIError. header is the response headers; only the 202 mapping reads
// it (Retry-After).
func errorForStatus(status int, body, path string, quota Quota, header http.Header) error {
	base := &APIError{Status: status, Body: body, Path: path}
	switch status {
	case 202:
		return newNotReadyError(base, header)
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

func newNotReadyError(base *APIError, header http.Header) *NotReadyError {
	e := &NotReadyError{APIError: base, Message: defaultNotReadyMessage}

	var payload struct {
		Status            string `json:"status"`
		Message           string `json:"message"`
		EstimatedReadyAt  string `json:"estimated_ready_at"`
		RetryAfterSeconds *int64 `json:"retry_after_seconds"`
	}
	// A body that is not the documented JSON object leaves every field empty:
	// the error still reports a 202 with the default message, and Body keeps the
	// raw text for the caller.
	_ = json.Unmarshal([]byte(base.Body), &payload)
	e.State = payload.Status
	e.EstimatedReadyAt = payload.EstimatedReadyAt
	if strings.TrimSpace(payload.Message) != "" {
		e.Message = payload.Message
	}

	switch {
	case payload.RetryAfterSeconds != nil && *payload.RetryAfterSeconds >= 0:
		e.RetryAfterSeconds = payload.RetryAfterSeconds
	default:
		if secs, ok := int64Header(header, "Retry-After"); ok && secs >= 0 {
			e.RetryAfterSeconds = &secs
		}
	}
	return e
}
