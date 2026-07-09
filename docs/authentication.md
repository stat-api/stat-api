# Authentication

Every request to `https://api.stat-api.com` is authenticated with an API key
sent as a bearer token:

```sh
curl -sS --compressed \
  -H "Authorization: Bearer $STAT_API_KEY" \
  "https://api.stat-api.com/api/v1/nba/teams?limit=3"
```

## Getting a key

Create an account at [stat-api.com](https://stat-api.com) — every account
starts on the free tier with a working key. Keys look like
`sdb_xxxxxxxx_xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx` and are managed from your
account dashboard.

## Environment variables

All official SDKs are zero-config: they read the same two environment
variables, so code never embeds credentials.

| Variable | Meaning | Default |
| --- | --- | --- |
| `STAT_API_KEY` | Your API key | — (required) |
| `STAT_API_BASE_URL` | API base URL override | `https://api.stat-api.com` |

Constructor arguments override the environment in every SDK:

```ts
new StatApi({ apiKey: 'sdb_...' });          // TypeScript
```
```python
StatApi(api_key="sdb_...")                    # Python
```

If no key is available, SDK construction fails immediately with a message
pointing at `STAT_API_KEY` — you never get silent unauthenticated calls.

## Failure modes

| Status | Meaning |
| --- | --- |
| `401` | Key missing, malformed, or revoked. |
| `402` | The endpoint or row set requires a plan your key does not hold. |

See [Errors](errors.md) for the full error contract.
