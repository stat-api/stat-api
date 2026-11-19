// DO NOT EDIT — generated from schema/api/sim/nfl_sim.yml by generate-sim-sdks.ts
package com.statapi;

import java.util.List;
import java.util.Map;

/**
 * Normalized full-game box score scenarios ({@code output.scenarios}): top-level {@code teams} and {@code players} input lookups, plus probability-weighted scenarios starting with {@code scenario_id: 1} (the highest-probability joint-median expected outcome).
 *
 * @param mode Output distribution mode ({@code scenarios}).
 * @param nMonteCarloDraws Number of Monte Carlo draws partitioned into the scenario clusters.
 * @param scenariosReturned Number of representative full-game box score scenarios returned ({@code K}).
 * @param probabilitySum Sum of {@code probability} across all returned scenarios ({@code 1.0000}).
 * @param teams Top-level lookup of team model inputs ({@code NFLTeamFootballInput}) keyed by canonical {@code team_id} string.
 * @param players Top-level lookup of player identity and model traits ({@code SimScenarioPlayerProfile}) keyed by canonical {@code player_id} string.
 * @param scenarios Representative full-game box score scenarios ordered with {@code scenario_id: 1} (highest-probability joint-median expected scenario) first.
 */
public record NFLSimScenariosSection(
    String mode,
    Integer nMonteCarloDraws,
    Integer scenariosReturned,
    Double probabilitySum,
    Map<String, NFLSimTeam> teams,
    Map<String, NFLSimScenarioPlayerProfile> players,
    List<NFLSimGameScenario> scenarios
) implements JsonSerializable {

    public static NFLSimScenariosSection fromJson(JsonValue json) {
        return new NFLSimScenariosSection(
            json.get("mode").asString(),
            json.get("n_monte_carlo_draws").asInt(),
            json.get("scenarios_returned").asInt(),
            json.get("probability_sum").asDouble(),
            JsonIn.map(json.get("teams"), NFLSimTeam::fromJson),
            JsonIn.map(json.get("players"), NFLSimScenarioPlayerProfile::fromJson),
            JsonIn.list(json.get("scenarios"), NFLSimGameScenario::fromJson)
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
