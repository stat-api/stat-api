// DO NOT EDIT — generated from schema/api/sim/nfl_sim.yml by generate-sim-sdks.ts
package com.statapi;

/**
 * Football-native RB / QB / WR rushing efficiency, vision before contact, power after contact, and run-length profile.
 *
 * @param yardsPerCarry Expected rushing yards per carry ({@code YPC}, e.g. {@code 4.35}).
 * @param yardsBeforeContact Average rushing yards gained before first defensive contact ({@code YBC/att}, NFL avg {@code 1.60 yds}).
 * @param yardsAfterContact Average rushing yards gained after contact ({@code YACon/att}, NFL avg {@code 2.75 yds}).
 * @param brokenTackleRate Broken tackles forced per rushing attempt (NFL avg {@code 0.075} = 7.5%).
 * @param run15plusRate Share of carries that gain {@code 15+} yards (NFL RB avg {@code 0.055} = 5.5%).
 * @param insideRunRate Share of rushing attempts directed inside the tackles ({@code A}/{@code B} gaps) vs. outside perimeter ({@code C}/{@code D} gaps, {@code 0.15..0.85}).
 * @param fumbleRate Fumbles per offensive touch (NFL avg {@code 0.0085}).
 */
public record NFLSimRushing(
    Double yardsPerCarry,
    Double yardsBeforeContact,
    Double yardsAfterContact,
    Double brokenTackleRate,
    Double run15plusRate,
    Double insideRunRate,
    Double fumbleRate
) implements JsonSerializable {

    public static NFLSimRushing fromJson(JsonValue json) {
        return new NFLSimRushing(
            json.get("yards_per_carry").asDouble(),
            json.get("yards_before_contact").asDouble(),
            json.get("yards_after_contact").asDouble(),
            json.get("broken_tackle_rate").asDouble(),
            json.get("run_15plus_rate").asDouble(),
            json.get("inside_run_rate").asDouble(),
            json.get("fumble_rate").asDouble()
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "yards_per_carry", yardsPerCarry);
        first = JsonOut.field(sb, first, "yards_before_contact", yardsBeforeContact);
        first = JsonOut.field(sb, first, "yards_after_contact", yardsAfterContact);
        first = JsonOut.field(sb, first, "broken_tackle_rate", brokenTackleRate);
        first = JsonOut.field(sb, first, "run_15plus_rate", run15plusRate);
        first = JsonOut.field(sb, first, "inside_run_rate", insideRunRate);
        first = JsonOut.field(sb, first, "fumble_rate", fumbleRate);
        sb.append('}');
        return sb.toString();
    }
}
