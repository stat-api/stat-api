// Build a PGA golfer's scorecard
// Generated from schema/api/examples/scorecard.yml — do not edit.
package com.statapi.cookbook;

import com.statapi.StatApi;
import com.statapi.pga.*;
import java.util.*;

public final class PgaScorecard {
    public static void main(String[] args) {
        StatApi api = new StatApi(); // reads STAT_API_KEY from the environment

        // Pick a golfer
        List<PGAPlayer> players = api.pga().players().list(new PGAPlayersListParams().limit(1L)).rows();

        // Index the golfer by id for name lookups
        Map<Long, PGAPlayer> player_by_id = new LinkedHashMap<>();
        for (PGAPlayer row : players) {
            player_by_id.put(row.id(), row);
        }

        // Pull the hole-by-hole cards
        List<PGAPlayerHole> holes = api.pga().playerHoles().list(new PGAPlayerHolesListParams().playerId((long) (players.get(0).id()))).rows();

        // Render the scorecard
        System.out.println(String.join("\t", "full_name", "round_number", "hole_number", "to_par"));
        for (PGAPlayerHole row : holes) {
            System.out.println(String.join("\t", str(player_by_id.get(row.playerId()) != null ? player_by_id.get(row.playerId()).fullName() : row.playerId()), str(row.roundNumber()), str(row.holeNumber()), str(row.toPar())));
        }

    }

    static String str(Object v) {
        return v == null ? "" : String.valueOf(v);
    }
}
