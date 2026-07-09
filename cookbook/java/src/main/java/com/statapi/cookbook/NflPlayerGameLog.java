// Read an NFL player's game log
// Generated from schema/api/examples/player-game-log.yml — do not edit.
package com.statapi.cookbook;

import com.statapi.StatApi;
import com.statapi.nfl.*;
import java.util.*;

public final class NflPlayerGameLog {
    public static void main(String[] args) {
        StatApi api = new StatApi(); // reads STAT_API_KEY from the environment

        // Resolve the current season
        List<NFLSeason> seasonList = api.nfl().seasons().list(new NFLSeasonsListParams().limit(200L)).rows();
        NFLSeason seasonCurrent = seasonList.stream().max(Comparator.comparingInt(NFLSeason::startYear)).orElseThrow();
        long season = seasonCurrent.id();

        // Page the season's games
        List<NFLGame> games = new ArrayList<>();
        for (NFLGame row : api.nfl().games().iterate(new NFLGamesListParams().seasonId((long) (season)))) {
            games.add(row);
        }

        // Index games by id
        Map<Long, NFLGame> game_by_id = new LinkedHashMap<>();
        for (NFLGame row : games) {
            game_by_id.put(row.id(), row);
        }

        // Borrow a player from one game's box score
        List<NFLGamePlayerStat> seed = api.nfl().gamePlayerStats().list(new NFLGamePlayerStatsListParams().gameId((long) (games.get(0).id())).limit(1L)).rows();

        // Page that player's game log
        List<NFLGamePlayerStat> gamelog = new ArrayList<>();
        for (NFLGamePlayerStat row : api.nfl().gamePlayerStats().iterate(new NFLGamePlayerStatsListParams().playerId((long) (seed.get(0).playerId())))) {
            gamelog.add(row);
        }

        // Print the game log
        System.out.println("NFL game log (fantasy points)");
        System.out.println(String.join("\t", "game_time", "fantasy_pts"));
        for (NFLGamePlayerStat row : gamelog) {
            System.out.println(String.join("\t", str(game_by_id.get(row.gameId()) != null ? game_by_id.get(row.gameId()).gameTime() : row.gameId()), str(row.fantasyPts())));
        }

    }

    static String str(Object v) {
        return v == null ? "" : String.valueOf(v);
    }
}
