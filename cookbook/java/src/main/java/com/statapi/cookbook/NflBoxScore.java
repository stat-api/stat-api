// Build an NFL box score
// Generated from schema/api/examples/box-score.yml — do not edit.
package com.statapi.cookbook;

import com.statapi.StatApi;
import com.statapi.nfl.*;
import java.util.*;

public final class NflBoxScore {
    public static void main(String[] args) {
        StatApi api = new StatApi(); // reads STAT_API_KEY from the environment

        // Resolve the current season
        List<NFLSeason> seasonList = api.nfl().seasons().list(new NFLSeasonsListParams().limit(200L)).rows();
        NFLSeason seasonCurrent = seasonList.stream().max(Comparator.comparingInt(NFLSeason::startYear)).orElseThrow();
        long season = seasonCurrent.id();

        // Find a game
        List<NFLGame> games = api.nfl().games().list(new NFLGamesListParams().seasonId((long) (season)).limit(1L)).rows();

        // Pull per-player stats for that game
        List<NFLGamePlayerStat> stats = api.nfl().gamePlayerStats().list(new NFLGamePlayerStatsListParams().gameId((long) (games.get(0).id()))).rows();

        // Split the stat lines into the two teams
        Map<Long, List<NFLGamePlayerStat>> by_team = new LinkedHashMap<>();
        for (NFLGamePlayerStat row : stats) {
            by_team.computeIfAbsent(row.teamId(), k -> new ArrayList<>()).add(row);
        }

        // Render both halves of the box score
        System.out.println(by_team);

    }
}
