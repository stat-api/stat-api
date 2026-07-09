// Handle errors by type — NBA
// Generated from schema/api/examples/error-handling.yml — do not edit.

import { StatApi } from '@stat-api/client'

const api = new StatApi() // reads STAT_API_KEY from the environment

// Trigger a 404 and branch on the error
try {
  await api.nba.teams.get(999999999)
  console.log('unexpectedly found a team')
} catch (err) {
  // Every SDK error is a StatApiError subclass carrying .status and .body.
  // @stat-api/client also exports NotFoundError / AuthenticationError /
  // ValidationError / QuotaExceededError for instanceof checks.
  const e = err as { status?: number; body?: { message?: string } }
  if (e.status === 404) console.log('404 NotFoundError: no such team')
  else if (e.status === 401) console.log('401 AuthenticationError: bad or missing API key')
  else if (e.status === 400) console.log(`400 ValidationError: ${e.body?.message ?? 'bad request'}`)
  else if (e.status === 429) console.log('429 QuotaExceededError: monthly quota spent — never retried')
  else throw err
}
