// HTTP + SSE plumbing, driven in-process via Hono's `app.request` — no port, no
// SDK, no network. This is the offline stand-in for the live smoke: it proves
// the exact wire behaviour a browser sees (full snapshot on GET, initial
// `snapshot` SSE event, then a `patch` when a league changes).
import { describe, expect, test } from 'bun:test';

import { createApp } from '../src/app.ts';
import { SnapshotStore } from '../src/snapshot.ts';
import type { GameCard, SseEvent } from '../src/contract.ts';

function newApp() {
  const store = new SnapshotStore(['mlb', 'nba', 'nfl']);
  const app = createApp({ store, startedAt: Date.now() });
  return { app, store };
}

const CARD: GameCard = {
  id: 'mlb-9001',
  status: 'live',
  startTime: '2026-07-15T21:00:00.000Z',
  away: { name: 'Houston Astros', abbr: 'HOU', score: 3 },
  home: { name: 'Seattle Mariners', abbr: 'SEA', score: 2 },
};

describe('http endpoints', () => {
  test('GET /healthz reports liveness', async () => {
    const { app } = newApp();
    const res = await app.request('/healthz');
    expect(res.status).toBe(200);
    const body = (await res.json()) as { ok: boolean; subscribers: number };
    expect(body.ok).toBe(true);
    expect(body.subscribers).toBe(0);
  });

  test('GET /api/snapshot returns the full three-league snapshot', async () => {
    const { app } = newApp();
    const res = await app.request('/api/snapshot');
    expect(res.status).toBe(200);
    const snap = (await res.json()) as {
      leagues: Record<string, { status: string }>;
      quota: unknown;
    };
    expect(Object.keys(snap.leagues).sort()).toEqual(['mlb', 'nba', 'nfl']);
    expect(snap.leagues.mlb!.status).toBe('offseason');
    expect(snap.quota).toBeNull();
  });
});

describe('sse stream', () => {
  test('delivers an initial snapshot then a patch when a league changes', async () => {
    const { app, store } = newApp();
    const res = await app.request('/api/events');
    expect(res.headers.get('content-type')).toContain('text/event-stream');

    const reader = res.body!.getReader();
    const decoder = new TextDecoder();
    let buffer = '';
    const events: SseEvent[] = [];
    const guard = setTimeout(() => void reader.cancel(), 4000);

    // Read whole SSE blocks until we have `want` real (non-heartbeat) events.
    async function pumpUntil(want: number): Promise<void> {
      while (events.length < want) {
        const { value, done } = await reader.read();
        if (done) return;
        buffer += decoder.decode(value, { stream: true });
        let sep: number;
        while ((sep = buffer.indexOf('\n\n')) >= 0) {
          const block = buffer.slice(0, sep);
          buffer = buffer.slice(sep + 2);
          if (block.includes('event: ping')) continue;
          const dataLine = block.split('\n').find(l => l.startsWith('data:'));
          const json = dataLine?.slice('data:'.length).trim();
          if (json && json.startsWith('{')) events.push(JSON.parse(json) as SseEvent);
        }
      }
    }

    // 1) The first event is always the full current snapshot.
    await pumpUntil(1);
    expect(events[0]!.type).toBe('snapshot');
    expect(store.subscriberCount).toBe(1);

    // 2) A league change fans out as a patch carrying just that league's games.
    store.updateLeague('mlb', 'in-season', [CARD], new Date().toISOString());
    await pumpUntil(2);
    const patch = events[1]!;
    expect(patch.type).toBe('patch');
    if (patch.type === 'patch') {
      expect(patch.league).toBe('mlb');
      expect(patch.games).toHaveLength(1);
      expect(patch.games[0]!.id).toBe('mlb-9001');
    }

    clearTimeout(guard);
    await reader.cancel();
    // The stream unsubscribes on abort.
    expect(store.subscriberCount).toBe(0);
  });
});
