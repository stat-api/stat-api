// Export NBA Classic DFS lineups as ready-to-upload CSV
// Generated from schema/api/examples/dfs-optimizer-nba-csv.yml — do not edit.
package com.statapi.cookbook;

import com.statapi.*;
import com.statapi.dfs.*;
import java.util.*;

public final class DfsOptimizerNbaCsv {
    public static void main(String[] args) {
        StatApi api = new StatApi(); // reads STAT_API_KEY from the environment

        // Generate NBA Classic lineups in CSV format
        String csvText = api.dfs().slates().optimizerLineupsCsv(1243812L, new DfsLineups.OptimizerLineupsRequest().lineupCount(3).minUniquePlayers(2));

        // Preview exported CSV header and rows
        List<String> csvLines = new ArrayList<>();
        for (String raw : csvText.split("\n")) {
            String trimmed = raw.endsWith("\r") ? raw.substring(0, raw.length() - 1) : raw;
            if (!trimmed.isEmpty()) csvLines.add(trimmed);
        }
        System.out.println("CSV lines=" + csvLines.size());
        for (int i = 0; i < Math.min(4, csvLines.size()); i++) {
            System.out.println(csvLines.get(i));
        }

    }
}
