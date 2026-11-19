// DO NOT EDIT — generated from schema/api/sim/nfl_sim.yml by generate-sim-sdks.ts
package com.statapi;

/**
 * Football scouting ratings ({@code 0..99} scale where {@code 75.0} = positional NFL average).
 *
 * @param speedRating Straight-line long speed &amp; breakaway gear ({@code 0..99}, {@code 75.0} = positional average).
 * @param elusivenessRating Short-area juke, cutback &amp; lateral agility ({@code 0..99}, {@code 75.0} = positional average).
 * @param contactRating Strength and balance through contact, power through arm tackles ({@code 0..99}, {@code 75.0} = positional average).
 */
public record NFLSimAthleticism(
    Double speedRating,
    Double elusivenessRating,
    Double contactRating
) implements JsonSerializable {

    public static NFLSimAthleticism fromJson(JsonValue json) {
        return new NFLSimAthleticism(
            json.get("speed_rating").asDouble(),
            json.get("elusiveness_rating").asDouble(),
            json.get("contact_rating").asDouble()
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "speed_rating", speedRating);
        first = JsonOut.field(sb, first, "elusiveness_rating", elusivenessRating);
        first = JsonOut.field(sb, first, "contact_rating", contactRating);
        sb.append('}');
        return sb.toString();
    }
}
