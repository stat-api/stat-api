// Runtime configuration for the dashboard server, resolved once from the
// environment at boot. Every knob has a safe default so `bun run start` works
// with nothing but a STAT_API_KEY set.
import type { League } from './contract.ts';

/** Poll cadences in milliseconds, one per tier. These are the *base* cadences;
 *  the poller mechanically multiplies them when quota runs low (self-throttle). */
export interface Cadences {
  /** Scoreboard (games?day) per league. */
  readonly scoreboardMs: number;
  /** Line movement per in-window game. */
  readonly linesMs: number;
  /** Box score per in-window game (MLB only, per the compact contract shape). */
  readonly boxMs: number;
  /** Kalshi market pulse (NBA only, in-season). */
  readonly kalshiMs: number;
}

export interface Config {
  /** HTTP port. */
  readonly port: number;
  /** Leagues the dashboard covers. NHL is excluded upstream (coming-soon). */
  readonly leagues: readonly League[];
  readonly cadences: Cadences;
  /** Per-request row limits, kept small so a full slate stays inside budget. */
  readonly limits: {
    readonly scoreboard: number;
    readonly lines: number;
    readonly box: number;
    readonly kalshi: number;
    readonly teams: number;
  };
  /** When `X-Quota-Remaining` drops below this, every cadence is halved. */
  readonly quotaFloor: number;
  /** Minutes before a game's start that its detail (lines/box/pulse) tiers open. */
  readonly windowLeadMinutes: number;
  /** Optional YYYYMMDD ET day override. Lets a smoke run point the scoreboard
   *  at a specific populated day instead of "today". Unset in production. */
  readonly dayOverride: number | null;
}

const DEFAULTS = {
  port: 4400,
  scoreboardMs: 60_000,
  linesMs: 120_000,
  boxMs: 180_000,
  kalshiMs: 900_000,
  quotaFloor: 100_000,
  windowLeadMinutes: 60,
} as const;

const LEAGUES: readonly League[] = ['mlb', 'nba', 'nfl'];

function intEnv(name: string, fallback: number): number {
  const raw = process.env[name];
  if (raw === undefined || raw.trim() === '') return fallback;
  const n = Number(raw);
  if (!Number.isFinite(n) || n <= 0) {
    throw new Error(`dashboard: ${name}=${raw} is not a positive number`);
  }
  return Math.floor(n);
}

/** Resolve config from the environment. Throws loudly on malformed numeric env. */
export function loadConfig(env: NodeJS.ProcessEnv = process.env): Config {
  const rawDay = env.DASHBOARD_DAY?.trim();
  return {
    port: intEnv('PORT', DEFAULTS.port),
    leagues: LEAGUES,
    cadences: {
      scoreboardMs: intEnv('SCOREBOARD_MS', DEFAULTS.scoreboardMs),
      linesMs: intEnv('LINES_MS', DEFAULTS.linesMs),
      boxMs: intEnv('BOX_MS', DEFAULTS.boxMs),
      kalshiMs: intEnv('KALSHI_MS', DEFAULTS.kalshiMs),
    },
    limits: {
      scoreboard: intEnv('SCOREBOARD_LIMIT', 100),
      lines: intEnv('LINES_LIMIT', 500),
      box: intEnv('BOX_LIMIT', 100),
      kalshi: intEnv('KALSHI_LIMIT', 50),
      teams: intEnv('TEAMS_LIMIT', 60),
    },
    quotaFloor: intEnv('QUOTA_FLOOR', DEFAULTS.quotaFloor),
    windowLeadMinutes: intEnv('WINDOW_LEAD_MINUTES', DEFAULTS.windowLeadMinutes),
    dayOverride: rawDay ? intEnv('DASHBOARD_DAY', 0) : null,
  };
}
