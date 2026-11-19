// DO NOT EDIT — generated from schema/api/sim/nba_sim.yml by generate-sim-sdks.ts
package com.statapi;

/**
 * Pre-simulation CRN Vegas calibration diagnostics.
 *
 * @param vegasMode Effective engine calibration mode ({@code full}, {@code partial}, {@code macro_only}, {@code zero_vegas}).
 * @param iterationsRun Solver iterations completed.
 * @param targetHomePts Calibrated home team point target.
 * @param targetAwayPts Calibrated away team point target.
 * @param achievedPilotHomePts Achieved home team pilot mean points.
 * @param achievedPilotAwayPts Achieved away team pilot mean points.
 * @param homeEfgTilt Home eFG calibration tilt applied.
 * @param awayEfgTilt Away eFG calibration tilt applied.
 * @param rankInversionCount Count of team scoring rank inversions ({@code 0} = monotone hierarchy preserved).
 */
public record NBAVegasCalibrationDiagnostics(
    String vegasMode,
    Integer iterationsRun,
    Double targetHomePts,
    Double targetAwayPts,
    Double achievedPilotHomePts,
    Double achievedPilotAwayPts,
    Double homeEfgTilt,
    Double awayEfgTilt,
    Integer rankInversionCount
) implements JsonSerializable {

    public static NBAVegasCalibrationDiagnostics fromJson(JsonValue json) {
        return new NBAVegasCalibrationDiagnostics(
            json.get("vegas_mode").asString(),
            json.get("iterations_run").asInt(),
            json.get("target_home_pts").asDouble(),
            json.get("target_away_pts").asDouble(),
            json.get("achieved_pilot_home_pts").asDouble(),
            json.get("achieved_pilot_away_pts").asDouble(),
            json.get("home_efg_tilt").asDouble(),
            json.get("away_efg_tilt").asDouble(),
            json.get("rank_inversion_count").asInt()
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "vegas_mode", vegasMode);
        first = JsonOut.field(sb, first, "iterations_run", iterationsRun);
        first = JsonOut.field(sb, first, "target_home_pts", targetHomePts);
        first = JsonOut.field(sb, first, "target_away_pts", targetAwayPts);
        first = JsonOut.field(sb, first, "achieved_pilot_home_pts", achievedPilotHomePts);
        first = JsonOut.field(sb, first, "achieved_pilot_away_pts", achievedPilotAwayPts);
        first = JsonOut.field(sb, first, "home_efg_tilt", homeEfgTilt);
        first = JsonOut.field(sb, first, "away_efg_tilt", awayEfgTilt);
        first = JsonOut.field(sb, first, "rank_inversion_count", rankInversionCount);
        sb.append('}');
        return sb.toString();
    }
}
