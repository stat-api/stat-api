# Generate EV-maximized NFL GPP lineups with Prefer: wait — DFS
# Generated from schema/api/examples/dfs-sim-gpp-build.yml — do not edit.
from statapi import StatApi

api = StatApi()  # reads STAT_API_KEY from the environment

# Run synchronous NFL GPP contest simulation
simBuild = api.dfs.slates.sim_lineups(1126128, {"contest_id": 91632452, "lineup_count": 5, "sim_game_runs": 200, "sim_seed": 42, "diversity_weight": 0.25, "max_player_exposure_pct": 60}, wait=True)

# Print portfolio EV, ROI%, diversity, and lineup breakdown
p = simBuild["portfolio"]
print(f"Slate {simBuild['slate']['id']} | contest {simBuild['contest']['contest_id']} | lineups={p['lineup_count']} ev={p['payout']['ev_dollars']:.2f} roi_pct={p['payout']['roi_pct']:.2f} diversity={p['diversity']['composite_diversity_score']:.2f}")
for l in simBuild["lineups"][:5]:
    lp = l["payout"]
    print(f"SimLineup #{l['rank']} | salary={l['salary']} proj={l['projected_points']:.2f} ev={lp['ev_dollars']:.2f} roi_pct={lp['roi_pct']:.2f} win_pct={lp['win_pct']:.2f}")
