// DO NOT EDIT — generated from schema/api/sim/nba_sim.yml by generate-sim-sdks.ts
package com.statapi;

import java.util.Map;

/**
 * Individual simulated NBA game inside {@code output.simulations.games}.
 *
 * @param simId 1-based simulated game index.
 * @param drawIdx 0-based Monte Carlo draw index.
 * @param scoreboard Quarter and final scoreboard.
 * @param teams Sparse team box scores keyed by {@code team_id}.
 * @param players Sparse active player box scores keyed by {@code player_id}.
 * @param pbp Optional nested official NBA PBP when {@code simulations_pbp = true} (omitted when null).
 */
public record NBASimSimulatedGame(
    Long simId,
    Integer drawIdx,
    NBASimScenarioScoreboard scoreboard,
    Map<String, NBASimTeamBoxScoreStat> teams,
    Map<String, NBASimPlayerBoxScoreStat> players,
    NBASimGamePbp pbp
) implements JsonSerializable {

    public static NBASimSimulatedGame fromJson(JsonValue json) {
        return new NBASimSimulatedGame(
            json.get("sim_id").asLong(),
            json.get("draw_idx").asInt(),
            JsonIn.obj(json.get("scoreboard"), NBASimScenarioScoreboard::fromJson),
            JsonIn.map(json.get("teams"), NBASimTeamBoxScoreStat::fromJson),
            JsonIn.map(json.get("players"), NBASimPlayerBoxScoreStat::fromJson),
            JsonIn.obj(json.get("pbp"), NBASimGamePbp::fromJson)
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "sim_id", simId);
        first = JsonOut.field(sb, first, "draw_idx", drawIdx);
        first = JsonOut.field(sb, first, "scoreboard", scoreboard);
        first = JsonOut.field(sb, first, "teams", teams);
        first = JsonOut.field(sb, first, "players", players);
        first = JsonOut.field(sb, first, "pbp", pbp);
        sb.append('}');
        return sb.toString();
    }
}
