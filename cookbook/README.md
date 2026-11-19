# stat-api cookbook

Runnable examples for the [stat-api](https://stat-api.com) SDKs in 4 languages.

> 100% generated from the stat-api schema — do not hand-edit any file in this
> repository. Version 0.3.0.

Each example is emitted in every language against the same published SDK
surface, so the code you read is the code the SDK actually exposes. Pick your
language directory for install + run instructions.

Part of the [stat-api monorepo](https://github.com/stat-api/stat-api) — this cookbook lives under
`cookbook/`.

## SIM & Optimizer Tracks

Production-ready, byte-identical cross-language workflows across NBA/NFL game simulators, Fast Optimizer, and Monte Carlo SIM Lineup generation:

### NFL Play-by-Play SIM

Full-game NFL Monte Carlo drive & play simulation — summary metrics, 30-bin bell curves, 6 archetype scoreboxes, play-by-play tracing, and CRN what-if overrides.

| Recipe | TypeScript | Python | Go | Java |
| --- | --- | --- | --- | --- |
| `nfl-sim-summary` — Run your first NFL game simulation | [TypeScript](typescript/src/nfl-sim-summary.ts) | [Python](python/nfl_sim_summary.py) | [Go](go/nfl-sim-summary/main.go) | [Java](java/src/main/java/com/statapi/cookbook/NFLSimSummary.java) |
| `nfl-sim-distributions` — See the whole range of outcomes | [TypeScript](typescript/src/nfl-sim-distributions.ts) | [Python](python/nfl_sim_distributions.py) | [Go](go/nfl-sim-distributions/main.go) | [Java](java/src/main/java/com/statapi/cookbook/NFLSimDistributions.java) |
| `nfl-sim-scorebox` — Meet six typical games | [TypeScript](typescript/src/nfl-sim-scorebox.ts) | [Python](python/nfl_sim_scorebox.py) | [Go](go/nfl-sim-scorebox/main.go) | [Java](java/src/main/java/com/statapi/cookbook/NFLSimScorebox.java) |
| `nfl-sim-trace` — Replay one simulated game | [TypeScript](typescript/src/nfl-sim-trace.ts) | [Python](python/nfl_sim_trace.py) | [Go](go/nfl-sim-trace/main.go) | [Java](java/src/main/java/com/statapi/cookbook/NFLSimTrace.java) |
| `nfl-sim-overrides` — Ask a what-if question | [TypeScript](typescript/src/nfl-sim-overrides.ts) | [Python](python/nfl_sim_overrides.py) | [Go](go/nfl-sim-overrides/main.go) | [Java](java/src/main/java/com/statapi/cookbook/NFLSimOverrides.java) |

### NBA Possession-by-Possession SIM

Full-game NBA lineup-rotation and possession simulation — free game discovery, game & player summary projections, empirical bell curves, and CRN spread sensitivity overrides.

| Recipe | TypeScript | Python | Go | Java |
| --- | --- | --- | --- | --- |
| `nba-game-sim-summary` — Run an NBA possession simulation summary | [TypeScript](typescript/src/nba-game-sim-summary.ts) | [Python](python/nba_game_sim_summary.py) | [Go](go/nba-game-sim-summary/main.go) | [Java](java/src/main/java/com/statapi/cookbook/NbaGameSimSummary.java) |
| `nba-game-sim-distributions` — Extract NBA empirical bell curves and quantiles | [TypeScript](typescript/src/nba-game-sim-distributions.ts) | [Python](python/nba_game_sim_distributions.py) | [Go](go/nba-game-sim-distributions/main.go) | [Java](java/src/main/java/com/statapi/cookbook/NbaGameSimDistributions.java) |
| `nba-game-sim-overrides` — Compare NBA Common Random Numbers spread overrides | [TypeScript](typescript/src/nba-game-sim-overrides.ts) | [Python](python/nba_game_sim_overrides.py) | [Go](go/nba-game-sim-overrides/main.go) | [Java](java/src/main/java/com/statapi/cookbook/NbaGameSimOverrides.java) |

### DFS Fast Lineup Optimizer & Late Swapper

Sub-second deterministic MILP lineup construction & projection late swapping — Classic portfolios, correlated QB/WR bring-back stacks + conditional player groups, direct CSV upload export, and contest entry late swaps.

| Recipe | TypeScript | Python | Go | Java |
| --- | --- | --- | --- | --- |
| `dfs-optimizer-classic` — Build a diversified NFL Classic DFS lineup portfolio | [TypeScript](typescript/src/dfs-optimizer-classic.ts) | [Python](python/dfs_optimizer_classic.py) | [Go](go/dfs-optimizer-classic/main.go) | [Java](java/src/main/java/com/statapi/cookbook/DfsOptimizerClassic.java) |
| `dfs-optimizer-stacks-groups` — Enforce correlated QB stacks, bring-backs, and player groups | [TypeScript](typescript/src/dfs-optimizer-stacks-groups.ts) | [Python](python/dfs_optimizer_stacks_groups.py) | [Go](go/dfs-optimizer-stacks-groups/main.go) | [Java](java/src/main/java/com/statapi/cookbook/DfsOptimizerStacksGroups.java) |
| `dfs-optimizer-nba-csv` — Export NBA Classic DFS lineups as ready-to-upload CSV | [TypeScript](typescript/src/dfs-optimizer-nba-csv.ts) | [Python](python/dfs_optimizer_nba_csv.py) | [Go](go/dfs-optimizer-nba-csv/main.go) | [Java](java/src/main/java/com/statapi/cookbook/DfsOptimizerNbaCsv.java) |
| `dfs-optimizer-late-swap` — Late-swap unlocked slots on active contest entries | [TypeScript](typescript/src/dfs-optimizer-late-swap.ts) | [Python](python/dfs_optimizer_late_swap.py) | [Go](go/dfs-optimizer-late-swap/main.go) | [Java](java/src/main/java/com/statapi/cookbook/DfsOptimizerLateSwap.java) |

### DFS SIM Lineups, Pool Re-Selector & Async Jobs

Contest-aware Monte Carlo GPP lineup generation, sub-50ms in-memory pool_id re-selection with EV/diversity Pareto frontiers, async 202 job polling, and NBA contest simulation.

| Recipe | TypeScript | Python | Go | Java |
| --- | --- | --- | --- | --- |
| `dfs-lineup-sim-gpp-build` — Generate EV-maximized NFL GPP lineups with Prefer: wait | [TypeScript](typescript/src/dfs-lineup-sim-gpp-build.ts) | [Python](python/dfs_lineup_sim_gpp_build.py) | [Go](go/dfs-lineup-sim-gpp-build/main.go) | [Java](java/src/main/java/com/statapi/cookbook/DfsLineupSimGppBuild.java) |
| `dfs-lineup-sim-pool-reselect` — Re-select SIM portfolios in <50ms via cached pool_id | [TypeScript](typescript/src/dfs-lineup-sim-pool-reselect.ts) | [Python](python/dfs_lineup_sim_pool_reselect.py) | [Go](go/dfs-lineup-sim-pool-reselect/main.go) | [Java](java/src/main/java/com/statapi/cookbook/DfsLineupSimPoolReselect.java) |
| `dfs-lineup-sim-async-job` — Queue an async 202 SIM job and poll until completion | [TypeScript](typescript/src/dfs-lineup-sim-async-job.ts) | [Python](python/dfs_lineup_sim_async_job.py) | [Go](go/dfs-lineup-sim-async-job/main.go) | [Java](java/src/main/java/com/statapi/cookbook/DfsLineupSimAsyncJob.java) |
| `dfs-lineup-sim-nba-build` — Generate EV-optimized NBA Classic GPP lineups | [TypeScript](typescript/src/dfs-lineup-sim-nba-build.ts) | [Python](python/dfs_lineup_sim_nba_build.py) | [Go](go/dfs-lineup-sim-nba-build/main.go) | [Java](java/src/main/java/com/statapi/cookbook/DfsLineupSimNbaBuild.java) |

## NFL sim tutorial (beta)

The recipes below teach the NFL play-by-play simulator, one call at a time. Each recipe runs on the free game and prints real output. Each recipe is one program in TypeScript, Python, Go and Java, and the four programs print the same lines.

Start with [the tutorial](nfl-sim/README.md).

| # | Recipe | Calls | TypeScript | Python | Go | Java |
| --- | --- | --- | --- | --- | --- | --- |
| 1 | Run your first NFL game simulation | `weeks`, `run` | [src](typescript/src/nfl-sim-summary.ts) | [src](python/nfl_sim_summary.py) | [src](go/nfl-sim-summary/main.go) | [src](java/src/main/java/com/statapi/cookbook/NFLSimSummary.java) |
| 2 | See the whole range of outcomes | `weeks`, `run` | [src](typescript/src/nfl-sim-distributions.ts) | [src](python/nfl_sim_distributions.py) | [src](go/nfl-sim-distributions/main.go) | [src](java/src/main/java/com/statapi/cookbook/NFLSimDistributions.java) |
| 3 | Meet six typical games | `weeks`, `run` | [src](typescript/src/nfl-sim-scorebox.ts) | [src](python/nfl_sim_scorebox.py) | [src](go/nfl-sim-scorebox/main.go) | [src](java/src/main/java/com/statapi/cookbook/NFLSimScorebox.java) |
| 4 | Replay one simulated game | `weeks`, `run` | [src](typescript/src/nfl-sim-trace.ts) | [src](python/nfl_sim_trace.py) | [src](go/nfl-sim-trace/main.go) | [src](java/src/main/java/com/statapi/cookbook/NFLSimTrace.java) |
| 5 | Ask a what-if question | `weeks`, `run` | [src](typescript/src/nfl-sim-overrides.ts) | [src](python/nfl_sim_overrides.py) | [src](go/nfl-sim-overrides/main.go) | [src](java/src/main/java/com/statapi/cookbook/NFLSimOverrides.java) |

## Showcase — data nobody else has

Examples built on data you can only get here — DFS salaries and derived
rolling windows — each linked in every language.

| Example | What it does | TypeScript | Python | Go | Java |
| --- | --- | --- | --- | --- | --- |
| `dfs-slate-to-salaries` | A DFS slate resolved to player salaries through its required filter sets. | [TypeScript](typescript/src/dfs-slate-to-salaries.ts) | [Python](python/dfs_slate_to_salaries.py) | [Go](go/dfs-slate-to-salaries/main.go) | [Java](java/src/main/java/com/statapi/cookbook/DfsSlateToSalaries.java) |
| `nba-rolling-averages` | Rolling windows computed over a player's game log. | [TypeScript](typescript/src/nba-rolling-averages.ts) | [Python](python/nba_rolling_averages.py) | [Go](go/nba-rolling-averages/main.go) | [Java](java/src/main/java/com/statapi/cookbook/NbaRollingAverages.java) |

## Tier 1 — first steps

| Example | TypeScript | Python | Go | Java |
| --- | --- | --- | --- | --- |
| `dfs-main-slate` — Find the main slate for a day | [src](typescript/src/dfs-main-slate.ts) | [src](python/dfs_main_slate.py) | [src](go/dfs-main-slate/main.go) | [src](java/src/main/java/com/statapi/cookbook/DfsMainSlate.java) |
| `dfs-salary-board` — Read a slate's salaries | [src](typescript/src/dfs-salary-board.ts) | [src](python/dfs_salary_board.py) | [src](go/dfs-salary-board/main.go) | [src](java/src/main/java/com/statapi/cookbook/DfsSalaryBoard.java) |
| `mlb-filtering-basics` — Filter MLB games by season | [src](typescript/src/mlb-filtering-basics.ts) | [src](python/mlb_filtering_basics.py) | [src](go/mlb-filtering-basics/main.go) | [src](java/src/main/java/com/statapi/cookbook/MlbFilteringBasics.java) |
| `mlb-hello-stat-api` — Hello, MLB | [src](typescript/src/mlb-hello-stat-api.ts) | [src](python/mlb_hello_stat_api.py) | [src](go/mlb-hello-stat-api/main.go) | [src](java/src/main/java/com/statapi/cookbook/MlbHelloStatApi.java) |
| `mlb-pagination-auto` — Auto-paginate every MLB team | [src](typescript/src/mlb-pagination-auto.ts) | [src](python/mlb_pagination_auto.py) | [src](go/mlb-pagination-auto/main.go) | [src](java/src/main/java/com/statapi/cookbook/MlbPaginationAuto.java) |
| `nba-filtering-basics` — Filter NBA games by season | [src](typescript/src/nba-filtering-basics.ts) | [src](python/nba_filtering_basics.py) | [src](go/nba-filtering-basics/main.go) | [src](java/src/main/java/com/statapi/cookbook/NbaFilteringBasics.java) |
| `nba-get-by-id` — Fetch one NBA team by id | [src](typescript/src/nba-get-by-id.ts) | [src](python/nba_get_by_id.py) | [src](go/nba-get-by-id/main.go) | [src](java/src/main/java/com/statapi/cookbook/NbaGetById.java) |
| `nba-hello-stat-api` — Hello, NBA | [src](typescript/src/nba-hello-stat-api.ts) | [src](python/nba_hello_stat_api.py) | [src](go/nba-hello-stat-api/main.go) | [src](java/src/main/java/com/statapi/cookbook/NbaHelloStatApi.java) |
| `nba-pagination-auto` — Auto-paginate every NBA team | [src](typescript/src/nba-pagination-auto.ts) | [src](python/nba_pagination_auto.py) | [src](go/nba-pagination-auto/main.go) | [src](java/src/main/java/com/statapi/cookbook/NbaPaginationAuto.java) |
| `nfl-filtering-basics` — Filter NFL games by season | [src](typescript/src/nfl-filtering-basics.ts) | [src](python/nfl_filtering_basics.py) | [src](go/nfl-filtering-basics/main.go) | [src](java/src/main/java/com/statapi/cookbook/NflFilteringBasics.java) |
| `nfl-hello-stat-api` — Hello, NFL | [src](typescript/src/nfl-hello-stat-api.ts) | [src](python/nfl_hello_stat_api.py) | [src](go/nfl-hello-stat-api/main.go) | [src](java/src/main/java/com/statapi/cookbook/NflHelloStatApi.java) |
| `nfl-pagination-auto` — Auto-paginate every NFL team | [src](typescript/src/nfl-pagination-auto.ts) | [src](python/nfl_pagination_auto.py) | [src](go/nfl-pagination-auto/main.go) | [src](java/src/main/java/com/statapi/cookbook/NflPaginationAuto.java) |
| `nhl-filtering-basics` — Filter NHL games by season | [src](typescript/src/nhl-filtering-basics.ts) | [src](python/nhl_filtering_basics.py) | [src](go/nhl-filtering-basics/main.go) | [src](java/src/main/java/com/statapi/cookbook/NhlFilteringBasics.java) |
| `nhl-hello-stat-api` — Hello, NHL | [src](typescript/src/nhl-hello-stat-api.ts) | [src](python/nhl_hello_stat_api.py) | [src](go/nhl-hello-stat-api/main.go) | [src](java/src/main/java/com/statapi/cookbook/NhlHelloStatApi.java) |
| `nhl-pagination-auto` — Auto-paginate every NHL team | [src](typescript/src/nhl-pagination-auto.ts) | [src](python/nhl_pagination_auto.py) | [src](go/nhl-pagination-auto/main.go) | [src](java/src/main/java/com/statapi/cookbook/NhlPaginationAuto.java) |

## Tier 2 — pagination & errors

| Example | TypeScript | Python | Go | Java |
| --- | --- | --- | --- | --- |
| `dfs-scoring-breakdown` — See where a player's fantasy points came from | [src](typescript/src/dfs-scoring-breakdown.ts) | [src](python/dfs_scoring_breakdown.py) | [src](go/dfs-scoring-breakdown/main.go) | [src](java/src/main/java/com/statapi/cookbook/DfsScoringBreakdown.java) |
| `dfs-slate-scorers` — See who actually scored on a slate | [src](typescript/src/dfs-slate-scorers.ts) | [src](python/dfs_slate_scorers.py) | [src](go/dfs-slate-scorers/main.go) | [src](java/src/main/java/com/statapi/cookbook/DfsSlateScorers.java) |
| `nba-error-handling` — Handle errors by type | [src](typescript/src/nba-error-handling.ts) | [src](python/nba_error_handling.py) | [src](go/nba-error-handling/main.go) | [src](java/src/main/java/com/statapi/cookbook/NbaErrorHandling.java) |
| `nba-game-sim-distributions` — Extract NBA empirical bell curves and quantiles | [src](typescript/src/nba-game-sim-distributions.ts) | [src](python/nba_game_sim_distributions.py) | [src](go/nba-game-sim-distributions/main.go) | [src](java/src/main/java/com/statapi/cookbook/NbaGameSimDistributions.java) |
| `nba-game-sim-overrides` — Compare NBA Common Random Numbers spread overrides | [src](typescript/src/nba-game-sim-overrides.ts) | [src](python/nba_game_sim_overrides.py) | [src](go/nba-game-sim-overrides/main.go) | [src](java/src/main/java/com/statapi/cookbook/NbaGameSimOverrides.java) |
| `nba-game-sim-summary` — Run an NBA possession simulation summary | [src](typescript/src/nba-game-sim-summary.ts) | [src](python/nba_game_sim_summary.py) | [src](go/nba-game-sim-summary/main.go) | [src](java/src/main/java/com/statapi/cookbook/NbaGameSimSummary.java) |
| `nba-pagination-manual` — Paginate by hand with from_id | [src](typescript/src/nba-pagination-manual.ts) | [src](python/nba_pagination_manual.py) | [src](go/nba-pagination-manual/main.go) | [src](java/src/main/java/com/statapi/cookbook/NbaPaginationManual.java) |
| `nba-quota-headers` — Read your quota off a response | [src](typescript/src/nba-quota-headers.ts) | [src](python/nba_quota_headers.py) | [src](go/nba-quota-headers/main.go) | [src](java/src/main/java/com/statapi/cookbook/NbaQuotaHeaders.java) |

## Tier 3 — worked examples

| Example | TypeScript | Python | Go | Java |
| --- | --- | --- | --- | --- |
| `dfs-optimizer-classic` — Build a diversified NFL Classic DFS lineup portfolio | [src](typescript/src/dfs-optimizer-classic.ts) | [src](python/dfs_optimizer_classic.py) | [src](go/dfs-optimizer-classic/main.go) | [src](java/src/main/java/com/statapi/cookbook/DfsOptimizerClassic.java) |
| `dfs-optimizer-late-swap` — Late-swap unlocked slots on active contest entries | [src](typescript/src/dfs-optimizer-late-swap.ts) | [src](python/dfs_optimizer_late_swap.py) | [src](go/dfs-optimizer-late-swap/main.go) | [src](java/src/main/java/com/statapi/cookbook/DfsOptimizerLateSwap.java) |
| `dfs-optimizer-nba-csv` — Export NBA Classic DFS lineups as ready-to-upload CSV | [src](typescript/src/dfs-optimizer-nba-csv.ts) | [src](python/dfs_optimizer_nba_csv.py) | [src](go/dfs-optimizer-nba-csv/main.go) | [src](java/src/main/java/com/statapi/cookbook/DfsOptimizerNbaCsv.java) |
| `dfs-optimizer-stacks-groups` — Enforce correlated QB stacks, bring-backs, and player groups | [src](typescript/src/dfs-optimizer-stacks-groups.ts) | [src](python/dfs_optimizer_stacks_groups.py) | [src](go/dfs-optimizer-stacks-groups/main.go) | [src](java/src/main/java/com/statapi/cookbook/DfsOptimizerStacksGroups.java) |
| `dfs-shootout-games` — Find the highest-scoring games on a slate | [src](typescript/src/dfs-shootout-games.ts) | [src](python/dfs_shootout_games.py) | [src](go/dfs-shootout-games/main.go) | [src](java/src/main/java/com/statapi/cookbook/DfsShootoutGames.java) |
| `dfs-team-stacks` — Rank teams for a stack | [src](typescript/src/dfs-team-stacks.ts) | [src](python/dfs_team_stacks.py) | [src](go/dfs-team-stacks/main.go) | [src](java/src/main/java/com/statapi/cookbook/DfsTeamStacks.java) |
| `mlb-box-score` — Build an MLB box score | [src](typescript/src/mlb-box-score.ts) | [src](python/mlb_box_score.py) | [src](go/mlb-box-score/main.go) | [src](java/src/main/java/com/statapi/cookbook/MlbBoxScore.java) |
| `mlb-roster` — List a MLB team's roster | [src](typescript/src/mlb-roster.ts) | [src](python/mlb_roster.py) | [src](go/mlb-roster/main.go) | [src](java/src/main/java/com/statapi/cookbook/MlbRoster.java) |
| `mlb-season-leaders` — Rank MLB season leaders | [src](typescript/src/mlb-season-leaders.ts) | [src](python/mlb_season_leaders.py) | [src](go/mlb-season-leaders/main.go) | [src](java/src/main/java/com/statapi/cookbook/MlbSeasonLeaders.java) |
| `mlb-team-standings` — Build the MLB standings | [src](typescript/src/mlb-team-standings.ts) | [src](python/mlb_team_standings.py) | [src](go/mlb-team-standings/main.go) | [src](java/src/main/java/com/statapi/cookbook/MlbTeamStandings.java) |
| `nba-box-score` — Build an NBA box score | [src](typescript/src/nba-box-score.ts) | [src](python/nba_box_score.py) | [src](go/nba-box-score/main.go) | [src](java/src/main/java/com/statapi/cookbook/NbaBoxScore.java) |
| `nba-player-game-log` — Read an NBA player's game log | [src](typescript/src/nba-player-game-log.ts) | [src](python/nba_player_game_log.py) | [src](go/nba-player-game-log/main.go) | [src](java/src/main/java/com/statapi/cookbook/NbaPlayerGameLog.java) |
| `nba-season-leaders` — Rank NBA season leaders | [src](typescript/src/nba-season-leaders.ts) | [src](python/nba_season_leaders.py) | [src](go/nba-season-leaders/main.go) | [src](java/src/main/java/com/statapi/cookbook/NbaSeasonLeaders.java) |
| `nba-team-standings` — Build the NBA standings | [src](typescript/src/nba-team-standings.ts) | [src](python/nba_team_standings.py) | [src](go/nba-team-standings/main.go) | [src](java/src/main/java/com/statapi/cookbook/NbaTeamStandings.java) |
| `nfl-box-score` — Build an NFL box score | [src](typescript/src/nfl-box-score.ts) | [src](python/nfl_box_score.py) | [src](go/nfl-box-score/main.go) | [src](java/src/main/java/com/statapi/cookbook/NflBoxScore.java) |
| `nfl-player-game-log` — Read an NFL player's game log | [src](typescript/src/nfl-player-game-log.ts) | [src](python/nfl_player_game_log.py) | [src](go/nfl-player-game-log/main.go) | [src](java/src/main/java/com/statapi/cookbook/NflPlayerGameLog.java) |
| `nfl-roster` — List a NFL team's roster | [src](typescript/src/nfl-roster.ts) | [src](python/nfl_roster.py) | [src](go/nfl-roster/main.go) | [src](java/src/main/java/com/statapi/cookbook/NflRoster.java) |
| `nfl-season-leaders` — Rank NFL season leaders | [src](typescript/src/nfl-season-leaders.ts) | [src](python/nfl_season_leaders.py) | [src](go/nfl-season-leaders/main.go) | [src](java/src/main/java/com/statapi/cookbook/NflSeasonLeaders.java) |
| `nfl-sim-distributions` — See the whole range of outcomes | [src](typescript/src/nfl-sim-distributions.ts) | [src](python/nfl_sim_distributions.py) | [src](go/nfl-sim-distributions/main.go) | [src](java/src/main/java/com/statapi/cookbook/NFLSimDistributions.java) |
| `nfl-sim-scorebox` — Meet six typical games | [src](typescript/src/nfl-sim-scorebox.ts) | [src](python/nfl_sim_scorebox.py) | [src](go/nfl-sim-scorebox/main.go) | [src](java/src/main/java/com/statapi/cookbook/NFLSimScorebox.java) |
| `nfl-sim-summary` — Run your first NFL game simulation | [src](typescript/src/nfl-sim-summary.ts) | [src](python/nfl_sim_summary.py) | [src](go/nfl-sim-summary/main.go) | [src](java/src/main/java/com/statapi/cookbook/NFLSimSummary.java) |
| `nfl-team-standings` — Build the NFL standings | [src](typescript/src/nfl-team-standings.ts) | [src](python/nfl_team_standings.py) | [src](go/nfl-team-standings/main.go) | [src](java/src/main/java/com/statapi/cookbook/NflTeamStandings.java) |
| `nhl-box-score` — Build an NHL box score | [src](typescript/src/nhl-box-score.ts) | [src](python/nhl_box_score.py) | [src](go/nhl-box-score/main.go) | [src](java/src/main/java/com/statapi/cookbook/NhlBoxScore.java) |
| `nhl-season-leaders` — Rank NHL season leaders | [src](typescript/src/nhl-season-leaders.ts) | [src](python/nhl_season_leaders.py) | [src](go/nhl-season-leaders/main.go) | [src](java/src/main/java/com/statapi/cookbook/NhlSeasonLeaders.java) |
| `pga-leaderboard` — Read a PGA golfer's leaderboard finishes | [src](typescript/src/pga-leaderboard.ts) | [src](python/pga_leaderboard.py) | [src](go/pga-leaderboard/main.go) | [src](java/src/main/java/com/statapi/cookbook/PgaLeaderboard.java) |
| `pga-scorecard` — Build a PGA golfer's scorecard | [src](typescript/src/pga-scorecard.ts) | [src](python/pga_scorecard.py) | [src](go/pga-scorecard/main.go) | [src](java/src/main/java/com/statapi/cookbook/PgaScorecard.java) |

## Tier 4 — advanced

| Example | TypeScript | Python | Go | Java |
| --- | --- | --- | --- | --- |
| `dfs-lineup-sim-async-job` — Queue an async 202 SIM job and poll until completion | [src](typescript/src/dfs-lineup-sim-async-job.ts) | [src](python/dfs_lineup_sim_async_job.py) | [src](go/dfs-lineup-sim-async-job/main.go) | [src](java/src/main/java/com/statapi/cookbook/DfsLineupSimAsyncJob.java) |
| `dfs-lineup-sim-gpp-build` — Generate EV-maximized NFL GPP lineups with Prefer: wait | [src](typescript/src/dfs-lineup-sim-gpp-build.ts) | [src](python/dfs_lineup_sim_gpp_build.py) | [src](go/dfs-lineup-sim-gpp-build/main.go) | [src](java/src/main/java/com/statapi/cookbook/DfsLineupSimGppBuild.java) |
| `dfs-lineup-sim-nba-build` — Generate EV-optimized NBA Classic GPP lineups | [src](typescript/src/dfs-lineup-sim-nba-build.ts) | [src](python/dfs_lineup_sim_nba_build.py) | [src](go/dfs-lineup-sim-nba-build/main.go) | [src](java/src/main/java/com/statapi/cookbook/DfsLineupSimNbaBuild.java) |
| `dfs-lineup-sim-pool-reselect` — Re-select SIM portfolios in <50ms via cached pool_id | [src](typescript/src/dfs-lineup-sim-pool-reselect.ts) | [src](python/dfs_lineup_sim_pool_reselect.py) | [src](go/dfs-lineup-sim-pool-reselect/main.go) | [src](java/src/main/java/com/statapi/cookbook/DfsLineupSimPoolReselect.java) |
| `dfs-slate-to-salaries` — Rank a DFS slate by salary and value | [src](typescript/src/dfs-slate-to-salaries.ts) | [src](python/dfs_slate_to_salaries.py) | [src](go/dfs-slate-to-salaries/main.go) | [src](java/src/main/java/com/statapi/cookbook/DfsSlateToSalaries.java) |
| `nba-rolling-averages` — Rolling averages over an NBA game log | [src](typescript/src/nba-rolling-averages.ts) | [src](python/nba_rolling_averages.py) | [src](go/nba-rolling-averages/main.go) | [src](java/src/main/java/com/statapi/cookbook/NbaRollingAverages.java) |
| `nfl-sim-overrides` — Ask a what-if question | [src](typescript/src/nfl-sim-overrides.ts) | [src](python/nfl_sim_overrides.py) | [src](go/nfl-sim-overrides/main.go) | [src](java/src/main/java/com/statapi/cookbook/NFLSimOverrides.java) |
| `nfl-sim-trace` — Replay one simulated game | [src](typescript/src/nfl-sim-trace.ts) | [src](python/nfl_sim_trace.py) | [src](go/nfl-sim-trace/main.go) | [src](java/src/main/java/com/statapi/cookbook/NFLSimTrace.java) |

## License

MIT
