// Auto-paginate every NHL team
// Generated from schema/api/examples/pagination-auto.yml — do not edit.
package com.statapi.cookbook;

import com.statapi.StatApi;
import com.statapi.nhl.*;
import java.util.*;

public final class NhlPaginationAuto {
    public static void main(String[] args) {
        StatApi api = new StatApi(); // reads STAT_API_KEY from the environment

        // Walk every page of teams
        List<NHLTeam> teams = new ArrayList<>();
        for (NHLTeam row : api.nhl().teams().iterate()) {
            teams.add(row);
        }

        // Report how many rows the iterator collected
        System.out.println("fetched " + teams.size() + " teams");

    }
}
