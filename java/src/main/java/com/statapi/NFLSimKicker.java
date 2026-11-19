// DO NOT EDIT — generated from schema/api/sim/nfl_sim.yml by generate-sim-sdks.ts
package com.statapi;

/**
 * Football-native placekicker field goal make rate, maximum range in yards, and extra point make rate.
 *
 * @param playerId Canonical kicker player ID.
 * @param fgMakeRate Expected field goal make percentage across standard distances (NFL avg {@code 0.855} = 85.5%).
 * @param fgRangeYds Maximum field goal attempt range in neutral weather in yards (NFL avg {@code 53.0} yds).
 * @param xpMakeRate Extra point (PAT) make percentage (NFL avg {@code 0.942} = 94.2%).
 */
public record NFLSimKicker(
    Long playerId,
    Double fgMakeRate,
    Double fgRangeYds,
    Double xpMakeRate
) implements JsonSerializable {

    public static NFLSimKicker fromJson(JsonValue json) {
        return new NFLSimKicker(
            json.get("player_id").asLong(),
            json.get("fg_make_rate").asDouble(),
            json.get("fg_range_yds").asDouble(),
            json.get("xp_make_rate").asDouble()
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "player_id", playerId);
        first = JsonOut.field(sb, first, "fg_make_rate", fgMakeRate);
        first = JsonOut.field(sb, first, "fg_range_yds", fgRangeYds);
        first = JsonOut.field(sb, first, "xp_make_rate", xpMakeRate);
        sb.append('}');
        return sb.toString();
    }
}
