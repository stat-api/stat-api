// Run an NBA possession simulation summary
// Generated from schema/api/examples/nba-sim-summary.yml — do not edit.

import { StatApi } from '@stat-api/client';

const api = new StatApi(); // reads STAT_API_KEY from the environment

// Discover the free NBA showcase game
const weeks = await api.nba.weeks({ season: 2026 });

// Run the NBA possession simulator
const simRes = await api.sim.nba.run({
  game_id: weeks.free_game_id,
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

// Print calibrated summary and top NBA players
const summary = simRes.output.summary;
if (!summary) throw new Error('missing output.summary');
console.log(`Game ${summary.game_id} | season ${summary.season} | draws ${summary.n_draws}`);
console.log(
  `Total mean=${summary.sim_total_mean.toFixed(2)} sd=${summary.sim_total_sd.toFixed(2)} | home_win_pct=${(summary.home_win_prob * 100).toFixed(2)}% away_win_pct=${(summary.away_win_prob * 100).toFixed(2)}%`,
);
console.log(
  `Teams away=${summary.away_team.team_code}:${summary.away_team.points_mean.toFixed(2)} home=${summary.home_team.team_code}:${summary.home_team.points_mean.toFixed(2)}`,
);
const sortedPlayers = [...summary.players].sort(
  (a, b) => b.dk_pts_mean - a.dk_pts_mean || a.player_id - b.player_id,
);
for (const p of sortedPlayers.slice(0, 5)) {
  console.log(
    `Player ${p.name} (${p.team_code}) | pts=${p.pts_mean.toFixed(2)} reb=${p.reb_mean.toFixed(2)} ast=${p.ast_mean.toFixed(2)} dk=${p.dk_pts_mean.toFixed(2)}`,
  );
}
