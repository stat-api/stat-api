// Rank teams for a stack — DFS
// Generated from schema/api/examples/dfs-team-stacks.yml — do not edit.
package com.statapi.cookbook;

import com.statapi.StatApi;
import com.statapi.dfs.*;
import java.util.*;

public final class DfsTeamStacks {
    public static void main(String[] args) {
        StatApi api = new StatApi(); // reads STAT_API_KEY from the environment

        // Read both sides of every game on the slate
        List<DFSTeamStat> teams = api.dfs().teamStats().list(new DFSTeamStatsListParams().slateId(129036L).limit(100L)).rows();

        // Keep the teams that played
        List<DFSTeamStat> played = new ArrayList<>();
        for (DFSTeamStat r : teams) {
            if (r.fantasyPts() != null) {
                played.add(r);
            }
        }

        // Rank by fantasy points produced
        List<DFSTeamStat> best = new ArrayList<>(played);
        best.sort(Comparator.comparingDouble((DFSTeamStat r) -> r.fantasyPts() == null ? 0 : r.fantasyPts()).reversed());

        // Take the eight best offenses
        List<DFSTeamStat> top = best.subList(0, Math.min(8, best.size()));

        // Print the stack board
        System.out.println("Most productive offenses on the slate");
        System.out.println(String.join("\t", "team_id", "opponent_team_id", "is_home", "fantasy_pts", "pts", "total_yds", "player_count"));
        for (DFSTeamStat row : top) {
            System.out.println(String.join("\t", str(row.teamId()), str(row.opponentTeamId()), str(row.isHome()), str(row.fantasyPts()), str(row.pts()), str(row.totalYds()), str(row.playerCount())));
        }

    }

    static String str(Object v) {
        return v == null ? "" : String.valueOf(v);
    }
}
