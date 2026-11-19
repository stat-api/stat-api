// See where a player's fantasy points came from — DFS
// Generated from schema/api/examples/dfs-scoring-breakdown.yml — do not edit.
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

	// Read one player's slate history
	rowsPage, err := client.DFS.PlayerStats.List(ctx, &statapi.DFSPlayerStatsListParams{PlayerID: statapi.Int64(int64(301)), Limit: statapi.Int(200)})
	if err != nil {
		log.Fatal(err)
	}
	rows := rowsPage.Rows

	// Keep the games that were played
	played := make([]statapi.DFSPlayerStat, 0, len(rows))
	for _, r := range rows {
		if r.FantasyPts != nil {
			played = append(played, r)
		}
	}

	// Best game first
	best := append([]statapi.DFSPlayerStat(nil), played...)
	bestKey := func(r statapi.DFSPlayerStat) float64 {
		if r.FantasyPts != nil {
			return *r.FantasyPts
		}
		return 0
	}
	sort.Slice(best, func(i, j int) bool {
		return bestKey(best[j]) < bestKey(best[i])
	})

	// Take the single best game
	top := best
	if len(top) > 1 {
		top = top[:1]
	}

	// Split the score into its categories
	fmt.Println("Where the points came from")
	fmt.Println(strings.Join([]string{"fantasy_pts", "passing_yds_fpts", "passing_td_fpts", "rushing_yds_fpts", "rushing_td_fpts", "receiving_yds_fpts", "reception_fpts"}, "\t"))
	for _, row := range top {
		fmt.Println(strings.Join([]string{str(row.FantasyPts), str(row.PassingYdsFpts), str(row.PassingTdFpts), str(row.RushingYdsFpts), str(row.RushingTdFpts), str(row.ReceivingYdsFpts), str(row.ReceptionFpts)}, "\t"))
	}

	// Compare against the raw box score
	fmt.Println("The raw stats behind those points")
	fmt.Println(strings.Join([]string{"passing_yds", "passing_tds", "rushing_yds", "rushing_tds", "receptions", "receiving_yds", "receiving_tds"}, "\t"))
	for _, row := range top {
		fmt.Println(strings.Join([]string{str(row.PassingYds), str(row.PassingTds), str(row.RushingYds), str(row.RushingTds), str(row.Receptions), str(row.ReceivingYds), str(row.ReceivingTds)}, "\t"))
	}

}
