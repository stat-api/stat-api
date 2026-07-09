// =============================================================================
// cache — global TTL cache + single-flight, the quota-discipline core
// =============================================================================
//
// The teaching point of this demo. Every command reads through this ONE cache,
// keyed by (command, league, normalized args) and shared across every Discord
// server the bot is in — NOT per-guild. If ten servers ask for tonight's scores
// in the same minute, that is one stat-api call, not ten.
//
//   - TTLs are matched to upstream freshness (scores 60s … standings 1h) so the
//     cache stays as cheap as it can be without going stale.
//   - Single-flight: concurrent misses for the same key share one in-flight
//     fetch, so a burst of identical requests still costs one API call.
//   - Failures are never cached — a rejected fetch clears the key so the next
//     request retries.
//
// The clock is injectable (`setClock`) so tests get deterministic "cached Ns
// ago" footers; production uses the real wall clock.
// =============================================================================

export type Clock = () => number;

interface Entry<T> {
  readonly value: T;
  readonly fetchedAt: number;
}

/** A cache read: the value plus the provenance a footer needs. */
export interface Cached<T> {
  readonly value: T;
  /** ms epoch when the underlying fetch resolved. */
  readonly fetchedAt: number;
  /** whole seconds between `fetchedAt` and now, floored (never negative). */
  readonly ageSeconds: number;
}

/** Per-command TTLs in milliseconds, matched to how fast each source moves. */
export const TTL = {
  scores: 60_000,
  box: 120_000,
  news: 300_000,
  standings: 3_600_000,
  player: 3_600_000,
  /** Teams/rosters — effectively static within a session. */
  reference: 3_600_000,
} as const;

class TtlCache {
  private readonly store = new Map<string, Entry<unknown>>();
  private readonly inflight = new Map<string, Promise<Entry<unknown>>>();
  private now: Clock = () => Date.now();

  /** Override the clock (tests). */
  setClock(now: Clock): void {
    this.now = now;
  }

  /** Drop every entry and pending fetch (tests / manual invalidation). */
  clear(): void {
    this.store.clear();
    this.inflight.clear();
  }

  /** Read an entry without fetching. Used by autocomplete to serve choices
   *  from an already-cached slate at zero API cost. Ignores TTL — a slightly
   *  stale autocomplete list is fine. */
  peek<T>(key: string): Cached<T> | undefined {
    const entry = this.store.get(key) as Entry<T> | undefined;
    return entry ? this.toCached(entry) : undefined;
  }

  /**
   * Return the cached value for `key`, or run `fetcher` (once, even under a
   * concurrent burst) and cache it for `ttlMs`.
   */
  async fetch<T>(key: string, ttlMs: number, fetcher: () => Promise<T>): Promise<Cached<T>> {
    const hit = this.store.get(key) as Entry<T> | undefined;
    if (hit && this.now() - hit.fetchedAt < ttlMs) return this.toCached(hit);

    const pending = this.inflight.get(key) as Promise<Entry<T>> | undefined;
    if (pending) return this.toCached(await pending);

    const run: Promise<Entry<T>> = (async () => {
      const value = await fetcher();
      const entry: Entry<T> = { value, fetchedAt: this.now() };
      this.store.set(key, entry);
      return entry;
    })();
    this.inflight.set(key, run as Promise<Entry<unknown>>);
    try {
      return this.toCached(await run);
    } finally {
      if (this.inflight.get(key) === (run as Promise<Entry<unknown>>)) {
        this.inflight.delete(key);
      }
    }
  }

  private toCached<T>(entry: Entry<T>): Cached<T> {
    const ageSeconds = Math.max(0, Math.floor((this.now() - entry.fetchedAt) / 1000));
    return { value: entry.value, fetchedAt: entry.fetchedAt, ageSeconds };
  }
}

/** The single process-wide cache instance. */
export const cache = new TtlCache();

/** Deterministic key from (command, league, args). Sorted so arg order never
 *  produces two keys for the same logical request. */
export function cacheKey(command: string, league: string, args: Record<string, unknown> = {}): string {
  const normalized = Object.keys(args)
    .sort()
    .map((k) => `${k}=${String(args[k])}`)
    .join('&');
  return `${command}:${league}:${normalized}`;
}

/** Scores-slate key. Exported so the /box autocomplete peeks the exact same
 *  entry the /scores handler wrote. */
export function scoresKey(league: string, date: string): string {
  return cacheKey('scores', league, { date });
}
