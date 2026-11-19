// See the whole range of outcomes — NFL
// Generated from schema/api/examples/sim-distributions.yml — do not edit.
package main

import (
	"context"
	"fmt"
	statapi "github.com/stat-api/stat-api/go"
	"log"
	"sort"
	"strconv"
	"strings"
)

func main() {
	client, err := statapi.New()
	if err != nil {
		log.Fatal(err)
	}

	// Ask for the distributions
	ctx := context.Background()
	weeks, err := client.NFL.Weeks(ctx, &statapi.NFLWeeksParams{Season: statapi.Int(2026)})
	if err != nil {
		log.Fatal(err)
	}
	gameID := weeks.FreeGameID
	run, err := client.Sim.NFL.Run(ctx, &statapi.NFLSimPublicRunRequest{
		GameID: gameID,
		NDraws: statapi.Int(5000),
		Seed:   statapi.Int64(42),
		Include: &statapi.NFLSimInclude{
			Summary:       statapi.Bool(false),
			Distributions: statapi.Bool(true),
			Scorebox:      statapi.Bool(false),
			Pbp:           statapi.Bool(false),
		},
	})
	if err != nil {
		log.Fatal(err)
	}
	dist := run.Output.Distributions
	if dist == nil {
		log.Fatal("the run returned no distributions")
	}
	total := dist.TotalPoints
	fmt.Printf("Game total | mean %.2f, SD %.2f | 10th %.2f, median %.2f, 90th %.2f\n",
		total.Mean, total.Sd, total.P10, total.P50, total.P90)
	if total.VegasLine != nil && total.POverVegas != nil {
		fmt.Printf("Market total %.1f | simulated chance of the over %.1f%%\n", *total.VegasLine, *total.POverVegas*100)
	}

	// Read the histogram
	fmt.Println("Share of draws by game total, bins with 2% or more:")
	for _, bin := range total.Bins {
		if bin.Prob < 0.02 {
			continue
		}
		fmt.Printf("  [%6.2f, %6.2f) %5.2f%% %s\n",
			bin.XMin, bin.XMax, bin.Prob*100, strings.Repeat("#", int(bin.Prob*100)))
	}

	// Find a draw that you can replay
	sample := "none"
	for _, bin := range total.Bins {
		if bin.XMin <= total.P50 && total.P50 < bin.XMax && bin.SampleDrawIdx != nil {
			sample = strconv.Itoa(*bin.SampleDrawIdx)
		}
	}
	fmt.Printf("Median bin: sample draw %s\n", sample)

	// Read the range of a player
	var receivers []statapi.NFLPlayerBellCurves
	for _, p := range dist.Players {
		if p.RecYd != nil {
			receivers = append(receivers, p)
		}
	}
	sort.Slice(receivers, func(i, j int) bool { return receivers[i].RecYd.Mean > receivers[j].RecYd.Mean })
	star := receivers[0]
	rec := star.RecYd
	fmt.Printf("Top receiver %d (%s) rec yds | 10th %.2f, median %.2f, 90th %.2f | skew %.2f\n",
		star.PlayerID, star.Position, rec.P10, rec.P50, rec.P90, rec.Skew)

}
