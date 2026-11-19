// Generate EV-optimized NBA Classic GPP lineups — DFS
// Generated from schema/api/examples/dfs-sim-nba-build.yml — do not edit.
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
	ctx := context.Background()

	// Run NBA contest SIM lineup build
	nbaSimBuild, err := client.DFS.Slates.SimLineups(ctx, 1243812, &statapi.SimLineupsRequest{ContestID: statapi.Int64(108465399), LineupCount: statapi.Int(5), SimGameRuns: statapi.Int(200), SimSeed: statapi.Int(42), DiversityWeight: statapi.Float64(0.25), MaxPlayerExposurePct: statapi.Float64(60.0)}, &statapi.SimRequestOptions{Wait: true})
	if err != nil {
		log.Fatal(err)
	}

	// Print NBA SIM portfolio and lineups
	p := nbaSimBuild.Portfolio
	fmt.Printf("Slate %d | contest %d | lineups=%d ev=%.2f roi_pct=%.2f diversity=%.2f\n", nbaSimBuild.Slate.ID, nbaSimBuild.Contest.ContestID, p.LineupCount, p.Payout.EVDollars, p.Payout.ROIPct, p.Diversity.CompositeDiversityScore)
	limit := 5
	if len(nbaSimBuild.Lineups) < limit {
		limit = len(nbaSimBuild.Lineups)
	}
	for _, l := range nbaSimBuild.Lineups[:limit] {
		fmt.Printf("SimLineup #%d | salary=%d proj=%.2f ev=%.2f roi_pct=%.2f win_pct=%.2f\n", l.Rank, l.Salary, l.ProjectedPoints, l.Payout.EVDollars, l.Payout.ROIPct, l.Payout.WinPct)
	}

}
