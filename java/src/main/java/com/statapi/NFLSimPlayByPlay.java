// DO NOT EDIT — generated from schema/api/sim/nfl_sim.yml by generate-sim-sdks.ts
package com.statapi;

import java.util.List;

/**
 * One play in {@code pbp.plays} with {@code start_situation}, {@code end_situation}, on-field personnel, route runners, and multi-player {@code play_stats}.
 *
 * @param playId Sequential play identifier within the game ({@code 1..=total_plays}).
 * @param driveNumber 1-based drive number in the game.
 * @param playType Play type ({@code kickoff}, {@code pass}, {@code run}, {@code punt}, {@code field_goal}, {@code extra_point}, {@code two_point_attempt}, {@code qb_kneel}, {@code qb_spike}).
 * @param description Formatted NFL Gamebook play description.
 * @param startSituation Pre-snap field and clock situation.
 * @param endSituation Post-snap field and clock situation.
 * @param offensePersonnel Offensive personnel package ({@code 11}, {@code 12}, {@code 21}, {@code 13}, {@code 22}, {@code empty}, {@code goal_line}).
 * @param defensePackage Defensive sub-package ({@code base_4_3}, {@code nickel_4_2_5}, {@code dime_4_1_6}, {@code goal_line}).
 * @param defendersInBox Number of defenders aligned in the tackle box ({@code 5..=8}).
 * @param shotgun True when offense is in Shotgun formation.
 * @param noHuddle True when offense is in No-Huddle.
 * @param qbDropback True on QB dropbacks ({@code pass}, {@code sack}, {@code scramble}, {@code throwaway}).
 * @param qbScramble True on QB scrambles.
 * @param wasPressure True when the pass rush generated pressure on the QB.
 * @param passLength {@code short} ({@code air_yards < 16}) or {@code deep} ({@code air_yards >= 16}).
 * @param passLocation {@code left}, {@code middle}, or {@code right}.
 * @param airYards Air yards on pass attempts.
 * @param yardsAfterCatch Yards after catch on completions.
 * @param runLocation {@code left}, {@code middle}, or {@code right}.
 * @param runGap {@code end}, {@code guard}, or {@code tackle}.
 * @param yardsGained Net yards gained on the play.
 * @param driveNetYards Cumulative net yards on the drive through this play.
 * @param firstDown True when a first down was achieved.
 * @param touchdown True when a touchdown was scored.
 * @param turnover True when possession turned over via interception or fumble lost.
 * @param sack True when QB was sacked.
 * @param qbHit True when QB was hit.
 * @param tackledForLoss True on a tackle for loss or sack.
 * @param awayScoreBefore Away score before the play.
 * @param homeScoreBefore Home score before the play.
 * @param awayScoreAfter Away score after the play.
 * @param homeScoreAfter Home score after the play.
 * @param onFieldOffenseIds Canonical {@code player_id}s of the active QB and offensive skill players ({@code RB}/{@code WR}/{@code TE}) on the field for this snap (omitted when empty).
 * @param routeRunnerIds Canonical {@code player_id}s of the offensive skill players who ran a pass route on this dropback (omitted when empty).
 * @param playStats Ordered multi-player stat attributions across offense, defense/IDP, and special teams on this play.
 */
public record NFLSimPlayByPlay(
    Long playId,
    Integer driveNumber,
    String playType,
    String description,
    NFLSimPlaySituation startSituation,
    NFLSimPlaySituation endSituation,
    String offensePersonnel,
    String defensePackage,
    Integer defendersInBox,
    Boolean shotgun,
    Boolean noHuddle,
    Boolean qbDropback,
    Boolean qbScramble,
    Boolean wasPressure,
    String passLength,
    String passLocation,
    Integer airYards,
    Integer yardsAfterCatch,
    String runLocation,
    String runGap,
    Integer yardsGained,
    Integer driveNetYards,
    Boolean firstDown,
    Boolean touchdown,
    Boolean turnover,
    Boolean sack,
    Boolean qbHit,
    Boolean tackledForLoss,
    Integer awayScoreBefore,
    Integer homeScoreBefore,
    Integer awayScoreAfter,
    Integer homeScoreAfter,
    List<Long> onFieldOffenseIds,
    List<Long> routeRunnerIds,
    List<NFLSimPlayStat> playStats
) implements JsonSerializable {

    public static NFLSimPlayByPlay fromJson(JsonValue json) {
        return new NFLSimPlayByPlay(
            json.get("play_id").asLong(),
            json.get("drive_number").asInt(),
            json.get("play_type").asString(),
            json.get("description").asString(),
            JsonIn.obj(json.get("start_situation"), NFLSimPlaySituation::fromJson),
            JsonIn.obj(json.get("end_situation"), NFLSimPlaySituation::fromJson),
            json.get("offense_personnel").asString(),
            json.get("defense_package").asString(),
            json.get("defenders_in_box").asInt(),
            json.get("shotgun").asBoolean(),
            json.get("no_huddle").asBoolean(),
            json.get("qb_dropback").asBoolean(),
            json.get("qb_scramble").asBoolean(),
            json.get("was_pressure").asBoolean(),
            json.get("pass_length").asString(),
            json.get("pass_location").asString(),
            json.get("air_yards").asInt(),
            json.get("yards_after_catch").asInt(),
            json.get("run_location").asString(),
            json.get("run_gap").asString(),
            json.get("yards_gained").asInt(),
            json.get("drive_net_yards").asInt(),
            json.get("first_down").asBoolean(),
            json.get("touchdown").asBoolean(),
            json.get("turnover").asBoolean(),
            json.get("sack").asBoolean(),
            json.get("qb_hit").asBoolean(),
            json.get("tackled_for_loss").asBoolean(),
            json.get("away_score_before").asInt(),
            json.get("home_score_before").asInt(),
            json.get("away_score_after").asInt(),
            json.get("home_score_after").asInt(),
            JsonIn.list(json.get("on_field_offense_ids"), JsonValue::asLong),
            JsonIn.list(json.get("route_runner_ids"), JsonValue::asLong),
            JsonIn.list(json.get("play_stats"), NFLSimPlayStat::fromJson)
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "play_id", playId);
        first = JsonOut.field(sb, first, "drive_number", driveNumber);
        first = JsonOut.field(sb, first, "play_type", playType);
        first = JsonOut.field(sb, first, "description", description);
        first = JsonOut.field(sb, first, "start_situation", startSituation);
        first = JsonOut.field(sb, first, "end_situation", endSituation);
        first = JsonOut.field(sb, first, "offense_personnel", offensePersonnel);
        first = JsonOut.field(sb, first, "defense_package", defensePackage);
        first = JsonOut.field(sb, first, "defenders_in_box", defendersInBox);
        first = JsonOut.field(sb, first, "shotgun", shotgun);
        first = JsonOut.field(sb, first, "no_huddle", noHuddle);
        first = JsonOut.field(sb, first, "qb_dropback", qbDropback);
        first = JsonOut.field(sb, first, "qb_scramble", qbScramble);
        first = JsonOut.field(sb, first, "was_pressure", wasPressure);
        first = JsonOut.field(sb, first, "pass_length", passLength);
        first = JsonOut.field(sb, first, "pass_location", passLocation);
        first = JsonOut.field(sb, first, "air_yards", airYards);
        first = JsonOut.field(sb, first, "yards_after_catch", yardsAfterCatch);
        first = JsonOut.field(sb, first, "run_location", runLocation);
        first = JsonOut.field(sb, first, "run_gap", runGap);
        first = JsonOut.field(sb, first, "yards_gained", yardsGained);
        first = JsonOut.field(sb, first, "drive_net_yards", driveNetYards);
        first = JsonOut.field(sb, first, "first_down", firstDown);
        first = JsonOut.field(sb, first, "touchdown", touchdown);
        first = JsonOut.field(sb, first, "turnover", turnover);
        first = JsonOut.field(sb, first, "sack", sack);
        first = JsonOut.field(sb, first, "qb_hit", qbHit);
        first = JsonOut.field(sb, first, "tackled_for_loss", tackledForLoss);
        first = JsonOut.field(sb, first, "away_score_before", awayScoreBefore);
        first = JsonOut.field(sb, first, "home_score_before", homeScoreBefore);
        first = JsonOut.field(sb, first, "away_score_after", awayScoreAfter);
        first = JsonOut.field(sb, first, "home_score_after", homeScoreAfter);
        first = JsonOut.field(sb, first, "on_field_offense_ids", onFieldOffenseIds);
        first = JsonOut.field(sb, first, "route_runner_ids", routeRunnerIds);
        first = JsonOut.field(sb, first, "play_stats", playStats);
        sb.append('}');
        return sb.toString();
    }
}
