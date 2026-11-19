// Replay one simulated game — NFL
// Generated from schema/api/examples/sim-trace.yml — do not edit.

import { StatApi } from '@stat-api/client';

const api = new StatApi(); // reads STAT_API_KEY from the environment

// Pick a draw from a run
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
const shootout = run.output.scorebox?.archetypes.find((g) => g.archetype === 'shootout_p95');
if (!shootout) throw new Error('the run has no shootout game');
console.log(`Replay draw ${shootout.draw_idx}, the 95th percentile shootout of 2000 draws`);

// Replay the draw with run
const replay = await api.sim.nfl.run({
  game_id: gameId,
  n_draws: 2000,
  seed: 42,
  include: {
    summary: false,
    distributions: false,
    scorebox: false,
    pbp: true,
    pbp_draws: [shootout.draw_idx],
  },
});
const traced = replay.output.pbp?.[0];
if (!traced) throw new Error('the run returned no play-by-play trace');
const away = run.matchup.away_abbr;
const home = run.matchup.home_abbr;
console.log(
  `${away} ${traced.scorebox.away_linescore[5]} - ${home} ${traced.scorebox.home_linescore[5]} | ` +
    `${traced.drives.length} drives | ${traced.plays.length} plays`,
);

// Read the drives and the plays
console.log('First 5 drives:');
for (const d of traced.drives.slice(0, 5)) {
  const team = d.posteam_is_home ? home : away;
  console.log(
    `  ${d.drive_number}. ${team} | Q${d.start_quarter} ${d.start_clock} | ${d.plays} plays, ${d.net_yards} yds | ` +
      `${d.result} | ${away} ${d.away_score_after} - ${home} ${d.home_score_after}`,
  );
}
console.log('First 5 plays:');
for (const p of traced.plays.slice(0, 5)) {
  console.log(
    `  ${p.clock_display} | ${p.down_distance_display} | ${p.yardline_display} | ${p.description}`,
  );
}
