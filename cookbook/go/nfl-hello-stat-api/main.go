// Hello, NFL
// Generated from schema/api/examples/hello-stat-api.yml — do not edit.
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

	// List a few teams
	teamsPage, err := client.NFL.Teams.List(ctx, &statapi.NFLTeamsListParams{Limit: statapi.Int(3)})
	if err != nil {
		log.Fatal(err)
	}
	teams := teamsPage.Rows

	// Print what came back
	teamsJSON, err := json.MarshalIndent(teams, "", "  ")
	if err != nil {
		log.Fatal(err)
	}
	fmt.Println(string(teamsJSON))

}
