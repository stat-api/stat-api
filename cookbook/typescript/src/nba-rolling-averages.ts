// Rolling averages over an NBA game log
// Generated from schema/api/examples/rolling-averages.yml — do not edit.

import { StatApi, type NBAGamePlayerStat } from '@stat-api/client'

const api = new StatApi() // reads STAT_API_KEY from the environment

// Resolve the current season
const season = (await api.nba.seasons.list({ limit: 200 })).seasons
  .reduce((cur, s) => (s.start_year > cur.start_year ? s : cur)).id

// Grab one game
const games = (await api.nba.games.list({ season_id: season, limit: 1 })).games

// Borrow a player from that game's box score
const seed = (await api.nba.game_player_stats.list({ game_id: games[0].id, limit: 1 })).game_player_stats

// Page that player's full game log
const gamelog: NBAGamePlayerStat[] = []
for await (const row of api.nba.game_player_stats.iter({ player_id: seed[0].player_id })) gamelog.push(row)

// Oldest game first
const chron = [...gamelog].sort((a, b) => a.game_date - b.game_date)

// Compute a 5-game trailing average of points
const window = 5
for (let i = 0; i < chron.length; i++) {
  const start = Math.max(0, i - window + 1)
  const span = chron.slice(start, i + 1)
  const avg = span.reduce((sum, r) => sum + r.pts, 0) / span.length
  console.log(`game ${i + 1}: pts=${chron[i].pts}, ${window}-game avg=${avg.toFixed(1)}`)
}
