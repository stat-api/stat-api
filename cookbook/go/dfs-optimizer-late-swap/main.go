// Late-swap unlocked slots on active contest entries — DFS
// Generated from schema/api/examples/dfs-optimizer-late-swap.yml — do not edit.
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

	// Run fast late swap with locked game 43545
	swapRes, err := client.DFS.Slates.OptimizerLineups(ctx, 1126128, &statapi.OptimizerLineupsRequest{Entries: []statapi.CustomLineupInput{statapi.CustomLineupInput{EntryID: statapi.String("entry-1"), SlatePlayerIDs: []int64{44186595, 44186556, 44186645, 44186641, 44186568, 44186860, 44186666, 44186564, 44187045}}}, AsOf: statapi.String("2025-01-01T00:00:00Z"), LockedGameIDs: []int64{43545}, MinProjectionGain: statapi.Float64(0.0)})
	if err != nil {
		log.Fatal(err)
	}

	// Print per-entry swap decision and gain
	swappedCnt, heldCnt := 0, 0
	if swapRes.Portfolio.SwappedCount != nil { swappedCnt = *swapRes.Portfolio.SwappedCount }
	if swapRes.Portfolio.HeldCount != nil { heldCnt = *swapRes.Portfolio.HeldCount }
	fmt.Printf("Slate %d | entries=%d swapped=%d held=%d\n", swapRes.Slate.ID, len(swapRes.Lineups), swappedCnt, heldCnt)
	nz := func(v float64) float64 { if math.Abs(v) < 0.005 { return 0 }; return v }
	for _, l := range swapRes.Lineups {
		entryID := "entry"
		if l.EntryID != nil && *l.EntryID != "" { entryID = *l.EntryID }
		action := ""
		if l.Action != nil { action = *l.Action }
		holdPts := l.ProjectedPoints
		if l.HoldProjectedPoints != nil { holdPts = *l.HoldProjectedPoints }
		gainPts := 0.0
		if l.ProjectionGain != nil { gainPts = nz(*l.ProjectionGain) }
		fmt.Printf("Entry %s | action=%s salary=%d hold=%.2f proj=%.2f gain=%.2f locked_slots=%d\n", entryID, action, l.Salary, holdPts, l.ProjectedPoints, gainPts, len(l.LockedSlots))
	}

}
