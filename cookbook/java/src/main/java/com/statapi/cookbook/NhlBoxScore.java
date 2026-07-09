// Build an NHL box score
// Generated from schema/api/examples/box-score.yml — do not edit.
package com.statapi.cookbook;

import com.statapi.StatApi;
import com.statapi.nhl.*;
import java.util.*;

public final class NhlBoxScore {
    public static void main(String[] args) {
        StatApi api = new StatApi(); // reads STAT_API_KEY from the environment

        // Resolve the current season
        List<NHLSeason> seasonList = api.nhl().seasons().list(new NHLSeasonsListParams().limit(200L)).rows();
        NHLSeason seasonCurrent = seasonList.stream().max(Comparator.comparingInt(NHLSeason::startYear)).orElseThrow();
        long season = seasonCurrent.id();

        // Find a game
        List<NHLGame> games = api.nhl().games().list(new NHLGamesListParams().seasonId((long) (season)).limit(1L)).rows();

        // Pull per-player stats for that game
        List<NHLGamePlayerStat> stats = api.nhl().gamePlayerStats().list(new NHLGamePlayerStatsListParams().gameId((long) (games.get(0).id()))).rows();

        // Split the stat lines into the two teams
        Map<Long, List<NHLGamePlayerStat>> by_team = new LinkedHashMap<>();
        for (NHLGamePlayerStat row : stats) {
            by_team.computeIfAbsent(row.teamId(), k -> new ArrayList<>()).add(row);
        }

        // Render both halves of the box score
        System.out.println(by_team);

    }
}
