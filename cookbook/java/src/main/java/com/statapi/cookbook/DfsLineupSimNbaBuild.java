// Generate EV-optimized NBA Classic GPP lineups — DFS
// Generated from schema/api/examples/dfs-sim-nba-build.yml — do not edit.
package com.statapi.cookbook;

import com.statapi.*;
import com.statapi.dfs.*;
import java.util.*;

public final class DfsLineupSimNbaBuild {
    public static void main(String[] args) {
        StatApi api = new StatApi(); // reads STAT_API_KEY from the environment

        // Run NBA contest SIM lineup build
        DfsLineups.SimLineupsResponse nbaSimBuild = api.dfs().slates().simLineups(1243812L, new DfsLineups.SimLineupsRequest().contestId(108465399L).lineupCount(5).simGameRuns(200).simSeed(42).diversityWeight(0.25).maxPlayerExposurePct(60.0), new DfsLineups.SimRequestOptions().wait(true));

        // Print NBA SIM portfolio and lineups
        DfsLineups.PortfolioModel p = nbaSimBuild.portfolio();
        System.out.println(String.format(Locale.US, "Slate %d | contest %d | lineups=%d ev=%.2f roi_pct=%.2f diversity=%.2f", nbaSimBuild.slate().id(), nbaSimBuild.contest().contestId(), p.lineupCount(), p.payout().evDollars(), p.payout().roiPct(), p.diversity().compositeDiversityScore()));
        for (int i = 0; i < Math.min(5, nbaSimBuild.lineups().size()); i++) {
            DfsLineups.BuiltLineupModel l = nbaSimBuild.lineups().get(i);
            System.out.println(String.format(Locale.US, "SimLineup #%d | salary=%d proj=%.2f ev=%.2f roi_pct=%.2f win_pct=%.2f", l.rank(), l.salary(), l.projectedPoints(), l.payout().evDollars(), l.payout().roiPct(), l.payout().winPct()));
        }

    }
}
