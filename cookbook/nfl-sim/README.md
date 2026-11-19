# NFL sim tutorial (beta)

> 100% generated from schema/api/sim/guide.yml and schema/api/examples/sim-*.yml. Do not hand-edit. Version 0.3.0.

The recipes below teach the NFL play-by-play simulator, one call at a time. Each recipe runs on the free game and prints real output. Each recipe is one program in TypeScript, Python, Go and Java, and the four programs print the same lines.

## Start here

### What you need

An API key from stat-api.com, and one of the four SDKs. Set the key in the environment variable STAT_API_KEY. A free key runs every recipe, because every recipe uses the free game.

### The free game

Game 43418 (2026 Week 1 DEN @ KC) is free for every caller on `run`. Other games need a Pro or Elite plan, or the DFS add-on. The `weeks` call is free for all games.

### How a run works

A run plays the game `n_draws` times. The limits are 50 to 10000, and the default is 2500. The same `seed` gives the same draws. A run returns the sections that you ask for in `include`: summary, distributions, scorebox and play by play (`pbp`). Every section is on by default, so turn off the sections that you do not read.

### What to trust

The sim is a beta. Each `run` answer carries the header `x-sim-engine`, which names the engine release, and the header `x-sim-validation`, which names the page that says what is validated. A game is listed and runs only after its betting line has posted. A game without a line answers `409`.

### Your first call, with curl

Two calls need no SDK. The first lists the weeks and names the free game. The second runs the free game 1,000 times and returns the summary only.

```sh
curl -s -H "Authorization: Bearer $STAT_API_KEY" \
  "https://api.stat-api.com/api/v1/nfl/weeks?season=2026"

curl -s -X POST "https://api.stat-api.com/api/v1/sim/nfl/run" \
  -H "Authorization: Bearer $STAT_API_KEY" \
  -H "Content-Type: application/json" \
  -d '{"game_id": 43418, "n_draws": 1000, "seed": 42,
       "include": {"summary": true, "distributions": false,
                   "scorebox": false, "pbp": false}}'
```

### Run a recipe

Run these from the `cookbook` folder. Set `STAT_API_KEY` first. The commands run the first recipe. To run another recipe, use its program from the table below.

```sh
export STAT_API_KEY=...
(cd typescript && npm install && npx tsx src/nfl-sim-summary.ts)
(cd python && pip install -r requirements.txt && python nfl_sim_summary.py)
(cd go && go run ./nfl-sim-summary)
(cd java && gradle run -PmainClass=com.statapi.cookbook.NFLSimSummary)
```

## The recipes

| # | Recipe | You learn | Calls | Programs |
| --- | --- | --- | --- | --- |
| 1 | [Run your first NFL game simulation](#1-run-your-first-nfl-game-simulation) | Find the free game, run it without manually assembling inputs, and read the win chances and player projections. | `weeks`, `run` | [TypeScript](../typescript/src/nfl-sim-summary.ts) · [Python](../python/nfl_sim_summary.py) · [Go](../go/nfl-sim-summary/main.go) · [Java](../java/src/main/java/com/statapi/cookbook/NFLSimSummary.java) |
| 2 | [See the whole range of outcomes](#2-see-the-whole-range-of-outcomes) | Read the histogram of a game total and of a player, with its percentiles and the share above the market line. | `weeks`, `run` | [TypeScript](../typescript/src/nfl-sim-distributions.ts) · [Python](../python/nfl_sim_distributions.py) · [Go](../go/nfl-sim-distributions/main.go) · [Java](../java/src/main/java/com/statapi/cookbook/NFLSimDistributions.java) |
| 3 | [Meet six typical games](#3-meet-six-typical-games) | Read six whole games that stand for the middle of the range, its tails and the overtime case. | `weeks`, `run` | [TypeScript](../typescript/src/nfl-sim-scorebox.ts) · [Python](../python/nfl_sim_scorebox.py) · [Go](../go/nfl-sim-scorebox/main.go) · [Java](../java/src/main/java/com/statapi/cookbook/NFLSimScorebox.java) |
| 4 | [Replay one simulated game](#4-replay-one-simulated-game) | Replay one draw of a run, drive by drive and play by play. | `weeks`, `run` | [TypeScript](../typescript/src/nfl-sim-trace.ts) · [Python](../python/nfl_sim_trace.py) · [Go](../go/nfl-sim-trace/main.go) · [Java](../java/src/main/java/com/statapi/cookbook/NFLSimTrace.java) |
| 5 | [Ask a what-if question](#5-ask-a-what-if-question) | Change the spread of the game, and read the exact change in the win chance and in the points, because both runs use the same seed. | `weeks`, `run` | [TypeScript](../typescript/src/nfl-sim-overrides.ts) · [Python](../python/nfl_sim_overrides.py) · [Go](../go/nfl-sim-overrides/main.go) · [Java](../java/src/main/java/com/statapi/cookbook/NFLSimOverrides.java) |

## 1. Run your first NFL game simulation

The sim plays one NFL game many times and counts what happens. Start with the free game. The `weeks` call names it and `run` plays the game with zero manual inputs required. Every section of a run is on by default, so this recipe asks for the summary only.

- **What it does.** Find the free game (2026 Week 1 DEN @ KC, game 43418), run the game 5,000 times, and read the summary.
- **When to use it.** You want the chance that the home team wins, covers or goes over the market total, and the player projections.
- **What it returns.** A summary with the simulated total, the win, cover and over chances, and one row of statistics per player.
- **Calls.** [`weeks`](https://stat-api.com/docs/api/nfl/seasons/#endpoint-nfl-seasons-weeks), [`run`](https://stat-api.com/docs/api/sim/#endpoint-sim-nfl-run)
- **Programs.** [TypeScript](../typescript/src/nfl-sim-summary.ts) · [Python](../python/nfl_sim_summary.py) · [Go](../go/nfl-sim-summary/main.go) · [Java](../java/src/main/java/com/statapi/cookbook/NFLSimSummary.java)

1. **Find the free game.** The `weeks` call is free. It lists the games of a season and names the free game in `free_game_id`.
2. **Run the game 5,000 times.** The `run` call plays the game `n_draws` times. A `seed` makes the run repeatable. Every section of `include` is on by default, so switch on the summary and switch off the rest.
3. **Rank the players.** The summary holds one row per player. Sort by projected mean receiving yards and inspect the median and 90th percentile.

Sample output. Engine nfl-play-sim/2.8.0, game 43418, made on 2026-11-18. The four programs print the same lines.

```text
Season 2026 | free game 43418 | 163 games
Simulated total 43.16 (SD 13.00) | home win 55.1% | home cover 50.8% | over 50.5%
Box-score checks: passed
Top 5 players by mean receiving yards:
  Player 1311 (WR/WR1) | mean 59.1 | median 54.0 | 90th percentile 109.0
  Player 2455 (WR/WR1) | mean 54.8 | median 50.0 | 90th percentile 100.0
  Player 79 (TE/TE1) | mean 46.2 | median 41.0 | 90th percentile 87.0
  Player 525 (WR/WR2) | mean 45.9 | median 40.0 | 90th percentile 88.0
  Player 3156 (WR/WR2) | mean 41.8 | median 36.0 | 90th percentile 85.0
```

Next: [See the whole range of outcomes](#2-see-the-whole-range-of-outcomes).

## 2. See the whole range of outcomes

A projection gives one number. The sim gives a draw for every one of its runs, so it can also tell you how wide the range is. Set `distributions` in `include` to get one curve for the game total, the spread and the team stats, and one curve for each player. A curve holds the mean, the percentiles, the skew and a histogram. The `p_over_vegas` field is the share of draws above the market line.

- **What it does.** Run the free game (2026 Week 1 DEN @ KC) 5,000 times and read the histogram of the game total and the range of the best fantasy player.
- **When to use it.** You price an alternate line or a prop, or you draw a bell curve, and the mean alone is not enough.
- **What it returns.** A curve for the game total and a curve for each player, each with the mean, the percentiles, the skew, the histogram bins, and the share of draws above the market line.
- **Calls.** [`weeks`](https://stat-api.com/docs/api/nfl/seasons/#endpoint-nfl-seasons-weeks), [`run`](https://stat-api.com/docs/api/sim/#endpoint-sim-nfl-run)
- **Programs.** [TypeScript](../typescript/src/nfl-sim-distributions.ts) · [Python](../python/nfl_sim_distributions.py) · [Go](../go/nfl-sim-distributions/main.go) · [Java](../java/src/main/java/com/statapi/cookbook/NFLSimDistributions.java)

1. **Ask for the distributions.** Run the free game 5,000 times and switch on the distributions section only.
2. **Read the histogram.** A curve holds `bins`. Each bin has the share of draws in it (`prob`). This prints the bins with 2% of the draws or more, one # for each 1%.
3. **Find a draw that you can replay.** Each bin has a `sample_draw_idx`, the index of one draw that landed in the bin. The trace recipe replays a draw, play by play.
4. **Read the range of a player.** Each player has bell curves for passing, rushing, and receiving yards. The skew tells you if the upside is longer than the downside.

Sample output. Engine nfl-play-sim/2.8.0, game 43418, made on 2026-11-18. The four programs print the same lines.

```text
Game total | mean 43.16, SD 13.00 | 10th 27.00, median 43.00, 90th 60.00
Market total 42.7 | simulated chance of the over 50.5%
Share of draws by game total, bins with 2% or more:
  [ 17.46,  21.62)  2.38% ##
  [ 21.62,  25.77)  4.04% ####
  [ 25.77,  29.92)  6.26% ######
  [ 29.92,  34.08) 11.54% ###########
  [ 34.08,  38.23) 11.08% ###########
  [ 38.23,  42.38) 12.58% ############
  [ 42.38,  46.54) 12.14% ############
  [ 46.54,  50.69) 11.04% ###########
  [ 50.69,  54.85)  8.32% ########
  [ 54.85,  59.00)  6.58% ######
  [ 59.00,  63.15)  5.74% #####
  [ 63.15,  67.31)  2.98% ##
Median bin: sample draw 15
Top receiver 1311 (WR) rec yds | 10th 16.00, median 54.00, 90th 109.00 | skew 0.83
```

Next: [Meet six typical games](#3-meet-six-typical-games).

## 3. Meet six typical games

A histogram says how often a total happens. A scorebox shows a whole game. With `scorebox` in `include` a run returns six archetypes: the median game, the 95th percentile shootout, the 5th percentile defensive game, a home blowout, an away blowout and an overtime game. Each one is a real draw of the run, so you can replay it with the same `seed` in the trace recipe.

- **What it does.** Run the free game (2026 Week 1 DEN @ KC) 2,000 times and read six whole games from the run: the median game, a shootout, a defensive game, two blowouts and an overtime game.
- **When to use it.** You want to see what the tails of the range look like as games, with a score by quarter, and not as numbers.
- **What it returns.** Six archetype games. Each one has its draw index, its score by quarter for both teams, its total and its home margin.
- **Calls.** [`weeks`](https://stat-api.com/docs/api/nfl/seasons/#endpoint-nfl-seasons-weeks), [`run`](https://stat-api.com/docs/api/sim/#endpoint-sim-nfl-run)
- **Programs.** [TypeScript](../typescript/src/nfl-sim-scorebox.ts) · [Python](../python/nfl_sim_scorebox.py) · [Go](../go/nfl-sim-scorebox/main.go) · [Java](../java/src/main/java/com/statapi/cookbook/NFLSimScorebox.java)

1. **Ask for the scorebox.** Run the free game 2,000 times and switch on the scorebox section only. The `scorebox_limit` field is the number of extra draws that the section returns next to the six archetypes.
2. **Read the six archetypes.** Each archetype has a score by quarter for both teams: Q1, Q2, Q3, Q4, overtime, and the total. The home margin is positive when the home team wins.

Sample output. Engine nfl-play-sim/2.8.0, game 43418, made on 2026-11-18. The four programs print the same lines.

```text
Six typical games of 2000 draws | scores are Q1-Q2-Q3-Q4-OT-Total | DEN at KC
  median | draw 140 | DEN 7-3-3-7-0-20 | KC 7-3-3-9-0-22 | home margin +2
  shootout_p95 | draw 1930 | DEN 7-14-10-10-0-41 | KC 7-7-7-3-0-24 | home margin -17
  defensive_p05 | draw 173 | DEN 7-0-3-3-0-13 | KC 0-0-7-3-0-10 | home margin -3
  home_blowout | draw 372 | DEN 0-9-3-0-0-12 | KC 14-14-3-3-0-34 | home margin +22
  away_blowout | draw 54 | DEN 14-3-0-14-0-31 | KC 3-3-7-0-0-13 | home margin -18
  overtime | draw 37 | DEN 0-10-7-14-0-31 | KC 7-7-3-14-7-38 | home margin +7
```

Next: [Replay one simulated game](#4-replay-one-simulated-game).

## 4. Replay one simulated game

A run keeps the summary statistics of its draws unless `include.pbp` is turned on. Pass a specific `draw_idx` inside `include.pbp_draws` with the same `seed` and `n_draws` to `run` to replay that draw's drives and every play. The first call asks for the scorebox, because the six archetypes carry the index of a draw worth replaying.

- **What it does.** Pick the shootout game from a run of the free game (2026 Week 1 DEN @ KC), then replay that one draw via `run` (`include.pbp` + `pbp_draws`): its drives and its plays.
- **When to use it.** A number from a run looks odd and you want to see the game behind it, or you want to show one simulated game to a reader.
- **What it returns.** The score by quarter, the drives with their results, and the plays with the clock, the down and distance, the field position and a description.
- **Calls.** [`weeks`](https://stat-api.com/docs/api/nfl/seasons/#endpoint-nfl-seasons-weeks), [`run`](https://stat-api.com/docs/api/sim/#endpoint-sim-nfl-run)
- **Programs.** [TypeScript](../typescript/src/nfl-sim-trace.ts) · [Python](../python/nfl_sim_trace.py) · [Go](../go/nfl-sim-trace/main.go) · [Java](../java/src/main/java/com/statapi/cookbook/NFLSimTrace.java)

1. **Pick a draw from a run.** Run the free game 2,000 times with the scorebox on. Take the draw of the 95th percentile shootout. Its `draw_idx` is passed to `include.pbp_draws` in the next step.
2. **Replay the draw with run.** Pass the game, the same `seed`, the same `n_draws`, and `include.pbp: true` with `pbp_draws: [shootout.draw_idx]` to `run`. Passing the same seed and draw count replays that exact draw in `output.pbp[0]`.
3. **Read the drives and the plays.** A drive has its team, its start, its length and its result. A play has the clock, the down and distance, the field position and a description.

Sample output. Engine nfl-play-sim/2.8.0, game 43418, made on 2026-11-18. The four programs print the same lines.

```text
Replay draw 1930, the 95th percentile shootout of 2000 draws
DEN 41 - KC 24 | 24 drives | 163 plays
First 5 drives:
  1. KC | Q1 15:00 | 9 plays, 74 yds | TOUCHDOWN | DEN 0 - KC 7
  2. DEN | Q1 10:08 | 8 plays, 80 yds | TOUCHDOWN | DEN 7 - KC 7
  3. KC | Q1 07:34 | 7 plays, 26 yds | TURNOVER | DEN 7 - KC 7
  4. DEN | Q1 04:26 | 10 plays, 50 yds | TOUCHDOWN | DEN 14 - KC 7
  5. KC | Q2 14:54 | 4 plays, 6 yds | PUNT | DEN 14 - KC 7
First 5 plays:
  15:00 | 1st & 10 | OWN 26 | Rush for 4 yds
  14:17 | 2nd & 6 | OWN 30 | Pass complete for 14 yds (13 air, 1 YAC), 1ST DOWN
  13:31 | 1st & 10 | OWN 44 | Pass complete for 19 yds (19 air, 0 YAC), 1ST DOWN
  13:11 | 1st & 10 | OPP 37 | Pass complete for 19 yds (-1 air, 20 YAC), 1ST DOWN
  12:28 | 1st & 10 | OPP 18 | Rush for 3 yds
```

Next: [Ask a what-if question](#5-ask-a-what-if-question).

## 5. Ask a what-if question

Flat top-level and `home`/`away`/`players` fields on `run` override the game's hydrated inputs: the market line (`total`, `home_spread`, `home_score`, `away_score`), the weather, team tempo/pass rate, and player availability or props. Run the game twice with the same `seed`, once without overrides and once with them. Both runs use the same random draws, so each difference between the two summaries comes from your change and not from luck. This is called common random numbers. The sim solves each game to the market, so the team totals follow a new spread.

- **What it does.** Run the free game (2026 Week 1 DEN @ KC) twice with the same seed: once as it is, and once with the home team as a 6.5 point favorite.
- **When to use it.** You want the exact effect of one change, such as a moved line, a different pace, or a player who sits, with no noise from the random draws.
- **What it returns.** Two summaries. Every difference between them comes from the override, because both runs use the same draws.
- **Calls.** [`weeks`](https://stat-api.com/docs/api/nfl/seasons/#endpoint-nfl-seasons-weeks), [`run`](https://stat-api.com/docs/api/sim/#endpoint-sim-nfl-run)
- **Programs.** [TypeScript](../typescript/src/nfl-sim-overrides.ts) · [Python](../python/nfl_sim_overrides.py) · [Go](../go/nfl-sim-overrides/main.go) · [Java](../java/src/main/java/com/statapi/cookbook/NFLSimOverrides.java)

1. **Run the baseline.** The baseline is the game as the market and the inputs have it. It uses `seed` 42.
2. **Run the what-if with the same seed.** Set the spread to 6.5 points for the home team. Keep `seed` 42 and `n_draws` 5000. The sim solves the game to the market, so the team totals follow the new spread.
3. **Read the exact change.** Compare the two summaries. The difference is the effect of the change, with no sampling noise.

Sample output. Engine nfl-play-sim/2.8.0, game 43418, made on 2026-11-18. The four programs print the same lines.

```text
What if KC were a 6.5 point favorite? Seed 42 on both runs.
Home win chance | 55.1% -> 70.4% (+15.3 points)
KC points | 22.41 -> 24.84 (+2.43)
DEN points | 20.74 -> 18.47 (-2.27)
```

## Where to go next

- The API reference: https://stat-api.com/docs/api/sim/
- The tutorial on the site: https://stat-api.com/docs/cookbook/sim/
