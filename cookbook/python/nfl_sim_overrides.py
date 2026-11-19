# Ask a what-if question — NFL
# Generated from schema/api/examples/sim-overrides.yml — do not edit.
from statapi import StatApi

api = StatApi()  # reads STAT_API_KEY from the environment

# Run the baseline
weeks = api.nfl.weeks(season=2026)
game_id = weeks["free_game_id"]
include = {
    "summary": True,
    "distributions": False,
    "scorebox": False,
    "pbp": False,
}
baseline = api.sim.nfl.run(game_id=game_id, n_draws=5000, seed=42, include=include)

# Run the what-if with the same seed
scenario = api.sim.nfl.run(
    game_id=game_id,
    n_draws=5000,
    seed=42,
    include=include,
    home_spread=-6.5,
)

# Read the exact change
before = baseline["output"]["summary"]
after = scenario["output"]["summary"]
if before is None or after is None:
    raise RuntimeError("a run returned no summary")
home = baseline["matchup"]["home_abbr"]
away = baseline["matchup"]["away_abbr"]
print(f"What if {home} were a 6.5 point favorite? Seed 42 on both runs.")
print(
    f"Home win chance | {before['home_win_prob'] * 100:.1f}% -> {after['home_win_prob'] * 100:.1f}% "
    f"({(after['home_win_prob'] - before['home_win_prob']) * 100:+.1f} points)"
)
print(
    f"{home} points | {before['home_team']['points_mean']:.2f} -> {after['home_team']['points_mean']:.2f} "
    f"({after['home_team']['points_mean'] - before['home_team']['points_mean']:+.2f})"
)
print(
    f"{away} points | {before['away_team']['points_mean']:.2f} -> {after['away_team']['points_mean']:.2f} "
    f"({after['away_team']['points_mean'] - before['away_team']['points_mean']:+.2f})"
)
