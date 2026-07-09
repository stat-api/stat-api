// Build an NBA box score
// Generated from schema/api/examples/box-score.yml — do not edit.
using StatApi;
using System.Text.Json;

var client = new StatApiClient(); // reads STAT_API_KEY from the environment

// Resolve the current season
var season = (await client.Nba.Seasons.ListAsync(new NbaSeasonsListParams { Limit = 200 })).Rows.OrderByDescending(s => s.StartYear).First().Id;

// Find a game
var games = (await client.Nba.Games.ListAsync(new NbaGamesListParams { SeasonId = season, Limit = 1 })).Rows;

// Pull per-player stats for that game
var stats = (await client.Nba.GamePlayerStats.ListAsync(new NbaGamePlayerStatsListParams { GameId = games[0].Id })).Rows;

// Split the stat lines into the two teams
var by_team = stats.GroupBy(row => row.TeamId).ToDictionary(g => g.Key, g => g.ToList());

// Render both halves of the box score
Console.WriteLine(JsonSerializer.Serialize(by_team, new JsonSerializerOptions { WriteIndented = true }));
