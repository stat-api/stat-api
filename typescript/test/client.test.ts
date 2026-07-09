// Mocked-fetch unit tests for the @stat-api/client SDK runtime + generated
// resources. No live server; fetch is injected via the `fetch` option.
//
// Moved here in the S3 cutover from packages/client/test/sdk-client.test.ts and
// retargeted to the new StatApi surface (apiKey auth, typed errors, quota).

import { describe, expect, test } from 'bun:test';
import {
  StatApi,
  StatApiError,
  NotFoundError,
  QuotaExceededError,
} from '../src/index.js';

interface CapturedCall {
  url: string;
  init: RequestInit;
}

/** Single fixed response. */
function makeMock(response: { status?: number; body?: unknown; headers?: Record<string, string> } = {}) {
  const calls: CapturedCall[] = [];
  const status = response.status ?? 200;
  const body = response.body ?? {};
  const mock: typeof fetch = async (input, init) => {
    calls.push({ url: input.toString(), init: init ?? {} });
    return new Response(JSON.stringify(body), { status, headers: response.headers });
  };
  return { mock, calls };
}

/** Responds from a queue, one entry per call (for retry tests). */
function makeQueueMock(queue: Array<{ status: number; body?: unknown }>) {
  const calls: CapturedCall[] = [];
  const mock: typeof fetch = async (input, init) => {
    calls.push({ url: input.toString(), init: init ?? {} });
    const next = queue.shift() ?? { status: 200, body: {} };
    return new Response(JSON.stringify(next.body ?? {}), { status: next.status });
  };
  return { mock, calls };
}

const KEY = 'sdb_deadbeef_00000000000000000000000000000000';

describe('StatApi — construction', () => {
  test('throws loudly when no apiKey is available', () => {
    const saved = process.env.STAT_API_KEY;
    delete process.env.STAT_API_KEY;
    try {
      expect(() => new StatApi()).toThrow(/STAT_API_KEY/);
    } finally {
      if (saved !== undefined) process.env.STAT_API_KEY = saved;
    }
  });

  test('reads apiKey from STAT_API_KEY when not passed', async () => {
    const saved = process.env.STAT_API_KEY;
    process.env.STAT_API_KEY = KEY;
    try {
      const { mock, calls } = makeMock({ body: { teams: [] } });
      const api = new StatApi({ baseUrl: 'http://test:3000', fetch: mock });
      await api.nfl.teams.list();
      expect(new Headers(calls[0].init.headers).get('Authorization')).toBe(`Bearer ${KEY}`);
    } finally {
      if (saved === undefined) delete process.env.STAT_API_KEY;
      else process.env.STAT_API_KEY = saved;
    }
  });
});

describe('StatApi — request shape', () => {
  test('nfl.teams.list({ limit: 10 }) issues GET /api/v1/nfl/teams?limit=10', async () => {
    const { mock, calls } = makeMock({ body: { teams: [] } });
    const api = new StatApi({ apiKey: KEY, baseUrl: 'http://test:3000', fetch: mock });
    await api.nfl.teams.list({ limit: 10 } as never);
    expect(calls.length).toBe(1);
    expect(calls[0].url).toBe('http://test:3000/api/v1/nfl/teams?limit=10');
    expect((calls[0].init.method ?? 'GET').toUpperCase()).toBe('GET');
  });

  test('get(1) issues GET /api/v1/nfl/teams/1 and returns the row (unwrapped)', async () => {
    const { mock, calls } = makeMock({ body: { id: 1, name: 'Cardinals' } });
    const api = new StatApi({ apiKey: KEY, baseUrl: 'http://test:3000', fetch: mock });
    const team = await api.nfl.teams.get(1);
    expect(calls[0].url).toBe('http://test:3000/api/v1/nfl/teams/1');
    expect(team).toEqual({ id: 1, name: 'Cardinals' } as never);
  });

  test('injects Authorization: Bearer from apiKey', async () => {
    const { mock, calls } = makeMock({ body: { teams: [] } });
    const api = new StatApi({ apiKey: KEY, baseUrl: 'http://test:3000', fetch: mock });
    await api.nfl.teams.list();
    expect(new Headers(calls[0].init.headers).get('Authorization')).toBe(`Bearer ${KEY}`);
  });

  test('omits undefined params from the query string', async () => {
    const { mock, calls } = makeMock({ body: { teams: [] } });
    const api = new StatApi({ apiKey: KEY, baseUrl: 'http://test:3000', fetch: mock });
    await api.nba.players.list({ team_id: undefined, roster_status: 'active' } as never);
    expect(calls[0].url).toBe('http://test:3000/api/v1/nba/players?roster_status=active');
  });
});

describe('StatApi — typed errors', () => {
  test('404 throws NotFoundError carrying status + body + path', async () => {
    const { mock } = makeMock({ status: 404, body: { error: 'not-found', message: '...' } });
    const api = new StatApi({ apiKey: KEY, baseUrl: 'http://test:3000', fetch: mock });
    let thrown: unknown;
    try {
      await api.nfl.teams.get(99999);
    } catch (e) {
      thrown = e;
    }
    expect(thrown).toBeInstanceOf(NotFoundError);
    expect(thrown).toBeInstanceOf(StatApiError);
    const err = thrown as NotFoundError;
    expect(err.status).toBe(404);
    expect(err.path).toBe('/api/v1/nfl/teams/99999');
    expect(err.body).toEqual({ error: 'not-found', message: '...' });
  });

  test('429 throws QuotaExceededError (never retried) and parses quota', async () => {
    const { mock, calls } = makeMock({
      status: 429,
      body: { error: 'quota_exceeded', limit: 5_000_000, used: 5_000_000, resets_at: '2026-08-01T00:00:00Z', upgrade_url: 'https://stat-api.com/pricing' },
      headers: { 'X-Quota-Limit': '5000000', 'X-Quota-Used': '5000000', 'X-Quota-Remaining': '0' },
    });
    const api = new StatApi({ apiKey: KEY, baseUrl: 'http://test:3000', fetch: mock });
    let thrown: unknown;
    try {
      await api.nfl.teams.list();
    } catch (e) {
      thrown = e;
    }
    expect(thrown).toBeInstanceOf(QuotaExceededError);
    const err = thrown as QuotaExceededError;
    expect(err.status).toBe(429);
    expect(err.limit).toBe(5_000_000);
    expect(err.resetsAt).toBe('2026-08-01T00:00:00Z');
    expect(err.upgradeUrl).toBe('https://stat-api.com/pricing');
    expect(err.quota?.remaining).toBe(0);
    expect(calls.length).toBe(1); // 429 is NOT retried
  });
});

describe('StatApi — retries', () => {
  test('GET retries a 5xx and succeeds on the next attempt', async () => {
    const { mock, calls } = makeQueueMock([
      { status: 503, body: { error: 'unavailable' } },
      { status: 200, body: { teams: [{ id: 1 }] } },
    ]);
    const api = new StatApi({ apiKey: KEY, baseUrl: 'http://test:3000', fetch: mock });
    const res = await api.nfl.teams.list();
    expect(calls.length).toBe(2);
    expect(res).toEqual({ teams: [{ id: 1 }], quota: null } as never);
  });
});

describe('StatApi — composition', () => {
  test('exposes all 7 sport/market leagues on the root client', () => {
    const api = new StatApi({ apiKey: KEY });
    for (const league of ['nfl', 'nba', 'mlb', 'nhl', 'pga', 'kalshi', 'polymarket'] as const) {
      expect(typeof (api as unknown as Record<string, unknown>)[league]).toBe('object');
    }
  });

  test('each league exposes its tables as resource instances', () => {
    const api = new StatApi({ apiKey: KEY });
    expect(typeof api.nfl.teams.list).toBe('function');
    expect(typeof api.nfl.teams.get).toBe('function');
    expect(typeof api.kalshi.public_trades.list).toBe('function');
  });
});
