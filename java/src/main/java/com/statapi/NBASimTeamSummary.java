// DO NOT EDIT — generated from schema/api/sim/nba_sim.yml by generate-sim-sdks.ts
package com.statapi;

/**
 * Team-level aggregated simulation statistics.
 *
 * @param teamCode Team abbreviation.
 * @param teamName Full team name.
 * @param isHome True for the home team.
 * @param vegasTeamTotal Implied Vegas team total when Vegas lines are active.
 * @param pointsMean Simulated mean points scored.
 * @param pointsSd Simulated points standard deviation.
 * @param pointsP01 1st percentile team points.
 * @param pointsP05 5th percentile team points.
 * @param pointsP10 10th percentile team points.
 * @param pointsP25 25th percentile team points.
 * @param pointsP50 50th percentile (median) team points.
 * @param pointsP75 75th percentile team points.
 * @param pointsP90 90th percentile team points.
 * @param pointsP95 95th percentile team points.
 * @param pointsP99 99th percentile team points.
 * @param pointsQuantiles Full team points quantile profile.
 * @param q1PointsMean Mean Q1 points.
 * @param q2PointsMean Mean Q2 points.
 * @param q3PointsMean Mean Q3 points.
 * @param q4PointsMean Mean Q4 points.
 * @param otPointsMean Mean overtime points.
 * @param winProb Simulated win probability ({@code 0.0..1.0}).
 * @param possessionsMean Mean possessions.
 * @param offensiveRatingMean Mean simulated offensive rating ({@code pts / 100 poss}).
 * @param fgPctMean Mean field goal percentage.
 * @param fg2PctMean Mean two-point field goal percentage.
 * @param fg3PctMean Mean three-point field goal percentage.
 * @param ftPctMean Mean free throw percentage.
 * @param fgmMean Mean field goals made.
 * @param fgaMean Mean field goals attempted.
 * @param fg2mMean Mean two-pointers made.
 * @param fg2aMean Mean two-pointers attempted.
 * @param fg3mMean Mean three-pointers made.
 * @param fg3aMean Mean three-pointers attempted.
 * @param ftmMean Mean free throws made.
 * @param ftaMean Mean free throws attempted.
 * @param rebMean Mean total rebounds.
 * @param orebMean Mean offensive rebounds.
 * @param drebMean Mean defensive rebounds.
 * @param astMean Mean assists.
 * @param stlMean Mean steals.
 * @param blkMean Mean blocks.
 * @param baMean Mean blocked attempts.
 * @param tovMean Mean turnovers.
 * @param pfMean Mean personal fouls.
 * @param fdMean Mean fouls drawn.
 * @param fastbreakPtsMean Mean fastbreak points.
 * @param paintPtsMean Mean points in the paint.
 * @param secondChancePtsMean Mean second-chance points.
 * @param ptsOffTurnoversMean Mean points off turnovers.
 * @param starterPtsMean Mean starter points.
 * @param benchPtsMean Mean bench points.
 * @param biggestLeadMean Mean largest lead.
 * @param leadChangesMean Mean lead changes.
 * @param timesTiedMean Mean times tied.
 * @param praMean Mean team PRA total.
 * @param dkPtsMean Mean team sum of DraftKings fantasy points.
 * @param fdPtsMean Mean team sum of FanDuel fantasy points.
 */
public record NBASimTeamSummary(
    String teamCode,
    String teamName,
    Boolean isHome,
    Double vegasTeamTotal,
    Double pointsMean,
    Double pointsSd,
    Double pointsP01,
    Double pointsP05,
    Double pointsP10,
    Double pointsP25,
    Double pointsP50,
    Double pointsP75,
    Double pointsP90,
    Double pointsP95,
    Double pointsP99,
    NBASimQuantileProfile pointsQuantiles,
    Double q1PointsMean,
    Double q2PointsMean,
    Double q3PointsMean,
    Double q4PointsMean,
    Double otPointsMean,
    Double winProb,
    Double possessionsMean,
    Double offensiveRatingMean,
    Double fgPctMean,
    Double fg2PctMean,
    Double fg3PctMean,
    Double ftPctMean,
    Double fgmMean,
    Double fgaMean,
    Double fg2mMean,
    Double fg2aMean,
    Double fg3mMean,
    Double fg3aMean,
    Double ftmMean,
    Double ftaMean,
    Double rebMean,
    Double orebMean,
    Double drebMean,
    Double astMean,
    Double stlMean,
    Double blkMean,
    Double baMean,
    Double tovMean,
    Double pfMean,
    Double fdMean,
    Double fastbreakPtsMean,
    Double paintPtsMean,
    Double secondChancePtsMean,
    Double ptsOffTurnoversMean,
    Double starterPtsMean,
    Double benchPtsMean,
    Double biggestLeadMean,
    Double leadChangesMean,
    Double timesTiedMean,
    Double praMean,
    Double dkPtsMean,
    Double fdPtsMean
) implements JsonSerializable {

    public static NBASimTeamSummary fromJson(JsonValue json) {
        return new NBASimTeamSummary(
            json.get("team_code").asString(),
            json.get("team_name").asString(),
            json.get("is_home").asBoolean(),
            json.get("vegas_team_total").asDouble(),
            json.get("points_mean").asDouble(),
            json.get("points_sd").asDouble(),
            json.get("points_p01").asDouble(),
            json.get("points_p05").asDouble(),
            json.get("points_p10").asDouble(),
            json.get("points_p25").asDouble(),
            json.get("points_p50").asDouble(),
            json.get("points_p75").asDouble(),
            json.get("points_p90").asDouble(),
            json.get("points_p95").asDouble(),
            json.get("points_p99").asDouble(),
            JsonIn.obj(json.get("points_quantiles"), NBASimQuantileProfile::fromJson),
            json.get("q1_points_mean").asDouble(),
            json.get("q2_points_mean").asDouble(),
            json.get("q3_points_mean").asDouble(),
            json.get("q4_points_mean").asDouble(),
            json.get("ot_points_mean").asDouble(),
            json.get("win_prob").asDouble(),
            json.get("possessions_mean").asDouble(),
            json.get("offensive_rating_mean").asDouble(),
            json.get("fg_pct_mean").asDouble(),
            json.get("fg2_pct_mean").asDouble(),
            json.get("fg3_pct_mean").asDouble(),
            json.get("ft_pct_mean").asDouble(),
            json.get("fgm_mean").asDouble(),
            json.get("fga_mean").asDouble(),
            json.get("fg2m_mean").asDouble(),
            json.get("fg2a_mean").asDouble(),
            json.get("fg3m_mean").asDouble(),
            json.get("fg3a_mean").asDouble(),
            json.get("ftm_mean").asDouble(),
            json.get("fta_mean").asDouble(),
            json.get("reb_mean").asDouble(),
            json.get("oreb_mean").asDouble(),
            json.get("dreb_mean").asDouble(),
            json.get("ast_mean").asDouble(),
            json.get("stl_mean").asDouble(),
            json.get("blk_mean").asDouble(),
            json.get("ba_mean").asDouble(),
            json.get("tov_mean").asDouble(),
            json.get("pf_mean").asDouble(),
            json.get("fd_mean").asDouble(),
            json.get("fastbreak_pts_mean").asDouble(),
            json.get("paint_pts_mean").asDouble(),
            json.get("second_chance_pts_mean").asDouble(),
            json.get("pts_off_turnovers_mean").asDouble(),
            json.get("starter_pts_mean").asDouble(),
            json.get("bench_pts_mean").asDouble(),
            json.get("biggest_lead_mean").asDouble(),
            json.get("lead_changes_mean").asDouble(),
            json.get("times_tied_mean").asDouble(),
            json.get("pra_mean").asDouble(),
            json.get("dk_pts_mean").asDouble(),
            json.get("fd_pts_mean").asDouble()
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "team_code", teamCode);
        first = JsonOut.field(sb, first, "team_name", teamName);
        first = JsonOut.field(sb, first, "is_home", isHome);
        first = JsonOut.field(sb, first, "vegas_team_total", vegasTeamTotal);
        first = JsonOut.field(sb, first, "points_mean", pointsMean);
        first = JsonOut.field(sb, first, "points_sd", pointsSd);
        first = JsonOut.field(sb, first, "points_p01", pointsP01);
        first = JsonOut.field(sb, first, "points_p05", pointsP05);
        first = JsonOut.field(sb, first, "points_p10", pointsP10);
        first = JsonOut.field(sb, first, "points_p25", pointsP25);
        first = JsonOut.field(sb, first, "points_p50", pointsP50);
        first = JsonOut.field(sb, first, "points_p75", pointsP75);
        first = JsonOut.field(sb, first, "points_p90", pointsP90);
        first = JsonOut.field(sb, first, "points_p95", pointsP95);
        first = JsonOut.field(sb, first, "points_p99", pointsP99);
        first = JsonOut.field(sb, first, "points_quantiles", pointsQuantiles);
        first = JsonOut.field(sb, first, "q1_points_mean", q1PointsMean);
        first = JsonOut.field(sb, first, "q2_points_mean", q2PointsMean);
        first = JsonOut.field(sb, first, "q3_points_mean", q3PointsMean);
        first = JsonOut.field(sb, first, "q4_points_mean", q4PointsMean);
        first = JsonOut.field(sb, first, "ot_points_mean", otPointsMean);
        first = JsonOut.field(sb, first, "win_prob", winProb);
        first = JsonOut.field(sb, first, "possessions_mean", possessionsMean);
        first = JsonOut.field(sb, first, "offensive_rating_mean", offensiveRatingMean);
        first = JsonOut.field(sb, first, "fg_pct_mean", fgPctMean);
        first = JsonOut.field(sb, first, "fg2_pct_mean", fg2PctMean);
        first = JsonOut.field(sb, first, "fg3_pct_mean", fg3PctMean);
        first = JsonOut.field(sb, first, "ft_pct_mean", ftPctMean);
        first = JsonOut.field(sb, first, "fgm_mean", fgmMean);
        first = JsonOut.field(sb, first, "fga_mean", fgaMean);
        first = JsonOut.field(sb, first, "fg2m_mean", fg2mMean);
        first = JsonOut.field(sb, first, "fg2a_mean", fg2aMean);
        first = JsonOut.field(sb, first, "fg3m_mean", fg3mMean);
        first = JsonOut.field(sb, first, "fg3a_mean", fg3aMean);
        first = JsonOut.field(sb, first, "ftm_mean", ftmMean);
        first = JsonOut.field(sb, first, "fta_mean", ftaMean);
        first = JsonOut.field(sb, first, "reb_mean", rebMean);
        first = JsonOut.field(sb, first, "oreb_mean", orebMean);
        first = JsonOut.field(sb, first, "dreb_mean", drebMean);
        first = JsonOut.field(sb, first, "ast_mean", astMean);
        first = JsonOut.field(sb, first, "stl_mean", stlMean);
        first = JsonOut.field(sb, first, "blk_mean", blkMean);
        first = JsonOut.field(sb, first, "ba_mean", baMean);
        first = JsonOut.field(sb, first, "tov_mean", tovMean);
        first = JsonOut.field(sb, first, "pf_mean", pfMean);
        first = JsonOut.field(sb, first, "fd_mean", fdMean);
        first = JsonOut.field(sb, first, "fastbreak_pts_mean", fastbreakPtsMean);
        first = JsonOut.field(sb, first, "paint_pts_mean", paintPtsMean);
        first = JsonOut.field(sb, first, "second_chance_pts_mean", secondChancePtsMean);
        first = JsonOut.field(sb, first, "pts_off_turnovers_mean", ptsOffTurnoversMean);
        first = JsonOut.field(sb, first, "starter_pts_mean", starterPtsMean);
        first = JsonOut.field(sb, first, "bench_pts_mean", benchPtsMean);
        first = JsonOut.field(sb, first, "biggest_lead_mean", biggestLeadMean);
        first = JsonOut.field(sb, first, "lead_changes_mean", leadChangesMean);
        first = JsonOut.field(sb, first, "times_tied_mean", timesTiedMean);
        first = JsonOut.field(sb, first, "pra_mean", praMean);
        first = JsonOut.field(sb, first, "dk_pts_mean", dkPtsMean);
        first = JsonOut.field(sb, first, "fd_pts_mean", fdPtsMean);
        sb.append('}');
        return sb.toString();
    }
}
