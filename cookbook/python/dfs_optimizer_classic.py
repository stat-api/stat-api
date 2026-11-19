# Build a diversified NFL Classic DFS lineup portfolio
# Generated from schema/api/examples/dfs-optimizer-classic.yml — do not edit.
from statapi import StatApi

api = StatApi()  # reads STAT_API_KEY from the environment

# Optimize 5 diversified NFL Classic lineups
optRes = api.dfs.slates.optimizer_lineups(1126128, {"lineup_count": 5, "lineup_min_salary": 48500, "min_unique_players": 2, "max_player_exposure_pct": 60})

# Print portfolio metrics and ranked lineups
print(f"Slate {optRes['slate']['id']} ({optRes['slate']['league']}/{optRes['slate']['operator']}) | lineups={optRes['portfolio']['lineup_count']} unique={optRes['portfolio']['unique_players']} mean_pts={optRes['portfolio']['mean_projected_points']:.2f}")
for l in optRes["lineups"][:5]:
    print(f"Lineup #{l['rank']} | salary={l['salary']} pts={l['projected_points']:.2f} stack={l['stack']['summary']}")
