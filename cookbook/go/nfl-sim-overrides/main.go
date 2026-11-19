// Ask a what-if question — NFL
// Generated from schema/api/examples/sim-overrides.yml — do not edit.
package main

import (
	"context"
	"fmt"
	statapi "github.com/stat-api/stat-api/go"
	"log"
)

func main() {
	client, err := statapi.New()
	if err != nil {
		log.Fatal(err)
	}

	// Run the baseline
	ctx := context.Background()
	weeks, err := client.NFL.Weeks(ctx, &statapi.NFLWeeksParams{Season: statapi.Int(2026)})
	if err != nil {
		log.Fatal(err)
	}
	gameID := weeks.FreeGameID
	include := &statapi.NFLSimInclude{
		Summary:       statapi.Bool(true),
		Distributions: statapi.Bool(false),
		Scorebox:      statapi.Bool(false),
		Pbp:           statapi.Bool(false),
	}
	baseline, err := client.Sim.NFL.Run(ctx, &statapi.NFLSimPublicRunRequest{
		GameID: gameID, NDraws: statapi.Int(5000), Seed: statapi.Int64(42), Include: include,
	})
	if err != nil {
		log.Fatal(err)
	}

	// Run the what-if with the same seed
	scenario, err := client.Sim.NFL.Run(ctx, &statapi.NFLSimPublicRunRequest{
		GameID:     gameID,
		NDraws:     statapi.Int(5000),
		Seed:       statapi.Int64(42),
		Include:    include,
		HomeSpread: statapi.Float64(-6.5),
	})
	if err != nil {
		log.Fatal(err)
	}

	// Read the exact change
	before, after := baseline.Output.Summary, scenario.Output.Summary
	if before == nil || after == nil {
		log.Fatal("a run returned no summary")
	}
	home, away := baseline.Matchup.HomeAbbr, baseline.Matchup.AwayAbbr
	fmt.Printf("What if %s were a 6.5 point favorite? Seed 42 on both runs.\n", home)
	fmt.Printf("Home win chance | %.1f%% -> %.1f%% (%+.1f points)\n",
		before.HomeWinProb*100, after.HomeWinProb*100, (after.HomeWinProb-before.HomeWinProb)*100)
	fmt.Printf("%s points | %.2f -> %.2f (%+.2f)\n",
		home, before.HomeTeam.PointsMean, after.HomeTeam.PointsMean, after.HomeTeam.PointsMean-before.HomeTeam.PointsMean)
	fmt.Printf("%s points | %.2f -> %.2f (%+.2f)\n",
		away, before.AwayTeam.PointsMean, after.AwayTeam.PointsMean, after.AwayTeam.PointsMean-before.AwayTeam.PointsMean)

}
