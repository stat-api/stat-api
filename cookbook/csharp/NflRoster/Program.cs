// List a NFL team's roster
// Generated from schema/api/examples/roster.yml — do not edit.
using StatApi;

var client = new StatApiClient(); // reads STAT_API_KEY from the environment

// Grab one team
var teams = (await client.Nfl.Teams.ListAsync(new NflTeamsListParams { Limit = 1 })).Rows;

// List that team's players
var roster = (await client.Nfl.Players.ListAsync(new NflPlayersListParams { TeamId = teams[0].Id })).Rows;

// Print the roster
Console.WriteLine("NFL roster");
Console.WriteLine(string.Join("\t", new[] { "full_name", "primary_position", "jersey" }));
foreach (var row in roster)
{
    Console.WriteLine(string.Join("\t", new[] { Str(row.FullName), Str(row.PrimaryPosition), Str(row.Jersey) }));
}


static string Str(object? v) => v?.ToString() ?? "";
