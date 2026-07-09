// Composition root: wire the SDK (with quota metering), the snapshot store, the
// tiered poller, and the HTTP app, then serve. This is the only file that
// constructs the real `StatApi` and binds a port.
//
//   STAT_API_KEY=... bun run start        # from apps/demos/dashboard/
import { resolve } from 'node:path';
import { StatApi } from '@stat-api/client';

import { createApp } from './app.ts';
import { loadConfig } from './config.ts';
import { Poller } from './poller.ts';
import { QuotaMeter, type StatApiClient } from './sdk.ts';
import { SnapshotStore } from './snapshot.ts';

async function main(): Promise<void> {
  const config = loadConfig();

  // The SDK's public `.list()` discards quota headers; the fetch wrapper is the
  // one seam that lets us skim them off every response.
  const meter = new QuotaMeter();
  const api: StatApiClient = new StatApi({ fetch: meter.fetch });

  const store = new SnapshotStore(config.leagues);
  const poller = new Poller({ api, quota: () => meter.current(), store, config });

  const frontendDist = resolve(import.meta.dir, '../frontend/dist');
  const app = createApp({ store, startedAt: Date.now(), frontendDist });

  const server = Bun.serve({ port: config.port, idleTimeout: 60, fetch: app.fetch });
  console.log(
    `[dashboard] serving on http://localhost:${server.port} ` +
      `(leagues=${config.leagues.join(',')}, quotaFloor=${config.quotaFloor})`,
  );

  await poller.start();
  console.log('[dashboard] poller primed — first snapshot ready');

  const shutdown = (signal: string) => {
    console.log(`[dashboard] ${signal} — shutting down`);
    poller.stop();
    server.stop();
    process.exit(0);
  };
  process.on('SIGINT', () => shutdown('SIGINT'));
  process.on('SIGTERM', () => shutdown('SIGTERM'));
}

main().catch((err: unknown) => {
  console.error('[dashboard] fatal:', err instanceof Error ? err.message : err);
  process.exit(1);
});
