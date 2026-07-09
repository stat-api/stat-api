// /scores [league] [date] — scoreboard for a league on a date (YYYYMMDD, ET).
import type { StatApi } from '@stat-api/client';
import { cache, scoresKey, TTL } from '../cache';
import { mlbTeamAbbrevMap, type TeamLeague } from '../leagues';
import { offseasonEmbed, scoresEmbed, statFooter, type Embed, type ScoreLine } from '../format';

export interface ScoresArgs {
  league: TeamLeague;
  /** YYYYMMDD in US Eastern; resolved by the caller (main.ts defaults today). */
  date: string;
}

/** The normalized slate cached under `scoresKey` — also what /box autocomplete
 *  reads (hence `gameId` on every line). */
export interface Slate {
  games: ScoreLine[];
}

export async function fetchSlate(api: StatApi, league: TeamLeague, date: string): Promise<Slate> {
  const day = Number(date);
  if (league === 'nba') {
    const { games } = await api.nba.games.list({ day, limit: 100 });
    return {
      games: games.map((g) => ({
        gameId: g.id,
        away: g.away_team,
        home: g.home_team,
        awayScore: g.away_team_score,
        homeScore: g.home_team_score,
        status: g.status,
        startTime: g.game_time,
      })),
    };
  }
  // MLB games carry only team ids — resolve abbreviations through the cache.
  const teams = await mlbTeamAbbrevMap(api);
  const { games } = await api.mlb.games.list({ day, limit: 100 });
  return {
    games: games.map((g) => ({
      gameId: g.id,
      away: teams.get(g.away_team_id) ?? `#${g.away_team_id}`,
      home: teams.get(g.home_team_id) ?? `#${g.home_team_id}`,
      awayScore: g.away_team_score,
      homeScore: g.home_team_score,
      status: g.status,
      startTime: g.game_time,
    })),
  };
}

export async function handleScores(api: StatApi, args: ScoresArgs): Promise<Embed> {
  const { value, ...meta } = await cache.fetch(scoresKey(args.league, args.date), TTL.scores, () =>
    fetchSlate(api, args.league, args.date),
  );
  const footer = statFooter(meta);
  if (value.games.length === 0) return offseasonEmbed(args.league, args.date, footer);
  return scoresEmbed(args.league, args.date, value.games, footer);
}
