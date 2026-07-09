# Rank NFL season leaders
# Generated from schema/api/examples/season-leaders.yml — do not edit.
from statapi import StatApi

api = StatApi()  # reads STAT_API_KEY from the environment

def _cell(value: object) -> str:
    return "" if value is None else str(value)

# Resolve the current season
season = max(api.nfl.seasons.list(limit=200).rows, key=lambda s: s["start_year"])["id"]

# Page through the whole season
rows = list(api.nfl.season_player_stats.iter(season_id=season))

# Rank by passing yards
ranked = sorted(rows, key=lambda r: r["passing_yds"], reverse=True)

# Take the top ten
leaders = ranked[:10]

# Print the leaderboard
print("NFL passing yards leaders")
print("\t".join(["player_id", "passing_yds"]))
for row in leaders:
    print("\t".join([_cell(row["player_id"]), _cell(row["passing_yds"])]))
