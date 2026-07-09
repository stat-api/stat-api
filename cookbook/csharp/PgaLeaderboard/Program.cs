// Read a PGA golfer's leaderboard finishes
// Generated from schema/api/examples/leaderboard.yml — do not edit.
using StatApi;

var client = new StatApiClient(); // reads STAT_API_KEY from the environment

// Pick a golfer
var players = (await client.Pga.Players.ListAsync(new PgaPlayersListParams { Limit = 1 })).Rows;

// Read that golfer's finishes
var board = (await client.Pga.Leaderboards.ListAsync(new PgaLeaderboardsListParams { PlayerId = players[0].Id, Limit = 25 })).Rows;

// Best finishes first
var ranked = board.OrderBy(r => r.Rank ?? 0).ToList();

// Show the finishes
Console.WriteLine(string.Join("\t", new[] { "rank", "sg_total" }));
foreach (var row in ranked)
{
    Console.WriteLine(string.Join("\t", new[] { Str(row.Rank), Str(row.SgTotal) }));
}


static string Str(object? v) => v?.ToString() ?? "";
