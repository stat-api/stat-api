// DO NOT EDIT — generated from schema/api/sim/nfl_sim.yml by generate-sim-sdks.ts
package com.statapi;

import java.util.List;

/**
 * Complete traced single-game simulation returned in {@code output.pbp[i]} on {@code POST /api/v1/sim/nfl/run}.
 *
 * @param drawIdx 0-based Monte Carlo draw index traced.
 * @param archetype Archetype slug if this draw corresponds to one of the 6 archetypes.
 * @param label Human-readable draw label.
 * @param scorebox Complete game Scorebox (linescore, team totals, and player box scores) for this draw.
 * @param drives Chronological Drive Chart summarizing every possession in the game.
 * @param plays Chronological snap-by-snap Play-by-Play tape with formatted NFL Gamebook descriptions.
 */
public record NFLSimTracedGame(
    Integer drawIdx,
    String archetype,
    String label,
    NFLSimGameScoreboxDraw scorebox,
    List<NFLSimDriveSummary> drives,
    List<NFLSimPlayRecord> plays
) implements JsonSerializable {

    public static NFLSimTracedGame fromJson(JsonValue json) {
        return new NFLSimTracedGame(
            json.get("draw_idx").asInt(),
            json.get("archetype").asString(),
            json.get("label").asString(),
            JsonIn.obj(json.get("scorebox"), NFLSimGameScoreboxDraw::fromJson),
            JsonIn.list(json.get("drives"), NFLSimDriveSummary::fromJson),
            JsonIn.list(json.get("plays"), NFLSimPlayRecord::fromJson)
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
        first = JsonOut.field(sb, first, "drives", drives);
        first = JsonOut.field(sb, first, "plays", plays);
        sb.append('}');
        return sb.toString();
    }
}
