# Errors

Non-2xx responses carry a JSON body of the shape:

```json
{ "error": "machine_readable_code", "message": "human readable detail" }
```

Some errors carry extra fields — `400` includes the accepted filter sets,
`429` includes quota state (see below).

## Status codes

| Status | SDK error type | Meaning |
| --- | --- | --- |
| `400` | `ValidationError` | Request rejected — unknown filter, bad value, or a missing required filter set (body lists `accepted` combinations). |
| `401` | `AuthenticationError` | API key missing, malformed, or revoked. |
| `402` | `PlanRequiredError` | Endpoint or row set requires a higher plan. |
| `404` | `NotFoundError` | No row matched the requested id. |
| `429` | `QuotaExceededError` | Monthly record quota exhausted. Body carries `limit`, `used`, `resets_at`, `upgrade_url`. |
| `5xx` | `StatApiError` | Transient server failure — safe to retry GETs. |

## Typed errors in the SDKs

Every SDK maps these to a typed hierarchy under a common base
(`StatApiError`), so you catch the specific failure instead of switching on a
status number. The raw server body is always attached verbatim.

```ts
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

## Retry policy (built into every SDK)

- **GETs retry twice** on network failures, timeouts, and `5xx` — jittered
  backoff of ~250ms, then ~1s.
- **`4xx` is never retried** — including `429`: the quota is monthly, a retry
  cannot succeed, so it fails loud instead of burning requests.
- Default request timeout is 30s, constructor-overridable.
