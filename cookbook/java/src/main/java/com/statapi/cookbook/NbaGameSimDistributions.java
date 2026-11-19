// Extract NBA empirical bell curves and quantiles
// Generated from schema/api/examples/nba-sim-distributions.yml — do not edit.
package com.statapi.cookbook;

import com.statapi.*;
import com.statapi.nba.*;
import java.util.*;

public final class NbaGameSimDistributions {
    public static void main(String[] args) {
        StatApi api = new StatApi(); // reads STAT_API_KEY from the environment

        // Run NBA SIM with empirical distributions enabled
        NBASimPublicRunResponse distRes = api.sim().nba().run(new NBASimPublicRunRequest(6029936L).nDraws(200).seed(42L).include(new NBASimInclude().summary(true).distributions(true).scorebox(false).pbp(false).scenarios(false).simulations(false)));

        // Print game and player quantile distributions
        NBASimDistributionsSection dist = distRes.output().distributions();
        NBASimSummarySection sumD = distRes.output().summary();
        if (dist == null || sumD == null) throw new IllegalStateException("missing distributions or summary");
        NBASimBellCurve tp = dist.totalPoints();
        NBASimBellCurve sm = dist.spreadMarginHome();
        System.out.println(String.format(Locale.US, "Game %s | total mean=%.2f p10=%.2f p50=%.2f p90=%.2f", sumD.gameId(), tp.mean(), tp.p10(), tp.p50(), tp.p90()));
        System.out.println(String.format(Locale.US, "SpreadHome mean=%.2f p10=%.2f p50=%.2f p90=%.2f", sm.mean(), sm.p10(), sm.p50(), sm.p90()));
        System.out.println(String.format(Locale.US, "Teams %s=%.2f %s=%.2f", sumD.awayTeam().teamCode(), dist.awayPoints().mean(), sumD.homeTeam().teamCode(), dist.homePoints().mean()));
        List<NBAPlayerBellCurves> sortedCurves = new ArrayList<>(dist.players());
        sortedCurves.sort(Comparator.comparingDouble((NBAPlayerBellCurves p) -> p.dkPts().mean()).reversed().thenComparingLong(NBAPlayerBellCurves::playerId));
        for (int i = 0; i < Math.min(5, sortedCurves.size()); i++) {
            NBAPlayerBellCurves p = sortedCurves.get(i);
            System.out.println(String.format(Locale.US, "Dist %s (%s) | dk_p50=%.2f dk_p90=%.2f pts_p50=%.2f", p.name(), p.teamCode(), p.dkPts().p50(), p.dkPts().p90(), p.points().p50()));
        }

    }
}
