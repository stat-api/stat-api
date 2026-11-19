// DO NOT EDIT — generated from schema/api/sim/nfl_sim.yml by generate-sim-sdks.ts
package com.statapi;

import java.util.List;

/**
 * The {@code scorebox} section ({@code output.scorebox}): 6 canonical game archetypes plus individual draw box scores.
 *
 * @param archetypes 6 representative game draws: {@code median}, {@code shootout}, {@code defensive_slugfest}, {@code home_blowout}, {@code away_blowout}, and {@code overtime_thriller}.
 * @param draws First {@code scorebox_limit} individual game draw scoreboxes ({@code draw_idx = 0..scorebox_limit-1}).
 */
public record NFLSimScoreboxSection(
    List<NFLSimGameScoreboxDraw> archetypes,
    List<NFLSimGameScoreboxDraw> draws
) implements JsonSerializable {

    public static NFLSimScoreboxSection fromJson(JsonValue json) {
        return new NFLSimScoreboxSection(
            JsonIn.list(json.get("archetypes"), NFLSimGameScoreboxDraw::fromJson),
            JsonIn.list(json.get("draws"), NFLSimGameScoreboxDraw::fromJson)
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "archetypes", archetypes);
        first = JsonOut.field(sb, first, "draws", draws);
        sb.append('}');
        return sb.toString();
    }
}
