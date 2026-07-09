// Satisfy a required filter set — DFS
// Generated from schema/api/examples/required-filter-sets.yml — do not edit.
using StatApi;

var client = new StatApiClient(); // reads STAT_API_KEY from the environment

// See the 400, then satisfy the set
// dfs.slates requires the [operator_id, date] set — a bare call is a 400.
try
{
    await client.Dfs.Slates.ListAsync(new DfsSlatesListParams { Limit = 5 });
    Console.WriteLine("unexpected: unfiltered slates call succeeded");
}
catch (ValidationException e)
{
    Console.WriteLine($"rejected ({e.Status}): {e.Body}");
}
// Supply BOTH members of the set and the call is accepted.
var slates = (await client.Dfs.Slates.ListAsync(new DfsSlatesListParams { OperatorId = 1, Date = "2026-07-02" })).Rows;
Console.WriteLine($"operator 1 ran {slates.Count} slates on 2026-07-02");
