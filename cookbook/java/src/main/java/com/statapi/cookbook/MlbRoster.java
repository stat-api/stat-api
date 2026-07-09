// List a MLB team's roster
// Generated from schema/api/examples/roster.yml — do not edit.
package com.statapi.cookbook;

import com.statapi.StatApi;
import com.statapi.mlb.*;
import java.util.*;

public final class MlbRoster {
    public static void main(String[] args) {
        StatApi api = new StatApi(); // reads STAT_API_KEY from the environment

        // Grab one team
        List<MLBTeam> teams = api.mlb().teams().list(new MLBTeamsListParams().limit(1L)).rows();

        // List that team's players
        List<MLBPlayer> roster = api.mlb().players().list(new MLBPlayersListParams().teamId((long) (teams.get(0).id()))).rows();

        // Print the roster
        System.out.println("MLB roster");
        System.out.println(String.join("\t", "full_name", "primary_position", "jersey"));
        for (MLBPlayer row : roster) {
            System.out.println(String.join("\t", str(row.fullName()), str(row.primaryPosition()), str(row.jersey())));
        }

    }

    static String str(Object v) {
        return v == null ? "" : String.valueOf(v);
    }
}
