// DO NOT EDIT — generated from schema/api/sim/nfl_sim.yml by generate-sim-sdks.ts
package com.statapi;

/**
 * Football-native Quarterback passing accuracy, pocket timing, tight-window aggressiveness, sack avoidance, and scramble profile.
 *
 * @param completionRate Expected completion percentage in a neutral matchup ({@code 0.52..0.75}, NFL avg {@code 0.648}).
 * @param timeToThrowSec Average pocket time from snap to throw or pressure in seconds ({@code 2.30..3.20s}, NFL avg {@code 2.72s}).
 * @param badThrowRate Rate of uncatchable off-target throws ({@code 0.08..0.28}, NFL avg {@code 0.160}).
 * @param tightWindowRate Share of pass attempts thrown into tight ({@code < 1 yd} separation) coverage windows (NFL avg {@code 0.150}).
 * @param pressureSackRate Share of pressured dropbacks that result in a sack ({@code 0.10..0.32}, NFL avg {@code 0.185}).
 * @param pressureScrambleRate Share of pressured dropbacks where the QB scrambles ({@code 0.03..0.30}, NFL avg {@code 0.115}).
 * @param interceptionRate Interceptions thrown per pass attempt ({@code 0.008..0.055}, NFL avg {@code 0.023}).
 * @param designedRunShare Share of team designed rushing plays called for the QB ({@code 0.01..0.28}, NFL avg {@code 0.085}).
 */
public record NFLSimPassing(
    Double completionRate,
    Double timeToThrowSec,
    Double badThrowRate,
    Double tightWindowRate,
    Double pressureSackRate,
    Double pressureScrambleRate,
    Double interceptionRate,
    Double designedRunShare
) implements JsonSerializable {

    public static NFLSimPassing fromJson(JsonValue json) {
        return new NFLSimPassing(
            json.get("completion_rate").asDouble(),
            json.get("time_to_throw_sec").asDouble(),
            json.get("bad_throw_rate").asDouble(),
            json.get("tight_window_rate").asDouble(),
            json.get("pressure_sack_rate").asDouble(),
            json.get("pressure_scramble_rate").asDouble(),
            json.get("interception_rate").asDouble(),
            json.get("designed_run_share").asDouble()
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "completion_rate", completionRate);
        first = JsonOut.field(sb, first, "time_to_throw_sec", timeToThrowSec);
        first = JsonOut.field(sb, first, "bad_throw_rate", badThrowRate);
        first = JsonOut.field(sb, first, "tight_window_rate", tightWindowRate);
        first = JsonOut.field(sb, first, "pressure_sack_rate", pressureSackRate);
        first = JsonOut.field(sb, first, "pressure_scramble_rate", pressureScrambleRate);
        first = JsonOut.field(sb, first, "interception_rate", interceptionRate);
        first = JsonOut.field(sb, first, "designed_run_share", designedRunShare);
        sb.append('}');
        return sb.toString();
    }
}
