// See where a player's fantasy points came from — DFS
// Generated from schema/api/examples/dfs-scoring-breakdown.yml — do not edit.
package com.statapi.cookbook;

import com.statapi.StatApi;
import com.statapi.dfs.*;
import java.util.*;

public final class DfsScoringBreakdown {
    public static void main(String[] args) {
        StatApi api = new StatApi(); // reads STAT_API_KEY from the environment

        // Read one player's slate history
        List<DFSPlayerStat> rows = api.dfs().playerStats().list(new DFSPlayerStatsListParams().playerId(301L).limit(200L)).rows();

        // Keep the games that were played
        List<DFSPlayerStat> played = new ArrayList<>();
        for (DFSPlayerStat r : rows) {
            if (r.fantasyPts() != null) {
                played.add(r);
            }
        }

        // Best game first
        List<DFSPlayerStat> best = new ArrayList<>(played);
        best.sort(Comparator.comparingDouble((DFSPlayerStat r) -> r.fantasyPts() == null ? 0 : r.fantasyPts()).reversed());

        // Take the single best game
        List<DFSPlayerStat> top = best.subList(0, Math.min(1, best.size()));

        // Split the score into its categories
        System.out.println("Where the points came from");
        System.out.println(String.join("\t", "fantasy_pts", "passing_yds_fpts", "passing_td_fpts", "rushing_yds_fpts", "rushing_td_fpts", "receiving_yds_fpts", "reception_fpts"));
        for (DFSPlayerStat row : top) {
            System.out.println(String.join("\t", str(row.fantasyPts()), str(row.passingYdsFpts()), str(row.passingTdFpts()), str(row.rushingYdsFpts()), str(row.rushingTdFpts()), str(row.receivingYdsFpts()), str(row.receptionFpts())));
        }

        // Compare against the raw box score
        System.out.println("The raw stats behind those points");
        System.out.println(String.join("\t", "passing_yds", "passing_tds", "rushing_yds", "rushing_tds", "receptions", "receiving_yds", "receiving_tds"));
        for (DFSPlayerStat row : top) {
            System.out.println(String.join("\t", str(row.passingYds()), str(row.passingTds()), str(row.rushingYds()), str(row.rushingTds()), str(row.receptions()), str(row.receivingYds()), str(row.receivingTds())));
        }

    }

    static String str(Object v) {
        return v == null ? "" : String.valueOf(v);
    }
}
