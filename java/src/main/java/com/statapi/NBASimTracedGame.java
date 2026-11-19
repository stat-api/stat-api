// DO NOT EDIT — generated from schema/api/sim/nba_sim.yml by generate-sim-sdks.ts
package com.statapi;

import java.util.List;

/**
 * Complete traced single-game NBA simulation returned in {@code output.pbp[i]} on {@code POST /api/v1/sim/nba/run}.
 *
 * @param drawIdx 0-based traced Monte Carlo draw index.
 * @param archetype Archetype slug when applicable.
 * @param label Human-readable draw label.
 * @param scorebox Full linescore, team box scores, and player box scores.
 * @param possessions Chronological possession-by-possession event tape.
 */
public record NBASimTracedGame(
    Integer drawIdx,
    String archetype,
    String label,
    NBASimGameScoreboxDraw scorebox,
    List<NBAPossessionEvent> possessions
) implements JsonSerializable {

    public static NBASimTracedGame fromJson(JsonValue json) {
        return new NBASimTracedGame(
            json.get("draw_idx").asInt(),
            json.get("archetype").asString(),
            json.get("label").asString(),
            JsonIn.obj(json.get("scorebox"), NBASimGameScoreboxDraw::fromJson),
            JsonIn.list(json.get("possessions"), NBAPossessionEvent::fromJson)
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "draw_idx", drawIdx);
        first = JsonOut.field(sb, first, "archetype", archetype);
        first = JsonOut.field(sb, first, "label", label);
        first = JsonOut.field(sb, first, "scorebox", scorebox);
        first = JsonOut.field(sb, first, "possessions", possessions);
        sb.append('}');
        return sb.toString();
    }
}
