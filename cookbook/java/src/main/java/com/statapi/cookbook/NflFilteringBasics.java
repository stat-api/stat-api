// Filter NFL games by season
// Generated from schema/api/examples/filtering-basics.yml — do not edit.
package com.statapi.cookbook;

import com.statapi.StatApi;
import com.statapi.nfl.*;
import java.util.*;

public final class NflFilteringBasics {
    public static void main(String[] args) {
        StatApi api = new StatApi(); // reads STAT_API_KEY from the environment

        // Resolve the current season
        List<NFLSeason> seasonList = api.nfl().seasons().list(new NFLSeasonsListParams().limit(200L)).rows();
        NFLSeason seasonCurrent = seasonList.stream().max(Comparator.comparingInt(NFLSeason::startYear)).orElseThrow();
        long season = seasonCurrent.id();

        // Filter games to that season
        List<NFLGame> games = api.nfl().games().list(new NFLGamesListParams().seasonId((long) (season)).limit(5L)).rows();

        // Print the filtered games
        System.out.println(games);

    }
}
