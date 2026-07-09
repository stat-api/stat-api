// Fetch one NBA team by id
// Generated from schema/api/examples/get-by-id.yml — do not edit.
package com.statapi.cookbook;

import com.statapi.StatApi;
import com.statapi.nba.*;
import java.util.*;

public final class NbaGetById {
    public static void main(String[] args) {
        StatApi api = new StatApi(); // reads STAT_API_KEY from the environment

        // List one team to borrow an id
        List<NBATeam> teams = api.nba().teams().list(new NBATeamsListParams().limit(1L)).rows();

        // Fetch that team by id
        NBATeam team = api.nba().teams().get(teams.get(0).id());

        // Inspect the row
        System.out.println(team);

    }
}
