// Rank NHL season leaders
// Generated from schema/api/examples/season-leaders.yml — do not edit.
using StatApi;

var client = new StatApiClient(); // reads STAT_API_KEY from the environment

// Resolve the current season
var season = (await client.Nhl.Seasons.ListAsync(new NhlSeasonsListParams { Limit = 200 })).Rows.OrderByDescending(s => s.StartYear).First().Id;

// Page through the whole season
var rows = new List<NhlSeasonPlayerStat>();
await foreach (var row in client.Nhl.SeasonPlayerStats.IterateAsync(new NhlSeasonPlayerStatsListParams { SeasonId = season }))
{
    rows.Add(row);
}

// Rank by goals
var ranked = rows.OrderByDescending(r => r.Goals).ToList();

// Take the top ten
var leaders = ranked.Take(10).ToList();

// Print the leaderboard
Console.WriteLine("NHL goals leaders");
Console.WriteLine(string.Join("\t", new[] { "player_id", "goals" }));
foreach (var row in leaders)
{
    Console.WriteLine(string.Join("\t", new[] { Str(row.PlayerId), Str(row.Goals) }));
}


static string Str(object? v) => v?.ToString() ?? "";
