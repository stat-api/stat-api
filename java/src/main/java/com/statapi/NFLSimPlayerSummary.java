// DO NOT EDIT — generated from schema/api/sim/nfl_sim.yml by generate-sim-sdks.ts
package com.statapi;

/**
 * Per-player Monte Carlo summary projections, quantiles, milestone probabilities, fantasy points, and Vegas input medians.
 *
 * @param playerId Canonical player ID.
 * @param teamId Canonical team ID.
 * @param isHome True if on the home team.
 * @param position Skill position ({@code QB}, {@code RB}, {@code WR}, {@code TE}, {@code K}, {@code DST}).
 * @param depthRole Depth chart role ({@code QB1}, {@code RB1}, {@code WR1}, {@code TE1}, etc.).
 * @param marketTier Market calibration tier ({@code RICH_LADDER}, {@code MAIN_LINES}, {@code NO_PROPS_SHRINKAGE}).
 * @param pActive Fraction of simulated draws in which the player was active ({@code 0.0..1.0}).
 * @param snapsMean Mean offensive snaps played.
 * @param snapsP50 Median offensive snaps played.
 * @param routesMean Mean pass routes run.
 * @param passCmpMean Mean pass completions.
 * @param passAttMean Mean pass attempts.
 * @param passYdMean Mean passing yards.
 * @param passYdSd Standard deviation of passing yards.
 * @param passYdP10 10th percentile passing yards.
 * @param passYdP50 Median (50th percentile) passing yards.
 * @param passYdP90 90th percentile passing yards.
 * @param passTdMean Mean passing touchdowns.
 * @param intMean Mean interceptions thrown.
 * @param longestPassP50 Median longest completion in yards.
 * @param carriesMean Mean total rushing attempts ({@code designed_rushes + scrambles + kneels}).
 * @param designedRushesMean Mean designed rushing attempts.
 * @param scramblesMean Mean QB scramble rushes.
 * @param rushYdMean Mean rushing yards.
 * @param rushYdSd Standard deviation of rushing yards.
 * @param rushYdP10 10th percentile rushing yards.
 * @param rushYdP50 Median (50th percentile) rushing yards.
 * @param rushYdP90 90th percentile rushing yards.
 * @param rushTdMean Mean rushing touchdowns.
 * @param longestRushP50 Median longest rush in yards.
 * @param targetsMean Mean receiving targets.
 * @param receptionsMean Mean receptions.
 * @param recYdMean Mean receiving yards.
 * @param recYdSd Standard deviation of receiving yards.
 * @param recYdP10 10th percentile receiving yards.
 * @param recYdP50 Median (50th percentile) receiving yards.
 * @param recYdP90 90th percentile receiving yards.
 * @param recTdMean Mean receiving touchdowns.
 * @param airYdMean Mean target air yards.
 * @param yacYdMean Mean yards after catch (YAC).
 * @param longestRecP50 Median longest reception in yards.
 * @param fgMadeMean Mean field goals made.
 * @param fgAttMean Mean field goals attempted.
 * @param xpMadeMean Mean extra points (PATs) made.
 * @param kickPtsMean Mean kicking points ({@code 3 * fg_made + xp_made}).
 * @param fumblesLostMean Mean fumbles lost.
 * @param pAnytimeTd Probability of scoring 1+ rushing or receiving touchdowns ({@code 0.0..1.0}).
 * @param p2plusTd Probability of scoring 2+ rushing or receiving touchdowns ({@code 0.0..1.0}).
 * @param pPass300yd Probability of recording 300+ passing yards ({@code 0.0..1.0}).
 * @param pRush100yd Probability of recording 100+ rushing yards ({@code 0.0..1.0}).
 * @param pRec100yd Probability of recording 100+ receiving yards ({@code 0.0..1.0}).
 * @param vegasPassYd Input Vegas passing yards median for direct calibration comparison.
 * @param vegasPassAtt Input Vegas pass attempts median.
 * @param vegasRushYd Input Vegas rushing yards median.
 * @param vegasCarries Input Vegas rushing attempts median.
 * @param vegasRecYd Input Vegas receiving yards median.
 * @param vegasReceptions Input Vegas receptions median.
 * @param vegasTdProb Input de-vigged Vegas anytime TD probability.
 */
public record NFLSimPlayerSummary(
    Long playerId,
    Long teamId,
    Boolean isHome,
    String position,
    String depthRole,
    String marketTier,
    Double pActive,
    Double snapsMean,
    Double snapsP50,
    Double routesMean,
    Double passCmpMean,
    Double passAttMean,
    Double passYdMean,
    Double passYdSd,
    Double passYdP10,
    Double passYdP50,
    Double passYdP90,
    Double passTdMean,
    Double intMean,
    Double longestPassP50,
    Double carriesMean,
    Double designedRushesMean,
    Double scramblesMean,
    Double rushYdMean,
    Double rushYdSd,
    Double rushYdP10,
    Double rushYdP50,
    Double rushYdP90,
    Double rushTdMean,
    Double longestRushP50,
    Double targetsMean,
    Double receptionsMean,
    Double recYdMean,
    Double recYdSd,
    Double recYdP10,
    Double recYdP50,
    Double recYdP90,
    Double recTdMean,
    Double airYdMean,
    Double yacYdMean,
    Double longestRecP50,
    Double fgMadeMean,
    Double fgAttMean,
    Double xpMadeMean,
    Double kickPtsMean,
    Double fumblesLostMean,
    Double pAnytimeTd,
    Double p2plusTd,
    Double pPass300yd,
    Double pRush100yd,
    Double pRec100yd,
    Double vegasPassYd,
    Double vegasPassAtt,
    Double vegasRushYd,
    Double vegasCarries,
    Double vegasRecYd,
    Double vegasReceptions,
    Double vegasTdProb
) implements JsonSerializable {

    public static NFLSimPlayerSummary fromJson(JsonValue json) {
        return new NFLSimPlayerSummary(
            json.get("player_id").asLong(),
            json.get("team_id").asLong(),
            json.get("is_home").asBoolean(),
            json.get("position").asString(),
            json.get("depth_role").asString(),
            json.get("market_tier").asString(),
            json.get("p_active").asDouble(),
            json.get("snaps_mean").asDouble(),
            json.get("snaps_p50").asDouble(),
            json.get("routes_mean").asDouble(),
            json.get("pass_cmp_mean").asDouble(),
            json.get("pass_att_mean").asDouble(),
            json.get("pass_yd_mean").asDouble(),
            json.get("pass_yd_sd").asDouble(),
            json.get("pass_yd_p10").asDouble(),
            json.get("pass_yd_p50").asDouble(),
            json.get("pass_yd_p90").asDouble(),
            json.get("pass_td_mean").asDouble(),
            json.get("int_mean").asDouble(),
            json.get("longest_pass_p50").asDouble(),
            json.get("carries_mean").asDouble(),
            json.get("designed_rushes_mean").asDouble(),
            json.get("scrambles_mean").asDouble(),
            json.get("rush_yd_mean").asDouble(),
            json.get("rush_yd_sd").asDouble(),
            json.get("rush_yd_p10").asDouble(),
            json.get("rush_yd_p50").asDouble(),
            json.get("rush_yd_p90").asDouble(),
            json.get("rush_td_mean").asDouble(),
            json.get("longest_rush_p50").asDouble(),
            json.get("targets_mean").asDouble(),
            json.get("receptions_mean").asDouble(),
            json.get("rec_yd_mean").asDouble(),
            json.get("rec_yd_sd").asDouble(),
            json.get("rec_yd_p10").asDouble(),
            json.get("rec_yd_p50").asDouble(),
            json.get("rec_yd_p90").asDouble(),
            json.get("rec_td_mean").asDouble(),
            json.get("air_yd_mean").asDouble(),
            json.get("yac_yd_mean").asDouble(),
            json.get("longest_rec_p50").asDouble(),
            json.get("fg_made_mean").asDouble(),
            json.get("fg_att_mean").asDouble(),
            json.get("xp_made_mean").asDouble(),
            json.get("kick_pts_mean").asDouble(),
            json.get("fumbles_lost_mean").asDouble(),
            json.get("p_anytime_td").asDouble(),
            json.get("p_2plus_td").asDouble(),
            json.get("p_pass_300yd").asDouble(),
            json.get("p_rush_100yd").asDouble(),
            json.get("p_rec_100yd").asDouble(),
            json.get("vegas_pass_yd").asDouble(),
            json.get("vegas_pass_att").asDouble(),
            json.get("vegas_rush_yd").asDouble(),
            json.get("vegas_carries").asDouble(),
            json.get("vegas_rec_yd").asDouble(),
            json.get("vegas_receptions").asDouble(),
            json.get("vegas_td_prob").asDouble()
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "player_id", playerId);
        first = JsonOut.field(sb, first, "team_id", teamId);
        first = JsonOut.field(sb, first, "is_home", isHome);
        first = JsonOut.field(sb, first, "position", position);
        first = JsonOut.field(sb, first, "depth_role", depthRole);
        first = JsonOut.field(sb, first, "market_tier", marketTier);
        first = JsonOut.field(sb, first, "p_active", pActive);
        first = JsonOut.field(sb, first, "snaps_mean", snapsMean);
        first = JsonOut.field(sb, first, "snaps_p50", snapsP50);
        first = JsonOut.field(sb, first, "routes_mean", routesMean);
        first = JsonOut.field(sb, first, "pass_cmp_mean", passCmpMean);
        first = JsonOut.field(sb, first, "pass_att_mean", passAttMean);
        first = JsonOut.field(sb, first, "pass_yd_mean", passYdMean);
        first = JsonOut.field(sb, first, "pass_yd_sd", passYdSd);
        first = JsonOut.field(sb, first, "pass_yd_p10", passYdP10);
        first = JsonOut.field(sb, first, "pass_yd_p50", passYdP50);
        first = JsonOut.field(sb, first, "pass_yd_p90", passYdP90);
        first = JsonOut.field(sb, first, "pass_td_mean", passTdMean);
        first = JsonOut.field(sb, first, "int_mean", intMean);
        first = JsonOut.field(sb, first, "longest_pass_p50", longestPassP50);
        first = JsonOut.field(sb, first, "carries_mean", carriesMean);
        first = JsonOut.field(sb, first, "designed_rushes_mean", designedRushesMean);
        first = JsonOut.field(sb, first, "scrambles_mean", scramblesMean);
        first = JsonOut.field(sb, first, "rush_yd_mean", rushYdMean);
        first = JsonOut.field(sb, first, "rush_yd_sd", rushYdSd);
        first = JsonOut.field(sb, first, "rush_yd_p10", rushYdP10);
        first = JsonOut.field(sb, first, "rush_yd_p50", rushYdP50);
        first = JsonOut.field(sb, first, "rush_yd_p90", rushYdP90);
        first = JsonOut.field(sb, first, "rush_td_mean", rushTdMean);
        first = JsonOut.field(sb, first, "longest_rush_p50", longestRushP50);
        first = JsonOut.field(sb, first, "targets_mean", targetsMean);
        first = JsonOut.field(sb, first, "receptions_mean", receptionsMean);
        first = JsonOut.field(sb, first, "rec_yd_mean", recYdMean);
        first = JsonOut.field(sb, first, "rec_yd_sd", recYdSd);
        first = JsonOut.field(sb, first, "rec_yd_p10", recYdP10);
        first = JsonOut.field(sb, first, "rec_yd_p50", recYdP50);
        first = JsonOut.field(sb, first, "rec_yd_p90", recYdP90);
        first = JsonOut.field(sb, first, "rec_td_mean", recTdMean);
        first = JsonOut.field(sb, first, "air_yd_mean", airYdMean);
        first = JsonOut.field(sb, first, "yac_yd_mean", yacYdMean);
        first = JsonOut.field(sb, first, "longest_rec_p50", longestRecP50);
        first = JsonOut.field(sb, first, "fg_made_mean", fgMadeMean);
        first = JsonOut.field(sb, first, "fg_att_mean", fgAttMean);
        first = JsonOut.field(sb, first, "xp_made_mean", xpMadeMean);
        first = JsonOut.field(sb, first, "kick_pts_mean", kickPtsMean);
        first = JsonOut.field(sb, first, "fumbles_lost_mean", fumblesLostMean);
        first = JsonOut.field(sb, first, "p_anytime_td", pAnytimeTd);
        first = JsonOut.field(sb, first, "p_2plus_td", p2plusTd);
        first = JsonOut.field(sb, first, "p_pass_300yd", pPass300yd);
        first = JsonOut.field(sb, first, "p_rush_100yd", pRush100yd);
        first = JsonOut.field(sb, first, "p_rec_100yd", pRec100yd);
        first = JsonOut.field(sb, first, "vegas_pass_yd", vegasPassYd);
        first = JsonOut.field(sb, first, "vegas_pass_att", vegasPassAtt);
        first = JsonOut.field(sb, first, "vegas_rush_yd", vegasRushYd);
        first = JsonOut.field(sb, first, "vegas_carries", vegasCarries);
        first = JsonOut.field(sb, first, "vegas_rec_yd", vegasRecYd);
        first = JsonOut.field(sb, first, "vegas_receptions", vegasReceptions);
        first = JsonOut.field(sb, first, "vegas_td_prob", vegasTdProb);
        sb.append('}');
        return sb.toString();
    }
}
