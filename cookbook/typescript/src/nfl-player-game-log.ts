// Read an NFL player's game log
// Generated from schema/api/examples/player-game-log.yml — do not edit.

import { StatApi, type NFLGame, type NFLGamePlayerStat } from '@stat-api/client'

const api = new StatApi() // reads STAT_API_KEY from the environment

// Resolve the current season
const season = (await api.nfl.seasons.list({ limit: 200 })).seasons
  .reduce((cur, s) => (s.start_year > cur.start_year ? s : cur)).id

// Page the season's games
const games: NFLGame[] = []
for await (const row of api.nfl.games.iter({ season_id: season })) games.push(row)

// Index games by id
const game_by_id = new Map<number, NFLGame>()
for (const row of games) game_by_id.set(row.id, row)

// Borrow a player from one game's box score
const seed = (await api.nfl.game_player_stats.list({ game_id: games[0].id, limit: 1 })).game_player_stats

// Page that player's game log
const gamelog: NFLGamePlayerStat[] = []
for await (const row of api.nfl.game_player_stats.iter({ player_id: seed[0].player_id })) gamelog.push(row)

// Print the game log
console.log("NFL game log (fantasy points)")
console.log(["game_time", "fantasy_pts"].join('\t'))
for (const row of gamelog) {
  console.log([String(game_by_id.get(row.game_id)?.game_time ?? row.game_id), String(row.fantasy_pts ?? '')].join('\t'))
}
