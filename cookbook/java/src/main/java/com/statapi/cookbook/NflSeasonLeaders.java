// Rank NFL season leaders
// Generated from schema/api/examples/season-leaders.yml — do not edit.
package com.statapi.cookbook;

import com.statapi.StatApi;
import com.statapi.nfl.*;
import java.util.*;

public final class NflSeasonLeaders {
    public static void main(String[] args) {
        StatApi api = new StatApi(); // reads STAT_API_KEY from the environment

        // Resolve the current season
        List<NFLSeason> seasonList = api.nfl().seasons().list(new NFLSeasonsListParams().limit(200L)).rows();
        NFLSeason seasonCurrent = seasonList.stream().max(Comparator.comparingInt(NFLSeason::startYear)).orElseThrow();
        long season = seasonCurrent.id();

        // Page through the whole season
        List<NFLSeasonPlayerStat> rows = new ArrayList<>();
        for (NFLSeasonPlayerStat row : api.nfl().seasonPlayerStats().iterate(new NFLSeasonPlayerStatsListParams().seasonId((long) (season)))) {
            rows.add(row);
        }

        // Rank by passing yards
        List<NFLSeasonPlayerStat> ranked = new ArrayList<>(rows);
        ranked.sort(Comparator.comparingInt((NFLSeasonPlayerStat r) -> r.passingYds()).reversed());

        // Take the top ten
        List<NFLSeasonPlayerStat> leaders = ranked.subList(0, Math.min(10, ranked.size()));

        // Print the leaderboard
        System.out.println("NFL passing yards leaders");
        System.out.println(String.join("\t", "player_id", "passing_yds"));
        for (NFLSeasonPlayerStat row : leaders) {
            System.out.println(String.join("\t", str(row.playerId()), str(row.passingYds())));
        }

    }

    static String str(Object v) {
        return v == null ? "" : String.valueOf(v);
    }
}
