// Build the NBA standings
// Generated from schema/api/examples/team-standings.yml — do not edit.

import { StatApi, type NBASeasonTeamStat } from '@stat-api/client'

const api = new StatApi() // reads STAT_API_KEY from the environment

// Resolve the current season
const season = (await api.nba.seasons.list({ limit: 200 })).seasons
  .reduce((cur, s) => (s.start_year > cur.start_year ? s : cur)).id

// Page every team's season record
const rows: NBASeasonTeamStat[] = []
for await (const row of api.nba.season_team_stats.iter({ season_id: season })) rows.push(row)

// Order by wins, best first
const standings = [...rows].sort((a, b) => b.wins - a.wins)

// Take the top ten
const top = standings.slice(0, 10)

// Print the standings
console.log("NBA standings — top 10 by wins")
console.log(["team_id", "wins", "losses"].join('\t'))
for (const row of top) {
  console.log([String(row.team_id ?? ''), String(row.wins ?? ''), String(row.losses ?? '')].join('\t'))
}
