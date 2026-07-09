#!/usr/bin/env bun
// Live smoke: boot the real server against the published API, run one poll
// cycle, and prove the browser-facing surface works end to end —
// GET /api/snapshot carries today's MLB slate + a metered quota, and
// GET /api/events pushes at least one event.
//
// Env-gated: with no STAT_API_KEY it prints BLOCKED and exits 0 (a skip, not a
// failure) so it is safe to wire into CI. Point at a different backend with
// STAT_API_BASE_URL (e.g. a local sports-db-server); override the ET day with
// DASHBOARD_DAY when a backend has no games "today".
import { StatApi } from '@stat-api/client';

import { createApp } from '../src/app.ts';
import { loadConfig } from '../src/config.ts';
import { Poller } from '../src/poller.ts';
import { QuotaMeter, type StatApiClient } from '../src/sdk.ts';
import { SnapshotStore } from '../src/snapshot.ts';
import type { Snapshot, SseEvent } from '../src/contract.ts';

async function main(): Promise<number> {
  if (!process.env.STAT_API_KEY) {
    console.log('BLOCKED: set STAT_API_KEY to run the live smoke (skipping, not failing).');
    console.log('  e.g. STAT_API_KEY=sdb_... bun run test:smoke');
    return 0;
  }

  const config = loadConfig();
  const meter = new QuotaMeter();
  const api: StatApiClient = new StatApi({ fetch: meter.fetch });
  const store = new SnapshotStore(config.leagues);
  const poller = new Poller({ api, quota: () => meter.current(), store, config });

  // Ephemeral port so the smoke never collides with a running dashboard.
  const app = createApp({ store, startedAt: Date.now() });
  const server = Bun.serve({ port: 0, idleTimeout: 60, fetch: app.fetch });
  const base = `http://localhost:${server.port}`;
  console.log(`[smoke] server on ${base} (backend ${config.dayOverride ? `day=${config.dayOverride} ` : ''}via SDK)`);

  let ok = true;
  try {
    console.log('[smoke] running one poll cycle...');
    await poller.pollOnce();

    const snap = (await (await fetch(`${base}/api/snapshot`)).json()) as Snapshot;
    for (const league of config.leagues) {
      const slice = snap.leagues[league];
      console.log(
        `[smoke]   ${league}: ${slice.status.padEnd(10)} ${slice.games.length} games  asOf=${slice.asOf}`,
      );
    }
    const q = snap.quota;
    console.log(
      q
        ? `[smoke]   quota: used ${q.used} / ${q.limit} (remaining ${q.remaining})`
        : '[smoke]   quota: <none — no metered call recorded>',
    );

    // Assertions: MLB is in season through the summer and must carry a slate,
    // every league must stamp freshness, and a metered call must yield quota.
    ok = assert(snap.leagues.mlb.status === 'in-season', 'mlb is in-season') && ok;
    ok = assert(snap.leagues.mlb.games.length > 0, 'mlb has games today') && ok;
    ok = assert(Boolean(snap.leagues.mlb.asOf), 'mlb slice carries an asOf stamp') && ok;
    ok = assert(q !== null, 'footer quota populated from X-Quota-* headers') && ok;

    const firstEvent = await readOneSseEvent(`${base}/api/events`, 5000);
    ok = assert(firstEvent !== null, 'SSE stream delivered an event') && ok;
    if (firstEvent) console.log(`[smoke]   first SSE event: type=${firstEvent.type}`);
  } catch (err) {
    console.error('[smoke] ERROR:', err instanceof Error ? err.message : err);
    ok = false;
  } finally {
    poller.stop();
    server.stop(true);
  }

  console.log(ok ? '[smoke] PASS' : '[smoke] FAIL');
  return ok ? 0 : 1;
}

function assert(condition: boolean, label: string): boolean {
  console.log(`[smoke]   ${condition ? 'ok  ' : 'FAIL'} — ${label}`);
  return condition;
}

/** Open the SSE stream and return the first real event, or null on timeout. */
async function readOneSseEvent(url: string, timeoutMs: number): Promise<SseEvent | null> {
  const res = await fetch(url, { headers: { Accept: 'text/event-stream' } });
  if (!res.body) return null;
  const reader = res.body.getReader();
  const decoder = new TextDecoder();
  const guard = setTimeout(() => void reader.cancel(), timeoutMs);
  let buffer = '';
  try {
    for (;;) {
      const { value, done } = await reader.read();
      if (done) return null;
      buffer += decoder.decode(value, { stream: true });
      let sep: number;
      while ((sep = buffer.indexOf('\n\n')) >= 0) {
        const block = buffer.slice(0, sep);
        buffer = buffer.slice(sep + 2);
        if (block.includes('event: ping')) continue;
        const dataLine = block.split('\n').find(l => l.startsWith('data:'));
        const json = dataLine?.slice('data:'.length).trim();
        if (json && json.startsWith('{')) return JSON.parse(json) as SseEvent;
      }
    }
  } finally {
    clearTimeout(guard);
    await reader.cancel().catch(() => {});
  }
}

process.exit(await main());
