// Read an NBA player's game log
// Generated from schema/api/examples/player-game-log.yml — do not edit.
package com.statapi.cookbook;

import com.statapi.StatApi;
import com.statapi.nba.*;
import java.util.*;

public final class NbaPlayerGameLog {
    public static void main(String[] args) {
        StatApi api = new StatApi(); // reads STAT_API_KEY from the environment

        // Resolve the current season
        List<NBASeason> seasonList = api.nba().seasons().list(new NBASeasonsListParams().limit(200L)).rows();
        NBASeason seasonCurrent = seasonList.stream().max(Comparator.comparingInt(NBASeason::startYear)).orElseThrow();
        long season = seasonCurrent.id();

        // Page the season's games
        List<NBAGame> games = new ArrayList<>();
        for (NBAGame row : api.nba().games().iterate(new NBAGamesListParams().seasonId((long) (season)))) {
            games.add(row);
        }

        // Index games by id
        Map<Long, NBAGame> game_by_id = new LinkedHashMap<>();
        for (NBAGame row : games) {
            game_by_id.put(row.id(), row);
        }

        // Borrow a player from one game's box score
        List<NBAGamePlayerStat> seed = api.nba().gamePlayerStats().list(new NBAGamePlayerStatsListParams().gameId((long) (games.get(0).id())).limit(1L)).rows();

        // Page that player's game log
        List<NBAGamePlayerStat> gamelog = new ArrayList<>();
        for (NBAGamePlayerStat row : api.nba().gamePlayerStats().iterate(new NBAGamePlayerStatsListParams().playerId((long) (seed.get(0).playerId())))) {
            gamelog.add(row);
        }

        // Print the game log
        System.out.println("NBA game log (points)");
        System.out.println(String.join("\t", "game_time", "pts"));
        for (NBAGamePlayerStat row : gamelog) {
            System.out.println(String.join("\t", str(game_by_id.get(row.gameId()) != null ? game_by_id.get(row.gameId()).gameTime() : row.gameId()), str(row.pts())));
        }

    }

    static String str(Object v) {
        return v == null ? "" : String.valueOf(v);
    }
}
