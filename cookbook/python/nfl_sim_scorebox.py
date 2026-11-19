# Meet six typical games — NFL
# Generated from schema/api/examples/sim-scorebox.yml — do not edit.
from statapi import StatApi

api = StatApi()  # reads STAT_API_KEY from the environment

# Ask for the scorebox
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
if scorebox is None:
    raise RuntimeError("the run returned no scorebox")

# Read the six archetypes
away = run["matchup"]["away_abbr"]
home = run["matchup"]["home_abbr"]
print(f"Six typical games of 2000 draws | scores are Q1-Q2-Q3-Q4-OT-Total | {away} at {home}")
for game in scorebox["archetypes"]:
    print(
        f"  {game['archetype'] or 'draw'} | draw {game['draw_idx']} | "
        f"{away} {'-'.join(str(n) for n in game['away_linescore'])} | "
        f"{home} {'-'.join(str(n) for n in game['home_linescore'])} | "
        f"home margin {game['final_margin_home']:+d}"
    )
