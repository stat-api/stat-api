package main

import (
	"context"
	"fmt"
	"io"
	"sort"

	statapi "github.com/stat-api/stat-api/go"
)

// runBox renders a game's box score. The game id alone does not carry its
// league, so the caller selects it (defaulting to mlb, the in-season sport).
func runBox(ctx context.Context, c *statapi.Client, out io.Writer, league string, gameID int64) error {
	switch league {
	case "mlb":
		return boxMLB(ctx, c, out, gameID)
	case "nba":
		return boxNBA(ctx, c, out, gameID)
	default:
		return fmt.Errorf("box: unsupported league %q (use mlb or nba)", league)
	}
}

func boxNBA(ctx context.Context, c *statapi.Client, out io.Writer, gameID int64) error {
	game, err := c.NBA.Games.Get(ctx, gameID)
	if err != nil {
		return err
	}
	names, err := nbaPlayerNames(ctx, c, game.AwayTeamID, game.HomeTeamID)
	if err != nil {
		return err
	}
	page, err := c.NBA.GamePlayerStats.List(ctx, &statapi.NBAGamePlayerStatsListParams{
		GameID: &gameID,
		Limit:  statapi.Int(60),
	})
	if err != nil {
		return err
	}

	fmt.Fprintf(out, "NBA Box · %s @ %s · %d-%d (%s)\n",
		game.AwayTeam, game.HomeTeam, game.AwayTeamScore, game.HomeTeamScore, game.Status)
	renderNBATeamBox(out, game.AwayTeam, game.AwayTeamID, page.Rows, names)
	renderNBATeamBox(out, game.HomeTeam, game.HomeTeamID, page.Rows, names)
	return nil
}

func renderNBATeamBox(out io.Writer, label string, teamID int64, all []statapi.NBAGamePlayerStat, names map[int64]string) {
	rows := make([]statapi.NBAGamePlayerStat, 0)
	for _, r := range all {
		if r.TeamID == teamID && r.Minutes > 0 {
			rows = append(rows, r)
		}
	}
	sort.Slice(rows, func(i, j int) bool {
		if rows[i].Minutes != rows[j].Minutes {
			return rows[i].Minutes > rows[j].Minutes
		}
		if rows[i].Pts != rows[j].Pts {
			return rows[i].Pts > rows[j].Pts
		}
		return rows[i].PlayerID < rows[j].PlayerID
	})

	fmt.Fprintf(out, "\n%s\n", label)
	tw := newTable(out)
	fmt.Fprintln(tw, "PLAYER\tMIN\tPTS\tREB\tAST\tSTL\tBLK\tTO\tFG\t3P\tFT\t+/-")
	for _, r := range rows {
		fmt.Fprintf(tw, "%s\t%d\t%d\t%d\t%d\t%d\t%d\t%d\t%d-%d\t%d-%d\t%d-%d\t%+d\n",
			playerName(names, r.PlayerID), roundInt(r.Minutes), r.Pts, r.Rebounds, r.Assists,
			r.Steals, r.Blocks, r.Turnovers,
			r.FieldGoalsMade, r.FieldGoalsAttempted,
			r.ThreePointersMade, r.ThreePointersAttempted,
			r.FreeThrowsMade, r.FreeThrowsAttempted, r.PlusMinus)
	}
	tw.Flush()
}

func boxMLB(ctx context.Context, c *statapi.Client, out io.Writer, gameID int64) error {
	game, err := c.MLB.Games.Get(ctx, gameID)
	if err != nil {
		return err
	}
	teams, err := mlbTeamIndex(ctx, c)
	if err != nil {
		return err
	}
	names, err := mlbPlayerNames(ctx, c, game.AwayTeamID, game.HomeTeamID)
	if err != nil {
		return err
	}
	batters, err := c.MLB.GamePlayerBatterStats.List(ctx, &statapi.MLBGamePlayerBatterStatsListParams{
		GameID: &gameID,
		Limit:  statapi.Int(100),
	})
	if err != nil {
		return err
	}
	pitchers, err := c.MLB.GamePlayerPitchingStats.List(ctx, &statapi.MLBGamePlayerPitchingStatsListParams{
		GameID: &gameID,
		Limit:  statapi.Int(100),
	})
	if err != nil {
		return err
	}

	fmt.Fprintf(out, "MLB Box · %s @ %s · %d-%d (%s)\n",
		mlbAbbr(teams, game.AwayTeamID), mlbAbbr(teams, game.HomeTeamID),
		game.AwayTeamScore, game.HomeTeamScore, game.Status)

	for _, teamID := range []int64{game.AwayTeamID, game.HomeTeamID} {
		label := mlbAbbr(teams, teamID)
		renderMLBBatters(out, label, teamID, batters.Rows, names)
		renderMLBPitchers(out, label, teamID, pitchers.Rows, names)
	}
	return nil
}

func renderMLBBatters(out io.Writer, label string, teamID int64, all []statapi.MLBGamePlayerBatterStat, names map[int64]string) {
	rows := make([]statapi.MLBGamePlayerBatterStat, 0)
	for _, r := range all {
		if r.TeamID == teamID && r.PlateAppearances > 0 {
			rows = append(rows, r)
		}
	}
	sort.Slice(rows, func(i, j int) bool {
		if rows[i].PlateAppearances != rows[j].PlateAppearances {
			return rows[i].PlateAppearances > rows[j].PlateAppearances
		}
		return rows[i].PlayerID < rows[j].PlayerID
	})

	fmt.Fprintf(out, "\n%s Batting\n", label)
	tw := newTable(out)
	fmt.Fprintln(tw, "BATTER\tAB\tR\tH\tRBI\tBB\tK\tAVG")
	for _, r := range rows {
		fmt.Fprintf(tw, "%s\t%d\t%d\t%d\t%d\t%d\t%d\t%s\n",
			playerName(names, r.PlayerID), r.AtBats, r.Runs, r.Hits,
			r.RunsBattedIn, r.Walks, r.Strikeouts, avg3(r.BattingAverage))
	}
	tw.Flush()
}

func renderMLBPitchers(out io.Writer, label string, teamID int64, all []statapi.MLBGamePlayerPitchingStat, names map[int64]string) {
	rows := make([]statapi.MLBGamePlayerPitchingStat, 0)
	for _, r := range all {
		if r.TeamID == teamID && (r.Outs > 0 || r.GamesPitched > 0) {
			rows = append(rows, r)
		}
	}
	sort.Slice(rows, func(i, j int) bool {
		if rows[i].Outs != rows[j].Outs {
			return rows[i].Outs > rows[j].Outs
		}
		return rows[i].PlayerID < rows[j].PlayerID
	})

	fmt.Fprintf(out, "\n%s Pitching\n", label)
	tw := newTable(out)
	fmt.Fprintln(tw, "PITCHER\tIP\tH\tR\tER\tBB\tK\tERA")
	for _, r := range rows {
		fmt.Fprintf(tw, "%s\t%.1f\t%d\t%d\t%d\t%d\t%d\t%.2f\n",
			playerName(names, r.PlayerID), r.InningsPitched, r.HitsAllowed,
			r.RunsAllowed, r.EarnedRuns, r.WalksAllowed, r.StrikeoutsPitched, r.EarnedRunAverage)
	}
	tw.Flush()
}

// nbaPlayerNames builds a player-id → full-name map from the given teams'
// current rosters. Stat rows carry only ids; a player not on the current roster
// (traded, historical) falls back to "#id" at render time.
func nbaPlayerNames(ctx context.Context, c *statapi.Client, teamIDs ...int64) (map[int64]string, error) {
	names := make(map[int64]string)
	for _, teamID := range teamIDs {
		id := teamID
		page, err := c.NBA.Players.List(ctx, &statapi.NBAPlayersListParams{TeamID: &id, Limit: statapi.Int(100)})
		if err != nil {
			return nil, err
		}
		for _, p := range page.Rows {
			names[p.ID] = p.FullName
		}
	}
	return names, nil
}

func mlbPlayerNames(ctx context.Context, c *statapi.Client, teamIDs ...int64) (map[int64]string, error) {
	names := make(map[int64]string)
	for _, teamID := range teamIDs {
		id := teamID
		page, err := c.MLB.Players.List(ctx, &statapi.MLBPlayersListParams{TeamID: &id, Limit: statapi.Int(100)})
		if err != nil {
			return nil, err
		}
		for _, p := range page.Rows {
			names[p.ID] = p.FullName
		}
	}
	return names, nil
}

func playerName(names map[int64]string, id int64) string {
	if n, ok := names[id]; ok && n != "" {
		return n
	}
	return fmt.Sprintf("#%d", id)
}
