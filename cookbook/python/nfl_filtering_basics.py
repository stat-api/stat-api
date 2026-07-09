# Filter NFL games by season
# Generated from schema/api/examples/filtering-basics.yml — do not edit.
from statapi import StatApi
import json

api = StatApi()  # reads STAT_API_KEY from the environment

# Resolve the current season
season = max(api.nfl.seasons.list(limit=200).rows, key=lambda s: s["start_year"])["id"]

# Filter games to that season
games = api.nfl.games.list(season_id=season, limit=5).rows

# Print the filtered games
print(json.dumps(games, indent=2, default=str))
