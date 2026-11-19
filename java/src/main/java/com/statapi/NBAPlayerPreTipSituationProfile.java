// DO NOT EDIT — generated from schema/api/sim/nba_sim.yml by generate-sim-sdks.ts
package com.statapi;

/**
 * Recent workload trend context.
 *
 * @param last10Minutes Trailing 10-game average minutes.
 */
public record NBAPlayerPreTipSituationProfile(
    Double last10Minutes
) implements JsonSerializable {

    public static NBAPlayerPreTipSituationProfile fromJson(JsonValue json) {
        return new NBAPlayerPreTipSituationProfile(
            json.get("last10_minutes").asDouble()
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "last10_minutes", last10Minutes);
        sb.append('}');
        return sb.toString();
    }
}
