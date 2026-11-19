// Rank teams for a stack — DFS
// Generated from schema/api/examples/dfs-team-stacks.yml — do not edit.

import { StatApi } from '@stat-api/client';

const api = new StatApi(); // reads STAT_API_KEY from the environment

// Read both sides of every game on the slate
const teams = (await api.dfs.team_stats.list({ slate_id: 129036, limit: 100 })).team_stats;

// Keep the teams that played
const played = teams.filter((row) => row.fantasy_pts !== null && row.fantasy_pts !== undefined);

// Rank by fantasy points produced
const best = [...played].sort((a, b) => (b.fantasy_pts ?? 0) - (a.fantasy_pts ?? 0));

// Take the eight best offenses
const top = best.slice(0, 8);

// Print the stack board
console.log('Most productive offenses on the slate');
console.log(
  [
    'team_id',
    'opponent_team_id',
    'is_home',
    'fantasy_pts',
    'pts',
    'total_yds',
    'player_count',
  ].join('\t'),
);
for (const row of top) {
  console.log(
    [
      String(row.team_id ?? ''),
      String(row.opponent_team_id ?? ''),
      String(row.is_home ?? ''),
      String(row.fantasy_pts ?? ''),
      String(row.pts ?? ''),
      String(row.total_yds ?? ''),
      String(row.player_count ?? ''),
    ].join('\t'),
  );
}
