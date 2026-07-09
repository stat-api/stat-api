// Filter MLB games by season
// Generated from schema/api/examples/filtering-basics.yml — do not edit.
package com.statapi.cookbook;

import com.statapi.StatApi;
import com.statapi.mlb.*;
import java.util.*;

public final class MlbFilteringBasics {
    public static void main(String[] args) {
        StatApi api = new StatApi(); // reads STAT_API_KEY from the environment

        // Resolve the current season
        List<MLBSeason> seasonList = api.mlb().seasons().list(new MLBSeasonsListParams().limit(200L)).rows();
        MLBSeason seasonCurrent = seasonList.stream().max(Comparator.comparingInt(MLBSeason::startYear)).orElseThrow();
        long season = seasonCurrent.id();

        // Filter games to that season
        List<MLBGame> games = api.mlb().games().list(new MLBGamesListParams().seasonId((long) (season)).limit(5L)).rows();

        // Print the filtered games
        System.out.println(games);

    }
}
