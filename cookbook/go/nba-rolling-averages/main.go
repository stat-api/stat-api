// Rolling averages over an NBA game log
// Generated from schema/api/examples/rolling-averages.yml — do not edit.
package main

import (
	"context"
	"fmt"
	statapi "github.com/stat-api/stat-api/go"
	"log"
	"sort"
)

func main() {
	client, err := statapi.New()
	if err != nil {
		log.Fatal(err)
	}
	ctx := context.Background()

	// Resolve the current season
	seasonPage, err := client.NBA.Seasons.List(ctx, &statapi.NBASeasonsListParams{Limit: statapi.Int(200)})
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

	// Grab one game
	gamesPage, err := client.NBA.Games.List(ctx, &statapi.NBAGamesListParams{SeasonID: statapi.Int64(int64(season)), Limit: statapi.Int(1)})
	if err != nil {
		log.Fatal(err)
	}
	games := gamesPage.Rows

	// Borrow a player from that game's box score
	seedPage, err := client.NBA.GamePlayerStats.List(ctx, &statapi.NBAGamePlayerStatsListParams{GameID: statapi.Int64(int64(games[0].ID)), Limit: statapi.Int(1)})
	if err != nil {
		log.Fatal(err)
	}
	seed := seedPage.Rows

	// Page that player's full game log
	var gamelog []statapi.NBAGamePlayerStat
	for row, err := range client.NBA.GamePlayerStats.All(ctx, &statapi.NBAGamePlayerStatsListParams{PlayerID: statapi.Int64(int64(seed[0].PlayerID))}) {
		if err != nil {
			log.Fatal(err)
		}
		gamelog = append(gamelog, row)
	}

	// Oldest game first
	chron := append([]statapi.NBAGamePlayerStat(nil), gamelog...)
	chronKey := func(r statapi.NBAGamePlayerStat) int {
		return r.GameDate
	}
	sort.Slice(chron, func(i, j int) bool {
		return chronKey(chron[i]) < chronKey(chron[j])
	})

	// Compute a 5-game trailing average of points
	window := 5
	for i, row := range chron {
	    start := i - window + 1
	    if start < 0 {
	        start = 0
	    }
	    span := chron[start : i+1]
	    sum := 0
	    for _, r := range span {
	        sum += r.Pts
	    }
	    avg := float64(sum) / float64(len(span))
	    fmt.Printf("game %d: pts=%d, %d-game avg=%.1f\n", i+1, row.Pts, window, avg)
	}

}
