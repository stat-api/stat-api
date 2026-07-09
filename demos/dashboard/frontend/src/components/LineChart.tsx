import styles from './LineChart.module.css';

/** One line on the chart. `t` is epoch milliseconds; `v` is the plotted value. */
export interface LineSeries {
  label: string;
  color: string;
  points: { t: number; v: number }[];
}

interface LineChartProps {
  series: LineSeries[];
  height?: number;
  /** Fixed y-axis range; when omitted the domain is derived from the data with headroom. */
  yDomain?: [number, number];
  yTicks?: number;
  formatY?: (v: number) => string;
  formatX?: (t: number) => string;
}

// Internal coordinate system. The SVG scales to its container width via viewBox; these are
// design units, not pixels.
const WIDTH = 640;
const PAD = { top: 14, right: 16, bottom: 26, left: 48 };

/**
 * A dependency-free SVG line chart. Draws one polyline per series over a shared time axis with a
 * horizontal grid and endpoint markers. Used for both sportsbook line movement and the
 * market-implied win-probability pulse.
 */
export function LineChart({
  series,
  height = 220,
  yDomain,
  yTicks = 4,
  formatY = (v) => `${Math.round(v)}`,
  formatX = (t) => new Date(t).toISOString(),
}: LineChartProps) {
  const drawable = series.filter((s) => s.points.length > 0);
  const values = drawable.flatMap((s) => s.points.map((p) => p.v));
  if (values.length === 0) {
    return (
      <div className={styles.empty} style={{ height }}>
        No data
      </div>
    );
  }

  const times = drawable.flatMap((s) => s.points.map((p) => p.t));
  const xMin = Math.min(...times);
  const xMax = Math.max(...times);
  const [yMin, yMax] = yDomain ?? paddedDomain(values);

  const left = PAD.left;
  const right = WIDTH - PAD.right;
  const top = PAD.top;
  const bottom = height - PAD.bottom;

  const scaleX = (t: number) =>
    xMax === xMin ? (left + right) / 2 : left + ((t - xMin) / (xMax - xMin)) * (right - left);
  const scaleY = (v: number) =>
    yMax === yMin ? (top + bottom) / 2 : bottom - ((v - yMin) / (yMax - yMin)) * (bottom - top);

  const gridLines = Array.from({ length: yTicks + 1 }, (_, i) => yMin + ((yMax - yMin) * i) / yTicks);

  return (
    <svg
      className={styles.chart}
      viewBox={`0 0 ${WIDTH} ${height}`}
      preserveAspectRatio="xMidYMid meet"
      role="img"
    >
      {gridLines.map((gv, i) => {
        const y = scaleY(gv);
        return (
          <g key={`grid-${i}`}>
            <line className={styles.grid} x1={left} y1={y} x2={right} y2={y} />
            <text className={styles.label} x={left - 8} y={y} dominantBaseline="middle" textAnchor="end">
              {formatY(gv)}
            </text>
          </g>
        );
      })}

      <line className={styles.axis} x1={left} y1={bottom} x2={right} y2={bottom} />
      <text className={styles.label} x={left} y={height - 8} textAnchor="start">
        {formatX(xMin)}
      </text>
      <text className={styles.label} x={right} y={height - 8} textAnchor="end">
        {formatX(xMax)}
      </text>

      {drawable.map((s) => {
        const ordered = s.points.slice().sort((a, b) => a.t - b.t);
        const last = ordered[ordered.length - 1]!;
        const path = ordered.map((p) => `${scaleX(p.t).toFixed(1)},${scaleY(p.v).toFixed(1)}`).join(' ');
        return (
          <g key={s.label}>
            <polyline className={styles.line} points={path} stroke={s.color} />
            <circle className={styles.dot} cx={scaleX(last.t)} cy={scaleY(last.v)} r={3.2} fill={s.color} />
          </g>
        );
      })}
    </svg>
  );
}

/** Data-derived y-domain with 12% headroom so lines don't touch the frame. */
function paddedDomain(values: number[]): [number, number] {
  const min = Math.min(...values);
  const max = Math.max(...values);
  if (min === max) return [min - 1, max + 1];
  const pad = (max - min) * 0.12;
  return [min - pad, max + pad];
}
