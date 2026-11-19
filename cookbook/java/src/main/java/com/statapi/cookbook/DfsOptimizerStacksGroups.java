// Enforce correlated QB stacks, bring-backs, and player groups — DFS
// Generated from schema/api/examples/dfs-optimizer-stacks-groups.yml — do not edit.
package com.statapi.cookbook;

import com.statapi.*;
import com.statapi.dfs.*;
import java.util.*;

public final class DfsOptimizerStacksGroups {
    public static void main(String[] args) {
        StatApi api = new StatApi(); // reads STAT_API_KEY from the environment

        // Optimize 5 stacked lineups with group constraints
        DfsLineups.OptimizerLineupsResponse stackedRes = api.dfs().slates().optimizerLineups(1126128L, new DfsLineups.OptimizerLineupsRequest().lineupCount(5).lineupAllowDefenseVsOffense(false).lineupStacks(List.of(new DfsLineups.StackBlock().positions(List.of("QB", "WR")).bringBackCount(1))).playerGroups(List.of(new DfsLineups.GroupBlock().slatePlayerIds(List.of(44186556L, 44186570L)).maxPlayers(1))).maxPlayerExposurePct(60.0));

        // Print stacked lineup summary
        System.out.println(String.format(Locale.US, "Slate %d (%s/%s) | lineups=%d unique=%d mean_pts=%.2f", stackedRes.slate().id(), stackedRes.slate().league(), stackedRes.slate().operator(), stackedRes.portfolio().lineupCount(), stackedRes.portfolio().uniquePlayers(), stackedRes.portfolio().meanProjectedPoints()));
        for (int i = 0; i < Math.min(5, stackedRes.lineups().size()); i++) {
            DfsLineups.OptimizerLineupView l = stackedRes.lineups().get(i);
            System.out.println(String.format(Locale.US, "Lineup #%d | salary=%d pts=%.2f stack=%s", l.rank(), l.salary(), l.projectedPoints(), l.stack().summary()));
        }

    }
}
