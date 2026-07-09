// List a MLB team's roster
// Generated from schema/api/examples/roster.yml — do not edit.
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

	// Grab one team
	teamsPage, err := client.MLB.Teams.List(ctx, &statapi.MLBTeamsListParams{Limit: statapi.Int(1)})
	if err != nil {
		log.Fatal(err)
	}
	teams := teamsPage.Rows

	// List that team's players
	rosterPage, err := client.MLB.Players.List(ctx, &statapi.MLBPlayersListParams{TeamID: statapi.Int64(int64(teams[0].ID))})
	if err != nil {
		log.Fatal(err)
	}
	roster := rosterPage.Rows

	// Print the roster
	fmt.Println("MLB roster")
	fmt.Println(strings.Join([]string{"full_name", "primary_position", "jersey"}, "\t"))
	for _, row := range roster {
		fmt.Println(strings.Join([]string{str(row.FullName), str(row.PrimaryPosition), str(row.Jersey)}, "\t"))
	}

}
