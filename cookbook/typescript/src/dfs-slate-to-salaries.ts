// Rank a DFS slate by salary and value
// Generated from schema/api/examples/slate-to-salaries.yml — do not edit.

import { StatApi, type DFSSlatePlayer } from '@stat-api/client'

const api = new StatApi() // reads STAT_API_KEY from the environment

// Page the slate's players
const players: DFSSlatePlayer[] = []
for await (const row of api.dfs.slate_players.iter({ slate_id: 91396 })) players.push(row)

// Rank by salary, highest first
const bysalary = [...players].sort((a, b) => b.salary - a.salary)

// Take the ten priciest players
const top = bysalary.slice(0, 10)

// Fetch the top player's projection
const proj = (await api.dfs.slate_player_projections.list({ slate_player_id: top[0].id })).slate_player_projections

// Compute projected points per $1000 of salary
if (proj.length > 0 && top.length > 0) {
  const value = (proj[0].projection / top[0].salary) * 1000
  const who = top[0].display_name ?? String(top[0].id)
  console.log(`value(${who}) = ${value.toFixed(2)} projected pts per $1000`)
}

// Print the salary board
console.log("Highest-salaried players on the slate")
console.log(["display_name", "position", "salary"].join('\t'))
for (const row of top) {
  console.log([String(row.display_name ?? ''), String(row.position ?? ''), String(row.salary ?? '')].join('\t'))
}
