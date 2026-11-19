// DO NOT EDIT — generated from schema/api/sim/nfl_sim.yml by generate-sim-sdks.ts
package com.statapi;

/**
 * Football-native WR / TE / RB route-running, target depth, separation, contested catch, and Yards After Catch profile.
 *
 * @param catchRate Expected catch rate (receptions per target, e.g. {@code 0.66}).
 * @param yardsPerTarget Expected receiving yards per target (e.g. {@code 8.6}).
 * @param adot Average depth of target (aDOT) in air yards downfield (e.g. {@code 11.2 yds} for perimeter WR, {@code 7.1 yds} for TE, {@code 0.8 yds} for RB).
 * @param separationYds Average yards of separation from the nearest defender at target arrival (NFL avg {@code 2.90 yds}).
 * @param contestedCatchRate Catch win rate on contested targets in tight coverage (NFL avg {@code 0.460} = 46.0%).
 * @param dropRate Drop rate per catchable target (NFL avg {@code 0.055} = 5.5%).
 * @param yacPerCatch Overall average Yards After Catch per reception ({@code YAC/rec}, e.g. {@code 4.5 yds}).
 * @param zeroYacRate Share of catches that end at the catch point with 0 YAC (e.g. {@code 0.39}).
 * @param brokenTackleRate Broken tackles forced per reception (e.g. {@code 0.060} for WR, {@code 0.110} for RB).
 * @param manTargetFactor Target share multiplier when the defense plays Man coverage ({@code 1.10} = man-beater alpha WR).
 * @param zoneTargetFactor Target share multiplier when the defense plays Zone coverage ({@code 1.06} = zone-settling slot/TE).
 * @param screenTargetRate Share of receiving targets that are designed screens or quick flat concepts behind the line of scrimmage ({@code 0.01..0.55}).
 */
public record NFLSimReceiving(
    Double catchRate,
    Double yardsPerTarget,
    Double adot,
    Double separationYds,
    Double contestedCatchRate,
    Double dropRate,
    Double yacPerCatch,
    Double zeroYacRate,
    Double brokenTackleRate,
    Double manTargetFactor,
    Double zoneTargetFactor,
    Double screenTargetRate
) implements JsonSerializable {

    public static NFLSimReceiving fromJson(JsonValue json) {
        return new NFLSimReceiving(
            json.get("catch_rate").asDouble(),
            json.get("yards_per_target").asDouble(),
            json.get("adot").asDouble(),
            json.get("separation_yds").asDouble(),
            json.get("contested_catch_rate").asDouble(),
            json.get("drop_rate").asDouble(),
            json.get("yac_per_catch").asDouble(),
            json.get("zero_yac_rate").asDouble(),
            json.get("broken_tackle_rate").asDouble(),
            json.get("man_target_factor").asDouble(),
            json.get("zone_target_factor").asDouble(),
            json.get("screen_target_rate").asDouble()
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "catch_rate", catchRate);
        first = JsonOut.field(sb, first, "yards_per_target", yardsPerTarget);
        first = JsonOut.field(sb, first, "adot", adot);
        first = JsonOut.field(sb, first, "separation_yds", separationYds);
        first = JsonOut.field(sb, first, "contested_catch_rate", contestedCatchRate);
        first = JsonOut.field(sb, first, "drop_rate", dropRate);
        first = JsonOut.field(sb, first, "yac_per_catch", yacPerCatch);
        first = JsonOut.field(sb, first, "zero_yac_rate", zeroYacRate);
        first = JsonOut.field(sb, first, "broken_tackle_rate", brokenTackleRate);
        first = JsonOut.field(sb, first, "man_target_factor", manTargetFactor);
        first = JsonOut.field(sb, first, "zone_target_factor", zoneTargetFactor);
        first = JsonOut.field(sb, first, "screen_target_rate", screenTargetRate);
        sb.append('}');
        return sb.toString();
    }
}
