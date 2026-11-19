// DO NOT EDIT — generated from schema/api/sim/nba_sim.yml by generate-sim-sdks.ts
package com.statapi;

/**
 * Pre-tip injury report and availability profile.
 *
 * @param status Official injury report status.
 * @param playChancePct Play probability ({@code 0..100}).
 * @param exitRate In-game exit rate per 36 minutes ({@code %}).
 */
public record NBAPlayerInjuryProfile(
    String status,
    Double playChancePct,
    Double exitRate
) implements JsonSerializable {

    public static NBAPlayerInjuryProfile fromJson(JsonValue json) {
        return new NBAPlayerInjuryProfile(
            json.get("status").asString(),
            json.get("play_chance_pct").asDouble(),
            json.get("exit_rate").asDouble()
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "status", status);
        first = JsonOut.field(sb, first, "play_chance_pct", playChancePct);
        first = JsonOut.field(sb, first, "exit_rate", exitRate);
        sb.append('}');
        return sb.toString();
    }
}
