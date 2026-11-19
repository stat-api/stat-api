// Ask a what-if question — NFL
// Generated from schema/api/examples/sim-overrides.yml — do not edit.
package com.statapi.cookbook;

import com.statapi.*;
import com.statapi.nfl.*;
import java.util.*;

public final class NFLSimOverrides {
    public static void main(String[] args) {
        StatApi api = new StatApi(); // reads STAT_API_KEY from the environment

        // Run the baseline
        NFLWeeksResponse weeks = api.nfl().weeks(2026);
        long gameId = weeks.freeGameId();
        NFLSimInclude include = new NFLSimInclude()
            .summary(true)
            .distributions(false)
            .scorebox(false)
            .pbp(false);
        NFLSimPublicRunResponse baseline = api.sim().nfl().run(
            new NFLSimPublicRunRequest(gameId).nDraws(5000).seed(42L).include(include));

        // Run the what-if with the same seed
        NFLSimPublicRunResponse scenario = api.sim().nfl().run(
            new NFLSimPublicRunRequest(gameId)
                .nDraws(5000)
                .seed(42L)
                .include(include)
                .homeSpread(-6.5));

        // Read the exact change
        NFLSimSummarySection before = baseline.output().summary();
        NFLSimSummarySection after = scenario.output().summary();
        if (before == null || after == null) {
            throw new IllegalStateException("a run returned no summary");
        }
        String home = baseline.matchup().homeAbbr();
        String away = baseline.matchup().awayAbbr();
        System.out.println("What if " + home + " were a 6.5 point favorite? Seed 42 on both runs.");
        System.out.println(String.format(Locale.ROOT, "Home win chance | %.1f%% -> %.1f%% (%+.1f points)",
            before.homeWinProb() * 100, after.homeWinProb() * 100, (after.homeWinProb() - before.homeWinProb()) * 100));
        System.out.println(String.format(Locale.ROOT, "%s points | %.2f -> %.2f (%+.2f)",
            home, before.homeTeam().pointsMean(), after.homeTeam().pointsMean(),
            after.homeTeam().pointsMean() - before.homeTeam().pointsMean()));
        System.out.println(String.format(Locale.ROOT, "%s points | %.2f -> %.2f (%+.2f)",
            away, before.awayTeam().pointsMean(), after.awayTeam().pointsMean(),
            after.awayTeam().pointsMean() - before.awayTeam().pointsMean()));

    }
}
