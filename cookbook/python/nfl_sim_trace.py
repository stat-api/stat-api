# Replay one simulated game — NFL
# Generated from schema/api/examples/sim-trace.yml — do not edit.
from statapi import StatApi

api = StatApi()  # reads STAT_API_KEY from the environment

# Pick a draw from a run
weeks = api.nfl.weeks(season=2026)
game_id = weeks["free_game_id"]
run = api.sim.nfl.run(
    game_id=game_id,
    n_draws=2000,
    seed=42,
    include={
        "summary": False,
        "distributions": False,
        "scorebox": True,
        "pbp": False,
        "scorebox_limit": 1,
    },
)
scorebox = run["output"]["scorebox"]
shootout = next((g for g in scorebox["archetypes"] if g["archetype"] == "shootout_p95"), None)
if shootout is None:
    raise RuntimeError("the run has no shootout game")
print(f"Replay draw {shootout['draw_idx']}, the 95th percentile shootout of 2000 draws")

# Replay the draw with run
replay = api.sim.nfl.run(
    game_id=game_id,
    n_draws=2000,
    seed=42,
    include={
        "summary": False,
        "distributions": False,
        "scorebox": False,
        "pbp": True,
        "pbp_draws": [shootout["draw_idx"]],
    },
)
pbp_list = replay["output"]["pbp"]
if not pbp_list:
    raise RuntimeError("the run returned no play-by-play trace")
traced = pbp_list[0]
away = run["matchup"]["away_abbr"]
home = run["matchup"]["home_abbr"]
print(
    f"{away} {traced['scorebox']['away_linescore'][5]} - {home} {traced['scorebox']['home_linescore'][5]} | "
    f"{len(traced['drives'])} drives | {len(traced['plays'])} plays"
)

# Read the drives and the plays
print("First 5 drives:")
for d in traced["drives"][:5]:
    team = home if d["posteam_is_home"] else away
    print(
        f"  {d['drive_number']}. {team} | Q{d['start_quarter']} {d['start_clock']} | "
        f"{d['plays']} plays, {d['net_yards']} yds | {d['result']} | "
        f"{away} {d['away_score_after']} - {home} {d['home_score_after']}"
    )
print("First 5 plays:")
for p in traced["plays"][:5]:
    print(f"  {p['clock_display']} | {p['down_distance_display']} | {p['yardline_display']} | {p['description']}")
