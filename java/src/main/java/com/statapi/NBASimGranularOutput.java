// DO NOT EDIT — generated from schema/api/sim/nba_sim.yml by generate-sim-sdks.ts
package com.statapi;

import java.util.List;

/**
 * Granular simulation output inside {@code NBASimPublicRunResponse.output}.
 *
 * @param gameId Simulated NBA game ID string.
 * @param nDraws Number of Monte Carlo draws executed.
 * @param seed 64-bit CRN seed used for the rollout.
 * @param solveElapsedMs Milliseconds spent in the pre-sim CRN Vegas calibration solver.
 * @param simElapsedMs Milliseconds spent executing parallel possession rollouts.
 * @param summary Populated when {@code include.summary = true}.
 * @param distributions Populated when {@code include.distributions = true}.
 * @param scorebox Populated when {@code include.scorebox = true}.
 * @param pbp Populated when {@code include.pbp = true}.
 * @param scenarios Populated when {@code include.scenarios = true}.
 * @param simulations Populated when {@code include.simulations = true}.
 */
public record NBASimGranularOutput(
    String gameId,
    Integer nDraws,
    Long seed,
    Double solveElapsedMs,
    Double simElapsedMs,
    NBASimSummarySection summary,
    NBASimDistributionsSection distributions,
    NBASimScoreboxSection scorebox,
    List<NBASimTracedGame> pbp,
    NBASimScenariosSection scenarios,
    NBASimSimulationsSection simulations
) implements JsonSerializable {

    public static NBASimGranularOutput fromJson(JsonValue json) {
        return new NBASimGranularOutput(
            json.get("game_id").asString(),
            json.get("n_draws").asInt(),
            json.get("seed").asLong(),
            json.get("solve_elapsed_ms").asDouble(),
            json.get("sim_elapsed_ms").asDouble(),
            JsonIn.obj(json.get("summary"), NBASimSummarySection::fromJson),
            JsonIn.obj(json.get("distributions"), NBASimDistributionsSection::fromJson),
            JsonIn.obj(json.get("scorebox"), NBASimScoreboxSection::fromJson),
            JsonIn.list(json.get("pbp"), NBASimTracedGame::fromJson),
            JsonIn.obj(json.get("scenarios"), NBASimScenariosSection::fromJson),
            JsonIn.obj(json.get("simulations"), NBASimSimulationsSection::fromJson)
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "game_id", gameId);
        first = JsonOut.field(sb, first, "n_draws", nDraws);
        first = JsonOut.field(sb, first, "seed", seed);
        first = JsonOut.field(sb, first, "solve_elapsed_ms", solveElapsedMs);
        first = JsonOut.field(sb, first, "sim_elapsed_ms", simElapsedMs);
        first = JsonOut.field(sb, first, "summary", summary);
        first = JsonOut.field(sb, first, "distributions", distributions);
        first = JsonOut.field(sb, first, "scorebox", scorebox);
        first = JsonOut.field(sb, first, "pbp", pbp);
        first = JsonOut.field(sb, first, "scenarios", scenarios);
        first = JsonOut.field(sb, first, "simulations", simulations);
        sb.append('}');
        return sb.toString();
    }
}
