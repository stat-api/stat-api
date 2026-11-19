// Generate EV-optimized NBA Classic GPP lineups — DFS
// Generated from schema/api/examples/dfs-sim-nba-build.yml — do not edit.

import { StatApi } from '@stat-api/client';

const api = new StatApi(); // reads STAT_API_KEY from the environment

// Run NBA contest SIM lineup build
const nbaSimBuild = await api.dfs.slates.simLineups(
  1243812,
  {
    contest_id: 108465399,
    lineup_count: 5,
    sim_game_runs: 200,
    sim_seed: 42,
    diversity_weight: 0.25,
    max_player_exposure_pct: 60,
  },
  { wait: true },
);

// Print NBA SIM portfolio and lineups
console.log(
  `Slate ${nbaSimBuild.slate.id} | contest ${nbaSimBuild.contest.contest_id} | lineups=${nbaSimBuild.portfolio.lineup_count} ev=${nbaSimBuild.portfolio.payout.ev_dollars.toFixed(2)} roi_pct=${nbaSimBuild.portfolio.payout.roi_pct.toFixed(2)} diversity=${nbaSimBuild.portfolio.diversity.composite_diversity_score.toFixed(2)}`,
);
for (const l of nbaSimBuild.lineups.slice(0, 5)) {
  console.log(
    `SimLineup #${l.rank} | salary=${l.salary} proj=${l.projected_points.toFixed(2)} ev=${l.payout.ev_dollars.toFixed(2)} roi_pct=${l.payout.roi_pct.toFixed(2)} win_pct=${l.payout.win_pct.toFixed(2)}`,
  );
}
