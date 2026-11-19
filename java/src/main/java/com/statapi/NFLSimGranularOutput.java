// DO NOT EDIT — generated from schema/api/sim/nfl_sim.yml by generate-sim-sdks.ts
package com.statapi;

import java.util.List;

/**
 * The sections of a run ({@code summary}, {@code distributions}, {@code scorebox}, {@code pbp}) that you chose in {@code include}, inside NFLSimPublicRunResponse.output.
 *
 * @param gameId Simulated NFL game ID.
 * @param nDraws Number of Monte Carlo draws executed.
 * @param seed 64-bit CRN seed used for the rollout.
 * @param solveElapsedMs Wall-clock milliseconds spent in the Pre-Sim CRN Vegas Calibration Solver.
 * @param simElapsedMs Wall-clock milliseconds spent executing the parallel play-by-play rollout.
 * @param summary Populated when {@code include.summary = true}.
 * @param distributions Populated when {@code include.distributions = true}.
 * @param scorebox Populated when {@code include.scorebox = true}.
 * @param pbp Populated when {@code include.pbp = true}.
 * @param scenarios Populated when {@code include.scenarios = true} or {@code mode = "scenarios"}.
 * @param simulations Populated when {@code include.simulations = true} or {@code simulations = true}.
 */
public record NFLSimGranularOutput(
    Long gameId,
    Integer nDraws,
    Long seed,
    Double solveElapsedMs,
    Double simElapsedMs,
    NFLSimSummarySection summary,
    NFLSimDistributionsSection distributions,
    NFLSimScoreboxSection scorebox,
    List<NFLSimTracedGame> pbp,
    NFLSimScenariosSection scenarios,
    NFLSimSimulationsSection simulations
) implements JsonSerializable {

    public static NFLSimGranularOutput fromJson(JsonValue json) {
        return new NFLSimGranularOutput(
            json.get("game_id").asLong(),
            json.get("n_draws").asInt(),
            json.get("seed").asLong(),
            json.get("solve_elapsed_ms").asDouble(),
            json.get("sim_elapsed_ms").asDouble(),
            JsonIn.obj(json.get("summary"), NFLSimSummarySection::fromJson),
            JsonIn.obj(json.get("distributions"), NFLSimDistributionsSection::fromJson),
            JsonIn.obj(json.get("scorebox"), NFLSimScoreboxSection::fromJson),
            JsonIn.list(json.get("pbp"), NFLSimTracedGame::fromJson),
            JsonIn.obj(json.get("scenarios"), NFLSimScenariosSection::fromJson),
            JsonIn.obj(json.get("simulations"), NFLSimSimulationsSection::fromJson)
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
