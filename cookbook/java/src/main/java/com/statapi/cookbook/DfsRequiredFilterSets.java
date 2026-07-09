// Satisfy a required filter set — DFS
// Generated from schema/api/examples/required-filter-sets.yml — do not edit.
package com.statapi.cookbook;

import com.statapi.StatApi;
import com.statapi.dfs.*;
import java.util.*;

public final class DfsRequiredFilterSets {
    public static void main(String[] args) {
        StatApi api = new StatApi(); // reads STAT_API_KEY from the environment

        // See the 400, then satisfy the set
        // dfs.slates requires the [operator_id, date] set — a bare call is a 400.
        try {
            api.dfs().slates().list(new DFSSlatesListParams().limit(5L));
            System.out.println("unexpected: unfiltered slates call succeeded");
        } catch (com.statapi.ValidationException e) {
            System.out.println("rejected (" + e.status() + "): " + e.body());
        }
        // Supply BOTH members of the set and the call is accepted.
        var slates = api.dfs().slates().list(new DFSSlatesListParams().operatorId(1L).date("2026-07-02")).rows();
        System.out.println("operator 1 ran " + slates.size() + " slates on 2026-07-02");

    }
}
