// In-memory snapshot store + SSE fan-out.
//
// The poller writes league slices and quota here; browsers read the whole
// `Snapshot` once (GET /api/snapshot) and then subscribe for changes. A new
// subscriber is handed the current full snapshot immediately, then receives a
// `patch` for a league only when that league's games actually change — so a
// thousand idle viewers cost nothing and a real update reaches all of them from
// a single poll. This is the fan-out that makes one metered poll serve every
// browser.
import type {
  GameCard,
  League,
  LeagueSnapshot,
  Quota,
  Snapshot,
  SseEvent,
} from './contract.ts';

type Subscriber = (event: SseEvent) => void;

export class SnapshotStore {
  private readonly leagues: Record<League, LeagueSnapshot>;
  private quota: Quota | null = null;
  private generatedAt: string;
  private readonly subscribers = new Set<Subscriber>();

  constructor(leagues: readonly League[]) {
    const now = new Date().toISOString();
    this.generatedAt = now;
    const initial = {} as Record<League, LeagueSnapshot>;
    for (const league of leagues) {
      initial[league] = { status: 'offseason', games: [], asOf: now };
    }
    this.leagues = initial;
  }

  /** The current whole-dashboard view, deep-cloned so callers can serialize it
   *  without racing an in-flight poll. */
  snapshot(): Snapshot {
    return structuredClone({
      generatedAt: this.generatedAt,
      quota: this.quota,
      leagues: this.leagues,
    });
  }

  /** Update the live quota shown in the footer. Silent — quota rides the next
   *  full snapshot (GET /api/snapshot), not its own SSE event. */
  setQuota(quota: Quota | null): void {
    if (quota !== null) this.quota = quota;
  }

  /**
   * Replace one league's slice. Emits a `patch` to every subscriber iff the
   * games or status actually changed, so unchanged leagues produce no traffic.
   */
  updateLeague(
    league: League,
    status: LeagueSnapshot['status'],
    games: GameCard[],
    asOf: string,
  ): void {
    const prev = this.leagues[league];
    const changed = prev.status !== status || !gamesEqual(prev.games, games);
    this.leagues[league] = { status, games, asOf };
    this.generatedAt = new Date().toISOString();
    if (changed) {
      this.broadcast({ type: 'patch', league, games: structuredClone(games) });
    }
  }

  /**
   * Push the current full snapshot to every subscriber. Unlike `patch` events
   * (which carry only a league's games), a full snapshot refreshes the footer
   * quota and every league's `asOf` on connected clients. The poller calls this
   * once per scoreboard cycle so those stamps stay live over SSE without any
   * contract change — `snapshot` is already the initial event type.
   */
  broadcastSnapshot(): void {
    this.broadcast({ type: 'snapshot', data: this.snapshot() });
  }

  /** Subscribe to the SSE stream. The current full snapshot is delivered
   *  synchronously first; returns an unsubscribe function. */
  subscribe(onEvent: Subscriber): () => void {
    this.subscribers.add(onEvent);
    onEvent({ type: 'snapshot', data: this.snapshot() });
    return () => {
      this.subscribers.delete(onEvent);
    };
  }

  /** Number of live SSE subscribers (for /healthz + tests). */
  get subscriberCount(): number {
    return this.subscribers.size;
  }

  private broadcast(event: SseEvent): void {
    for (const sub of this.subscribers) sub(event);
  }
}

/** Structural equality by canonical JSON. Games are built deterministically
 *  (stable key order, sorted arrays), so this reliably detects real changes. */
function gamesEqual(a: GameCard[], b: GameCard[]): boolean {
  return a.length === b.length && JSON.stringify(a) === JSON.stringify(b);
}
