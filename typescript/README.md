# @stat-api/client

Official TypeScript / JavaScript client for the [stat-api](https://stat-api.com) sports and
prediction-market data API. Zero runtime dependencies; ships dual ESM + CommonJS
builds with full type declarations.

> Generated from the stat-api schema — do not hand-edit. Version 0.1.0.

## Install

```sh
npm install @stat-api/client
```

## Authenticate

Create a key at [https://stat-api.com](https://stat-api.com) and expose it as
`STAT_API_KEY`. The client reads it automatically:

```ts
import { StatApi } from '@stat-api/client';

const api = new StatApi(); // apiKey ← process.env.STAT_API_KEY
// or: new StatApi({ apiKey: '...', baseUrl: 'https://api.stat-api.com' });
```

## List, get, and page

```ts
// A single bounded page (the list envelope):
const { teams } = await api.nfl.teams.list({ limit: 10 });

// One row by primary key:
const team = await api.nfl.teams.get(1);

// Walk every page automatically (non-filter-gated tables):
for await (const player of api.nba.players.iter()) {
  console.log(player.full_name);
}
```

## Typed errors

Every non-2xx response throws a typed subclass of `StatApiError`:

```ts
import { StatApi, QuotaExceededError, AuthenticationError } from '@stat-api/client';

try {
  await api.nba.players.list();
} catch (err) {
  if (err instanceof QuotaExceededError) {
    console.error(`quota exhausted; resets at ${err.resetsAt}`);
  } else if (err instanceof AuthenticationError) {
    console.error('bad or missing STAT_API_KEY');
  } else {
    throw err;
  }
}
```

`AuthenticationError` (401), `PlanRequiredError` (402), `ValidationError` (400,
covers the required-filter-set envelope), `NotFoundError` (404), and
`QuotaExceededError` (429) each carry the raw server `body` and request `path`.

## Quota

Responses carry `X-Quota-*` headers; on a 429 the `QuotaExceededError` exposes
`limit`, `used`, `resetsAt`, and `upgradeUrl`. GET requests retry transient
network failures and 5xx responses twice; 4xx (including 429) never retry.

## Links

- API docs: https://stat-api.com/docs
- Source: https://github.com/stat-api/stat-api/tree/main/typescript
- Issues: https://github.com/stat-api/stat-api/issues

## License

MIT
