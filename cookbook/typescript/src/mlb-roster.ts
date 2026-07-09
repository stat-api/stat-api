// List a MLB team's roster
// Generated from schema/api/examples/roster.yml — do not edit.

import { StatApi } from '@stat-api/client'

const api = new StatApi() // reads STAT_API_KEY from the environment

// Grab one team
const teams = (await api.mlb.teams.list({ limit: 1 })).teams

// List that team's players
const roster = (await api.mlb.players.list({ team_id: teams[0].id })).players

// Print the roster
console.log("MLB roster")
console.log(["full_name", "primary_position", "jersey"].join('\t'))
for (const row of roster) {
  console.log([String(row.full_name ?? ''), String(row.primary_position ?? ''), String(row.jersey ?? '')].join('\t'))
}
