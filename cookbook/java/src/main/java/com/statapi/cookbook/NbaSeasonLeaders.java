// Rank NBA season leaders
// Generated from schema/api/examples/season-leaders.yml — do not edit.
package com.statapi.cookbook;

import com.statapi.StatApi;
import com.statapi.nba.*;
import java.util.*;

public final class NbaSeasonLeaders {
    public static void main(String[] args) {
        StatApi api = new StatApi(); // reads STAT_API_KEY from the environment

        // Resolve the current season
        List<NBASeason> seasonList = api.nba().seasons().list(new NBASeasonsListParams().limit(200L)).rows();
        NBASeason seasonCurrent = seasonList.stream().max(Comparator.comparingInt(NBASeason::startYear)).orElseThrow();
        long season = seasonCurrent.id();

        // Page through the whole season
        List<NBASeasonPlayerStat> rows = new ArrayList<>();
        for (NBASeasonPlayerStat row : api.nba().seasonPlayerStats().iterate(new NBASeasonPlayerStatsListParams().seasonId((long) (season)))) {
            rows.add(row);
        }

        // Rank by points
        List<NBASeasonPlayerStat> ranked = new ArrayList<>(rows);
        ranked.sort(Comparator.comparingInt((NBASeasonPlayerStat r) -> r.pts()).reversed());

        // Take the top ten
        List<NBASeasonPlayerStat> leaders = ranked.subList(0, Math.min(10, ranked.size()));

        // Print the leaderboard
        System.out.println("NBA points leaders");
        System.out.println(String.join("\t", "player_id", "pts"));
        for (NBASeasonPlayerStat row : leaders) {
            System.out.println(String.join("\t", str(row.playerId()), str(row.pts())));
        }

    }

    static String str(Object v) {
        return v == null ? "" : String.valueOf(v);
    }
}
