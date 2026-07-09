package main

import (
	"errors"
	"fmt"
	"io"
	"math"
	"strings"
	"text/tabwriter"

	statapi "github.com/stat-api/stat-api/go"
)

// newTable returns a tabwriter configured for the CLI's aligned, space-padded
// columns. Callers must Flush before the underlying writer is inspected.
func newTable(w io.Writer) *tabwriter.Writer {
	return tabwriter.NewWriter(w, 0, 2, 2, ' ', 0)
}

// scoreRow is one game on a scoreboard, league-agnostic. started is false for
// games that have not begun (Scheduled/Postponed), where a score would be
// misleading.
type scoreRow struct {
	away, home           string
	awayScore, homeScore int
	status               string
	started              bool
}

func renderScoreboard(w io.Writer, rows []scoreRow) {
	tw := newTable(w)
	fmt.Fprintln(tw, "AWAY\tHOME\tSCORE\tSTATUS")
	for _, r := range rows {
		score := "—"
		if r.started {
			score = fmt.Sprintf("%d-%d", r.awayScore, r.homeScore)
		}
		fmt.Fprintf(tw, "%s\t%s\t%s\t%s\n", r.away, r.home, score, r.status)
	}
	tw.Flush()
}

// gameStarted reports whether a status string denotes a game that has produced
// a real score (final or in progress) versus one still on the schedule.
func gameStarted(status string) bool {
	switch strings.ToLower(strings.TrimSpace(status)) {
	case "", "scheduled", "postponed", "ppd", "suspended", "pre-game", "pregame", "warmup", "delayed":
		return false
	}
	return true
}

// formatDate renders a YYYYMMDD integer as YYYY-MM-DD.
func formatDate(d int) string {
	return fmt.Sprintf("%04d-%02d-%02d", d/10000, (d/100)%100, d%100)
}

// rate formats a made/attempted pair as a percentage, avoiding any assumption
// about how the API scales its stored shooting percentages.
func rate(made, attempted int) string {
	if attempted == 0 {
		return "—"
	}
	return fmt.Sprintf("%.1f%%", 100*float64(made)/float64(attempted))
}

// avg3 formats a batting-average-style rate to three decimals, dropping the
// leading zero (.312, 1.000).
func avg3(v float64) string {
	s := fmt.Sprintf("%.3f", v)
	return strings.TrimPrefix(s, "0")
}

func roundInt(v float64) int { return int(math.Round(v)) }

// formatError maps the SDK's typed errors onto a single friendly line so every
// command reports failures the same way — and a bad key never panics. Specific
// types are checked before the base *APIError they all wrap.
func formatError(err error) string {
	var auth *statapi.AuthenticationError
	var quota *statapi.QuotaExceededError
	var notFound *statapi.NotFoundError
	var validation *statapi.ValidationError
	var plan *statapi.PlanRequiredError
	var api *statapi.APIError

	switch {
	case errors.As(err, &auth):
		return "authentication failed — set a valid STAT_API_KEY (HTTP 401)"
	case errors.As(err, &quota):
		if quota.Limit != nil && quota.Used != nil {
			return fmt.Sprintf("monthly quota exhausted — %d of %d records used (HTTP 429)", *quota.Used, *quota.Limit)
		}
		return "monthly quota exhausted (HTTP 429)"
	case errors.As(err, &plan):
		return "this endpoint requires a paid plan (HTTP 402)"
	case errors.As(err, &notFound):
		return "not found (HTTP 404)"
	case errors.As(err, &validation):
		return "invalid request (HTTP 400)"
	case errors.As(err, &api):
		if api.Status == 0 {
			return "could not reach the API — check your connection and STAT_API_BASE_URL"
		}
		return fmt.Sprintf("API error (HTTP %d)", api.Status)
	default:
		return err.Error()
	}
}
