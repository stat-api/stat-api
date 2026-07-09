// Kalshi win-probability source (NBA only, in season).
//
// Kalshi lists a "will <team> win" binary market per game; its yes price in
// cents is a direct market estimate of that team's win probability. We join to
// the game through `competition_id` (= the NBA game id — see the cross-sport
// resolution note in the repo CLAUDE.md) and read the home team's market.
//
// This is labelled coarse ("hourly") because kalshi settlement/last-price moves
// far slower than the sportsbook tape. NBA is out of season through the summer,
// so in July this source returns nothing — the poller skips it cleanly.
import type { MarketPulsePoint } from '../contract.ts';
import type { WpContext, WpSource } from './wp-source.ts';

const KALSHI_MARKET_LIMIT = 50;

export function kalshiSource(): WpSource {
  return {
    kind: 'kalshi',
    pulse: kalshiPulse,
  };
}

async function kalshiPulse(ctx: WpContext): Promise<MarketPulsePoint[]> {
  if (!ctx.competitionId) return [];
  const { markets } = await ctx.api.kalshi.markets.list({
    competition_id: ctx.competitionId,
    limit: KALSHI_MARKET_LIMIT,
  });

  const home = ctx.homeTeamName.toLowerCase();
  const market = markets.find(
    m =>
      m.last_price !== null &&
      m.last_price !== undefined &&
      (m.title.toLowerCase().includes(home) || (m.subtitle ?? '').toLowerCase().includes(home)),
  );
  if (!market || market.last_price === null || market.last_price === undefined) return [];

  return [
    {
      source: 'kalshi',
      t: ctx.now.toISOString(),
      homeWinProb: clamp01(market.last_price / 100),
    },
  ];
}

function clamp01(x: number): number {
  return Math.min(1, Math.max(0, x));
}
