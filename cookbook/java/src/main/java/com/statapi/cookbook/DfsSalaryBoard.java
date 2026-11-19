// Read a slate's salaries — DFS
// Generated from schema/api/examples/dfs-salary-board.yml — do not edit.
package com.statapi.cookbook;

import com.statapi.StatApi;
import com.statapi.dfs.*;
import java.util.*;

public final class DfsSalaryBoard {
    public static void main(String[] args) {
        StatApi api = new StatApi(); // reads STAT_API_KEY from the environment

        // Page the whole player pool
        List<DFSSlatePlayer> pool = new ArrayList<>();
        for (DFSSlatePlayer row : api.dfs().slatePlayers().iterate(new DFSSlatePlayersListParams().slateId(129036L))) {
            pool.add(row);
        }

        // Rank by salary, most expensive first
        List<DFSSlatePlayer> bysalary = new ArrayList<>(pool);
        bysalary.sort(Comparator.comparingInt((DFSSlatePlayer r) -> r.salary()).reversed());

        // Take the ten priciest players
        List<DFSSlatePlayer> top = bysalary.subList(0, Math.min(10, bysalary.size()));

        // Print the salary board
        System.out.println("Highest-salaried players on the slate");
        System.out.println(String.join("\t", "display_name", "position", "salary", "avg_points"));
        for (DFSSlatePlayer row : top) {
            System.out.println(String.join("\t", str(row.displayName()), str(row.position()), str(row.salary()), str(row.avgPoints())));
        }

    }

    static String str(Object v) {
        return v == null ? "" : String.valueOf(v);
    }
}
