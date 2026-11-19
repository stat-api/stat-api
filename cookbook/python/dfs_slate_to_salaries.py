# Rank a DFS slate by salary and value
# Generated from schema/api/examples/dfs-slate-to-salaries.yml — do not edit.
from statapi import StatApi

api = StatApi()  # reads STAT_API_KEY from the environment

# Page the slate's scoring rows
stats = list(api.dfs.player_stats.iter(slate_id=129036))

# Drop the players who did not play
scored = [r for r in stats if r["fantasy_pts"] is not None]

# Drop minimum-salary players
playable = [r for r in scored if r["salary"] >= 4000]

# Compute points per $1,000 of salary
board = sorted(
    (
        {
            "player_id": r["player_id"],
            "position": r["position"],
            "salary": r["salary"],
            "points": r["fantasy_pts"],
            "value": (r["fantasy_pts"] / r["salary"]) * 1000,
        }
        for r in playable
    ),
    key=lambda r: r["value"],
    reverse=True,
)[:10]
print("\t".join(["player_id", "position", "salary", "points", "per $1k"]))
for r in board:
    print(f"{r['player_id']}\t{r['position']}\t{r['salary']}\t{r['points']}\t{r['value']:.2f}")
