// Rank NHL season leaders
// Generated from schema/api/examples/season-leaders.yml — do not edit.
package com.statapi.cookbook;

import com.statapi.StatApi;
import com.statapi.nhl.*;
import java.util.*;

public final class NhlSeasonLeaders {
    public static void main(String[] args) {
        StatApi api = new StatApi(); // reads STAT_API_KEY from the environment

        // Resolve the current season
        List<NHLSeason> seasonList = api.nhl().seasons().list(new NHLSeasonsListParams().limit(200L)).rows();
        NHLSeason seasonCurrent = seasonList.stream().max(Comparator.comparingInt(NHLSeason::startYear)).orElseThrow();
        long season = seasonCurrent.id();

        // Page through the whole season
        List<NHLSeasonPlayerStat> rows = new ArrayList<>();
        for (NHLSeasonPlayerStat row : api.nhl().seasonPlayerStats().iterate(new NHLSeasonPlayerStatsListParams().seasonId((long) (season)))) {
            rows.add(row);
        }

        // Rank by goals
        List<NHLSeasonPlayerStat> ranked = new ArrayList<>(rows);
        ranked.sort(Comparator.comparingInt((NHLSeasonPlayerStat r) -> r.goals()).reversed());

        // Take the top ten
        List<NHLSeasonPlayerStat> leaders = ranked.subList(0, Math.min(10, ranked.size()));

        // Print the leaderboard
        System.out.println("NHL goals leaders");
        System.out.println(String.join("\t", "player_id", "goals"));
        for (NHLSeasonPlayerStat row : leaders) {
            System.out.println(String.join("\t", str(row.playerId()), str(row.goals())));
        }

    }

    static String str(Object v) {
        return v == null ? "" : String.valueOf(v);
    }
}
