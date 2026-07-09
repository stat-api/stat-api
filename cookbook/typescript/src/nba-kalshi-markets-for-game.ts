// Find Kalshi markets for an NBA game
// Generated from schema/api/examples/kalshi-markets-for-game.yml — do not edit.

import { StatApi } from '@stat-api/client'

const api = new StatApi() // reads STAT_API_KEY from the environment

// Resolve the current season
const season = (await api.nba.seasons.list({ limit: 200 })).seasons
  .reduce((cur, s) => (s.start_year > cur.start_year ? s : cur)).id

// Grab one game
const games = (await api.nba.games.list({ season_id: season, limit: 1 })).games

// Join to Kalshi by (league_code, competition_id), then list markets
const game = games[0]
const events = (await api.kalshi.events.list({ competition_id: game.id, league_code: 'nba' })).events
if (events.length === 0) {
  console.log(`no Kalshi event linked to game ${game.id}`)
} else {
  const event = events[0]
  console.log(`event: ${event.title}`)
  const markets = (await api.kalshi.markets.list({ event_id: event.id })).markets
  console.log('ticker\ttitle\tstatus')
  for (const m of markets) {
    console.log(`${m.ticker}\t${m.title}\t${m.status}`)
  }
}
