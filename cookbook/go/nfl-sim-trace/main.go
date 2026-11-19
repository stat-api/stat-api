// Replay one simulated game — NFL
// Generated from schema/api/examples/sim-trace.yml — do not edit.
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

	// Pick a draw from a run
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
	var shootout *statapi.NFLSimGameScoreboxDraw
	if run.Output.Scorebox != nil {
		for i := range run.Output.Scorebox.Archetypes {
			game := &run.Output.Scorebox.Archetypes[i]
			if game.Archetype != nil && *game.Archetype == "shootout_p95" {
				shootout = game
			}
		}
	}
	if shootout == nil {
		log.Fatal("the run has no shootout game")
	}
	fmt.Printf("Replay draw %d, the 95th percentile shootout of 2000 draws\n", shootout.DrawIdx)

	// Replay the draw with run
	replay, err := client.Sim.NFL.Run(ctx, &statapi.NFLSimPublicRunRequest{
		GameID: gameID,
		NDraws: statapi.Int(2000),
		Seed:   statapi.Int64(42),
		Include: &statapi.NFLSimInclude{
			Summary:       statapi.Bool(false),
			Distributions: statapi.Bool(false),
			Scorebox:      statapi.Bool(false),
			Pbp:           statapi.Bool(true),
			PbpDraws:      []int{shootout.DrawIdx},
		},
	})
	if err != nil {
		log.Fatal(err)
	}
	if len(replay.Output.Pbp) == 0 {
		log.Fatal("the run returned no play-by-play trace")
	}
	traced := replay.Output.Pbp[0]
	away, home := run.Matchup.AwayAbbr, run.Matchup.HomeAbbr
	fmt.Printf("%s %d - %s %d | %d drives | %d plays\n",
		away, traced.Scorebox.AwayLinescore[5], home, traced.Scorebox.HomeLinescore[5],
		len(traced.Drives), len(traced.Plays))

	// Read the drives and the plays
	fmt.Println("First 5 drives:")
	for _, d := range traced.Drives[:5] {
		team := away
		if d.PosteamIsHome {
			team = home
		}
		fmt.Printf("  %d. %s | Q%d %s | %d plays, %d yds | %s | %s %d - %s %d\n",
			d.DriveNumber, team, d.StartQuarter, d.StartClock, d.Plays, d.NetYards, d.Result,
			away, d.AwayScoreAfter, home, d.HomeScoreAfter)
	}
	fmt.Println("First 5 plays:")
	for _, p := range traced.Plays[:5] {
		fmt.Printf("  %s | %s | %s | %s\n", p.ClockDisplay, p.DownDistanceDisplay, p.YardlineDisplay, p.Description)
	}

}
