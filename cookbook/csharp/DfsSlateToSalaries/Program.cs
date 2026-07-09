// Rank a DFS slate by salary and value
// Generated from schema/api/examples/slate-to-salaries.yml — do not edit.
using StatApi;

var client = new StatApiClient(); // reads STAT_API_KEY from the environment

// Page the slate's players
var players = new List<DfsSlatePlayer>();
await foreach (var row in client.Dfs.SlatePlayers.IterateAsync(new DfsSlatePlayersListParams { SlateId = 91396 }))
{
    players.Add(row);
}

// Rank by salary, highest first
var bysalary = players.OrderByDescending(r => r.Salary).ToList();

// Take the ten priciest players
var top = bysalary.Take(10).ToList();

// Fetch the top player's projection
var proj = (await client.Dfs.SlatePlayerProjections.ListAsync(new DfsSlatePlayerProjectionsListParams { SlatePlayerId = top[0].Id })).Rows;

// Compute projected points per $1000 of salary
if (proj.Count > 0 && top.Count > 0)
{
    double value = ((double)proj[0].Projection / top[0].Salary) * 1000;
    Console.WriteLine($"value of highest-salaried player = {value:F2} projected pts per $1000");
}

// Print the salary board
Console.WriteLine("Highest-salaried players on the slate");
Console.WriteLine(string.Join("\t", new[] { "display_name", "position", "salary" }));
foreach (var row in top)
{
    Console.WriteLine(string.Join("\t", new[] { Str(row.DisplayName), Str(row.Position), Str(row.Salary) }));
}


static string Str(object? v) => v?.ToString() ?? "";
