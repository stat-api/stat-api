// Compare NBA Common Random Numbers spread overrides
// Generated from schema/api/examples/nba-sim-overrides.yml — do not edit.
package com.statapi.cookbook;

import com.statapi.*;
import com.statapi.nba.*;
import java.util.*;

public final class NbaGameSimOverrides {
    public static void main(String[] args) {
        StatApi api = new StatApi(); // reads STAT_API_KEY from the environment

        // Run the market baseline simulation (seed 42)
        NBASimPublicRunResponse baseline = api.sim().nba().run(new NBASimPublicRunRequest(6029936L).nDraws(200).seed(42L).include(new NBASimInclude().summary(true).distributions(false).scorebox(false).pbp(false).scenarios(false).simulations(false)));

        // Re-simulate with an alternate home spread (-8.5, seed 42)
        NBASimPublicRunResponse shifted = api.sim().nba().run(new NBASimPublicRunRequest(6029936L).nDraws(200).seed(42L).include(new NBASimInclude().summary(true).distributions(false).scorebox(false).pbp(false).scenarios(false).simulations(false)).homeSpread(-8.5));

        // Print the CRN sensitivity delta
        NBASimSummarySection baseS = baseline.output().summary();
        NBASimSummarySection overS = shifted.output().summary();
        if (baseS == null || overS == null) throw new IllegalStateException("missing summary");
        double dTot = overS.simTotalMean() - baseS.simTotalMean();
        if (Math.abs(dTot) < 0.005) dTot = 0.0;
        double dWin = (overS.homeWinProb() - baseS.homeWinProb()) * 100.0;
        if (Math.abs(dWin) < 0.005) dWin = 0.0;
        System.out.println(String.format(Locale.US, "Baseline total=%.2f home_win_pct=%.2f%% spread_home=%.2f", baseS.simTotalMean(), baseS.homeWinProb() * 100.0, baseS.simSpreadHomeMean()));
        System.out.println(String.format(Locale.US, "Override total=%.2f home_win_pct=%.2f%% spread_home=%.2f", overS.simTotalMean(), overS.homeWinProb() * 100.0, overS.simSpreadHomeMean()));
        System.out.println(String.format(Locale.US, "Delta total=%.2f home_win_pp=%.2f", dTot, dWin));

    }
}
