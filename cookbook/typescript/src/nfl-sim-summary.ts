// Run your first NFL game simulation
// Generated from schema/api/examples/sim-summary.yml — do not edit.

import { StatApi } from '@stat-api/client';

const api = new StatApi(); // reads STAT_API_KEY from the environment

// Find the free game
const weeks = await api.nfl.weeks({ season: 2026 });
const gameId = weeks.free_game_id;
console.log(`Season ${weeks.season} | free game ${gameId} | ${weeks.games.length} games`);

// Run the game 5,000 times
const run = await api.sim.nfl.run({
  game_id: gameId,
  n_draws: 5000,
  seed: 42,
  include: {
    summary: true,
    distributions: false,
    scorebox: false,
    pbp: false,
  },
});
const summary = run.output.summary;
if (!summary) throw new Error('the run returned no summary');
console.log(
  `Simulated total ${summary.sim_total_mean.toFixed(2)} (SD ${summary.sim_total_sd.toFixed(2)}) | ` +
    `home win ${(summary.home_win_prob * 100).toFixed(1)}% | ` +
    `home cover ${(summary.home_cover_prob * 100).toFixed(1)}% | ` +
    `over ${(summary.over_prob * 100).toFixed(1)}%`,
);
console.log(`Box-score checks: ${summary.conservation_ok ? 'passed' : 'failed'}`);

// Rank the players
const top = [...summary.players].sort((a, b) => b.rec_yd_mean - a.rec_yd_mean).slice(0, 5);
console.log('Top 5 players by mean receiving yards:');
for (const p of top) {
  console.log(
    `  Player ${p.player_id} (${p.position}/${p.depth_role}) | mean ${p.rec_yd_mean.toFixed(1)} | ` +
      `median ${p.rec_yd_p50.toFixed(1)} | 90th percentile ${p.rec_yd_p90.toFixed(1)}`,
  );
}
