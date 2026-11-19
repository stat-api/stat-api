// Extract NBA empirical bell curves and quantiles
// Generated from schema/api/examples/nba-sim-distributions.yml — do not edit.

import { StatApi } from '@stat-api/client';

const api = new StatApi(); // reads STAT_API_KEY from the environment

// Run NBA SIM with empirical distributions enabled
const distRes = await api.sim.nba.run({
  game_id: 6029936,
  n_draws: 200,
  seed: 42,
  include: {
    summary: true,
    distributions: true,
    scorebox: false,
    pbp: false,
    scenarios: false,
    simulations: false,
  },
});

// Print game and player quantile distributions
const dist = distRes.output.distributions;
const sumD = distRes.output.summary;
if (!dist || !sumD) throw new Error('missing distributions or summary');
console.log(
  `Game ${sumD.game_id} | total mean=${dist.total_points.mean.toFixed(2)} p10=${dist.total_points.p10.toFixed(2)} p50=${dist.total_points.p50.toFixed(2)} p90=${dist.total_points.p90.toFixed(2)}`,
);
console.log(
  `SpreadHome mean=${dist.spread_margin_home.mean.toFixed(2)} p10=${dist.spread_margin_home.p10.toFixed(2)} p50=${dist.spread_margin_home.p50.toFixed(2)} p90=${dist.spread_margin_home.p90.toFixed(2)}`,
);
console.log(
  `Teams ${sumD.away_team.team_code}=${dist.away_points.mean.toFixed(2)} ${sumD.home_team.team_code}=${dist.home_points.mean.toFixed(2)}`,
);
const sortedCurves = [...dist.players].sort(
  (a, b) => b.dk_pts.mean - a.dk_pts.mean || a.player_id - b.player_id,
);
for (const p of sortedCurves.slice(0, 5)) {
  console.log(
    `Dist ${p.name} (${p.team_code}) | dk_p50=${p.dk_pts.p50.toFixed(2)} dk_p90=${p.dk_pts.p90.toFixed(2)} pts_p50=${p.points.p50.toFixed(2)}`,
  );
}
