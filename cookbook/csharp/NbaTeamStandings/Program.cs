// Build the NBA standings
// Generated from schema/api/examples/team-standings.yml — do not edit.
using StatApi;

var client = new StatApiClient(); // reads STAT_API_KEY from the environment

// Resolve the current season
var season = (await client.Nba.Seasons.ListAsync(new NbaSeasonsListParams { Limit = 200 })).Rows.OrderByDescending(s => s.StartYear).First().Id;

// Page every team's season record
var rows = new List<NbaSeasonTeamStat>();
await foreach (var row in client.Nba.SeasonTeamStats.IterateAsync(new NbaSeasonTeamStatsListParams { SeasonId = season }))
{
    rows.Add(row);
}

// Order by wins, best first
var standings = rows.OrderByDescending(r => r.Wins).ToList();

// Take the top ten
var top = standings.Take(10).ToList();

// Print the standings
Console.WriteLine("NBA standings — top 10 by wins");
Console.WriteLine(string.Join("\t", new[] { "team_id", "wins", "losses" }));
foreach (var row in top)
{
    Console.WriteLine(string.Join("\t", new[] { Str(row.TeamId), Str(row.Wins), Str(row.Losses) }));
}


static string Str(object? v) => v?.ToString() ?? "";
