// Build a PGA golfer's scorecard
// Generated from schema/api/examples/scorecard.yml — do not edit.

import { StatApi, type PGAPlayer } from '@stat-api/client'

const api = new StatApi() // reads STAT_API_KEY from the environment

// Pick a golfer
const players = (await api.pga.players.list({ limit: 1 })).players

// Index the golfer by id for name lookups
const player_by_id = new Map<number, PGAPlayer>()
for (const row of players) player_by_id.set(row.id, row)

// Pull the hole-by-hole cards
const holes = (await api.pga.player_holes.list({ player_id: players[0].id })).player_holes

// Render the scorecard
console.log(["full_name", "round_number", "hole_number", "to_par"].join('\t'))
for (const row of holes) {
  console.log([String(player_by_id.get(row.player_id)?.full_name ?? row.player_id), String(row.round_number ?? ''), String(row.hole_number ?? ''), String(row.to_par ?? '')].join('\t'))
}
