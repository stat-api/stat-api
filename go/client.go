// Package statapi is the official Go client for the stat-api sports data API —
// a single, typed surface over NBA, NFL, MLB, NHL, and PGA data.
//
// Construct a client with New (which reads STAT_API_KEY from the environment)
// or NewWithOptions, then reach a table through its league accessor:
//
//	client, err := statapi.New()
//	if err != nil {
//		log.Fatal(err)
//	}
//	page, err := client.NBA.Players.List(ctx, &statapi.NBAPlayersListParams{
//		Limit: statapi.Int(25),
//	})
//
// The client has zero dependencies beyond the standard library. The generated
// per-table surface (row structs, list params, services) lives in the *.gen.go
// files; this file plus errors.go and quota.go are the hand-written core.
package statapi

import (
	"bytes"
	"context"
	"encoding/json"
	"fmt"
	"io"
	"math/rand"
	"net/http"
	"net/url"
	"os"
	"strings"
	"time"
)

const (
	envAPIKey       = "STAT_API_KEY"
	envBaseURL      = "STAT_API_BASE_URL"
	defaultBaseURL  = "https://api.stat-api.com"
	defaultTimeout  = 30 * time.Second
	userAgentPrefix = "statapi-go"
	// One initial attempt plus two retries on connection errors / 5xx.
	maxAttempts = 3
)

// Base backoff per retry, jittered at call time (matches the SDK family: 250ms
// then 1s).
var backoffs = []time.Duration{250 * time.Millisecond, 1 * time.Second}

// Options configures a Client. Every field is optional; the zero value falls
// back to the environment and the documented defaults.
type Options struct {
	// APIKey authenticates every request. Falls back to STAT_API_KEY.
	APIKey string
	// BaseURL overrides the API host. Falls back to STAT_API_BASE_URL, then to
	// https://api.stat-api.com.
	BaseURL string
	// Timeout bounds each HTTP request (default 30s). Ignored when HTTPClient
	// is supplied.
	Timeout time.Duration
	// HTTPClient, when set, is used verbatim (its own Timeout wins).
	HTTPClient *http.Client
}

// Client is the entry point to the stat-api surface. Its exported league fields
// (Client.NBA, Client.NFL, …) are promoted from a generated struct and expose
// the per-table services (Client.NBA.Players.List(…)).
type Client struct {
	services // generated: league accessor fields wired by wireServices

	apiKey     string
	baseURL    string
	userAgent  string
	httpClient *http.Client
}

// New builds a Client from the environment, reading STAT_API_KEY and (optional)
// STAT_API_BASE_URL. It returns an error when no API key is available — the
// client never falls back to unauthenticated requests.
func New() (*Client, error) {
	return NewWithOptions(Options{})
}

// NewWithOptions builds a Client from explicit options, falling back to the
// environment and defaults for anything left unset. It returns an error when no
// API key is available from either the options or STAT_API_KEY.
func NewWithOptions(opts Options) (*Client, error) {
	apiKey := opts.APIKey
	if apiKey == "" {
		apiKey = os.Getenv(envAPIKey)
	}
	if apiKey == "" {
		return nil, fmt.Errorf(
			"statapi: no API key — set Options.APIKey or the %s environment variable",
			envAPIKey,
		)
	}

	baseURL := opts.BaseURL
	if baseURL == "" {
		baseURL = os.Getenv(envBaseURL)
	}
	if baseURL == "" {
		baseURL = defaultBaseURL
	}

	httpClient := opts.HTTPClient
	if httpClient == nil {
		timeout := opts.Timeout
		if timeout == 0 {
			timeout = defaultTimeout
		}
		httpClient = &http.Client{Timeout: timeout}
	}

	c := &Client{
		apiKey:     apiKey,
		baseURL:    strings.TrimRight(baseURL, "/"),
		userAgent:  userAgentPrefix + "/" + Version,
		httpClient: httpClient,
	}
	c.wireServices()
	return c, nil
}

// get issues an authenticated GET and returns the raw body plus the parsed
// quota snapshot. Connection errors and 5xx responses are retried up to
// maxAttempts with jittered backoff; 4xx (including 429) are never retried and
// surface as the mapped typed error. A 202 Accepted holds no data: it surfaces
// as *NotReadyError (never as a body) and is never retried.
func (c *Client) get(ctx context.Context, path string, q url.Values) ([]byte, Quota, error) {
	target := c.baseURL + path
	if enc := q.Encode(); enc != "" {
		target += "?" + enc
	}

	for attempt := 0; ; attempt++ {
		last := attempt == maxAttempts-1
		res, err := c.do(ctx, http.MethodGet, target, nil)
		if err != nil {
			if last {
				return nil, Quota{}, &APIError{Status: 0, Body: err.Error(), Path: path}
			}
			if werr := sleepBackoff(ctx, attempt); werr != nil {
				return nil, Quota{}, &APIError{Status: 0, Body: werr.Error(), Path: path}
			}
			continue
		}
		// A 202 holds no data: it falls through to errorForStatus (NotReadyError)
		// and, being below 500, is never retried.
		if res.status >= 200 && res.status < 300 && res.status != http.StatusAccepted {
			return res.body, res.quota, nil
		}
		if res.status >= 500 && !last {
			if werr := sleepBackoff(ctx, attempt); werr != nil {
				return nil, res.quota, &APIError{Status: 0, Body: werr.Error(), Path: path}
			}
			continue
		}
		return nil, res.quota, errorForStatus(res.status, string(res.body), path, res.quota, res.header)
	}
}

// post issues an authenticated JSON POST request and returns the raw body plus
// the parsed quota snapshot.
func (c *Client) post(ctx context.Context, path string, payload any) ([]byte, Quota, error) {
	raw, err := json.Marshal(payload)
	if err != nil {
		return nil, Quota{}, err
	}
	target := c.baseURL + path
	res, err := c.do(ctx, http.MethodPost, target, raw)
	if err != nil {
		return nil, Quota{}, &APIError{Status: 0, Body: err.Error(), Path: path}
	}
	if res.status >= 200 && res.status < 300 && res.status != http.StatusAccepted {
		return res.body, res.quota, nil
	}
	return nil, res.quota, errorForStatus(res.status, string(res.body), path, res.quota, res.header)
}

// response is one completed request attempt: the full body, the parsed quota
// snapshot, the HTTP status and the response headers.
type response struct {
	body   []byte
	quota  Quota
	status int
	header http.Header
}

// doRaw issues an authenticated request returning the raw response (including 202 Accepted)
// with optional extra headers and 5xx/network retries for idempotent calls.
func (c *Client) doRaw(
	ctx context.Context,
	method string,
	path string,
	payload any,
	extraHeaders map[string]string,
	retry5xx bool,
) (response, error) {
	var bodyBytes []byte
	if payload != nil {
		raw, err := json.Marshal(payload)
		if err != nil {
			return response{}, err
		}
		bodyBytes = raw
	}
	target := c.baseURL + path
	for attempt := 0; ; attempt++ {
		last := !retry5xx || attempt == maxAttempts-1
		res, err := c.doWithHeaders(ctx, method, target, bodyBytes, extraHeaders)
		if err != nil {
			if last {
				return response{}, &APIError{Status: 0, Body: err.Error(), Path: path}
			}
			if werr := sleepBackoff(ctx, attempt); werr != nil {
				return response{}, &APIError{Status: 0, Body: werr.Error(), Path: path}
			}
			continue
		}
		if res.status >= 200 && res.status < 300 {
			return res, nil
		}
		if retry5xx && res.status >= 500 && !last {
			if werr := sleepBackoff(ctx, attempt); werr != nil {
				return response{}, &APIError{Status: 0, Body: werr.Error(), Path: path}
			}
			continue
		}
		return response{}, errorForStatus(res.status, string(res.body), path, res.quota, res.header)
	}
}

// do performs a single request attempt: build, send, read the full body, parse
// quota headers. A non-nil error is a transport-level failure (retryable); an
// HTTP error status is reported through the returned status code.
func (c *Client) do(ctx context.Context, method string, target string, bodyBytes []byte) (response, error) {
	return c.doWithHeaders(ctx, method, target, bodyBytes, nil)
}

func (c *Client) doWithHeaders(
	ctx context.Context,
	method string,
	target string,
	bodyBytes []byte,
	extraHeaders map[string]string,
) (response, error) {
	var bodyReader io.Reader
	if bodyBytes != nil {
		bodyReader = bytes.NewReader(bodyBytes)
	}
	req, err := http.NewRequestWithContext(ctx, method, target, bodyReader)
	if err != nil {
		return response{}, err
	}
	req.Header.Set("Authorization", "Bearer "+c.apiKey)
	req.Header.Set("User-Agent", c.userAgent)
	req.Header.Set("Accept", "application/json")
	if bodyBytes != nil {
		req.Header.Set("Content-Type", "application/json")
	}
	for k, v := range extraHeaders {
		if v != "" {
			req.Header.Set(k, v)
		}
	}

	resp, err := c.httpClient.Do(req)
	if err != nil {
		return response{}, err
	}
	defer resp.Body.Close()

	body, err := io.ReadAll(resp.Body)
	if err != nil {
		return response{}, err
	}
	return response{
		body:   body,
		quota:  parseQuota(resp.Header),
		status: resp.StatusCode,
		header: resp.Header,
	}, nil
}

// sleepBackoff waits out the jittered backoff for the given retry attempt,
// returning early with the context error if the context is cancelled.
func sleepBackoff(ctx context.Context, attempt int) error {
	idx := attempt
	if idx >= len(backoffs) {
		idx = len(backoffs) - 1
	}
	d := time.Duration(float64(backoffs[idx]) * (0.5 + rand.Float64()))
	timer := time.NewTimer(d)
	defer timer.Stop()
	select {
	case <-ctx.Done():
		return ctx.Err()
	case <-timer.C:
		return nil
	}
}

// Int returns a pointer to v — a convenience for setting optional *int list
// params inline (Limit, integer filters).
func Int(v int) *int { return &v }

// Int64 returns a pointer to v — a convenience for setting optional *int64 list
// params inline (FromID, bigint filters).
func Int64(v int64) *int64 { return &v }

// String returns a pointer to v — a convenience for setting optional *string
// list params inline.
func String(v string) *string { return &v }

// Bool returns a pointer to v — a convenience for setting optional *bool list
// params inline.
func Bool(v bool) *bool { return &v }

// Float64 returns a pointer to v — a convenience for setting optional *float64
// list params inline.
func Float64(v float64) *float64 { return &v }
