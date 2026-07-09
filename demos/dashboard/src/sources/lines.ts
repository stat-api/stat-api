// Line-movement source: one game's sportsbook history → contract `BookLines[]`,
// one series per book, points in chronological order.
//
// A single `game_lines?game_id=<id>` list per game. Operator ids are resolved
// to human book names via `reference.operators`, fetched once and cached.
import type { BookLines, League, LinePoint } from '../contract.ts';
import type { StatApiClient } from '../sdk.ts';

/** id → operator display name (e.g. 5 → "DraftKings"). */
export type OperatorMap = ReadonlyMap<number, string>;

/** Fields the builder reads, common across all three league line rows. */
interface AnyLineRow {
  operator_id: number;
  captured_at: string;
  spread?: string | null;
  total?: string | null;
  moneyline_home?: number | null;
  moneyline_away?: number | null;
}

/** Fetch and cache the operator id → name map. */
export async function fetchOperatorMap(api: StatApiClient, limit: number): Promise<OperatorMap> {
  const { operators } = await api.reference.operators.list({ limit });
  const map = new Map<number, string>();
  for (const op of operators) map.set(op.id, op.name);
  return map;
}

/** Fetch one game's line movement and group it into per-book series. */
export async function fetchLines(
  api: StatApiClient,
  league: League,
  gameId: number,
  operators: OperatorMap,
  limit: number,
): Promise<BookLines[]> {
  const { game_lines } = await api[league].game_lines.list({ game_id: gameId, limit });
  const rows: AnyLineRow[] = game_lines;

  const byBook = new Map<number, LinePoint[]>();
  for (const row of rows) {
    const points = byBook.get(row.operator_id) ?? [];
    points.push({
      t: row.captured_at,
      spread: parseNum(row.spread),
      total: parseNum(row.total),
      moneylineHome: row.moneyline_home ?? null,
      moneylineAway: row.moneyline_away ?? null,
    });
    byBook.set(row.operator_id, points);
  }

  return [...byBook.entries()]
    .map(([operatorId, points]) => ({
      book: operators.get(operatorId) ?? `Book ${operatorId}`,
      points: points.sort((a, b) => a.t.localeCompare(b.t)),
    }))
    .sort((a, b) => a.book.localeCompare(b.book));
}

/** Wire decimals arrive as strings (precision-preserving); parse to number|null. */
function parseNum(value: string | null | undefined): number | null {
  if (value === null || value === undefined) return null;
  const n = Number(value);
  return Number.isFinite(n) ? n : null;
}
