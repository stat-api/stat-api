# Read a slate's salaries — DFS
# Generated from schema/api/examples/dfs-salary-board.yml — do not edit.
from statapi import StatApi

api = StatApi()  # reads STAT_API_KEY from the environment

def _cell(value: object) -> str:
    return "" if value is None else str(value)

# Page the whole player pool
pool = list(api.dfs.slate_players.iter(slate_id=129036))

# Rank by salary, most expensive first
bysalary = sorted(pool, key=lambda r: r["salary"], reverse=True)

# Take the ten priciest players
top = bysalary[:10]

# Print the salary board
print("Highest-salaried players on the slate")
print("\t".join(["display_name", "position", "salary", "avg_points"]))
for row in top:
    print("\t".join([_cell(row["display_name"]), _cell(row["position"]), _cell(row["salary"]), _cell(row["avg_points"])]))
