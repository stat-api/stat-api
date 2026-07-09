// Auto-paginate every MLB team
// Generated from schema/api/examples/pagination-auto.yml — do not edit.
package com.statapi.cookbook;

import com.statapi.StatApi;
import com.statapi.mlb.*;
import java.util.*;

public final class MlbPaginationAuto {
    public static void main(String[] args) {
        StatApi api = new StatApi(); // reads STAT_API_KEY from the environment

        // Walk every page of teams
        List<MLBTeam> teams = new ArrayList<>();
        for (MLBTeam row : api.mlb().teams().iterate()) {
            teams.add(row);
        }

        // Report how many rows the iterator collected
        System.out.println("fetched " + teams.size() + " teams");

    }
}
