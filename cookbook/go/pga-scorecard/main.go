// Build a PGA golfer's scorecard
// Generated from schema/api/examples/scorecard.yml — do not edit.
package main

import (
	"context"
	"fmt"
	statapi "github.com/stat-api/stat-api/go"
	"log"
	"reflect"
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

	// Index the golfer by id for name lookups
	player_by_id := map[int64]statapi.PGAPlayer{}
	for _, row := range players {
		player_by_id[row.ID] = row
	}

	// Pull the hole-by-hole cards
	holesPage, err := client.PGA.PlayerHoles.List(ctx, &statapi.PGAPlayerHolesListParams{PlayerID: statapi.Int64(int64(players[0].ID))})
	if err != nil {
		log.Fatal(err)
	}
	holes := holesPage.Rows

	// Render the scorecard
	fmt.Println(strings.Join([]string{"full_name", "round_number", "hole_number", "to_par"}, "\t"))
	for _, row := range holes {
		fmt.Println(strings.Join([]string{str(func() any {
		if v, ok := player_by_id[row.PlayerID]; ok {
			return v.FullName
		}
		return row.PlayerID
	}()), str(row.RoundNumber), str(row.HoleNumber), str(row.ToPar)}, "\t"))
	}

}
