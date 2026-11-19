// DO NOT EDIT — generated from schema/api/sim/nba_sim.yml by generate-sim-sdks.ts
package com.statapi;

import java.util.Map;

/**
 * Single discrete probability-weighted NBA full-game scenario ({@code scenario_id: 1..K}).
 *
 * @param scenarioId 1-based scenario index ({@code 1} = highest-probability joint-median scenario).
 * @param drawIdx 0-based medoid draw index.
 * @param probability Voronoi cluster probability mass ({@code sum = 1.0}).
 * @param clusterDrawCount Monte Carlo draws assigned to this scenario.
 * @param scoreboard Quarter and final scoreboard.
 * @param teams Sparse team box scores keyed by {@code team_id}.
 * @param players Sparse active player box scores keyed by {@code player_id}.
 * @param playerCorrelations Pairwise rotation correlation matrix.
 * @param pbp Optional nested NBA PBP when {@code scenarios_pbp = true} (omitted when null).
 */
public record NBASimGameScenario(
    Long scenarioId,
    Integer drawIdx,
    Double probability,
    Integer clusterDrawCount,
    NBASimScenarioScoreboard scoreboard,
    Map<String, NBASimTeamBoxScoreStat> teams,
    Map<String, NBASimPlayerBoxScoreStat> players,
    NBASimPlayerCorrelations playerCorrelations,
    NBASimGamePbp pbp
) implements JsonSerializable {

    public static NBASimGameScenario fromJson(JsonValue json) {
        return new NBASimGameScenario(
            json.get("scenario_id").asLong(),
            json.get("draw_idx").asInt(),
            json.get("probability").asDouble(),
            json.get("cluster_draw_count").asInt(),
            JsonIn.obj(json.get("scoreboard"), NBASimScenarioScoreboard::fromJson),
            JsonIn.map(json.get("teams"), NBASimTeamBoxScoreStat::fromJson),
            JsonIn.map(json.get("players"), NBASimPlayerBoxScoreStat::fromJson),
            JsonIn.obj(json.get("player_correlations"), NBASimPlayerCorrelations::fromJson),
            JsonIn.obj(json.get("pbp"), NBASimGamePbp::fromJson)
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
