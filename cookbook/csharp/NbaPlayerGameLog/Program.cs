// Read an NBA player's game log
// Generated from schema/api/examples/player-game-log.yml — do not edit.
using StatApi;

var client = new StatApiClient(); // reads STAT_API_KEY from the environment

// Resolve the current season
var season = (await client.Nba.Seasons.ListAsync(new NbaSeasonsListParams { Limit = 200 })).Rows.OrderByDescending(s => s.StartYear).First().Id;

// Page the season's games
var games = new List<NbaGame>();
await foreach (var row in client.Nba.Games.IterateAsync(new NbaGamesListParams { SeasonId = season }))
{
    games.Add(row);
}

// Index games by id
var game_by_id = games.ToDictionary(row => row.Id);

// Borrow a player from one game's box score
var seed = (await client.Nba.GamePlayerStats.ListAsync(new NbaGamePlayerStatsListParams { GameId = games[0].Id, Limit = 1 })).Rows;

// Page that player's game log
var gamelog = new List<NbaGamePlayerStat>();
await foreach (var row in client.Nba.GamePlayerStats.IterateAsync(new NbaGamePlayerStatsListParams { PlayerId = seed[0].PlayerId }))
{
    gamelog.Add(row);
}

// Print the game log
Console.WriteLine("NBA game log (points)");
Console.WriteLine(string.Join("\t", new[] { "game_time", "pts" }));
foreach (var row in gamelog)
{
    Console.WriteLine(string.Join("\t", new[] { Str(game_by_id.TryGetValue(row.GameId, out var joined) ? (object?)joined.GameTime : row.GameId), Str(row.Pts) }));
}


static string Str(object? v) => v?.ToString() ?? "";
