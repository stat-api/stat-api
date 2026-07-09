# Find Kalshi markets for an NBA game
# Generated from schema/api/examples/kalshi-markets-for-game.yml — do not edit.
from statapi import StatApi

api = StatApi()  # reads STAT_API_KEY from the environment

# Resolve the current season
season = max(api.nba.seasons.list(limit=200).rows, key=lambda s: s["start_year"])["id"]

# Grab one game
games = api.nba.games.list(season_id=season, limit=1).rows

# Join to Kalshi by (league_code, competition_id), then list markets
game = games[0]
events = api.kalshi.events.list(competition_id=game["id"], league_code="nba").rows
if not events:
    print(f"no Kalshi event linked to game {game['id']}")
else:
    event = events[0]
    print(f"event: {event['title']}")
    markets = api.kalshi.markets.list(event_id=event["id"]).rows
    print("ticker\ttitle\tstatus")
    for m in markets:
        print(f"{m['ticker']}\t{m['title']}\t{m['status']}")
