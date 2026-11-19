# Extract NBA empirical bell curves and quantiles
# Generated from schema/api/examples/nba-sim-distributions.yml — do not edit.
from statapi import StatApi

api = StatApi()  # reads STAT_API_KEY from the environment

# Run NBA SIM with empirical distributions enabled
distRes = api.sim.nba.run(game_id=6029936, n_draws=200, seed=42, include={"summary": True, "distributions": True, "scorebox": False, "pbp": False, "scenarios": False, "simulations": False})

# Print game and player quantile distributions
dist = distRes["output"].get("distributions")
sum_d = distRes["output"].get("summary")
if dist is None or sum_d is None:
    raise RuntimeError("missing distributions or summary")
tp = dist["total_points"]
sm = dist["spread_margin_home"]
print(f"Game {sum_d['game_id']} | total mean={tp['mean']:.2f} p10={tp['p10']:.2f} p50={tp['p50']:.2f} p90={tp['p90']:.2f}")
print(f"SpreadHome mean={sm['mean']:.2f} p10={sm['p10']:.2f} p50={sm['p50']:.2f} p90={sm['p90']:.2f}")
print(f"Teams {sum_d['away_team']['team_code']}={dist['away_points']['mean']:.2f} {sum_d['home_team']['team_code']}={dist['home_points']['mean']:.2f}")
sorted_curves = sorted(dist["players"], key=lambda p: (-p["dk_pts"]["mean"], p["player_id"]))
for p in sorted_curves[:5]:
    print(f"Dist {p['name']} ({p['team_code']}) | dk_p50={p['dk_pts']['p50']:.2f} dk_p90={p['dk_pts']['p90']:.2f} pts_p50={p['points']['p50']:.2f}")
