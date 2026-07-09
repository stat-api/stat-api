# Build an NFL box score
# Generated from schema/api/examples/box-score.yml — do not edit.
from statapi import StatApi
import json

api = StatApi()  # reads STAT_API_KEY from the environment

# Resolve the current season
season = max(api.nfl.seasons.list(limit=200).rows, key=lambda s: s["start_year"])["id"]

# Find a game
games = api.nfl.games.list(season_id=season, limit=1).rows

# Pull per-player stats for that game
stats = api.nfl.game_player_stats.list(game_id=games[0]["id"]).rows

# Split the stat lines into the two teams
by_team: dict = {}
for row in stats:
    by_team.setdefault(row["team_id"], []).append(row)

# Render both halves of the box score
print(json.dumps(by_team, indent=2, default=str))
