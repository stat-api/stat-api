# Build the NFL standings
# Generated from schema/api/examples/team-standings.yml — do not edit.
from statapi import StatApi

api = StatApi()  # reads STAT_API_KEY from the environment

def _cell(value: object) -> str:
    return "" if value is None else str(value)

# Resolve the current season
season = max(api.nfl.seasons.list(limit=200).rows, key=lambda s: s["start_year"])["id"]

# Page every team's season record
rows = list(api.nfl.season_team_stats.iter(season_id=season))

# Order by wins, best first
standings = sorted(rows, key=lambda r: r["wins"], reverse=True)

# Take the top ten
top = standings[:10]

# Print the standings
print("NFL standings — top 10 by wins")
print("\t".join(["team_id", "wins", "losses"]))
for row in top:
    print("\t".join([_cell(row["team_id"]), _cell(row["wins"]), _cell(row["losses"])]))
