// DO NOT EDIT — generated from schema/api/sim/nfl_sim.yml by generate-sim-sdks.ts
package com.statapi;

import java.util.List;

/**
 * Nested Play-by-Play ({@code output.simulations.games[i].pbp}) containing drive summaries ({@code drives}) and snap-by-snap plays ({@code plays}) with multi-player {@code play_stats}.
 *
 * @param drives Chronological offensive drives in the simulated game.
 * @param plays Chronological snap-by-snap plays in the simulated game.
 */
public record NFLSimGamePbp(
    List<NFLSimGameDrive> drives,
    List<NFLSimPlayByPlay> plays
) implements JsonSerializable {

    public static NFLSimGamePbp fromJson(JsonValue json) {
        return new NFLSimGamePbp(
            JsonIn.list(json.get("drives"), NFLSimGameDrive::fromJson),
            JsonIn.list(json.get("plays"), NFLSimPlayByPlay::fromJson)
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "drives", drives);
        first = JsonOut.field(sb, first, "plays", plays);
        sb.append('}');
        return sb.toString();
    }
}
