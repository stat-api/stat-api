// Rank NBA season leaders
// Generated from schema/api/examples/season-leaders.yml — do not edit.
using StatApi;

var client = new StatApiClient(); // reads STAT_API_KEY from the environment

// Resolve the current season
var season = (await client.Nba.Seasons.ListAsync(new NbaSeasonsListParams { Limit = 200 })).Rows.OrderByDescending(s => s.StartYear).First().Id;

// Page through the whole season
var rows = new List<NbaSeasonPlayerStat>();
await foreach (var row in client.Nba.SeasonPlayerStats.IterateAsync(new NbaSeasonPlayerStatsListParams { SeasonId = season }))
{
    rows.Add(row);
}

// Rank by points
var ranked = rows.OrderByDescending(r => r.Pts).ToList();

// Take the top ten
var leaders = ranked.Take(10).ToList();

// Print the leaderboard
Console.WriteLine("NBA points leaders");
Console.WriteLine(string.Join("\t", new[] { "player_id", "pts" }));
foreach (var row in leaders)
{
    Console.WriteLine(string.Join("\t", new[] { Str(row.PlayerId), Str(row.Pts) }));
}


static string Str(object? v) => v?.ToString() ?? "";
