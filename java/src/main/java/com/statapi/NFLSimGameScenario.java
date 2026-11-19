// DO NOT EDIT — generated from schema/api/sim/nfl_sim.yml by generate-sim-sdks.ts
package com.statapi;

import java.util.Map;

/**
 * One pure full-game box score scenario ({@code scenario_id: 1} is the highest-probability joint-median expected scenario).
 *
 * @param scenarioId 1-based scenario index ({@code 1..scenarios_returned}; {@code 1} is the joint-median expected scenario).
 * @param drawIdx 0-based Monte Carlo medoid draw index (pass in {@code include.pbp_draws} on {@code POST /api/v1/sim/nfl/run} for full play-by-play).
 * @param probability Voronoi cluster probability mass ({@code cluster_draw_count / n_monte_carlo_draws}; highest on {@code scenario_id: 1}).
 * @param clusterDrawCount Number of Monte Carlo draws assigned to this scenario's Voronoi cell.
 * @param scoreboard Quarter linescore, final score, total points, and home margin.
 * @param teams Sparse non-zero team box score stats keyed by canonical {@code team_id} string.
 * @param players Sparse non-zero player football box score stats keyed by canonical {@code player_id} string (omits players with zero stats).
 * @param playerCorrelations Pairwise player correlation matrix for this scenario ({@code scenario_id: 1} is the baseline expected correlation; {@code scenario_id >= 2} is the regime-conditioned local correlation).
 * @param pbp Optional nested Play-by-Play ({@code drives} and {@code plays} with {@code play_stats}) for this scenario's representative medoid draw ({@code draw_idx}) when {@code scenarios_pbp = true}.
 */
public record NFLSimGameScenario(
    Long scenarioId,
    Integer drawIdx,
    Double probability,
    Integer clusterDrawCount,
    NFLSimScenarioScoreboard scoreboard,
    Map<String, NFLSimTeamBoxScoreStat> teams,
    Map<String, NFLSimPlayerBoxScoreStat> players,
    NFLSimPlayerCorrelations playerCorrelations,
    NFLSimGamePbp pbp
) implements JsonSerializable {

    public static NFLSimGameScenario fromJson(JsonValue json) {
        return new NFLSimGameScenario(
            json.get("scenario_id").asLong(),
            json.get("draw_idx").asInt(),
            json.get("probability").asDouble(),
            json.get("cluster_draw_count").asInt(),
            JsonIn.obj(json.get("scoreboard"), NFLSimScenarioScoreboard::fromJson),
            JsonIn.map(json.get("teams"), NFLSimTeamBoxScoreStat::fromJson),
            JsonIn.map(json.get("players"), NFLSimPlayerBoxScoreStat::fromJson),
            JsonIn.obj(json.get("player_correlations"), NFLSimPlayerCorrelations::fromJson),
            JsonIn.obj(json.get("pbp"), NFLSimGamePbp::fromJson)
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "scenario_id", scenarioId);
        first = JsonOut.field(sb, first, "draw_idx", drawIdx);
        first = JsonOut.field(sb, first, "probability", probability);
        first = JsonOut.field(sb, first, "cluster_draw_count", clusterDrawCount);
        first = JsonOut.field(sb, first, "scoreboard", scoreboard);
        first = JsonOut.field(sb, first, "teams", teams);
        first = JsonOut.field(sb, first, "players", players);
        first = JsonOut.field(sb, first, "player_correlations", playerCorrelations);
        first = JsonOut.field(sb, first, "pbp", pbp);
        sb.append('}');
        return sb.toString();
    }
}
