# See who actually scored on a slate — DFS
# Generated from schema/api/examples/dfs-slate-scorers.yml — do not edit.
from statapi import StatApi

api = StatApi()  # reads STAT_API_KEY from the environment

def _cell(value: object) -> str:
    return "" if value is None else str(value)

# Page the player pool for names
pool = list(api.dfs.slate_players.iter(slate_id=129036))

# Index the pool by player id
names = {row["player_id"]: row for row in pool}

# Page the slate's scoring rows
stats = list(api.dfs.player_stats.iter(slate_id=129036))

# Drop the players who did not play
scored = [r for r in stats if r["fantasy_pts"] is not None]

# Rank by fantasy points
best = sorted(scored, key=lambda r: (r["fantasy_pts"] or 0), reverse=True)

# Take the top fifteen
top = best[:15]

# Print the scoring board
print("Top scorers on the slate")
print("\t".join(["display_name", "position", "salary", "fantasy_pts"]))
for row in top:
    print("\t".join([_cell((names.get(row["player_id"]) or {}).get("display_name", row["player_id"])), _cell(row["position"]), _cell(row["salary"]), _cell(row["fantasy_pts"])]))
