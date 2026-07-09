# Quota

stat-api meters **records per month** — every row returned by every request
counts against your plan, including repeated reads of the same data. Plans
and current limits: [stat-api.com](https://stat-api.com).

## Quota headers

Every response reports your live quota state:

| Header | Meaning |
| --- | --- |
| `X-Quota-Limit` | Records included in your plan per month |
| `X-Quota-Used` | Records consumed this period |
| `X-Quota-Remaining` | Records left this period |

The SDKs surface these on every list response (e.g. `page.quota` /
`page.Quota`), so applications can self-throttle before hitting the wall:

```ts
const page = await api.mlb.games.list({ limit: 100 });
console.log(page.quota); // { limit, used, remaining }
```

## Exhaustion — 429

When the quota is exhausted, requests return `429` with the reset time and an
upgrade link:

```json
{
  "error": "quota_exceeded",
  "limit": 50000,
  "used": 50000,
  "resets_at": "2026-08-01T00:00:00Z",
  "upgrade_url": "https://stat-api.com"
}
```

SDKs raise `QuotaExceededError` carrying those fields and **never retry** a
`429` — the quota is monthly, so retrying cannot succeed and only burns
requests. Handle it by backing off until `resets_at` or upgrading.

## Budgeting tips

- Ask for what you need: `limit` caps rows per response, and every row counts.
- Filter server-side (season, team, game) instead of paging everything and
  filtering client-side.
- Cache aggressively on your side — cache hits on stat-api's side still count
  as records served.
- Watch `X-Quota-Remaining` and degrade gracefully (the demo dashboard halves
  its polling cadence below a remaining-quota floor).
