// DO NOT EDIT — generated from schema/api/sim/nba_sim.yml by generate-sim-sdks.ts
package com.statapi;

import java.util.List;
import java.util.Map;

/**
 * Normalized {@code K}-scenario box-score and correlation output ({@code output.scenarios}).
 *
 * @param mode Section mode identifier ({@code scenarios}).
 * @param nMonteCarloDraws Monte Carlo draw count partitioned into scenarios.
 * @param scenariosReturned Number of scenarios returned ({@code K}).
 * @param probabilitySum Sum of scenario probabilities ({@code 1.0000}).
 * @param teams Normalized team profiles keyed by {@code team_id}.
 * @param players Normalized player profiles keyed by {@code player_id}.
 * @param scenarios Probability-weighted scenarios ({@code scenario_id = 1} first).
 */
public record NBASimScenariosSection(
    String mode,
    Integer nMonteCarloDraws,
    Integer scenariosReturned,
    Double probabilitySum,
    Map<String, NBASimScenarioTeamProfile> teams,
    Map<String, NBASimScenarioPlayerProfile> players,
    List<NBASimGameScenario> scenarios
) implements JsonSerializable {

    public static NBASimScenariosSection fromJson(JsonValue json) {
        return new NBASimScenariosSection(
            json.get("mode").asString(),
            json.get("n_monte_carlo_draws").asInt(),
            json.get("scenarios_returned").asInt(),
            json.get("probability_sum").asDouble(),
            JsonIn.map(json.get("teams"), NBASimScenarioTeamProfile::fromJson),
            JsonIn.map(json.get("players"), NBASimScenarioPlayerProfile::fromJson),
            JsonIn.list(json.get("scenarios"), NBASimGameScenario::fromJson)
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "mode", mode);
        first = JsonOut.field(sb, first, "n_monte_carlo_draws", nMonteCarloDraws);
        first = JsonOut.field(sb, first, "scenarios_returned", scenariosReturned);
        first = JsonOut.field(sb, first, "probability_sum", probabilitySum);
        first = JsonOut.field(sb, first, "teams", teams);
        first = JsonOut.field(sb, first, "players", players);
        first = JsonOut.field(sb, first, "scenarios", scenarios);
        sb.append('}');
        return sb.toString();
    }
}
