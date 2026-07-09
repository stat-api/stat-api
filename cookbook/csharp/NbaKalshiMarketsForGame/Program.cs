// Find Kalshi markets for an NBA game
// Generated from schema/api/examples/kalshi-markets-for-game.yml — do not edit.
using StatApi;

var client = new StatApiClient(); // reads STAT_API_KEY from the environment

// Resolve the current season
var season = (await client.Nba.Seasons.ListAsync(new NbaSeasonsListParams { Limit = 200 })).Rows.OrderByDescending(s => s.StartYear).First().Id;

// Grab one game
var games = (await client.Nba.Games.ListAsync(new NbaGamesListParams { SeasonId = season, Limit = 1 })).Rows;

// Join to Kalshi by (league_code, competition_id), then list markets
var game = games[0];
var events = (await client.Kalshi.Events.ListAsync(new KalshiEventsListParams { CompetitionId = game.Id, LeagueCode = "nba" })).Rows;
if (events.Count == 0)
{
    Console.WriteLine($"no Kalshi event linked to game {game.Id}");
}
else
{
    var kalshiEvent = events[0];
    Console.WriteLine($"event: {kalshiEvent.Title}");
    var markets = (await client.Kalshi.Markets.ListAsync(new KalshiMarketsListParams { EventId = kalshiEvent.Id })).Rows;
    Console.WriteLine("ticker\ttitle\tstatus");
    foreach (var m in markets)
    {
        Console.WriteLine($"{m.Ticker}\t{m.Title}\t{m.Status}");
    }
}
