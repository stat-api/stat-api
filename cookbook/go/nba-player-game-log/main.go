// Read an NBA player's game log
// Generated from schema/api/examples/player-game-log.yml — do not edit.
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

	// Page the season's games
	var games []statapi.NBAGame
	for row, err := range client.NBA.Games.All(ctx, &statapi.NBAGamesListParams{SeasonID: statapi.Int64(int64(season))}) {
		if err != nil {
			log.Fatal(err)
		}
		games = append(games, row)
	}

	// Index games by id
	game_by_id := map[int64]statapi.NBAGame{}
	for _, row := range games {
		game_by_id[row.ID] = row
	}

	// Borrow a player from one game's box score
	seedPage, err := client.NBA.GamePlayerStats.List(ctx, &statapi.NBAGamePlayerStatsListParams{GameID: statapi.Int64(int64(games[0].ID)), Limit: statapi.Int(1)})
	if err != nil {
		log.Fatal(err)
	}
	seed := seedPage.Rows

	// Page that player's game log
	var gamelog []statapi.NBAGamePlayerStat
	for row, err := range client.NBA.GamePlayerStats.All(ctx, &statapi.NBAGamePlayerStatsListParams{PlayerID: statapi.Int64(int64(seed[0].PlayerID))}) {
		if err != nil {
			log.Fatal(err)
		}
		gamelog = append(gamelog, row)
	}

	// Print the game log
	fmt.Println("NBA game log (points)")
	fmt.Println(strings.Join([]string{"game_time", "pts"}, "\t"))
	for _, row := range gamelog {
		fmt.Println(strings.Join([]string{str(func() any {
		if v, ok := game_by_id[row.GameID]; ok {
			return v.GameTime
		}
		return row.GameID
	}()), str(row.Pts)}, "\t"))
	}

}
