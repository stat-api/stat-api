# stat-api cookbook

Runnable examples for the [stat-api](https://stat-api.com) SDKs in 5 languages.

> 100% generated from the stat-api schema — do not hand-edit any file in this
> repository. Version 0.1.0.

Each example is emitted in every language against the same published SDK
surface, so the code you read is the code the SDK actually exposes. Pick your
language directory for install + run instructions.

Part of the [stat-api monorepo](https://github.com/stat-api/stat-api) — this cookbook lives under
`cookbook/`.

## Showcase — data nobody else has

Examples built on data you can only get here — prediction-market tape, DFS
salaries, and derived rolling windows — each linked in every language.

| Example | What it does | TypeScript | Python | Go | Java | C# |
| --- | --- | --- | --- | --- | --- | --- |
| `nba-kalshi-markets-for-game` | Prediction-market (Kalshi) tape joined to a game via `competition_id`. | [TypeScript](typescript/src/nba-kalshi-markets-for-game.ts) | [Python](python/nba_kalshi_markets_for_game.py) | [Go](go/nba-kalshi-markets-for-game/main.go) | [Java](java/src/main/java/com/statapi/cookbook/NbaKalshiMarketsForGame.java) | [C#](csharp/NbaKalshiMarketsForGame/Program.cs) |
| `dfs-slate-to-salaries` | A DFS slate resolved to player salaries through its required filter sets. | [TypeScript](typescript/src/dfs-slate-to-salaries.ts) | [Python](python/dfs_slate_to_salaries.py) | [Go](go/dfs-slate-to-salaries/main.go) | [Java](java/src/main/java/com/statapi/cookbook/DfsSlateToSalaries.java) | [C#](csharp/DfsSlateToSalaries/Program.cs) |
| `nba-rolling-averages` | Rolling windows computed over a player's game log. | [TypeScript](typescript/src/nba-rolling-averages.ts) | [Python](python/nba_rolling_averages.py) | [Go](go/nba-rolling-averages/main.go) | [Java](java/src/main/java/com/statapi/cookbook/NbaRollingAverages.java) | [C#](csharp/NbaRollingAverages/Program.cs) |

## Tier 1 — first steps

| Example | TypeScript | Python | Go | Java | C# |
| --- | --- | --- | --- | --- | --- |
| `mlb-filtering-basics` — Filter MLB games by season | [src](typescript/src/mlb-filtering-basics.ts) | [src](python/mlb_filtering_basics.py) | [src](go/mlb-filtering-basics/main.go) | [src](java/src/main/java/com/statapi/cookbook/MlbFilteringBasics.java) | [src](csharp/MlbFilteringBasics/Program.cs) |
| `mlb-hello-stat-api` — Hello, MLB | [src](typescript/src/mlb-hello-stat-api.ts) | [src](python/mlb_hello_stat_api.py) | [src](go/mlb-hello-stat-api/main.go) | [src](java/src/main/java/com/statapi/cookbook/MlbHelloStatApi.java) | [src](csharp/MlbHelloStatApi/Program.cs) |
| `mlb-pagination-auto` — Auto-paginate every MLB team | [src](typescript/src/mlb-pagination-auto.ts) | [src](python/mlb_pagination_auto.py) | [src](go/mlb-pagination-auto/main.go) | [src](java/src/main/java/com/statapi/cookbook/MlbPaginationAuto.java) | [src](csharp/MlbPaginationAuto/Program.cs) |
| `nba-filtering-basics` — Filter NBA games by season | [src](typescript/src/nba-filtering-basics.ts) | [src](python/nba_filtering_basics.py) | [src](go/nba-filtering-basics/main.go) | [src](java/src/main/java/com/statapi/cookbook/NbaFilteringBasics.java) | [src](csharp/NbaFilteringBasics/Program.cs) |
| `nba-get-by-id` — Fetch one NBA team by id | [src](typescript/src/nba-get-by-id.ts) | [src](python/nba_get_by_id.py) | [src](go/nba-get-by-id/main.go) | [src](java/src/main/java/com/statapi/cookbook/NbaGetById.java) | [src](csharp/NbaGetById/Program.cs) |
| `nba-hello-stat-api` — Hello, NBA | [src](typescript/src/nba-hello-stat-api.ts) | [src](python/nba_hello_stat_api.py) | [src](go/nba-hello-stat-api/main.go) | [src](java/src/main/java/com/statapi/cookbook/NbaHelloStatApi.java) | [src](csharp/NbaHelloStatApi/Program.cs) |
| `nba-pagination-auto` — Auto-paginate every NBA team | [src](typescript/src/nba-pagination-auto.ts) | [src](python/nba_pagination_auto.py) | [src](go/nba-pagination-auto/main.go) | [src](java/src/main/java/com/statapi/cookbook/NbaPaginationAuto.java) | [src](csharp/NbaPaginationAuto/Program.cs) |
| `nfl-filtering-basics` — Filter NFL games by season | [src](typescript/src/nfl-filtering-basics.ts) | [src](python/nfl_filtering_basics.py) | [src](go/nfl-filtering-basics/main.go) | [src](java/src/main/java/com/statapi/cookbook/NflFilteringBasics.java) | [src](csharp/NflFilteringBasics/Program.cs) |
| `nfl-hello-stat-api` — Hello, NFL | [src](typescript/src/nfl-hello-stat-api.ts) | [src](python/nfl_hello_stat_api.py) | [src](go/nfl-hello-stat-api/main.go) | [src](java/src/main/java/com/statapi/cookbook/NflHelloStatApi.java) | [src](csharp/NflHelloStatApi/Program.cs) |
| `nfl-pagination-auto` — Auto-paginate every NFL team | [src](typescript/src/nfl-pagination-auto.ts) | [src](python/nfl_pagination_auto.py) | [src](go/nfl-pagination-auto/main.go) | [src](java/src/main/java/com/statapi/cookbook/NflPaginationAuto.java) | [src](csharp/NflPaginationAuto/Program.cs) |
| `nhl-filtering-basics` — Filter NHL games by season | [src](typescript/src/nhl-filtering-basics.ts) | [src](python/nhl_filtering_basics.py) | [src](go/nhl-filtering-basics/main.go) | [src](java/src/main/java/com/statapi/cookbook/NhlFilteringBasics.java) | [src](csharp/NhlFilteringBasics/Program.cs) |
| `nhl-hello-stat-api` — Hello, NHL | [src](typescript/src/nhl-hello-stat-api.ts) | [src](python/nhl_hello_stat_api.py) | [src](go/nhl-hello-stat-api/main.go) | [src](java/src/main/java/com/statapi/cookbook/NhlHelloStatApi.java) | [src](csharp/NhlHelloStatApi/Program.cs) |
| `nhl-pagination-auto` — Auto-paginate every NHL team | [src](typescript/src/nhl-pagination-auto.ts) | [src](python/nhl_pagination_auto.py) | [src](go/nhl-pagination-auto/main.go) | [src](java/src/main/java/com/statapi/cookbook/NhlPaginationAuto.java) | [src](csharp/NhlPaginationAuto/Program.cs) |

## Tier 2 — pagination & errors

| Example | TypeScript | Python | Go | Java | C# |
| --- | --- | --- | --- | --- | --- |
| `dfs-required-filter-sets` — Satisfy a required filter set | [src](typescript/src/dfs-required-filter-sets.ts) | [src](python/dfs_required_filter_sets.py) | [src](go/dfs-required-filter-sets/main.go) | [src](java/src/main/java/com/statapi/cookbook/DfsRequiredFilterSets.java) | [src](csharp/DfsRequiredFilterSets/Program.cs) |
| `nba-error-handling` — Handle errors by type | [src](typescript/src/nba-error-handling.ts) | [src](python/nba_error_handling.py) | [src](go/nba-error-handling/main.go) | [src](java/src/main/java/com/statapi/cookbook/NbaErrorHandling.java) | [src](csharp/NbaErrorHandling/Program.cs) |
| `nba-pagination-manual` — Paginate by hand with from_id | [src](typescript/src/nba-pagination-manual.ts) | [src](python/nba_pagination_manual.py) | [src](go/nba-pagination-manual/main.go) | [src](java/src/main/java/com/statapi/cookbook/NbaPaginationManual.java) | [src](csharp/NbaPaginationManual/Program.cs) |
| `nba-quota-headers` — Read your quota off a response | [src](typescript/src/nba-quota-headers.ts) | [src](python/nba_quota_headers.py) | [src](go/nba-quota-headers/main.go) | [src](java/src/main/java/com/statapi/cookbook/NbaQuotaHeaders.java) | [src](csharp/NbaQuotaHeaders/Program.cs) |

## Tier 3 — worked examples

| Example | TypeScript | Python | Go | Java | C# |
| --- | --- | --- | --- | --- | --- |
| `mlb-box-score` — Build an MLB box score | [src](typescript/src/mlb-box-score.ts) | [src](python/mlb_box_score.py) | [src](go/mlb-box-score/main.go) | [src](java/src/main/java/com/statapi/cookbook/MlbBoxScore.java) | [src](csharp/MlbBoxScore/Program.cs) |
| `mlb-roster` — List a MLB team's roster | [src](typescript/src/mlb-roster.ts) | [src](python/mlb_roster.py) | [src](go/mlb-roster/main.go) | [src](java/src/main/java/com/statapi/cookbook/MlbRoster.java) | [src](csharp/MlbRoster/Program.cs) |
| `mlb-season-leaders` — Rank MLB season leaders | [src](typescript/src/mlb-season-leaders.ts) | [src](python/mlb_season_leaders.py) | [src](go/mlb-season-leaders/main.go) | [src](java/src/main/java/com/statapi/cookbook/MlbSeasonLeaders.java) | [src](csharp/MlbSeasonLeaders/Program.cs) |
| `mlb-team-standings` — Build the MLB standings | [src](typescript/src/mlb-team-standings.ts) | [src](python/mlb_team_standings.py) | [src](go/mlb-team-standings/main.go) | [src](java/src/main/java/com/statapi/cookbook/MlbTeamStandings.java) | [src](csharp/MlbTeamStandings/Program.cs) |
| `nba-box-score` — Build an NBA box score | [src](typescript/src/nba-box-score.ts) | [src](python/nba_box_score.py) | [src](go/nba-box-score/main.go) | [src](java/src/main/java/com/statapi/cookbook/NbaBoxScore.java) | [src](csharp/NbaBoxScore/Program.cs) |
| `nba-player-game-log` — Read an NBA player's game log | [src](typescript/src/nba-player-game-log.ts) | [src](python/nba_player_game_log.py) | [src](go/nba-player-game-log/main.go) | [src](java/src/main/java/com/statapi/cookbook/NbaPlayerGameLog.java) | [src](csharp/NbaPlayerGameLog/Program.cs) |
| `nba-season-leaders` — Rank NBA season leaders | [src](typescript/src/nba-season-leaders.ts) | [src](python/nba_season_leaders.py) | [src](go/nba-season-leaders/main.go) | [src](java/src/main/java/com/statapi/cookbook/NbaSeasonLeaders.java) | [src](csharp/NbaSeasonLeaders/Program.cs) |
| `nba-team-standings` — Build the NBA standings | [src](typescript/src/nba-team-standings.ts) | [src](python/nba_team_standings.py) | [src](go/nba-team-standings/main.go) | [src](java/src/main/java/com/statapi/cookbook/NbaTeamStandings.java) | [src](csharp/NbaTeamStandings/Program.cs) |
| `nfl-box-score` — Build an NFL box score | [src](typescript/src/nfl-box-score.ts) | [src](python/nfl_box_score.py) | [src](go/nfl-box-score/main.go) | [src](java/src/main/java/com/statapi/cookbook/NflBoxScore.java) | [src](csharp/NflBoxScore/Program.cs) |
| `nfl-player-game-log` — Read an NFL player's game log | [src](typescript/src/nfl-player-game-log.ts) | [src](python/nfl_player_game_log.py) | [src](go/nfl-player-game-log/main.go) | [src](java/src/main/java/com/statapi/cookbook/NflPlayerGameLog.java) | [src](csharp/NflPlayerGameLog/Program.cs) |
| `nfl-roster` — List a NFL team's roster | [src](typescript/src/nfl-roster.ts) | [src](python/nfl_roster.py) | [src](go/nfl-roster/main.go) | [src](java/src/main/java/com/statapi/cookbook/NflRoster.java) | [src](csharp/NflRoster/Program.cs) |
| `nfl-season-leaders` — Rank NFL season leaders | [src](typescript/src/nfl-season-leaders.ts) | [src](python/nfl_season_leaders.py) | [src](go/nfl-season-leaders/main.go) | [src](java/src/main/java/com/statapi/cookbook/NflSeasonLeaders.java) | [src](csharp/NflSeasonLeaders/Program.cs) |
| `nfl-team-standings` — Build the NFL standings | [src](typescript/src/nfl-team-standings.ts) | [src](python/nfl_team_standings.py) | [src](go/nfl-team-standings/main.go) | [src](java/src/main/java/com/statapi/cookbook/NflTeamStandings.java) | [src](csharp/NflTeamStandings/Program.cs) |
| `nhl-box-score` — Build an NHL box score | [src](typescript/src/nhl-box-score.ts) | [src](python/nhl_box_score.py) | [src](go/nhl-box-score/main.go) | [src](java/src/main/java/com/statapi/cookbook/NhlBoxScore.java) | [src](csharp/NhlBoxScore/Program.cs) |
| `nhl-season-leaders` — Rank NHL season leaders | [src](typescript/src/nhl-season-leaders.ts) | [src](python/nhl_season_leaders.py) | [src](go/nhl-season-leaders/main.go) | [src](java/src/main/java/com/statapi/cookbook/NhlSeasonLeaders.java) | [src](csharp/NhlSeasonLeaders/Program.cs) |
| `pga-leaderboard` — Read a PGA golfer's leaderboard finishes | [src](typescript/src/pga-leaderboard.ts) | [src](python/pga_leaderboard.py) | [src](go/pga-leaderboard/main.go) | [src](java/src/main/java/com/statapi/cookbook/PgaLeaderboard.java) | [src](csharp/PgaLeaderboard/Program.cs) |
| `pga-scorecard` — Build a PGA golfer's scorecard | [src](typescript/src/pga-scorecard.ts) | [src](python/pga_scorecard.py) | [src](go/pga-scorecard/main.go) | [src](java/src/main/java/com/statapi/cookbook/PgaScorecard.java) | [src](csharp/PgaScorecard/Program.cs) |

## Tier 4 — advanced

| Example | TypeScript | Python | Go | Java | C# |
| --- | --- | --- | --- | --- | --- |
| `dfs-slate-to-salaries` — Rank a DFS slate by salary and value | [src](typescript/src/dfs-slate-to-salaries.ts) | [src](python/dfs_slate_to_salaries.py) | [src](go/dfs-slate-to-salaries/main.go) | [src](java/src/main/java/com/statapi/cookbook/DfsSlateToSalaries.java) | [src](csharp/DfsSlateToSalaries/Program.cs) |
| `nba-kalshi-markets-for-game` — Find Kalshi markets for an NBA game | [src](typescript/src/nba-kalshi-markets-for-game.ts) | [src](python/nba_kalshi_markets_for_game.py) | [src](go/nba-kalshi-markets-for-game/main.go) | [src](java/src/main/java/com/statapi/cookbook/NbaKalshiMarketsForGame.java) | [src](csharp/NbaKalshiMarketsForGame/Program.cs) |
| `nba-rolling-averages` — Rolling averages over an NBA game log | [src](typescript/src/nba-rolling-averages.ts) | [src](python/nba_rolling_averages.py) | [src](go/nba-rolling-averages/main.go) | [src](java/src/main/java/com/statapi/cookbook/NbaRollingAverages.java) | [src](csharp/NbaRollingAverages/Program.cs) |

## License

MIT
