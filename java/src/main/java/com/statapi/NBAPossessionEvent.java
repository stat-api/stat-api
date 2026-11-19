// DO NOT EDIT — generated from schema/api/sim/nba_sim.yml by generate-sim-sdks.ts
package com.statapi;

import java.util.List;

/**
 * Chronological possession event inside {@code NBASimTracedGame.possessions}.
 *
 * @param eventIdx 0-based sequential event index.
 * @param period 1-based period number ({@code 1..4} regulation, {@code 5+} OT).
 * @param periodLabel Period label ({@code Q1}..{@code Q4}, {@code OT}).
 * @param clockRemainingSec Seconds remaining in period.
 * @param clockDisplay Formatted game clock.
 * @param offenseTeam Offense team abbreviation.
 * @param defenseTeam Defense team abbreviation.
 * @param playType Play outcome token.
 * @param primaryPlayerId Primary actor player ID.
 * @param primaryPlayerName Primary actor name.
 * @param secondaryPlayerId Secondary actor ID (assister/blocker/rebounder).
 * @param secondaryPlayerName Secondary actor name.
 * @param pointsScored Points scored on event.
 * @param homeScore Home score after event.
 * @param awayScore Away score after event.
 * @param description Human-readable NBA play description.
 * @param playFamily Tactical play family label when recorded.
 * @param shotZone Shot chart zone ({@code Restricted Area}, {@code Paint}, {@code Mid-Range}, {@code Corner 3}, {@code Above Break 3}).
 * @param homeOnCourtIds Five home players on floor.
 * @param awayOnCourtIds Five away players on floor.
 */
public record NBAPossessionEvent(
    Integer eventIdx,
    Integer period,
    String periodLabel,
    Double clockRemainingSec,
    String clockDisplay,
    String offenseTeam,
    String defenseTeam,
    String playType,
    Long primaryPlayerId,
    String primaryPlayerName,
    Long secondaryPlayerId,
    String secondaryPlayerName,
    Integer pointsScored,
    Integer homeScore,
    Integer awayScore,
    String description,
    String playFamily,
    String shotZone,
    List<Long> homeOnCourtIds,
    List<Long> awayOnCourtIds
) implements JsonSerializable {

    public static NBAPossessionEvent fromJson(JsonValue json) {
        return new NBAPossessionEvent(
            json.get("event_idx").asInt(),
            json.get("period").asInt(),
            json.get("period_label").asString(),
            json.get("clock_remaining_sec").asDouble(),
            json.get("clock_display").asString(),
            json.get("offense_team").asString(),
            json.get("defense_team").asString(),
            json.get("play_type").asString(),
            json.get("primary_player_id").asLong(),
            json.get("primary_player_name").asString(),
            json.get("secondary_player_id").asLong(),
            json.get("secondary_player_name").asString(),
            json.get("points_scored").asInt(),
            json.get("home_score").asInt(),
            json.get("away_score").asInt(),
            json.get("description").asString(),
            json.get("play_family").asString(),
            json.get("shot_zone").asString(),
            JsonIn.list(json.get("home_on_court_ids"), JsonValue::asLong),
            JsonIn.list(json.get("away_on_court_ids"), JsonValue::asLong)
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "event_idx", eventIdx);
        first = JsonOut.field(sb, first, "period", period);
        first = JsonOut.field(sb, first, "period_label", periodLabel);
        first = JsonOut.field(sb, first, "clock_remaining_sec", clockRemainingSec);
        first = JsonOut.field(sb, first, "clock_display", clockDisplay);
        first = JsonOut.field(sb, first, "offense_team", offenseTeam);
        first = JsonOut.field(sb, first, "defense_team", defenseTeam);
        first = JsonOut.field(sb, first, "play_type", playType);
        first = JsonOut.field(sb, first, "primary_player_id", primaryPlayerId);
        first = JsonOut.field(sb, first, "primary_player_name", primaryPlayerName);
        first = JsonOut.field(sb, first, "secondary_player_id", secondaryPlayerId);
        first = JsonOut.field(sb, first, "secondary_player_name", secondaryPlayerName);
        first = JsonOut.field(sb, first, "points_scored", pointsScored);
        first = JsonOut.field(sb, first, "home_score", homeScore);
        first = JsonOut.field(sb, first, "away_score", awayScore);
        first = JsonOut.field(sb, first, "description", description);
        first = JsonOut.field(sb, first, "play_family", playFamily);
        first = JsonOut.field(sb, first, "shot_zone", shotZone);
        first = JsonOut.field(sb, first, "home_on_court_ids", homeOnCourtIds);
        first = JsonOut.field(sb, first, "away_on_court_ids", awayOnCourtIds);
        sb.append('}');
        return sb.toString();
    }
}
