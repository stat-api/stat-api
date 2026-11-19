# Run an NBA possession simulation summary
# Generated from schema/api/examples/nba-sim-summary.yml — do not edit.
from statapi import StatApi

api = StatApi()  # reads STAT_API_KEY from the environment

# Discover the free NBA showcase game
weeks = api.nba.weeks(season=2026)

# Run the NBA possession simulator
simRes = api.sim.nba.run(game_id=weeks["free_game_id"], n_draws=200, seed=42, include={"summary": True, "distributions": False, "scorebox": False, "pbp": False, "scenarios": False, "simulations": False})

# Print calibrated summary and top NBA players
summary = simRes["output"].get("summary")
if summary is None:
    raise RuntimeError("missing output.summary")
print(f"Game {summary['game_id']} | season {summary['season']} | draws {summary['n_draws']}")
print(f"Total mean={summary['sim_total_mean']:.2f} sd={summary['sim_total_sd']:.2f} | home_win_pct={summary['home_win_prob'] * 100:.2f}% away_win_pct={summary['away_win_prob'] * 100:.2f}%")
print(f"Teams away={summary['away_team']['team_code']}:{summary['away_team']['points_mean']:.2f} home={summary['home_team']['team_code']}:{summary['home_team']['points_mean']:.2f}")
sorted_players = sorted(summary["players"], key=lambda p: (-p["dk_pts_mean"], p["player_id"]))
for p in sorted_players[:5]:
    print(f"Player {p['name']} ({p['team_code']}) | pts={p['pts_mean']:.2f} reb={p['reb_mean']:.2f} ast={p['ast_mean']:.2f} dk={p['dk_pts_mean']:.2f}")
