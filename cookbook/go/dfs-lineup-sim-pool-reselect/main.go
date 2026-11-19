// Re-select SIM portfolios in <50ms via cached pool_id — DFS
// Generated from schema/api/examples/dfs-sim-pool-reselect.yml — do not edit.
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

	// Build baseline SIM candidate pool
	baselinePool, err := client.DFS.Slates.SimLineups(ctx, 1126128, &statapi.SimLineupsRequest{ContestID: statapi.Int64(91632452), LineupCount: statapi.Int(5), SimGameRuns: statapi.Int(200), SimSeed: statapi.Int(42), DiversityWeight: statapi.Float64(0.15), MaxPlayerExposurePct: statapi.Float64(60.0)}, &statapi.SimRequestOptions{Wait: true})
	if err != nil {
		log.Fatal(err)
	}

	// Re-select portfolio from cached pool_id + Pareto frontier
	if baselinePool.Sim.Versions.PoolID == nil || *baselinePool.Sim.Versions.PoolID == "" {
		log.Fatal("missing sim.versions.pool_id")
	}
	reselectedPoolID := *baselinePool.Sim.Versions.PoolID
	reselected, err := client.DFS.Slates.SimSelectPool(ctx, 1126128, &statapi.PortfolioSelectRequest{PoolID: reselectedPoolID, LineupCount: statapi.Int(5), DiversityWeight: statapi.Float64(0.35), MinUniquePlayers: statapi.Int(2), MaxPlayerExposurePct: statapi.Float64(60.0), IncludeDiversityFrontier: statapi.Bool(true)})
	if err != nil {
		log.Fatal(err)
	}

	// Print baseline vs reselected comparison and frontier
	d := reselected.Diagnostics
	bp := baselinePool.Portfolio
	rp := reselected.Portfolio
	fmt.Printf("Pool total_pool=%d active=%d\n", d.TotalPoolLineups, d.ActivePoolLineups)
	fmt.Printf("Baseline lineups=%d ev=%.2f diversity=%.2f\n", bp.LineupCount, bp.Payout.EVDollars, bp.Diversity.CompositeDiversityScore)
	fmt.Printf("Reselected lineups=%d ev=%.2f diversity=%.2f frontier=%d\n", rp.LineupCount, rp.Payout.EVDollars, rp.Diversity.CompositeDiversityScore, len(reselected.Frontier))
	limit := 4
	if len(reselected.Frontier) < limit {
		limit = len(reselected.Frontier)
	}
	for _, fp := range reselected.Frontier[:limit] {
		fmt.Printf("Frontier weight=%.2f | ev=%.2f roi_pct=%.2f diversity=%.2f\n", fp.DiversityWeight, fp.EVDollars, fp.ROIPct, fp.CompositeDiversityScore)
	}

}
