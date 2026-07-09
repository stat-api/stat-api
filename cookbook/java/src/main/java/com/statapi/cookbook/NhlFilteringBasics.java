// Filter NHL games by season
// Generated from schema/api/examples/filtering-basics.yml — do not edit.
package com.statapi.cookbook;

import com.statapi.StatApi;
import com.statapi.nhl.*;
import java.util.*;

public final class NhlFilteringBasics {
    public static void main(String[] args) {
        StatApi api = new StatApi(); // reads STAT_API_KEY from the environment

        // Resolve the current season
        List<NHLSeason> seasonList = api.nhl().seasons().list(new NHLSeasonsListParams().limit(200L)).rows();
        NHLSeason seasonCurrent = seasonList.stream().max(Comparator.comparingInt(NHLSeason::startYear)).orElseThrow();
        long season = seasonCurrent.id();

        // Filter games to that season
        List<NHLGame> games = api.nhl().games().list(new NHLGamesListParams().seasonId((long) (season)).limit(5L)).rows();

        // Print the filtered games
        System.out.println(games);

    }
}
