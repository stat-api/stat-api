// Presentation formatters shared across panels. Pure functions over wire values.

const clock = new Intl.DateTimeFormat('en-US', {
  hour: 'numeric',
  minute: '2-digit',
});

/** ISO timestamp -> local wall clock, e.g. "7:42 PM". */
export function formatClock(iso: string): string {
  return clock.format(new Date(iso));
}

/** ISO timestamp -> coarse "ago" label relative to now, e.g. "2m ago", "just now". */
export function formatAgo(iso: string, now: number = Date.now()): string {
  const seconds = Math.max(0, Math.round((now - new Date(iso).getTime()) / 1000));
  if (seconds < 10) return 'just now';
  if (seconds < 60) return `${seconds}s ago`;
  const minutes = Math.round(seconds / 60);
  if (minutes < 60) return `${minutes}m ago`;
  const hours = Math.round(minutes / 60);
  return `${hours}h ago`;
}

/** Integer with thousands separators, e.g. 4871597 -> "4,871,597". */
export function formatCount(n: number): string {
  return n.toLocaleString('en-US');
}

/** [0,1] probability -> percent string, e.g. 0.413 -> "41%". */
export function formatPercent(p: number): string {
  return `${Math.round(p * 100)}%`;
}

/** American odds with an explicit sign, e.g. 145 -> "+145", -140 -> "-140". */
export function formatMoneyline(odds: number): string {
  return odds > 0 ? `+${odds}` : `${odds}`;
}

/** Home spread with an explicit sign, e.g. -1.5 -> "-1.5", 1.5 -> "+1.5". */
export function formatSpread(spread: number): string {
  return spread > 0 ? `+${spread}` : `${spread}`;
}
