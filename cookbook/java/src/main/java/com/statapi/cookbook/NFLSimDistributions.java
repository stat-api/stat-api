// See the whole range of outcomes — NFL
// Generated from schema/api/examples/sim-distributions.yml — do not edit.
package com.statapi.cookbook;

import com.statapi.*;
import com.statapi.nfl.*;
import java.util.*;

public final class NFLSimDistributions {
    public static void main(String[] args) {
        StatApi api = new StatApi(); // reads STAT_API_KEY from the environment

        // Ask for the distributions
        NFLWeeksResponse weeks = api.nfl().weeks(2026);
        long gameId = weeks.freeGameId();
        NFLSimPublicRunResponse run = api.sim().nfl().run(
            new NFLSimPublicRunRequest(gameId)
                .nDraws(5000)
                .seed(42L)
                .include(new NFLSimInclude()
                    .summary(false)
                    .distributions(true)
                    .scorebox(false)
                    .pbp(false)));
        NFLSimDistributionsSection dist = run.output().distributions();
        if (dist == null) {
            throw new IllegalStateException("the run returned no distributions");
        }
        NFLSimBellCurve total = dist.totalPoints();
        System.out.println(String.format(Locale.ROOT,
            "Game total | mean %.2f, SD %.2f | 10th %.2f, median %.2f, 90th %.2f",
            total.mean(), total.sd(), total.p10(), total.p50(), total.p90()));
        if (total.vegasLine() != null && total.pOverVegas() != null) {
            System.out.println(String.format(Locale.ROOT,
                "Market total %.1f | simulated chance of the over %.1f%%",
                total.vegasLine(), total.pOverVegas() * 100));
        }

        // Read the histogram
        System.out.println("Share of draws by game total, bins with 2% or more:");
        for (NFLHistogramBin bin : total.bins()) {
            if (bin.prob() < 0.02) {
                continue;
            }
            System.out.println(String.format(Locale.ROOT, "  [%6.2f, %6.2f) %5.2f%% %s",
                bin.xMin(), bin.xMax(), bin.prob() * 100, "#".repeat((int) (bin.prob() * 100))));
        }

        // Find a draw that you can replay
        String sample = "none";
        for (NFLHistogramBin bin : total.bins()) {
            if (bin.xMin() <= total.p50() && total.p50() < bin.xMax() && bin.sampleDrawIdx() != null) {
                sample = String.valueOf(bin.sampleDrawIdx());
            }
        }
        System.out.println("Median bin: sample draw " + sample);

        // Read the range of a player
        NFLPlayerBellCurves star = dist.players().stream()
            .filter((NFLPlayerBellCurves p) -> p.recYd() != null)
            .max(Comparator.comparingDouble((NFLPlayerBellCurves p) -> p.recYd().mean()))
            .orElseThrow();
        NFLSimBellCurve rec = star.recYd();
        System.out.println(String.format(Locale.ROOT,
            "Top receiver %d (%s) rec yds | 10th %.2f, median %.2f, 90th %.2f | skew %.2f",
            star.playerId(), star.position(), rec.p10(), rec.p50(), rec.p90(), rec.skew()));

    }
}
