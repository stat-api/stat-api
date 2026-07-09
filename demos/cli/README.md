# sports — stat-api terminal CLI

**For backend & terminal developers.**

- **Who it's for** — engineers who live in the shell and want scores without leaving it.
- **What it teaches** — a fast, dependency-free Go CLI on the [`statapi`](../../../sdks/go) Go SDK:
  stdlib `flag` + `text/tabwriter` only, every command **golden-tested** against fixtures (no key in
  CI).
- **What stat-api data it shows off** — `scores`, `box`, `standings`, and live `quota` across
  MLB / NBA / NFL (NHL coming soon), from the poll-only public API.

A small, fast terminal client for the public **stat-api**, built as a dogfooding demo. Zero
third-party dependencies: stdlib `flag` + `text/tabwriter` only.

```
$ sports today
Scores · 2026-07-08

MLB · 2026-07-08
AWAY  HOME  SCORE  STATUS
NYY   BOS   3-5    Final
LAD   SF    2-1    In Progress
CHC   STL   —      Scheduled
...
```

## Install

Once the module is published (release = the git tag):

```bash
go install github.com/stat-api/stat-api/demos/cli@main   # installs the `sports` binary
```

Before then, run it from this directory:

```bash
go run . today
```

Local development resolves the SDK through a **gitignored** `go.work` that points
the `github.com/stat-api/stat-api/go` require at the in-repo copy. The committed
`go.mod` requires the published tag; CI builds without the workspace.

## Configure

```bash
export STAT_API_KEY=sdb_xxxxxxxx_...          # required
export STAT_API_BASE_URL=https://api.stat-api.com   # optional (this is the default)
```

## Commands

| Command | Description |
|---|---|
| `sports today` | Today's slate across leagues — MLB + NBA by date, NFL by current week; NHL is coming-soon. |
| `sports scores --league mlb\|nba\|nfl [--date YYYYMMDD] [--week N]` | One league's scoreboard. `--date` applies to MLB/NBA (defaults to today); `--week` to NFL (defaults to the current week). |
| `sports box --league mlb\|nba <game-id>` | Box score for a game. MLB shows batting + pitching; NBA shows player lines. `--league` defaults to `mlb`. |
| `sports standings [--league nba]` | League standings by conference (NBA only in v1). |
| `sports player <name>` | Fuzzy NBA player search + the top match's latest-season averages. |
| `sports quota` | Current API quota (limit / used / remaining) via one metered call. |
| `sports help` · `sports version` | Usage and SDK version. |

Player search uses the NBA-only handcrafted `/nba/players/search` endpoint via a
small documented raw-HTTP helper ([`search.go`](search.go)) — the generated SDK
deliberately omits handcrafted endpoints, so this is the escape hatch until name
search is promoted across leagues and into the SDK.

## Testing

Golden render tests ([`main_test.go`](main_test.go)) run each command against a
Go `httptest.Server` with canned fixtures (base URL injected via
`STAT_API_BASE_URL`) and diff the rendered tables in `testdata/*.golden` — no API
key needed in CI. Regenerate goldens after an intentional format change:

```bash
go test ./... -update
```

Gates: `go vet ./... && go build ./... && go test ./...` and `gofmt -l .` (empty).
