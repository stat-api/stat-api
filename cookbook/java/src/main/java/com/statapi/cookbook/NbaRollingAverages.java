// Rolling averages over an NBA game log
// Generated from schema/api/examples/rolling-averages.yml — do not edit.
package com.statapi.cookbook;

import com.statapi.StatApi;
import com.statapi.nba.*;
import java.util.*;

public final class NbaRollingAverages {
    public static void main(String[] args) {
        StatApi api = new StatApi(); // reads STAT_API_KEY from the environment

        // Resolve the current season
        List<NBASeason> seasonList = api.nba().seasons().list(new NBASeasonsListParams().limit(200L)).rows();
        NBASeason seasonCurrent = seasonList.stream().max(Comparator.comparingInt(NBASeason::startYear)).orElseThrow();
        long season = seasonCurrent.id();

        // Grab one game
        List<NBAGame> games = api.nba().games().list(new NBAGamesListParams().seasonId((long) (season)).limit(1L)).rows();

        // Borrow a player from that game's box score
        List<NBAGamePlayerStat> seed = api.nba().gamePlayerStats().list(new NBAGamePlayerStatsListParams().gameId((long) (games.get(0).id())).limit(1L)).rows();

        // Page that player's full game log
        List<NBAGamePlayerStat> gamelog = new ArrayList<>();
        for (NBAGamePlayerStat row : api.nba().gamePlayerStats().iterate(new NBAGamePlayerStatsListParams().playerId((long) (seed.get(0).playerId())))) {
            gamelog.add(row);
        }

        // Oldest game first
        List<NBAGamePlayerStat> chron = new ArrayList<>(gamelog);
        chron.sort(Comparator.comparingInt((NBAGamePlayerStat r) -> r.gameDate()));

        // Compute a 5-game trailing average of points
        int window = 5;
        for (int i = 0; i < chron.size(); i++) {
            int start = Math.max(0, i - window + 1);
            var span = chron.subList(start, i + 1);
            double avg = span.stream().mapToInt(NBAGamePlayerStat::pts).average().orElse(0.0);
            System.out.printf("game %d: pts=%d, %d-game avg=%.1f%n", i + 1, chron.get(i).pts(), window, avg);
        }

    }
}
