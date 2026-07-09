// Handler snapshot + cache-behavior tests. No Discord token, no live API — the
// SDK is a fixture stub, the cache clock is frozen for deterministic footers.
import { test, expect, beforeEach, mock } from 'bun:test';
import type { StatApi } from '@stat-api/client';

import { cache } from '../src/cache';
import { handleScores } from '../src/commands/scores';
import { handleBox } from '../src/commands/box';
import { handlePlayer } from '../src/commands/player';
import { handleStandings } from '../src/commands/standings';
import { handleNews } from '../src/commands/news';

// ---- fixtures ----

const NBA_GAMES = [
  { id: 501, away_team: 'Lakers', home_team: 'Celtics', away_team_score: 102, home_team_score: 98, status: 'Final', game_time: '2026-07-08T23:30:00Z' },
  { id: 502, away_team: 'Warriors', home_team: 'Nuggets', away_team_score: 0, home_team_score: 0, status: 'Scheduled', game_time: '2026-07-09T02:00:00Z' },
];
const NBA_GAME_501 = { id: 501, away_team_id: 1, home_team_id: 2, away_team: 'Lakers', home_team: 'Celtics', away_team_score: 102, home_team_score: 98, status: 'Final' };
const NBA_PLAYER_STATS_501 = [
  { game_id: 501, player_id: 10, team_id: 1, pts: 33, rebounds: 8, assists: 9 },
  { game_id: 501, player_id: 11, team_id: 1, pts: 21, rebounds: 5, assists: 3 },
  { game_id: 501, player_id: 20, team_id: 2, pts: 28, rebounds: 11, assists: 6 },
];
const NBA_ROSTERS: Record<number, { id: number; first_name: string; last_name: string }[]> = {
  1: [{ id: 10, first_name: 'LeBron', last_name: 'James' }, { id: 11, first_name: 'Austin', last_name: 'Reaves' }],
  2: [{ id: 20, first_name: 'Jayson', last_name: 'Tatum' }],
};
const NBA_SEARCH = [{ id: 10, full_name: 'LeBron James', team_name: 'Los Angeles Lakers', team_abbreviation: 'LAL', primary_position: 'F' }];
const NBA_SEASON_STATS = [
  { season_id: 30, player_id: 10, games_played: 70, pts: 1750, rebounds: 560, assists: 630, steals: 91, blocks: 42, field_goal_percentage: '0.512', three_point_percentage: '0.365', free_throw_percentage: '0.735' },
];
const NBA_SEASON_30 = { id: 30, start_year: 2025 };
const NBA_STANDINGS = [
  { team_id: 3, day: 20260410, wins: 55, losses: 27, win_pct: '0.671' },
  { team_id: 1, day: 20260410, wins: 52, losses: 30, win_pct: '0.634' },
  { team_id: 2, day: 20260410, wins: 48, losses: 34, win_pct: '0.585' },
  { team_id: 1, day: 20260409, wins: 51, losses: 30, win_pct: '0.630' }, // stale day — ignored
];
const NBA_TEAMS = [
  { id: 1, abbreviation: 'LAL', conference: 'Western' },
  { id: 2, abbreviation: 'BOS', conference: 'Eastern' },
  { id: 3, abbreviation: 'DEN', conference: 'Western' },
];
const NBA_NEWS = [
  { id: 9, title: 'Star guard questionable for opener', source: 'Rotowire', link: 'https://stat-api.com/n/9', news_time: '2026-07-08T18:00:00Z' },
  { id: 8, title: 'Front office eyes a trade', source: 'ESPN', link: null, news_time: '2026-07-08T16:30:00Z' },
];

const MLB_TEAMS = [{ id: 100, abbreviation: 'NYY' }, { id: 101, abbreviation: 'BOS' }];
const MLB_GAMES = [{ id: 900, away_team_id: 100, home_team_id: 101, away_team_score: 5, home_team_score: 3, status: 'Final', game_time: '2026-07-08T23:05:00Z' }];
const MLB_GAME_900 = { id: 900, away_team_id: 100, home_team_id: 101, away_team_score: 5, home_team_score: 3, status: 'Final' };
const MLB_BATTERS_900 = [
  { game_id: 900, player_id: 200, team_id: 100, hits: 3, at_bats: 5, runs_batted_in: 2 },
  { game_id: 900, player_id: 201, team_id: 101, hits: 2, at_bats: 4, runs_batted_in: 1 },
];
const MLB_PITCHERS_900 = [{ game_id: 900, player_id: 210, team_id: 100, innings_pitched: '6.0', strikeouts_pitched: 7, earned_runs: 2 }];
const MLB_ROSTERS: Record<number, { id: number; first_name: string; last_name: string }[]> = {
  100: [{ id: 200, first_name: 'Aaron', last_name: 'Judge' }, { id: 210, first_name: 'Gerrit', last_name: 'Cole' }],
  101: [{ id: 201, first_name: 'Rafael', last_name: 'Devers' }],
};

// A fixture SDK: only the accessor methods the handlers call, each a mock so we
// can assert invocation counts. Cast to StatApi — handlers touch a slice.
function makeApi() {
  const nbaGamesList = mock(async (_p?: unknown) => ({ games: NBA_GAMES }));
  const mlbGamesList = mock(async (_p?: unknown) => ({ games: MLB_GAMES }));
  const api = {
    nba: {
      games: { list: nbaGamesList, get: mock(async (_id: number) => NBA_GAME_501) },
      game_player_stats: { list: mock(async (_p?: unknown) => ({ game_player_stats: NBA_PLAYER_STATS_501 })) },
      players: {
        list: mock(async (p: { team_id: number }) => ({ players: NBA_ROSTERS[p.team_id] ?? [] })),
        search: mock(async (_p: { q: string }) => NBA_SEARCH),
      },
      season_player_stats: { list: mock(async (_p: { player_id: number }) => ({ season_player_stats: NBA_SEASON_STATS })) },
      seasons: { get: mock(async (_id: number) => NBA_SEASON_30) },
      team_standings: { list: mock(async (_p?: unknown) => ({ team_standings: NBA_STANDINGS })) },
      teams: { list: mock(async (_p?: unknown) => ({ teams: NBA_TEAMS })) },
      player_news: { list: mock(async (_p?: unknown) => ({ player_news: NBA_NEWS })) },
    },
    nfl: { player_news: { list: mock(async (_p?: unknown) => ({ player_news: [] })) } },
    mlb: {
      games: { list: mlbGamesList, get: mock(async (_id: number) => MLB_GAME_900) },
      teams: { list: mock(async (_p?: unknown) => ({ teams: MLB_TEAMS })) },
      players: { list: mock(async (p: { team_id: number }) => ({ players: MLB_ROSTERS[p.team_id] ?? [] })) },
      game_player_batter_stats: { list: mock(async (_p?: unknown) => ({ game_player_batter_stats: MLB_BATTERS_900 })) },
      game_player_pitching_stats: { list: mock(async (_p?: unknown) => ({ game_player_pitching_stats: MLB_PITCHERS_900 })) },
      player_news: { list: mock(async (_p?: unknown) => ({ player_news: [] })) },
    },
  };
  return { api: api as unknown as StatApi, nbaGamesList };
}

// Frozen clock so "data as of …" and "cached Ns ago" are deterministic.
const BASE_MS = Date.UTC(2026, 6, 8, 17, 0, 0);
let clockMs = BASE_MS;

beforeEach(() => {
  cache.clear();
  clockMs = BASE_MS;
  cache.setClock(() => clockMs);
});

// ---- per-command snapshots ----

test('scores: NBA slate', async () => {
  const { api } = makeApi();
  expect(await handleScores(api, { league: 'nba', date: '20260708' })).toMatchSnapshot();
});

test('scores: MLB slate resolves team abbreviations', async () => {
  const { api } = makeApi();
  expect(await handleScores(api, { league: 'mlb', date: '20260708' })).toMatchSnapshot();
});

test('scores: empty slate → offseason embed', async () => {
  const api = {
    nba: { games: { list: mock(async (_p?: unknown) => ({ games: [] })) } },
  } as unknown as StatApi;
  expect(await handleScores(api, { league: 'nba', date: '20260708' })).toMatchSnapshot();
});

test('box: NBA top scorers per team', async () => {
  const { api } = makeApi();
  expect(await handleBox(api, { league: 'nba', gameId: 501 })).toMatchSnapshot();
});

test('box: MLB batting + pitching lines', async () => {
  const { api } = makeApi();
  expect(await handleBox(api, { league: 'mlb', gameId: 900 })).toMatchSnapshot();
});

test('player: found → season averages', async () => {
  const { api } = makeApi();
  expect(await handlePlayer(api, { name: 'lebron' })).toMatchSnapshot();
});

test('player: not found', async () => {
  const api = {
    nba: { players: { search: mock(async (_p: { q: string }) => []) } },
  } as unknown as StatApi;
  expect(await handlePlayer(api, { name: 'nobody xyz' })).toMatchSnapshot();
});

test('standings: NBA grouped by conference', async () => {
  const { api } = makeApi();
  expect(await handleStandings(api, { league: 'nba' })).toMatchSnapshot();
});

test('news: NBA latest headlines', async () => {
  const { api } = makeApi();
  expect(await handleNews(api, { league: 'nba' })).toMatchSnapshot();
});

// ---- cache behavior ----

test('cache hit: second call within TTL reuses the fetch and ages the footer', async () => {
  const { api, nbaGamesList } = makeApi();
  const args = { league: 'nba' as const, date: '20260708' };

  const first = await handleScores(api, args);
  expect(first.footer?.text).toContain('cached 0s ago');

  clockMs += 30_000; // 30s later, still inside the 60s scores TTL
  const second = await handleScores(api, args);

  expect(nbaGamesList).toHaveBeenCalledTimes(1);
  expect(second.footer?.text).toContain('cached 30s ago');
  expect(second).toMatchSnapshot();
});

test('single-flight: two concurrent identical calls share one fetch', async () => {
  const { api, nbaGamesList } = makeApi();
  const args = { league: 'nba' as const, date: '20260708' };

  const [a, b] = await Promise.all([handleScores(api, args), handleScores(api, args)]);

  expect(nbaGamesList).toHaveBeenCalledTimes(1);
  expect(a).toEqual(b);
});
