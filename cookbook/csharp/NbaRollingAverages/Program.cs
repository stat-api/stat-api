// Rolling averages over an NBA game log
// Generated from schema/api/examples/rolling-averages.yml — do not edit.
using StatApi;

var client = new StatApiClient(); // reads STAT_API_KEY from the environment

// Resolve the current season
var season = (await client.Nba.Seasons.ListAsync(new NbaSeasonsListParams { Limit = 200 })).Rows.OrderByDescending(s => s.StartYear).First().Id;

// Grab one game
var games = (await client.Nba.Games.ListAsync(new NbaGamesListParams { SeasonId = season, Limit = 1 })).Rows;

// Borrow a player from that game's box score
var seed = (await client.Nba.GamePlayerStats.ListAsync(new NbaGamePlayerStatsListParams { GameId = games[0].Id, Limit = 1 })).Rows;

// Page that player's full game log
var gamelog = new List<NbaGamePlayerStat>();
await foreach (var row in client.Nba.GamePlayerStats.IterateAsync(new NbaGamePlayerStatsListParams { PlayerId = seed[0].PlayerId }))
{
    gamelog.Add(row);
}

// Oldest game first
var chron = gamelog.OrderBy(r => r.GameDate).ToList();

// Compute a 5-game trailing average of points
int window = 5;
for (int i = 0; i < chron.Count; i++)
{
    int start = Math.Max(0, i - window + 1);
    var span = chron.Skip(start).Take(i - start + 1).ToList();
    double avg = span.Average(r => r.Pts);
    Console.WriteLine($"game {i + 1}: pts={chron[i].Pts}, {window}-game avg={avg:F1}");
}
