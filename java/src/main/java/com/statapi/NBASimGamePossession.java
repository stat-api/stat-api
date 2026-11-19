// DO NOT EDIT — generated from schema/api/sim/nba_sim.yml by generate-sim-sdks.ts
package com.statapi;

import java.util.List;

/**
 * Possession summary inside {@code pbp.game_possessions}.
 *
 * @param possessionNumber 1-based possession number.
 * @param period Period number ({@code 1..4} regulation, {@code 5+} OT).
 * @param startClock Period clock when possession began.
 * @param endClock Period clock when possession ended.
 * @param durationSeconds Possession duration in seconds.
 * @param offenseTeamId Offense team ID.
 * @param defenseTeamId Defense team ID.
 * @param startType Possession start trigger.
 * @param result Possession terminal outcome.
 * @param pointsScored Points scored.
 * @param playCount Number of events in possession.
 * @param fga Field goal attempts.
 * @param fgm Field goals made.
 * @param fg3a Three-point attempts.
 * @param fg3m Three-pointers made.
 * @param fta Free throw attempts.
 * @param ftm Free throws made.
 * @param turnover True if possession ended in a turnover.
 * @param offensiveRebounds Offensive rebounds grabbed on possession.
 * @param awayScoreEnd Away score at end of possession.
 * @param homeScoreEnd Home score at end of possession.
 * @param awayLineupIds Five away players on floor.
 * @param homeLineupIds Five home players on floor.
 * @param firstEventId First {@code event_id} in possession.
 * @param lastEventId Last {@code event_id} in possession.
 */
public record NBASimGamePossession(
    Integer possessionNumber,
    Integer period,
    String startClock,
    String endClock,
    Integer durationSeconds,
    Long offenseTeamId,
    Long defenseTeamId,
    String startType,
    String result,
    Integer pointsScored,
    Integer playCount,
    Integer fga,
    Integer fgm,
    Integer fg3a,
    Integer fg3m,
    Integer fta,
    Integer ftm,
    Boolean turnover,
    Integer offensiveRebounds,
    Integer awayScoreEnd,
    Integer homeScoreEnd,
    List<Long> awayLineupIds,
    List<Long> homeLineupIds,
    Long firstEventId,
    Long lastEventId
) implements JsonSerializable {

    public static NBASimGamePossession fromJson(JsonValue json) {
        return new NBASimGamePossession(
            json.get("possession_number").asInt(),
            json.get("period").asInt(),
            json.get("start_clock").asString(),
            json.get("end_clock").asString(),
            json.get("duration_seconds").asInt(),
            json.get("offense_team_id").asLong(),
            json.get("defense_team_id").asLong(),
            json.get("start_type").asString(),
            json.get("result").asString(),
            json.get("points_scored").asInt(),
            json.get("play_count").asInt(),
            json.get("fga").asInt(),
            json.get("fgm").asInt(),
            json.get("fg3a").asInt(),
            json.get("fg3m").asInt(),
            json.get("fta").asInt(),
            json.get("ftm").asInt(),
            json.get("turnover").asBoolean(),
            json.get("offensive_rebounds").asInt(),
            json.get("away_score_end").asInt(),
            json.get("home_score_end").asInt(),
            JsonIn.list(json.get("away_lineup_ids"), JsonValue::asLong),
            JsonIn.list(json.get("home_lineup_ids"), JsonValue::asLong),
            json.get("first_event_id").asLong(),
            json.get("last_event_id").asLong()
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "possession_number", possessionNumber);
        first = JsonOut.field(sb, first, "period", period);
        first = JsonOut.field(sb, first, "start_clock", startClock);
        first = JsonOut.field(sb, first, "end_clock", endClock);
        first = JsonOut.field(sb, first, "duration_seconds", durationSeconds);
        first = JsonOut.field(sb, first, "offense_team_id", offenseTeamId);
        first = JsonOut.field(sb, first, "defense_team_id", defenseTeamId);
        first = JsonOut.field(sb, first, "start_type", startType);
        first = JsonOut.field(sb, first, "result", result);
        first = JsonOut.field(sb, first, "points_scored", pointsScored);
        first = JsonOut.field(sb, first, "play_count", playCount);
        first = JsonOut.field(sb, first, "fga", fga);
        first = JsonOut.field(sb, first, "fgm", fgm);
        first = JsonOut.field(sb, first, "fg3a", fg3a);
        first = JsonOut.field(sb, first, "fg3m", fg3m);
        first = JsonOut.field(sb, first, "fta", fta);
        first = JsonOut.field(sb, first, "ftm", ftm);
        first = JsonOut.field(sb, first, "turnover", turnover);
        first = JsonOut.field(sb, first, "offensive_rebounds", offensiveRebounds);
        first = JsonOut.field(sb, first, "away_score_end", awayScoreEnd);
        first = JsonOut.field(sb, first, "home_score_end", homeScoreEnd);
        first = JsonOut.field(sb, first, "away_lineup_ids", awayLineupIds);
        first = JsonOut.field(sb, first, "home_lineup_ids", homeLineupIds);
        first = JsonOut.field(sb, first, "first_event_id", firstEventId);
        first = JsonOut.field(sb, first, "last_event_id", lastEventId);
        sb.append('}');
        return sb.toString();
    }
}
