// Handle errors by type — NBA
// Generated from schema/api/examples/error-handling.yml — do not edit.
using StatApi;

var client = new StatApiClient(); // reads STAT_API_KEY from the environment

// Trigger a 404 and branch on the error
try
{
    await client.Nba.Teams.GetAsync(999999999);
    Console.WriteLine("unexpectedly found a team");
}
catch (NotFoundException e)
{
    Console.WriteLine($"404 NotFoundException: no such team ({e.Status})");
}
catch (AuthenticationException)
{
    Console.WriteLine("401 AuthenticationException: bad or missing API key");
}
catch (ValidationException e)
{
    Console.WriteLine($"400 ValidationException: {e.Body}");
}
catch (QuotaExceededException)
{
    Console.WriteLine("429 QuotaExceededException: monthly quota spent — never retried");
}
