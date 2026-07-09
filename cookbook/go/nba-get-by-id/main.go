// Fetch one NBA team by id
// Generated from schema/api/examples/get-by-id.yml — do not edit.
package main

import (
	"context"
	"encoding/json"
	"fmt"
	statapi "github.com/stat-api/stat-api/go"
	"log"
)

func main() {
	client, err := statapi.New()
	if err != nil {
		log.Fatal(err)
	}
	ctx := context.Background()

	// List one team to borrow an id
	teamsPage, err := client.NBA.Teams.List(ctx, &statapi.NBATeamsListParams{Limit: statapi.Int(1)})
	if err != nil {
		log.Fatal(err)
	}
	teams := teamsPage.Rows

	// Fetch that team by id
	team, err := client.NBA.Teams.Get(ctx, int64(teams[0].ID))
	if err != nil {
		log.Fatal(err)
	}

	// Inspect the row
	teamJSON, err := json.MarshalIndent(team, "", "  ")
	if err != nil {
		log.Fatal(err)
	}
	fmt.Println(string(teamJSON))

}
