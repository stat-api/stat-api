// Find the highest-scoring games on a slate — DFS
// Generated from schema/api/examples/dfs-shootout-games.yml — do not edit.

import { StatApi } from '@stat-api/client';

const api = new StatApi(); // reads STAT_API_KEY from the environment

// Read every game in the slate's pool
const games = (await api.dfs.game_stats.list({ slate_id: 129036, limit: 50 })).game_stats;

// Keep the games that finished
const played = games.filter((row) => row.total_fpts !== null && row.total_fpts !== undefined);

// Rank by total fantasy points
const best = [...played].sort((a, b) => (b.total_fpts ?? 0) - (a.total_fpts ?? 0));

// Take the five biggest games
const top = best.slice(0, 5);

// Print the game board
console.log('Highest-scoring games on the slate');
console.log(
  ['game_id', 'total_fpts', 'home_fpts', 'away_fpts', 'home_pts', 'away_pts', 'player_count'].join(
    '\t',
  ),
);
for (const row of top) {
  console.log(
    [
      String(row.game_id ?? ''),
      String(row.total_fpts ?? ''),
      String(row.home_fpts ?? ''),
      String(row.away_fpts ?? ''),
      String(row.home_pts ?? ''),
      String(row.away_pts ?? ''),
      String(row.player_count ?? ''),
    ].join('\t'),
  );
}
