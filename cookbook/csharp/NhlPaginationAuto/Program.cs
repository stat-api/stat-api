// Auto-paginate every NHL team
// Generated from schema/api/examples/pagination-auto.yml — do not edit.
using StatApi;

var client = new StatApiClient(); // reads STAT_API_KEY from the environment

// Walk every page of teams
var teams = new List<NhlTeam>();
await foreach (var row in client.Nhl.Teams.IterateAsync())
{
    teams.Add(row);
}

// Report how many rows the iterator collected
Console.WriteLine($"fetched {teams.Count} teams");
