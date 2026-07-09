package main

import (
	"context"
	"fmt"
	"io"

	statapi "github.com/stat-api/stat-api/go"
)

// runPlayer looks an NBA player up by name (fuzzy search) and prints the
// matches plus the top match's most recent season averages. Name search is the
// NBA-only handcrafted endpoint the generated SDK does not cover, so it goes
// through the raw helper in search.go; season stats use the SDK.
func runPlayer(ctx context.Context, c *statapi.Client, out io.Writer, baseURL, apiKey, name string) error {
	players, err := searchNBAPlayers(ctx, baseURL, apiKey, name, 10)
	if err != nil {
		return err
	}
	if len(players) == 0 {
		fmt.Fprintf(out, "No NBA players matched %q.\n", name)
		return nil
	}

	renderPlayerMatches(out, name, players)

	top := players[0]
	stats, err := latestSeasonStats(ctx, c, top.ID)
	if err != nil {
		return err
	}
	if stats == nil {
		fmt.Fprintf(out, "\nNo season stats on record for %s.\n", top.FullName)
		return nil
	}
	renderPlayerSeason(out, top, stats, seasonLabel(ctx, c, stats.SeasonID))
	return nil
}

func renderPlayerMatches(out io.Writer, query string, players []searchPlayer) {
	fmt.Fprintf(out, "NBA player search · %q\n\n", query)
	tw := newTable(out)
	fmt.Fprintln(tw, "#\tPLAYER\tPOS\tTEAM")
	for i, p := range players {
		fmt.Fprintf(tw, "%d\t%s\t%s\t%s\n", i+1, p.FullName, dash(p.PrimaryPosition), playerTeam(p))
	}
	tw.Flush()
}

func renderPlayerSeason(out io.Writer, p searchPlayer, s *statapi.NBASeasonPlayerStat, label string) {
	games := s.GamesPlayed
	per := func(total int) float64 {
		if games == 0 {
			return 0
		}
		return float64(total) / float64(games)
	}

	fmt.Fprintf(out, "\n%s · %s season averages (%d GP)\n\n", p.FullName, label, games)
	tw := newTable(out)
	fmt.Fprintln(tw, "PTS\tREB\tAST\tSTL\tBLK\tFG%\t3P%\tFT%")
	fmt.Fprintf(tw, "%.1f\t%.1f\t%.1f\t%.1f\t%.1f\t%s\t%s\t%s\n",
		per(s.Pts), per(s.Rebounds), per(s.Assists), per(s.Steals), per(s.Blocks),
		rate(s.FieldGoalsMade, s.FieldGoalsAttempted),
		rate(s.ThreePointersMade, s.ThreePointersAttempted),
		rate(s.FreeThrowsMade, s.FreeThrowsAttempted))
	tw.Flush()
}

// latestSeasonStats returns the player's most recent season row (highest season
// id), or nil when the player has no season stats.
func latestSeasonStats(ctx context.Context, c *statapi.Client, playerID int64) (*statapi.NBASeasonPlayerStat, error) {
	page, err := c.NBA.SeasonPlayerStats.List(ctx, &statapi.NBASeasonPlayerStatsListParams{
		PlayerID: &playerID,
		Limit:    statapi.Int(30),
	})
	if err != nil {
		return nil, err
	}
	if len(page.Rows) == 0 {
		return nil, nil
	}
	best := page.Rows[0]
	for _, r := range page.Rows {
		if r.SeasonID > best.SeasonID {
			best = r
		}
	}
	return &best, nil
}

func playerTeam(p searchPlayer) string {
	if p.TeamAbbreviation != nil && *p.TeamAbbreviation != "" {
		return *p.TeamAbbreviation
	}
	if p.TeamName != nil && *p.TeamName != "" {
		return *p.TeamName
	}
	return "FA"
}

func dash(s string) string {
	if s == "" {
		return "—"
	}
	return s
}
