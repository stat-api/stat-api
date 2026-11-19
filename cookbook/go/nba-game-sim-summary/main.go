// Run an NBA possession simulation summary
// Generated from schema/api/examples/nba-sim-summary.yml — do not edit.
package main

import (
	"context"
	"fmt"
	statapi "github.com/stat-api/stat-api/go"
	"log"
	"sort"
)

func main() {
	client, err := statapi.New()
	if err != nil {
		log.Fatal(err)
	}
	ctx := context.Background()

	// Discover the free NBA showcase game
	weeks, err := client.NBA.Weeks(ctx, &statapi.NBAWeeksParams{Season: statapi.Int(2026)})
	if err != nil {
		log.Fatal(err)
	}

	// Run the NBA possession simulator
	simRes, err := client.Sim.NBA.Run(ctx, &statapi.NBASimPublicRunRequest{GameID: weeks.FreeGameID, NDraws: statapi.Int(200), Seed: statapi.Int64(42), Include: &statapi.NBASimInclude{Summary: statapi.Bool(true), Distributions: statapi.Bool(false), Scorebox: statapi.Bool(false), Pbp: statapi.Bool(false), Scenarios: statapi.Bool(false), Simulations: statapi.Bool(false)}})
	if err != nil {
		log.Fatal(err)
	}

	// Print calibrated summary and top NBA players
	summary := simRes.Output.Summary
	if summary == nil {
		log.Fatal("missing output.summary")
	}
	fmt.Printf("Game %s | season %d | draws %d\n", summary.GameID, summary.Season, summary.NDraws)
	fmt.Printf("Total mean=%.2f sd=%.2f | home_win_pct=%.2f%% away_win_pct=%.2f%%\n", summary.SimTotalMean, summary.SimTotalSd, summary.HomeWinProb*100, summary.AwayWinProb*100)
	fmt.Printf("Teams away=%s:%.2f home=%s:%.2f\n", summary.AwayTeam.TeamCode, summary.AwayTeam.PointsMean, summary.HomeTeam.TeamCode, summary.HomeTeam.PointsMean)
	sortedPlayers := append([]statapi.NBASimPlayerSummary(nil), summary.Players...)
	sort.Slice(sortedPlayers, func(i, j int) bool {
		if sortedPlayers[i].DkPtsMean != sortedPlayers[j].DkPtsMean {
			return sortedPlayers[i].DkPtsMean > sortedPlayers[j].DkPtsMean
		}
		return sortedPlayers[i].PlayerID < sortedPlayers[j].PlayerID
	})
	limit := 5
	if len(sortedPlayers) < limit {
		limit = len(sortedPlayers)
	}
	for _, p := range sortedPlayers[:limit] {
		fmt.Printf("Player %s (%s) | pts=%.2f reb=%.2f ast=%.2f dk=%.2f\n", p.Name, p.TeamCode, p.PtsMean, p.RebMean, p.AstMean, p.DkPtsMean)
	}

}
