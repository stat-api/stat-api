package main

import (
	"context"
	"fmt"
	"io"
	"sort"

	statapi "github.com/stat-api/stat-api/go"
)

// runStandings renders league standings. NBA is the only league surfaced in v1
// (the standings endpoint the demo leans on); other leagues report that plainly.
func runStandings(ctx context.Context, c *statapi.Client, out io.Writer, league string) error {
	if league != "nba" {
		return fmt.Errorf("standings: only nba is supported in v1 (got %q)", league)
	}

	season, err := newestNBASeasonID(ctx, c)
	if err != nil {
		return err
	}
	teams, err := nbaTeamIndex(ctx, c)
	if err != nil {
		return err
	}
	page, err := c.NBA.TeamStandings.List(ctx, &statapi.NBATeamStandingsListParams{
		SeasonID: &season,
		Limit:    statapi.Int(120),
	})
	if err != nil {
		return err
	}
	if len(page.Rows) == 0 {
		fmt.Fprintln(out, "No standings available.")
		return nil
	}

	// Standings are snapshotted per day; keep each team's most recent snapshot.
	latest := make(map[int64]statapi.NBATeamStanding)
	for _, r := range page.Rows {
		if cur, ok := latest[r.TeamID]; !ok || r.Day > cur.Day {
			latest[r.TeamID] = r
		}
	}

	byConf := map[string][]statapi.NBATeamStanding{}
	for _, r := range latest {
		conf := "League"
		if t, ok := teams[r.TeamID]; ok && t.Conference != "" {
			conf = t.Conference
		}
		byConf[conf] = append(byConf[conf], r)
	}

	fmt.Fprintf(out, "NBA Standings · %s\n", seasonLabel(ctx, c, season))
	for _, conf := range sortedKeys(byConf) {
		renderConference(out, conf, byConf[conf], teams)
	}
	return nil
}

func renderConference(out io.Writer, conf string, rows []statapi.NBATeamStanding, teams map[int64]statapi.NBATeam) {
	sort.Slice(rows, func(i, j int) bool {
		if rows[i].ConferenceRank != rows[j].ConferenceRank {
			return rows[i].ConferenceRank < rows[j].ConferenceRank
		}
		return rows[i].WinPct > rows[j].WinPct
	})

	fmt.Fprintf(out, "\n%s\n", conf)
	tw := newTable(out)
	fmt.Fprintln(tw, "#\tTEAM\tW\tL\tPCT\tGB\tSTRK\tL10")
	for _, r := range rows {
		fmt.Fprintf(tw, "%d\t%s\t%d\t%d\t%s\t%s\t%s\t%d-%d\n",
			r.ConferenceRank, teamLabel(teams, r.TeamID), r.Wins, r.Losses,
			avg3(r.WinPct), gamesBack(r.ConferenceGb), streak(r.Streak),
			r.L10Wins, r.L10Losses)
	}
	tw.Flush()
}

func gamesBack(gb float64) string {
	if gb <= 0 {
		return "—"
	}
	return fmt.Sprintf("%.1f", gb)
}

func streak(s int) string {
	switch {
	case s > 0:
		return fmt.Sprintf("W%d", s)
	case s < 0:
		return fmt.Sprintf("L%d", -s)
	default:
		return "—"
	}
}

func newestNBASeasonID(ctx context.Context, c *statapi.Client) (int64, error) {
	page, err := c.NBA.Games.List(ctx, &statapi.NBAGamesListParams{Limit: statapi.Int(1)})
	if err != nil {
		return 0, err
	}
	if len(page.Rows) == 0 {
		return 0, fmt.Errorf("no NBA games available")
	}
	return page.Rows[0].SeasonID, nil
}

func nbaTeamIndex(ctx context.Context, c *statapi.Client) (map[int64]statapi.NBATeam, error) {
	page, err := c.NBA.Teams.List(ctx, &statapi.NBATeamsListParams{Limit: statapi.Int(50)})
	if err != nil {
		return nil, err
	}
	idx := make(map[int64]statapi.NBATeam, len(page.Rows))
	for _, t := range page.Rows {
		idx[t.ID] = t
	}
	return idx, nil
}

func teamLabel(idx map[int64]statapi.NBATeam, id int64) string {
	if t, ok := idx[id]; ok {
		if t.FullName != "" {
			return t.FullName
		}
		if t.Abbreviation != "" {
			return t.Abbreviation
		}
	}
	return fmt.Sprintf("#%d", id)
}

// seasonLabel turns a season id into a display span like "2025-26", falling
// back to the raw id if the lookup fails.
func seasonLabel(ctx context.Context, c *statapi.Client, seasonID int64) string {
	s, err := c.NBA.Seasons.Get(ctx, seasonID)
	if err != nil {
		return fmt.Sprintf("season %d", seasonID)
	}
	return fmt.Sprintf("%d-%02d", s.StartYear, (s.StartYear+1)%100)
}

func sortedKeys[V any](m map[string]V) []string {
	keys := make([]string, 0, len(m))
	for k := range m {
		keys = append(keys, k)
	}
	sort.Strings(keys)
	return keys
}
