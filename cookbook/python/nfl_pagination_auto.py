# Auto-paginate every NFL team
# Generated from schema/api/examples/pagination-auto.yml — do not edit.
from statapi import StatApi

api = StatApi()  # reads STAT_API_KEY from the environment

# Walk every page of teams
teams = list(api.nfl.teams.iter())

# Report how many rows the iterator collected
print(f"fetched {len(teams)} teams")
