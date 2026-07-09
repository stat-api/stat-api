// Market-implied win-probability sources.
//
// The dashboard never exposes the private win-probability model. Instead it
// derives a *market-implied* home win probability from public data, behind a
// small pluggable `WpSource` interface so the exposure can evolve (add books,
// swap kalshi in) without the frontend changing — the contract already carries
// a `source` tag on every pulse point.
//
// This module owns the interface and the sportsbook-implied source (moneyline →
// de-vigged home probability, reusing the line movement already fetched, so it
// costs zero extra API calls). The kalshi source lives in `./kalshi.ts`.
import type { BookLines, MarketPulsePoint, WpSource as WpSourceKind } from '../contract.ts';
import type { StatApiClient } from '../sdk.ts';

/** Everything a source needs to produce a pulse for one game. */
export interface WpContext {
  readonly gameId: number;
  /** The underlying competition id (`games.id`) used for the kalshi join. */
  readonly competitionId: number;
  readonly homeTeamName: string;
  readonly awayTeamName: string;
  /** Line movement already fetched for this game — the sportsbook source reuses it. */
  readonly books: BookLines[];
  readonly api: StatApiClient;
  readonly now: Date;
}

export interface WpSource {
  readonly kind: WpSourceKind;
  pulse(ctx: WpContext): Promise<MarketPulsePoint[]>;
}

/** Home win probability implied by moneyline odds, de-vigged against the away
 *  price and averaged across books at each timestamp. */
export function sportsbookImpliedSource(): WpSource {
  return {
    kind: 'sportsbook-implied',
    pulse(ctx) {
      return Promise.resolve(sportsbookImpliedPulse(ctx.books));
    },
  };
}

export function sportsbookImpliedPulse(books: BookLines[]): MarketPulsePoint[] {
  const byTime = new Map<string, { sum: number; count: number }>();
  for (const book of books) {
    for (const p of book.points) {
      if (p.moneylineHome === null || p.moneylineAway === null) continue;
      const home = impliedProb(p.moneylineHome);
      const away = impliedProb(p.moneylineAway);
      const overround = home + away;
      if (overround <= 0) continue;
      const devigged = home / overround; // strip the book's vig
      const agg = byTime.get(p.t) ?? { sum: 0, count: 0 };
      agg.sum += devigged;
      agg.count += 1;
      byTime.set(p.t, agg);
    }
  }
  return [...byTime.entries()]
    .map(([t, { sum, count }]) => ({
      source: 'sportsbook-implied' as const,
      t,
      homeWinProb: round4(sum / count),
    }))
    .sort((a, b) => a.t.localeCompare(b.t));
}

/** American odds → implied probability in (0, 1). */
export function impliedProb(american: number): number {
  if (american > 0) return 100 / (american + 100);
  if (american < 0) return -american / (-american + 100);
  return 0.5;
}

function round4(x: number): number {
  return Math.round(x * 10_000) / 10_000;
}
