// Rank a DFS slate by salary and value
// Generated from schema/api/examples/slate-to-salaries.yml — do not edit.
package com.statapi.cookbook;

import com.statapi.StatApi;
import com.statapi.dfs.*;
import java.util.*;

public final class DfsSlateToSalaries {
    public static void main(String[] args) {
        StatApi api = new StatApi(); // reads STAT_API_KEY from the environment

        // Page the slate's players
        List<DFSSlatePlayer> players = new ArrayList<>();
        for (DFSSlatePlayer row : api.dfs().slatePlayers().iterate(new DFSSlatePlayersListParams().slateId(91396L))) {
            players.add(row);
        }

        // Rank by salary, highest first
        List<DFSSlatePlayer> bysalary = new ArrayList<>(players);
        bysalary.sort(Comparator.comparingInt((DFSSlatePlayer r) -> r.salary()).reversed());

        // Take the ten priciest players
        List<DFSSlatePlayer> top = bysalary.subList(0, Math.min(10, bysalary.size()));

        // Fetch the top player's projection
        List<DFSSlatePlayerProjection> proj = api.dfs().slatePlayerProjections().list(new DFSSlatePlayerProjectionsListParams().slatePlayerId((long) (top.get(0).id()))).rows();

        // Compute projected points per $1000 of salary
        if (!proj.isEmpty() && !top.isEmpty()) {
            double value = (proj.get(0).projection() / (double) top.get(0).salary()) * 1000;
            System.out.printf("value of highest-salaried player = %.2f projected pts per $1000%n", value);
        }

        // Print the salary board
        System.out.println("Highest-salaried players on the slate");
        System.out.println(String.join("\t", "display_name", "position", "salary"));
        for (DFSSlatePlayer row : top) {
            System.out.println(String.join("\t", str(row.displayName()), str(row.position()), str(row.salary())));
        }

    }

    static String str(Object v) {
        return v == null ? "" : String.valueOf(v);
    }
}
