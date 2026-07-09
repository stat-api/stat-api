// Rank NFL season leaders
// Generated from schema/api/examples/season-leaders.yml — do not edit.

import { StatApi, type NFLSeasonPlayerStat } from '@stat-api/client'

const api = new StatApi() // reads STAT_API_KEY from the environment

// Resolve the current season
const season = (await api.nfl.seasons.list({ limit: 200 })).seasons
  .reduce((cur, s) => (s.start_year > cur.start_year ? s : cur)).id

// Page through the whole season
const rows: NFLSeasonPlayerStat[] = []
for await (const row of api.nfl.season_player_stats.iter({ season_id: season })) rows.push(row)

// Rank by passing yards
const ranked = [...rows].sort((a, b) => b.passing_yds - a.passing_yds)

// Take the top ten
const leaders = ranked.slice(0, 10)

// Print the leaderboard
console.log("NFL passing yards leaders")
console.log(["player_id", "passing_yds"].join('\t'))
for (const row of leaders) {
  console.log([String(row.player_id ?? ''), String(row.passing_yds ?? '')].join('\t'))
}
