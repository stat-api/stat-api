# Run your first NFL game simulation
# Generated from schema/api/examples/sim-summary.yml — do not edit.
from statapi import StatApi

api = StatApi()  # reads STAT_API_KEY from the environment

# Find the free game
weeks = api.nfl.weeks(season=2026)
game_id = weeks["free_game_id"]
print(f"Season {weeks['season']} | free game {game_id} | {len(weeks['games'])} games")

# Run the game 5,000 times
run = api.sim.nfl.run(
    game_id=game_id,
    n_draws=5000,
    seed=42,
    include={
        "summary": True,
        "distributions": False,
        "scorebox": False,
        "pbp": False,
    },
)
summary = run["output"]["summary"]
if summary is None:
    raise RuntimeError("the run returned no summary")
print(
    f"Simulated total {summary['sim_total_mean']:.2f} (SD {summary['sim_total_sd']:.2f}) | "
    f"home win {summary['home_win_prob'] * 100:.1f}% | "
    f"home cover {summary['home_cover_prob'] * 100:.1f}% | "
    f"over {summary['over_prob'] * 100:.1f}%"
)
print(f"Box-score checks: {'passed' if summary['conservation_ok'] else 'failed'}")

# Rank the players
top = sorted(summary["players"], key=lambda p: p["rec_yd_mean"], reverse=True)[:5]
print("Top 5 players by mean receiving yards:")
for p in top:
    print(
        f"  Player {p['player_id']} ({p['position']}/{p['depth_role']}) | "
        f"mean {p['rec_yd_mean']:.1f} | median {p['rec_yd_p50']:.1f} | "
        f"90th percentile {p['rec_yd_p90']:.1f}"
    )
