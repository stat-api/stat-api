// Enforce correlated QB stacks, bring-backs, and player groups — DFS
// Generated from schema/api/examples/dfs-optimizer-stacks-groups.yml — do not edit.

import { StatApi } from '@stat-api/client';

const api = new StatApi(); // reads STAT_API_KEY from the environment

// Optimize 5 stacked lineups with group constraints
const stackedRes = await api.dfs.slates.optimizerLineups(1126128, {
  lineup_count: 5,
  lineup_allow_defense_vs_offense: false,
  lineup_stacks: [{ positions: ['QB', 'WR'], bring_back_count: 1 }],
  player_groups: [{ slate_player_ids: [44186556, 44186570], max_players: 1 }],
  max_player_exposure_pct: 60,
});

// Print stacked lineup summary
console.log(
  `Slate ${stackedRes.slate.id} (${stackedRes.slate.league}/${stackedRes.slate.operator}) | lineups=${stackedRes.portfolio.lineup_count} unique=${stackedRes.portfolio.unique_players} mean_pts=${stackedRes.portfolio.mean_projected_points.toFixed(2)}`,
);
for (const l of stackedRes.lineups.slice(0, 5)) {
  console.log(
    `Lineup #${l.rank} | salary=${l.salary} pts=${l.projected_points.toFixed(2)} stack=${l.stack.summary}`,
  );
}
