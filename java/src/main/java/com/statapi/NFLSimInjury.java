// DO NOT EDIT — generated from schema/api/sim/nfl_sim.yml by generate-sim-sdks.ts
package com.statapi;

/**
 * Pre-game injury report, practice participation, and snap limit. The engine derives its injury multipliers from the injury type, the practice level and the games missed.
 *
 * @param playChance Pre-kickoff probability ({@code 0.0..1.0}) that the player suits up and plays.
 * @param snapLimit The share of his normal snaps that he may play ({@code 1.0} = no limit; {@code 0.60..0.90} when on a snap count).
 * @param injuryType Affected anatomical region ({@code none}, {@code lower_soft_tissue}, {@code joint_sprain}, {@code upper_body}, {@code concussion}, {@code illness_rest}).
 * @param practice Final Friday/pre-game practice participation ({@code full}, {@code limited}, {@code dnp}).
 */
public record NFLSimInjury(
    Double playChance,
    Double snapLimit,
    String injuryType,
    String practice
) implements JsonSerializable {

    public static NFLSimInjury fromJson(JsonValue json) {
        return new NFLSimInjury(
            json.get("play_chance").asDouble(),
            json.get("snap_limit").asDouble(),
            json.get("injury_type").asString(),
            json.get("practice").asString()
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "play_chance", playChance);
        first = JsonOut.field(sb, first, "snap_limit", snapLimit);
        first = JsonOut.field(sb, first, "injury_type", injuryType);
        first = JsonOut.field(sb, first, "practice", practice);
        sb.append('}');
        return sb.toString();
    }
}
