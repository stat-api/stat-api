# Build a PGA golfer's scorecard
# Generated from schema/api/examples/scorecard.yml — do not edit.
from statapi import StatApi

api = StatApi()  # reads STAT_API_KEY from the environment

def _cell(value: object) -> str:
    return "" if value is None else str(value)

# Pick a golfer
players = api.pga.players.list(limit=1).rows

# Index the golfer by id for name lookups
player_by_id = {row["id"]: row for row in players}

# Pull the hole-by-hole cards
holes = api.pga.player_holes.list(player_id=players[0]["id"]).rows

# Render the scorecard
print("\t".join(["full_name", "round_number", "hole_number", "to_par"]))
for row in holes:
    print("\t".join([_cell((player_by_id.get(row["player_id"]) or {}).get("full_name", row["player_id"])), _cell(row["round_number"]), _cell(row["hole_number"]), _cell(row["to_par"])]))
