// =============================================================================
// leagues — per-league display metadata + shared cached lookups
// =============================================================================
//
// The SDK's accessor tree is typed per league (nba.games vs mlb.games have
// different row shapes), so command handlers branch on league explicitly. What
// IS shared lives here: display metadata, and the id→name lookups a box score
// needs. Both lookups go through the global cache with a long (reference) TTL —
// teams and rosters barely move within a session.
// =============================================================================

import type { StatApi } from '@stat-api/client';
import { cache, cacheKey, TTL } from './cache';

/** Leagues whose games carry a `day` (YYYYMMDD ET) filter — scores + box. */
export type TeamLeague = 'nba' | 'mlb';
/** Leagues with a player_news table exposed publicly. */
export type NewsLeague = 'nba' | 'nfl' | 'mlb';

export interface LeagueMeta {
  readonly code: string;
  readonly name: string;
  readonly emoji: string;
  readonly color: number;
}

const META: Record<string, LeagueMeta> = {
  nba: { code: 'nba', name: 'NBA', emoji: '🏀', color: 0xc8102e },
  nfl: { code: 'nfl', name: 'NFL', emoji: '🏈', color: 0x013369 },
  mlb: { code: 'mlb', name: 'MLB', emoji: '⚾', color: 0x041e42 },
};

export function leagueMeta(code: string): LeagueMeta {
  return META[code] ?? { code, name: code.toUpperCase(), emoji: '🏟️', color: 0x5865f2 };
}

/** "LeBron James" → "L. James". Falls back gracefully on missing parts. */
export function shortName(first: string, last: string): string {
  const initial = first.trim().charAt(0);
  const surname = last.trim();
  if (!surname) return first.trim() || 'Unknown';
  return initial ? `${initial}. ${surname}` : surname;
}

/** team_id → abbreviation for MLB. MLB games carry only ids (NBA/NFL games
 *  embed the names), so the scoreboard and box score resolve them through this
 *  (cached ~1h). */
export async function mlbTeamAbbrevMap(api: StatApi): Promise<Map<number, string>> {
  const { value } = await cache.fetch(cacheKey('teams', 'mlb'), TTL.reference, async () => {
    const { teams } = await api.mlb.teams.list({ limit: 60 });
    return teams.map((t) => ({ id: t.id, abbreviation: t.abbreviation }));
  });
  return new Map(value.map((t) => [t.id, t.abbreviation]));
}

/** player_id → short name for one team's roster (cached ~1h). Box scores use
 *  it to turn the game's stat rows into readable leader lines. */
export async function rosterNameMap(
  api: StatApi,
  league: TeamLeague,
  teamId: number,
): Promise<Map<number, string>> {
  const { value } = await cache.fetch(cacheKey('roster', league, { team_id: teamId }), TTL.reference, async () => {
    const rows =
      league === 'nba'
        ? (await api.nba.players.list({ team_id: teamId, limit: 100 })).players
        : (await api.mlb.players.list({ team_id: teamId, limit: 100 })).players;
    return rows.map((p) => ({ id: p.id, name: shortName(p.first_name, p.last_name) }));
  });
  return new Map(value.map((p) => [p.id, p.name]));
}
