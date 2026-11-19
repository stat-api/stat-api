# Find the highest-scoring games on a slate — DFS
# Generated from schema/api/examples/dfs-shootout-games.yml — do not edit.
from statapi import StatApi

api = StatApi()  # reads STAT_API_KEY from the environment

def _cell(value: object) -> str:
    return "" if value is None else str(value)

# Read every game in the slate's pool
games = api.dfs.game_stats.list(slate_id=129036, limit=50).rows

# Keep the games that finished
played = [r for r in games if r["total_fpts"] is not None]

# Rank by total fantasy points
best = sorted(played, key=lambda r: (r["total_fpts"] or 0), reverse=True)

# Take the five biggest games
top = best[:5]

# Print the game board
print("Highest-scoring games on the slate")
print("\t".join(["game_id", "total_fpts", "home_fpts", "away_fpts", "home_pts", "away_pts", "player_count"]))
for row in top:
    print("\t".join([_cell(row["game_id"]), _cell(row["total_fpts"]), _cell(row["home_fpts"]), _cell(row["away_fpts"]), _cell(row["home_pts"]), _cell(row["away_pts"]), _cell(row["player_count"])]))
