# Rank a DFS slate by salary and value
# Generated from schema/api/examples/slate-to-salaries.yml — do not edit.
from statapi import StatApi

api = StatApi()  # reads STAT_API_KEY from the environment

def _cell(value: object) -> str:
    return "" if value is None else str(value)

# Page the slate's players
players = list(api.dfs.slate_players.iter(slate_id=91396))

# Rank by salary, highest first
bysalary = sorted(players, key=lambda r: r["salary"], reverse=True)

# Take the ten priciest players
top = bysalary[:10]

# Fetch the top player's projection
proj = api.dfs.slate_player_projections.list(slate_player_id=top[0]["id"]).rows

# Compute projected points per $1000 of salary
if proj and top:
    value = (proj[0]["projection"] / top[0]["salary"]) * 1000
    who = top[0]["display_name"] or top[0]["id"]
    print(f"value({who}) = {value:.2f} projected pts per $1000")

# Print the salary board
print("Highest-salaried players on the slate")
print("\t".join(["display_name", "position", "salary"]))
for row in top:
    print("\t".join([_cell(row["display_name"]), _cell(row["position"]), _cell(row["salary"])]))
