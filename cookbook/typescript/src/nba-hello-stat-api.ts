// Hello, NBA
// Generated from schema/api/examples/hello-stat-api.yml — do not edit.

import { StatApi } from '@stat-api/client'

const api = new StatApi() // reads STAT_API_KEY from the environment

// List a few teams
const teams = (await api.nba.teams.list({ limit: 3 })).teams

// Print what came back
console.log(JSON.stringify(teams, null, 2))
