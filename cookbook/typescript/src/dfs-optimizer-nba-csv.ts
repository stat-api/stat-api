// Export NBA Classic DFS lineups as ready-to-upload CSV
// Generated from schema/api/examples/dfs-optimizer-nba-csv.yml — do not edit.

import { StatApi } from '@stat-api/client';

const api = new StatApi(); // reads STAT_API_KEY from the environment

// Generate NBA Classic lineups in CSV format
const csvText = await api.dfs.slates.optimizerLineupsCsv(1243812, {
  lineup_count: 3,
  min_unique_players: 2,
});

// Preview exported CSV header and rows
const csvLines = csvText
  .split('\n')
  .map((s) => s.replace(/\r$/, ''))
  .filter((s) => s.length > 0);
console.log(`CSV lines=${csvLines.length}`);
for (const line of csvLines.slice(0, 4)) console.log(line);
