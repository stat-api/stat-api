// Late-swap unlocked slots on active contest entries — DFS
// Generated from schema/api/examples/dfs-optimizer-late-swap.yml — do not edit.
package com.statapi.cookbook;

import com.statapi.*;
import com.statapi.dfs.*;
import java.util.*;

public final class DfsOptimizerLateSwap {
    public static void main(String[] args) {
        StatApi api = new StatApi(); // reads STAT_API_KEY from the environment

        // Run fast late swap with locked game 43545
        DfsLineups.OptimizerLineupsResponse swapRes = api.dfs().slates().optimizerLineups(1126128L, new DfsLineups.OptimizerLineupsRequest().entries(List.of(new DfsLineups.CustomLineupInput().entryId("entry-1").slatePlayerIds(List.of(44186595L, 44186556L, 44186645L, 44186641L, 44186568L, 44186860L, 44186666L, 44186564L, 44187045L)))).asOf("2025-01-01T00:00:00Z").lockedGameIds(List.of(43545L)).minProjectionGain(0.0));

        // Print per-entry swap decision and gain
        int swappedCnt = swapRes.portfolio().swappedCount() != null ? swapRes.portfolio().swappedCount() : 0;
        int heldCnt = swapRes.portfolio().heldCount() != null ? swapRes.portfolio().heldCount() : 0;
        System.out.println("Slate " + swapRes.slate().id() + " | entries=" + swapRes.lineups().size() + " swapped=" + swappedCnt + " held=" + heldCnt);
        for (DfsLineups.OptimizerLineupView l : swapRes.lineups()) {
            String entryId = (l.entryId() != null && !l.entryId().isEmpty()) ? l.entryId() : "entry";
            String action = l.action() != null ? l.action() : "";
            double holdPts = l.holdProjectedPoints() != null ? l.holdProjectedPoints() : l.projectedPoints();
            double gainPts = l.projectionGain() != null ? l.projectionGain() : 0.0;
            if (Math.abs(gainPts) < 0.005) gainPts = 0.0;
            int lockedCnt = l.lockedSlots() != null ? l.lockedSlots().size() : 0;
            System.out.println("Entry " + entryId + " | action=" + action + " salary=" + l.salary() + " hold=" + String.format(Locale.US, "%.2f", holdPts) + " proj=" + String.format(Locale.US, "%.2f", l.projectedPoints()) + " gain=" + String.format(Locale.US, "%.2f", gainPts) + " locked_slots=" + lockedCnt);
        }

    }
}
