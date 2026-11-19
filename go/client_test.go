package statapi

// Tests for the 202 Accepted rule: a 202 holds NO data, so the client returns
// *NotReadyError instead of the body, and never retries it. They run against a
// local httptest server (127.0.0.1, ephemeral port): no live API.

import (
	"context"
	"errors"
	"io"
	"net/http"
	"net/http/httptest"
	"strings"
	"sync"
	"testing"
	"time"
)

const queuedBody = `{
  "status": "queued",
  "message": "The lineups of this contest are queued to load.",
  "contest_id": 29647259,
  "external_id": 196420231,
  "requested_at": "2026-10-12T02:26:11Z",
  "estimated_ready_at": "2026-10-12T02:42:00Z",
  "retry_after_seconds": 120,
  "entry_count": 340
}`

type reply struct {
	status  int
	body    string
	headers map[string]string
}

// fakeAPI serves the scripted replies in order (then 200 {}) and records paths and request bodies.
type fakeAPI struct {
	mu      sync.Mutex
	replies []reply
	paths   []string
	bodies  []string
}

func (f *fakeAPI) ServeHTTP(w http.ResponseWriter, r *http.Request) {
	f.mu.Lock()
	f.paths = append(f.paths, r.URL.RequestURI())
	body, _ := io.ReadAll(r.Body)
	f.bodies = append(f.bodies, string(body))
	next := reply{status: http.StatusOK, body: "{}"}
	if len(f.replies) > 0 {
		next = f.replies[0]
		f.replies = f.replies[1:]
	}
	f.mu.Unlock()

	w.Header().Set("Content-Type", "application/json")
	for name, value := range next.headers {
		w.Header().Set(name, value)
	}
	w.WriteHeader(next.status)
	_, _ = w.Write([]byte(next.body))
}

func (f *fakeAPI) requests() int {
	f.mu.Lock()
	defer f.mu.Unlock()
	return len(f.paths)
}

// newTestClient starts a fake API with the scripted replies and returns a
// client pointed at it. Retry backoff is shortened for the test's duration.
func newTestClient(t *testing.T, replies ...reply) (*Client, *fakeAPI) {
	t.Helper()
	saved := backoffs
	backoffs = []time.Duration{time.Millisecond, time.Millisecond}
	t.Cleanup(func() { backoffs = saved })

	api := &fakeAPI{replies: replies}
	srv := httptest.NewServer(api)
	t.Cleanup(srv.Close)

	client, err := NewWithOptions(Options{APIKey: "test-key", BaseURL: srv.URL})
	if err != nil {
		t.Fatalf("NewWithOptions: %v", err)
	}
	return client, api
}

func wantNotReady(t *testing.T, err error) *NotReadyError {
	t.Helper()
	var notReady *NotReadyError
	if !errors.As(err, &notReady) {
		t.Fatalf("want *NotReadyError, got %T: %v", err, err)
	}
	return notReady
}

func TestNotReady202CarriesEveryFieldAndIsNotRetried(t *testing.T) {
	client, api := newTestClient(t, reply{
		status:  http.StatusAccepted,
		body:    queuedBody,
		headers: map[string]string{"Retry-After": "120"},
	})

	row, err := client.DFS.Contests.Get(context.Background(), 29647259)
	if row != nil {
		t.Fatalf("a 202 must not return data, got %+v", row)
	}
	notReady := wantNotReady(t, err)

	var base *APIError
	if !errors.As(err, &base) {
		t.Fatalf("NotReadyError must wrap *APIError")
	}
	if base.Status != 202 || notReady.Status != 202 {
		t.Errorf("status: want 202, got %d", notReady.Status)
	}
	if notReady.Path != "/api/v1/dfs/contests/29647259" {
		t.Errorf("path: got %q", notReady.Path)
	}
	if notReady.RetryAfterSeconds == nil || *notReady.RetryAfterSeconds != 120 {
		t.Errorf("RetryAfterSeconds: want 120, got %v", notReady.RetryAfterSeconds)
	}
	if notReady.EstimatedReadyAt != "2026-10-12T02:42:00Z" {
		t.Errorf("EstimatedReadyAt: got %q", notReady.EstimatedReadyAt)
	}
	if notReady.State != "queued" {
		t.Errorf("State: got %q", notReady.State)
	}
	if notReady.Message != "The lineups of this contest are queued to load." {
		t.Errorf("Message: got %q", notReady.Message)
	}
	if !strings.Contains(notReady.Body, `"entry_count": 340`) {
		t.Errorf("Body must hold the raw 202 body, got %q", notReady.Body)
	}
	if !strings.Contains(notReady.Error(), "202") || !strings.Contains(notReady.Error(), notReady.Message) {
		t.Errorf("Error(): got %q", notReady.Error())
	}
	if n := api.requests(); n != 1 {
		t.Errorf("a 202 must not be retried: want 1 request, got %d", n)
	}
}

func TestNotReadyWaitingForContestHasEmptyEstimate(t *testing.T) {
	client, _ := newTestClient(t, reply{
		status: http.StatusAccepted,
		body: `{"status":"waiting_for_contest","message":"waiting","estimated_ready_at":null,` +
			`"retry_after_seconds":900}`,
	})

	_, _, err := client.get(context.Background(), "/api/v1/dfs/contests/1/standings", nil)
	notReady := wantNotReady(t, err)
	if notReady.State != "waiting_for_contest" {
		t.Errorf("State: got %q", notReady.State)
	}
	if notReady.EstimatedReadyAt != "" {
		t.Errorf("EstimatedReadyAt: want empty, got %q", notReady.EstimatedReadyAt)
	}
	if notReady.RetryAfterSeconds == nil || *notReady.RetryAfterSeconds != 900 {
		t.Errorf("RetryAfterSeconds: want 900, got %v", notReady.RetryAfterSeconds)
	}
}

func TestNotReadyRetryAfterSourceOrder(t *testing.T) {
	cases := []struct {
		name   string
		body   string
		header string
		want   *int64
	}{
		{"header used when the body has none", `{"status":"queued"}`, "45", Int64(45)},
		{"body wins over the header", `{"retry_after_seconds":120}`, "999", Int64(120)},
		{"zero in the body is kept", `{"retry_after_seconds":0}`, "30", Int64(0)},
		{"HTTP-date header is not usable", `{"status":"queued"}`, "Wed, 21 Oct 2026 07:28:00 GMT", nil},
		{"negative header is not usable", `{"status":"queued"}`, "-5", nil},
		{"neither gives nil", `{"status":"queued"}`, "", nil},
	}
	for _, tc := range cases {
		t.Run(tc.name, func(t *testing.T) {
			headers := map[string]string{}
			if tc.header != "" {
				headers["Retry-After"] = tc.header
			}
			client, _ := newTestClient(t, reply{status: http.StatusAccepted, body: tc.body, headers: headers})
			_, _, err := client.get(context.Background(), "/api/v1/dfs/contests/1/download", nil)
			got := wantNotReady(t, err).RetryAfterSeconds
			switch {
			case tc.want == nil && got != nil:
				t.Errorf("want nil, got %d", *got)
			case tc.want != nil && (got == nil || *got != *tc.want):
				t.Errorf("want %d, got %v", *tc.want, got)
			}
		})
	}
}

func TestNotReadyNonJSONBodyStillRaises(t *testing.T) {
	client, _ := newTestClient(t, reply{status: http.StatusAccepted, body: "accepted"})

	_, _, err := client.get(context.Background(), "/api/v1/dfs/contests/1/download", nil)
	notReady := wantNotReady(t, err)
	if notReady.Body != "accepted" {
		t.Errorf("Body: got %q", notReady.Body)
	}
	if notReady.State != "" || notReady.EstimatedReadyAt != "" || notReady.RetryAfterSeconds != nil {
		t.Errorf("fields must be empty, got %+v", notReady)
	}
	if notReady.Message == "" {
		t.Errorf("Message must fall back to a default sentence")
	}
}

func TestOtherStatusesAreUnchanged(t *testing.T) {
	t.Run("200 still returns data", func(t *testing.T) {
		client, api := newTestClient(t, reply{status: http.StatusOK, body: `{"id":1,"name":"Cardinals"}`})
		team, err := client.NFL.Teams.Get(context.Background(), 1)
		if err != nil {
			t.Fatalf("Get: %v", err)
		}
		if team.ID != 1 || team.Name != "Cardinals" {
			t.Errorf("row: got %+v", team)
		}
		if n := api.requests(); n != 1 {
			t.Errorf("want 1 request, got %d", n)
		}
	})

	t.Run("500 is still retried until a 200", func(t *testing.T) {
		client, api := newTestClient(t,
			reply{status: http.StatusInternalServerError, body: `{"error":"internal"}`},
			reply{status: http.StatusInternalServerError, body: `{"error":"internal"}`},
			reply{status: http.StatusOK, body: `{"id":1}`},
		)
		team, err := client.NFL.Teams.Get(context.Background(), 1)
		if err != nil {
			t.Fatalf("Get: %v", err)
		}
		if team.ID != 1 {
			t.Errorf("row: got %+v", team)
		}
		if n := api.requests(); n != 3 {
			t.Errorf("want 3 requests, got %d", n)
		}
	})

	t.Run("500 that never recovers is an APIError after three attempts", func(t *testing.T) {
		client, api := newTestClient(t,
			reply{status: http.StatusInternalServerError, body: `{"error":"internal"}`},
			reply{status: http.StatusInternalServerError, body: `{"error":"internal"}`},
			reply{status: http.StatusInternalServerError, body: `{"error":"internal"}`},
		)
		_, err := client.NFL.Teams.Get(context.Background(), 1)
		var notReady *NotReadyError
		if errors.As(err, &notReady) {
			t.Fatalf("a 500 must not be a NotReadyError")
		}
		var base *APIError
		if !errors.As(err, &base) || base.Status != 500 {
			t.Fatalf("want *APIError status 500, got %T: %v", err, err)
		}
		if n := api.requests(); n != 3 {
			t.Errorf("want 3 requests, got %d", n)
		}
	})

	t.Run("404 is still NotFoundError and not retried", func(t *testing.T) {
		client, api := newTestClient(t, reply{status: http.StatusNotFound, body: `{"error":"not-found"}`})
		_, err := client.NFL.Teams.Get(context.Background(), 99999)
		var notFound *NotFoundError
		if !errors.As(err, &notFound) {
			t.Fatalf("want *NotFoundError, got %T: %v", err, err)
		}
		if n := api.requests(); n != 1 {
			t.Errorf("want 1 request, got %d", n)
		}
	})
}

func TestNFLAndNBASimPublicEndpoints(t *testing.T) {
	nflRunBody := `{"matchup":{"game_id":43418,"season":2026,"week":1,"season_type":"REG","home_team_id":12,"home_abbr":"KC","away_team_id":7,"away_abbr":"DEN","spread_home":-3.0,"game_total":47.5,"home_team_total":25.25,"away_team_total":22.25,"is_final":true,"is_free":true},"input_sha256":"sha256-nfl","output":{"game_id":43418,"n_draws":5000,"seed":42,"solve_elapsed_ms":1.2,"sim_elapsed_ms":14.5,"pbp":[]}}`
	nbaRunBody := `{"matchup":{"game_id":6029936,"season":2025,"week":1,"season_type":"REG","kickoff":"2025-11-12T00:00:00Z","day":1,"home_team_id":2,"home_abbr":"BOS","away_team_id":8,"away_abbr":"DET","spread_home":-8.5,"game_total":228.5,"home_team_total":118.5,"away_team_total":110.0,"moneyline_home":-340,"moneyline_away":270,"is_final":true,"is_free":true},"input_sha256":"sha256-nba","output":{"game_id":"6029936","n_draws":5000,"seed":42,"solve_elapsed_ms":1.0,"sim_elapsed_ms":12.0}}`
	client, api := newTestClient(t,
		reply{status: http.StatusOK, body: `{"season":2026,"available_seasons":[2026],"free_game_id":43418,"weeks":[1],"games":[{"game_id":43418,"season":2026,"week":1,"season_type":"REG","home_team_id":12,"home_abbr":"KC","away_team_id":7,"away_abbr":"DEN","spread_home":-3.0,"game_total":47.5,"home_team_total":25.25,"away_team_total":22.25,"is_final":true,"is_free":true}]}`},
		reply{status: http.StatusOK, body: nflRunBody},
		reply{status: http.StatusOK, body: `{"season":2025,"available_seasons":[2025],"free_game_id":6029936,"weeks":[1],"games":[]}`},
		reply{status: http.StatusOK, body: nbaRunBody},
	)

	ctx := context.Background()
	weeks, err := client.NFL.Weeks(ctx, &NFLWeeksParams{Season: Int(2026)})
	if err != nil {
		t.Fatalf("NFL.Weeks: %v", err)
	}
	if weeks.Season != 2026 || weeks.FreeGameID != 43418 || len(weeks.Games) != 1 || weeks.Games[0].GameID != 43418 {
		t.Errorf("unexpected weeks response: %+v", weeks)
	}

	runRes, err := client.Sim.NFL.Run(ctx, &NFLSimPublicRunRequest{
		GameID: 43418,
		NDraws: Int(5000),
		Total:  Float64(46.5),
		Include: &NFLSimInclude{
			Summary:       Bool(true),
			Distributions: Bool(true),
			Scorebox:      Bool(false),
		},
	})
	if err != nil {
		t.Fatalf("Sim.NFL.Run: %v", err)
	}
	if runRes.Output.GameID != 43418 || runRes.Output.NDraws != 5000 {
		t.Errorf("unexpected run response: %+v", runRes)
	}

	// A false flag must reach the server: its default is true, and omitempty drops a plain false.
	wantRunBody := `{"game_id":43418,"n_draws":5000,"include":{"summary":true,"distributions":true,"scorebox":false},"total":46.5}`
	if len(api.bodies) < 2 || api.bodies[1] != wantRunBody {
		t.Errorf("run body: want %s, got %v", wantRunBody, api.bodies)
	}

	nbaWeeks, err := client.NBA.Weeks(ctx, &NBAWeeksParams{Season: Int(2025)})
	if err != nil || nbaWeeks.FreeGameID != 6029936 {
		t.Fatalf("NBA.Weeks: err=%v res=%+v", err, nbaWeeks)
	}
	if nbaRun, err := client.Sim.NBA.Run(ctx, &NBASimPublicRunRequest{GameID: 6029936, NDraws: Int(5000), Total: Float64(228.5)}); err != nil || nbaRun.Output.NDraws != 5000 {
		t.Fatalf("Sim.NBA.Run: err=%v res=%+v", err, nbaRun)
	}

	wantPaths := []string{
		"/api/v1/nfl/weeks?season=2026",
		"/api/v1/sim/nfl/run",
		"/api/v1/nba/weeks?season=2025",
		"/api/v1/sim/nba/run",
	}
	for i, want := range wantPaths {
		if i >= len(api.paths) || api.paths[i] != want {
			t.Errorf("path[%d]: want %q, got %v", i, want, api.paths)
		}
	}
}
