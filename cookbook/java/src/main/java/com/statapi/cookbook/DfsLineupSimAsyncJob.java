// Queue an async 202 SIM job and poll until completion — DFS
// Generated from schema/api/examples/dfs-sim-async-job.yml — do not edit.
package com.statapi.cookbook;

import com.statapi.*;
import com.statapi.dfs.*;
import java.util.*;

public final class DfsLineupSimAsyncJob {
    public static void main(String[] args) {
        StatApi api = new StatApi(); // reads STAT_API_KEY from the environment

        // Submit async SIM job (Prefer: respond-async)
        DfsLineups.SimJobStatusResponse job = api.dfs().slates().simLineupsStart(1126128L, new DfsLineups.SimLineupsRequest().contestId(91632452L).lineupCount(3).simGameRuns(200).simSeed(42));

        // Poll job handle until ready
        DfsLineups.SimJobPollResult completedPoll = api.dfs().slates().simJob(1126128L, job.jobId());
        for (int i = 0; i < 60 && !completedPoll.ready(); i++) {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException("Interrupted while polling SIM job", e);
            }
            completedPoll = api.dfs().slates().simJob(1126128L, job.jobId());
        }
        if (!completedPoll.ready() || completedPoll.result() == null) throw new IllegalStateException("SIM job did not complete in time");
        DfsLineups.SimLineupsResponse completed = completedPoll.result();

        // Print initial job status and completed SIM lineups
        System.out.println("Job " + job.jobId() + " | initial_status=" + job.status());
        DfsLineups.PortfolioModel p = completed.portfolio();
        System.out.println(String.format(Locale.US, "Completed slate=%d lineups=%d ev=%.2f roi_pct=%.2f", completed.slate().id(), p.lineupCount(), p.payout().evDollars(), p.payout().roiPct()));
        for (int i = 0; i < Math.min(3, completed.lineups().size()); i++) {
            DfsLineups.BuiltLineupModel l = completed.lineups().get(i);
            System.out.println(String.format(Locale.US, "AsyncLineup #%d | salary=%d proj=%.2f ev=%.2f roi_pct=%.2f", l.rank(), l.salary(), l.projectedPoints(), l.payout().evDollars(), l.payout().roiPct()));
        }

    }
}
