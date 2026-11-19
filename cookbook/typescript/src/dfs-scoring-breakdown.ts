// See where a player's fantasy points came from — DFS
// Generated from schema/api/examples/dfs-scoring-breakdown.yml — do not edit.

import { StatApi } from '@stat-api/client';

const api = new StatApi(); // reads STAT_API_KEY from the environment

// Read one player's slate history
const rows = (await api.dfs.player_stats.list({ player_id: 301, limit: 200 })).player_stats;

// Keep the games that were played
const played = rows.filter((row) => row.fantasy_pts !== null && row.fantasy_pts !== undefined);

// Best game first
const best = [...played].sort((a, b) => (b.fantasy_pts ?? 0) - (a.fantasy_pts ?? 0));

// Take the single best game
const top = best.slice(0, 1);

// Split the score into its categories
console.log('Where the points came from');
console.log(
  [
    'fantasy_pts',
    'passing_yds_fpts',
    'passing_td_fpts',
    'rushing_yds_fpts',
    'rushing_td_fpts',
    'receiving_yds_fpts',
    'reception_fpts',
  ].join('\t'),
);
for (const row of top) {
  console.log(
    [
      String(row.fantasy_pts ?? ''),
      String(row.passing_yds_fpts ?? ''),
      String(row.passing_td_fpts ?? ''),
      String(row.rushing_yds_fpts ?? ''),
      String(row.rushing_td_fpts ?? ''),
      String(row.receiving_yds_fpts ?? ''),
      String(row.reception_fpts ?? ''),
    ].join('\t'),
  );
}

// Compare against the raw box score
console.log('The raw stats behind those points');
console.log(
  [
    'passing_yds',
    'passing_tds',
    'rushing_yds',
    'rushing_tds',
    'receptions',
    'receiving_yds',
    'receiving_tds',
  ].join('\t'),
);
for (const row of top) {
  console.log(
    [
      String(row.passing_yds ?? ''),
      String(row.passing_tds ?? ''),
      String(row.rushing_yds ?? ''),
      String(row.rushing_tds ?? ''),
      String(row.receptions ?? ''),
      String(row.receiving_yds ?? ''),
      String(row.receiving_tds ?? ''),
    ].join('\t'),
  );
}
