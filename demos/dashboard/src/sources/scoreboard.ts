// Scoreboard source: today's slate for one league → contract `GameCard`s.
//
// One `games?day=<YYYYMMDD>` list per league per cycle. Team display names and
// abbreviations come from a per-league `teams` list fetched once and cached by
// the poller (MLB game rows carry only team ids; NBA/NFL carry the full name
// inline but still need the abbreviation).
import type { GameCard, GameStatus, League, TeamSide } from '../contract.ts';
import type { StatApiClient } from '../sdk.ts';
import { mapGameStatus } from '../season.ts';

/** id → display name + abbreviation for one league's teams. */
export type TeamMap = ReadonlyMap<number, { name: string; abbr: string }>;

/** A scored game plus the numeric game id the detail tiers need to fetch by. */
export interface ScoredGame {
  readonly card: GameCard;
  readonly gameId: number;
}

export interface Scoreboard {
  readonly games: ScoredGame[];
  readonly asOf: string;
}

/** The fields the builder reads, common across all three league game rows. */
interface AnyGameRow {
  id: number;
  away_team_id: number;
  home_team_id: number;
  away_team_score: number;
  home_team_score: number;
  status: string;
  game_time: string;
  start_time?: string;
  away_team?: string | null;
  home_team?: string | null;
  day?: number;
}

/** Fetch and cache one league's teams as an id → name/abbr map. */
export async function fetchTeamMap(
  api: StatApiClient,
  league: League,
  limit: number,
): Promise<TeamMap> {
  const { teams } = await api[league].teams.list({ active: true, limit });
  const map = new Map<number, { name: string; abbr: string }>();
  for (const t of teams) {
    map.set(t.id, { name: t.full_name, abbr: t.abbreviation });
  }
  return map;
}

/**
 * Fetch one league's slate for the given ET day and map it to `GameCard`s.
 *
 * MLB and NBA expose a `day` (YYYYMMDD ET) list filter, so their slate is a
 * single server-filtered call. NFL has NO `day` filter — its games are keyed by
 * week — so it takes a different query shape (see `fetchNflDayRows`).
 */
export async function fetchScoreboard(
  api: StatApiClient,
  league: League,
  day: number,
  teams: TeamMap,
  limit: number,
): Promise<Scoreboard> {
  const rows =
    league === 'nfl'
      ? await fetchNflDayRows(api, day, limit)
      : await fetchDayFilteredRows(api, league, day, limit);
  const scored = rows
    .map(row => buildScoredGame(league, row, teams))
    .sort((a, b) => a.card.startTime.localeCompare(b.card.startTime));
  return { games: scored, asOf: new Date().toISOString() };
}

/** MLB/NBA: one server-side `day`-filtered list. */
async function fetchDayFilteredRows(
  api: StatApiClient,
  league: 'mlb' | 'nba',
  day: number,
  limit: number,
): Promise<AnyGameRow[]> {
  const { games } = await api[league].games.list({ day, limit });
  return games;
}

/**
 * NFL: there is no `day` filter, so resolve the current week from the most
 * recently completed game, pull that week and the next (so a game that has not
 * started yet today is still found across the week boundary), and keep only
 * rows whose `day` field matches today. Cost is ~3 small lists, not a full-season
 * scan. Edge: before any game of a new week is Final, the anchor is last week —
 * fetching week+1 is exactly what keeps today's opener visible; the sole gap is
 * the regular→postseason boundary (week numbering restarts), which is acceptable
 * for a demo scoreboard.
 */
async function fetchNflDayRows(
  api: StatApiClient,
  day: number,
  limit: number,
): Promise<AnyGameRow[]> {
  const recent = await api.nfl.games.list({ status: 'Final', limit: 1 });
  const anchor = recent.games[0];
  if (!anchor) return [];
  const rows: AnyGameRow[] = [];
  for (const week of [anchor.week, anchor.week + 1]) {
    const page = await api.nfl.games.list({
      season_id: anchor.season_id,
      season_type: anchor.season_type,
      week,
      limit,
    });
    rows.push(...page.games);
  }
  return rows.filter(row => row.day === day);
}

/** How many recent seasons to inspect when resolving the last completed one. */
const SEASON_LOOKBACK = 6;

/**
 * The most recent *completed* season's Final games, for an offseason league's
 * cards. Cannot just call `games?status=Final` — the games list defaults the
 * absent `season_id` to `current_season`, whose waterfall (in-season → earliest
 * UPCOMING → most-recent past) resolves to a forward-scheduled empty season in
 * the offseason gap (verified: NFL's next season exists with a future
 * start_date, so its Final set is empty). So resolve the season explicitly:
 * pick the season with the latest `end_date` that is already past, then pull its
 * finals (newest-first via the games `default_sort` of game/start time desc).
 */
export async function fetchLastFinalSlate(
  api: StatApiClient,
  league: League,
  teams: TeamMap,
  limit: number,
  now: Date = new Date(),
): Promise<Scoreboard> {
  const { seasons } = await api[league].seasons.list({ limit: SEASON_LOOKBACK });
  const nowMs = now.getTime();
  const completed = seasons
    .filter((s): s is typeof s & { end_date: string } => Boolean(s.end_date))
    .filter(s => Date.parse(s.end_date) < nowMs)
    .sort((a, b) => Date.parse(b.end_date) - Date.parse(a.end_date));
  const season = completed[0];
  if (!season) return { games: [], asOf: new Date().toISOString() };

  const { games } = await api[league].games.list({
    season_id: season.id,
    status: 'Final',
    limit,
  });
  const rows: AnyGameRow[] = games;
  const scored = rows
    .map(row => buildScoredGame(league, row, teams))
    .sort((a, b) => b.card.startTime.localeCompare(a.card.startTime)); // most recent first
  return { games: scored, asOf: new Date().toISOString() };
}

function buildScoredGame(league: League, row: AnyGameRow, teams: TeamMap): ScoredGame {
  const status = mapGameStatus(row.status);
  const startTime = row.start_time ?? row.game_time;
  return {
    gameId: row.id,
    card: {
      id: `${league}-${row.id}`,
      status,
      startTime,
      away: side(teams, row.away_team_id, row.away_team, row.away_team_score, status),
      home: side(teams, row.home_team_id, row.home_team, row.home_team_score, status),
    },
  };
}

function side(
  teams: TeamMap,
  teamId: number,
  inlineName: string | null | undefined,
  rawScore: number,
  status: GameStatus,
): TeamSide {
  const known = teams.get(teamId);
  const name = inlineName ?? known?.name ?? `Team ${teamId}`;
  const abbr = known?.abbr ?? deriveAbbr(name);
  // Before first pitch/tip/kickoff there is no score; the API reports 0, which
  // would render as a real "0 – 0" — the contract wants null instead.
  const score = status === 'scheduled' ? null : rawScore;
  return { name, abbr, score };
}

function deriveAbbr(name: string): string {
  const letters = name.replace(/[^A-Za-z]/g, '');
  return (letters.slice(0, 3) || name.slice(0, 3)).toUpperCase();
}
