// Filter NBA games by season
// Generated from schema/api/examples/filtering-basics.yml — do not edit.
package com.statapi.cookbook;

import com.statapi.StatApi;
import com.statapi.nba.*;
import java.util.*;

public final class NbaFilteringBasics {
    public static void main(String[] args) {
        StatApi api = new StatApi(); // reads STAT_API_KEY from the environment

        // Resolve the current season
        List<NBASeason> seasonList = api.nba().seasons().list(new NBASeasonsListParams().limit(200L)).rows();
        NBASeason seasonCurrent = seasonList.stream().max(Comparator.comparingInt(NBASeason::startYear)).orElseThrow();
        long season = seasonCurrent.id();

        // Filter games to that season
        List<NBAGame> games = api.nba().games().list(new NBAGamesListParams().seasonId((long) (season)).limit(5L)).rows();

        // Print the filtered games
        System.out.println(games);

    }
}
