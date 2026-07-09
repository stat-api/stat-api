# Read a PGA golfer's leaderboard finishes
# Generated from schema/api/examples/leaderboard.yml — do not edit.
from statapi import StatApi

api = StatApi()  # reads STAT_API_KEY from the environment

def _cell(value: object) -> str:
    return "" if value is None else str(value)

# Pick a golfer
players = api.pga.players.list(limit=1).rows

# Read that golfer's finishes
board = api.pga.leaderboards.list(player_id=players[0]["id"], limit=25).rows

# Best finishes first
ranked = sorted(board, key=lambda r: (r["rank"] or 0), reverse=False)

# Show the finishes
print("\t".join(["rank", "sg_total"]))
for row in ranked:
    print("\t".join([_cell(row["rank"]), _cell(row["sg_total"])]))
