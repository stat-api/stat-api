// Replay one simulated game — NFL
// Generated from schema/api/examples/sim-trace.yml — do not edit.
package com.statapi.cookbook;

import com.statapi.*;
import com.statapi.nfl.*;
import java.util.*;

public final class NFLSimTrace {
    public static void main(String[] args) {
        StatApi api = new StatApi(); // reads STAT_API_KEY from the environment

        // Pick a draw from a run
        NFLWeeksResponse weeks = api.nfl().weeks(2026);
        long gameId = weeks.freeGameId();
        NFLSimPublicRunResponse run = api.sim().nfl().run(
            new NFLSimPublicRunRequest(gameId)
                .nDraws(2000)
                .seed(42L)
                .include(new NFLSimInclude()
                    .summary(false)
                    .distributions(false)
                    .scorebox(true)
                    .pbp(false)
                    .scoreboxLimit(1)));
        NFLSimGameScoreboxDraw shootout = null;
        if (run.output().scorebox() != null) {
            for (NFLSimGameScoreboxDraw game : run.output().scorebox().archetypes()) {
                if ("shootout_p95".equals(game.archetype())) {
                    shootout = game;
                }
            }
        }
        if (shootout == null) {
            throw new IllegalStateException("the run has no shootout game");
        }
        System.out.println("Replay draw " + shootout.drawIdx() + ", the 95th percentile shootout of 2000 draws");

        // Replay the draw with run
        NFLSimPublicRunResponse replay = api.sim().nfl().run(
            new NFLSimPublicRunRequest(gameId)
                .nDraws(2000)
                .seed(42L)
                .include(new NFLSimInclude()
                    .summary(false)
                    .distributions(false)
                    .scorebox(false)
                    .pbp(true)
                    .pbpDraws(List.of(shootout.drawIdx()))));
        List<NFLSimTracedGame> pbpList = replay.output().pbp();
        if (pbpList == null || pbpList.isEmpty()) {
            throw new IllegalStateException("the run returned no play-by-play trace");
        }
        NFLSimTracedGame traced = pbpList.get(0);
        String away = run.matchup().awayAbbr();
        String home = run.matchup().homeAbbr();
        System.out.println(away + " " + traced.scorebox().awayLinescore().get(5) + " - " + home + " "
            + traced.scorebox().homeLinescore().get(5) + " | " + traced.drives().size() + " drives | "
            + traced.plays().size() + " plays");

        // Read the drives and the plays
        System.out.println("First 5 drives:");
        for (NFLSimDriveSummary d : traced.drives().subList(0, 5)) {
            String team = Boolean.TRUE.equals(d.posteamIsHome()) ? home : away;
            System.out.println("  " + d.driveNumber() + ". " + team + " | Q" + d.startQuarter() + " " + d.startClock()
                + " | " + d.plays() + " plays, " + d.netYards() + " yds | " + d.result() + " | "
                + away + " " + d.awayScoreAfter() + " - " + home + " " + d.homeScoreAfter());
        }
        System.out.println("First 5 plays:");
        for (NFLSimPlayRecord p : traced.plays().subList(0, 5)) {
            System.out.println("  " + p.clockDisplay() + " | " + p.downDistanceDisplay() + " | "
                + p.yardlineDisplay() + " | " + p.description());
        }

    }
}
