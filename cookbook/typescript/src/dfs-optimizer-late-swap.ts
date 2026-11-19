// Late-swap unlocked slots on active contest entries — DFS
// Generated from schema/api/examples/dfs-optimizer-late-swap.yml — do not edit.

import { StatApi } from '@stat-api/client';

const api = new StatApi(); // reads STAT_API_KEY from the environment

// Run fast late swap with locked game 43545
const swapRes = await api.dfs.slates.optimizerLineups(1126128, {
  entries: [
    {
      entry_id: 'entry-1',
      slate_player_ids: [
        44186595, 44186556, 44186645, 44186641, 44186568, 44186860, 44186666, 44186564, 44187045,
      ],
    },
  ],
  as_of: '2025-01-01T00:00:00Z',
  locked_game_ids: [43545],
  min_projection_gain: 0,
});

// Print per-entry swap decision and gain
console.log(
  `Slate ${swapRes.slate.id} | entries=${swapRes.lineups.length} swapped=${swapRes.portfolio.swapped_count ?? 0} held=${swapRes.portfolio.held_count ?? 0}`,
);
const _nz = (v: number) => (Math.abs(v) < 0.005 ? 0 : v);
for (const l of swapRes.lineups) {
  const holdPts = l.hold_projected_points ?? l.projected_points;
  const gainPts = _nz(l.projection_gain ?? 0);
  console.log(
    `Entry ${l.entry_id ?? 'entry'} | action=${l.action ?? ''} salary=${l.salary} hold=${holdPts.toFixed(2)} proj=${l.projected_points.toFixed(2)} gain=${gainPts.toFixed(2)} locked_slots=${(l.locked_slots ?? []).length}`,
  );
}
