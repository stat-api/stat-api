// Re-select SIM portfolios in <50ms via cached pool_id — DFS
// Generated from schema/api/examples/dfs-sim-pool-reselect.yml — do not edit.
package com.statapi.cookbook;

import com.statapi.*;
import com.statapi.dfs.*;
import java.util.*;

public final class DfsLineupSimPoolReselect {
    public static void main(String[] args) {
        StatApi api = new StatApi(); // reads STAT_API_KEY from the environment

        // Build baseline SIM candidate pool
        DfsLineups.SimLineupsResponse baselinePool = api.dfs().slates().simLineups(1126128L, new DfsLineups.SimLineupsRequest().contestId(91632452L).lineupCount(5).simGameRuns(200).simSeed(42).diversityWeight(0.15).maxPlayerExposurePct(60.0), new DfsLineups.SimRequestOptions().wait(true));

        // Re-select portfolio from cached pool_id + Pareto frontier
        String reselectedPoolId = baselinePool.sim().versions().poolId();
        if (reselectedPoolId == null || reselectedPoolId.isEmpty()) throw new IllegalStateException("missing sim.versions.pool_id");
        DfsLineups.PortfolioSelectResponse reselected = api.dfs().slates().simSelectPool(1126128L, new DfsLineups.PortfolioSelectRequest().poolId(reselectedPoolId).lineupCount(5).diversityWeight(0.35).minUniquePlayers(2).maxPlayerExposurePct(60.0).includeDiversityFrontier(true));

        // Print baseline vs reselected comparison and frontier
        List<DfsLineups.DiversityFrontierPoint> frontier = reselected.frontier() != null ? reselected.frontier() : Collections.emptyList();
        DfsLineups.PortfolioSelectDiagnostics d = reselected.diagnostics();
        DfsLineups.PortfolioModel bp = baselinePool.portfolio();
        DfsLineups.PortfolioModel rp = reselected.portfolio();
        System.out.println("Pool total_pool=" + d.totalPoolLineups() + " active=" + d.activePoolLineups());
        System.out.println(String.format(Locale.US, "Baseline lineups=%d ev=%.2f diversity=%.2f", bp.lineupCount(), bp.payout().evDollars(), bp.diversity().compositeDiversityScore()));
        System.out.println(String.format(Locale.US, "Reselected lineups=%d ev=%.2f diversity=%.2f frontier=%d", rp.lineupCount(), rp.payout().evDollars(), rp.diversity().compositeDiversityScore(), frontier.size()));
        for (int i = 0; i < Math.min(4, frontier.size()); i++) {
            DfsLineups.DiversityFrontierPoint fp = frontier.get(i);
            System.out.println(String.format(Locale.US, "Frontier weight=%.2f | ev=%.2f roi_pct=%.2f diversity=%.2f", fp.diversityWeight(), fp.evDollars(), fp.roiPct(), fp.compositeDiversityScore()));
        }

    }
}
