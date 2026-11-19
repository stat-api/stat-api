// See who actually scored on a slate — DFS
// Generated from schema/api/examples/dfs-slate-scorers.yml — do not edit.

import { type DFSPlayerStat, type DFSSlatePlayer, StatApi } from '@stat-api/client';

const api = new StatApi(); // reads STAT_API_KEY from the environment

// Page the player pool for names
const pool: DFSSlatePlayer[] = [];
for await (const row of api.dfs.slate_players.iter({ slate_id: 129036 })) pool.push(row);

// Index the pool by player id
const names = new Map<number, DFSSlatePlayer>();
for (const row of pool) names.set(row.player_id, row);

// Page the slate's scoring rows
const stats: DFSPlayerStat[] = [];
for await (const row of api.dfs.player_stats.iter({ slate_id: 129036 })) stats.push(row);

// Drop the players who did not play
const scored = stats.filter((row) => row.fantasy_pts !== null && row.fantasy_pts !== undefined);

// Rank by fantasy points
const best = [...scored].sort((a, b) => (b.fantasy_pts ?? 0) - (a.fantasy_pts ?? 0));

// Take the top fifteen
const top = best.slice(0, 15);

// Print the scoring board
console.log('Top scorers on the slate');
console.log(['display_name', 'position', 'salary', 'fantasy_pts'].join('\t'));
for (const row of top) {
  console.log(
    [
      String(names.get(row.player_id)?.display_name ?? row.player_id),
      String(row.position ?? ''),
      String(row.salary ?? ''),
      String(row.fantasy_pts ?? ''),
    ].join('\t'),
  );
}
