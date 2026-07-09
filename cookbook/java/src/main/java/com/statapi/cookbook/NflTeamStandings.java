// Build the NFL standings
// Generated from schema/api/examples/team-standings.yml — do not edit.
package com.statapi.cookbook;

import com.statapi.StatApi;
import com.statapi.nfl.*;
import java.util.*;

public final class NflTeamStandings {
    public static void main(String[] args) {
        StatApi api = new StatApi(); // reads STAT_API_KEY from the environment

        // Resolve the current season
        List<NFLSeason> seasonList = api.nfl().seasons().list(new NFLSeasonsListParams().limit(200L)).rows();
        NFLSeason seasonCurrent = seasonList.stream().max(Comparator.comparingInt(NFLSeason::startYear)).orElseThrow();
        long season = seasonCurrent.id();

        // Page every team's season record
        List<NFLSeasonTeamStat> rows = new ArrayList<>();
        for (NFLSeasonTeamStat row : api.nfl().seasonTeamStats().iterate(new NFLSeasonTeamStatsListParams().seasonId((long) (season)))) {
            rows.add(row);
        }

        // Order by wins, best first
        List<NFLSeasonTeamStat> standings = new ArrayList<>(rows);
        standings.sort(Comparator.comparingInt((NFLSeasonTeamStat r) -> r.wins()).reversed());

        // Take the top ten
        List<NFLSeasonTeamStat> top = standings.subList(0, Math.min(10, standings.size()));

        // Print the standings
        System.out.println("NFL standings — top 10 by wins");
        System.out.println(String.join("\t", "team_id", "wins", "losses"));
        for (NFLSeasonTeamStat row : top) {
            System.out.println(String.join("\t", str(row.teamId()), str(row.wins()), str(row.losses())));
        }

    }

    static String str(Object v) {
        return v == null ? "" : String.valueOf(v);
    }
}
