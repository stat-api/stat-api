# Queue an async 202 SIM job and poll until completion — DFS
# Generated from schema/api/examples/dfs-sim-async-job.yml — do not edit.
from statapi import StatApi
import time

api = StatApi()  # reads STAT_API_KEY from the environment

# Submit async SIM job (Prefer: respond-async)
job = api.dfs.slates.sim_lineups_start(1126128, {"contest_id": 91632452, "lineup_count": 3, "sim_game_runs": 200, "sim_seed": 42})

# Poll job handle until ready
completed_poll = api.dfs.slates.sim_job(1126128, job["job_id"])
for _ in range(60):
    if completed_poll["ready"]:
        break
    time.sleep(1)
    completed_poll = api.dfs.slates.sim_job(1126128, job["job_id"])
if not completed_poll["ready"] or completed_poll["result"] is None:
    raise RuntimeError("SIM job did not complete in time")
completed = completed_poll["result"]

# Print initial job status and completed SIM lineups
print(f"Job {job['job_id']} | initial_status={job['status']}")
p = completed["portfolio"]
print(f"Completed slate={completed['slate']['id']} lineups={p['lineup_count']} ev={p['payout']['ev_dollars']:.2f} roi_pct={p['payout']['roi_pct']:.2f}")
for l in completed["lineups"][:3]:
    lp = l["payout"]
    print(f"AsyncLineup #{l['rank']} | salary={l['salary']} proj={l['projected_points']:.2f} ev={lp['ev_dollars']:.2f} roi_pct={lp['roi_pct']:.2f}")
