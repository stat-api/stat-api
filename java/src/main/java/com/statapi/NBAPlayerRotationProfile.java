// DO NOT EDIT — generated from schema/api/sim/nba_sim.yml by generate-sim-sdks.ts
package com.statapi;

/**
 * Pre-tip player rotation &amp; minutes parameters.
 *
 * @param role Rotation role ({@code starter}, {@code sixth_man}, {@code rotation}, {@code bench}, {@code out}).
 * @param minutes Competitive-script baseline minutes prior.
 * @param minutesCap Minutes restriction ceiling ({@code 0..48}).
 * @param foulsPer36 Expected personal fouls per 36 minutes.
 * @param dnpRate Healthy DNP probability ({@code 0..100}).
 */
public record NBAPlayerRotationProfile(
    String role,
    Double minutes,
    Double minutesCap,
    Double foulsPer36,
    Double dnpRate
) implements JsonSerializable {

    public static NBAPlayerRotationProfile fromJson(JsonValue json) {
        return new NBAPlayerRotationProfile(
            json.get("role").asString(),
            json.get("minutes").asDouble(),
            json.get("minutes_cap").asDouble(),
            json.get("fouls_per_36").asDouble(),
            json.get("dnp_rate").asDouble()
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "role", role);
        first = JsonOut.field(sb, first, "minutes", minutes);
        first = JsonOut.field(sb, first, "minutes_cap", minutesCap);
        first = JsonOut.field(sb, first, "fouls_per_36", foulsPer36);
        first = JsonOut.field(sb, first, "dnp_rate", dnpRate);
        sb.append('}');
        return sb.toString();
    }
}
