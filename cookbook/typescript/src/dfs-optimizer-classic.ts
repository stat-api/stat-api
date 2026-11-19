// Build a diversified NFL Classic DFS lineup portfolio
// Generated from schema/api/examples/dfs-optimizer-classic.yml — do not edit.

import { StatApi } from '@stat-api/client';

const api = new StatApi(); // reads STAT_API_KEY from the environment

// Optimize 5 diversified NFL Classic lineups
const optRes = await api.dfs.slates.optimizerLineups(1126128, {
  lineup_count: 5,
  lineup_min_salary: 48500,
  min_unique_players: 2,
  max_player_exposure_pct: 60,
});

// Print portfolio metrics and ranked lineups
console.log(
  `Slate ${optRes.slate.id} (${optRes.slate.league}/${optRes.slate.operator}) | lineups=${optRes.portfolio.lineup_count} unique=${optRes.portfolio.unique_players} mean_pts=${optRes.portfolio.mean_projected_points.toFixed(2)}`,
);
for (const l of optRes.lineups.slice(0, 5)) {
  console.log(
    `Lineup #${l.rank} | salary=${l.salary} pts=${l.projected_points.toFixed(2)} stack=${l.stack.summary}`,
  );
}
