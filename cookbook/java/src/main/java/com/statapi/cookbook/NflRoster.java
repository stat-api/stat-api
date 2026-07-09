// List a NFL team's roster
// Generated from schema/api/examples/roster.yml — do not edit.
package com.statapi.cookbook;

import com.statapi.StatApi;
import com.statapi.nfl.*;
import java.util.*;

public final class NflRoster {
    public static void main(String[] args) {
        StatApi api = new StatApi(); // reads STAT_API_KEY from the environment

        // Grab one team
        List<NFLTeam> teams = api.nfl().teams().list(new NFLTeamsListParams().limit(1L)).rows();

        // List that team's players
        List<NFLPlayer> roster = api.nfl().players().list(new NFLPlayersListParams().teamId((long) (teams.get(0).id()))).rows();

        // Print the roster
        System.out.println("NFL roster");
        System.out.println(String.join("\t", "full_name", "primary_position", "jersey"));
        for (NFLPlayer row : roster) {
            System.out.println(String.join("\t", str(row.fullName()), str(row.primaryPosition()), str(row.jersey())));
        }

    }

    static String str(Object v) {
        return v == null ? "" : String.valueOf(v);
    }
}
