// Re-select SIM portfolios in <50ms via cached pool_id — DFS
// Generated from schema/api/examples/dfs-sim-pool-reselect.yml — do not edit.

import { StatApi } from '@stat-api/client';

const api = new StatApi(); // reads STAT_API_KEY from the environment

// Build baseline SIM candidate pool
const baselinePool = await api.dfs.slates.simLineups(
  1126128,
  {
    contest_id: 91632452,
    lineup_count: 5,
    sim_game_runs: 200,
    sim_seed: 42,
    diversity_weight: 0.15,
    max_player_exposure_pct: 60,
  },
  { wait: true },
);

// Re-select portfolio from cached pool_id + Pareto frontier
const reselectedPoolId = baselinePool.sim.versions.pool_id;
if (!reselectedPoolId) throw new Error('missing sim.versions.pool_id');
const reselected = await api.dfs.slates.simSelectPool(1126128, {
  pool_id: reselectedPoolId,
  lineup_count: 5,
  diversity_weight: 0.35,
  min_unique_players: 2,
  max_player_exposure_pct: 60,
  include_diversity_frontier: true,
});

// Print baseline vs reselected comparison and frontier
const frontier = reselected.frontier ?? [];
console.log(
  `Pool total_pool=${reselected.diagnostics.total_pool_lineups} active=${reselected.diagnostics.active_pool_lineups}`,
);
console.log(
  `Baseline lineups=${baselinePool.portfolio.lineup_count} ev=${baselinePool.portfolio.payout.ev_dollars.toFixed(2)} diversity=${baselinePool.portfolio.diversity.composite_diversity_score.toFixed(2)}`,
);
console.log(
  `Reselected lineups=${reselected.portfolio.lineup_count} ev=${reselected.portfolio.payout.ev_dollars.toFixed(2)} diversity=${reselected.portfolio.diversity.composite_diversity_score.toFixed(2)} frontier=${frontier.length}`,
);
for (const fp of frontier.slice(0, 4)) {
  console.log(
    `Frontier weight=${fp.diversity_weight.toFixed(2)} | ev=${fp.ev_dollars.toFixed(2)} roi_pct=${fp.roi_pct.toFixed(2)} diversity=${fp.composite_diversity_score.toFixed(2)}`,
  );
}
