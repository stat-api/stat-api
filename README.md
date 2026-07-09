# stat-api

One API key for NBA, NFL, MLB, NHL, and PGA Tour data — plus DFS
salaries/projections and prediction-market tape from Kalshi and Polymarket.
177 public tables over REST, GraphQL, and MCP.

- **Sign up, keys, and interactive docs:** [stat-api.com](https://stat-api.com)
- **API base:** `https://api.stat-api.com`
- **MCP server:** `https://api.stat-api.com/mcp` ([stat-api/mcp](https://github.com/stat-api/mcp))

## Quickstart

```sh
curl -sS --compressed \
  -H "Authorization: Bearer $STAT_API_KEY" \
  "https://api.stat-api.com/api/v1/nba/teams?limit=3"
```

Every list endpoint returns the same envelope — rows keyed by table name plus
keyset-pagination fields:

```json
{ "teams": [ { "id": 1, "...": "..." } ], "limit": 3, "next_from_id": 4 }
```

## Official SDKs

Five clients, all generated from one schema, all with the same semantics:
zero runtime dependencies, env-var zero config (`STAT_API_KEY`), typed errors,
auto-paginating iterators, and quota surfacing. Versions move in lockstep.

| Language | Package | Install | Repo |
| --- | --- | --- | --- |
| TypeScript / JavaScript | npm [`@stat-api/client`](https://www.npmjs.com/package/@stat-api/client) | `npm install @stat-api/client` | [statapi-js](https://github.com/stat-api/statapi-js) |
| Python | PyPI `statapi` | `pip install statapi` | [statapi-python](https://github.com/stat-api/statapi-python) |
| Go | `github.com/stat-api/statapi-go` | `go get github.com/stat-api/statapi-go@latest` | [statapi-go](https://github.com/stat-api/statapi-go) |
| Java | JitPack `com.github.stat-api:statapi-java` | see repo README | [statapi-java](https://github.com/stat-api/statapi-java) |
| C# / .NET | source install (NuGet deferred) | `<ProjectReference>` to `StatApi.csproj` | [statapi-dotnet](https://github.com/stat-api/statapi-dotnet) |

> **v0.1.0 is rolling out.** Repo and registry links above go live as each
> artifact publishes; anything that 404s today lands with the current release.

Thirty seconds to first data (TypeScript):

```ts
import { StatApi } from '@stat-api/client';

const api = new StatApi(); // apiKey ← process.env.STAT_API_KEY

const { teams } = await api.nfl.teams.list({ limit: 10 }); // one page
const team = await api.nfl.teams.get(1);                   // one row by id

for await (const player of api.nba.players.iter()) {       // every page
  console.log(player.full_name);
}
```

## Cookbook

[stat-api/cookbook](https://github.com/stat-api/cookbook) — 37 runnable
examples × 5 languages, generated against the same published SDK surface and
organized in tiers from *hello, stat-api* through pagination, error handling,
required filter sets, box scores, season leaders, DFS slates, Kalshi market
joins, and rolling averages.

## Demo applications

Real applications consuming the published SDKs like any customer would:

| Demo | Stack | Repo |
| --- | --- | --- |
| Live games dashboard ([demos.stat-api.com](https://demos.stat-api.com)) | TypeScript · Bun + Hono + React, SSE fan-out | [demo-dashboard](https://github.com/stat-api/demo-dashboard) |
| Analytics notebooks | Python · Jupyter, executed outputs committed | [demo-notebooks](https://github.com/stat-api/demo-notebooks) |
| `sports` terminal CLI | Go · stdlib only | [demo-cli](https://github.com/stat-api/demo-cli) |
| Discord bot | TypeScript · discord.js v14 | [demo-discord-bot](https://github.com/stat-api/demo-discord-bot) |

## Data coverage

| Group | Tables | Highlights |
| --- | --- | --- |
| `nfl` | 42 | games, play-by-play, drives, NGS/PFR advanced stats, depth charts, contracts, combine, weather |
| `mlb` | 38 | games, pitch-by-pitch, at-bats, batter/pitcher splits, park factors, umpires, starting lineups |
| `nba` | 29 | games, play-by-play, possessions, lineup stints, standings, referees, props |
| `nhl` | 22 | games, skater/goalie stats, line combinations, scoring plays *(coming soon)* |
| `pga` | 15 | tournaments, leaderboards, scorecards, shot-level TOURCAST data, strokes gained |
| `dfs` | 13 | slates, salaries, projections, contests, ownership |
| `kalshi` | 7 | series, events, markets, candles, public trades |
| `polymarket` | 10 | events, markets, order-book snapshots, price histories, trades |
| `reference` | 1 | cross-domain operators |

## Core concepts

| Guide | Covers |
| --- | --- |
| [Authentication](docs/authentication.md) | API keys, the `Authorization` header, environment variables |
| [Pagination](docs/pagination.md) | Keyset paging with `limit`/`from_id`, ordering, required filter sets |
| [Errors](docs/errors.md) | Status codes, error body shape, SDK retry policy |
| [Quota](docs/quota.md) | Record metering, `X-Quota-*` headers, 429 behavior, plans |

## License

SDKs, cookbook, and demos are [MIT licensed](LICENSE). API usage is governed by
the [stat-api terms](https://stat-api.com).
