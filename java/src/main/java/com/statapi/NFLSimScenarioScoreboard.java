// DO NOT EDIT — generated from schema/api/sim/nfl_sim.yml by generate-sim-sdks.ts
package com.statapi;

/**
 * Pure full-game scoreboard for one scenario ({@code SimGameScenario.scoreboard}).
 *
 * @param quarters Quarter-by-quarter points scored by away and home.
 * @param awayScore Final away team score.
 * @param homeScore Final home team score.
 * @param totalPoints Combined final points ({@code away_score + home_score}).
 * @param homeMargin Final home score margin ({@code home_score - away_score}).
 * @param overtime True when the game went to overtime (omitted when {@code false}).
 */
public record NFLSimScenarioScoreboard(
    NFLSimScenarioQuarters quarters,
    Integer awayScore,
    Integer homeScore,
    Integer totalPoints,
    Integer homeMargin,
    Boolean overtime
) implements JsonSerializable {

    public static NFLSimScenarioScoreboard fromJson(JsonValue json) {
        return new NFLSimScenarioScoreboard(
            JsonIn.obj(json.get("quarters"), NFLSimScenarioQuarters::fromJson),
            json.get("away_score").asInt(),
            json.get("home_score").asInt(),
            json.get("total_points").asInt(),
            json.get("home_margin").asInt(),
            json.get("overtime").asBoolean()
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "quarters", quarters);
        first = JsonOut.field(sb, first, "away_score", awayScore);
        first = JsonOut.field(sb, first, "home_score", homeScore);
        first = JsonOut.field(sb, first, "total_points", totalPoints);
        first = JsonOut.field(sb, first, "home_margin", homeMargin);
        first = JsonOut.field(sb, first, "overtime", overtime);
        sb.append('}');
        return sb.toString();
    }
}
