// Paginate by hand with from_id — NBA
// Generated from schema/api/examples/pagination-manual.yml — do not edit.
using StatApi;

var client = new StatApiClient(); // reads STAT_API_KEY from the environment

// Follow next_from_id until it is null
long? fromId = null;
int total = 0;
int pageNum = 0;
while (true)
{
    var page = await client.Nba.Teams.ListAsync(new NbaTeamsListParams { Limit = 100, FromId = fromId });
    pageNum++;
    total += page.Rows.Count;
    Console.WriteLine($"page {pageNum}: {page.Rows.Count} rows, next cursor = {page.NextFromId?.ToString() ?? "none"}");
    if (page.NextFromId is null)
    {
        break;
    }
    fromId = page.NextFromId;
}
Console.WriteLine($"walked {total} teams across {pageNum} pages by hand");
