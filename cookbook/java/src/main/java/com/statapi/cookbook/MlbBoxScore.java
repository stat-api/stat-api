// Build an MLB box score
// Generated from schema/api/examples/box-score.yml — do not edit.
package com.statapi.cookbook;

import com.statapi.StatApi;
import com.statapi.mlb.*;
import java.util.*;

public final class MlbBoxScore {
    public static void main(String[] args) {
        StatApi api = new StatApi(); // reads STAT_API_KEY from the environment

        // Resolve the current season
        List<MLBSeason> seasonList = api.mlb().seasons().list(new MLBSeasonsListParams().limit(200L)).rows();
        MLBSeason seasonCurrent = seasonList.stream().max(Comparator.comparingInt(MLBSeason::startYear)).orElseThrow();
        long season = seasonCurrent.id();

        // Find a game
        List<MLBGame> games = api.mlb().games().list(new MLBGamesListParams().seasonId((long) (season)).limit(1L)).rows();

        // Pull per-player stats for that game
        List<MLBGamePlayerBatterStat> stats = api.mlb().gamePlayerBatterStats().list(new MLBGamePlayerBatterStatsListParams().gameId((long) (games.get(0).id()))).rows();

        // Split the stat lines into the two teams
        Map<Long, List<MLBGamePlayerBatterStat>> by_team = new LinkedHashMap<>();
        for (MLBGamePlayerBatterStat row : stats) {
            by_team.computeIfAbsent(row.teamId(), k -> new ArrayList<>()).add(row);
        }

        // Render both halves of the box score
        System.out.println(by_team);

    }
}
