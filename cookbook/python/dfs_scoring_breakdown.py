# See where a player's fantasy points came from — DFS
# Generated from schema/api/examples/dfs-scoring-breakdown.yml — do not edit.
from statapi import StatApi

api = StatApi()  # reads STAT_API_KEY from the environment

def _cell(value: object) -> str:
    return "" if value is None else str(value)

# Read one player's slate history
rows = api.dfs.player_stats.list(player_id=301, limit=200).rows

# Keep the games that were played
played = [r for r in rows if r["fantasy_pts"] is not None]

# Best game first
best = sorted(played, key=lambda r: (r["fantasy_pts"] or 0), reverse=True)

# Take the single best game
top = best[:1]

# Split the score into its categories
print("Where the points came from")
print("\t".join(["fantasy_pts", "passing_yds_fpts", "passing_td_fpts", "rushing_yds_fpts", "rushing_td_fpts", "receiving_yds_fpts", "reception_fpts"]))
for row in top:
    print("\t".join([_cell(row["fantasy_pts"]), _cell(row["passing_yds_fpts"]), _cell(row["passing_td_fpts"]), _cell(row["rushing_yds_fpts"]), _cell(row["rushing_td_fpts"]), _cell(row["receiving_yds_fpts"]), _cell(row["reception_fpts"])]))

# Compare against the raw box score
print("The raw stats behind those points")
print("\t".join(["passing_yds", "passing_tds", "rushing_yds", "rushing_tds", "receptions", "receiving_yds", "receiving_tds"]))
for row in top:
    print("\t".join([_cell(row["passing_yds"]), _cell(row["passing_tds"]), _cell(row["rushing_yds"]), _cell(row["rushing_tds"]), _cell(row["receptions"]), _cell(row["receiving_yds"]), _cell(row["receiving_tds"])]))
