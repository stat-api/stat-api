// Meet six typical games — NFL
// Generated from schema/api/examples/sim-scorebox.yml — do not edit.

import { StatApi } from '@stat-api/client';

const api = new StatApi(); // reads STAT_API_KEY from the environment

// Ask for the scorebox
const weeks = await api.nfl.weeks({ season: 2026 });
const gameId = weeks.free_game_id;
const run = await api.sim.nfl.run({
  game_id: gameId,
  n_draws: 2000,
  seed: 42,
  include: {
    summary: false,
    distributions: false,
    scorebox: true,
    pbp: false,
    scorebox_limit: 1,
  },
});
const scorebox = run.output.scorebox;
if (!scorebox) throw new Error('the run returned no scorebox');

// Read the six archetypes
const away = run.matchup.away_abbr;
const home = run.matchup.home_abbr;
console.log(
  `Six typical games of 2000 draws | scores are Q1-Q2-Q3-Q4-OT-Total | ${away} at ${home}`,
);
for (const game of scorebox.archetypes) {
  const margin = game.final_margin_home;
  console.log(
    `  ${game.archetype ?? 'draw'} | draw ${game.draw_idx} | ${away} ${game.away_linescore.join('-')} | ` +
      `${home} ${game.home_linescore.join('-')} | home margin ${margin >= 0 ? '+' : ''}${margin}`,
  );
}
