// Find the main slate for a day — DFS
// Generated from schema/api/examples/dfs-main-slate.yml — do not edit.
package com.statapi.cookbook;

import com.statapi.StatApi;
import com.statapi.dfs.*;
import java.util.*;

public final class DfsMainSlate {
    public static void main(String[] args) {
        StatApi api = new StatApi(); // reads STAT_API_KEY from the environment

        // List one operator's slates for one day
        List<DFSSlate> days = api.dfs().slates().list(new DFSSlatesListParams().operatorId(1L).date("2025-11-30").league("nfl")).rows();

        // Keep the main slate
        List<DFSSlate> mains = new ArrayList<>();
        for (DFSSlate r : days) {
            if (java.util.Objects.equals(r.main(), true)) {
                mains.add(r);
            }
        }

        // Read the slate id
        System.out.println("Main slate");
        System.out.println(String.join("\t", "id", "name", "date", "start_time"));
        for (DFSSlate row : mains) {
            System.out.println(String.join("\t", str(row.id()), str(row.name()), str(row.date()), str(row.startTime())));
        }

    }

    static String str(Object v) {
        return v == null ? "" : String.valueOf(v);
    }
}
