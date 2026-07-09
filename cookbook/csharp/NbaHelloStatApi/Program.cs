// Hello, NBA
// Generated from schema/api/examples/hello-stat-api.yml — do not edit.
using StatApi;
using System.Text.Json;

var client = new StatApiClient(); // reads STAT_API_KEY from the environment

// List a few teams
var teams = (await client.Nba.Teams.ListAsync(new NbaTeamsListParams { Limit = 3 })).Rows;

// Print what came back
Console.WriteLine(JsonSerializer.Serialize(teams, new JsonSerializerOptions { WriteIndented = true }));
