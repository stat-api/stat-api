// DO NOT EDIT — generated from schema/api/sim/nba_sim.yml by generate-sim-sdks.ts
package com.statapi;

import java.util.List;
import java.util.Map;

/**
 * Individual simulated NBA games ({@code output.simulations}).
 *
 * @param nMonteCarloDraws Total Monte Carlo draws simulated.
 * @param simulationsReturned Number of games returned in {@code games}.
 * @param pbpIncluded True when {@code pbp} is embedded per game.
 * @param teams Normalized team profiles keyed by {@code team_id}.
 * @param players Normalized player profiles keyed by {@code player_id}.
 * @param games Individual simulated games.
 */
public record NBASimSimulationsSection(
    Integer nMonteCarloDraws,
    Integer simulationsReturned,
    Boolean pbpIncluded,
    Map<String, NBASimScenarioTeamProfile> teams,
    Map<String, NBASimScenarioPlayerProfile> players,
    List<NBASimSimulatedGame> games
) implements JsonSerializable {

    public static NBASimSimulationsSection fromJson(JsonValue json) {
        return new NBASimSimulationsSection(
            json.get("n_monte_carlo_draws").asInt(),
            json.get("simulations_returned").asInt(),
            json.get("pbp_included").asBoolean(),
            JsonIn.map(json.get("teams"), NBASimScenarioTeamProfile::fromJson),
            JsonIn.map(json.get("players"), NBASimScenarioPlayerProfile::fromJson),
            JsonIn.list(json.get("games"), NBASimSimulatedGame::fromJson)
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "n_monte_carlo_draws", nMonteCarloDraws);
        first = JsonOut.field(sb, first, "simulations_returned", simulationsReturned);
        first = JsonOut.field(sb, first, "pbp_included", pbpIncluded);
        first = JsonOut.field(sb, first, "teams", teams);
        first = JsonOut.field(sb, first, "players", players);
        first = JsonOut.field(sb, first, "games", games);
        sb.append('}');
        return sb.toString();
    }
}
