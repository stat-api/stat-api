// Build the NBA standings
// Generated from schema/api/examples/team-standings.yml — do not edit.
package com.statapi.cookbook;

import com.statapi.StatApi;
import com.statapi.nba.*;
import java.util.*;

public final class NbaTeamStandings {
    public static void main(String[] args) {
        StatApi api = new StatApi(); // reads STAT_API_KEY from the environment

        // Resolve the current season
        List<NBASeason> seasonList = api.nba().seasons().list(new NBASeasonsListParams().limit(200L)).rows();
        NBASeason seasonCurrent = seasonList.stream().max(Comparator.comparingInt(NBASeason::startYear)).orElseThrow();
        long season = seasonCurrent.id();

        // Page every team's season record
        List<NBASeasonTeamStat> rows = new ArrayList<>();
        for (NBASeasonTeamStat row : api.nba().seasonTeamStats().iterate(new NBASeasonTeamStatsListParams().seasonId((long) (season)))) {
            rows.add(row);
        }

        // Order by wins, best first
        List<NBASeasonTeamStat> standings = new ArrayList<>(rows);
        standings.sort(Comparator.comparingInt((NBASeasonTeamStat r) -> r.wins()).reversed());

        // Take the top ten
        List<NBASeasonTeamStat> top = standings.subList(0, Math.min(10, standings.size()));

        // Print the standings
        System.out.println("NBA standings — top 10 by wins");
        System.out.println(String.join("\t", "team_id", "wins", "losses"));
        for (NBASeasonTeamStat row : top) {
            System.out.println(String.join("\t", str(row.teamId()), str(row.wins()), str(row.losses())));
        }

    }

    static String str(Object v) {
        return v == null ? "" : String.valueOf(v);
    }
}
