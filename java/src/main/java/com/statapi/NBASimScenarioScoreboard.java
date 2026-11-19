// DO NOT EDIT — generated from schema/api/sim/nba_sim.yml by generate-sim-sdks.ts
package com.statapi;

/**
 * Scenario and simulated-game final scoreboard.
 *
 * @param quarters Quarter-by-quarter scoring.
 * @param awayScore Final away points.
 * @param homeScore Final home points.
 * @param totalPoints Combined total points.
 * @param homeMargin Home minus away margin.
 * @param overtime True if overtime occurred ({@code omitted when false}).
 * @param overtimePeriods Number of overtime periods played ({@code omitted when 0}).
 */
public record NBASimScenarioScoreboard(
    NBASimScenarioQuarters quarters,
    Integer awayScore,
    Integer homeScore,
    Integer totalPoints,
    Integer homeMargin,
    Boolean overtime,
    Integer overtimePeriods
) implements JsonSerializable {

    public static NBASimScenarioScoreboard fromJson(JsonValue json) {
        return new NBASimScenarioScoreboard(
            JsonIn.obj(json.get("quarters"), NBASimScenarioQuarters::fromJson),
            json.get("away_score").asInt(),
            json.get("home_score").asInt(),
            json.get("total_points").asInt(),
            json.get("home_margin").asInt(),
            json.get("overtime").asBoolean(),
            json.get("overtime_periods").asInt()
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
        first = JsonOut.field(sb, first, "overtime_periods", overtimePeriods);
        sb.append('}');
        return sb.toString();
    }
}
