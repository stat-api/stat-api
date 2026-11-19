// DO NOT EDIT — generated from schema/api/sim/nfl_sim.yml by generate-sim-sdks.ts
package com.statapi;

/**
 * Enriched play-by-play snap record inside SimTracedGame.plays.
 *
 * @param event Structured machine {@code PlayEvent} ({@code NFLSimPlayEvent}) with down, distance, field position, player IDs, yards, and scoring flags.
 * @param clockDisplay Formatted quarter and clock string (e.g., {@code Q1 14:22}).
 * @param downDistanceDisplay Formatted down and distance string (e.g., {@code 2nd & 6}).
 * @param yardlineDisplay Formatted field position string (e.g., {@code KC 42}).
 * @param personnelDisplay Offensive personnel grouping code ({@code 11}, {@code 12}, {@code 21}, {@code 13}, {@code 22}, {@code Empty}, {@code GoalLine}).
 * @param description NFL Gamebook play-by-play text (e.g., {@code (14:22) P.Mahomes pass short middle to T.Kelce to KC 48 for 11 yards.}).
 */
public record NFLSimPlayRecord(
    NFLSimPlayEvent event,
    String clockDisplay,
    String downDistanceDisplay,
    String yardlineDisplay,
    String personnelDisplay,
    String description
) implements JsonSerializable {

    public static NFLSimPlayRecord fromJson(JsonValue json) {
        return new NFLSimPlayRecord(
            JsonIn.obj(json.get("event"), NFLSimPlayEvent::fromJson),
            json.get("clock_display").asString(),
            json.get("down_distance_display").asString(),
            json.get("yardline_display").asString(),
            json.get("personnel_display").asString(),
            json.get("description").asString()
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "event", event);
        first = JsonOut.field(sb, first, "clock_display", clockDisplay);
        first = JsonOut.field(sb, first, "down_distance_display", downDistanceDisplay);
        first = JsonOut.field(sb, first, "yardline_display", yardlineDisplay);
        first = JsonOut.field(sb, first, "personnel_display", personnelDisplay);
        first = JsonOut.field(sb, first, "description", description);
        sb.append('}');
        return sb.toString();
    }
}
