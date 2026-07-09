// Filter NFL games by season
// Generated from schema/api/examples/filtering-basics.yml — do not edit.
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

	// Resolve the current season
	seasonPage, err := client.NFL.Seasons.List(ctx, &statapi.NFLSeasonsListParams{Limit: statapi.Int(200)})
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

	// Filter games to that season
	gamesPage, err := client.NFL.Games.List(ctx, &statapi.NFLGamesListParams{SeasonID: statapi.Int64(int64(season)), Limit: statapi.Int(5)})
	if err != nil {
		log.Fatal(err)
	}
	games := gamesPage.Rows

	// Print the filtered games
	gamesJSON, err := json.MarshalIndent(games, "", "  ")
	if err != nil {
		log.Fatal(err)
	}
	fmt.Println(string(gamesJSON))

}
