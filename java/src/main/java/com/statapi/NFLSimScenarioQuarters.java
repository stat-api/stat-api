// DO NOT EDIT — generated from schema/api/sim/nfl_sim.yml by generate-sim-sdks.ts
package com.statapi;

import java.util.List;

/**
 * Quarter-by-quarter linescore arrays ({@code [Q1, Q2, Q3, Q4]} or {@code [Q1, Q2, Q3, Q4, OT]}).
 *
 * @param away Away team points by quarter.
 * @param home Home team points by quarter.
 */
public record NFLSimScenarioQuarters(
    List<Integer> away,
    List<Integer> home
) implements JsonSerializable {

    public static NFLSimScenarioQuarters fromJson(JsonValue json) {
        return new NFLSimScenarioQuarters(
            JsonIn.list(json.get("away"), JsonValue::asInt),
            JsonIn.list(json.get("home"), JsonValue::asInt)
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "away", away);
        first = JsonOut.field(sb, first, "home", home);
        sb.append('}');
        return sb.toString();
    }
}
