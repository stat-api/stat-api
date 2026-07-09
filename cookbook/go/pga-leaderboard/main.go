// Read a PGA golfer's leaderboard finishes
// Generated from schema/api/examples/leaderboard.yml — do not edit.
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

	// Pick a golfer
	playersPage, err := client.PGA.Players.List(ctx, &statapi.PGAPlayersListParams{Limit: statapi.Int(1)})
	if err != nil {
		log.Fatal(err)
	}
	players := playersPage.Rows

	// Read that golfer's finishes
	boardPage, err := client.PGA.Leaderboards.List(ctx, &statapi.PGALeaderboardsListParams{PlayerID: statapi.Int64(int64(players[0].ID)), Limit: statapi.Int(25)})
	if err != nil {
		log.Fatal(err)
	}
	board := boardPage.Rows

	// Best finishes first
	ranked := append([]statapi.PGALeaderboard(nil), board...)
	rankedKey := func(r statapi.PGALeaderboard) int {
		if r.Rank != nil {
			return *r.Rank
		}
		return 0
	}
	sort.Slice(ranked, func(i, j int) bool {
		return rankedKey(ranked[i]) < rankedKey(ranked[j])
	})

	// Show the finishes
	fmt.Println(strings.Join([]string{"rank", "sg_total"}, "\t"))
	for _, row := range ranked {
		fmt.Println(strings.Join([]string{str(row.Rank), str(row.SgTotal)}, "\t"))
	}

}
