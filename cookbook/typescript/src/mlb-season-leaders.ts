// Rank MLB season leaders
// Generated from schema/api/examples/season-leaders.yml — do not edit.

import { StatApi, type MLBSeasonPlayerStat } from '@stat-api/client'

const api = new StatApi() // reads STAT_API_KEY from the environment

// Resolve the current season
const season = (await api.mlb.seasons.list({ limit: 200 })).seasons
  .reduce((cur, s) => (s.start_year > cur.start_year ? s : cur)).id

// Page through the whole season
const rows: MLBSeasonPlayerStat[] = []
for await (const row of api.mlb.season_player_stats.iter({ season_id: season })) rows.push(row)

// Rank by home runs
const ranked = [...rows].sort((a, b) => b.home_runs - a.home_runs)

// Take the top ten
const leaders = ranked.slice(0, 10)

// Print the leaderboard
console.log("MLB home runs leaders")
console.log(["player_id", "home_runs"].join('\t'))
for (const row of leaders) {
  console.log([String(row.player_id ?? ''), String(row.home_runs ?? '')].join('\t'))
}
