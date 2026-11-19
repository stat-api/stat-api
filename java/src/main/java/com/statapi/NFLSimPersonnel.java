// DO NOT EDIT — generated from schema/api/sim/nfl_sim.yml by generate-sim-sdks.ts
package com.statapi;

/**
 * Offensive personnel shares of snaps. The engine rescales the seven shares so that they sum to 1. The first digit of a group is the RBs, the second is the TEs, and WRs fill the rest.
 *
 * @param p11 {@code 11} personnel (1 RB, 1 TE, 3 WR) snap share. NFL average {@code 0.61}.
 * @param p12 {@code 12} personnel (1 RB, 2 TE, 2 WR) snap share. NFL average {@code 0.19}.
 * @param p21 {@code 21} personnel (2 RB, 1 TE, 2 WR) snap share. NFL average {@code 0.08}.
 * @param p13 {@code 13} personnel (1 RB, 3 TE, 1 WR) snap share. NFL average {@code 0.04}.
 * @param p22 {@code 22} personnel (2 RB, 2 TE, 1 WR) snap share. NFL average {@code 0.03}.
 * @param noRb No running back on the field (1 TE, 4 WR) snap share. NFL average {@code 0.03}.
 * @param goalLine The goal-line package (2 RB, 2 TE, 1 WR, against a heavy defense) snap share. NFL average {@code 0.02}.
 */
public record NFLSimPersonnel(
    Double p11,
    Double p12,
    Double p21,
    Double p13,
    Double p22,
    Double noRb,
    Double goalLine
) implements JsonSerializable {

    public static NFLSimPersonnel fromJson(JsonValue json) {
        return new NFLSimPersonnel(
            json.get("p11").asDouble(),
            json.get("p12").asDouble(),
            json.get("p21").asDouble(),
            json.get("p13").asDouble(),
            json.get("p22").asDouble(),
            json.get("no_rb").asDouble(),
            json.get("goal_line").asDouble()
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "p11", p11);
        first = JsonOut.field(sb, first, "p12", p12);
        first = JsonOut.field(sb, first, "p21", p21);
        first = JsonOut.field(sb, first, "p13", p13);
        first = JsonOut.field(sb, first, "p22", p22);
        first = JsonOut.field(sb, first, "no_rb", noRb);
        first = JsonOut.field(sb, first, "goal_line", goalLine);
        sb.append('}');
        return sb.toString();
    }
}
