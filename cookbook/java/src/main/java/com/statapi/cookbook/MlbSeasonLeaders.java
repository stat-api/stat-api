// Rank MLB season leaders
// Generated from schema/api/examples/season-leaders.yml — do not edit.
package com.statapi.cookbook;

import com.statapi.StatApi;
import com.statapi.mlb.*;
import java.util.*;

public final class MlbSeasonLeaders {
    public static void main(String[] args) {
        StatApi api = new StatApi(); // reads STAT_API_KEY from the environment

        // Resolve the current season
        List<MLBSeason> seasonList = api.mlb().seasons().list(new MLBSeasonsListParams().limit(200L)).rows();
        MLBSeason seasonCurrent = seasonList.stream().max(Comparator.comparingInt(MLBSeason::startYear)).orElseThrow();
        long season = seasonCurrent.id();

        // Page through the whole season
        List<MLBSeasonPlayerStat> rows = new ArrayList<>();
        for (MLBSeasonPlayerStat row : api.mlb().seasonPlayerStats().iterate(new MLBSeasonPlayerStatsListParams().seasonId((long) (season)))) {
            rows.add(row);
        }

        // Rank by home runs
        List<MLBSeasonPlayerStat> ranked = new ArrayList<>(rows);
        ranked.sort(Comparator.comparingInt((MLBSeasonPlayerStat r) -> r.homeRuns()).reversed());

        // Take the top ten
        List<MLBSeasonPlayerStat> leaders = ranked.subList(0, Math.min(10, ranked.size()));

        // Print the leaderboard
        System.out.println("MLB home runs leaders");
        System.out.println(String.join("\t", "player_id", "home_runs"));
        for (MLBSeasonPlayerStat row : leaders) {
            System.out.println(String.join("\t", str(row.playerId()), str(row.homeRuns())));
        }

    }

    static String str(Object v) {
        return v == null ? "" : String.valueOf(v);
    }
}
