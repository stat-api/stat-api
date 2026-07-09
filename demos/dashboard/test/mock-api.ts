// A hand-fed, call-counting StatApiClient for the poller tests. No network.
//
// State rows are typed loosely (Partial) and cast to the SDK response types at
// the boundary — a source only reads a handful of fields per row, so building
// full 30-column rows would be noise. The `counts` map records every list call
// so tests can assert the per-cycle request budget.
import type {
  MLBGame,
  MLBTeam,
  MLBGameLine,
  MLBGamePlayerBatterStat,
  MLBPlayer,
  ReferenceOperator,
  KalshiMarket,
  NBAGame,
  NFLGame,
  NBATeam,
  NFLTeam,
  MLBSeason,
  NBASeason,
  NFLSeason,
  NBAGameLinesListResponse,
  NFLGameLinesListResponse,
  KalshiEventsListResponse,
} from '@stat-api/client';
import type { StatApiClient } from '../src/sdk.ts';

export interface MockState {
  mlbGames: Partial<MLBGame>[];
  mlbTeams: Partial<MLBTeam>[];
  mlbLines: Partial<MLBGameLine>[];
  mlbBatters: Partial<MLBGamePlayerBatterStat>[];
  mlbPlayers: Partial<MLBPlayer>[];
  operators: Partial<ReferenceOperator>[];
  kalshiMarkets: Partial<KalshiMarket>[];
  nbaGames: Partial<NBAGame>[];
  nbaTeams: Partial<NBATeam>[];
  nflGames: Partial<NFLGame>[];
  nflTeams: Partial<NFLTeam>[];
  mlbSeasons: Partial<MLBSeason>[];
  nbaSeasons: Partial<NBASeason>[];
  nflSeasons: Partial<NFLSeason>[];
}

/** One recorded list call: which resource, and the params it was given. */
export interface RecordedCall {
  key: string;
  params: unknown;
}

export interface MockApi {
  api: StatApiClient;
  counts: Record<string, number>;
  calls: RecordedCall[];
}

export function emptyState(): MockState {
  return {
    mlbGames: [],
    mlbTeams: [],
    mlbLines: [],
    mlbBatters: [],
    mlbPlayers: [],
    operators: [],
    kalshiMarkets: [],
    nbaGames: [],
    nbaTeams: [],
    nflGames: [],
    nflTeams: [],
    mlbSeasons: [],
    nbaSeasons: [],
    nflSeasons: [],
  };
}

/** Server-side games filtering the mock reproduces: season / week / status
 *  (status also flips to newest-first, matching the API default_sort) / limit.
 *  Never filters by `day` — MLB/NBA `day` is applied server-side (the mock
 *  returns all and the tests supply "today's" slate), and NFL's `day` filter is
 *  client-side in the source. */
function pickGames<T extends { id?: number; status?: string; season_id?: number; week?: number; season_type?: string }>(
  rows: T[],
  opts: { status?: string; season_id?: number; week?: number; season_type?: string; limit?: number },
): T[] {
  let out = rows;
  if (opts.season_id !== undefined) out = out.filter(g => g.season_id === opts.season_id);
  if (opts.season_type !== undefined) out = out.filter(g => g.season_type === opts.season_type);
  if (opts.week !== undefined) out = out.filter(g => g.week === opts.week);
  if (opts.status !== undefined) {
    out = [...out].filter(g => g.status === opts.status).sort((a, b) => (b.id ?? 0) - (a.id ?? 0));
  }
  return opts.limit === undefined ? out : out.slice(0, opts.limit);
}

export function makeMockApi(state: MockState): MockApi {
  const counts: Record<string, number> = {};
  const calls: RecordedCall[] = [];
  const record = (key: string, params?: unknown): void => {
    counts[key] = (counts[key] ?? 0) + 1;
    calls.push({ key, params });
  };
  const resp = <T>(payload: Record<string, unknown>): Promise<T> =>
    Promise.resolve(payload as unknown as T);
  const envelope = (key: string, rows: unknown[]): Record<string, unknown> => ({
    [key]: rows,
    limit: rows.length,
    next_from_id: null,
  });
  const byGame = <T extends { game_id?: number }>(rows: T[], gameId?: number): T[] =>
    gameId === undefined ? rows : rows.filter(r => r.game_id === gameId);

  const emptyList = <T>(key: string): Promise<T> => resp<T>(envelope(key, []));

  const api: StatApiClient = {
    mlb: {
      games: {
        list: params => {
          record('mlb.games', params);
          return resp(
            envelope(
              'games',
              pickGames(state.mlbGames, {
                status: params?.status,
                season_id: params?.season_id,
                limit: params?.limit,
              }),
            ),
          );
        },
      },
      teams: {
        list: params => {
          record('mlb.teams', params);
          return resp(envelope('teams', state.mlbTeams));
        },
      },
      seasons: {
        list: params => {
          record('mlb.seasons', params);
          return resp(envelope('seasons', state.mlbSeasons));
        },
      },
      game_lines: {
        list: params => {
          record('mlb.game_lines', params);
          return resp(envelope('game_lines', byGame(state.mlbLines, params?.game_id)));
        },
      },
      game_player_batter_stats: {
        list: params => {
          record('mlb.game_player_batter_stats', params);
          return resp(
            envelope('game_player_batter_stats', byGame(state.mlbBatters, params?.game_id)),
          );
        },
      },
      players: {
        list: params => {
          record('mlb.players', params);
          const rows =
            params?.team_id === undefined
              ? state.mlbPlayers
              : state.mlbPlayers.filter(p => p.team_id === params.team_id);
          return resp(envelope('players', rows));
        },
      },
    },
    nba: {
      games: {
        list: params => {
          record('nba.games', params);
          return resp(
            envelope(
              'games',
              pickGames(state.nbaGames, {
                status: params?.status,
                season_id: params?.season_id,
                limit: params?.limit,
              }),
            ),
          );
        },
      },
      teams: {
        list: params => {
          record('nba.teams', params);
          return resp(envelope('teams', state.nbaTeams));
        },
      },
      seasons: {
        list: params => {
          record('nba.seasons', params);
          return resp(envelope('seasons', state.nbaSeasons));
        },
      },
      game_lines: {
        list: params => {
          record('nba.game_lines', params);
          return emptyList<NBAGameLinesListResponse>('game_lines');
        },
      },
    },
    nfl: {
      games: {
        list: params => {
          record('nfl.games', params);
          return resp(
            envelope(
              'games',
              pickGames(state.nflGames, {
                status: params?.status,
                season_id: params?.season_id,
                week: params?.week,
                season_type: params?.season_type,
                limit: params?.limit,
              }),
            ),
          );
        },
      },
      teams: {
        list: params => {
          record('nfl.teams', params);
          return resp(envelope('teams', state.nflTeams));
        },
      },
      seasons: {
        list: params => {
          record('nfl.seasons', params);
          return resp(envelope('seasons', state.nflSeasons));
        },
      },
      game_lines: {
        list: params => {
          record('nfl.game_lines', params);
          return emptyList<NFLGameLinesListResponse>('game_lines');
        },
      },
    },
    kalshi: {
      events: {
        list: params => {
          record('kalshi.events', params);
          return emptyList<KalshiEventsListResponse>('events');
        },
      },
      markets: {
        list: params => {
          record('kalshi.markets', params);
          return resp(envelope('markets', state.kalshiMarkets));
        },
      },
    },
    reference: {
      operators: {
        list: params => {
          record('reference.operators', params);
          return resp(envelope('operators', state.operators));
        },
      },
    },
  };

  return { api, counts, calls };
}
