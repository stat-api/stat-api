// Run your first NFL game simulation
// Generated from schema/api/examples/sim-summary.yml — do not edit.
package com.statapi.cookbook;

import com.statapi.*;
import com.statapi.nfl.*;
import java.util.*;

public final class NFLSimSummary {
    public static void main(String[] args) {
        StatApi api = new StatApi(); // reads STAT_API_KEY from the environment

        // Find the free game
        NFLWeeksResponse weeks = api.nfl().weeks(2026);
        long gameId = weeks.freeGameId();
        System.out.println("Season " + weeks.season() + " | free game " + gameId + " | " + weeks.games().size() + " games");

        // Run the game 5,000 times
        NFLSimPublicRunResponse run = api.sim().nfl().run(
            new NFLSimPublicRunRequest(gameId)
                .nDraws(5000)
                .seed(42L)
                .include(new NFLSimInclude()
                    .summary(true)
                    .distributions(false)
                    .scorebox(false)
                    .pbp(false)));
        NFLSimSummarySection summary = run.output().summary();
        if (summary == null) {
            throw new IllegalStateException("the run returned no summary");
        }
        System.out.println(String.format(Locale.ROOT,
            "Simulated total %.2f (SD %.2f) | home win %.1f%% | home cover %.1f%% | over %.1f%%",
            summary.simTotalMean(), summary.simTotalSd(), summary.homeWinProb() * 100,
            summary.homeCoverProb() * 100, summary.overProb() * 100));
        System.out.println("Box-score checks: " + (summary.conservationOk() ? "passed" : "failed"));

        // Rank the players
        List<NFLSimPlayerSummary> players = new ArrayList<>(summary.players());
        players.sort(Comparator.comparingDouble(NFLSimPlayerSummary::recYdMean).reversed());
        System.out.println("Top 5 players by mean receiving yards:");
        for (NFLSimPlayerSummary p : players.subList(0, 5)) {
            System.out.println(String.format(Locale.ROOT,
                "  Player %d (%s/%s) | mean %.1f | median %.1f | 90th percentile %.1f",
                p.playerId(), p.position(), p.depthRole(), p.recYdMean(), p.recYdP50(), p.recYdP90()));
        }

    }
}
