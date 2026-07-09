// Read a PGA golfer's leaderboard finishes
// Generated from schema/api/examples/leaderboard.yml — do not edit.

import { StatApi } from '@stat-api/client'

const api = new StatApi() // reads STAT_API_KEY from the environment

// Pick a golfer
const players = (await api.pga.players.list({ limit: 1 })).players

// Read that golfer's finishes
const board = (await api.pga.leaderboards.list({ player_id: players[0].id, limit: 25 })).leaderboards

// Best finishes first
const ranked = [...board].sort((a, b) => (a.rank ?? 0) - (b.rank ?? 0))

// Show the finishes
console.log(["rank", "sg_total"].join('\t'))
for (const row of ranked) {
  console.log([String(row.rank ?? ''), String(row.sg_total ?? '')].join('\t'))
}
