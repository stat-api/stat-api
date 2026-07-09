// Find Kalshi markets for an NBA game
// Generated from schema/api/examples/kalshi-markets-for-game.yml — do not edit.
package com.statapi.cookbook;

import com.statapi.StatApi;
import com.statapi.nba.*;
import java.util.*;

public final class NbaKalshiMarketsForGame {
    public static void main(String[] args) {
        StatApi api = new StatApi(); // reads STAT_API_KEY from the environment

        // Resolve the current season
        List<NBASeason> seasonList = api.nba().seasons().list(new NBASeasonsListParams().limit(200L)).rows();
        NBASeason seasonCurrent = seasonList.stream().max(Comparator.comparingInt(NBASeason::startYear)).orElseThrow();
        long season = seasonCurrent.id();

        // Grab one game
        List<NBAGame> games = api.nba().games().list(new NBAGamesListParams().seasonId((long) (season)).limit(1L)).rows();

        // Join to Kalshi by (league_code, competition_id), then list markets
        var game = games.get(0);
        var events = api.kalshi().events().list(new com.statapi.kalshi.KalshiEventsListParams().competitionId(game.id()).leagueCode("nba")).rows();
        if (events.isEmpty()) {
            System.out.println("no Kalshi event linked to game " + game.id());
        } else {
            var event = events.get(0);
            System.out.println("event: " + event.title());
            var markets = api.kalshi().markets().list(new com.statapi.kalshi.KalshiMarketsListParams().eventId(event.id())).rows();
            System.out.println("ticker\ttitle\tstatus");
            for (var m : markets) {
                System.out.println(m.ticker() + "\t" + m.title() + "\t" + m.status());
            }
        }

    }
}
