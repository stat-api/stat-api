// Build an NBA box score
// Generated from schema/api/examples/box-score.yml — do not edit.
package com.statapi.cookbook;

import com.statapi.StatApi;
import com.statapi.nba.*;
import java.util.*;

public final class NbaBoxScore {
    public static void main(String[] args) {
        StatApi api = new StatApi(); // reads STAT_API_KEY from the environment

        // Resolve the current season
        List<NBASeason> seasonList = api.nba().seasons().list(new NBASeasonsListParams().limit(200L)).rows();
        NBASeason seasonCurrent = seasonList.stream().max(Comparator.comparingInt(NBASeason::startYear)).orElseThrow();
        long season = seasonCurrent.id();

        // Find a game
        List<NBAGame> games = api.nba().games().list(new NBAGamesListParams().seasonId((long) (season)).limit(1L)).rows();

        // Pull per-player stats for that game
        List<NBAGamePlayerStat> stats = api.nba().gamePlayerStats().list(new NBAGamePlayerStatsListParams().gameId((long) (games.get(0).id()))).rows();

        // Split the stat lines into the two teams
        Map<Long, List<NBAGamePlayerStat>> by_team = new LinkedHashMap<>();
        for (NBAGamePlayerStat row : stats) {
            by_team.computeIfAbsent(row.teamId(), k -> new ArrayList<>()).add(row);
        }

        // Render both halves of the box score
        System.out.println(by_team);

    }
}
