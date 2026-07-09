// Find Kalshi markets for an NBA game
// Generated from schema/api/examples/kalshi-markets-for-game.yml — do not edit.
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

	// Join to Kalshi by (league_code, competition_id), then list markets
	game := games[0]
	events, err := client.Kalshi.Events.List(ctx, &statapi.KalshiEventsListParams{CompetitionID: statapi.Int64(game.ID), LeagueCode: statapi.String("nba")})
	if err != nil {
	    log.Fatal(err)
	}
	if len(events.Rows) == 0 {
	    fmt.Printf("no Kalshi event linked to game %d\n", game.ID)
	} else {
	    event := events.Rows[0]
	    fmt.Printf("event: %s\n", event.Title)
	    markets, err := client.Kalshi.Markets.List(ctx, &statapi.KalshiMarketsListParams{EventID: statapi.Int64(event.ID)})
	    if err != nil {
	        log.Fatal(err)
	    }
	    fmt.Println("ticker\ttitle\tstatus")
	    for _, m := range markets.Rows {
	        fmt.Printf("%s\t%s\t%s\n", m.Ticker, m.Title, m.Status)
	    }
	}

}
