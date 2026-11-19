# Re-select SIM portfolios in <50ms via cached pool_id — DFS
# Generated from schema/api/examples/dfs-sim-pool-reselect.yml — do not edit.
from statapi import StatApi

api = StatApi()  # reads STAT_API_KEY from the environment

# Build baseline SIM candidate pool
baselinePool = api.dfs.slates.sim_lineups(1126128, {"contest_id": 91632452, "lineup_count": 5, "sim_game_runs": 200, "sim_seed": 42, "diversity_weight": 0.15, "max_player_exposure_pct": 60}, wait=True)

# Re-select portfolio from cached pool_id + Pareto frontier
reselected_pool_id = baselinePool["sim"]["versions"].get("pool_id")
if not reselected_pool_id:
    raise RuntimeError("missing sim.versions.pool_id")
reselected = api.dfs.slates.sim_select_pool(1126128, {"pool_id": reselected_pool_id, "lineup_count": 5, "diversity_weight": 0.35, "min_unique_players": 2, "max_player_exposure_pct": 60, "include_diversity_frontier": True})

# Print baseline vs reselected comparison and frontier
frontier = reselected.get("frontier") or []
d = reselected["diagnostics"]
bp = baselinePool["portfolio"]
rp = reselected["portfolio"]
print(f"Pool total_pool={d['total_pool_lineups']} active={d['active_pool_lineups']}")
print(f"Baseline lineups={bp['lineup_count']} ev={bp['payout']['ev_dollars']:.2f} diversity={bp['diversity']['composite_diversity_score']:.2f}")
print(f"Reselected lineups={rp['lineup_count']} ev={rp['payout']['ev_dollars']:.2f} diversity={rp['diversity']['composite_diversity_score']:.2f} frontier={len(frontier)}")
for fp in frontier[:4]:
    print(f"Frontier weight={fp['diversity_weight']:.2f} | ev={fp['ev_dollars']:.2f} roi_pct={fp['roi_pct']:.2f} diversity={fp['composite_diversity_score']:.2f}")
