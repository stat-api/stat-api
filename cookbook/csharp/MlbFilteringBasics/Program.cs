// Filter MLB games by season
// Generated from schema/api/examples/filtering-basics.yml — do not edit.
using StatApi;
using System.Text.Json;

var client = new StatApiClient(); // reads STAT_API_KEY from the environment

// Resolve the current season
var season = (await client.Mlb.Seasons.ListAsync(new MlbSeasonsListParams { Limit = 200 })).Rows.OrderByDescending(s => s.StartYear).First().Id;

// Filter games to that season
var games = (await client.Mlb.Games.ListAsync(new MlbGamesListParams { SeasonId = season, Limit = 5 })).Rows;

// Print the filtered games
Console.WriteLine(JsonSerializer.Serialize(games, new JsonSerializerOptions { WriteIndented = true }));
