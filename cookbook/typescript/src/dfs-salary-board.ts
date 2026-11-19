// Read a slate's salaries — DFS
// Generated from schema/api/examples/dfs-salary-board.yml — do not edit.

import { type DFSSlatePlayer, StatApi } from '@stat-api/client';

const api = new StatApi(); // reads STAT_API_KEY from the environment

// Page the whole player pool
const pool: DFSSlatePlayer[] = [];
for await (const row of api.dfs.slate_players.iter({ slate_id: 129036 })) pool.push(row);

// Rank by salary, most expensive first
const bysalary = [...pool].sort((a, b) => b.salary - a.salary);

// Take the ten priciest players
const top = bysalary.slice(0, 10);

// Print the salary board
console.log('Highest-salaried players on the slate');
console.log(['display_name', 'position', 'salary', 'avg_points'].join('\t'));
for (const row of top) {
  console.log(
    [
      String(row.display_name ?? ''),
      String(row.position ?? ''),
      String(row.salary ?? ''),
      String(row.avg_points ?? ''),
    ].join('\t'),
  );
}
