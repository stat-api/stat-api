// Read an NFL player's game log
// Generated from schema/api/examples/player-game-log.yml — do not edit.
using StatApi;

var client = new StatApiClient(); // reads STAT_API_KEY from the environment

// Resolve the current season
var season = (await client.Nfl.Seasons.ListAsync(new NflSeasonsListParams { Limit = 200 })).Rows.OrderByDescending(s => s.StartYear).First().Id;

// Page the season's games
var games = new List<NflGame>();
await foreach (var row in client.Nfl.Games.IterateAsync(new NflGamesListParams { SeasonId = season }))
{
    games.Add(row);
}

// Index games by id
var game_by_id = games.ToDictionary(row => row.Id);

// Borrow a player from one game's box score
var seed = (await client.Nfl.GamePlayerStats.ListAsync(new NflGamePlayerStatsListParams { GameId = games[0].Id, Limit = 1 })).Rows;

// Page that player's game log
var gamelog = new List<NflGamePlayerStat>();
await foreach (var row in client.Nfl.GamePlayerStats.IterateAsync(new NflGamePlayerStatsListParams { PlayerId = seed[0].PlayerId }))
{
    gamelog.Add(row);
}

// Print the game log
Console.WriteLine("NFL game log (fantasy points)");
Console.WriteLine(string.Join("\t", new[] { "game_time", "fantasy_pts" }));
foreach (var row in gamelog)
{
    Console.WriteLine(string.Join("\t", new[] { Str(game_by_id.TryGetValue(row.GameId, out var joined) ? (object?)joined.GameTime : row.GameId), Str(row.FantasyPts) }));
}


static string Str(object? v) => v?.ToString() ?? "";
