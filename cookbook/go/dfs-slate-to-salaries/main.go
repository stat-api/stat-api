// Rank a DFS slate by salary and value
// Generated from schema/api/examples/dfs-slate-to-salaries.yml — do not edit.
package main

import (
	"context"
	"fmt"
	statapi "github.com/stat-api/stat-api/go"
	"log"
	"sort"
	"strings"
)

func main() {
	client, err := statapi.New()
	if err != nil {
		log.Fatal(err)
	}
	ctx := context.Background()

	// Page the slate's scoring rows
	var stats []statapi.DFSPlayerStat
	for row, err := range client.DFS.PlayerStats.All(ctx, &statapi.DFSPlayerStatsListParams{SlateID: statapi.Int64(int64(129036))}) {
		if err != nil {
			log.Fatal(err)
		}
		stats = append(stats, row)
	}

	// Drop the players who did not play
	scored := make([]statapi.DFSPlayerStat, 0, len(stats))
	for _, r := range stats {
		if r.FantasyPts != nil {
			scored = append(scored, r)
		}
	}

	// Drop minimum-salary players
	playable := make([]statapi.DFSPlayerStat, 0, len(scored))
	for _, r := range scored {
		if r.Salary >= 4000 {
			playable = append(playable, r)
		}
	}

	// Compute points per $1,000 of salary
	type valueRow struct {
	    PlayerID int64
	    Position string
	    Salary   int
	    Points   float64
	    Value    float64
	}
	board := make([]valueRow, 0, len(playable))
	for _, r := range playable {
	    pts := 0.0
	    if r.FantasyPts != nil {
	        pts = *r.FantasyPts
	    }
	    board = append(board, valueRow{r.PlayerID, r.Position, r.Salary, pts, (pts / float64(r.Salary)) * 1000})
	}
	sort.Slice(board, func(i, j int) bool { return board[j].Value < board[i].Value })
	if len(board) > 10 {
	    board = board[:10]
	}
	fmt.Println(strings.Join([]string{"player_id", "position", "salary", "points", "per $1k"}, "\t"))
	for _, r := range board {
	    fmt.Printf("%d\t%s\t%d\t%.2f\t%.2f\n", r.PlayerID, r.Position, r.Salary, r.Points, r.Value)
	}

}
