// Build an MLB box score
// Generated from schema/api/examples/box-score.yml — do not edit.

import { StatApi, type MLBGamePlayerBatterStat } from '@stat-api/client'

const api = new StatApi() // reads STAT_API_KEY from the environment

// Resolve the current season
const season = (await api.mlb.seasons.list({ limit: 200 })).seasons
  .reduce((cur, s) => (s.start_year > cur.start_year ? s : cur)).id

// Find a game
const games = (await api.mlb.games.list({ season_id: season, limit: 1 })).games

// Pull per-player stats for that game
const stats = (await api.mlb.game_player_batter_stats.list({ game_id: games[0].id })).game_player_batter_stats

// Split the stat lines into the two teams
const by_team = new Map<number, MLBGamePlayerBatterStat[]>()
for (const row of stats) {
  const bucket = by_team.get(row.team_id) ?? []
  bucket.push(row)
  by_team.set(row.team_id, bucket)
}

// Render both halves of the box score
console.log(JSON.stringify(Object.fromEntries(by_team), null, 2))
