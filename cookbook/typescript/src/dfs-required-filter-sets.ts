// Satisfy a required filter set — DFS
// Generated from schema/api/examples/required-filter-sets.yml — do not edit.

import { StatApi } from '@stat-api/client'

const api = new StatApi() // reads STAT_API_KEY from the environment

// See the 400, then satisfy the set
// dfs.slates requires the [operator_id, date] set — a bare call is a 400.
try {
  await api.dfs.slates.list({ limit: 5 })
  console.log('unexpected: unfiltered slates call succeeded')
} catch (err) {
  const e = err as { status?: number; body?: { message?: string } }
  console.log(`rejected (${e.status}): ${e.body?.message ?? 'missing required filter set'}`)
}
// Supply BOTH members of the set and the call is accepted.
const slates = (await api.dfs.slates.list({ operator_id: 1, date: '2026-07-02' })).slates
console.log(`operator 1 ran ${slates.length} slates on 2026-07-02`)
