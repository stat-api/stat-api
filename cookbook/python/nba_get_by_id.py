# Fetch one NBA team by id
# Generated from schema/api/examples/get-by-id.yml — do not edit.
from statapi import StatApi
import json

api = StatApi()  # reads STAT_API_KEY from the environment

# List one team to borrow an id
teams = api.nba.teams.list(limit=1).rows

# Fetch that team by id
team = api.nba.teams.get(teams[0]["id"])

# Inspect the row
print(json.dumps(team, indent=2, default=str))
