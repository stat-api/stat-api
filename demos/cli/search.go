package main

import (
	"context"
	"encoding/json"
	"io"
	"net/http"
	"net/url"
	"strconv"
	"strings"
	"time"

	statapi "github.com/stat-api/stat-api/go"
)

// searchHTTPClient bounds the one raw call the CLI makes outside the SDK.
var searchHTTPClient = &http.Client{Timeout: 30 * time.Second}

// searchPlayer is the subset of an nba.players row the player command renders.
// The search endpoint returns full player objects; we decode only what we use.
type searchPlayer struct {
	ID               int64   `json:"id"`
	FullName         string  `json:"full_name"`
	PrimaryPosition  string  `json:"primary_position"`
	TeamAbbreviation *string `json:"team_abbreviation"`
	TeamName         *string `json:"team_name"`
}

// searchNBAPlayers calls GET /api/v1/nba/players/search — the NBA-only
// handcrafted fuzzy-name endpoint that the generated SDK deliberately omits
// (handcrafted endpoints are out of the SDK surface; raw HTTP is the escape
// hatch). It mirrors the SDK's base-URL + bearer-auth conventions and maps HTTP
// error statuses onto the SDK's typed errors so callers get uniform handling.
func searchNBAPlayers(ctx context.Context, baseURL, apiKey, query string, limit int) ([]searchPlayer, error) {
	q := url.Values{}
	q.Set("q", query)
	if limit > 0 {
		q.Set("limit", strconv.Itoa(limit))
	}
	target := strings.TrimRight(baseURL, "/") + "/api/v1/nba/players/search?" + q.Encode()

	req, err := http.NewRequestWithContext(ctx, http.MethodGet, target, nil)
	if err != nil {
		return nil, err
	}
	req.Header.Set("Authorization", "Bearer "+apiKey)
	req.Header.Set("Accept", "application/json")

	resp, err := searchHTTPClient.Do(req)
	if err != nil {
		return nil, &statapi.APIError{Status: 0, Body: err.Error(), Path: "/api/v1/nba/players/search"}
	}
	defer resp.Body.Close()

	body, err := io.ReadAll(resp.Body)
	if err != nil {
		return nil, err
	}
	if resp.StatusCode < 200 || resp.StatusCode >= 300 {
		return nil, apiErrorForStatus(resp.StatusCode, string(body), "/api/v1/nba/players/search")
	}

	var players []searchPlayer
	if err := json.Unmarshal(body, &players); err != nil {
		return nil, err
	}
	return players, nil
}

// apiErrorForStatus reuses the SDK's exported typed errors so a failure from the
// raw helper flows through formatError exactly like an SDK failure.
func apiErrorForStatus(status int, body, path string) error {
	base := &statapi.APIError{Status: status, Body: body, Path: path}
	switch status {
	case http.StatusBadRequest:
		return &statapi.ValidationError{APIError: base}
	case http.StatusUnauthorized:
		return &statapi.AuthenticationError{APIError: base}
	case http.StatusPaymentRequired:
		return &statapi.PlanRequiredError{APIError: base}
	case http.StatusNotFound:
		return &statapi.NotFoundError{APIError: base}
	default:
		return base
	}
}
