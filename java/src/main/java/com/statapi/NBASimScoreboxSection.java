// DO NOT EDIT — generated from schema/api/sim/nba_sim.yml by generate-sim-sdks.ts
package com.statapi;

import java.util.List;

/**
 * Scoreboxes for canonical archetypes and sampled draws ({@code output.scorebox}).
 *
 * @param archetypes Canonical game script archetypes.
 * @param draws Sampled draw scoreboxes up to {@code include.scorebox_limit}.
 */
public record NBASimScoreboxSection(
    List<NBASimGameScoreboxDraw> archetypes,
    List<NBASimGameScoreboxDraw> draws
) implements JsonSerializable {

    public static NBASimScoreboxSection fromJson(JsonValue json) {
        return new NBASimScoreboxSection(
            JsonIn.list(json.get("archetypes"), NBASimGameScoreboxDraw::fromJson),
            JsonIn.list(json.get("draws"), NBASimGameScoreboxDraw::fromJson)
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
