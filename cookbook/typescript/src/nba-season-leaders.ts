// Rank NBA season leaders
// Generated from schema/api/examples/season-leaders.yml — do not edit.

import { StatApi, type NBASeasonPlayerStat } from '@stat-api/client'

const api = new StatApi() // reads STAT_API_KEY from the environment

// Resolve the current season
const season = (await api.nba.seasons.list({ limit: 200 })).seasons
  .reduce((cur, s) => (s.start_year > cur.start_year ? s : cur)).id

// Page through the whole season
const rows: NBASeasonPlayerStat[] = []
for await (const row of api.nba.season_player_stats.iter({ season_id: season })) rows.push(row)

// Rank by points
const ranked = [...rows].sort((a, b) => b.pts - a.pts)

// Take the top ten
const leaders = ranked.slice(0, 10)

// Print the leaderboard
console.log("NBA points leaders")
console.log(["player_id", "pts"].join('\t'))
for (const row of leaders) {
  console.log([String(row.player_id ?? ''), String(row.pts ?? '')].join('\t'))
}
