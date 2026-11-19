// DO NOT EDIT — generated from schema/api/sim/nfl_sim.yml by generate-sim-sdks.ts
package com.statapi;

import java.util.List;

/**
 * Macro game, team, and player summary statistics across all n_draws ({@code output.summary}).
 *
 * @param gameId Canonical game ID.
 * @param season NFL season year.
 * @param week NFL week number.
 * @param nDraws Number of simulated draws aggregated.
 * @param seed 64-bit CRN seed.
 * @param hasVegasLine True if calibrated to Vegas lines; false in Zero-Vegas Mode 0.
 * @param vegasSpreadHome Input Vegas home spread.
 * @param vegasTotal Input Vegas game total.
 * @param simTotalMean Simulated mean combined points across all draws.
 * @param simTotalSd Simulated standard deviation of combined points.
 * @param simSpreadHomeMean Simulated mean home spread ({@code away_pts - home_pts}).
 * @param simSpreadHomeSd Simulated standard deviation of score margin.
 * @param homeWinProb Simulated home win probability ({@code 0.0..1.0}).
 * @param awayWinProb Simulated away win probability ({@code 0.0..1.0}).
 * @param tieProb Simulated tie probability after overtime ({@code 0.0..1.0}).
 * @param homeCoverProb Probability that the home team covers {@code vegas_spread_home} (excluding pushes).
 * @param overProb Probability that combined points exceed {@code vegas_total} (excluding pushes).
 * @param overtimeRate Fraction of simulated games that went to overtime ({@code 0.0..1.0}).
 * @param solveElapsedMs Pre-sim CRN Vegas solver runtime in milliseconds.
 * @param simElapsedMs Parallel play-by-play simulation runtime in milliseconds.
 * @param conservationOk True when 100% of simulated draws satisfied every C0 physical conservation identity.
 * @param c0MaxDiff Maximum absolute discrepancy across all C0 conservation identities ({@code 0.0} when exact).
 * @param homeTeam Aggregated home team summary statistics.
 * @param awayTeam Aggregated away team summary statistics.
 * @param players Aggregated per-player summary projections, quantiles, milestones, and Vegas medians.
 */
public record NFLSimSummarySection(
    Long gameId,
    Integer season,
    Integer week,
    Integer nDraws,
    Long seed,
    Boolean hasVegasLine,
    Double vegasSpreadHome,
    Double vegasTotal,
    Double simTotalMean,
    Double simTotalSd,
    Double simSpreadHomeMean,
    Double simSpreadHomeSd,
    Double homeWinProb,
    Double awayWinProb,
    Double tieProb,
    Double homeCoverProb,
    Double overProb,
    Double overtimeRate,
    Double solveElapsedMs,
    Double simElapsedMs,
    Boolean conservationOk,
    Double c0MaxDiff,
    NFLSimTeamSummary homeTeam,
    NFLSimTeamSummary awayTeam,
    List<NFLSimPlayerSummary> players
) implements JsonSerializable {

    public static NFLSimSummarySection fromJson(JsonValue json) {
        return new NFLSimSummarySection(
            json.get("game_id").asLong(),
            json.get("season").asInt(),
            json.get("week").asInt(),
            json.get("n_draws").asInt(),
            json.get("seed").asLong(),
            json.get("has_vegas_line").asBoolean(),
            json.get("vegas_spread_home").asDouble(),
            json.get("vegas_total").asDouble(),
            json.get("sim_total_mean").asDouble(),
            json.get("sim_total_sd").asDouble(),
            json.get("sim_spread_home_mean").asDouble(),
            json.get("sim_spread_home_sd").asDouble(),
            json.get("home_win_prob").asDouble(),
            json.get("away_win_prob").asDouble(),
            json.get("tie_prob").asDouble(),
            json.get("home_cover_prob").asDouble(),
            json.get("over_prob").asDouble(),
            json.get("overtime_rate").asDouble(),
            json.get("solve_elapsed_ms").asDouble(),
            json.get("sim_elapsed_ms").asDouble(),
            json.get("conservation_ok").asBoolean(),
            json.get("c0_max_diff").asDouble(),
            JsonIn.obj(json.get("home_team"), NFLSimTeamSummary::fromJson),
            JsonIn.obj(json.get("away_team"), NFLSimTeamSummary::fromJson),
            JsonIn.list(json.get("players"), NFLSimPlayerSummary::fromJson)
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "game_id", gameId);
        first = JsonOut.field(sb, first, "season", season);
        first = JsonOut.field(sb, first, "week", week);
        first = JsonOut.field(sb, first, "n_draws", nDraws);
        first = JsonOut.field(sb, first, "seed", seed);
        first = JsonOut.field(sb, first, "has_vegas_line", hasVegasLine);
        first = JsonOut.field(sb, first, "vegas_spread_home", vegasSpreadHome);
        first = JsonOut.field(sb, first, "vegas_total", vegasTotal);
        first = JsonOut.field(sb, first, "sim_total_mean", simTotalMean);
        first = JsonOut.field(sb, first, "sim_total_sd", simTotalSd);
        first = JsonOut.field(sb, first, "sim_spread_home_mean", simSpreadHomeMean);
        first = JsonOut.field(sb, first, "sim_spread_home_sd", simSpreadHomeSd);
        first = JsonOut.field(sb, first, "home_win_prob", homeWinProb);
        first = JsonOut.field(sb, first, "away_win_prob", awayWinProb);
        first = JsonOut.field(sb, first, "tie_prob", tieProb);
        first = JsonOut.field(sb, first, "home_cover_prob", homeCoverProb);
        first = JsonOut.field(sb, first, "over_prob", overProb);
        first = JsonOut.field(sb, first, "overtime_rate", overtimeRate);
        first = JsonOut.field(sb, first, "solve_elapsed_ms", solveElapsedMs);
        first = JsonOut.field(sb, first, "sim_elapsed_ms", simElapsedMs);
        first = JsonOut.field(sb, first, "conservation_ok", conservationOk);
        first = JsonOut.field(sb, first, "c0_max_diff", c0MaxDiff);
        first = JsonOut.field(sb, first, "home_team", homeTeam);
        first = JsonOut.field(sb, first, "away_team", awayTeam);
        first = JsonOut.field(sb, first, "players", players);
        sb.append('}');
        return sb.toString();
    }
}
