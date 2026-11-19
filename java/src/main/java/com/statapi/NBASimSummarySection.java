// DO NOT EDIT — generated from schema/api/sim/nba_sim.yml by generate-sim-sdks.ts
package com.statapi;

import java.util.List;

/**
 * Macro game, team, and player summary statistics across all simulated draws ({@code output.summary}).
 *
 * @param gameId Simulated NBA game ID string.
 * @param season NBA season year.
 * @param gameDate Scheduled game date ({@code YYYY-MM-DD}).
 * @param seasonType Modeled NBA season phase.
 * @param nDraws Number of Monte Carlo draws aggregated.
 * @param seed 64-bit CRN seed.
 * @param vegasMode Effective engine calibration mode.
 * @param vegasSpreadHome Target Vegas home spread.
 * @param vegasTotal Target Vegas game total.
 * @param simTotalMean Simulated mean combined points.
 * @param simTotalSd Simulated standard deviation of combined points.
 * @param totalQuantiles Quantile profile for combined game points.
 * @param simSpreadHomeMean Simulated mean home margin ({@code away - home} spread convention).
 * @param simSpreadHomeSd Simulated standard deviation of margin.
 * @param spreadHomeQuantiles Quantile profile for home margin.
 * @param homeWinProb Simulated home win probability ({@code 0.0..1.0}).
 * @param awayWinProb Simulated away win probability ({@code 0.0..1.0}).
 * @param tieProb Tie probability ({@code 0.0} in NBA after overtime resolution).
 * @param homeCoverProb Probability that home covers {@code vegas_spread_home}.
 * @param overProb Probability that combined points exceed {@code vegas_total}.
 * @param overtimeRate Fraction of simulated draws reaching overtime ({@code 0.0..1.0}).
 * @param possessionsPerTeamMean Mean possessions per team across draws.
 * @param solveElapsedMs Solver elapsed time in milliseconds.
 * @param simElapsedMs Simulation rollout time in milliseconds.
 * @param conservationOk True when {@code Team == Sum(Players)} conservation passes across every draw ({@code C0 == 0}).
 * @param c0MaxDiff Maximum conservation discrepancy across draws ({@code 0.0}).
 * @param calibrationDiagnostics Vegas calibration solver diagnostics.
 * @param homeTeam Home team aggregated projection summary.
 * @param awayTeam Away team aggregated projection summary.
 * @param gamePraMean Combined Points + Rebounds + Assists mean.
 * @param gameDkPtsMean Combined DraftKings NBA fantasy points mean.
 * @param gameFdPtsMean Combined FanDuel NBA fantasy points mean.
 * @param scoreConditionedScaling Game-total quintile breakdown showing how box stats scale with score environment (omitted on wire when empty).
 * @param players Per-player mean, SD, quantile, and milestone summaries.
 * @param coMovement Focal player boom conditional co-movement winners and losers.
 */
public record NBASimSummarySection(
    String gameId,
    Integer season,
    String gameDate,
    String seasonType,
    Integer nDraws,
    Long seed,
    String vegasMode,
    Double vegasSpreadHome,
    Double vegasTotal,
    Double simTotalMean,
    Double simTotalSd,
    NBASimQuantileProfile totalQuantiles,
    Double simSpreadHomeMean,
    Double simSpreadHomeSd,
    NBASimQuantileProfile spreadHomeQuantiles,
    Double homeWinProb,
    Double awayWinProb,
    Double tieProb,
    Double homeCoverProb,
    Double overProb,
    Double overtimeRate,
    Double possessionsPerTeamMean,
    Double solveElapsedMs,
    Double simElapsedMs,
    Boolean conservationOk,
    Double c0MaxDiff,
    NBAVegasCalibrationDiagnostics calibrationDiagnostics,
    NBASimTeamSummary homeTeam,
    NBASimTeamSummary awayTeam,
    Double gamePraMean,
    Double gameDkPtsMean,
    Double gameFdPtsMean,
    List<NBAScoreBinStatProfile> scoreConditionedScaling,
    List<NBASimPlayerSummary> players,
    List<NBACoMovementSummary> coMovement
) implements JsonSerializable {

    public static NBASimSummarySection fromJson(JsonValue json) {
        return new NBASimSummarySection(
            json.get("game_id").asString(),
            json.get("season").asInt(),
            json.get("game_date").asString(),
            json.get("season_type").asString(),
            json.get("n_draws").asInt(),
            json.get("seed").asLong(),
            json.get("vegas_mode").asString(),
            json.get("vegas_spread_home").asDouble(),
            json.get("vegas_total").asDouble(),
            json.get("sim_total_mean").asDouble(),
            json.get("sim_total_sd").asDouble(),
            JsonIn.obj(json.get("total_quantiles"), NBASimQuantileProfile::fromJson),
            json.get("sim_spread_home_mean").asDouble(),
            json.get("sim_spread_home_sd").asDouble(),
            JsonIn.obj(json.get("spread_home_quantiles"), NBASimQuantileProfile::fromJson),
            json.get("home_win_prob").asDouble(),
            json.get("away_win_prob").asDouble(),
            json.get("tie_prob").asDouble(),
            json.get("home_cover_prob").asDouble(),
            json.get("over_prob").asDouble(),
            json.get("overtime_rate").asDouble(),
            json.get("possessions_per_team_mean").asDouble(),
            json.get("solve_elapsed_ms").asDouble(),
            json.get("sim_elapsed_ms").asDouble(),
            json.get("conservation_ok").asBoolean(),
            json.get("c0_max_diff").asDouble(),
            JsonIn.obj(json.get("calibration_diagnostics"), NBAVegasCalibrationDiagnostics::fromJson),
            JsonIn.obj(json.get("home_team"), NBASimTeamSummary::fromJson),
            JsonIn.obj(json.get("away_team"), NBASimTeamSummary::fromJson),
            json.get("game_pra_mean").asDouble(),
            json.get("game_dk_pts_mean").asDouble(),
            json.get("game_fd_pts_mean").asDouble(),
            JsonIn.list(json.get("score_conditioned_scaling"), NBAScoreBinStatProfile::fromJson),
            JsonIn.list(json.get("players"), NBASimPlayerSummary::fromJson),
            JsonIn.list(json.get("co_movement"), NBACoMovementSummary::fromJson)
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "game_id", gameId);
        first = JsonOut.field(sb, first, "season", season);
        first = JsonOut.field(sb, first, "game_date", gameDate);
        first = JsonOut.field(sb, first, "season_type", seasonType);
        first = JsonOut.field(sb, first, "n_draws", nDraws);
        first = JsonOut.field(sb, first, "seed", seed);
        first = JsonOut.field(sb, first, "vegas_mode", vegasMode);
        first = JsonOut.field(sb, first, "vegas_spread_home", vegasSpreadHome);
        first = JsonOut.field(sb, first, "vegas_total", vegasTotal);
        first = JsonOut.field(sb, first, "sim_total_mean", simTotalMean);
        first = JsonOut.field(sb, first, "sim_total_sd", simTotalSd);
        first = JsonOut.field(sb, first, "total_quantiles", totalQuantiles);
        first = JsonOut.field(sb, first, "sim_spread_home_mean", simSpreadHomeMean);
        first = JsonOut.field(sb, first, "sim_spread_home_sd", simSpreadHomeSd);
        first = JsonOut.field(sb, first, "spread_home_quantiles", spreadHomeQuantiles);
        first = JsonOut.field(sb, first, "home_win_prob", homeWinProb);
        first = JsonOut.field(sb, first, "away_win_prob", awayWinProb);
        first = JsonOut.field(sb, first, "tie_prob", tieProb);
        first = JsonOut.field(sb, first, "home_cover_prob", homeCoverProb);
        first = JsonOut.field(sb, first, "over_prob", overProb);
        first = JsonOut.field(sb, first, "overtime_rate", overtimeRate);
        first = JsonOut.field(sb, first, "possessions_per_team_mean", possessionsPerTeamMean);
        first = JsonOut.field(sb, first, "solve_elapsed_ms", solveElapsedMs);
        first = JsonOut.field(sb, first, "sim_elapsed_ms", simElapsedMs);
        first = JsonOut.field(sb, first, "conservation_ok", conservationOk);
        first = JsonOut.field(sb, first, "c0_max_diff", c0MaxDiff);
        first = JsonOut.field(sb, first, "calibration_diagnostics", calibrationDiagnostics);
        first = JsonOut.field(sb, first, "home_team", homeTeam);
        first = JsonOut.field(sb, first, "away_team", awayTeam);
        first = JsonOut.field(sb, first, "game_pra_mean", gamePraMean);
        first = JsonOut.field(sb, first, "game_dk_pts_mean", gameDkPtsMean);
        first = JsonOut.field(sb, first, "game_fd_pts_mean", gameFdPtsMean);
        first = JsonOut.field(sb, first, "score_conditioned_scaling", scoreConditionedScaling);
        first = JsonOut.field(sb, first, "players", players);
        first = JsonOut.field(sb, first, "co_movement", coMovement);
        sb.append('}');
        return sb.toString();
    }
}
