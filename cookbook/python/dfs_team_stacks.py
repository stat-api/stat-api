# Rank teams for a stack — DFS
# Generated from schema/api/examples/dfs-team-stacks.yml — do not edit.
from statapi import StatApi

api = StatApi()  # reads STAT_API_KEY from the environment

def _cell(value: object) -> str:
    return "" if value is None else str(value)

# Read both sides of every game on the slate
teams = api.dfs.team_stats.list(slate_id=129036, limit=100).rows

# Keep the teams that played
played = [r for r in teams if r["fantasy_pts"] is not None]

# Rank by fantasy points produced
best = sorted(played, key=lambda r: (r["fantasy_pts"] or 0), reverse=True)

# Take the eight best offenses
top = best[:8]

# Print the stack board
print("Most productive offenses on the slate")
print("\t".join(["team_id", "opponent_team_id", "is_home", "fantasy_pts", "pts", "total_yds", "player_count"]))
for row in top:
    print("\t".join([_cell(row["team_id"]), _cell(row["opponent_team_id"]), _cell(row["is_home"]), _cell(row["fantasy_pts"]), _cell(row["pts"]), _cell(row["total_yds"]), _cell(row["player_count"])]))
