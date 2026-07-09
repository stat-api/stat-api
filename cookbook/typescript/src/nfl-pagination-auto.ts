// Auto-paginate every NFL team
// Generated from schema/api/examples/pagination-auto.yml — do not edit.

import { StatApi, type NFLTeam } from '@stat-api/client'

const api = new StatApi() // reads STAT_API_KEY from the environment

// Walk every page of teams
const teams: NFLTeam[] = []
for await (const row of api.nfl.teams.iter()) teams.push(row)

// Report how many rows the iterator collected
console.log(`fetched ${teams.length} teams`)
