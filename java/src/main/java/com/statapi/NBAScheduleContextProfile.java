// DO NOT EDIT — generated from schema/api/sim/nba_sim.yml by generate-sim-sdks.ts
package com.statapi;

/**
 * Pre-tip rest and schedule context inside {@code output.scenarios.teams[id].schedule}.
 *
 * @param restDays Days of rest prior to game ({@code 0} = back-to-back).
 */
public record NBAScheduleContextProfile(
    Integer restDays
) implements JsonSerializable {

    public static NBAScheduleContextProfile fromJson(JsonValue json) {
        return new NBAScheduleContextProfile(
            json.get("rest_days").asInt()
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "rest_days", restDays);
        sb.append('}');
        return sb.toString();
    }
}
