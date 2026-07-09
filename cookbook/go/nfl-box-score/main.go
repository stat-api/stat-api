// Build an NFL box score
// Generated from schema/api/examples/box-score.yml — do not edit.
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

	// Find a game
	gamesPage, err := client.NFL.Games.List(ctx, &statapi.NFLGamesListParams{SeasonID: statapi.Int64(int64(season)), Limit: statapi.Int(1)})
	if err != nil {
		log.Fatal(err)
	}
	games := gamesPage.Rows

	// Pull per-player stats for that game
	statsPage, err := client.NFL.GamePlayerStats.List(ctx, &statapi.NFLGamePlayerStatsListParams{GameID: statapi.Int64(int64(games[0].ID))})
	if err != nil {
		log.Fatal(err)
	}
	stats := statsPage.Rows

	// Split the stat lines into the two teams
	by_team := map[int64][]statapi.NFLGamePlayerStat{}
	for _, row := range stats {
		by_team[row.TeamID] = append(by_team[row.TeamID], row)
	}

	// Render both halves of the box score
	by_teamJSON, err := json.MarshalIndent(by_team, "", "  ")
	if err != nil {
		log.Fatal(err)
	}
	fmt.Println(string(by_teamJSON))

}
