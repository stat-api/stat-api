// Rank NHL season leaders
// Generated from schema/api/examples/season-leaders.yml — do not edit.
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

	// Resolve the current season
	seasonPage, err := client.NHL.Seasons.List(ctx, &statapi.NHLSeasonsListParams{Limit: statapi.Int(200)})
	if err != nil {
		log.Fatal(err)
	}
	var season int64
	seasonBest := -1
	for _, s := range seasonPage.Rows {
		if s.StartYear > seasonBest {
			seasonBest = s.StartYear
			season = s.ID
		}
	}

	// Page through the whole season
	var rows []statapi.NHLSeasonPlayerStat
	for row, err := range client.NHL.SeasonPlayerStats.All(ctx, &statapi.NHLSeasonPlayerStatsListParams{SeasonID: statapi.Int64(int64(season))}) {
		if err != nil {
			log.Fatal(err)
		}
		rows = append(rows, row)
	}

	// Rank by goals
	ranked := append([]statapi.NHLSeasonPlayerStat(nil), rows...)
	rankedKey := func(r statapi.NHLSeasonPlayerStat) int {
		return r.Goals
	}
	sort.Slice(ranked, func(i, j int) bool {
		return rankedKey(ranked[j]) < rankedKey(ranked[i])
	})

	// Take the top ten
	leaders := ranked
	if len(leaders) > 10 {
		leaders = leaders[:10]
	}

	// Print the leaderboard
	fmt.Println("NHL goals leaders")
	fmt.Println(strings.Join([]string{"player_id", "goals"}, "\t"))
	for _, row := range leaders {
		fmt.Println(strings.Join([]string{str(row.PlayerID), str(row.Goals)}, "\t"))
	}

}
