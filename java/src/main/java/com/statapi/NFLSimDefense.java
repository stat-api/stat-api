// DO NOT EDIT — generated from schema/api/sim/nfl_sim.yml by generate-sim-sdks.ts
package com.statapi;

/**
 * Football-native team defense pass rush, run defense, coverage grades, tackling, takeaways, and scheme tendencies.
 *
 * @param pressureRate Pass-rush pressure rate generated per opposing dropback (NFL avg {@code 0.350} = 35.0%).
 * @param sackRate Defensive sack rate per opposing dropback (NFL avg {@code 0.068} = 6.8%).
 * @param stuffRate Share of opposing rushing attempts stuffed at or behind the line of scrimmage (NFL avg {@code 0.190} = 19.0%).
 * @param outsideCbCoverage Outside/perimeter cornerback coverage grade ({@code 0..100} football scale, {@code 75.0} = NFL average).
 * @param slotCbCoverage Nickel/slot cornerback coverage grade ({@code 0..100} football scale, {@code 75.0} = NFL average).
 * @param lbSafetyCoverage Linebacker &amp; safety middle-of-field TE/RB coverage grade ({@code 0..100} football scale, {@code 75.0} = NFL average).
 * @param missedTackleRate Missed tackle rate in space (NFL avg {@code 0.110} = 11.0%; lower = surer tackling &amp; lower YAC allowed).
 * @param interceptionRate Defensive interception rate per opposing pass attempt (NFL avg {@code 0.023} = 2.3%).
 * @param forcedFumbleRate Forced fumble rate per opposing offensive touch or sack (NFL avg {@code 0.012} = 1.2%).
 * @param manCoverageRate Share of defensive coverage snaps played in Man coverage vs. Zone (NFL avg {@code 0.320} = 32.0%).
 * @param blitzRate Share of defensive dropbacks sending 5+ pass rushers (NFL avg {@code 0.250} = 25.0%).
 * @param twoHighRate Share of defensive snaps aligned in Two-High safety shells ({@code Cover 2} / {@code Cover 4} / {@code Cover 6}, NFL avg {@code 0.480} = 48.0%).
 * @param avgBoxCount Average number of defenders aligned in the tackle box (NFL avg {@code 6.4}).
 */
public record NFLSimDefense(
    Double pressureRate,
    Double sackRate,
    Double stuffRate,
    Double outsideCbCoverage,
    Double slotCbCoverage,
    Double lbSafetyCoverage,
    Double missedTackleRate,
    Double interceptionRate,
    Double forcedFumbleRate,
    Double manCoverageRate,
    Double blitzRate,
    Double twoHighRate,
    Double avgBoxCount
) implements JsonSerializable {

    public static NFLSimDefense fromJson(JsonValue json) {
        return new NFLSimDefense(
            json.get("pressure_rate").asDouble(),
            json.get("sack_rate").asDouble(),
            json.get("stuff_rate").asDouble(),
            json.get("outside_cb_coverage").asDouble(),
            json.get("slot_cb_coverage").asDouble(),
            json.get("lb_safety_coverage").asDouble(),
            json.get("missed_tackle_rate").asDouble(),
            json.get("interception_rate").asDouble(),
            json.get("forced_fumble_rate").asDouble(),
            json.get("man_coverage_rate").asDouble(),
            json.get("blitz_rate").asDouble(),
            json.get("two_high_rate").asDouble(),
            json.get("avg_box_count").asDouble()
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "pressure_rate", pressureRate);
        first = JsonOut.field(sb, first, "sack_rate", sackRate);
        first = JsonOut.field(sb, first, "stuff_rate", stuffRate);
        first = JsonOut.field(sb, first, "outside_cb_coverage", outsideCbCoverage);
        first = JsonOut.field(sb, first, "slot_cb_coverage", slotCbCoverage);
        first = JsonOut.field(sb, first, "lb_safety_coverage", lbSafetyCoverage);
        first = JsonOut.field(sb, first, "missed_tackle_rate", missedTackleRate);
        first = JsonOut.field(sb, first, "interception_rate", interceptionRate);
        first = JsonOut.field(sb, first, "forced_fumble_rate", forcedFumbleRate);
        first = JsonOut.field(sb, first, "man_coverage_rate", manCoverageRate);
        first = JsonOut.field(sb, first, "blitz_rate", blitzRate);
        first = JsonOut.field(sb, first, "two_high_rate", twoHighRate);
        first = JsonOut.field(sb, first, "avg_box_count", avgBoxCount);
        sb.append('}');
        return sb.toString();
    }
}
