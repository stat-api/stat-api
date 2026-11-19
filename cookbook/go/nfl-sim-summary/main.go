// Run your first NFL game simulation
// Generated from schema/api/examples/sim-summary.yml — do not edit.
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

	// Find the free game
	ctx := context.Background()
	weeks, err := client.NFL.Weeks(ctx, &statapi.NFLWeeksParams{Season: statapi.Int(2026)})
	if err != nil {
		log.Fatal(err)
	}
	gameID := weeks.FreeGameID
	fmt.Printf("Season %d | free game %d | %d games\n", weeks.Season, gameID, len(weeks.Games))

	// Run the game 5,000 times
	run, err := client.Sim.NFL.Run(ctx, &statapi.NFLSimPublicRunRequest{
		GameID: gameID,
		NDraws: statapi.Int(5000),
		Seed:   statapi.Int64(42),
		Include: &statapi.NFLSimInclude{
			Summary:       statapi.Bool(true),
			Distributions: statapi.Bool(false),
			Scorebox:      statapi.Bool(false),
			Pbp:           statapi.Bool(false),
		},
	})
	if err != nil {
		log.Fatal(err)
	}
	summary := run.Output.Summary
	if summary == nil {
		log.Fatal("the run returned no summary")
	}
	fmt.Printf("Simulated total %.2f (SD %.2f) | home win %.1f%% | home cover %.1f%% | over %.1f%%\n",
		summary.SimTotalMean, summary.SimTotalSd, summary.HomeWinProb*100, summary.HomeCoverProb*100, summary.OverProb*100)
	checks := "failed"
	if summary.ConservationOk {
		checks = "passed"
	}
	fmt.Printf("Box-score checks: %s\n", checks)

	// Rank the players
	players := summary.Players
	sort.Slice(players, func(i, j int) bool { return players[i].RecYdMean > players[j].RecYdMean })
	fmt.Println("Top 5 players by mean receiving yards:")
	for _, p := range players[:5] {
		fmt.Printf("  Player %d (%s/%s) | mean %.1f | median %.1f | 90th percentile %.1f\n",
			p.PlayerID, p.Position, p.DepthRole, p.RecYdMean, p.RecYdP50, p.RecYdP90)
	}

}
