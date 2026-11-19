// Extract NBA empirical bell curves and quantiles
// Generated from schema/api/examples/nba-sim-distributions.yml — do not edit.
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

	// Run NBA SIM with empirical distributions enabled
	distRes, err := client.Sim.NBA.Run(ctx, &statapi.NBASimPublicRunRequest{GameID: 6029936, NDraws: statapi.Int(200), Seed: statapi.Int64(42), Include: &statapi.NBASimInclude{Summary: statapi.Bool(true), Distributions: statapi.Bool(true), Scorebox: statapi.Bool(false), Pbp: statapi.Bool(false), Scenarios: statapi.Bool(false), Simulations: statapi.Bool(false)}})
	if err != nil {
		log.Fatal(err)
	}

	// Print game and player quantile distributions
	dist := distRes.Output.Distributions
	sumD := distRes.Output.Summary
	if dist == nil || sumD == nil {
		log.Fatal("missing distributions or summary")
	}
	tp := dist.TotalPoints
	sm := dist.SpreadMarginHome
	fmt.Printf("Game %s | total mean=%.2f p10=%.2f p50=%.2f p90=%.2f\n", sumD.GameID, tp.Mean, tp.P10, tp.P50, tp.P90)
	fmt.Printf("SpreadHome mean=%.2f p10=%.2f p50=%.2f p90=%.2f\n", sm.Mean, sm.P10, sm.P50, sm.P90)
	fmt.Printf("Teams %s=%.2f %s=%.2f\n", sumD.AwayTeam.TeamCode, dist.AwayPoints.Mean, sumD.HomeTeam.TeamCode, dist.HomePoints.Mean)
	sortedCurves := append([]statapi.NBAPlayerBellCurves(nil), dist.Players...)
	sort.Slice(sortedCurves, func(i, j int) bool {
		if sortedCurves[i].DkPts.Mean != sortedCurves[j].DkPts.Mean {
			return sortedCurves[i].DkPts.Mean > sortedCurves[j].DkPts.Mean
		}
		return sortedCurves[i].PlayerID < sortedCurves[j].PlayerID
	})
	limit := 5
	if len(sortedCurves) < limit {
		limit = len(sortedCurves)
	}
	for _, p := range sortedCurves[:limit] {
		fmt.Printf("Dist %s (%s) | dk_p50=%.2f dk_p90=%.2f pts_p50=%.2f\n", p.Name, p.TeamCode, p.DkPts.P50, p.DkPts.P90, p.Points.P50)
	}

}
