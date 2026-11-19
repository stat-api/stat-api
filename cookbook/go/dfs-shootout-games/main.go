// Find the highest-scoring games on a slate — DFS
// Generated from schema/api/examples/dfs-shootout-games.yml — do not edit.
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

	// Read every game in the slate's pool
	gamesPage, err := client.DFS.GameStats.List(ctx, &statapi.DFSGameStatsListParams{SlateID: statapi.Int64(int64(129036)), Limit: statapi.Int(50)})
	if err != nil {
		log.Fatal(err)
	}
	games := gamesPage.Rows

	// Keep the games that finished
	played := make([]statapi.DFSGameStat, 0, len(games))
	for _, r := range games {
		if r.TotalFpts != nil {
			played = append(played, r)
		}
	}

	// Rank by total fantasy points
	best := append([]statapi.DFSGameStat(nil), played...)
	bestKey := func(r statapi.DFSGameStat) float64 {
		if r.TotalFpts != nil {
			return *r.TotalFpts
		}
		return 0
	}
	sort.Slice(best, func(i, j int) bool {
		return bestKey(best[j]) < bestKey(best[i])
	})

	// Take the five biggest games
	top := best
	if len(top) > 5 {
		top = top[:5]
	}

	// Print the game board
	fmt.Println("Highest-scoring games on the slate")
	fmt.Println(strings.Join([]string{"game_id", "total_fpts", "home_fpts", "away_fpts", "home_pts", "away_pts", "player_count"}, "\t"))
	for _, row := range top {
		fmt.Println(strings.Join([]string{str(row.GameID), str(row.TotalFpts), str(row.HomeFpts), str(row.AwayFpts), str(row.HomePts), str(row.AwayPts), str(row.PlayerCount)}, "\t"))
	}

}
