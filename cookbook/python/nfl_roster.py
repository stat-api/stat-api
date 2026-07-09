# List a NFL team's roster
# Generated from schema/api/examples/roster.yml — do not edit.
from statapi import StatApi

api = StatApi()  # reads STAT_API_KEY from the environment

def _cell(value: object) -> str:
    return "" if value is None else str(value)

# Grab one team
teams = api.nfl.teams.list(limit=1).rows

# List that team's players
roster = api.nfl.players.list(team_id=teams[0]["id"]).rows

# Print the roster
print("NFL roster")
print("\t".join(["full_name", "primary_position", "jersey"]))
for row in roster:
    print("\t".join([_cell(row["full_name"]), _cell(row["primary_position"]), _cell(row["jersey"])]))
