package main

import (
	"context"
	"fmt"
	"io"

	statapi "github.com/stat-api/stat-api/go"
)

// runToday renders today's slate across the live leagues: MLB and NBA keyed by
// the given YYYYMMDD date, NFL by its current (most-recently-completed) week.
// NHL is coming-soon on the public API and is reported as such, not queried.
func runToday(ctx context.Context, c *statapi.Client, out io.Writer, date int) error {
	fmt.Fprintf(out, "Scores · %s\n\n", formatDate(date))

	if err := sectionMLB(ctx, c, out, date); err != nil {
		return err
	}
	fmt.Fprintln(out)
	if err := sectionNBA(ctx, c, out, date); err != nil {
		return err
	}
	fmt.Fprintln(out)
	if err := sectionNFLCurrent(ctx, c, out); err != nil {
		return err
	}
	fmt.Fprintln(out)
	fmt.Fprintln(out, "NHL · coming soon")
	return nil
}

// runScores renders a single league's scoreboard. MLB/NBA use date (0 = today);
// NFL uses week (0 = current week, resolved from the newest completed game).
func runScores(ctx context.Context, c *statapi.Client, out io.Writer, league string, date, week int) error {
	switch league {
	case "mlb":
		if date == 0 {
			date = todayET()
		}
		return sectionMLB(ctx, c, out, date)
	case "nba":
		if date == 0 {
			date = todayET()
		}
		return sectionNBA(ctx, c, out, date)
	case "nfl":
		if week == 0 {
			return sectionNFLCurrent(ctx, c, out)
		}
		season, err := nflSeasonID(ctx, c)
		if err != nil {
			return err
		}
		fmt.Fprintln(out, "NFL")
		return renderNFLWeek(ctx, c, out, season, week)
	default:
		return fmt.Errorf("unknown league %q (use mlb, nba, or nfl)", league)
	}
}

func sectionMLB(ctx context.Context, c *statapi.Client, out io.Writer, date int) error {
	fmt.Fprintf(out, "MLB · %s\n", formatDate(date))
	page, err := c.MLB.Games.List(ctx, &statapi.MLBGamesListParams{Day: statapi.Int(date), Limit: statapi.Int(50)})
	if err != nil {
		return err
	}
	if len(page.Rows) == 0 {
		fmt.Fprintln(out, "  No games scheduled.")
		return nil
	}
	teams, err := mlbTeamIndex(ctx, c)
	if err != nil {
		return err
	}
	rows := make([]scoreRow, 0, len(page.Rows))
	for _, g := range page.Rows {
		rows = append(rows, scoreRow{
			away:      mlbAbbr(teams, g.AwayTeamID),
			home:      mlbAbbr(teams, g.HomeTeamID),
			awayScore: g.AwayTeamScore,
			homeScore: g.HomeTeamScore,
			status:    g.Status,
			started:   gameStarted(g.Status),
		})
	}
	renderScoreboard(out, rows)
	return nil
}

func sectionNBA(ctx context.Context, c *statapi.Client, out io.Writer, date int) error {
	fmt.Fprintf(out, "NBA · %s\n", formatDate(date))
	page, err := c.NBA.Games.List(ctx, &statapi.NBAGamesListParams{Day: statapi.Int(date), Limit: statapi.Int(50)})
	if err != nil {
		return err
	}
	if len(page.Rows) == 0 {
		fmt.Fprintln(out, "  No games scheduled.")
		return nil
	}
	rows := make([]scoreRow, 0, len(page.Rows))
	for _, g := range page.Rows {
		rows = append(rows, scoreRow{
			away:      g.AwayTeam,
			home:      g.HomeTeam,
			awayScore: g.AwayTeamScore,
			homeScore: g.HomeTeamScore,
			status:    g.Status,
			started:   gameStarted(g.Status),
		})
	}
	renderScoreboard(out, rows)
	return nil
}

// sectionNFLCurrent resolves the current NFL week from the newest completed
// game (lists are newest-first) and renders it. With no completed game to
// anchor on, it prints the week-based hint instead of guessing.
func sectionNFLCurrent(ctx context.Context, c *statapi.Client, out io.Writer) error {
	fmt.Fprintln(out, "NFL")
	final := "Final"
	anchor, err := c.NFL.Games.List(ctx, &statapi.NFLGamesListParams{Status: &final, Limit: statapi.Int(1)})
	if err != nil {
		return err
	}
	if len(anchor.Rows) == 0 {
		fmt.Fprintln(out, "  Week-based schedule — no completed games found. Try: sports scores --league nfl --week N")
		return nil
	}
	g := anchor.Rows[0]
	return renderNFLWeek(ctx, c, out, g.SeasonID, g.Week)
}

func renderNFLWeek(ctx context.Context, c *statapi.Client, out io.Writer, season int64, week int) error {
	fmt.Fprintf(out, "  Week %d\n", week)
	page, err := c.NFL.Games.List(ctx, &statapi.NFLGamesListParams{
		SeasonID: &season,
		Week:     statapi.Int(week),
		Limit:    statapi.Int(40),
	})
	if err != nil {
		return err
	}
	if len(page.Rows) == 0 {
		fmt.Fprintln(out, "  No games.")
		return nil
	}
	rows := make([]scoreRow, 0, len(page.Rows))
	for _, g := range page.Rows {
		rows = append(rows, scoreRow{
			away:      g.AwayTeam,
			home:      g.HomeTeam,
			awayScore: g.AwayTeamScore,
			homeScore: g.HomeTeamScore,
			status:    g.Status,
			started:   gameStarted(g.Status),
		})
	}
	renderScoreboard(out, rows)
	return nil
}

// nflSeasonID returns the season of the newest NFL game — the current season
// during the year, the most recent one in the offseason.
func nflSeasonID(ctx context.Context, c *statapi.Client) (int64, error) {
	page, err := c.NFL.Games.List(ctx, &statapi.NFLGamesListParams{Limit: statapi.Int(1)})
	if err != nil {
		return 0, err
	}
	if len(page.Rows) == 0 {
		return 0, fmt.Errorf("no NFL games available")
	}
	return page.Rows[0].SeasonID, nil
}

// mlbTeamIndex fetches the MLB teams once so scoreboards and box scores can turn
// team ids into abbreviations (MLB game rows carry ids, not names).
func mlbTeamIndex(ctx context.Context, c *statapi.Client) (map[int64]statapi.MLBTeam, error) {
	page, err := c.MLB.Teams.List(ctx, &statapi.MLBTeamsListParams{Limit: statapi.Int(60)})
	if err != nil {
		return nil, err
	}
	idx := make(map[int64]statapi.MLBTeam, len(page.Rows))
	for _, t := range page.Rows {
		idx[t.ID] = t
	}
	return idx, nil
}

func mlbAbbr(idx map[int64]statapi.MLBTeam, id int64) string {
	if t, ok := idx[id]; ok && t.Abbreviation != "" {
		return t.Abbreviation
	}
	return fmt.Sprintf("#%d", id)
}
