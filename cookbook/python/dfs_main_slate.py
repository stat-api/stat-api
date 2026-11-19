# Find the main slate for a day — DFS
# Generated from schema/api/examples/dfs-main-slate.yml — do not edit.
from statapi import StatApi

api = StatApi()  # reads STAT_API_KEY from the environment

def _cell(value: object) -> str:
    return "" if value is None else str(value)

# List one operator's slates for one day
days = api.dfs.slates.list(operator_id=1, date="2025-11-30", league="nfl").rows

# Keep the main slate
mains = [r for r in days if r["main"]]

# Read the slate id
print("Main slate")
print("\t".join(["id", "name", "date", "start_time"]))
for row in mains:
    print("\t".join([_cell(row["id"]), _cell(row["name"]), _cell(row["date"]), _cell(row["start_time"])]))
