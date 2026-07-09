# Rolling averages over an NBA game log
# Generated from schema/api/examples/rolling-averages.yml — do not edit.
from statapi import StatApi

api = StatApi()  # reads STAT_API_KEY from the environment

# Resolve the current season
season = max(api.nba.seasons.list(limit=200).rows, key=lambda s: s["start_year"])["id"]

# Grab one game
games = api.nba.games.list(season_id=season, limit=1).rows

# Borrow a player from that game's box score
seed = api.nba.game_player_stats.list(game_id=games[0]["id"], limit=1).rows

# Page that player's full game log
gamelog = list(api.nba.game_player_stats.iter(player_id=seed[0]["player_id"]))

# Oldest game first
chron = sorted(gamelog, key=lambda r: r["game_date"], reverse=False)

# Compute a 5-game trailing average of points
window = 5
for i, row in enumerate(chron):
    start = max(0, i - window + 1)
    span = chron[start:i + 1]
    avg = sum(r["pts"] for r in span) / len(span)
    print(f"game {i + 1}: pts={row['pts']}, {window}-game avg={avg:.1f}")
