// Read your quota off a response — NBA
// Generated from schema/api/examples/quota-headers.yml — do not edit.
using StatApi;

var client = new StatApiClient(); // reads STAT_API_KEY from the environment

// List a page and read its quota
var page = await client.Nba.Teams.ListAsync(new NbaTeamsListParams { Limit = 3 });
if (page.Quota is not null)
{
    Console.WriteLine($"quota: {page.Quota.Remaining} of {page.Quota.Limit} records left this month");
}
else
{
    Console.WriteLine("no quota headers on this response");
}
