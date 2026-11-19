// Ask a what-if question — NFL
// Generated from schema/api/examples/sim-overrides.yml — do not edit.

import { StatApi } from '@stat-api/client';

const api = new StatApi(); // reads STAT_API_KEY from the environment

// Run the baseline
const weeks = await api.nfl.weeks({ season: 2026 });
const gameId = weeks.free_game_id;
const include = {
  summary: true,
  distributions: false,
  scorebox: false,
  pbp: false,
};
const baseline = await api.sim.nfl.run({ game_id: gameId, n_draws: 5000, seed: 42, include });

// Run the what-if with the same seed
const scenario = await api.sim.nfl.run({
  game_id: gameId,
  n_draws: 5000,
  seed: 42,
  include,
  home_spread: -6.5,
});

// Read the exact change
const before = baseline.output.summary;
const after = scenario.output.summary;
if (!before || !after) throw new Error('a run returned no summary');
const home = baseline.matchup.home_abbr;
const away = baseline.matchup.away_abbr;
const signed = (x: number, digits: number): string => `${x >= 0 ? '+' : ''}${x.toFixed(digits)}`;
console.log(`What if ${home} were a 6.5 point favorite? Seed 42 on both runs.`);
console.log(
  `Home win chance | ${(before.home_win_prob * 100).toFixed(1)}% -> ${(after.home_win_prob * 100).toFixed(1)}% ` +
    `(${signed((after.home_win_prob - before.home_win_prob) * 100, 1)} points)`,
);
console.log(
  `${home} points | ${before.home_team.points_mean.toFixed(2)} -> ${after.home_team.points_mean.toFixed(2)} ` +
    `(${signed(after.home_team.points_mean - before.home_team.points_mean, 2)})`,
);
console.log(
  `${away} points | ${before.away_team.points_mean.toFixed(2)} -> ${after.away_team.points_mean.toFixed(2)} ` +
    `(${signed(after.away_team.points_mean - before.away_team.points_mean, 2)})`,
);
