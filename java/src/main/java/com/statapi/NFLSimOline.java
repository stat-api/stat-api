// DO NOT EDIT — generated from schema/api/sim/nfl_sim.yml by generate-sim-sdks.ts
package com.statapi;

/**
 * Offensive line pass-protection grade, run-blocking grade, and sack rate allowed.
 *
 * @param passBlockGrade Pass-protection unit grade ({@code 0..100} football scale where {@code 75.0} = NFL average).
 * @param runBlockGrade Run-blocking trench grade ({@code 0..100} football scale where {@code 75.0} = NFL average).
 * @param sackRateAllowed Expected sacks allowed per dropback in neutral protection ({@code 0.035..0.115}, NFL avg {@code 0.064}).
 */
public record NFLSimOline(
    Double passBlockGrade,
    Double runBlockGrade,
    Double sackRateAllowed
) implements JsonSerializable {

    public static NFLSimOline fromJson(JsonValue json) {
        return new NFLSimOline(
            json.get("pass_block_grade").asDouble(),
            json.get("run_block_grade").asDouble(),
            json.get("sack_rate_allowed").asDouble()
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "pass_block_grade", passBlockGrade);
        first = JsonOut.field(sb, first, "run_block_grade", runBlockGrade);
        first = JsonOut.field(sb, first, "sack_rate_allowed", sackRateAllowed);
        sb.append('}');
        return sb.toString();
    }
}
