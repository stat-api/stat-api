// DO NOT EDIT — generated from schema/api/sim/nba_sim.yml by generate-sim-sdks.ts
package com.statapi;

import java.util.List;

/**
 * Quarter linescore arrays for one NBA scenario.
 *
 * @param away Away points per period ({@code [Q1, Q2, Q3, Q4]} or {@code [Q1..Q4, OT_total]}).
 * @param home Home points per period ({@code [Q1, Q2, Q3, Q4]} or {@code [Q1..Q4, OT_total]}).
 */
public record NBASimScenarioQuarters(
    List<Integer> away,
    List<Integer> home
) implements JsonSerializable {

    public static NBASimScenarioQuarters fromJson(JsonValue json) {
        return new NBASimScenarioQuarters(
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
