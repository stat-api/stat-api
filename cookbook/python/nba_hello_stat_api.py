# Hello, NBA
# Generated from schema/api/examples/hello-stat-api.yml — do not edit.
from statapi import StatApi
import json

api = StatApi()  # reads STAT_API_KEY from the environment

# List a few teams
teams = api.nba.teams.list(limit=3).rows

# Print what came back
print(json.dumps(teams, indent=2, default=str))
