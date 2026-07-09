// Build the MLB standings
// Generated from schema/api/examples/team-standings.yml — do not edit.
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
	seasonPage, err := client.MLB.Seasons.List(ctx, &statapi.MLBSeasonsListParams{Limit: statapi.Int(200)})
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

	// Page every team's season record
	var rows []statapi.MLBSeasonTeamStat
	for row, err := range client.MLB.SeasonTeamStats.All(ctx, &statapi.MLBSeasonTeamStatsListParams{SeasonID: statapi.Int64(int64(season))}) {
		if err != nil {
			log.Fatal(err)
		}
		rows = append(rows, row)
	}

	// Order by wins, best first
	standings := append([]statapi.MLBSeasonTeamStat(nil), rows...)
	standingsKey := func(r statapi.MLBSeasonTeamStat) int {
		return r.Wins
	}
	sort.Slice(standings, func(i, j int) bool {
		return standingsKey(standings[j]) < standingsKey(standings[i])
	})

	// Take the top ten
	top := standings
	if len(top) > 10 {
		top = top[:10]
	}

	// Print the standings
	fmt.Println("MLB standings — top 10 by wins")
	fmt.Println(strings.Join([]string{"team_id", "wins", "losses"}, "\t"))
	for _, row := range top {
		fmt.Println(strings.Join([]string{str(row.TeamID), str(row.Wins), str(row.Losses)}, "\t"))
	}

}
