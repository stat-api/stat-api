// See the whole range of outcomes — NFL
// Generated from schema/api/examples/sim-distributions.yml — do not edit.

import { StatApi } from '@stat-api/client';

const api = new StatApi(); // reads STAT_API_KEY from the environment

// Ask for the distributions
const weeks = await api.nfl.weeks({ season: 2026 });
const gameId = weeks.free_game_id;
const run = await api.sim.nfl.run({
  game_id: gameId,
  n_draws: 5000,
  seed: 42,
  include: {
    summary: false,
    distributions: true,
    scorebox: false,
    pbp: false,
  },
});
const dist = run.output.distributions;
if (!dist) throw new Error('the run returned no distributions');
const total = dist.total_points;
console.log(
  `Game total | mean ${total.mean.toFixed(2)}, SD ${total.sd.toFixed(2)} | ` +
    `10th ${total.p10.toFixed(2)}, median ${total.p50.toFixed(2)}, 90th ${total.p90.toFixed(2)}`,
);
if (total.vegas_line !== null && total.p_over_vegas !== null) {
  console.log(
    `Market total ${total.vegas_line.toFixed(1)} | simulated chance of the over ${(total.p_over_vegas * 100).toFixed(1)}%`,
  );
}

// Read the histogram
console.log('Share of draws by game total, bins with 2% or more:');
for (const bin of total.bins) {
  if (bin.prob < 0.02) continue;
  console.log(
    `  [${bin.x_min.toFixed(2).padStart(6)}, ${bin.x_max.toFixed(2).padStart(6)}) ` +
      `${(bin.prob * 100).toFixed(2).padStart(5)}% ${'#'.repeat(Math.floor(bin.prob * 100))}`,
  );
}

// Find a draw that you can replay
const median = total.bins.find((b) => b.x_min <= total.p50 && total.p50 < b.x_max);
console.log(`Median bin: sample draw ${median?.sample_draw_idx ?? 'none'}`);

// Read the range of a player
const star = [...dist.players]
  .filter((p) => p.rec_yd !== null)
  .sort((a, b) => (b.rec_yd?.mean ?? 0) - (a.rec_yd?.mean ?? 0))[0];
if (star?.rec_yd) {
  const rec = star.rec_yd;
  console.log(
    `Top receiver ${star.player_id} (${star.position}) rec yds | ` +
      `10th ${rec.p10.toFixed(2)}, median ${rec.p50.toFixed(2)}, 90th ${rec.p90.toFixed(2)} | skew ${rec.skew.toFixed(2)}`,
  );
}
