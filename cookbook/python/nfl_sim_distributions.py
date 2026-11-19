# See the whole range of outcomes — NFL
# Generated from schema/api/examples/sim-distributions.yml — do not edit.
from statapi import StatApi

api = StatApi()  # reads STAT_API_KEY from the environment

# Ask for the distributions
weeks = api.nfl.weeks(season=2026)
game_id = weeks["free_game_id"]
run = api.sim.nfl.run(
    game_id=game_id,
    n_draws=5000,
    seed=42,
    include={
        "summary": False,
        "distributions": True,
        "scorebox": False,
        "pbp": False,
    },
)
dist = run["output"]["distributions"]
if dist is None:
    raise RuntimeError("the run returned no distributions")
total = dist["total_points"]
print(
    f"Game total | mean {total['mean']:.2f}, SD {total['sd']:.2f} | "
    f"10th {total['p10']:.2f}, median {total['p50']:.2f}, 90th {total['p90']:.2f}"
)
if total["vegas_line"] is not None and total["p_over_vegas"] is not None:
    print(
        f"Market total {total['vegas_line']:.1f} | "
        f"simulated chance of the over {total['p_over_vegas'] * 100:.1f}%"
    )

# Read the histogram
print("Share of draws by game total, bins with 2% or more:")
for b in total["bins"]:
    if b["prob"] < 0.02:
        continue
    print(
        f"  [{b['x_min']:6.2f}, {b['x_max']:6.2f}) "
        f"{b['prob'] * 100:5.2f}% {'#' * int(b['prob'] * 100)}"
    )

# Find a draw that you can replay
median = next((b for b in total["bins"] if b["x_min"] <= total["p50"] < b["x_max"]), None)
sample = median["sample_draw_idx"] if median else None
print(f"Median bin: sample draw {'none' if sample is None else sample}")

# Read the range of a player
star = max(
    (p for p in dist["players"] if p["rec_yd"] is not None),
    key=lambda p: p["rec_yd"]["mean"],
)
rec = star["rec_yd"]
assert rec is not None
print(
    f"Top receiver {star['player_id']} ({star['position']}) rec yds | "
    f"10th {rec['p10']:.2f}, median {rec['p50']:.2f}, 90th {rec['p90']:.2f} | skew {rec['skew']:.2f}"
)
