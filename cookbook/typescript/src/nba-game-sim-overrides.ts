// Compare NBA Common Random Numbers spread overrides
// Generated from schema/api/examples/nba-sim-overrides.yml — do not edit.

import { StatApi } from '@stat-api/client';

const api = new StatApi(); // reads STAT_API_KEY from the environment

// Run the market baseline simulation (seed 42)
const baseline = await api.sim.nba.run({
  game_id: 6029936,
  n_draws: 200,
  seed: 42,
  include: {
    summary: true,
    distributions: false,
    scorebox: false,
    pbp: false,
    scenarios: false,
    simulations: false,
  },
});

// Re-simulate with an alternate home spread (-8.5, seed 42)
const shifted = await api.sim.nba.run({
  game_id: 6029936,
  n_draws: 200,
  seed: 42,
  include: {
    summary: true,
    distributions: false,
    scorebox: false,
    pbp: false,
    scenarios: false,
    simulations: false,
  },
  home_spread: -8.5,
});

// Print the CRN sensitivity delta
const baseS = baseline.output.summary;
const overS = shifted.output.summary;
if (!baseS || !overS) throw new Error('missing summary');
const _nz = (v: number) => (Math.abs(v) < 0.005 ? 0 : v);
console.log(
  `Baseline total=${baseS.sim_total_mean.toFixed(2)} home_win_pct=${(baseS.home_win_prob * 100).toFixed(2)}% spread_home=${baseS.sim_spread_home_mean.toFixed(2)}`,
);
console.log(
  `Override total=${overS.sim_total_mean.toFixed(2)} home_win_pct=${(overS.home_win_prob * 100).toFixed(2)}% spread_home=${overS.sim_spread_home_mean.toFixed(2)}`,
);
console.log(
  `Delta total=${_nz(overS.sim_total_mean - baseS.sim_total_mean).toFixed(2)} home_win_pp=${_nz((overS.home_win_prob - baseS.home_win_prob) * 100).toFixed(2)}`,
);
