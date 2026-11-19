// Enforce correlated QB stacks, bring-backs, and player groups — DFS
// Generated from schema/api/examples/dfs-optimizer-stacks-groups.yml — do not edit.
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

	// Optimize 5 stacked lineups with group constraints
	stackedRes, err := client.DFS.Slates.OptimizerLineups(ctx, 1126128, &statapi.OptimizerLineupsRequest{LineupCount: statapi.Int(5), LineupAllowDefenseVsOffense: statapi.Bool(false), LineupStacks: []statapi.StackBlock{statapi.StackBlock{Positions: []string{"QB", "WR"}, BringBackCount: statapi.Int(1)}}, PlayerGroups: []statapi.GroupBlock{statapi.GroupBlock{SlatePlayerIDs: []int64{44186556, 44186570}, MaxPlayers: statapi.Int(1)}}, MaxPlayerExposurePct: statapi.Float64(60.0)})
	if err != nil {
		log.Fatal(err)
	}

	// Print stacked lineup summary
	fmt.Printf("Slate %d (%s/%s) | lineups=%d unique=%d mean_pts=%.2f\n", stackedRes.Slate.ID, stackedRes.Slate.League, stackedRes.Slate.Operator, stackedRes.Portfolio.LineupCount, stackedRes.Portfolio.UniquePlayers, stackedRes.Portfolio.MeanProjectedPoints)
	limit := 5
	if len(stackedRes.Lineups) < limit {
		limit = len(stackedRes.Lineups)
	}
	for _, l := range stackedRes.Lineups[:limit] {
		fmt.Printf("Lineup #%d | salary=%d pts=%.2f stack=%s\n", l.Rank, l.Salary, l.ProjectedPoints, l.Stack.Summary)
	}

}
