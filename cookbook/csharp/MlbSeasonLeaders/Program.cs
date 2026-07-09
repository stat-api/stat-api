// Rank MLB season leaders
// Generated from schema/api/examples/season-leaders.yml — do not edit.
using StatApi;

var client = new StatApiClient(); // reads STAT_API_KEY from the environment

// Resolve the current season
var season = (await client.Mlb.Seasons.ListAsync(new MlbSeasonsListParams { Limit = 200 })).Rows.OrderByDescending(s => s.StartYear).First().Id;

// Page through the whole season
var rows = new List<MlbSeasonPlayerStat>();
await foreach (var row in client.Mlb.SeasonPlayerStats.IterateAsync(new MlbSeasonPlayerStatsListParams { SeasonId = season }))
{
    rows.Add(row);
}

// Rank by home runs
var ranked = rows.OrderByDescending(r => r.HomeRuns).ToList();

// Take the top ten
var leaders = ranked.Take(10).ToList();

// Print the leaderboard
Console.WriteLine("MLB home runs leaders");
Console.WriteLine(string.Join("\t", new[] { "player_id", "home_runs" }));
foreach (var row in leaders)
{
    Console.WriteLine(string.Join("\t", new[] { Str(row.PlayerId), Str(row.HomeRuns) }));
}


static string Str(object? v) => v?.ToString() ?? "";
