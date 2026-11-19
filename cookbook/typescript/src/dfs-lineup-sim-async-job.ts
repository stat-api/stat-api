// Queue an async 202 SIM job and poll until completion — DFS
// Generated from schema/api/examples/dfs-sim-async-job.yml — do not edit.

import { StatApi } from '@stat-api/client';

const api = new StatApi(); // reads STAT_API_KEY from the environment

// Submit async SIM job (Prefer: respond-async)
const job = await api.dfs.slates.simLineupsStart(1126128, {
  contest_id: 91632452,
  lineup_count: 3,
  sim_game_runs: 200,
  sim_seed: 42,
});

// Poll job handle until ready
let completedPoll = await api.dfs.slates.simJob(1126128, job.job_id);
for (let i = 0; i < 60 && !completedPoll.ready; i++) {
  await new Promise((r) => setTimeout(r, 1000));
  completedPoll = await api.dfs.slates.simJob(1126128, job.job_id);
}
if (!completedPoll.ready || !completedPoll.result)
  throw new Error('SIM job did not complete in time');
const completed = completedPoll.result;

// Print initial job status and completed SIM lineups
console.log(`Job ${job.job_id} | initial_status=${job.status}`);
console.log(
  `Completed slate=${completed.slate.id} lineups=${completed.portfolio.lineup_count} ev=${completed.portfolio.payout.ev_dollars.toFixed(2)} roi_pct=${completed.portfolio.payout.roi_pct.toFixed(2)}`,
);
for (const l of completed.lineups.slice(0, 3)) {
  console.log(
    `AsyncLineup #${l.rank} | salary=${l.salary} proj=${l.projected_points.toFixed(2)} ev=${l.payout.ev_dollars.toFixed(2)} roi_pct=${l.payout.roi_pct.toFixed(2)}`,
  );
}
