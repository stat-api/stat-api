// Read a PGA golfer's leaderboard finishes
// Generated from schema/api/examples/leaderboard.yml — do not edit.
package com.statapi.cookbook;

import com.statapi.StatApi;
import com.statapi.pga.*;
import java.util.*;

public final class PgaLeaderboard {
    public static void main(String[] args) {
        StatApi api = new StatApi(); // reads STAT_API_KEY from the environment

        // Pick a golfer
        List<PGAPlayer> players = api.pga().players().list(new PGAPlayersListParams().limit(1L)).rows();

        // Read that golfer's finishes
        List<PGALeaderboard> board = api.pga().leaderboards().list(new PGALeaderboardsListParams().playerId((long) (players.get(0).id())).limit(25L)).rows();

        // Best finishes first
        List<PGALeaderboard> ranked = new ArrayList<>(board);
        ranked.sort(Comparator.comparingInt((PGALeaderboard r) -> r.rank() == null ? 0 : r.rank()));

        // Show the finishes
        System.out.println(String.join("\t", "rank", "sg_total"));
        for (PGALeaderboard row : ranked) {
            System.out.println(String.join("\t", str(row.rank()), str(row.sgTotal())));
        }

    }

    static String str(Object v) {
        return v == null ? "" : String.valueOf(v);
    }
}
