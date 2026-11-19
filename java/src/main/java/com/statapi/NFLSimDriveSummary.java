// DO NOT EDIT — generated from schema/api/sim/nfl_sim.yml by generate-sim-sdks.ts
package com.statapi;

/**
 * Possession summary row inside SimTracedGame.drives.
 *
 * @param driveNumber 1-based sequential drive number in the game.
 * @param posteamIsHome True if the possessing offense is the home team.
 * @param startQuarter Quarter in which the drive began ({@code 1..5}).
 * @param startClock Game clock ({@code MM:SS}) when the drive started.
 * @param startYardline100 Starting distance from the opponent's end zone ({@code 1..99}; {@code 75} = own 25-yard line).
 * @param endYardline100 Ending distance from the opponent's end zone ({@code 0} = touchdown).
 * @param plays Number of offensive plays run on the drive.
 * @param netYards Net yards gained on the drive.
 * @param firstDowns First downs earned on the drive.
 * @param result Drive outcome ({@code TOUCHDOWN}, {@code FIELD GOAL}, {@code MISSED FG}, {@code PUNT}, {@code INTERCEPTION}, {@code FUMBLE}, {@code DOWNS}, {@code END OF HALF}, {@code END OF GAME}).
 * @param homeScoreAfter Home team score at the conclusion of the drive.
 * @param awayScoreAfter Away team score at the conclusion of the drive.
 */
public record NFLSimDriveSummary(
    Integer driveNumber,
    Boolean posteamIsHome,
    Integer startQuarter,
    String startClock,
    Integer startYardline100,
    Integer endYardline100,
    Integer plays,
    Integer netYards,
    Integer firstDowns,
    String result,
    Integer homeScoreAfter,
    Integer awayScoreAfter
) implements JsonSerializable {

    public static NFLSimDriveSummary fromJson(JsonValue json) {
        return new NFLSimDriveSummary(
            json.get("drive_number").asInt(),
            json.get("posteam_is_home").asBoolean(),
            json.get("start_quarter").asInt(),
            json.get("start_clock").asString(),
            json.get("start_yardline_100").asInt(),
            json.get("end_yardline_100").asInt(),
            json.get("plays").asInt(),
            json.get("net_yards").asInt(),
            json.get("first_downs").asInt(),
            json.get("result").asString(),
            json.get("home_score_after").asInt(),
            json.get("away_score_after").asInt()
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "drive_number", driveNumber);
        first = JsonOut.field(sb, first, "posteam_is_home", posteamIsHome);
        first = JsonOut.field(sb, first, "start_quarter", startQuarter);
        first = JsonOut.field(sb, first, "start_clock", startClock);
        first = JsonOut.field(sb, first, "start_yardline_100", startYardline100);
        first = JsonOut.field(sb, first, "end_yardline_100", endYardline100);
        first = JsonOut.field(sb, first, "plays", plays);
        first = JsonOut.field(sb, first, "net_yards", netYards);
        first = JsonOut.field(sb, first, "first_downs", firstDowns);
        first = JsonOut.field(sb, first, "result", result);
        first = JsonOut.field(sb, first, "home_score_after", homeScoreAfter);
        first = JsonOut.field(sb, first, "away_score_after", awayScoreAfter);
        sb.append('}');
        return sb.toString();
    }
}
