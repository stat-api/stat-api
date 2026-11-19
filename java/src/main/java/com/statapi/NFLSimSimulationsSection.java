// DO NOT EDIT — generated from schema/api/sim/nfl_sim.yml by generate-sim-sdks.ts
package com.statapi;

import java.util.List;

/**
 * Full-game simulation rollouts ({@code output.simulations}) containing all simulated games ({@code 0..n_simulations}) and optional nested multi-player Play-by-Play ({@code pbp}).
 *
 * @param nSimulations Number of simulated games returned in {@code games}.
 * @param pbpIncluded True when nested {@code pbp} ({@code drives} and {@code plays}) is included on every simulated game.
 * @param games All simulated games ({@code sim_id: 1..=n_simulations}, {@code draw_idx: 0..n_simulations - 1}).
 */
public record NFLSimSimulationsSection(
    Integer nSimulations,
    Boolean pbpIncluded,
    List<NFLSimSimulatedGame> games
) implements JsonSerializable {

    public static NFLSimSimulationsSection fromJson(JsonValue json) {
        return new NFLSimSimulationsSection(
            json.get("n_simulations").asInt(),
            json.get("pbp_included").asBoolean(),
            JsonIn.list(json.get("games"), NFLSimSimulatedGame::fromJson)
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "n_simulations", nSimulations);
        first = JsonOut.field(sb, first, "pbp_included", pbpIncluded);
        first = JsonOut.field(sb, first, "games", games);
        sb.append('}');
        return sb.toString();
    }
}
