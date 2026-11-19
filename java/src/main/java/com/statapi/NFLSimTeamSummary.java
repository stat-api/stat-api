// DO NOT EDIT — generated from schema/api/sim/nfl_sim.yml by generate-sim-sdks.ts
package com.statapi;

/**
 * Team-level offensive, drive, situational, and DST summary metrics across all n_draws.
 *
 * @param teamId Canonical team ID.
 * @param isHome True for home team, false for away team.
 * @param vegasTeamTotal Input implied Vegas team total points.
 * @param pointsMean Simulated mean points scored.
 * @param pointsSd Standard deviation of points scored.
 * @param pointsP10 10th percentile of points scored.
 * @param pointsP50 Median (50th percentile) points scored.
 * @param pointsP90 90th percentile of points scored.
 * @param q1PointsMean Mean 1st-quarter points.
 * @param q2PointsMean Mean 2nd-quarter points.
 * @param q3PointsMean Mean 3rd-quarter points.
 * @param q4PointsMean Mean 4th-quarter points.
 * @param otPointsMean Mean overtime points.
 * @param winProb Team win probability ({@code 0.0..1.0}).
 * @param playsMean Mean offensive plays ({@code pass_att + rush_att + sacks_taken}).
 * @param passAttMean Mean team pass attempts.
 * @param passCmpMean Mean team pass completions.
 * @param passYdMean Mean team gross passing yards.
 * @param passTdMean Mean team passing touchdowns.
 * @param intMean Mean interceptions thrown.
 * @param rushAttMean Mean team rushing attempts.
 * @param rushYdMean Mean team rushing yards.
 * @param rushTdMean Mean team rushing touchdowns.
 * @param sacksTakenMean Mean sacks taken by the offense.
 * @param drivesMean Mean offensive drives per game.
 * @param driveTdRate Fraction of offensive drives ending in an offensive touchdown ({@code 0.0..1.0}).
 * @param driveFgRate Fraction of offensive drives ending in a made field goal ({@code 0.0..1.0}).
 * @param drivePuntRate Fraction of offensive drives ending in a punt ({@code 0.0..1.0}).
 * @param driveTurnoverRate Fraction of offensive drives ending in an interception or lost fumble ({@code 0.0..1.0}).
 * @param thirdDownConvRate 3rd-down conversion rate ({@code 0.0..1.0}).
 * @param fourthDownConvRate 4th-down go-for-it conversion rate ({@code 0.0..1.0}).
 * @param dstSacksMean Mean defensive sacks recorded against the opponent.
 * @param dstIntMean Mean defensive interceptions recorded.
 * @param dstFumblesRecMean Mean opponent fumbles recovered by the defense.
 * @param dstTdMean Mean defensive/return touchdowns scored.
 * @param dstPtsAllowedMean Mean points allowed by the defense.
 */
public record NFLSimTeamSummary(
    Long teamId,
    Boolean isHome,
    Double vegasTeamTotal,
    Double pointsMean,
    Double pointsSd,
    Double pointsP10,
    Double pointsP50,
    Double pointsP90,
    Double q1PointsMean,
    Double q2PointsMean,
    Double q3PointsMean,
    Double q4PointsMean,
    Double otPointsMean,
    Double winProb,
    Double playsMean,
    Double passAttMean,
    Double passCmpMean,
    Double passYdMean,
    Double passTdMean,
    Double intMean,
    Double rushAttMean,
    Double rushYdMean,
    Double rushTdMean,
    Double sacksTakenMean,
    Double drivesMean,
    Double driveTdRate,
    Double driveFgRate,
    Double drivePuntRate,
    Double driveTurnoverRate,
    Double thirdDownConvRate,
    Double fourthDownConvRate,
    Double dstSacksMean,
    Double dstIntMean,
    Double dstFumblesRecMean,
    Double dstTdMean,
    Double dstPtsAllowedMean
) implements JsonSerializable {

    public static NFLSimTeamSummary fromJson(JsonValue json) {
        return new NFLSimTeamSummary(
            json.get("team_id").asLong(),
            json.get("is_home").asBoolean(),
            json.get("vegas_team_total").asDouble(),
            json.get("points_mean").asDouble(),
            json.get("points_sd").asDouble(),
            json.get("points_p10").asDouble(),
            json.get("points_p50").asDouble(),
            json.get("points_p90").asDouble(),
            json.get("q1_points_mean").asDouble(),
            json.get("q2_points_mean").asDouble(),
            json.get("q3_points_mean").asDouble(),
            json.get("q4_points_mean").asDouble(),
            json.get("ot_points_mean").asDouble(),
            json.get("win_prob").asDouble(),
            json.get("plays_mean").asDouble(),
            json.get("pass_att_mean").asDouble(),
            json.get("pass_cmp_mean").asDouble(),
            json.get("pass_yd_mean").asDouble(),
            json.get("pass_td_mean").asDouble(),
            json.get("int_mean").asDouble(),
            json.get("rush_att_mean").asDouble(),
            json.get("rush_yd_mean").asDouble(),
            json.get("rush_td_mean").asDouble(),
            json.get("sacks_taken_mean").asDouble(),
            json.get("drives_mean").asDouble(),
            json.get("drive_td_rate").asDouble(),
            json.get("drive_fg_rate").asDouble(),
            json.get("drive_punt_rate").asDouble(),
            json.get("drive_turnover_rate").asDouble(),
            json.get("third_down_conv_rate").asDouble(),
            json.get("fourth_down_conv_rate").asDouble(),
            json.get("dst_sacks_mean").asDouble(),
            json.get("dst_int_mean").asDouble(),
            json.get("dst_fumbles_rec_mean").asDouble(),
            json.get("dst_td_mean").asDouble(),
            json.get("dst_pts_allowed_mean").asDouble()
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "team_id", teamId);
        first = JsonOut.field(sb, first, "is_home", isHome);
        first = JsonOut.field(sb, first, "vegas_team_total", vegasTeamTotal);
        first = JsonOut.field(sb, first, "points_mean", pointsMean);
        first = JsonOut.field(sb, first, "points_sd", pointsSd);
        first = JsonOut.field(sb, first, "points_p10", pointsP10);
        first = JsonOut.field(sb, first, "points_p50", pointsP50);
        first = JsonOut.field(sb, first, "points_p90", pointsP90);
        first = JsonOut.field(sb, first, "q1_points_mean", q1PointsMean);
        first = JsonOut.field(sb, first, "q2_points_mean", q2PointsMean);
        first = JsonOut.field(sb, first, "q3_points_mean", q3PointsMean);
        first = JsonOut.field(sb, first, "q4_points_mean", q4PointsMean);
        first = JsonOut.field(sb, first, "ot_points_mean", otPointsMean);
        first = JsonOut.field(sb, first, "win_prob", winProb);
        first = JsonOut.field(sb, first, "plays_mean", playsMean);
        first = JsonOut.field(sb, first, "pass_att_mean", passAttMean);
        first = JsonOut.field(sb, first, "pass_cmp_mean", passCmpMean);
        first = JsonOut.field(sb, first, "pass_yd_mean", passYdMean);
        first = JsonOut.field(sb, first, "pass_td_mean", passTdMean);
        first = JsonOut.field(sb, first, "int_mean", intMean);
        first = JsonOut.field(sb, first, "rush_att_mean", rushAttMean);
        first = JsonOut.field(sb, first, "rush_yd_mean", rushYdMean);
        first = JsonOut.field(sb, first, "rush_td_mean", rushTdMean);
        first = JsonOut.field(sb, first, "sacks_taken_mean", sacksTakenMean);
        first = JsonOut.field(sb, first, "drives_mean", drivesMean);
        first = JsonOut.field(sb, first, "drive_td_rate", driveTdRate);
        first = JsonOut.field(sb, first, "drive_fg_rate", driveFgRate);
        first = JsonOut.field(sb, first, "drive_punt_rate", drivePuntRate);
        first = JsonOut.field(sb, first, "drive_turnover_rate", driveTurnoverRate);
        first = JsonOut.field(sb, first, "third_down_conv_rate", thirdDownConvRate);
        first = JsonOut.field(sb, first, "fourth_down_conv_rate", fourthDownConvRate);
        first = JsonOut.field(sb, first, "dst_sacks_mean", dstSacksMean);
        first = JsonOut.field(sb, first, "dst_int_mean", dstIntMean);
        first = JsonOut.field(sb, first, "dst_fumbles_rec_mean", dstFumblesRecMean);
        first = JsonOut.field(sb, first, "dst_td_mean", dstTdMean);
        first = JsonOut.field(sb, first, "dst_pts_allowed_mean", dstPtsAllowedMean);
        sb.append('}');
        return sb.toString();
    }
}
