// Auto-paginate every NFL team
// Generated from schema/api/examples/pagination-auto.yml — do not edit.
package com.statapi.cookbook;

import com.statapi.StatApi;
import com.statapi.nfl.*;
import java.util.*;

public final class NflPaginationAuto {
    public static void main(String[] args) {
        StatApi api = new StatApi(); // reads STAT_API_KEY from the environment

        // Walk every page of teams
        List<NFLTeam> teams = new ArrayList<>();
        for (NFLTeam row : api.nfl().teams().iterate()) {
            teams.add(row);
        }

        // Report how many rows the iterator collected
        System.out.println("fetched " + teams.size() + " teams");

    }
}
