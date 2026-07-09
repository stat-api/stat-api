// Fetch one NBA team by id
// Generated from schema/api/examples/get-by-id.yml — do not edit.
using StatApi;
using System.Text.Json;

var client = new StatApiClient(); // reads STAT_API_KEY from the environment

// List one team to borrow an id
var teams = (await client.Nba.Teams.ListAsync(new NbaTeamsListParams { Limit = 1 })).Rows;

// Fetch that team by id
var team = await client.Nba.Teams.GetAsync(teams[0].Id);

// Inspect the row
Console.WriteLine(JsonSerializer.Serialize(team, new JsonSerializerOptions { WriteIndented = true }));
