// Build the MLB standings
// Generated from schema/api/examples/team-standings.yml — do not edit.
package com.statapi.cookbook;

import com.statapi.StatApi;
import com.statapi.mlb.*;
import java.util.*;

public final class MlbTeamStandings {
    public static void main(String[] args) {
        StatApi api = new StatApi(); // reads STAT_API_KEY from the environment

        // Resolve the current season
        List<MLBSeason> seasonList = api.mlb().seasons().list(new MLBSeasonsListParams().limit(200L)).rows();
        MLBSeason seasonCurrent = seasonList.stream().max(Comparator.comparingInt(MLBSeason::startYear)).orElseThrow();
        long season = seasonCurrent.id();

        // Page every team's season record
        List<MLBSeasonTeamStat> rows = new ArrayList<>();
        for (MLBSeasonTeamStat row : api.mlb().seasonTeamStats().iterate(new MLBSeasonTeamStatsListParams().seasonId((long) (season)))) {
            rows.add(row);
        }

        // Order by wins, best first
        List<MLBSeasonTeamStat> standings = new ArrayList<>(rows);
        standings.sort(Comparator.comparingInt((MLBSeasonTeamStat r) -> r.wins()).reversed());

        // Take the top ten
        List<MLBSeasonTeamStat> top = standings.subList(0, Math.min(10, standings.size()));

        // Print the standings
        System.out.println("MLB standings — top 10 by wins");
        System.out.println(String.join("\t", "team_id", "wins", "losses"));
        for (MLBSeasonTeamStat row : top) {
            System.out.println(String.join("\t", str(row.teamId()), str(row.wins()), str(row.losses())));
        }

    }

    static String str(Object v) {
        return v == null ? "" : String.valueOf(v);
    }
}
