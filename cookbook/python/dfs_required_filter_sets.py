# Satisfy a required filter set — DFS
# Generated from schema/api/examples/required-filter-sets.yml — do not edit.
from statapi import StatApi

api = StatApi()  # reads STAT_API_KEY from the environment

# See the 400, then satisfy the set
from statapi import ValidationError

# dfs.slates requires the [operator_id, date] set — a bare call is a 400.
try:
    api.dfs.slates.list(limit=5)
    print("unexpected: unfiltered slates call succeeded")
except ValidationError as e:
    print(f"rejected ({e.status}): {e.body}")

# Supply BOTH members of the set and the call is accepted.
slates = api.dfs.slates.list(operator_id=1, date="2026-07-02").rows
print(f"operator 1 ran {len(slates)} slates on 2026-07-02")
