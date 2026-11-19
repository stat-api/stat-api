// Find the highest-scoring games on a slate — DFS
// Generated from schema/api/examples/dfs-shootout-games.yml — do not edit.
package com.statapi.cookbook;

import com.statapi.StatApi;
import com.statapi.dfs.*;
import java.util.*;

public final class DfsShootoutGames {
    public static void main(String[] args) {
        StatApi api = new StatApi(); // reads STAT_API_KEY from the environment

        // Read every game in the slate's pool
        List<DFSGameStat> games = api.dfs().gameStats().list(new DFSGameStatsListParams().slateId(129036L).limit(50L)).rows();

        // Keep the games that finished
        List<DFSGameStat> played = new ArrayList<>();
        for (DFSGameStat r : games) {
            if (r.totalFpts() != null) {
                played.add(r);
            }
        }

        // Rank by total fantasy points
        List<DFSGameStat> best = new ArrayList<>(played);
        best.sort(Comparator.comparingDouble((DFSGameStat r) -> r.totalFpts() == null ? 0 : r.totalFpts()).reversed());

        // Take the five biggest games
        List<DFSGameStat> top = best.subList(0, Math.min(5, best.size()));

        // Print the game board
        System.out.println("Highest-scoring games on the slate");
        System.out.println(String.join("\t", "game_id", "total_fpts", "home_fpts", "away_fpts", "home_pts", "away_pts", "player_count"));
        for (DFSGameStat row : top) {
            System.out.println(String.join("\t", str(row.gameId()), str(row.totalFpts()), str(row.homeFpts()), str(row.awayFpts()), str(row.homePts()), str(row.awayPts()), str(row.playerCount())));
        }

    }

    static String str(Object v) {
        return v == null ? "" : String.valueOf(v);
    }
}
