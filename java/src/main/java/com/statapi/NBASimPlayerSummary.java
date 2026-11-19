// DO NOT EDIT — generated from schema/api/sim/nba_sim.yml by generate-sim-sdks.ts
package com.statapi;

/**
 * Per-player mean, SD, quantiles, and DFS milestones across simulated draws.
 *
 * @param playerId Canonical player ID.
 * @param name Player display name.
 * @param teamCode Team abbreviation.
 * @param isHome True if home team.
 * @param position Court position ({@code PG}, {@code SG}, {@code SF}, {@code PF}, {@code C}).
 * @param rotationRole Rotation role ({@code starter}, {@code sixth_man}, {@code rotation}, {@code bench}, {@code out}).
 * @param roleLabel Display role label.
 * @param rotationArchetypeLabel Derived rotation archetype description.
 * @param starterProb Probability of starting ({@code 0.0..1.0}).
 * @param activeProb Probability of playing ({@code 0.0..1.0}).
 * @param coachDnpProb Simulated coach-DNP probability ({@code 0.0..1.0}).
 * @param dkSalary DraftKings Classic salary in dollars when listed on slate.
 * @param fdSalary FanDuel Classic salary in dollars when listed on slate.
 * @param projectedMinutes Expected realized minutes across all simulated scripts.
 * @param minutesMean Mean minutes.
 * @param minutesSd Minutes standard deviation.
 * @param minutesP10 10th percentile minutes.
 * @param minutesP50 50th percentile minutes.
 * @param minutesP90 90th percentile minutes.
 * @param minutesQuantiles Full minutes quantile profile.
 * @param q1MinutesMean Mean Q1 minutes.
 * @param q2MinutesMean Mean Q2 minutes.
 * @param q3MinutesMean Mean Q3 minutes.
 * @param q4MinutesMean Mean Q4 minutes.
 * @param otMinutesMean Mean overtime minutes.
 * @param q1Full12mProb Probability of playing all 12 minutes in Q1.
 * @param stintCountMean Mean number of floor stints played.
 * @param onCourtOrtgMean Mean team offensive rating while player is on floor.
 * @param offCourtOrtgMean Mean team offensive rating while player is on bench.
 * @param onCourtDrtgMean Mean team defensive rating while player is on floor.
 * @param offCourtDrtgMean Mean team defensive rating while player is on bench.
 * @param onOffNetRatingDeltaMean On/off net rating differential.
 * @param ptsMean Mean points.
 * @param ptsSd Points standard deviation.
 * @param ptsP10 10th percentile points.
 * @param ptsP50 50th percentile points.
 * @param ptsP90 90th percentile points.
 * @param ptsQuantiles Full points quantile profile.
 * @param q1PtsMean Mean Q1 points.
 * @param q2PtsMean Mean Q2 points.
 * @param q3PtsMean Mean Q3 points.
 * @param q4PtsMean Mean Q4 points.
 * @param rebMean Mean total rebounds.
 * @param rebSd Rebounds standard deviation.
 * @param rebP10 10th percentile rebounds.
 * @param rebP50 50th percentile rebounds.
 * @param rebP90 90th percentile rebounds.
 * @param rebQuantiles Full rebounds quantile profile.
 * @param orebMean Mean offensive rebounds.
 * @param drebMean Mean defensive rebounds.
 * @param astMean Mean assists.
 * @param astSd Assists standard deviation.
 * @param astP10 10th percentile assists.
 * @param astP50 50th percentile assists.
 * @param astP90 90th percentile assists.
 * @param astQuantiles Full assists quantile profile.
 * @param fg3mMean Mean three-pointers made.
 * @param fg3mP50 50th percentile three-pointers made.
 * @param fg3mP90 90th percentile three-pointers made.
 * @param fg3mQuantiles Full 3PM quantile profile.
 * @param fg3aMean Mean three-point attempts.
 * @param fg2mMean Mean two-pointers made.
 * @param fg2aMean Mean two-point attempts.
 * @param fgmMean Mean field goals made.
 * @param fgaMean Mean field goal attempts.
 * @param ftmMean Mean free throws made.
 * @param ftaMean Mean free throw attempts.
 * @param stlMean Mean steals.
 * @param blkMean Mean blocks.
 * @param baMean Mean blocked attempts.
 * @param tovMean Mean turnovers.
 * @param pfMean Mean personal fouls.
 * @param fdMean Mean fouls drawn.
 * @param paintPtsMean Mean paint points.
 * @param fastbreakPtsMean Mean fastbreak points.
 * @param secondChancePtsMean Mean second-chance points.
 * @param unassistedFgmMean Mean unassisted field goals made.
 * @param foulTroubleRate Probability of hitting foul trouble ({@code 0.0..1.0}).
 * @param plusMinusMean Mean on-court plus-minus.
 * @param praMean Mean PRA ({@code pts + reb + ast}).
 * @param praP50 50th percentile PRA.
 * @param praP90 90th percentile PRA.
 * @param praQuantiles Full PRA quantile profile.
 * @param doubleDoubleProb Probability of recording a double-double ({@code 0.0..1.0}).
 * @param tripleDoubleProb Probability of recording a triple-double ({@code 0.0..1.0}).
 * @param p20plusPts Probability of scoring {@code 20+} points.
 * @param p30plusPts Probability of scoring {@code 30+} points.
 * @param p40plusPts Probability of scoring {@code 40+} points.
 * @param p50plusPts Probability of scoring {@code 50+} points.
 * @param p10plusReb Probability of recording {@code 10+} rebounds.
 * @param p10plusAst Probability of recording {@code 10+} assists.
 * @param p50plusDk Probability of {@code 50+} DraftKings fantasy points.
 * @param p60plusDk Probability of {@code 60+} DraftKings fantasy points.
 * @param dkPtsMean Mean DraftKings NBA fantasy points.
 * @param dkPtsSd DraftKings fantasy points standard deviation.
 * @param dkPtsP10 10th percentile DraftKings points.
 * @param dkPtsP50 50th percentile DraftKings points.
 * @param dkPtsP75 75th percentile DraftKings points.
 * @param dkPtsP90 90th percentile DraftKings points.
 * @param dkPtsP95 95th percentile DraftKings points.
 * @param dkPtsQuantiles Full DraftKings fantasy points quantile profile.
 * @param fdPtsMean Mean FanDuel NBA fantasy points.
 * @param fdPtsSd FanDuel fantasy points standard deviation.
 * @param fdPtsP50 50th percentile FanDuel points.
 * @param fdPtsP90 90th percentile FanDuel points.
 * @param fdPtsQuantiles Full FanDuel fantasy points quantile profile.
 * @param vegasPtsLine Effective points prop line used in calibration.
 * @param vegasRebLine Effective rebounds prop line used in calibration.
 * @param vegasAstLine Effective assists prop line used in calibration.
 * @param vegasThreesLine Effective 3PM prop line used in calibration.
 * @param vegasPraLine Effective PRA prop line used in calibration.
 */
public record NBASimPlayerSummary(
    Long playerId,
    String name,
    String teamCode,
    Boolean isHome,
    String position,
    String rotationRole,
    String roleLabel,
    String rotationArchetypeLabel,
    Double starterProb,
    Double activeProb,
    Double coachDnpProb,
    Integer dkSalary,
    Integer fdSalary,
    Double projectedMinutes,
    Double minutesMean,
    Double minutesSd,
    Double minutesP10,
    Double minutesP50,
    Double minutesP90,
    NBASimQuantileProfile minutesQuantiles,
    Double q1MinutesMean,
    Double q2MinutesMean,
    Double q3MinutesMean,
    Double q4MinutesMean,
    Double otMinutesMean,
    Double q1Full12mProb,
    Double stintCountMean,
    Double onCourtOrtgMean,
    Double offCourtOrtgMean,
    Double onCourtDrtgMean,
    Double offCourtDrtgMean,
    Double onOffNetRatingDeltaMean,
    Double ptsMean,
    Double ptsSd,
    Double ptsP10,
    Double ptsP50,
    Double ptsP90,
    NBASimQuantileProfile ptsQuantiles,
    Double q1PtsMean,
    Double q2PtsMean,
    Double q3PtsMean,
    Double q4PtsMean,
    Double rebMean,
    Double rebSd,
    Double rebP10,
    Double rebP50,
    Double rebP90,
    NBASimQuantileProfile rebQuantiles,
    Double orebMean,
    Double drebMean,
    Double astMean,
    Double astSd,
    Double astP10,
    Double astP50,
    Double astP90,
    NBASimQuantileProfile astQuantiles,
    Double fg3mMean,
    Double fg3mP50,
    Double fg3mP90,
    NBASimQuantileProfile fg3mQuantiles,
    Double fg3aMean,
    Double fg2mMean,
    Double fg2aMean,
    Double fgmMean,
    Double fgaMean,
    Double ftmMean,
    Double ftaMean,
    Double stlMean,
    Double blkMean,
    Double baMean,
    Double tovMean,
    Double pfMean,
    Double fdMean,
    Double paintPtsMean,
    Double fastbreakPtsMean,
    Double secondChancePtsMean,
    Double unassistedFgmMean,
    Double foulTroubleRate,
    Double plusMinusMean,
    Double praMean,
    Double praP50,
    Double praP90,
    NBASimQuantileProfile praQuantiles,
    Double doubleDoubleProb,
    Double tripleDoubleProb,
    Double p20plusPts,
    Double p30plusPts,
    Double p40plusPts,
    Double p50plusPts,
    Double p10plusReb,
    Double p10plusAst,
    Double p50plusDk,
    Double p60plusDk,
    Double dkPtsMean,
    Double dkPtsSd,
    Double dkPtsP10,
    Double dkPtsP50,
    Double dkPtsP75,
    Double dkPtsP90,
    Double dkPtsP95,
    NBASimQuantileProfile dkPtsQuantiles,
    Double fdPtsMean,
    Double fdPtsSd,
    Double fdPtsP50,
    Double fdPtsP90,
    NBASimQuantileProfile fdPtsQuantiles,
    Double vegasPtsLine,
    Double vegasRebLine,
    Double vegasAstLine,
    Double vegasThreesLine,
    Double vegasPraLine
) implements JsonSerializable {

    public static NBASimPlayerSummary fromJson(JsonValue json) {
        return new NBASimPlayerSummary(
            json.get("player_id").asLong(),
            json.get("name").asString(),
            json.get("team_code").asString(),
            json.get("is_home").asBoolean(),
            json.get("position").asString(),
            json.get("rotation_role").asString(),
            json.get("role_label").asString(),
            json.get("rotation_archetype_label").asString(),
            json.get("starter_prob").asDouble(),
            json.get("active_prob").asDouble(),
            json.get("coach_dnp_prob").asDouble(),
            json.get("dk_salary").asInt(),
            json.get("fd_salary").asInt(),
            json.get("projected_minutes").asDouble(),
            json.get("minutes_mean").asDouble(),
            json.get("minutes_sd").asDouble(),
            json.get("minutes_p10").asDouble(),
            json.get("minutes_p50").asDouble(),
            json.get("minutes_p90").asDouble(),
            JsonIn.obj(json.get("minutes_quantiles"), NBASimQuantileProfile::fromJson),
            json.get("q1_minutes_mean").asDouble(),
            json.get("q2_minutes_mean").asDouble(),
            json.get("q3_minutes_mean").asDouble(),
            json.get("q4_minutes_mean").asDouble(),
            json.get("ot_minutes_mean").asDouble(),
            json.get("q1_full_12m_prob").asDouble(),
            json.get("stint_count_mean").asDouble(),
            json.get("on_court_ortg_mean").asDouble(),
            json.get("off_court_ortg_mean").asDouble(),
            json.get("on_court_drtg_mean").asDouble(),
            json.get("off_court_drtg_mean").asDouble(),
            json.get("on_off_net_rating_delta_mean").asDouble(),
            json.get("pts_mean").asDouble(),
            json.get("pts_sd").asDouble(),
            json.get("pts_p10").asDouble(),
            json.get("pts_p50").asDouble(),
            json.get("pts_p90").asDouble(),
            JsonIn.obj(json.get("pts_quantiles"), NBASimQuantileProfile::fromJson),
            json.get("q1_pts_mean").asDouble(),
            json.get("q2_pts_mean").asDouble(),
            json.get("q3_pts_mean").asDouble(),
            json.get("q4_pts_mean").asDouble(),
            json.get("reb_mean").asDouble(),
            json.get("reb_sd").asDouble(),
            json.get("reb_p10").asDouble(),
            json.get("reb_p50").asDouble(),
            json.get("reb_p90").asDouble(),
            JsonIn.obj(json.get("reb_quantiles"), NBASimQuantileProfile::fromJson),
            json.get("oreb_mean").asDouble(),
            json.get("dreb_mean").asDouble(),
            json.get("ast_mean").asDouble(),
            json.get("ast_sd").asDouble(),
            json.get("ast_p10").asDouble(),
            json.get("ast_p50").asDouble(),
            json.get("ast_p90").asDouble(),
            JsonIn.obj(json.get("ast_quantiles"), NBASimQuantileProfile::fromJson),
            json.get("fg3m_mean").asDouble(),
            json.get("fg3m_p50").asDouble(),
            json.get("fg3m_p90").asDouble(),
            JsonIn.obj(json.get("fg3m_quantiles"), NBASimQuantileProfile::fromJson),
            json.get("fg3a_mean").asDouble(),
            json.get("fg2m_mean").asDouble(),
            json.get("fg2a_mean").asDouble(),
            json.get("fgm_mean").asDouble(),
            json.get("fga_mean").asDouble(),
            json.get("ftm_mean").asDouble(),
            json.get("fta_mean").asDouble(),
            json.get("stl_mean").asDouble(),
            json.get("blk_mean").asDouble(),
            json.get("ba_mean").asDouble(),
            json.get("tov_mean").asDouble(),
            json.get("pf_mean").asDouble(),
            json.get("fd_mean").asDouble(),
            json.get("paint_pts_mean").asDouble(),
            json.get("fastbreak_pts_mean").asDouble(),
            json.get("second_chance_pts_mean").asDouble(),
            json.get("unassisted_fgm_mean").asDouble(),
            json.get("foul_trouble_rate").asDouble(),
            json.get("plus_minus_mean").asDouble(),
            json.get("pra_mean").asDouble(),
            json.get("pra_p50").asDouble(),
            json.get("pra_p90").asDouble(),
            JsonIn.obj(json.get("pra_quantiles"), NBASimQuantileProfile::fromJson),
            json.get("double_double_prob").asDouble(),
            json.get("triple_double_prob").asDouble(),
            json.get("p_20plus_pts").asDouble(),
            json.get("p_30plus_pts").asDouble(),
            json.get("p_40plus_pts").asDouble(),
            json.get("p_50plus_pts").asDouble(),
            json.get("p_10plus_reb").asDouble(),
            json.get("p_10plus_ast").asDouble(),
            json.get("p_50plus_dk").asDouble(),
            json.get("p_60plus_dk").asDouble(),
            json.get("dk_pts_mean").asDouble(),
            json.get("dk_pts_sd").asDouble(),
            json.get("dk_pts_p10").asDouble(),
            json.get("dk_pts_p50").asDouble(),
            json.get("dk_pts_p75").asDouble(),
            json.get("dk_pts_p90").asDouble(),
            json.get("dk_pts_p95").asDouble(),
            JsonIn.obj(json.get("dk_pts_quantiles"), NBASimQuantileProfile::fromJson),
            json.get("fd_pts_mean").asDouble(),
            json.get("fd_pts_sd").asDouble(),
            json.get("fd_pts_p50").asDouble(),
            json.get("fd_pts_p90").asDouble(),
            JsonIn.obj(json.get("fd_pts_quantiles"), NBASimQuantileProfile::fromJson),
            json.get("vegas_pts_line").asDouble(),
            json.get("vegas_reb_line").asDouble(),
            json.get("vegas_ast_line").asDouble(),
            json.get("vegas_threes_line").asDouble(),
            json.get("vegas_pra_line").asDouble()
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "player_id", playerId);
        first = JsonOut.field(sb, first, "name", name);
        first = JsonOut.field(sb, first, "team_code", teamCode);
        first = JsonOut.field(sb, first, "is_home", isHome);
        first = JsonOut.field(sb, first, "position", position);
        first = JsonOut.field(sb, first, "rotation_role", rotationRole);
        first = JsonOut.field(sb, first, "role_label", roleLabel);
        first = JsonOut.field(sb, first, "rotation_archetype_label", rotationArchetypeLabel);
        first = JsonOut.field(sb, first, "starter_prob", starterProb);
        first = JsonOut.field(sb, first, "active_prob", activeProb);
        first = JsonOut.field(sb, first, "coach_dnp_prob", coachDnpProb);
        first = JsonOut.field(sb, first, "dk_salary", dkSalary);
        first = JsonOut.field(sb, first, "fd_salary", fdSalary);
        first = JsonOut.field(sb, first, "projected_minutes", projectedMinutes);
        first = JsonOut.field(sb, first, "minutes_mean", minutesMean);
        first = JsonOut.field(sb, first, "minutes_sd", minutesSd);
        first = JsonOut.field(sb, first, "minutes_p10", minutesP10);
        first = JsonOut.field(sb, first, "minutes_p50", minutesP50);
        first = JsonOut.field(sb, first, "minutes_p90", minutesP90);
        first = JsonOut.field(sb, first, "minutes_quantiles", minutesQuantiles);
        first = JsonOut.field(sb, first, "q1_minutes_mean", q1MinutesMean);
        first = JsonOut.field(sb, first, "q2_minutes_mean", q2MinutesMean);
        first = JsonOut.field(sb, first, "q3_minutes_mean", q3MinutesMean);
        first = JsonOut.field(sb, first, "q4_minutes_mean", q4MinutesMean);
        first = JsonOut.field(sb, first, "ot_minutes_mean", otMinutesMean);
        first = JsonOut.field(sb, first, "q1_full_12m_prob", q1Full12mProb);
        first = JsonOut.field(sb, first, "stint_count_mean", stintCountMean);
        first = JsonOut.field(sb, first, "on_court_ortg_mean", onCourtOrtgMean);
        first = JsonOut.field(sb, first, "off_court_ortg_mean", offCourtOrtgMean);
        first = JsonOut.field(sb, first, "on_court_drtg_mean", onCourtDrtgMean);
        first = JsonOut.field(sb, first, "off_court_drtg_mean", offCourtDrtgMean);
        first = JsonOut.field(sb, first, "on_off_net_rating_delta_mean", onOffNetRatingDeltaMean);
        first = JsonOut.field(sb, first, "pts_mean", ptsMean);
        first = JsonOut.field(sb, first, "pts_sd", ptsSd);
        first = JsonOut.field(sb, first, "pts_p10", ptsP10);
        first = JsonOut.field(sb, first, "pts_p50", ptsP50);
        first = JsonOut.field(sb, first, "pts_p90", ptsP90);
        first = JsonOut.field(sb, first, "pts_quantiles", ptsQuantiles);
        first = JsonOut.field(sb, first, "q1_pts_mean", q1PtsMean);
        first = JsonOut.field(sb, first, "q2_pts_mean", q2PtsMean);
        first = JsonOut.field(sb, first, "q3_pts_mean", q3PtsMean);
        first = JsonOut.field(sb, first, "q4_pts_mean", q4PtsMean);
        first = JsonOut.field(sb, first, "reb_mean", rebMean);
        first = JsonOut.field(sb, first, "reb_sd", rebSd);
        first = JsonOut.field(sb, first, "reb_p10", rebP10);
        first = JsonOut.field(sb, first, "reb_p50", rebP50);
        first = JsonOut.field(sb, first, "reb_p90", rebP90);
        first = JsonOut.field(sb, first, "reb_quantiles", rebQuantiles);
        first = JsonOut.field(sb, first, "oreb_mean", orebMean);
        first = JsonOut.field(sb, first, "dreb_mean", drebMean);
        first = JsonOut.field(sb, first, "ast_mean", astMean);
        first = JsonOut.field(sb, first, "ast_sd", astSd);
        first = JsonOut.field(sb, first, "ast_p10", astP10);
        first = JsonOut.field(sb, first, "ast_p50", astP50);
        first = JsonOut.field(sb, first, "ast_p90", astP90);
        first = JsonOut.field(sb, first, "ast_quantiles", astQuantiles);
        first = JsonOut.field(sb, first, "fg3m_mean", fg3mMean);
        first = JsonOut.field(sb, first, "fg3m_p50", fg3mP50);
        first = JsonOut.field(sb, first, "fg3m_p90", fg3mP90);
        first = JsonOut.field(sb, first, "fg3m_quantiles", fg3mQuantiles);
        first = JsonOut.field(sb, first, "fg3a_mean", fg3aMean);
        first = JsonOut.field(sb, first, "fg2m_mean", fg2mMean);
        first = JsonOut.field(sb, first, "fg2a_mean", fg2aMean);
        first = JsonOut.field(sb, first, "fgm_mean", fgmMean);
        first = JsonOut.field(sb, first, "fga_mean", fgaMean);
        first = JsonOut.field(sb, first, "ftm_mean", ftmMean);
        first = JsonOut.field(sb, first, "fta_mean", ftaMean);
        first = JsonOut.field(sb, first, "stl_mean", stlMean);
        first = JsonOut.field(sb, first, "blk_mean", blkMean);
        first = JsonOut.field(sb, first, "ba_mean", baMean);
        first = JsonOut.field(sb, first, "tov_mean", tovMean);
        first = JsonOut.field(sb, first, "pf_mean", pfMean);
        first = JsonOut.field(sb, first, "fd_mean", fdMean);
        first = JsonOut.field(sb, first, "paint_pts_mean", paintPtsMean);
        first = JsonOut.field(sb, first, "fastbreak_pts_mean", fastbreakPtsMean);
        first = JsonOut.field(sb, first, "second_chance_pts_mean", secondChancePtsMean);
        first = JsonOut.field(sb, first, "unassisted_fgm_mean", unassistedFgmMean);
        first = JsonOut.field(sb, first, "foul_trouble_rate", foulTroubleRate);
        first = JsonOut.field(sb, first, "plus_minus_mean", plusMinusMean);
        first = JsonOut.field(sb, first, "pra_mean", praMean);
        first = JsonOut.field(sb, first, "pra_p50", praP50);
        first = JsonOut.field(sb, first, "pra_p90", praP90);
        first = JsonOut.field(sb, first, "pra_quantiles", praQuantiles);
        first = JsonOut.field(sb, first, "double_double_prob", doubleDoubleProb);
        first = JsonOut.field(sb, first, "triple_double_prob", tripleDoubleProb);
        first = JsonOut.field(sb, first, "p_20plus_pts", p20plusPts);
        first = JsonOut.field(sb, first, "p_30plus_pts", p30plusPts);
        first = JsonOut.field(sb, first, "p_40plus_pts", p40plusPts);
        first = JsonOut.field(sb, first, "p_50plus_pts", p50plusPts);
        first = JsonOut.field(sb, first, "p_10plus_reb", p10plusReb);
        first = JsonOut.field(sb, first, "p_10plus_ast", p10plusAst);
        first = JsonOut.field(sb, first, "p_50plus_dk", p50plusDk);
        first = JsonOut.field(sb, first, "p_60plus_dk", p60plusDk);
        first = JsonOut.field(sb, first, "dk_pts_mean", dkPtsMean);
        first = JsonOut.field(sb, first, "dk_pts_sd", dkPtsSd);
        first = JsonOut.field(sb, first, "dk_pts_p10", dkPtsP10);
        first = JsonOut.field(sb, first, "dk_pts_p50", dkPtsP50);
        first = JsonOut.field(sb, first, "dk_pts_p75", dkPtsP75);
        first = JsonOut.field(sb, first, "dk_pts_p90", dkPtsP90);
        first = JsonOut.field(sb, first, "dk_pts_p95", dkPtsP95);
        first = JsonOut.field(sb, first, "dk_pts_quantiles", dkPtsQuantiles);
        first = JsonOut.field(sb, first, "fd_pts_mean", fdPtsMean);
        first = JsonOut.field(sb, first, "fd_pts_sd", fdPtsSd);
        first = JsonOut.field(sb, first, "fd_pts_p50", fdPtsP50);
        first = JsonOut.field(sb, first, "fd_pts_p90", fdPtsP90);
        first = JsonOut.field(sb, first, "fd_pts_quantiles", fdPtsQuantiles);
        first = JsonOut.field(sb, first, "vegas_pts_line", vegasPtsLine);
        first = JsonOut.field(sb, first, "vegas_reb_line", vegasRebLine);
        first = JsonOut.field(sb, first, "vegas_ast_line", vegasAstLine);
        first = JsonOut.field(sb, first, "vegas_threes_line", vegasThreesLine);
        first = JsonOut.field(sb, first, "vegas_pra_line", vegasPraLine);
        sb.append('}');
        return sb.toString();
    }
}
