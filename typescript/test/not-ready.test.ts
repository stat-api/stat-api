// Mocked-fetch tests for the 202 Accepted rule: a 202 holds NO data, so the SDK
// throws NotReadyError instead of returning the body as the route's data, and
// never retries it. No live server; fetch is injected via the `fetch` option.

import { describe, expect, test } from 'bun:test';
import { NotReadyError, StatApi, StatApiError } from '../src/index.js';

const KEY = 'sdb_deadbeef_00000000000000000000000000000000';

/** The 202 body the API sends for a contest whose lineup file is not loaded. */
const QUEUED_BODY = {
  status: 'queued',
  message: 'The lineups of this contest are queued to load.',
  contest_id: 29647259,
  external_id: 196420231,
  requested_at: '2026-10-12T02:26:11Z',
  estimated_ready_at: '2026-10-12T02:42:00Z',
  retry_after_seconds: 120,
  entry_count: 340,
};

interface Reply {
  status: number;
  body?: unknown;
  /** Raw text body; wins over `body` (for a non-JSON answer). */
  text?: string;
  headers?: Record<string, string>;
}

/** Responds from a queue, one entry per call, and counts the calls. */
function makeMock(queue: Reply[]) {
  const urls: string[] = [];
  const mock: typeof fetch = async (input) => {
    urls.push(input.toString());
    const next = queue.shift() ?? { status: 200, body: {} };
    const payload = next.text ?? JSON.stringify(next.body ?? {});
    return new Response(payload, { status: next.status, headers: next.headers });
  };
  return { mock, urls };
}

function client(mock: typeof fetch): StatApi {
  return new StatApi({ apiKey: KEY, baseUrl: 'http://test:3000', fetch: mock });
}

async function caught(run: () => Promise<unknown>): Promise<unknown> {
  try {
    await run();
  } catch (e) {
    return e;
  }
  throw new Error('expected the call to throw');
}

/** Run the call and return the NotReadyError it throws (type-narrowed, no cast). */
async function caughtNotReady(run: () => Promise<unknown>): Promise<NotReadyError> {
  const e = await caught(run);
  if (!(e instanceof NotReadyError)) throw new Error(`expected NotReadyError, got ${String(e)}`);
  return e;
}

describe('202 Accepted — NotReadyError', () => {
  test('download throws NotReadyError with every field, status 202, and is not retried', async () => {
    const { mock, urls } = makeMock([
      { status: 202, body: QUEUED_BODY, headers: { 'Retry-After': '120' } },
    ]);
    const err = await caughtNotReady(() => client(mock).dfs.contests.download(29647259));
    expect(err).toBeInstanceOf(StatApiError);
    expect(err.name).toBe('NotReadyError');
    expect(err.status).toBe(202);
    expect(err.path).toBe('/api/v1/dfs/contests/29647259/download');
    expect(err.retryAfterSeconds).toBe(120);
    expect(err.estimatedReadyAt).toBe('2026-10-12T02:42:00Z');
    expect(err.state).toBe('queued');
    expect(err.message).toBe('The lineups of this contest are queued to load.');
    expect(err.body).toEqual(QUEUED_BODY);
    expect(urls.length).toBe(1); // a 202 is not a transient failure: no retry
  });

  test('waiting_for_contest carries a null estimatedReadyAt', async () => {
    const { mock } = makeMock([
      {
        status: 202,
        body: {
          ...QUEUED_BODY,
          status: 'waiting_for_contest',
          estimated_ready_at: null,
          retry_after_seconds: 900,
        },
      },
    ]);
    const err = await caughtNotReady(() => client(mock).dfs.contests.standings(29647259));
    expect(err).toBeInstanceOf(NotReadyError);
    expect(err.path).toBe('/api/v1/dfs/contests/29647259/standings');
    expect(err.state).toBe('waiting_for_contest');
    expect(err.estimatedReadyAt).toBeNull();
    expect(err.retryAfterSeconds).toBe(900);
  });

  test('user_lineups also raises NotReadyError', async () => {
    const { mock } = makeMock([{ status: 202, body: QUEUED_BODY }]);
    const err = await caughtNotReady(() =>
      client(mock).dfs.contests.user_lineups(29647259, 'some user'),
    );
    expect(err.path).toBe('/api/v1/dfs/contests/29647259/users/some%20user/lineups');
  });

  test('retryAfterSeconds falls back to the Retry-After header when the body has none', async () => {
    const { mock } = makeMock([
      {
        status: 202,
        body: { status: 'queued', message: 'queued' },
        headers: { 'Retry-After': '45' },
      },
    ]);
    const err = await caughtNotReady(() => client(mock).dfs.contests.download(1));
    expect(err.retryAfterSeconds).toBe(45);
    expect(err.estimatedReadyAt).toBeNull();
  });

  test('the body retry_after_seconds wins over the Retry-After header', async () => {
    const { mock } = makeMock([
      { status: 202, body: QUEUED_BODY, headers: { 'Retry-After': '999' } },
    ]);
    const err = await caughtNotReady(() => client(mock).dfs.contests.download(1));
    expect(err.retryAfterSeconds).toBe(120);
  });

  test('no body value and no usable header gives a null retryAfterSeconds', async () => {
    const { mock } = makeMock([
      {
        status: 202,
        body: { status: 'queued' },
        headers: { 'Retry-After': 'Wed, 21 Oct 2026 07:28:00 GMT' },
      },
    ]);
    const err = await caughtNotReady(() => client(mock).dfs.contests.download(1));
    expect(err.retryAfterSeconds).toBeNull();
  });

  test('a 202 with a non-JSON body still raises, with a default message and null fields', async () => {
    const { mock } = makeMock([{ status: 202, text: 'accepted' }]);
    const err = await caughtNotReady(() => client(mock).dfs.contests.download(1));
    expect(err).toBeInstanceOf(NotReadyError);
    expect(err.body).toBe('accepted');
    expect(err.state).toBeNull();
    expect(err.estimatedReadyAt).toBeNull();
    expect(err.retryAfterSeconds).toBeNull();
    expect(err.message).toContain('202');
  });
});

describe('202 rule leaves the other statuses alone', () => {
  test('a 200 still returns the data', async () => {
    const data = { contest: { id: 1 }, player_pool: [], lineups: [{ rank: 1 }] };
    const { mock, urls } = makeMock([{ status: 200, body: data }]);
    const res = await client(mock).dfs.contests.download(1);
    expect(res).toEqual(data as never);
    expect(urls.length).toBe(1);
  });

  test('a 500 is still retried and the next 200 returns data', async () => {
    const { mock, urls } = makeMock([
      { status: 500, body: { error: 'internal' } },
      { status: 200, body: { teams: [{ id: 1 }] } },
    ]);
    const res = await client(mock).nfl.teams.list();
    expect(urls.length).toBe(2);
    expect(res).toEqual({ teams: [{ id: 1 }], quota: null } as never);
  });

  test('a 404 is still NotFoundError, not NotReadyError', async () => {
    const { mock } = makeMock([{ status: 404, body: { error: 'not-found' } }]);
    const thrown = await caught(() => client(mock).nfl.teams.get(99999));
    expect(thrown).not.toBeInstanceOf(NotReadyError);
    if (!(thrown instanceof StatApiError)) throw new Error('expected a StatApiError');
    expect(thrown.status).toBe(404);
  });
});
