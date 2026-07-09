// Build a PGA golfer's scorecard
// Generated from schema/api/examples/scorecard.yml — do not edit.
using StatApi;

var client = new StatApiClient(); // reads STAT_API_KEY from the environment

// Pick a golfer
var players = (await client.Pga.Players.ListAsync(new PgaPlayersListParams { Limit = 1 })).Rows;

// Index the golfer by id for name lookups
var player_by_id = players.ToDictionary(row => row.Id);

// Pull the hole-by-hole cards
var holes = (await client.Pga.PlayerHoles.ListAsync(new PgaPlayerHolesListParams { PlayerId = players[0].Id })).Rows;

// Render the scorecard
Console.WriteLine(string.Join("\t", new[] { "full_name", "round_number", "hole_number", "to_par" }));
foreach (var row in holes)
{
    Console.WriteLine(string.Join("\t", new[] { Str(player_by_id.TryGetValue(row.PlayerId, out var joined) ? (object?)joined.FullName : row.PlayerId), Str(row.RoundNumber), Str(row.HoleNumber), Str(row.ToPar) }));
}


static string Str(object? v) => v?.ToString() ?? "";
