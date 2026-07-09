// The server ⇄ browser wire contract for the stat-api live dashboard.
//
// This is the ONLY coupling between the Bun+Hono poller (DM1) and this React frontend. It is
// deliberately independent of the stat-api SDK: the server talks to stat-api and distills the
// result into a `Snapshot`; the browser only ever sees `Snapshot`s and `SseEvent`s. Keeping this
// module lean and SDK-free is what lets the frontend ship (DM0) before the server exists.
//
//   GET  /api/snapshot   -> Snapshot                 (current full view)
//   GET  /api/events     -> text/event-stream        (SseEvent, one JSON object per `data:` line)

/** The three leagues the dashboard covers. NHL is intentionally excluded (coming-soon upstream). */
export type League = 'mlb' | 'nba' | 'nfl';

export const LEAGUES: readonly League[] = ['mlb', 'nba', 'nfl'];

/** API quota, mirrored from the `X-Quota-*` response headers on the server's last stat-api call. */
export interface Quota {
  limit: number;
  used: number;
  remaining: number;
}

/** Lifecycle of a single game. */
export type GameStatus = 'scheduled' | 'live' | 'final';

/** One side of a game. `score` is null before first pitch/tip/kickoff. */
export interface TeamSide {
  name: string;
  abbr: string;
  score: number | null;
}

/**
 * One sportsbook quote at one moment. Numeric fields are nullable because not every book posts
 * every market at every timestamp. Values mirror wire truth: American odds as integers, spread
 * and total as numbers.
 */
export interface LinePoint {
  /** ISO-8601 timestamp of the quote. */
  t: string;
  /** Home spread / run line (e.g. -1.5). */
  spread: number | null;
  /** Game total (over/under). */
  total: number | null;
  /** Home moneyline, American odds. */
  moneylineHome: number | null;
  /** Away moneyline, American odds. */
  moneylineAway: number | null;
}

/** A single sportsbook's line-movement series for a game. */
export interface BookLines {
  book: string;
  points: LinePoint[];
}

/**
 * One box-score batting line. Kept to a compact, MLB-shaped set for the demo; per-sport box
 * shapes can be modeled later. `avg` stays a string to preserve the `.312` wire formatting the
 * API returns for decimals (no float rounding).
 */
export interface BoxLine {
  team: string;
  player: string;
  position: string;
  ab: number;
  r: number;
  h: number;
  rbi: number;
  hr: number;
  bb: number;
  so: number;
  avg: string;
}

/** Where a market-implied win-probability point comes from. Pluggable so exposure can evolve. */
export type WpSource = 'sportsbook-implied' | 'kalshi';

/** One market-implied win-probability observation for the home team. */
export interface MarketPulsePoint {
  source: WpSource;
  /** ISO-8601 timestamp of the observation. */
  t: string;
  /** Home win probability in [0, 1]. */
  homeWinProb: number;
}

/** The per-game deep view, lazily attached only to games the server is actively polling. */
export interface GameDetail {
  /** Line movement, one series per book. */
  lines: BookLines[];
  /** Box score lines. */
  box: BoxLine[];
  /** Market-implied win-probability pulse, one or more sources. */
  pulse: MarketPulsePoint[];
}

/** A game card on the scoreboard. `detail` is present only for games in their live window. */
export interface GameCard {
  id: string;
  away: TeamSide;
  home: TeamSide;
  status: GameStatus;
  /** ISO-8601 scheduled/actual start time. */
  startTime: string;
  detail?: GameDetail;
}

/** Per-league slice of the snapshot. Offseason leagues carry no games but still report `asOf`. */
export interface LeagueSnapshot {
  status: 'in-season' | 'offseason';
  games: GameCard[];
  /** ISO-8601 timestamp this league's data was last refreshed by the poller. */
  asOf: string;
}

/** The whole dashboard view at one instant. */
export interface Snapshot {
  /** ISO-8601 timestamp this snapshot was assembled. */
  generatedAt: string;
  /** Live quota, or null if the server hasn't made a metered call yet. */
  quota: Quota | null;
  leagues: Record<League, LeagueSnapshot>;
}

/**
 * SSE messages pushed to browsers. The first message after connect is always a full `snapshot`;
 * subsequent `patch` messages replace just one league's games as the poller refreshes them.
 */
export type SseEvent =
  | { type: 'snapshot'; data: Snapshot }
  | { type: 'patch'; league: League; games: GameCard[] };
