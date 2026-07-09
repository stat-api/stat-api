// /box <game> — league-appropriate box score for one game.
import type { StatApi } from '@stat-api/client';
import { cache, cacheKey, TTL } from '../cache';
import { mlbTeamAbbrevMap, rosterNameMap, type TeamLeague } from '../leagues';
import { boxEmbed, statFooter, type BoxScore, type Embed } from '../format';

export interface BoxArgs {
  league: TeamLeague;
  gameId: number;
}

export async function handleBox(api: StatApi, args: BoxArgs): Promise<Embed> {
  const { value, ...meta } = await cache.fetch(cacheKey('box', args.league, { game_id: args.gameId }), TTL.box, () =>
    args.league === 'nba' ? fetchBoxNba(api, args.gameId) : fetchBoxMlb(api, args.gameId),
  );
  return boxEmbed(value, statFooter(meta));
}

async function fetchBoxNba(api: StatApi, gameId: number): Promise<BoxScore> {
  const game = await api.nba.games.get(gameId);
  const { game_player_stats } = await api.nba.game_player_stats.list({ game_id: gameId, limit: 100 });
  const [awayNames, homeNames] = await Promise.all([
    rosterNameMap(api, 'nba', game.away_team_id),
    rosterNameMap(api, 'nba', game.home_team_id),
  ]);

  const leaders = (teamId: number, names: Map<number, string>): string[] =>
    game_player_stats
      .filter((s) => s.team_id === teamId)
      .sort((a, b) => b.pts - a.pts)
      .slice(0, 5)
      .map((s) => `${(names.get(s.player_id) ?? `#${s.player_id}`).padEnd(16)} ${s.pts} pts  ${s.rebounds} reb  ${s.assists} ast`);

  return {
    league: 'nba',
    status: game.status,
    away: { name: game.away_team, score: game.away_team_score, leaders: leaders(game.away_team_id, awayNames) },
    home: { name: game.home_team, score: game.home_team_score, leaders: leaders(game.home_team_id, homeNames) },
  };
}

async function fetchBoxMlb(api: StatApi, gameId: number): Promise<BoxScore> {
  const game = await api.mlb.games.get(gameId);
  const teams = await mlbTeamAbbrevMap(api);
  const [batters, pitchers, awayNames, homeNames] = await Promise.all([
    api.mlb.game_player_batter_stats.list({ game_id: gameId, limit: 100 }),
    api.mlb.game_player_pitching_stats.list({ game_id: gameId, limit: 100 }),
    rosterNameMap(api, 'mlb', game.away_team_id),
    rosterNameMap(api, 'mlb', game.home_team_id),
  ]);

  const teamLines = (teamId: number, names: Map<number, string>): string[] => {
    const bats = batters.game_player_batter_stats
      .filter((b) => b.team_id === teamId)
      .sort((a, b) => b.hits - a.hits || b.runs_batted_in - a.runs_batted_in)
      .slice(0, 3)
      .map((b) => `${(names.get(b.player_id) ?? `#${b.player_id}`).padEnd(16)} ${b.hits}-${b.at_bats}, ${b.runs_batted_in} RBI`);
    const pits = pitchers.game_player_pitching_stats
      .filter((p) => p.team_id === teamId)
      .sort((a, b) => b.strikeouts_pitched - a.strikeouts_pitched)
      .slice(0, 1)
      .map((p) => `${(names.get(p.player_id) ?? `#${p.player_id}`).padEnd(16)} ${p.innings_pitched} IP, ${p.strikeouts_pitched} K, ${p.earned_runs} ER`);
    return pits.length ? [...bats, '— pitching —', ...pits] : bats;
  };

  const abbrev = (teamId: number): string => teams.get(teamId) ?? `#${teamId}`;
  return {
    league: 'mlb',
    status: game.status,
    away: { name: abbrev(game.away_team_id), score: game.away_team_score, leaders: teamLines(game.away_team_id, awayNames) },
    home: { name: abbrev(game.home_team_id), score: game.home_team_score, leaders: teamLines(game.home_team_id, homeNames) },
  };
}
