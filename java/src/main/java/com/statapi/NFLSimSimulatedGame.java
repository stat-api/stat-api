// DO NOT EDIT — generated from schema/api/sim/nfl_sim.yml by generate-sim-sdks.ts
package com.statapi;

import java.util.Map;

/**
 * One complete simulated NFL game ({@code output.simulations.games[i]}).
 *
 * @param simId 1-based simulated game number ({@code 1..=n_simulations}).
 * @param drawIdx 0-based Monte Carlo draw index ({@code 0..n_draws - 1}).
 * @param scoreboard Quarter linescore, final score, total points, home margin, and overtime flag.
 * @param teams Sparse non-zero team box score stats keyed by canonical {@code team_id} string.
 * @param players Sparse non-zero player offensive, kicking, and IDP box score stats keyed by canonical {@code player_id} string.
 * @param pbp Optional nested Play-by-Play ({@code drives} and {@code plays}) when {@code simulations_pbp = true}.
 */
public record NFLSimSimulatedGame(
    Long simId,
    Integer drawIdx,
    NFLSimScenarioScoreboard scoreboard,
    Map<String, NFLSimTeamBoxScoreStat> teams,
    Map<String, NFLSimPlayerBoxScoreStat> players,
    NFLSimGamePbp pbp
) implements JsonSerializable {

    public static NFLSimSimulatedGame fromJson(JsonValue json) {
        return new NFLSimSimulatedGame(
            json.get("sim_id").asLong(),
            json.get("draw_idx").asInt(),
            JsonIn.obj(json.get("scoreboard"), NFLSimScenarioScoreboard::fromJson),
            JsonIn.map(json.get("teams"), NFLSimTeamBoxScoreStat::fromJson),
            JsonIn.map(json.get("players"), NFLSimPlayerBoxScoreStat::fromJson),
            JsonIn.obj(json.get("pbp"), NFLSimGamePbp::fromJson)
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
