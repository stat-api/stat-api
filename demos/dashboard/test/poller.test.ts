// Poller gates (all mocked, no network):
//   (a) per-cycle request budget + window gating
//   (b) quota self-throttle
//   (c) snapshot diff → SSE patch
import { describe, expect, test } from 'bun:test';

import { loadConfig, type Config } from '../src/config.ts';
import { Poller } from '../src/poller.ts';
import { SnapshotStore } from '../src/snapshot.ts';
import type { Quota, SseEvent } from '../src/contract.ts';
import { emptyState, makeMockApi, type MockState } from './mock-api.ts';

// A July evening (ET): MLB in season, NBA + NFL offseason.
const NOW = new Date('2026-07-15T22:00:00.000Z');
const HOUR = 3_600_000;

const LIVE_START = new Date(NOW.getTime() - HOUR).toISOString(); // started an hour ago
const SCHEDULED_START = new Date(NOW.getTime() + 3 * HOUR).toISOString(); // 3h out
const FINAL_START = new Date(NOW.getTime() - 6 * HOUR).toISOString(); // this afternoon

function config(): Config {
  return loadConfig({});
}

function makePoller(state: MockState, opts: { quota?: () => Quota | null; logger?: (m: string) => void } = {}) {
  const { api, counts } = makeMockApi(state);
  const store = new SnapshotStore(['mlb', 'nba', 'nfl']);
  const poller = new Poller({
    api,
    store,
    config: config(),
    now: () => NOW,
    quota: opts.quota ?? (() => null),
    logger: opts.logger ?? (() => {}),
  });
  return { poller, store, counts };
}

/** Slate with one live, one far-future scheduled, and one finished MLB game,
 *  plus the reference data the detail tiers resolve against. */
function fullSlate(): MockState {
  const state = emptyState();
  state.mlbTeams = [
    { id: 105, full_name: 'Houston Astros', abbreviation: 'HOU' },
    { id: 106, full_name: 'Seattle Mariners', abbreviation: 'SEA' },
    { id: 101, full_name: 'New York Yankees', abbreviation: 'NYY' },
    { id: 102, full_name: 'Boston Red Sox', abbreviation: 'BOS' },
    { id: 103, full_name: 'Chicago Cubs', abbreviation: 'CHC' },
    { id: 104, full_name: 'St. Louis Cardinals', abbreviation: 'STL' },
  ];
  state.mlbGames = [
    { id: 9001, away_team_id: 105, home_team_id: 106, away_team_score: 3, home_team_score: 2, status: 'In Progress', game_time: LIVE_START },
    { id: 9002, away_team_id: 101, home_team_id: 102, away_team_score: 0, home_team_score: 0, status: 'Scheduled', game_time: SCHEDULED_START },
    { id: 9003, away_team_id: 103, home_team_id: 104, away_team_score: 5, home_team_score: 4, status: 'Final', game_time: FINAL_START },
  ];
  state.operators = [
    { id: 1, name: 'DraftKings' },
    { id: 2, name: 'FanDuel' },
  ];
  state.mlbLines = [
    { id: 1, game_id: 9001, operator_id: 1, captured_at: '2026-07-15T20:30:00.000Z', spread: '1.5', total: '8.5', moneyline_home: 120, moneyline_away: -140 },
    { id: 2, game_id: 9001, operator_id: 1, captured_at: '2026-07-15T21:00:00.000Z', spread: '1.5', total: '9.0', moneyline_home: 118, moneyline_away: -138 },
    { id: 3, game_id: 9001, operator_id: 2, captured_at: '2026-07-15T20:30:00.000Z', spread: '1.5', total: '8.5', moneyline_home: 122, moneyline_away: -142 },
  ];
  state.mlbPlayers = [
    { id: 501, team_id: 105, full_name: 'Jose Altuve', primary_position: '2B' },
    { id: 502, team_id: 105, full_name: 'Yordan Alvarez', primary_position: 'DH' },
    { id: 503, team_id: 106, full_name: 'Julio Rodriguez', primary_position: 'CF' },
    { id: 504, team_id: 106, full_name: 'Cal Raleigh', primary_position: 'C' },
  ];
  state.mlbBatters = [
    { id: 1, game_id: 9001, player_id: 501, team_id: 105, at_bats: 4, runs: 1, hits: 2, runs_batted_in: 1, home_runs: 0, walks: 1, strikeouts: 1, batting_average: '0.312' },
    { id: 2, game_id: 9001, player_id: 502, team_id: 105, at_bats: 4, runs: 2, hits: 3, runs_batted_in: 2, home_runs: 1, walks: 0, strikeouts: 0, batting_average: '0.298' },
    { id: 3, game_id: 9001, player_id: 503, team_id: 106, at_bats: 3, runs: 0, hits: 1, runs_batted_in: 0, home_runs: 0, walks: 1, strikeouts: 1, batting_average: '0.276' },
    { id: 4, game_id: 9001, player_id: 504, team_id: 106, at_bats: 4, runs: 1, hits: 1, runs_batted_in: 1, home_runs: 1, walks: 0, strikeouts: 2, batting_average: '0.244' },
  ];
  return state;
}

describe('request budget + window gating', () => {
  test('scoreboard cycle: exactly one games.list per in-season league, zero detail calls', async () => {
    const { poller, store, counts } = makePoller(fullSlate());
    await poller.runScoreboardCycle(NOW);

    // MLB is the only in-season league in July → one games list, one teams list.
    expect(counts['mlb.games']).toBe(1);
    expect(counts['mlb.teams']).toBe(1);
    // Offseason leagues are never polled.
    expect(counts['nba.games'] ?? 0).toBe(0);
    expect(counts['nfl.games'] ?? 0).toBe(0);
    // Scoreboard tier touches no per-game detail endpoints.
    expect(counts['mlb.game_lines'] ?? 0).toBe(0);
    expect(counts['mlb.game_player_batter_stats'] ?? 0).toBe(0);
    expect(counts['reference.operators'] ?? 0).toBe(0);

    const snap = store.snapshot();
    expect(snap.leagues.mlb.status).toBe('in-season');
    expect(snap.leagues.mlb.games).toHaveLength(3);
    expect(snap.leagues.nba.status).toBe('offseason');
    expect(snap.leagues.nfl.status).toBe('offseason');
  });

  test('window gating: scheduled-far and finished games trigger zero line/box calls', async () => {
    const state = emptyState();
    state.mlbTeams = fullSlate().mlbTeams;
    // Only out-of-window games: one far-future scheduled, one already final.
    state.mlbGames = [
      { id: 9002, away_team_id: 101, home_team_id: 102, away_team_score: 0, home_team_score: 0, status: 'Scheduled', game_time: SCHEDULED_START },
      { id: 9003, away_team_id: 103, home_team_id: 104, away_team_score: 5, home_team_score: 4, status: 'Final', game_time: FINAL_START },
    ];
    const { poller, counts } = makePoller(state);

    await poller.runScoreboardCycle(NOW);
    await poller.runLinesCycle(NOW);
    await poller.runBoxCycle(NOW);

    expect(counts['mlb.game_lines'] ?? 0).toBe(0);
    expect(counts['mlb.game_player_batter_stats'] ?? 0).toBe(0);
    // Operators are only fetched when there is an in-window game to price.
    expect(counts['reference.operators'] ?? 0).toBe(0);
  });

  test('window gating: a live game is polled once for lines and box, and detail is attached', async () => {
    const state = emptyState();
    const full = fullSlate();
    state.mlbTeams = full.mlbTeams;
    state.operators = full.operators;
    state.mlbLines = full.mlbLines;
    state.mlbPlayers = full.mlbPlayers;
    state.mlbBatters = full.mlbBatters;
    state.mlbGames = [full.mlbGames[0]!]; // the live game only
    const { poller, store, counts } = makePoller(state);

    await poller.runScoreboardCycle(NOW);
    await poller.runLinesCycle(NOW);
    await poller.runBoxCycle(NOW);

    expect(counts['mlb.game_lines']).toBe(1);
    expect(counts['mlb.game_player_batter_stats']).toBe(1);
    expect(counts['reference.operators']).toBe(1);
    // One roster list per team (2 teams), fetched once and cached.
    expect(counts['mlb.players']).toBe(2);

    const game = store.snapshot().leagues.mlb.games[0]!;
    expect(game.status).toBe('live');
    expect(game.detail).toBeDefined();
    expect(game.detail!.lines.length).toBeGreaterThanOrEqual(1);
    expect(game.detail!.box).toHaveLength(4);
    expect(game.detail!.pulse.length).toBeGreaterThanOrEqual(1);
    // Box lines carry resolved player names, not numeric ids.
    expect(game.detail!.box.some(b => b.player === 'Jose Altuve')).toBe(true);
    // Pulse is a market-implied home win probability in [0, 1].
    for (const point of game.detail!.pulse) {
      expect(point.source).toBe('sportsbook-implied');
      expect(point.homeWinProb).toBeGreaterThan(0);
      expect(point.homeWinProb).toBeLessThan(1);
    }
  });
});

describe('nfl scoreboard query shape', () => {
  // NFL has no `day` list filter (games are week-based). A November Thursday:
  // NFL, NBA, and MLB are all in season.
  const NOV = new Date('2025-11-13T18:00:00.000Z'); // ET 2025-11-13 → day 20251113
  const TODAY = 20251113;

  function nflSlate(): MockState {
    const state = emptyState();
    state.nflTeams = [
      { id: 20, full_name: 'Buffalo Bills', abbreviation: 'BUF' },
      { id: 21, full_name: 'Miami Dolphins', abbreviation: 'MIA' },
      { id: 22, full_name: 'Kansas City Chiefs', abbreviation: 'KC' },
      { id: 23, full_name: 'Denver Broncos', abbreviation: 'DEN' },
    ];
    // Anchor: last week's finished game (week 10). This week (11) has a Thursday
    // game today and a Sunday game later — neither is Final yet.
    state.nflGames = [
      { id: 200, away_team_id: 22, home_team_id: 23, away_team_score: 24, home_team_score: 17, status: 'Final', season_id: 2031, season_type: 'regular', week: 10, day: 20251110, game_time: '2025-11-10T20:15:00.000Z', start_time: '2025-11-10T20:15:00.000Z', away_team: 'Kansas City Chiefs', home_team: 'Denver Broncos' },
      { id: 210, away_team_id: 20, home_team_id: 21, away_team_score: 0, home_team_score: 0, status: 'Scheduled', season_id: 2031, season_type: 'regular', week: 11, day: TODAY, game_time: '2025-11-13T20:15:00.000Z', start_time: '2025-11-13T20:15:00.000Z', away_team: 'Buffalo Bills', home_team: 'Miami Dolphins' },
      { id: 211, away_team_id: 22, home_team_id: 20, away_team_score: 0, home_team_score: 0, status: 'Scheduled', season_id: 2031, season_type: 'regular', week: 11, day: 20251116, game_time: '2025-11-16T18:00:00.000Z', start_time: '2025-11-16T18:00:00.000Z', away_team: 'Kansas City Chiefs', home_team: 'Buffalo Bills' },
    ];
    return state;
  }

  test('resolves the current week and filters by day — never sends a `day` param to NFL', async () => {
    const { api, calls } = makeMockApi(nflSlate());
    const store = new SnapshotStore(['mlb', 'nba', 'nfl']);
    const poller = new Poller({ api, store, config: config(), now: () => NOV, quota: () => null, logger: () => {} });

    await poller.runScoreboardCycle(NOV);

    // The bug this guards against: `day` is not an NFL games filter.
    const nflGameCalls = calls.filter(c => c.key === 'nfl.games');
    expect(nflGameCalls.length).toBeGreaterThan(0);
    for (const call of nflGameCalls) {
      const params = (call.params ?? {}) as Record<string, unknown>;
      expect('day' in params).toBe(false);
    }

    // Only today's game survives the client-side day filter (the Thursday game,
    // found via week+1 even though this week has no Final yet).
    const nfl = store.snapshot().leagues.nfl;
    expect(nfl.status).toBe('in-season');
    expect(nfl.games).toHaveLength(1);
    expect(nfl.games[0]!.id).toBe('nfl-210');
    expect(nfl.games[0]!.away.abbr).toBe('BUF');
    expect(nfl.games[0]!.home.abbr).toBe('MIA');
  });
});

describe('offseason last-final slate', () => {
  // July: MLB in season; NBA + NFL offseason. NBA has no upcoming season row,
  // so current_season would resolve to 2025 (past). NFL DOES have a forward
  // season (2032) with a future end_date — the case that makes a naive
  // `status=Final` come back empty, which the season resolver must skip.
  function offseasonState(): MockState {
    const state = emptyState();
    state.nbaTeams = [
      { id: 30, full_name: 'Boston Celtics', abbreviation: 'BOS' },
      { id: 31, full_name: 'New York Knicks', abbreviation: 'NYK' },
    ];
    state.nflTeams = [
      { id: 40, full_name: 'Kansas City Chiefs', abbreviation: 'KC' },
      { id: 41, full_name: 'Philadelphia Eagles', abbreviation: 'PHI' },
    ];
    state.nbaSeasons = [
      { id: 2025, start_date: '2025-10-21T20:00:00-04:00', end_date: '2026-06-20T20:30:00-04:00' },
      { id: 2024, start_date: '2024-10-21T20:00:00-04:00', end_date: '2025-06-21T20:00:00-04:00' },
    ];
    state.nflSeasons = [
      // Forward-scheduled upcoming season — end_date in the future; must be skipped.
      { id: 2032, start_date: '2026-09-09T20:00:00-04:00', end_date: '2027-02-13T19:00:00-05:00' },
      { id: 2025, start_date: '2025-09-03T20:00:00-04:00', end_date: '2026-02-07T19:00:00-05:00' },
    ];
    state.nbaGames = [
      { id: 900, season_id: 2025, status: 'Final', away_team_id: 30, home_team_id: 31, away_team: 'Boston Celtics', home_team: 'New York Knicks', away_team_score: 110, home_team_score: 105, game_time: '2026-06-20T20:30:00-04:00', start_time: '2026-06-20T20:30:00-04:00' },
    ];
    state.nflGames = [
      { id: 800, season_id: 2025, season_type: 'postseason', week: 4, status: 'Final', away_team_id: 40, home_team_id: 41, away_team: 'Kansas City Chiefs', home_team: 'Philadelphia Eagles', away_team_score: 24, home_team_score: 21, game_time: '2026-02-07T18:30:00-05:00', start_time: '2026-02-07T18:30:00-05:00' },
      // Upcoming-season scheduled game — must NOT appear in the offseason slate.
      { id: 850, season_id: 2032, status: 'Scheduled', away_team_id: 41, home_team_id: 40, away_team: 'Philadelphia Eagles', home_team: 'Kansas City Chiefs', away_team_score: 0, home_team_score: 0, game_time: '2026-09-10T20:00:00-04:00', start_time: '2026-09-10T20:00:00-04:00' },
    ];
    return state;
  }

  test('populates offseason leagues with recent finals, skipping the upcoming season', async () => {
    const { api, calls, counts } = makeMockApi(offseasonState());
    const store = new SnapshotStore(['mlb', 'nba', 'nfl']);
    const poller = new Poller({ api, store, config: config(), now: () => NOW, quota: () => null, logger: () => {} });

    await poller.runScoreboardCycle(NOW);
    const snap = store.snapshot();

    // NBA offseason: status stays 'offseason', but games carry the last finals.
    expect(snap.leagues.nba.status).toBe('offseason');
    expect(snap.leagues.nba.games).toHaveLength(1);
    expect(snap.leagues.nba.games[0]!.id).toBe('nba-900');
    expect(snap.leagues.nba.games[0]!.status).toBe('final');
    expect(snap.leagues.nba.games[0]!.away.abbr).toBe('BOS');
    expect(snap.leagues.nba.games[0]!.home.score).toBe(105);

    // NFL: the finals query targeted the most-recently-COMPLETED season (2025),
    // not the forward-scheduled upcoming one (2032).
    expect(snap.leagues.nfl.status).toBe('offseason');
    expect(snap.leagues.nfl.games).toHaveLength(1);
    expect(snap.leagues.nfl.games[0]!.id).toBe('nfl-800');
    const nflFinalsCall = calls.find(
      c => c.key === 'nfl.games' && (c.params as { status?: string })?.status === 'Final',
    );
    expect((nflFinalsCall?.params as { season_id?: number })?.season_id).toBe(2025);

    // Cheap + cached: run again, the offseason slate is not re-fetched.
    await poller.runScoreboardCycle(NOW);
    expect(counts['nba.seasons']).toBe(1);
    expect(counts['nba.games']).toBe(1);
    expect(counts['nfl.seasons']).toBe(1);
  });
});

describe('periodic full-snapshot fan-out', () => {
  test('broadcasts a full snapshot after each scoreboard cycle (refreshes footer + asOf)', async () => {
    const { api } = makeMockApi(fullSlate());
    const store = new SnapshotStore(['mlb', 'nba', 'nfl']);
    const events: SseEvent[] = [];
    store.subscribe(e => events.push(e)); // initial snapshot (1)

    const poller = new Poller({ api, store, config: config(), now: () => NOW, quota: () => null, logger: () => {} });
    await poller.runScoreboardCycle(NOW);

    // The cycle ends with a broadcast snapshot, so at least two snapshot events
    // have reached the subscriber (initial + periodic).
    const snapshots = events.filter(e => e.type === 'snapshot');
    expect(snapshots.length).toBeGreaterThanOrEqual(2);
  });
});

describe('quota self-throttle', () => {
  test('halves cadence below the floor and restores it above', () => {
    const logs: string[] = [];
    let quota: Quota | null = null;
    const { poller } = makePoller(emptyState(), {
      quota: () => quota,
      logger: m => logs.push(m),
    });

    // Floor is 100_000 by default.
    quota = { limit: 5_000_000, used: 4_999_950, remaining: 50 };
    expect(poller.throttleMultiplier()).toBe(2);
    expect(logs.some(l => l.includes('QUOTA LOW'))).toBe(true);

    quota = { limit: 5_000_000, used: 100_000, remaining: 4_900_000 };
    expect(poller.throttleMultiplier()).toBe(1);
    expect(logs.some(l => l.includes('recovered'))).toBe(true);

    // No quota yet → never throttle.
    quota = null;
    expect(poller.throttleMultiplier()).toBe(1);
  });
});

describe('snapshot diff → SSE patch', () => {
  test('emits a patch only when a league actually changes', async () => {
    const state = fullSlate();
    const { api } = makeMockApi(state);
    const store = new SnapshotStore(['mlb', 'nba', 'nfl']);
    const events: SseEvent[] = [];
    store.subscribe(e => events.push(e));

    const poller = new Poller({
      api,
      store,
      config: config(),
      now: () => NOW,
      quota: () => null,
      logger: () => {},
    });

    // First scoreboard: MLB flips offseason→in-season with games → one patch.
    await poller.runScoreboardCycle(NOW);
    const firstPatches = events.filter(e => e.type === 'patch');
    expect(firstPatches).toHaveLength(1);
    expect(firstPatches[0]!.type === 'patch' && firstPatches[0].league).toBe('mlb');

    // Identical slate again → no new patch (games unchanged).
    await poller.runScoreboardCycle(NOW);
    expect(events.filter(e => e.type === 'patch')).toHaveLength(1);

    // A score change → a new patch.
    state.mlbGames[0]!.home_team_score = 7;
    await poller.runScoreboardCycle(NOW);
    const patches = events.filter(e => e.type === 'patch');
    expect(patches.length).toBe(2);
    expect(patches[1]!.type === 'patch' && patches[1].league).toBe('mlb');
  });
});
