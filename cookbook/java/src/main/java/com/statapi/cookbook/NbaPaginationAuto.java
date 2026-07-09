// Auto-paginate every NBA team
// Generated from schema/api/examples/pagination-auto.yml — do not edit.
package com.statapi.cookbook;

import com.statapi.StatApi;
import com.statapi.nba.*;
import java.util.*;

public final class NbaPaginationAuto {
    public static void main(String[] args) {
        StatApi api = new StatApi(); // reads STAT_API_KEY from the environment

        // Walk every page of teams
        List<NBATeam> teams = new ArrayList<>();
        for (NBATeam row : api.nba().teams().iterate()) {
            teams.add(row);
        }

        // Report how many rows the iterator collected
        System.out.println("fetched " + teams.size() + " teams");

    }
}
