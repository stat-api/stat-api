// "Is this thing live right now?" — the pure temporal predicates the poller
// leans on: which ET day today is, whether a league is in season, how a raw
// upstream status string collapses to the contract's three-state lifecycle,
// and whether a game is inside its detail-polling window. No I/O, no SDK — so
// every branch is unit-testable without a network.
import type { GameStatus, League } from './contract.ts';

const ET_DAY_FORMAT = new Intl.DateTimeFormat('en-CA', {
  timeZone: 'America/New_York',
  year: 'numeric',
  month: '2-digit',
  day: '2-digit',
});

const ET_MONTH_FORMAT = new Intl.DateTimeFormat('en-US', {
  timeZone: 'America/New_York',
  month: 'numeric',
});

/** Today's date in America/New_York as a YYYYMMDD integer — the shape the
 *  games `day` filter expects. */
export function todayEtYyyymmdd(now: Date = new Date()): number {
  // en-CA formats as YYYY-MM-DD; strip the dashes.
  return Number(ET_DAY_FORMAT.format(now).replace(/-/g, ''));
}

/**
 * Whether a league is in season on the given date, by month (ET). Deliberately
 * coarse: the dashboard only needs it to decide "poll today's slate" vs "show
 * an offseason card", and month boundaries are the honest granularity for that.
 * Playoff tails that spill a few days into the next month are treated as in
 * season by rounding the window outward.
 */
export function isInSeason(league: League, now: Date = new Date()): boolean {
  const month = Number(ET_MONTH_FORMAT.format(now));
  switch (league) {
    case 'mlb':
      return month >= 3 && month <= 11; // spring training tail → World Series
    case 'nba':
      return month >= 10 || month <= 6; // October tip-off → June Finals
    case 'nfl':
      return month >= 9 || month <= 2; // September → February (Super Bowl)
  }
}

/** Collapse an upstream status string (e.g. "Final", "In Progress",
 *  "Scheduled", "Postponed") to the contract's three-state lifecycle. */
export function mapGameStatus(raw: string): GameStatus {
  const s = raw.toLowerCase();
  if (
    s.includes('final') ||
    s.includes('complet') ||
    s.includes('game over') ||
    s === 'closed' ||
    s === 'f'
  ) {
    return 'final';
  }
  if (
    s.includes('progress') ||
    s.includes('live') ||
    s.includes('halftime') ||
    s.includes('warmup') ||
    s.includes('in game') ||
    /\bq[1-4]\b|period|inning/.test(s)
  ) {
    return 'live';
  }
  return 'scheduled';
}

/**
 * Whether a game is inside its detail-polling window — the gate that keeps the
 * per-game line/box/pulse tiers cheap:
 *
 *  - far-future games are not polled (they open only `leadMinutes` before start),
 *  - scheduled (pre-game, within the lead) and live games are polled,
 *  - final games are not re-polled — their last captured detail is retained and
 *    keeps rendering, but no further calls are spent on them.
 *
 * A game that flips to final between cycles therefore keeps the detail from its
 * last live poll (a few seconds stale); the authoritative final score still
 * arrives on the scoreboard tier. This trades a dedicated closing fetch for a
 * hard "zero calls on finished games" budget guarantee.
 */
export function isInWindow(
  startTime: Date,
  status: GameStatus,
  now: Date,
  leadMinutes: number,
): boolean {
  if (status === 'final') return false;
  const openAt = startTime.getTime() - leadMinutes * 60_000;
  return now.getTime() >= openAt;
}
