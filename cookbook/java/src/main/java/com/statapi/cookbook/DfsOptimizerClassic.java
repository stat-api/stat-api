// Build a diversified NFL Classic DFS lineup portfolio
// Generated from schema/api/examples/dfs-optimizer-classic.yml — do not edit.
package com.statapi.cookbook;

import com.statapi.*;
import com.statapi.dfs.*;
import java.util.*;

public final class DfsOptimizerClassic {
    public static void main(String[] args) {
        StatApi api = new StatApi(); // reads STAT_API_KEY from the environment

        // Optimize 5 diversified NFL Classic lineups
        DfsLineups.OptimizerLineupsResponse optRes = api.dfs().slates().optimizerLineups(1126128L, new DfsLineups.OptimizerLineupsRequest().lineupCount(5).lineupMinSalary(48500).minUniquePlayers(2).maxPlayerExposurePct(60.0));

        // Print portfolio metrics and ranked lineups
        System.out.println(String.format(Locale.US, "Slate %d (%s/%s) | lineups=%d unique=%d mean_pts=%.2f", optRes.slate().id(), optRes.slate().league(), optRes.slate().operator(), optRes.portfolio().lineupCount(), optRes.portfolio().uniquePlayers(), optRes.portfolio().meanProjectedPoints()));
        for (int i = 0; i < Math.min(5, optRes.lineups().size()); i++) {
            DfsLineups.OptimizerLineupView l = optRes.lineups().get(i);
            System.out.println(String.format(Locale.US, "Lineup #%d | salary=%d pts=%.2f stack=%s", l.rank(), l.salary(), l.projectedPoints(), l.stack().summary()));
        }

    }
}
