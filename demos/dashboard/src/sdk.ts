// The seam between the poller and the published `@stat-api/client` SDK.
//
// Two things live here:
//
//  1. `StatApiClient` — a *structural* narrowing of the SDK's accessor tree to
//     exactly the `.list()` calls the poller makes. The real `StatApi` is
//     assignable to it (it has these accessors and more), and a test mock can
//     implement it directly — so the poller stays fully typed against both.
//
//  2. `QuotaMeter` — the SDK's public `.list()` returns only the row envelope
//     and discards the `X-Quota-*` headers. The one seam the SDK *does* expose
//     is a `fetch` override, so we wrap fetch, read the quota headers off every
//     response, and keep the latest. That is what the footer shows and what the
//     poller's self-throttle reads.
import type {
  MLBGamesListParams,
  MLBGamesListResponse,
  NBAGamesListParams,
  NBAGamesListResponse,
  NFLGamesListParams,
  NFLGamesListResponse,
  MLBTeamsListParams,
  MLBTeamsListResponse,
  NBATeamsListParams,
  NBATeamsListResponse,
  NFLTeamsListParams,
  NFLTeamsListResponse,
  MLBGameLinesListParams,
  MLBGameLinesListResponse,
  NBAGameLinesListParams,
  NBAGameLinesListResponse,
  NFLGameLinesListParams,
  NFLGameLinesListResponse,
  MLBGamePlayerBatterStatsListParams,
  MLBGamePlayerBatterStatsListResponse,
  MLBPlayersListParams,
  MLBPlayersListResponse,
  MLBSeasonsListParams,
  MLBSeasonsListResponse,
  NBASeasonsListParams,
  NBASeasonsListResponse,
  NFLSeasonsListParams,
  NFLSeasonsListResponse,
  KalshiEventsListParams,
  KalshiEventsListResponse,
  KalshiMarketsListParams,
  KalshiMarketsListResponse,
  ReferenceOperatorsListParams,
  ReferenceOperatorsListResponse,
} from '@stat-api/client';
import type { Quota } from './contract.ts';

interface ListResource<P, R> {
  list(params?: P): Promise<R>;
}

/** The exact slice of the SDK accessor tree the dashboard poller depends on. */
export interface StatApiClient {
  readonly mlb: {
    readonly games: ListResource<MLBGamesListParams, MLBGamesListResponse>;
    readonly teams: ListResource<MLBTeamsListParams, MLBTeamsListResponse>;
    readonly seasons: ListResource<MLBSeasonsListParams, MLBSeasonsListResponse>;
    readonly game_lines: ListResource<MLBGameLinesListParams, MLBGameLinesListResponse>;
    readonly game_player_batter_stats: ListResource<
      MLBGamePlayerBatterStatsListParams,
      MLBGamePlayerBatterStatsListResponse
    >;
    readonly players: ListResource<MLBPlayersListParams, MLBPlayersListResponse>;
  };
  readonly nba: {
    readonly games: ListResource<NBAGamesListParams, NBAGamesListResponse>;
    readonly teams: ListResource<NBATeamsListParams, NBATeamsListResponse>;
    readonly seasons: ListResource<NBASeasonsListParams, NBASeasonsListResponse>;
    readonly game_lines: ListResource<NBAGameLinesListParams, NBAGameLinesListResponse>;
  };
  readonly nfl: {
    readonly games: ListResource<NFLGamesListParams, NFLGamesListResponse>;
    readonly teams: ListResource<NFLTeamsListParams, NFLTeamsListResponse>;
    readonly seasons: ListResource<NFLSeasonsListParams, NFLSeasonsListResponse>;
    readonly game_lines: ListResource<NFLGameLinesListParams, NFLGameLinesListResponse>;
  };
  readonly kalshi: {
    readonly events: ListResource<KalshiEventsListParams, KalshiEventsListResponse>;
    readonly markets: ListResource<KalshiMarketsListParams, KalshiMarketsListResponse>;
  };
  readonly reference: {
    readonly operators: ListResource<ReferenceOperatorsListParams, ReferenceOperatorsListResponse>;
  };
}

/**
 * Wraps `fetch` to skim `X-Quota-*` off every stat-api response and keep the
 * most recent snapshot. Reading headers does not consume the body, so the SDK
 * still parses the response normally.
 */
export class QuotaMeter {
  private latest: Quota | null = null;

  /** Pass this to `new StatApi({ fetch: meter.fetch })`. Bound so it can be
   *  detached from the instance. Cast to the SDK's `fetch` option type — the
   *  SDK only ever *calls* it, never touches the `preconnect` static Bun's fetch
   *  type carries, so the wrapper need not reproduce it. */
  readonly fetch = ((input: FetchInput, init?: FetchInit): Promise<Response> =>
    this.metered(input, init)) as unknown as typeof globalThis.fetch;

  private async metered(input: FetchInput, init?: FetchInit): Promise<Response> {
    const resp = await globalThis.fetch(input, init);
    const quota = parseQuota(resp.headers);
    if (quota) this.latest = quota;
    return resp;
  }

  /** The most recent quota seen, or null before the first metered call. */
  current(): Quota | null {
    return this.latest;
  }
}

type FetchInput = Parameters<typeof globalThis.fetch>[0];
type FetchInit = Parameters<typeof globalThis.fetch>[1];

/** Parse the three `X-Quota-*` headers into a Quota, or null if any is absent
 *  or non-numeric — the footer only shows a fully-populated quota. */
export function parseQuota(headers: Headers): Quota | null {
  const limit = numHeader(headers.get('X-Quota-Limit'));
  const used = numHeader(headers.get('X-Quota-Used'));
  const remaining = numHeader(headers.get('X-Quota-Remaining'));
  if (limit === null || used === null || remaining === null) return null;
  return { limit, used, remaining };
}

function numHeader(value: string | null): number | null {
  if (value === null) return null;
  const n = Number(value);
  return Number.isFinite(n) ? n : null;
}
