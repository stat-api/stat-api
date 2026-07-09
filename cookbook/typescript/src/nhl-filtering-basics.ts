// Filter NHL games by season
// Generated from schema/api/examples/filtering-basics.yml — do not edit.

import { StatApi } from '@stat-api/client'

const api = new StatApi() // reads STAT_API_KEY from the environment

// Resolve the current season
const season = (await api.nhl.seasons.list({ limit: 200 })).seasons
  .reduce((cur, s) => (s.start_year > cur.start_year ? s : cur)).id

// Filter games to that season
const games = (await api.nhl.games.list({ season_id: season, limit: 5 })).games

// Print the filtered games
console.log(JSON.stringify(games, null, 2))
