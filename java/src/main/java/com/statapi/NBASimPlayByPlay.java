// DO NOT EDIT — generated from schema/api/sim/nba_sim.yml by generate-sim-sdks.ts
package com.statapi;

import java.util.List;

/**
 * Atomic NBA play-by-play event inside {@code pbp.play_by_plays}.
 *
 * @param eventId Sequential 1-based event ID.
 * @param possessionNumber Parent possession number.
 * @param period Period number.
 * @param clock Formatted game clock ({@code MM:SS}).
 * @param periodSecondsRemaining Seconds left in period.
 * @param shotClock Seconds remaining on 24s/14s shot clock.
 * @param eventType Event type code.
 * @param eventSubType Event subtype code.
 * @param teamId Acting team ID.
 * @param opponentTeamId Opponent team ID.
 * @param playerId Primary actor player ID.
 * @param player2Id Secondary actor player ID (assister/blocker).
 * @param player3Id Tertiary actor player ID.
 * @param pointsScored Points scored on event.
 * @param shotValue Shot attempt point value ({@code 2} or {@code 3}).
 * @param shotMade True when shot fell.
 * @param shotDistanceFt Shot distance from hoop in feet.
 * @param coordX Court X coordinate.
 * @param coordY Court Y coordinate.
 * @param shotZone Shot chart zone.
 * @param isFastbreak True if transition/fastbreak play.
 * @param isSecondChance True if second-chance play.
 * @param isPaint True if shot originated in paint.
 * @param inPenalty True if defense is in bonus/penalty.
 * @param awayScore Away score after event.
 * @param homeScore Home score after event.
 * @param awayFoulsQtr Away team quarter fouls.
 * @param homeFoulsQtr Home team quarter fouls.
 * @param awayTimeoutsRemaining Away team timeouts left.
 * @param homeTimeoutsRemaining Home team timeouts left.
 * @param awayLineupIds Away 5-man floor unit.
 * @param homeLineupIds Home 5-man floor unit.
 * @param playStats Sparse player stat credits for this event.
 * @param description Official-style play description.
 */
public record NBASimPlayByPlay(
    Long eventId,
    Integer possessionNumber,
    Integer period,
    String clock,
    Double periodSecondsRemaining,
    Double shotClock,
    String eventType,
    String eventSubType,
    Long teamId,
    Long opponentTeamId,
    Long playerId,
    Long player2Id,
    Long player3Id,
    Integer pointsScored,
    Integer shotValue,
    Boolean shotMade,
    Double shotDistanceFt,
    Integer coordX,
    Integer coordY,
    String shotZone,
    Boolean isFastbreak,
    Boolean isSecondChance,
    Boolean isPaint,
    Boolean inPenalty,
    Integer awayScore,
    Integer homeScore,
    Integer awayFoulsQtr,
    Integer homeFoulsQtr,
    Integer awayTimeoutsRemaining,
    Integer homeTimeoutsRemaining,
    List<Long> awayLineupIds,
    List<Long> homeLineupIds,
    List<NBASimPlayStatCredit> playStats,
    String description
) implements JsonSerializable {

    public static NBASimPlayByPlay fromJson(JsonValue json) {
        return new NBASimPlayByPlay(
            json.get("event_id").asLong(),
            json.get("possession_number").asInt(),
            json.get("period").asInt(),
            json.get("clock").asString(),
            json.get("period_seconds_remaining").asDouble(),
            json.get("shot_clock").asDouble(),
            json.get("event_type").asString(),
            json.get("event_sub_type").asString(),
            json.get("team_id").asLong(),
            json.get("opponent_team_id").asLong(),
            json.get("player_id").asLong(),
            json.get("player2_id").asLong(),
            json.get("player3_id").asLong(),
            json.get("points_scored").asInt(),
            json.get("shot_value").asInt(),
            json.get("shot_made").asBoolean(),
            json.get("shot_distance_ft").asDouble(),
            json.get("coord_x").asInt(),
            json.get("coord_y").asInt(),
            json.get("shot_zone").asString(),
            json.get("is_fastbreak").asBoolean(),
            json.get("is_second_chance").asBoolean(),
            json.get("is_paint").asBoolean(),
            json.get("in_penalty").asBoolean(),
            json.get("away_score").asInt(),
            json.get("home_score").asInt(),
            json.get("away_fouls_qtr").asInt(),
            json.get("home_fouls_qtr").asInt(),
            json.get("away_timeouts_remaining").asInt(),
            json.get("home_timeouts_remaining").asInt(),
            JsonIn.list(json.get("away_lineup_ids"), JsonValue::asLong),
            JsonIn.list(json.get("home_lineup_ids"), JsonValue::asLong),
            JsonIn.list(json.get("play_stats"), NBASimPlayStatCredit::fromJson),
            json.get("description").asString()
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "event_id", eventId);
        first = JsonOut.field(sb, first, "possession_number", possessionNumber);
        first = JsonOut.field(sb, first, "period", period);
        first = JsonOut.field(sb, first, "clock", clock);
        first = JsonOut.field(sb, first, "period_seconds_remaining", periodSecondsRemaining);
        first = JsonOut.field(sb, first, "shot_clock", shotClock);
        first = JsonOut.field(sb, first, "event_type", eventType);
        first = JsonOut.field(sb, first, "event_sub_type", eventSubType);
        first = JsonOut.field(sb, first, "team_id", teamId);
        first = JsonOut.field(sb, first, "opponent_team_id", opponentTeamId);
        first = JsonOut.field(sb, first, "player_id", playerId);
        first = JsonOut.field(sb, first, "player2_id", player2Id);
        first = JsonOut.field(sb, first, "player3_id", player3Id);
        first = JsonOut.field(sb, first, "points_scored", pointsScored);
        first = JsonOut.field(sb, first, "shot_value", shotValue);
        first = JsonOut.field(sb, first, "shot_made", shotMade);
        first = JsonOut.field(sb, first, "shot_distance_ft", shotDistanceFt);
        first = JsonOut.field(sb, first, "coord_x", coordX);
        first = JsonOut.field(sb, first, "coord_y", coordY);
        first = JsonOut.field(sb, first, "shot_zone", shotZone);
        first = JsonOut.field(sb, first, "is_fastbreak", isFastbreak);
        first = JsonOut.field(sb, first, "is_second_chance", isSecondChance);
        first = JsonOut.field(sb, first, "is_paint", isPaint);
        first = JsonOut.field(sb, first, "in_penalty", inPenalty);
        first = JsonOut.field(sb, first, "away_score", awayScore);
        first = JsonOut.field(sb, first, "home_score", homeScore);
        first = JsonOut.field(sb, first, "away_fouls_qtr", awayFoulsQtr);
        first = JsonOut.field(sb, first, "home_fouls_qtr", homeFoulsQtr);
        first = JsonOut.field(sb, first, "away_timeouts_remaining", awayTimeoutsRemaining);
        first = JsonOut.field(sb, first, "home_timeouts_remaining", homeTimeoutsRemaining);
        first = JsonOut.field(sb, first, "away_lineup_ids", awayLineupIds);
        first = JsonOut.field(sb, first, "home_lineup_ids", homeLineupIds);
        first = JsonOut.field(sb, first, "play_stats", playStats);
        first = JsonOut.field(sb, first, "description", description);
        sb.append('}');
        return sb.toString();
    }
}
