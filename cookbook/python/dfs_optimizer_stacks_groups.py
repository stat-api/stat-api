# Enforce correlated QB stacks, bring-backs, and player groups — DFS
# Generated from schema/api/examples/dfs-optimizer-stacks-groups.yml — do not edit.
from statapi import StatApi

api = StatApi()  # reads STAT_API_KEY from the environment

# Optimize 5 stacked lineups with group constraints
stackedRes = api.dfs.slates.optimizer_lineups(1126128, {"lineup_count": 5, "lineup_allow_defense_vs_offense": False, "lineup_stacks": [{"positions": ["QB", "WR"], "bring_back_count": 1}], "player_groups": [{"slate_player_ids": [44186556, 44186570], "max_players": 1}], "max_player_exposure_pct": 60})

# Print stacked lineup summary
print(f"Slate {stackedRes['slate']['id']} ({stackedRes['slate']['league']}/{stackedRes['slate']['operator']}) | lineups={stackedRes['portfolio']['lineup_count']} unique={stackedRes['portfolio']['unique_players']} mean_pts={stackedRes['portfolio']['mean_projected_points']:.2f}")
for l in stackedRes["lineups"][:5]:
    print(f"Lineup #{l['rank']} | salary={l['salary']} pts={l['projected_points']:.2f} stack={l['stack']['summary']}")
