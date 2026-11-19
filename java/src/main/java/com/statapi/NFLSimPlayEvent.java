// DO NOT EDIT — generated from schema/api/sim/nfl_sim.yml by generate-sim-sdks.ts
package com.statapi;

/**
 * Snap-level play event state and outcome inside SimPlayRecord.event.
 *
 * @param gameId Canonical NFL game ID.
 * @param drawIdx 0-based Monte Carlo draw index.
 * @param driveNumber 1-based drive number within the game.
 * @param playNumber 1-based sequential play number within the game.
 * @param quarter Quarter number ({@code 1..5}).
 * @param halfSecondsRemaining Seconds remaining in the current half ({@code 0..1800}).
 * @param gameSecondsRemaining Seconds remaining in regulation ({@code 0..3600}).
 * @param posteamIsHome True if the possessing offense is the home team.
 * @param down Current down ({@code 1..4}).
 * @param ydstogo Yards needed for a first down or goal.
 * @param yardline100 Yards from the opponent's end zone ({@code 1..99}).
 * @param scoreDiff Possessing offense score minus defense score before the snap.
 * @param homeScoreBefore Home team score before the snap.
 * @param awayScoreBefore Away team score before the snap.
 * @param homeScoreAfter Home team score after the snap.
 * @param awayScoreAfter Away team score after the snap.
 * @param playType Play type code ({@code PassComplete}, {@code PassIncomplete}, {@code Sack}, {@code Scramble}, {@code Throwaway}, {@code Rush}, {@code Kneel}, {@code Spike}, {@code FieldGoal}, {@code Punt}, {@code ExtraPoint}, {@code TwoPoint}).
 * @param personnel Offensive personnel index ({@code 0=11}, {@code 1=12}, {@code 2=21}, {@code 3=13}, {@code 4=22}, {@code 5=Empty}, {@code 6=GoalLine}).
 * @param passerId Passer player ID ({@code 0} on non-pass plays).
 * @param rusherId Ball-carrier player ID ({@code 0} on non-rush plays).
 * @param receiverId Targeted receiver player ID ({@code 0} on non-targeted plays).
 * @param yardsGained Net yards gained on the play.
 * @param airYards Pass air yards on targeted pass plays ({@code 0} otherwise).
 * @param yacYards Yards after catch on completed passes ({@code 0} otherwise).
 * @param isFirstDown True if the play earned a first down.
 * @param isTouchdown True if the play resulted in a touchdown.
 * @param isTurnover True if the play resulted in an interception or lost fumble.
 * @param isSack True if the quarterback was sacked.
 * @param isComplete True if a pass was completed.
 * @param isFgGood True if a field goal attempt was made.
 * @param isTwoPtGood True if a two-point conversion succeeded.
 */
public record NFLSimPlayEvent(
    Long gameId,
    Integer drawIdx,
    Integer driveNumber,
    Integer playNumber,
    Integer quarter,
    Integer halfSecondsRemaining,
    Integer gameSecondsRemaining,
    Boolean posteamIsHome,
    Integer down,
    Integer ydstogo,
    Integer yardline100,
    Integer scoreDiff,
    Integer homeScoreBefore,
    Integer awayScoreBefore,
    Integer homeScoreAfter,
    Integer awayScoreAfter,
    String playType,
    Integer personnel,
    Long passerId,
    Long rusherId,
    Long receiverId,
    Integer yardsGained,
    Integer airYards,
    Integer yacYards,
    Boolean isFirstDown,
    Boolean isTouchdown,
    Boolean isTurnover,
    Boolean isSack,
    Boolean isComplete,
    Boolean isFgGood,
    Boolean isTwoPtGood
) implements JsonSerializable {

    public static NFLSimPlayEvent fromJson(JsonValue json) {
        return new NFLSimPlayEvent(
            json.get("game_id").asLong(),
            json.get("draw_idx").asInt(),
            json.get("drive_number").asInt(),
            json.get("play_number").asInt(),
            json.get("quarter").asInt(),
            json.get("half_seconds_remaining").asInt(),
            json.get("game_seconds_remaining").asInt(),
            json.get("posteam_is_home").asBoolean(),
            json.get("down").asInt(),
            json.get("ydstogo").asInt(),
            json.get("yardline_100").asInt(),
            json.get("score_diff").asInt(),
            json.get("home_score_before").asInt(),
            json.get("away_score_before").asInt(),
            json.get("home_score_after").asInt(),
            json.get("away_score_after").asInt(),
            json.get("play_type").asString(),
            json.get("personnel").asInt(),
            json.get("passer_id").asLong(),
            json.get("rusher_id").asLong(),
            json.get("receiver_id").asLong(),
            json.get("yards_gained").asInt(),
            json.get("air_yards").asInt(),
            json.get("yac_yards").asInt(),
            json.get("is_first_down").asBoolean(),
            json.get("is_touchdown").asBoolean(),
            json.get("is_turnover").asBoolean(),
            json.get("is_sack").asBoolean(),
            json.get("is_complete").asBoolean(),
            json.get("is_fg_good").asBoolean(),
            json.get("is_two_pt_good").asBoolean()
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "game_id", gameId);
        first = JsonOut.field(sb, first, "draw_idx", drawIdx);
        first = JsonOut.field(sb, first, "drive_number", driveNumber);
        first = JsonOut.field(sb, first, "play_number", playNumber);
        first = JsonOut.field(sb, first, "quarter", quarter);
        first = JsonOut.field(sb, first, "half_seconds_remaining", halfSecondsRemaining);
        first = JsonOut.field(sb, first, "game_seconds_remaining", gameSecondsRemaining);
        first = JsonOut.field(sb, first, "posteam_is_home", posteamIsHome);
        first = JsonOut.field(sb, first, "down", down);
        first = JsonOut.field(sb, first, "ydstogo", ydstogo);
        first = JsonOut.field(sb, first, "yardline_100", yardline100);
        first = JsonOut.field(sb, first, "score_diff", scoreDiff);
        first = JsonOut.field(sb, first, "home_score_before", homeScoreBefore);
        first = JsonOut.field(sb, first, "away_score_before", awayScoreBefore);
        first = JsonOut.field(sb, first, "home_score_after", homeScoreAfter);
        first = JsonOut.field(sb, first, "away_score_after", awayScoreAfter);
        first = JsonOut.field(sb, first, "play_type", playType);
        first = JsonOut.field(sb, first, "personnel", personnel);
        first = JsonOut.field(sb, first, "passer_id", passerId);
        first = JsonOut.field(sb, first, "rusher_id", rusherId);
        first = JsonOut.field(sb, first, "receiver_id", receiverId);
        first = JsonOut.field(sb, first, "yards_gained", yardsGained);
        first = JsonOut.field(sb, first, "air_yards", airYards);
        first = JsonOut.field(sb, first, "yac_yards", yacYards);
        first = JsonOut.field(sb, first, "is_first_down", isFirstDown);
        first = JsonOut.field(sb, first, "is_touchdown", isTouchdown);
        first = JsonOut.field(sb, first, "is_turnover", isTurnover);
        first = JsonOut.field(sb, first, "is_sack", isSack);
        first = JsonOut.field(sb, first, "is_complete", isComplete);
        first = JsonOut.field(sb, first, "is_fg_good", isFgGood);
        first = JsonOut.field(sb, first, "is_two_pt_good", isTwoPtGood);
        sb.append('}');
        return sb.toString();
    }
}
