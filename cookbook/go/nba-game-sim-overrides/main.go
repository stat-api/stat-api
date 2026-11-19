// Compare NBA Common Random Numbers spread overrides
// Generated from schema/api/examples/nba-sim-overrides.yml — do not edit.
package main

import (
	"context"
	"fmt"
	statapi "github.com/stat-api/stat-api/go"
	"log"
	"math"
)

func main() {
	client, err := statapi.New()
	if err != nil {
		log.Fatal(err)
	}
	ctx := context.Background()

	// Run the market baseline simulation (seed 42)
	baseline, err := client.Sim.NBA.Run(ctx, &statapi.NBASimPublicRunRequest{GameID: 6029936, NDraws: statapi.Int(200), Seed: statapi.Int64(42), Include: &statapi.NBASimInclude{Summary: statapi.Bool(true), Distributions: statapi.Bool(false), Scorebox: statapi.Bool(false), Pbp: statapi.Bool(false), Scenarios: statapi.Bool(false), Simulations: statapi.Bool(false)}})
	if err != nil {
		log.Fatal(err)
	}

	// Re-simulate with an alternate home spread (-8.5, seed 42)
	shifted, err := client.Sim.NBA.Run(ctx, &statapi.NBASimPublicRunRequest{GameID: 6029936, NDraws: statapi.Int(200), Seed: statapi.Int64(42), Include: &statapi.NBASimInclude{Summary: statapi.Bool(true), Distributions: statapi.Bool(false), Scorebox: statapi.Bool(false), Pbp: statapi.Bool(false), Scenarios: statapi.Bool(false), Simulations: statapi.Bool(false)}, HomeSpread: statapi.Float64(-8.5)})
	if err != nil {
		log.Fatal(err)
	}

	// Print the CRN sensitivity delta
	baseS := baseline.Output.Summary
	overS := shifted.Output.Summary
	if baseS == nil || overS == nil {
		log.Fatal("missing summary")
	}
	nz := func(v float64) float64 { if math.Abs(v) < 0.005 { return 0 }; return v }
	fmt.Printf("Baseline total=%.2f home_win_pct=%.2f%% spread_home=%.2f\n", baseS.SimTotalMean, baseS.HomeWinProb*100, baseS.SimSpreadHomeMean)
	fmt.Printf("Override total=%.2f home_win_pct=%.2f%% spread_home=%.2f\n", overS.SimTotalMean, overS.HomeWinProb*100, overS.SimSpreadHomeMean)
	fmt.Printf("Delta total=%.2f home_win_pp=%.2f\n", nz(overS.SimTotalMean-baseS.SimTotalMean), nz((overS.HomeWinProb-baseS.HomeWinProb)*100))

}
