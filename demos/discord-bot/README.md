# stat-api Discord bot

**For league & community operators.**

- **Who it's for** — anyone running a sports Discord (fantasy leagues, fan servers) who wants a
  scores bot they can clone and run.
- **What it teaches** — quota discipline: many servers, **one global TTL cache + single-flight
  dedupe**, so ten servers asking the same question in the same minute cost **one** upstream call.
  Built on [discord.js](https://discord.js.org) v14.
- **What stat-api data it shows off** — scores, box scores, player averages, standings, and player
  news, from the poll-only public API.

A clone-and-run Discord bot built on [`@stat-api/client`](https://www.npmjs.com/package/@stat-api/client)
as a dogfooding demo. The teaching point is **quota discipline** — see
[Quota discipline](#quota-discipline-the-point) below. The bot uses the discord.js **gateway** (a
persistent WebSocket **to Discord**, not to stat-api) rather than HTTP interactions, so it needs no
public HTTPS endpoint — clone it, set two env vars, run it.

## Run

Exactly **two** environment variables:

```bash
export DISCORD_TOKEN=...        # bot token from the Discord developer portal
export STAT_API_KEY=sdb_...     # your key from stat-api.com
```

Missing either one fails loud on startup, naming what's absent.

```bash
bun install          # own lockfile — this app is NOT in the root bun workspaces
bun run start
```

On startup the bot registers its slash commands globally (`/scores`, `/box`, `/player`,
`/standings`, `/news`) and logs in.

### Pre-publish local development

`@stat-api/client` is versioned in `package.json` at its published version, but until it is live on
npm you link the monorepo build instead of installing from the registry:

```bash
# from the repo root — build and register the SDK once
cd sdks/typescript && bun run build && bun link

# then, in this directory, link it in (also installs discord.js)
cd apps/demos/discord-bot && bun link @stat-api/client
```

`bun link @stat-api/client` symlinks the local SDK into `node_modules` **without** editing the
committed manifest, and pulls in discord.js in the same pass. After the SDK publishes, a plain
`bun install` is all you need.

## Commands

| Command | Leagues | Description |
|---|---|---|
| `/scores [league] [date]` | MLB (default), NBA | Scoreboard for a date (`YYYYMMDD`, US Eastern; defaults to today). |
| `/box <game> [league]` | MLB (default), NBA | Box score. The `game` option autocompletes from the cached slate. |
| `/player <name>` | NBA | Fuzzy name search → latest-season averages. |
| `/standings` | NBA | Standings grouped by conference. |
| `/news [league]` | NBA (default), NFL, MLB | Latest player-news headlines. |

Off day or offseason (empty slate) → a clean "no games" embed rather than an error. In July, MLB
carries the live experience; NBA and NFL are between seasons.

Every embed footer stamps provenance:

```
data as of 2026-07-08 17:00 UTC · cached 12s ago · stat-api.com
```

## Quota discipline (the point)

stat-api meters **records returned on every request, including cache hits on its own edge** — so a
naive bot that hits the API once per command per server burns quota fast. This bot is built the
other way around, in `src/cache.ts`:

- **One global TTL cache**, keyed `(command, league, normalized args)` — *not* per-guild. If ten
  servers ask for tonight's scores in the same minute, that's **one** upstream call, not ten.
- **Single-flight dedupe**: concurrent misses for the same key await one in-flight fetch, so a
  burst of identical requests still costs one call. Failed fetches are never cached.
- **TTLs matched to upstream freshness** — short for scores, long for reference data — so the cache
  is as cheap as it can be without going stale:

  | Data | TTL |
  |---|---|
  | scores | 60s |
  | box score | 120s |
  | news | 5 min |
  | standings | 1 hour |
  | player averages | 1 hour |
  | teams / rosters (reference) | 1 hour |

- **Autocomplete costs nothing.** The `/box` game picker reads choices from the already-cached
  `/scores` slate (`cache.peek`) — zero extra API calls per keystroke.

## Development

```bash
bun run typecheck    # tsc --noEmit, strict
bun run test         # snapshot + cache-behavior tests
```

Handlers are pure `(api, args) → embed` functions (`src/commands/*.ts`) with **no discord.js types
in their signatures**, so the tests exercise every command — including the offseason, cache-hit,
and single-flight paths — against a fixture SDK stub. No Discord token or live API is needed.

### Layout

```
src/
  main.ts            composition root — the only file that touches discord.js or builds the SDK
  cache.ts           global TTL cache + single-flight (the quota-discipline core)
  leagues.ts         per-league display metadata + shared id→name lookups
  format.ts          pure embed builders (no discord.js, no SDK)
  commands/          one pure handler per slash command
test/
  handlers.test.ts   fixture-stub snapshots + cache-behavior assertions
```
