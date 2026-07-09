// Read your quota off a response — NBA
// Generated from schema/api/examples/quota-headers.yml — do not edit.

import { StatApi } from '@stat-api/client'

const api = new StatApi() // reads STAT_API_KEY from the environment

// List a page and read its quota
const page = await api.nba.teams.list({ limit: 3 })
if (page.quota) {
  console.log(`quota: ${page.quota.remaining} of ${page.quota.limit} records left this month`)
} else {
  console.log('no quota headers on this response')
}
