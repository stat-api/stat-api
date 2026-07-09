// Auto-paginate every MLB team
// Generated from schema/api/examples/pagination-auto.yml — do not edit.
package main

import (
	"context"
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

	// Walk every page of teams
	var teams []statapi.MLBTeam
	for row, err := range client.MLB.Teams.All(ctx, nil) {
		if err != nil {
			log.Fatal(err)
		}
		teams = append(teams, row)
	}

	// Report how many rows the iterator collected
	fmt.Printf("fetched %d teams\n", len(teams))

}
