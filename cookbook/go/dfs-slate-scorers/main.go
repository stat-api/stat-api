// See who actually scored on a slate — DFS
// Generated from schema/api/examples/dfs-slate-scorers.yml — do not edit.
package main

import (
	"context"
	"fmt"
	statapi "github.com/stat-api/stat-api/go"
	"log"
	"reflect"
	"sort"
	"strings"
)

// str renders any value (dereferencing nil pointers to an empty cell).
func str(v any) string {
	if v == nil {
		return ""
	}
	rv := reflect.ValueOf(v)
	if rv.Kind() == reflect.Ptr {
		if rv.IsNil() {
			return ""
		}
		return fmt.Sprintf("%v", rv.Elem().Interface())
	}
	return fmt.Sprintf("%v", v)
}

func main() {
	client, err := statapi.New()
	if err != nil {
		log.Fatal(err)
	}
	ctx := context.Background()

	// Page the player pool for names
	var pool []statapi.DFSSlatePlayer
	for row, err := range client.DFS.SlatePlayers.All(ctx, &statapi.DFSSlatePlayersListParams{SlateID: statapi.Int64(int64(129036))}) {
		if err != nil {
			log.Fatal(err)
		}
		pool = append(pool, row)
	}

	// Index the pool by player id
	names := map[int64]statapi.DFSSlatePlayer{}
	for _, row := range pool {
		names[row.PlayerID] = row
	}

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

	// Rank by fantasy points
	best := append([]statapi.DFSPlayerStat(nil), scored...)
	bestKey := func(r statapi.DFSPlayerStat) float64 {
		if r.FantasyPts != nil {
			return *r.FantasyPts
		}
		return 0
	}
	sort.Slice(best, func(i, j int) bool {
		return bestKey(best[j]) < bestKey(best[i])
	})

	// Take the top fifteen
	top := best
	if len(top) > 15 {
		top = top[:15]
	}

	// Print the scoring board
	fmt.Println("Top scorers on the slate")
	fmt.Println(strings.Join([]string{"display_name", "position", "salary", "fantasy_pts"}, "\t"))
	for _, row := range top {
		fmt.Println(strings.Join([]string{str(func() any {
		if v, ok := names[row.PlayerID]; ok {
			return v.DisplayName
		}
		return row.PlayerID
	}()), str(row.Position), str(row.Salary), str(row.FantasyPts)}, "\t"))
	}

}
