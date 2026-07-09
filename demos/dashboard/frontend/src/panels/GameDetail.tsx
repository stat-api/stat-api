import type { BoxLine, GameCard } from '../lib/contract';
import type { LineSeries } from '../components/LineChart';
import { LineChart } from '../components/LineChart';
import { formatClock } from '../lib/format';
import styles from './GameDetail.module.css';

const BOOK_COLORS = ['#5db0ff', '#f4a63b', '#38d39f'];

interface GameDetailProps {
  game: GameCard;
}

export function GameDetail({ game }: GameDetailProps) {
  const detail = game.detail;
  if (!detail) return null;

  const lineSeries: LineSeries[] = detail.lines.map((book, i) => ({
    label: book.book,
    color: BOOK_COLORS[i % BOOK_COLORS.length]!,
    points: book.points
      .map((p) => ({ t: new Date(p.t).getTime(), v: p.total }))
      .filter((pt): pt is { t: number; v: number } => pt.v !== null),
  }));

  return (
    <section className={styles.detail}>
      <header className={styles.head}>
        <h2 className={styles.matchup}>
          {game.away.abbr} <span className={styles.at}>@</span> {game.home.abbr}
        </h2>
        <span className={styles.score}>
          {game.away.score ?? '—'} <span className={styles.dash}>–</span> {game.home.score ?? '—'}
        </span>
      </header>

      <div className={styles.block}>
        <div className={styles.blockHead}>
          <h3 className={styles.blockTitle}>Line movement · total</h3>
          <Legend series={lineSeries} />
        </div>
        <LineChart
          series={lineSeries}
          height={200}
          yTicks={4}
          formatY={(v) => v.toFixed(1)}
          formatX={(t) => formatClock(new Date(t).toISOString())}
        />
      </div>

      <div className={styles.block}>
        <h3 className={styles.blockTitle}>Box score</h3>
        <BoxTable box={detail.box} />
      </div>
    </section>
  );
}

function Legend({ series }: { series: LineSeries[] }) {
  return (
    <div className={styles.legend}>
      {series.map((s) => (
        <span key={s.label} className={styles.legendItem}>
          <span className={styles.swatch} style={{ backgroundColor: s.color }} />
          {s.label}
        </span>
      ))}
    </div>
  );
}

function BoxTable({ box }: { box: BoxLine[] }) {
  return (
    <div className={styles.tableWrap}>
      <table className={styles.table}>
        <thead>
          <tr>
            <th className={styles.left}>Batter</th>
            <th>AB</th>
            <th>R</th>
            <th>H</th>
            <th>RBI</th>
            <th>HR</th>
            <th>BB</th>
            <th>SO</th>
            <th>AVG</th>
          </tr>
        </thead>
        <tbody>
          {box.map((b, i) => (
            <tr key={`${b.team}-${b.player}-${i}`}>
              <td className={styles.left}>
                <span className={styles.boxTeam}>{b.team}</span>
                <span className={styles.boxPlayer}>{b.player}</span>
                <span className={styles.boxPos}>{b.position}</span>
              </td>
              <td>{b.ab}</td>
              <td>{b.r}</td>
              <td>{b.h}</td>
              <td>{b.rbi}</td>
              <td>{b.hr}</td>
              <td>{b.bb}</td>
              <td>{b.so}</td>
              <td className={styles.avg}>{b.avg}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
