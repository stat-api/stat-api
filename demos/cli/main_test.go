package main

import (
	"bytes"
	"context"
	"errors"
	"flag"
	"io"
	"net/http"
	"net/http/httptest"
	"os"
	"path/filepath"
	"testing"

	statapi "github.com/stat-api/stat-api/go"
)

var update = flag.Bool("update", false, "rewrite golden files from current output")

// fixture is one canned HTTP response, matched by request path and (optionally)
// a subset of query parameters — enough to disambiguate calls that share a path.
type fixture struct {
	path    string
	query   map[string]string
	status  int
	headers map[string]string
	body    string
}

func fixtureServer(t *testing.T, fixtures []fixture) *httptest.Server {
	t.Helper()
	srv := httptest.NewServer(http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		got := r.URL.Query()
		for _, f := range fixtures {
			if f.path != r.URL.Path || !queryMatches(f.query, got) {
				continue
			}
			for k, v := range f.headers {
				w.Header().Set(k, v)
			}
			w.Header().Set("Content-Type", "application/json")
			status := f.status
			if status == 0 {
				status = http.StatusOK
			}
			w.WriteHeader(status)
			io.WriteString(w, f.body)
			return
		}
		t.Errorf("no fixture for %s?%s", r.URL.Path, r.URL.RawQuery)
		w.WriteHeader(http.StatusNotFound)
		io.WriteString(w, `{"error":"no fixture"}`)
	}))
	t.Cleanup(srv.Close)
	return srv
}

func queryMatches(want map[string]string, got map[string][]string) bool {
	for k, v := range want {
		vals, ok := got[k]
		if !ok || len(vals) == 0 || vals[0] != v {
			return false
		}
	}
	return true
}

func testClient(t *testing.T, srv *httptest.Server) *statapi.Client {
	t.Helper()
	c, err := statapi.NewWithOptions(statapi.Options{APIKey: "test", BaseURL: srv.URL})
	if err != nil {
		t.Fatalf("build client: %v", err)
	}
	return c
}

func assertGolden(t *testing.T, name, got string) {
	t.Helper()
	path := filepath.Join("testdata", name)
	if *update {
		if err := os.WriteFile(path, []byte(got), 0o644); err != nil {
			t.Fatalf("write golden %s: %v", name, err)
		}
		return
	}
	want, err := os.ReadFile(path)
	if err != nil {
		t.Fatalf("read golden %s: %v (run: go test -update)", name, err)
	}
	if got != string(want) {
		t.Errorf("golden mismatch for %s\n--- got ---\n%s\n--- want ---\n%s", name, got, want)
	}
}

func TestToday(t *testing.T) {
	srv := fixtureServer(t, []fixture{
		{path: "/api/v1/mlb/games", query: map[string]string{"day": "20260708"}, body: `{"games":[
			{"id":1,"away_team_id":147,"home_team_id":111,"away_team_score":3,"home_team_score":5,"status":"Final"},
			{"id":2,"away_team_id":119,"home_team_id":137,"away_team_score":2,"home_team_score":1,"status":"In Progress"},
			{"id":3,"away_team_id":112,"home_team_id":138,"away_team_score":0,"home_team_score":0,"status":"Scheduled"}
		],"limit":50,"next_from_id":null}`},
		{path: "/api/v1/mlb/teams", body: `{"teams":[
			{"id":147,"abbreviation":"NYY"},{"id":111,"abbreviation":"BOS"},
			{"id":119,"abbreviation":"LAD"},{"id":137,"abbreviation":"SF"},
			{"id":112,"abbreviation":"CHC"},{"id":138,"abbreviation":"STL"}
		],"limit":60,"next_from_id":null}`},
		{path: "/api/v1/nba/games", query: map[string]string{"day": "20260708"}, body: `{"games":[],"limit":50,"next_from_id":null}`},
		{path: "/api/v1/nfl/games", query: map[string]string{"status": "Final"}, body: `{"games":[
			{"id":880,"season_id":28,"week":22,"away_team":"Chiefs","home_team":"Eagles","away_team_score":25,"home_team_score":22,"status":"Final"}
		],"limit":1,"next_from_id":null}`},
		{path: "/api/v1/nfl/games", query: map[string]string{"season_id": "28", "week": "22"}, body: `{"games":[
			{"id":880,"season_id":28,"week":22,"away_team":"Chiefs","home_team":"Eagles","away_team_score":25,"home_team_score":22,"status":"Final"}
		],"limit":40,"next_from_id":null}`},
	})

	var buf bytes.Buffer
	if err := runToday(context.Background(), testClient(t, srv), &buf, 20260708); err != nil {
		t.Fatalf("runToday: %v", err)
	}
	assertGolden(t, "today.golden", buf.String())
}

func TestScoresNBAOffseason(t *testing.T) {
	srv := fixtureServer(t, []fixture{
		{path: "/api/v1/nba/games", query: map[string]string{"day": "20260708"}, body: `{"games":[],"limit":50,"next_from_id":null}`},
	})
	var buf bytes.Buffer
	if err := runScores(context.Background(), testClient(t, srv), &buf, "nba", 20260708, 0); err != nil {
		t.Fatalf("runScores: %v", err)
	}
	assertGolden(t, "nba_offseason.golden", buf.String())
}

func TestScoresNFLNoAnchor(t *testing.T) {
	srv := fixtureServer(t, []fixture{
		{path: "/api/v1/nfl/games", query: map[string]string{"status": "Final"}, body: `{"games":[],"limit":1,"next_from_id":null}`},
	})
	var buf bytes.Buffer
	if err := runScores(context.Background(), testClient(t, srv), &buf, "nfl", 0, 0); err != nil {
		t.Fatalf("runScores: %v", err)
	}
	assertGolden(t, "nfl_no_anchor.golden", buf.String())
}

func TestBoxMLB(t *testing.T) {
	srv := fixtureServer(t, []fixture{
		{path: "/api/v1/mlb/games/2026001", body: `{"id":2026001,"away_team_id":147,"home_team_id":111,"away_team_score":3,"home_team_score":5,"status":"Final"}`},
		{path: "/api/v1/mlb/teams", body: `{"teams":[{"id":147,"abbreviation":"NYY"},{"id":111,"abbreviation":"BOS"}],"limit":60,"next_from_id":null}`},
		{path: "/api/v1/mlb/players", query: map[string]string{"team_id": "147"}, body: `{"players":[
			{"id":1,"full_name":"Aaron Judge"},{"id":2,"full_name":"Gerrit Cole"}
		],"limit":100,"next_from_id":null}`},
		{path: "/api/v1/mlb/players", query: map[string]string{"team_id": "111"}, body: `{"players":[
			{"id":10,"full_name":"Rafael Devers"},{"id":11,"full_name":"Brayan Bello"}
		],"limit":100,"next_from_id":null}`},
		{path: "/api/v1/mlb/game_player_batter_stats", query: map[string]string{"game_id": "2026001"}, body: `{"game_player_batter_stats":[
			{"id":1,"game_id":2026001,"player_id":1,"team_id":147,"at_bats":4,"runs":1,"hits":2,"runs_batted_in":1,"walks":0,"strikeouts":1,"batting_average":0.312,"plate_appearances":4},
			{"id":2,"game_id":2026001,"player_id":10,"team_id":111,"at_bats":4,"runs":1,"hits":1,"runs_batted_in":2,"walks":1,"strikeouts":0,"batting_average":0.289,"plate_appearances":5}
		],"limit":100,"next_from_id":null}`},
		{path: "/api/v1/mlb/game_player_pitching_stats", query: map[string]string{"game_id": "2026001"}, body: `{"game_player_pitching_stats":[
			{"id":1,"game_id":2026001,"player_id":2,"team_id":147,"innings_pitched":6.0,"hits_allowed":5,"runs_allowed":5,"earned_runs":5,"walks_allowed":1,"strikeouts_pitched":7,"earned_run_average":3.50,"outs":18},
			{"id":2,"game_id":2026001,"player_id":11,"team_id":111,"innings_pitched":7.0,"hits_allowed":6,"runs_allowed":3,"earned_runs":3,"walks_allowed":2,"strikeouts_pitched":5,"earned_run_average":2.90,"outs":21}
		],"limit":100,"next_from_id":null}`},
	})
	var buf bytes.Buffer
	if err := runBox(context.Background(), testClient(t, srv), &buf, "mlb", 2026001); err != nil {
		t.Fatalf("runBox mlb: %v", err)
	}
	assertGolden(t, "box_mlb.golden", buf.String())
}

func TestBoxNBA(t *testing.T) {
	srv := fixtureServer(t, []fixture{
		{path: "/api/v1/nba/games/22026001", body: `{"id":22026001,"away_team_id":1610612747,"home_team_id":1610612738,"away_team":"Lakers","home_team":"Celtics","away_team_score":110,"home_team_score":118,"status":"Final"}`},
		{path: "/api/v1/nba/players", query: map[string]string{"team_id": "1610612747"}, body: `{"players":[
			{"id":2544,"full_name":"LeBron James"},{"id":203076,"full_name":"Anthony Davis"}
		],"limit":100,"next_from_id":null}`},
		{path: "/api/v1/nba/players", query: map[string]string{"team_id": "1610612738"}, body: `{"players":[
			{"id":1628369,"full_name":"Jayson Tatum"},{"id":1627759,"full_name":"Jaylen Brown"}
		],"limit":100,"next_from_id":null}`},
		{path: "/api/v1/nba/game_player_stats", query: map[string]string{"game_id": "22026001"}, body: `{"game_player_stats":[
			{"id":1,"game_id":22026001,"player_id":2544,"team_id":1610612747,"minutes":35.0,"pts":28,"rebounds":8,"assists":9,"steals":1,"blocks":1,"turnovers":3,"field_goals_made":10,"field_goals_attempted":20,"three_pointers_made":2,"three_pointers_attempted":6,"free_throws_made":6,"free_throws_attempted":7,"plus_minus":-5},
			{"id":2,"game_id":22026001,"player_id":203076,"team_id":1610612747,"minutes":33.0,"pts":22,"rebounds":12,"assists":2,"steals":0,"blocks":3,"turnovers":2,"field_goals_made":9,"field_goals_attempted":17,"three_pointers_made":0,"three_pointers_attempted":1,"free_throws_made":4,"free_throws_attempted":5,"plus_minus":-8},
			{"id":3,"game_id":22026001,"player_id":1628369,"team_id":1610612738,"minutes":38.0,"pts":31,"rebounds":9,"assists":6,"steals":2,"blocks":1,"turnovers":2,"field_goals_made":11,"field_goals_attempted":21,"three_pointers_made":4,"three_pointers_attempted":9,"free_throws_made":5,"free_throws_attempted":6,"plus_minus":10},
			{"id":4,"game_id":22026001,"player_id":1627759,"team_id":1610612738,"minutes":36.0,"pts":26,"rebounds":6,"assists":3,"steals":1,"blocks":0,"turnovers":4,"field_goals_made":10,"field_goals_attempted":19,"three_pointers_made":3,"three_pointers_attempted":7,"free_throws_made":3,"free_throws_attempted":4,"plus_minus":8}
		],"limit":60,"next_from_id":null}`},
	})
	var buf bytes.Buffer
	if err := runBox(context.Background(), testClient(t, srv), &buf, "nba", 22026001); err != nil {
		t.Fatalf("runBox nba: %v", err)
	}
	assertGolden(t, "box_nba.golden", buf.String())
}

func TestStandings(t *testing.T) {
	srv := fixtureServer(t, []fixture{
		{path: "/api/v1/nba/games", query: map[string]string{"limit": "1"}, body: `{"games":[{"id":9,"season_id":22025}],"limit":1,"next_from_id":null}`},
		{path: "/api/v1/nba/teams", body: `{"teams":[
			{"id":1610612738,"abbreviation":"BOS","full_name":"Boston Celtics","conference":"East"},
			{"id":1610612739,"abbreviation":"CLE","full_name":"Cleveland Cavaliers","conference":"East"},
			{"id":1610612743,"abbreviation":"DEN","full_name":"Denver Nuggets","conference":"West"},
			{"id":1610612747,"abbreviation":"LAL","full_name":"Los Angeles Lakers","conference":"West"}
		],"limit":50,"next_from_id":null}`},
		{path: "/api/v1/nba/team_standings", query: map[string]string{"season_id": "22025"}, body: `{"team_standings":[
			{"id":1,"season_id":22025,"team_id":1610612738,"day":20260410,"wins":58,"losses":24,"win_pct":0.707,"conference_rank":1,"conference_gb":0,"streak":3,"l10_wins":8,"l10_losses":2},
			{"id":2,"season_id":22025,"team_id":1610612739,"day":20260410,"wins":48,"losses":34,"win_pct":0.585,"conference_rank":2,"conference_gb":10,"streak":-1,"l10_wins":5,"l10_losses":5},
			{"id":3,"season_id":22025,"team_id":1610612743,"day":20260410,"wins":53,"losses":29,"win_pct":0.646,"conference_rank":1,"conference_gb":0,"streak":2,"l10_wins":7,"l10_losses":3},
			{"id":4,"season_id":22025,"team_id":1610612747,"day":20260410,"wins":50,"losses":32,"win_pct":0.610,"conference_rank":2,"conference_gb":3,"streak":1,"l10_wins":6,"l10_losses":4}
		],"limit":120,"next_from_id":null}`},
		{path: "/api/v1/nba/seasons/22025", body: `{"id":22025,"start_year":2025,"start_date":"2025-10-21"}`},
	})
	var buf bytes.Buffer
	if err := runStandings(context.Background(), testClient(t, srv), &buf, "nba"); err != nil {
		t.Fatalf("runStandings: %v", err)
	}
	assertGolden(t, "standings.golden", buf.String())
}

func TestPlayer(t *testing.T) {
	srv := fixtureServer(t, []fixture{
		{path: "/api/v1/nba/players/search", query: map[string]string{"q": "LeBron"}, body: `[
			{"id":2544,"full_name":"LeBron James","primary_position":"F","team_abbreviation":"LAL","team_name":"Los Angeles Lakers"},
			{"id":1641705,"full_name":"Bronny James","primary_position":"G","team_abbreviation":"LAL","team_name":"Los Angeles Lakers"}
		]`},
		{path: "/api/v1/nba/season_player_stats", query: map[string]string{"player_id": "2544"}, body: `{"season_player_stats":[
			{"id":1,"player_id":2544,"season_id":22025,"games_played":70,"pts":1750,"rebounds":550,"assists":560,"steals":90,"blocks":40,"field_goals_made":700,"field_goals_attempted":1300,"three_pointers_made":130,"three_pointers_attempted":360,"free_throws_made":220,"free_throws_attempted":300}
		],"limit":30,"next_from_id":null}`},
		{path: "/api/v1/nba/seasons/22025", body: `{"id":22025,"start_year":2025,"start_date":"2025-10-21"}`},
	})
	var buf bytes.Buffer
	if err := runPlayer(context.Background(), testClient(t, srv), &buf, srv.URL, "test", "LeBron"); err != nil {
		t.Fatalf("runPlayer: %v", err)
	}
	assertGolden(t, "player.golden", buf.String())
}

func TestQuota(t *testing.T) {
	srv := fixtureServer(t, []fixture{
		{path: "/api/v1/nba/teams", query: map[string]string{"limit": "1"},
			headers: map[string]string{
				"X-Quota-Limit":     "5000000",
				"X-Quota-Used":      "1234567",
				"X-Quota-Remaining": "3765433",
			},
			body: `{"teams":[{"id":1610612738,"abbreviation":"BOS"}],"limit":1,"next_from_id":null}`},
	})
	var buf bytes.Buffer
	if err := runQuota(context.Background(), testClient(t, srv), &buf); err != nil {
		t.Fatalf("runQuota: %v", err)
	}
	assertGolden(t, "quota.golden", buf.String())
}

func TestErrorRendering401(t *testing.T) {
	srv := fixtureServer(t, []fixture{
		{path: "/api/v1/nba/teams", status: http.StatusUnauthorized, body: `{"error":"invalid_api_key"}`},
	})
	err := runQuota(context.Background(), testClient(t, srv), io.Discard)
	if err == nil {
		t.Fatal("expected an error from a 401 response")
	}
	var authErr *statapi.AuthenticationError
	if !errors.As(err, &authErr) {
		t.Fatalf("expected AuthenticationError, got %T", err)
	}
	if got, want := formatError(err), "authentication failed — set a valid STAT_API_KEY (HTTP 401)"; got != want {
		t.Errorf("formatError = %q, want %q", got, want)
	}
}

func TestSearchError401(t *testing.T) {
	srv := fixtureServer(t, []fixture{
		{path: "/api/v1/nba/players/search", status: http.StatusUnauthorized, body: `{"error":"invalid_api_key"}`},
	})
	_, err := searchNBAPlayers(context.Background(), srv.URL, "test", "LeBron", 10)
	var authErr *statapi.AuthenticationError
	if !errors.As(err, &authErr) {
		t.Fatalf("expected AuthenticationError from raw search helper, got %T (%v)", err, err)
	}
}
