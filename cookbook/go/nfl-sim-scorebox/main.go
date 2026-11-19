// Meet six typical games — NFL
// Generated from schema/api/examples/sim-scorebox.yml — do not edit.
package main

import (
	"context"
	"fmt"
	statapi "github.com/stat-api/stat-api/go"
	"log"
	"strconv"
	"strings"
)

func main() {
	client, err := statapi.New()
	if err != nil {
		log.Fatal(err)
	}

	// Ask for the scorebox
	ctx := context.Background()
	weeks, err := client.NFL.Weeks(ctx, &statapi.NFLWeeksParams{Season: statapi.Int(2026)})
	if err != nil {
		log.Fatal(err)
	}
	gameID := weeks.FreeGameID
	run, err := client.Sim.NFL.Run(ctx, &statapi.NFLSimPublicRunRequest{
		GameID: gameID,
		NDraws: statapi.Int(2000),
		Seed:   statapi.Int64(42),
		Include: &statapi.NFLSimInclude{
			Summary:       statapi.Bool(false),
			Distributions: statapi.Bool(false),
			Scorebox:      statapi.Bool(true),
			Pbp:           statapi.Bool(false),
			ScoreboxLimit: statapi.Int(1),
		},
	})
	if err != nil {
		log.Fatal(err)
	}
	scorebox := run.Output.Scorebox
	if scorebox == nil {
		log.Fatal("the run returned no scorebox")
	}

	// Read the six archetypes
	join := func(scores []int) string {
		parts := make([]string, len(scores))
		for i, n := range scores {
			parts[i] = strconv.Itoa(n)
		}
		return strings.Join(parts, "-")
	}
	away, home := run.Matchup.AwayAbbr, run.Matchup.HomeAbbr
	fmt.Printf("Six typical games of 2000 draws | scores are Q1-Q2-Q3-Q4-OT-Total | %s at %s\n", away, home)
	for _, game := range scorebox.Archetypes {
		name := "draw"
		if game.Archetype != nil {
			name = *game.Archetype
		}
		fmt.Printf("  %s | draw %d | %s %s | %s %s | home margin %+d\n",
			name, game.DrawIdx, away, join(game.AwayLinescore[:]), home, join(game.HomeLinescore[:]), game.FinalMarginHome)
	}

}
