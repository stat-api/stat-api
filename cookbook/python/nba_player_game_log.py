# Read an NBA player's game log
# Generated from schema/api/examples/player-game-log.yml — do not edit.
from statapi import StatApi

api = StatApi()  # reads STAT_API_KEY from the environment

def _cell(value: object) -> str:
    return "" if value is None else str(value)

# Resolve the current season
season = max(api.nba.seasons.list(limit=200).rows, key=lambda s: s["start_year"])["id"]

# Page the season's games
games = list(api.nba.games.iter(season_id=season))

# Index games by id
game_by_id = {row["id"]: row for row in games}

# Borrow a player from one game's box score
seed = api.nba.game_player_stats.list(game_id=games[0]["id"], limit=1).rows

# Page that player's game log
gamelog = list(api.nba.game_player_stats.iter(player_id=seed[0]["player_id"]))

# Print the game log
print("NBA game log (points)")
print("\t".join(["game_time", "pts"]))
for row in gamelog:
    print("\t".join([_cell((game_by_id.get(row["game_id"]) or {}).get("game_time", row["game_id"])), _cell(row["pts"])]))
