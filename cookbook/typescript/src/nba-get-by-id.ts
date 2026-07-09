// Fetch one NBA team by id
// Generated from schema/api/examples/get-by-id.yml — do not edit.

import { StatApi } from '@stat-api/client'

const api = new StatApi() // reads STAT_API_KEY from the environment

// List one team to borrow an id
const teams = (await api.nba.teams.list({ limit: 1 })).teams

// Fetch that team by id
const team = await api.nba.teams.get(teams[0].id)

// Inspect the row
console.log(JSON.stringify(team, null, 2))
