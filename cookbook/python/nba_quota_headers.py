# Read your quota off a response — NBA
# Generated from schema/api/examples/quota-headers.yml — do not edit.
from statapi import StatApi

api = StatApi()  # reads STAT_API_KEY from the environment

# List a page and read its quota
page = api.nba.teams.list(limit=3)
if page.quota is not None:
    print(f"quota: {page.quota.remaining} of {page.quota.limit} records left this month")
else:
    print("no quota headers on this response")
