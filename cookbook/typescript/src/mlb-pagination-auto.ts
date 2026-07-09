// Auto-paginate every MLB team
// Generated from schema/api/examples/pagination-auto.yml — do not edit.

import { StatApi, type MLBTeam } from '@stat-api/client'

const api = new StatApi() // reads STAT_API_KEY from the environment

// Walk every page of teams
const teams: MLBTeam[] = []
for await (const row of api.mlb.teams.iter()) teams.push(row)

// Report how many rows the iterator collected
console.log(`fetched ${teams.length} teams`)
