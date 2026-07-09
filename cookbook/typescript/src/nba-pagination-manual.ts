// Paginate by hand with from_id — NBA
// Generated from schema/api/examples/pagination-manual.yml — do not edit.

import { StatApi } from '@stat-api/client'

const api = new StatApi() // reads STAT_API_KEY from the environment

// Follow next_from_id until it is null
let fromId: number | undefined = undefined
let total = 0
let pageNum = 0
for (;;) {
  const page = await api.nba.teams.list({ limit: 100, from_id: fromId })
  pageNum += 1
  total += page.teams.length
  console.log(`page ${pageNum}: ${page.teams.length} rows, next cursor = ${page.next_from_id ?? 'none'}`)
  if (page.next_from_id === null) break
  fromId = page.next_from_id
}
console.log(`walked ${total} teams across ${pageNum} pages by hand`)
