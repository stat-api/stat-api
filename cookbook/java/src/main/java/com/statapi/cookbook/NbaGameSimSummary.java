// Run an NBA possession simulation summary
// Generated from schema/api/examples/nba-sim-summary.yml — do not edit.
package com.statapi.cookbook;

import com.statapi.*;
import com.statapi.nba.*;
import java.util.*;

public final class NbaGameSimSummary {
    public static void main(String[] args) {
        StatApi api = new StatApi(); // reads STAT_API_KEY from the environment

        // Discover the free NBA showcase game
        NBASimWeeksResponse weeks = api.nba().weeks(2026);

        // Run the NBA possession simulator
        NBASimPublicRunResponse simRes = api.sim().nba().run(new NBASimPublicRunRequest(weeks.freeGameId()).nDraws(200).seed(42L).include(new NBASimInclude().summary(true).distributions(false).scorebox(false).pbp(false).scenarios(false).simulations(false)));

        // Print calibrated summary and top NBA players
        NBASimSummarySection summary = simRes.output().summary();
        if (summary == null) throw new IllegalStateException("missing output.summary");
        System.out.println("Game " + summary.gameId() + " | season " + summary.season() + " | draws " + summary.nDraws());
        System.out.println(String.format(Locale.US, "Total mean=%.2f sd=%.2f | home_win_pct=%.2f%% away_win_pct=%.2f%%", summary.simTotalMean(), summary.simTotalSd(), summary.homeWinProb() * 100.0, summary.awayWinProb() * 100.0));
        System.out.println(String.format(Locale.US, "Teams away=%s:%.2f home=%s:%.2f", summary.awayTeam().teamCode(), summary.awayTeam().pointsMean(), summary.homeTeam().teamCode(), summary.homeTeam().pointsMean()));
        List<NBASimPlayerSummary> sortedPlayers = new ArrayList<>(summary.players());
        sortedPlayers.sort(Comparator.comparingDouble(NBASimPlayerSummary::dkPtsMean).reversed().thenComparingLong(NBASimPlayerSummary::playerId));
        for (int i = 0; i < Math.min(5, sortedPlayers.size()); i++) {
            NBASimPlayerSummary p = sortedPlayers.get(i);
            System.out.println(String.format(Locale.US, "Player %s (%s) | pts=%.2f reb=%.2f ast=%.2f dk=%.2f", p.name(), p.teamCode(), p.ptsMean(), p.rebMean(), p.astMean(), p.dkPtsMean()));
        }

    }
}
