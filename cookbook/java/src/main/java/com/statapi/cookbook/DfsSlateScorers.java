// See who actually scored on a slate — DFS
// Generated from schema/api/examples/dfs-slate-scorers.yml — do not edit.
package com.statapi.cookbook;

import com.statapi.StatApi;
import com.statapi.dfs.*;
import java.util.*;

public final class DfsSlateScorers {
    public static void main(String[] args) {
        StatApi api = new StatApi(); // reads STAT_API_KEY from the environment

        // Page the player pool for names
        List<DFSSlatePlayer> pool = new ArrayList<>();
        for (DFSSlatePlayer row : api.dfs().slatePlayers().iterate(new DFSSlatePlayersListParams().slateId(129036L))) {
            pool.add(row);
        }

        // Index the pool by player id
        Map<Long, DFSSlatePlayer> names = new LinkedHashMap<>();
        for (DFSSlatePlayer row : pool) {
            names.put(row.playerId(), row);
        }

        // Page the slate's scoring rows
        List<DFSPlayerStat> stats = new ArrayList<>();
        for (DFSPlayerStat row : api.dfs().playerStats().iterate(new DFSPlayerStatsListParams().slateId(129036L))) {
            stats.add(row);
        }

        // Drop the players who did not play
        List<DFSPlayerStat> scored = new ArrayList<>();
        for (DFSPlayerStat r : stats) {
            if (r.fantasyPts() != null) {
                scored.add(r);
            }
        }

        // Rank by fantasy points
        List<DFSPlayerStat> best = new ArrayList<>(scored);
        best.sort(Comparator.comparingDouble((DFSPlayerStat r) -> r.fantasyPts() == null ? 0 : r.fantasyPts()).reversed());

        // Take the top fifteen
        List<DFSPlayerStat> top = best.subList(0, Math.min(15, best.size()));

        // Print the scoring board
        System.out.println("Top scorers on the slate");
        System.out.println(String.join("\t", "display_name", "position", "salary", "fantasy_pts"));
        for (DFSPlayerStat row : top) {
            System.out.println(String.join("\t", str(names.get(row.playerId()) != null ? names.get(row.playerId()).displayName() : row.playerId()), str(row.position()), str(row.salary()), str(row.fantasyPts())));
        }

    }

    static String str(Object v) {
        return v == null ? "" : String.valueOf(v);
    }
}
