import type { GameCard, MarketPulsePoint, WpSource } from '../lib/contract';
import type { LineSeries } from '../components/LineChart';
import { LineChart } from '../components/LineChart';
import { formatAgo, formatClock, formatPercent } from '../lib/format';
import styles from './MarketPulse.module.css';

const SOURCE_META: Record<WpSource, { label: string; color: string }> = {
  'sportsbook-implied': { label: 'Sportsbook-implied', color: '#5db0ff' },
  kalshi: { label: 'Kalshi', color: '#38d39f' },
};

interface MarketPulseProps {
  game: GameCard;
}

/**
 * Market-implied home win probability over the course of a game, one line per source. The source
 * is pluggable (`WpSource`) on purpose: sportsbook-implied covers all leagues today; kalshi is
 * NBA-only and appears in-season. The private win-probability model is never exposed here.
 */
export function MarketPulse({ game }: MarketPulseProps) {
  const grouped = groupBySource(game.detail?.pulse ?? []);
  const series: LineSeries[] = grouped.map(([source, points]) => ({
    label: SOURCE_META[source].label,
    color: SOURCE_META[source].color,
    points: points.map((p) => ({ t: new Date(p.t).getTime(), v: p.homeWinProb })),
  }));

  return (
    <section className={styles.pulse}>
      <header className={styles.head}>
        <h3 className={styles.title}>
          Market pulse · <span className={styles.team}>{game.home.abbr}</span> win probability
        </h3>
        <span className={styles.subtitle}>Market-implied — not a private model</span>
      </header>

      {series.length === 0 ? (
        <div className={styles.empty}>No market data for this game.</div>
      ) : (
        <>
          <LineChart
            series={series}
            height={190}
            yDomain={[0, 1]}
            yTicks={4}
            formatY={(v) => formatPercent(v)}
            formatX={(t) => formatClock(new Date(t).toISOString())}
          />
          <div className={styles.sources}>
            {grouped.map(([source, points]) => {
              const latest = points[points.length - 1]!;
              return (
                <div key={source} className={styles.sourceRow}>
                  <span className={styles.swatch} style={{ backgroundColor: SOURCE_META[source].color }} />
                  <span className={styles.sourceLabel}>{SOURCE_META[source].label}</span>
                  <span className={styles.sourceValue}>{formatPercent(latest.homeWinProb)}</span>
                  <span className={styles.freshness}>updated {formatAgo(latest.t)}</span>
                </div>
              );
            })}
          </div>
        </>
      )}
    </section>
  );
}

function groupBySource(points: MarketPulsePoint[]): [WpSource, MarketPulsePoint[]][] {
  const buckets = new Map<WpSource, MarketPulsePoint[]>();
  for (const point of points) {
    const bucket = buckets.get(point.source) ?? [];
    bucket.push(point);
    buckets.set(point.source, bucket);
  }
  return [...buckets.entries()];
}
