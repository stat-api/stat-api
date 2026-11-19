// Rank a DFS slate by salary and value
// Generated from schema/api/examples/dfs-slate-to-salaries.yml — do not edit.

import { type DFSPlayerStat, StatApi } from '@stat-api/client';

const api = new StatApi(); // reads STAT_API_KEY from the environment

// Page the slate's scoring rows
const stats: DFSPlayerStat[] = [];
for await (const row of api.dfs.player_stats.iter({ slate_id: 129036 })) stats.push(row);

// Drop the players who did not play
const scored = stats.filter((row) => row.fantasy_pts !== null && row.fantasy_pts !== undefined);

// Drop minimum-salary players
const playable = scored.filter((row) => row.salary >= 4000);

// Compute points per $1,000 of salary
const board = playable
  .map((row) => ({
    player_id: row.player_id,
    position: row.position,
    salary: row.salary,
    points: row.fantasy_pts ?? 0,
    value: ((row.fantasy_pts ?? 0) / row.salary) * 1000,
  }))
  .sort((a, b) => b.value - a.value)
  .slice(0, 10);
console.log(['player_id', 'position', 'salary', 'points', 'per $1k'].join('\t'));
for (const r of board) {
  console.log([r.player_id, r.position, r.salary, r.points, r.value.toFixed(2)].join('\t'));
}
