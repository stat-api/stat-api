// Read an NBA player's game log
// Generated from schema/api/examples/player-game-log.yml — do not edit.

import { StatApi, type NBAGame, type NBAGamePlayerStat } from '@stat-api/client'

const api = new StatApi() // reads STAT_API_KEY from the environment

// Resolve the current season
const season = (await api.nba.seasons.list({ limit: 200 })).seasons
  .reduce((cur, s) => (s.start_year > cur.start_year ? s : cur)).id

// Page the season's games
const games: NBAGame[] = []
for await (const row of api.nba.games.iter({ season_id: season })) games.push(row)

// Index games by id
const game_by_id = new Map<number, NBAGame>()
for (const row of games) game_by_id.set(row.id, row)

// Borrow a player from one game's box score
const seed = (await api.nba.game_player_stats.list({ game_id: games[0].id, limit: 1 })).game_player_stats

// Page that player's game log
const gamelog: NBAGamePlayerStat[] = []
for await (const row of api.nba.game_player_stats.iter({ player_id: seed[0].player_id })) gamelog.push(row)

// Print the game log
console.log("NBA game log (points)")
console.log(["game_time", "pts"].join('\t'))
for (const row of gamelog) {
  console.log([String(game_by_id.get(row.game_id)?.game_time ?? row.game_id), String(row.pts ?? '')].join('\t'))
}
