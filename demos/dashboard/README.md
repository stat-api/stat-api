# stat-api live dashboard

**For web & product developers.**

- **Who it's for** — teams building live, data-driven web UIs on top of a metered public API.
- **What it teaches** — one poller → in-memory snapshot → **SSE fan-out**, so a thousand browsers
  cost the same quota as one; plus a **quota self-throttle** that halves polling cadence when
  `X-Quota-Remaining` runs low.
- **What stat-api data it shows off** — today's MLB / NBA / NFL slate: scores, box lines, sportsbook
  line movement, and a **market-implied win-probability** pulse (kalshi + sportsbook lines — *not* a
  proprietary model), all from the **poll-only** public REST API.

Built with **React 18 + Vite** and a **Bun + Hono** server, deployed at **demos.stat-api.com**.

This is a **dogfooding demo**: it consumes the same published SDK (`@stat-api/client`) and the same
quota-metered API that any customer would, using only public endpoints (no private routes, no
`game_moments`/WP tables). The interesting engineering is doing that *cheaply* — one poller feeds
every browser, so a thousand viewers cost the same quota as one.

## Architecture

```
              ┌───────────────────────────────────────────┐
              │  src/  (Bun + Hono, port 4400)             │
  stat-api ──▶│  tiered poller ──▶ in-memory snapshot ──▶ SSE fan-out
  (REST,      │    scoreboard 60s                          │      │
   metered)   │    lines 120s / box 180s (game-window gated)│     │
              │    kalshi 15m (NBA, in-season)             │      │
              │  self-throttle: halve cadence when          │      │
              │    X-Quota-Remaining drops below floor      │      │
              └───────────────────────────────────────────┘      │
                                                                  ▼
              ┌───────────────────────────────────────────┐
              │  frontend/  (React 18 + Vite)              │
              │  useSnapshot() ← EventSource(/api/events)  │
              │  scoreboard · game-detail · market-pulse   │
              │  quota-footer                              │
              └───────────────────────────────────────────┘
```

- **One poll cycle serves all viewers.** The server holds a single `Snapshot` in memory and pushes
  it (plus per-league patches) to browsers over Server-Sent Events. Extra viewers add **zero**
  API quota — SSE fan-out is free; only the poller talks to stat-api.
- **Window-gated cadences.** Expensive per-game pulls (box scores, line movement) only run while a
  game is in its live window (roughly T-60min through final). Idle leagues and finished games are
  not polled.
- **Quota discipline is the story.** A full MLB day burns on the order of ~3.4M records/month at
  the default cadences — a Starter-tier budget. The poller reads `X-Quota-*` on every response and
  mechanically halves its cadences when remaining quota drops below a floor, so a runaway can't
  silently 10× the bill. The live `X-Quota-*` numbers are shown in the dashboard footer.

## Run the server

The server consumes the SDK exactly as a customer would, via the `@stat-api/client` package. Until
that package is published (this is pre-publish DM1), link the local build:

```bash
# 1. Build + register the local SDK (once)
cd ../../../sdks/typescript
bun install && bun run build && bun link

# 2. Link it into this demo and install the rest (hono, etc.)
cd -                       # back to apps/demos/dashboard
bun link @stat-api/client  # installs deps + symlinks the local SDK

# 3. Run — needs a real API key; reads it from the environment
STAT_API_KEY=sdb_... bun run start      # http://localhost:4400
```

> Run every `bun` command from **inside this directory**. This is a standalone project — see
> [Workspace isolation](#workspace-isolation). `bun link @stat-api/client` both installs the other
> dependencies and symlinks the local SDK in one step; do **not** run a bare `bun install`
> afterward — the committed manifest pins `@stat-api/client@^0.1.0`, which does not resolve from npm
> until the SDK publishes (DM5). The committable lockfile lands at DM5 alongside the registry swap.

Serving the built frontend too:

```bash
bun run build:frontend     # builds frontend/dist
bun run start              # src/main.ts serves frontend/dist at /
```

Environment knobs (all optional, with safe defaults):

| Var | Default | Meaning |
|---|---|---|
| `STAT_API_KEY` | — | **required** — the API key the SDK authenticates with |
| `STAT_API_BASE_URL` | `https://api.stat-api.com` | backend to poll (point at a local server for dev) |
| `PORT` | `4400` | HTTP port |
| `QUOTA_FLOOR` | `100000` | halve all cadences when `X-Quota-Remaining` drops below this |
| `WINDOW_LEAD_MINUTES` | `60` | how early before start a game's detail tiers open |
| `SCOREBOARD_MS` / `LINES_MS` / `BOX_MS` / `KALSHI_MS` | `60000` / `120000` / `180000` / `900000` | tier cadences |
| `DASHBOARD_DAY` | — | force an ET `YYYYMMDD` day (for smoking a backend with no games "today") |

## Endpoints (server ⇄ browser contract)

The wire format is defined once in
[`frontend/src/lib/contract.ts`](frontend/src/lib/contract.ts) and is independent of the stat-api
SDK — the browser never talks to stat-api directly. The server re-exports those exact types
([`src/contract.ts`](src/contract.ts)) so it can never drift from what the frontend renders.

- `GET /healthz` → liveness + live SSE subscriber count.
- `GET /api/snapshot` → the whole current `Snapshot` (all three leagues + quota).
- `GET /api/events` (SSE) → an initial `{ type: 'snapshot' }`, then `{ type: 'patch', league, games }`
  as each league's games change, plus a full `{ type: 'snapshot' }` re-emitted once per scoreboard
  cycle (60s) so the footer quota and per-league `asOf` stay live — a patch carries only games. One
  JSON object per `data:` line; `event: ping` heartbeats keep the connection alive.
- `GET /*` → the built React app when `frontend/dist` exists.

## Poller tiers & burn budget

Four tiers on independent cadences feed the one in-memory snapshot. Only the poller spends quota;
SSE fan-out to browsers is free.

| Tier | Cadence | Scope | Lists / cycle (full MLB day) |
|---|---|---|---|
| scoreboard | 60s | today's slate per **in-season** league (offseason → cached last-final slate) | 1 MLB (NBA also `day`-filtered; NFL is week-based — see below) |
| lines | 120s | `game_lines?game_id` per **in-window** game → sportsbook pulse | ~15 |
| box | 180s | `game_player_batter_stats?game_id` per in-window MLB game | ~15 |
| kalshi | 15min | `kalshi/markets?competition_id` per in-window NBA game | 0 in summer (NBA offseason) |

**League query shapes differ.** MLB and NBA expose a `day` (YYYYMMDD ET) games filter, so their
slate is one server-filtered list. **NFL has no `day` filter** — its games are keyed by `week` — so
the NFL source resolves the current week from the most recent Final game and pulls that week plus the
next (to catch a game that has not started yet today across the week boundary), then filters to
today's `day` field client-side. That is ~3 small lists, not a full-season scan.

**Offseason leagues show their last completed slate** as final cards (status stays `offseason`).
This is *not* just `games?status=Final`: the games list defaults an absent `season_id` to
`current_season`, whose waterfall (in-season → earliest **upcoming** → most-recent past) resolves to
a forward-scheduled empty season during the offseason gap (NFL's next season already exists with a
future start date). So the source resolves the season explicitly — the most recent one whose
`end_date` is already past — then pulls its finals (newest-first via the games `default_sort` of
game/start-time desc). That slate is resolved **once and cached** (finals don't change out of season),
so an offseason league costs one `seasons` + one `games` list for the whole run.

Reference data (`teams`, `reference/operators`, MLB rosters) is fetched once and cached, so a busy
evening resolves every team, book, and batter name without per-row lookups. Across a ~15-game MLB
night the metered lists are dominated by the lines and box tiers during the live window, landing
around **~3.4M records/month** — inside the Starter tier (5M/mo). Because SSE fan-out is free, that
figure is independent of how many browsers are connected.

## Quota discipline

- The server wraps `fetch` ([`src/sdk.ts`](src/sdk.ts)) to skim `X-Quota-Limit/Used/Remaining` off
  **every** response and keep the latest — one place holds the freshest quota across all four tiers,
  which is what the footer shows and the throttle reads. (The SDK also surfaces `quota` on each list
  response; the fetch wrapper is the tier-agnostic capture point and covers non-list calls too.)
- **Self-throttle:** when `X-Quota-Remaining` falls below `QUOTA_FLOOR`, every cadence is halved
  (interval doubled) and the drop is logged loudly; it restores automatically when quota recovers.
- **Window gating** means far-future and finished games cost nothing — the per-game tiers touch only
  games inside their live window, so a gating bug can't silently 10× the burn.
- 429s are never retried (a monthly quota can't succeed on retry — fail loud); a `QuotaExceededError`
  aborts the rest of the cycle.

## Test & verify

```bash
bun run typecheck     # tsc --noEmit, strict
bun test              # poller budget/throttle/diff + HTTP/SSE plumbing (mocked, no network)
bun run test:smoke    # live smoke; BLOCKED (exit 0) unless STAT_API_KEY is set
```

- `test/poller.test.ts` drives a mocked SDK to assert the per-cycle request budget, window gating
  (zero line/box calls for scheduled-far and finished games), the quota self-throttle, and that a
  snapshot diff emits exactly one patch per changed league.
- `test/server.test.ts` drives the real Hono app in-process (no network): `GET /api/snapshot`, and
  the SSE stream delivering an initial `snapshot` then a `patch`.
- `test:smoke` boots the real server against the published API, runs one poll cycle, and asserts
  today's MLB slate + freshness stamps + a metered quota, plus one SSE event. With no `STAT_API_KEY`
  it prints `BLOCKED` and exits 0, so it is safe in CI.

## Status

- **DM0:** frontend shell — panels rendering from the fixture snapshot, the contract module, the
  `useSnapshot()` seam.
- **DM1 (this):** the Bun+Hono poller/SSE server implementing the contract against the published
  SDK — tiered poller, window gating, quota self-throttle, snapshot diff → SSE fan-out.
- **DM5:** registry-dep swap + committable lockfile, deploy to the box (systemd
  `demo-dashboard.service` + CF tunnel → demos.stat-api.com).

## Develop the frontend

```bash
cd frontend
bun install          # own lockfile — NEVER add this dir to the root bun workspaces
bun run dev          # vite dev server; renders the fixture snapshot (or the live server via SSE)
bun run build        # tsc --noEmit + vite build
```

### Workspace isolation

Both `frontend/` and this server are **standalone bun projects, each with its own lockfile**. They
must never be added to the root `sports-db` `package.json` `workspaces` (which are explicitly
enumerated and do not glob `apps/*`). Once `sdks/typescript` is workspace-registered as
`@stat-api/client`, a workspace demo would silently link the *local* SDK instead of the *published*
npm package — which defeats the whole point of a dogfooding demo. Run all `bun install`/`bun add`
from inside the project dir. (Same isolation rule as `apps/predictql/frontend`.)
