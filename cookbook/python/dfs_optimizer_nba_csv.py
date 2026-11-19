# Export NBA Classic DFS lineups as ready-to-upload CSV
# Generated from schema/api/examples/dfs-optimizer-nba-csv.yml — do not edit.
from statapi import StatApi

api = StatApi()  # reads STAT_API_KEY from the environment

# Generate NBA Classic lineups in CSV format
csvText = api.dfs.slates.optimizer_lineups_csv(1243812, {"lineup_count": 3, "min_unique_players": 2})

# Preview exported CSV header and rows
csv_lines = [s.rstrip("\r") for s in csvText.split("\n") if s.rstrip("\r")]
print(f"CSV lines={len(csv_lines)}")
for line in csv_lines[:4]:
    print(line)
