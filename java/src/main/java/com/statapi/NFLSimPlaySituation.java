// DO NOT EDIT — generated from schema/api/sim/nfl_sim.yml by generate-sim-sdks.ts
package com.statapi;

/**
 * Pre-snap ({@code start_situation}) or post-snap ({@code end_situation}) field and clock situation on a play.
 *
 * @param quarter Quarter ({@code 1..=4}, {@code 5} = OT).
 * @param clock Quarter clock ({@code MM:SS}).
 * @param quarterSecondsRemaining Seconds remaining in the current quarter ({@code 0..=900}).
 * @param possessionTeamId Canonical {@code team_id} with possession.
 * @param defensiveTeamId Canonical {@code team_id} on defense.
 * @param down Down ({@code 1..=4} on scrimmage plays; {@code null} on kickoffs and PAT/2-pt plays).
 * @param ydstogo Yards to first down or goal ({@code 0} on kickoffs and PAT/2-pt plays).
 * @param yardline100 Distance in yards to opponent's end zone ({@code 1..=99}).
 * @param yardline Formatted yard line string (e.g., {@code BUF 25}, {@code KC 12}, {@code MID 50}).
 * @param goalToGo True when {@code ydstogo >= yardline_100}.
 */
public record NFLSimPlaySituation(
    Integer quarter,
    String clock,
    Integer quarterSecondsRemaining,
    Long possessionTeamId,
    Long defensiveTeamId,
    Integer down,
    Integer ydstogo,
    Integer yardline100,
    String yardline,
    Boolean goalToGo
) implements JsonSerializable {

    public static NFLSimPlaySituation fromJson(JsonValue json) {
        return new NFLSimPlaySituation(
            json.get("quarter").asInt(),
            json.get("clock").asString(),
            json.get("quarter_seconds_remaining").asInt(),
            json.get("possession_team_id").asLong(),
            json.get("defensive_team_id").asLong(),
            json.get("down").asInt(),
            json.get("ydstogo").asInt(),
            json.get("yardline_100").asInt(),
            json.get("yardline").asString(),
            json.get("goal_to_go").asBoolean()
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "quarter", quarter);
        first = JsonOut.field(sb, first, "clock", clock);
        first = JsonOut.field(sb, first, "quarter_seconds_remaining", quarterSecondsRemaining);
        first = JsonOut.field(sb, first, "possession_team_id", possessionTeamId);
        first = JsonOut.field(sb, first, "defensive_team_id", defensiveTeamId);
        first = JsonOut.field(sb, first, "down", down);
        first = JsonOut.field(sb, first, "ydstogo", ydstogo);
        first = JsonOut.field(sb, first, "yardline_100", yardline100);
        first = JsonOut.field(sb, first, "yardline", yardline);
        first = JsonOut.field(sb, first, "goal_to_go", goalToGo);
        sb.append('}');
        return sb.toString();
    }
}
