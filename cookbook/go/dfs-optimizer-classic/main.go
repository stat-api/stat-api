// Build a diversified NFL Classic DFS lineup portfolio
// Generated from schema/api/examples/dfs-optimizer-classic.yml — do not edit.
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

	// Optimize 5 diversified NFL Classic lineups
	optRes, err := client.DFS.Slates.OptimizerLineups(ctx, 1126128, &statapi.OptimizerLineupsRequest{LineupCount: statapi.Int(5), LineupMinSalary: statapi.Int(48500), MinUniquePlayers: statapi.Int(2), MaxPlayerExposurePct: statapi.Float64(60.0)})
	if err != nil {
		log.Fatal(err)
	}

	// Print portfolio metrics and ranked lineups
	fmt.Printf("Slate %d (%s/%s) | lineups=%d unique=%d mean_pts=%.2f\n", optRes.Slate.ID, optRes.Slate.League, optRes.Slate.Operator, optRes.Portfolio.LineupCount, optRes.Portfolio.UniquePlayers, optRes.Portfolio.MeanProjectedPoints)
	limit := 5
	if len(optRes.Lineups) < limit {
		limit = len(optRes.Lineups)
	}
	for _, l := range optRes.Lineups[:limit] {
		fmt.Printf("Lineup #%d | salary=%d pts=%.2f stack=%s\n", l.Rank, l.Salary, l.ProjectedPoints, l.Stack.Summary)
	}

}
