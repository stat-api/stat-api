// HTTP surface: the server ⇄ browser contract endpoints plus static hosting of
// the built frontend. Pure in the store it is given, so tests can drive it with
// a hand-fed store and no poller/SDK/network.
//
//   GET /healthz       liveness + subscriber count
//   GET /api/snapshot  the current full Snapshot (JSON)
//   GET /api/events    SSE: initial `snapshot` event, then `patch` per league
//   GET /*             the built React app (when frontend/dist exists)
import { existsSync } from 'node:fs';
import { Hono } from 'hono';
import { serveStatic } from 'hono/bun';
import { streamSSE } from 'hono/streaming';

import type { SseEvent } from './contract.ts';
import type { SnapshotStore } from './snapshot.ts';

/** Heartbeat comment cadence — keeps proxies and Bun's idle timer from closing
 *  an otherwise-quiet SSE connection. */
const HEARTBEAT_MS = 15_000;

export interface AppOptions {
  readonly store: SnapshotStore;
  readonly startedAt: number;
  /** Absolute path to the built frontend (`frontend/dist`); static hosting is
   *  mounted only when it exists. */
  readonly frontendDist?: string;
}

export function createApp(opts: AppOptions): Hono {
  const { store, startedAt } = opts;
  const app = new Hono();

  app.get('/healthz', c =>
    c.json({
      ok: true,
      uptimeSec: Math.round((Date.now() - startedAt) / 1000),
      subscribers: store.subscriberCount,
    }),
  );

  app.get('/api/snapshot', c => c.json(store.snapshot()));

  app.get('/api/events', c => {
    c.header('Cache-Control', 'no-cache');
    c.header('X-Accel-Buffering', 'no');
    return streamSSE(c, async stream => {
      const pending: SseEvent[] = [];
      let wake: (() => void) | null = null;

      const unsubscribe = store.subscribe(event => {
        pending.push(event); // the current full snapshot arrives here synchronously
        wake?.();
      });
      stream.onAbort(() => {
        unsubscribe();
        wake?.();
      });

      try {
        while (!stream.aborted) {
          while (pending.length > 0) {
            const event = pending.shift() as SseEvent;
            await stream.writeSSE({ data: JSON.stringify(event) });
          }
          if (stream.aborted) break;
          await new Promise<void>(resolve => {
            const timer = setTimeout(resolve, HEARTBEAT_MS);
            wake = () => {
              clearTimeout(timer);
              resolve();
            };
          });
          wake = null;
          if (pending.length === 0 && !stream.aborted) {
            await stream.writeSSE({ event: 'ping', data: String(Date.now()) });
          }
        }
      } finally {
        unsubscribe();
      }
    });
  });

  // Static hosting of the built frontend, with an SPA fallback to index.html.
  // `serveStatic` roots are resolved relative to the process cwd, which is the
  // dashboard package dir under the documented `bun run start`.
  if (opts.frontendDist && existsSync(opts.frontendDist)) {
    app.use('/*', serveStatic({ root: './frontend/dist' }));
    app.get('*', serveStatic({ path: './frontend/dist/index.html' }));
  }

  return app;
}
