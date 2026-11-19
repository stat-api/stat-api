// Find the main slate for a day — DFS
// Generated from schema/api/examples/dfs-main-slate.yml — do not edit.

import { StatApi } from '@stat-api/client';

const api = new StatApi(); // reads STAT_API_KEY from the environment

// List one operator's slates for one day
const days = (await api.dfs.slates.list({ operator_id: 1, date: '2025-11-30', league: 'nfl' }))
  .slates;

// Keep the main slate
const mains = days.filter((row) => row.main === true);

// Read the slate id
console.log('Main slate');
console.log(['id', 'name', 'date', 'start_time'].join('\t'));
for (const row of mains) {
  console.log(
    [
      String(row.id ?? ''),
      String(row.name ?? ''),
      String(row.date ?? ''),
      String(row.start_time ?? ''),
    ].join('\t'),
  );
}
