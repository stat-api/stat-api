// Generate EV-maximized NFL GPP lineups with Prefer: wait — DFS
// Generated from schema/api/examples/dfs-sim-gpp-build.yml — do not edit.

import { StatApi } from '@stat-api/client';

const api = new StatApi(); // reads STAT_API_KEY from the environment

// Run synchronous NFL GPP contest simulation
const simBuild = await api.dfs.slates.simLineups(
  1126128,
  {
    contest_id: 91632452,
    lineup_count: 5,
    sim_game_runs: 200,
    sim_seed: 42,
    diversity_weight: 0.25,
    max_player_exposure_pct: 60,
  },
  { wait: true },
);

// Print portfolio EV, ROI%, diversity, and lineup breakdown
console.log(
  `Slate ${simBuild.slate.id} | contest ${simBuild.contest.contest_id} | lineups=${simBuild.portfolio.lineup_count} ev=${simBuild.portfolio.payout.ev_dollars.toFixed(2)} roi_pct=${simBuild.portfolio.payout.roi_pct.toFixed(2)} diversity=${simBuild.portfolio.diversity.composite_diversity_score.toFixed(2)}`,
);
for (const l of simBuild.lineups.slice(0, 5)) {
  console.log(
    `SimLineup #${l.rank} | salary=${l.salary} proj=${l.projected_points.toFixed(2)} ev=${l.payout.ev_dollars.toFixed(2)} roi_pct=${l.payout.roi_pct.toFixed(2)} win_pct=${l.payout.win_pct.toFixed(2)}`,
  );
}
