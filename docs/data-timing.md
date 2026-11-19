# Data timing (PRE and FULL)

Some NFL data changes after a game starts. A player listed as Questionable on
Friday can be ruled Out after an injury in the game. A slate's injury code can
change on Monday. If you train a model or back-test a lineup on the Monday
value, the model learns something it could not know before kickoff.

stat-api keeps both values apart and lets you choose with the `mode` parameter:

| `mode` | What you get |
|---|---|
| `pre` (default) | Each value as it stood at **its game's kickoff**. Post-game fields are `null`. |
| `full` | The latest value before **week end**, the first Tuesday 04:00 ET after the game. Post-game fields are filled. |

`pre` is the default. A request without `mode` gets the kickoff values.

NBA game rosters take the same parameter. An NBA status belongs to one game: `pre`
returns each player's status, injury designation, starting role and depth-chart
slot as they stood at that game's tip, and `full` returns the values recorded
after the game, with the same depth-chart slot. See
[NBA game rosters](#nba-game-rosters).

## The NFL week

Each game has its own lock: its kickoff (`games.game_time`). DFS late swap
works the same way. A Sunday 4:25 PM player locks at 4:25 PM, not at the
slate's 1:00 PM lock.

| Line | When (ET) | What becomes known | Mode |
|---|---|---|---|
| Week start | Tuesday 04:00, after the previous Monday game | Roster moves, waivers | PRE |
| Practice reports | Wednesday, Thursday, Friday about 16:00 (Monday to Wednesday for a Thursday game) | Did not practice / limited / full | PRE |
| Final injury report | Friday about 16:00 (Saturday for a Monday game, Wednesday for a Thursday game) | Out, Doubtful, Questionable | PRE |
| Roster deadline | 16:00 the day before the game | Practice-squad elevations, activations | PRE |
| Inactives | Kickoff minus 90 minutes | Game roster Active / Inactive | PRE |
| **Kickoff (lock)** | `games.game_time`, per game | **The PRE cut** | — |
| Final | End of the game | Stats, snaps, participation, results | FULL only |
| After the game | Monday, Tuesday | In-game injuries, new designations, IR moves | FULL |
| **Week end** | **First Tuesday 04:00 after the game** | **The FULL cut** | — |

- A Thursday game locks on Thursday. Its FULL window stays open until the next
  Tuesday 04:00.
- A postponed game moves its lock with its new `game_time`.
- A team on bye has no lock that week, so PRE and FULL are the same for it.

## Endpoints that take `mode`

| Endpoint | PRE | FULL |
|---|---|---|
| `GET /api/v1/nfl/injuries` (and `/{id}`) | The injury report at kickoff | The latest report before week end, including players first listed after kickoff |
| `GET /api/v1/nfl/game_team_rosters` (and `/{id}`) | Status, injury status, depth and starter flag at kickoff | The latest values before week end |
| `GET /api/v1/dfs/slate_players` (and `/{id}`) | The operator's injury code, details and average points at the player's kickoff | The latest values before week end |
| `GET /api/v1/nfl/projected_player_snaps` (and `/{id}`) | Projected fields; `actual_*` and `*_error` are `null` | Projected and actual fields |
| `GET /api/v1/nfl/projected_team_snaps` (and `/{id}`) | Projected fields; `actual_*` and `*_error` are `null` | Projected and actual fields |
| `GET /api/v1/dfs/slates/{slate_id}/projections`, `/dfs/slates/latest/projections` | `actual_fantasy_pts`, `actual_ownership`, `actual_rank`, `leverage_delta` are `null` | All fields |
| `GET /api/v1/dfs/slates/{slate_id}/roster-context` | Injury status and practice status at kickoff | The latest before week end |
| `GET /api/v1/dfs/slates/{slate_id}/injuries` | The report and operator codes at kickoff | The latest before week end |
| `GET /api/v1/nfl/projections` (weekly team projections) | Post-game player fields are `null` | All fields |
| `GET /api/v1/nba/game_team_rosters` (and `/{id}`) | Status, injury status, starter, lineup confirmation, depth-chart slot and the injury report's reason as they stood at that game's tip; `null` when nothing was known before tip | The values recorded after the game; the depth-chart slot at tip |

A time-walled list response names its mode in the envelope (`"mode": "pre"`)
and in the `X-Time-Wall-Mode` header. An item response and the custom DFS routes
use the header. The GraphQL root fields of the six tables take
`mode: PRE | FULL` (default `PRE`). Nested GraphQL lists always read PRE; query
the table's root field with `mode: FULL` to get FULL.

In `mode=pre`, `dfs/slate_players?enrich=dfs.player_stats` (and `enrich=*`)
returns **400**: fantasy points are known only after the game. Add `mode=full`.

Any other `/api/v1/{league}/{table}` endpoint returns 400 when you send `mode`
(other custom DFS routes ignore it). Those tables fall into three groups:

- **Pre-game by construction.** `nfl/depth_charts`, `nfl/game_lines` and
  `nfl/game_player_props` store only values captured before kickoff, so PRE and
  FULL would be the same.
- **Post-game (FULL only).** `nfl/game_player_stats`, `nfl/play_by_plays`,
  `nfl/play_participations`, `nfl/game_team_stats`, `nfl/game_weathers` (every
  row is recorded after kickoff), `dfs/player_stats` and the contest results.
  Do not use them as model inputs for the same game.
- **Not game-anchored.** `nfl/games` keeps its scores visible (use `status` and
  `game_time` to cut them). News rows carry their own publish time, so cut them
  at your lock with the timestamp.

## NBA game rosters

`GET /api/v1/nba/game_team_rosters` takes `mode` since 2026-10-22. An NBA
status belongs to one game, so the PRE cut is that game's tip
(`games.game_time`), and FULL holds the values recorded after the game. There is
no week window.

| Field | `pre` (default) | `full` |
|---|---|---|
| `status` | 3 when the player was ruled out before tip, 1 for any other designation, `null` when nothing was known before tip | Availability recorded after the game: 1 Active, 2 Inactive, 3 Out |
| `injury_status` | The designation that stood at tip (`OUT`, `DOUBTFUL`, `QUESTIONABLE`, `PROBABLE`); `null` when available or unknown | The designation recorded after the game |
| `starter` | In the starting lineup known before tip; `null` when no lineup was known | Started the game |
| `confirmed` | The team's starting lineup was confirmed before tip; `null` when no lineup was known | The starter value comes from post-game evidence |
| `comment` | The reason on the league's injury report for that game | The note of the post-game roster |
| `depth_order` | The player's rank at `depth_position` on the depth chart that stood at tip (1 = first at the position); `null` when the chart leaves him out | The same as `pre` |
| `depth_position` | The position of that rank: `PG`, `SG`, `SF`, `PF` or `C`. A player listed at two positions shows the one where his rank is better; on a tie, the first in that order | The same as `pre` |

The status at tip is the newest change for that game before tip, from the
league's injury report, the league's inactive list or another status feed. For
2021-22 through 2025-26 only the league's injury report is known before tip, so
`starter` and `confirmed` are `null` in `pre` for those seasons. The four
`pre_game_*` fields that the route returned before 2026-10-22 are gone.

The depth chart is the team's chart published before tip. For a game without
one, it is a chart built from the team's previous games. Like the NFL route, the
NBA route filters by `position`, `status`, `injury_status` and `depth_order`
(`depth_order=1`, or `depth_order__lte=2`). `status`, `injury_status` and
`depth_order` filter on the values that the mode shows.

```sh
curl -sS -H "Authorization: Bearer $STAT_API_KEY" \
  "https://api.stat-api.com/api/v1/nba/game_team_rosters?game_id=615"

curl -sS -H "Authorization: Bearer $STAT_API_KEY" \
  "https://api.stat-api.com/api/v1/nba/game_team_rosters?game_id=615&mode=full"

curl -sS -H "Authorization: Bearer $STAT_API_KEY" \
  "https://api.stat-api.com/api/v1/nba/game_team_rosters?game_id=615&depth_order=1"
```

## Examples

The kickoff report for one game, then the full week:

```sh
curl -sS -H "Authorization: Bearer $STAT_API_KEY" \
  "https://api.stat-api.com/api/v1/nfl/injuries?game_id=43445"

curl -sS -H "Authorization: Bearer $STAT_API_KEY" \
  "https://api.stat-api.com/api/v1/nfl/injuries?game_id=43445&mode=full"
```

```ts
const pre = await api.nfl.injuries.list({ game_id: 43445 });                // mode: 'pre'
const full = await api.nfl.injuries.list({ game_id: 43445, mode: 'full' });
```
```python
pre = api.nfl.injuries.list(game_id=43445)
full = api.nfl.injuries.list(game_id=43445, mode="full")
```

A back-test that joins projections to outcomes reads its inputs with the default
`pre` and its targets from a post-game table (`nfl/game_player_stats` or
`dfs/player_stats`), so no post-game value reaches the inputs.

## Database readers

Inside the platform the rule is the same. The base tables (`nfl.injuries`,
`nfl.game_team_rosters`, `dfs.slate_players`) hold PRE: a database trigger keeps
each walled column at its kickoff value. The views `nfl.injuries_full`,
`nfl.game_team_rosters_full` and `dfs.slate_players_full` serve FULL with the
same columns. `nba.game_team_rosters` holds the post-game values (FULL); its PRE
values and the depth-chart slot of both modes come from the NBA game roster
service at read time.
