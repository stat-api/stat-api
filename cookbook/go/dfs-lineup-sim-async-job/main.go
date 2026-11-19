// Queue an async 202 SIM job and poll until completion — DFS
// Generated from schema/api/examples/dfs-sim-async-job.yml — do not edit.
package main

import (
	"context"
	"fmt"
	statapi "github.com/stat-api/stat-api/go"
	"log"
	"time"
)

func main() {
	client, err := statapi.New()
	if err != nil {
		log.Fatal(err)
	}
	ctx := context.Background()

	// Submit async SIM job (Prefer: respond-async)
	job, err := client.DFS.Slates.SimLineupsStart(ctx, 1126128, &statapi.SimLineupsRequest{ContestID: statapi.Int64(91632452), LineupCount: statapi.Int(3), SimGameRuns: statapi.Int(200), SimSeed: statapi.Int(42)})
	if err != nil {
		log.Fatal(err)
	}

	// Poll job handle until ready
	var completed *statapi.SimLineupsResponse
	for i := 0; i < 60; i++ {
		completedPoll, pollErr := client.DFS.Slates.SimJob(ctx, 1126128, job.JobID)
		if pollErr != nil {
			log.Fatal(pollErr)
		}
		if completedPoll.Ready && completedPoll.Result != nil {
			completed = completedPoll.Result
			break
		}
		time.Sleep(time.Second)
	}
	if completed == nil {
		log.Fatal("SIM job did not complete in time")
	}

	// Print initial job status and completed SIM lineups
	fmt.Printf("Job %s | initial_status=%s\n", job.JobID, job.Status)
	p := completed.Portfolio
	fmt.Printf("Completed slate=%d lineups=%d ev=%.2f roi_pct=%.2f\n", completed.Slate.ID, p.LineupCount, p.Payout.EVDollars, p.Payout.ROIPct)
	limit := 3
	if len(completed.Lineups) < limit {
		limit = len(completed.Lineups)
	}
	for _, l := range completed.Lineups[:limit] {
		fmt.Printf("AsyncLineup #%d | salary=%d proj=%.2f ev=%.2f roi_pct=%.2f\n", l.Rank, l.Salary, l.ProjectedPoints, l.Payout.EVDollars, l.Payout.ROIPct)
	}

}
