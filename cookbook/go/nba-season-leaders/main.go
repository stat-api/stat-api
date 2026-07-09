// Rank NBA season leaders
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
	seasonPage, err := client.NBA.Seasons.List(ctx, &statapi.NBASeasonsListParams{Limit: statapi.Int(200)})
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
	var rows []statapi.NBASeasonPlayerStat
	for row, err := range client.NBA.SeasonPlayerStats.All(ctx, &statapi.NBASeasonPlayerStatsListParams{SeasonID: statapi.Int64(int64(season))}) {
		if err != nil {
			log.Fatal(err)
		}
		rows = append(rows, row)
	}

	// Rank by points
	ranked := append([]statapi.NBASeasonPlayerStat(nil), rows...)
	rankedKey := func(r statapi.NBASeasonPlayerStat) int {
		return r.Pts
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
	fmt.Println("NBA points leaders")
	fmt.Println(strings.Join([]string{"player_id", "pts"}, "\t"))
	for _, row := range leaders {
		fmt.Println(strings.Join([]string{str(row.PlayerID), str(row.Pts)}, "\t"))
	}

}
