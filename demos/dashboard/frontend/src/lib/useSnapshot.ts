import { useEffect, useState } from 'react';
import type { Snapshot, SseEvent } from './contract';
import snapshotFixture from '../fixtures/snapshot.json';

/**
 * Where the current snapshot came from. Shown in the header so the demo is honest about whether
 * it is serving bundled fixture data or a live SSE stream.
 */
export type ConnectionState = 'fixture' | 'connecting' | 'live' | 'error';

export interface SnapshotState {
  snapshot: Snapshot;
  connection: ConnectionState;
}

const FIXTURE = snapshotFixture as Snapshot;

/**
 * The single data-source seam for the whole dashboard. Every panel reads only the returned
 * `Snapshot`, so switching data sources is a change to this hook alone.
 *
 * Live mode subscribes to the fan-out server's `/api/events` SSE stream. EVERY `snapshot`
 * event is adopted — not just the first — because the server re-emits a full snapshot each
 * scoreboard cycle to refresh the quota footer and per-league `asOf` stamps (patches carry
 * neither). `patch` events replace one league's games in place.
 *
 * When the stream can't be established at all (e.g. `vite dev` with no server), the hook
 * falls back to the bundled fixture and stops retrying. After a live connection drops, the
 * EventSource is left open so the browser's auto-reconnect can restore it — the server
 * replays a full snapshot on reconnect.
 */
export function useSnapshot(): SnapshotState {
  const [state, setState] = useState<SnapshotState>({ snapshot: FIXTURE, connection: 'connecting' });

  useEffect(() => {
    const es = new EventSource('/api/events');
    let everLive = false;

    es.onmessage = ev => {
      const msg = JSON.parse(ev.data) as SseEvent;
      if (msg.type === 'snapshot') {
        everLive = true;
        setState({ snapshot: msg.data, connection: 'live' });
        return;
      }
      // patch — fold one league's games into the held snapshot.
      setState(s => ({
        connection: 'live',
        snapshot: {
          ...s.snapshot,
          leagues: {
            ...s.snapshot.leagues,
            [msg.league]: { ...s.snapshot.leagues[msg.league], games: msg.games },
          },
        },
      }));
    };

    es.onerror = () => {
      if (!everLive) {
        es.close();
        setState({ snapshot: FIXTURE, connection: 'fixture' });
        return;
      }
      setState(s => ({ ...s, connection: 'error' }));
    };

    return () => es.close();
  }, []);

  return state;
}
