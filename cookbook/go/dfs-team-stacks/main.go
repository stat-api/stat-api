// Rank teams for a stack — DFS
// Generated from schema/api/examples/dfs-team-stacks.yml — do not edit.
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

	// Read both sides of every game on the slate
	teamsPage, err := client.DFS.TeamStats.List(ctx, &statapi.DFSTeamStatsListParams{SlateID: statapi.Int64(int64(129036)), Limit: statapi.Int(100)})
	if err != nil {
		log.Fatal(err)
	}
	teams := teamsPage.Rows

	// Keep the teams that played
	played := make([]statapi.DFSTeamStat, 0, len(teams))
	for _, r := range teams {
		if r.FantasyPts != nil {
			played = append(played, r)
		}
	}

	// Rank by fantasy points produced
	best := append([]statapi.DFSTeamStat(nil), played...)
	bestKey := func(r statapi.DFSTeamStat) float64 {
		if r.FantasyPts != nil {
			return *r.FantasyPts
		}
		return 0
	}
	sort.Slice(best, func(i, j int) bool {
		return bestKey(best[j]) < bestKey(best[i])
	})

	// Take the eight best offenses
	top := best
	if len(top) > 8 {
		top = top[:8]
	}

	// Print the stack board
	fmt.Println("Most productive offenses on the slate")
	fmt.Println(strings.Join([]string{"team_id", "opponent_team_id", "is_home", "fantasy_pts", "pts", "total_yds", "player_count"}, "\t"))
	for _, row := range top {
		fmt.Println(strings.Join([]string{str(row.TeamID), str(row.OpponentTeamID), str(row.IsHome), str(row.FantasyPts), str(row.Pts), str(row.TotalYds), str(row.PlayerCount)}, "\t"))
	}

}
