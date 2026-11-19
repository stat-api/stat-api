# Compare NBA Common Random Numbers spread overrides
# Generated from schema/api/examples/nba-sim-overrides.yml — do not edit.
from statapi import StatApi

api = StatApi()  # reads STAT_API_KEY from the environment

# Run the market baseline simulation (seed 42)
baseline = api.sim.nba.run(game_id=6029936, n_draws=200, seed=42, include={"summary": True, "distributions": False, "scorebox": False, "pbp": False, "scenarios": False, "simulations": False})

# Re-simulate with an alternate home spread (-8.5, seed 42)
shifted = api.sim.nba.run(game_id=6029936, n_draws=200, seed=42, include={"summary": True, "distributions": False, "scorebox": False, "pbp": False, "scenarios": False, "simulations": False}, home_spread=-8.5)

# Print the CRN sensitivity delta
base_s = baseline["output"].get("summary")
over_s = shifted["output"].get("summary")
if base_s is None or over_s is None:
    raise RuntimeError("missing summary")
def _nz(v: float) -> float: return 0.0 if abs(v) < 0.005 else v
print(f"Baseline total={base_s['sim_total_mean']:.2f} home_win_pct={base_s['home_win_prob'] * 100:.2f}% spread_home={base_s['sim_spread_home_mean']:.2f}")
print(f"Override total={over_s['sim_total_mean']:.2f} home_win_pct={over_s['home_win_prob'] * 100:.2f}% spread_home={over_s['sim_spread_home_mean']:.2f}")
print(f"Delta total={_nz(over_s['sim_total_mean'] - base_s['sim_total_mean']):.2f} home_win_pp={_nz((over_s['home_win_prob'] - base_s['home_win_prob']) * 100):.2f}")
