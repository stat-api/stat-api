// DO NOT EDIT — generated from schema/api/sim/nfl_sim.yml by generate-sim-sdks.ts
package com.statapi;

/**
 * One offensive drive summary in {@code pbp.drives}.
 *
 * @param driveNumber 1-based drive sequence number in the game.
 * @param possessionTeamId Canonical {@code team_id} of the team with possession.
 * @param quarterStart Quarter when the drive started ({@code 1..=5}).
 * @param quarterEnd Quarter when the drive ended ({@code 1..=5}).
 * @param clockStart Game clock at drive start ({@code MM:SS}).
 * @param clockEnd Game clock at drive end ({@code MM:SS}).
 * @param timeOfPossession Drive duration ({@code MM:SS}).
 * @param startYardline Starting field position (e.g., {@code BUF 25}).
 * @param endYardline Ending field position (e.g., {@code KC 12}).
 * @param startReason How possession was gained ({@code KICKOFF}, {@code PUNT}, {@code INTERCEPTION}, {@code FUMBLE}, {@code DOWNS}, {@code MISSED_FG}).
 * @param result Human-readable drive outcome ({@code Touchdown}, {@code Field goal}, {@code Missed field goal}, {@code Punt}, {@code Interception}, {@code Fumble}, {@code Safety}, {@code Turnover on downs}, {@code End of half}, {@code End of game}).
 * @param playCount Number of scrimmage plays in the drive.
 * @param netYards Total net yards gained on the drive.
 * @param firstDowns First downs gained on the drive.
 * @param inside20 True if the drive reached the opponent's red zone ({@code yardline_100 <= 20}).
 * @param scoringDrive True if the drive resulted in offensive/kicking points ({@code Touchdown} or {@code Field goal}).
 * @param firstPlayId {@code play_id} of the first play in the drive.
 * @param lastPlayId {@code play_id} of the last play in the drive.
 */
public record NFLSimGameDrive(
    Integer driveNumber,
    Long possessionTeamId,
    Integer quarterStart,
    Integer quarterEnd,
    String clockStart,
    String clockEnd,
    String timeOfPossession,
    String startYardline,
    String endYardline,
    String startReason,
    String result,
    Integer playCount,
    Integer netYards,
    Integer firstDowns,
    Boolean inside20,
    Boolean scoringDrive,
    Long firstPlayId,
    Long lastPlayId
) implements JsonSerializable {

    public static NFLSimGameDrive fromJson(JsonValue json) {
        return new NFLSimGameDrive(
            json.get("drive_number").asInt(),
            json.get("possession_team_id").asLong(),
            json.get("quarter_start").asInt(),
            json.get("quarter_end").asInt(),
            json.get("clock_start").asString(),
            json.get("clock_end").asString(),
            json.get("time_of_possession").asString(),
            json.get("start_yardline").asString(),
            json.get("end_yardline").asString(),
            json.get("start_reason").asString(),
            json.get("result").asString(),
            json.get("play_count").asInt(),
            json.get("net_yards").asInt(),
            json.get("first_downs").asInt(),
            json.get("inside_20").asBoolean(),
            json.get("scoring_drive").asBoolean(),
            json.get("first_play_id").asLong(),
            json.get("last_play_id").asLong()
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "drive_number", driveNumber);
        first = JsonOut.field(sb, first, "possession_team_id", possessionTeamId);
        first = JsonOut.field(sb, first, "quarter_start", quarterStart);
        first = JsonOut.field(sb, first, "quarter_end", quarterEnd);
        first = JsonOut.field(sb, first, "clock_start", clockStart);
        first = JsonOut.field(sb, first, "clock_end", clockEnd);
        first = JsonOut.field(sb, first, "time_of_possession", timeOfPossession);
        first = JsonOut.field(sb, first, "start_yardline", startYardline);
        first = JsonOut.field(sb, first, "end_yardline", endYardline);
        first = JsonOut.field(sb, first, "start_reason", startReason);
        first = JsonOut.field(sb, first, "result", result);
        first = JsonOut.field(sb, first, "play_count", playCount);
        first = JsonOut.field(sb, first, "net_yards", netYards);
        first = JsonOut.field(sb, first, "first_downs", firstDowns);
        first = JsonOut.field(sb, first, "inside_20", inside20);
        first = JsonOut.field(sb, first, "scoring_drive", scoringDrive);
        first = JsonOut.field(sb, first, "first_play_id", firstPlayId);
        first = JsonOut.field(sb, first, "last_play_id", lastPlayId);
        sb.append('}');
        return sb.toString();
    }
}
