// Meet six typical games — NFL
// Generated from schema/api/examples/sim-scorebox.yml — do not edit.
package com.statapi.cookbook;

import com.statapi.*;
import com.statapi.nfl.*;
import java.util.*;

public final class NFLSimScorebox {
    public static void main(String[] args) {
        StatApi api = new StatApi(); // reads STAT_API_KEY from the environment

        // Ask for the scorebox
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
        NFLSimScoreboxSection scorebox = run.output().scorebox();
        if (scorebox == null) {
            throw new IllegalStateException("the run returned no scorebox");
        }

        // Read the six archetypes
        String away = run.matchup().awayAbbr();
        String home = run.matchup().homeAbbr();
        System.out.println("Six typical games of 2000 draws | scores are Q1-Q2-Q3-Q4-OT-Total | " + away + " at " + home);
        for (NFLSimGameScoreboxDraw game : scorebox.archetypes()) {
            String name = game.archetype() == null ? "draw" : game.archetype();
            String awayScores = String.join("-", game.awayLinescore().stream().map(String::valueOf).toList());
            String homeScores = String.join("-", game.homeLinescore().stream().map(String::valueOf).toList());
            System.out.println(String.format(Locale.ROOT, "  %s | draw %d | %s %s | %s %s | home margin %+d",
                name, game.drawIdx(), away, awayScores, home, homeScores, game.finalMarginHome()));
        }

    }
}
