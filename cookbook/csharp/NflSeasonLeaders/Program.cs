// Rank NFL season leaders
// Generated from schema/api/examples/season-leaders.yml — do not edit.
using StatApi;

var client = new StatApiClient(); // reads STAT_API_KEY from the environment

// Resolve the current season
var season = (await client.Nfl.Seasons.ListAsync(new NflSeasonsListParams { Limit = 200 })).Rows.OrderByDescending(s => s.StartYear).First().Id;

// Page through the whole season
var rows = new List<NflSeasonPlayerStat>();
await foreach (var row in client.Nfl.SeasonPlayerStats.IterateAsync(new NflSeasonPlayerStatsListParams { SeasonId = season }))
{
    rows.Add(row);
}

// Rank by passing yards
var ranked = rows.OrderByDescending(r => r.PassingYds).ToList();

// Take the top ten
var leaders = ranked.Take(10).ToList();

// Print the leaderboard
Console.WriteLine("NFL passing yards leaders");
Console.WriteLine(string.Join("\t", new[] { "player_id", "passing_yds" }));
foreach (var row in leaders)
{
    Console.WriteLine(string.Join("\t", new[] { Str(row.PlayerId), Str(row.PassingYds) }));
}


static string Str(object? v) => v?.ToString() ?? "";
