// DO NOT EDIT — generated from schema/api/sim/nfl_sim.yml by generate-sim-sdks.ts
package com.statapi;

/**
 * Complete football-native team input profile (pace, play-calling, personnel, O-line, defense, and kicker). The same names and groups serve {@code inputs} and {@code changes.home_team} / {@code changes.away_team}.
 *
 * @param teamId Canonical team ID.
 * @param teamAbbr Team 2/3-letter abbreviation (e.g., {@code KC}, {@code DEN}).
 * @param expectedPlays Expected offensive plays in this game (NFL avg {@code 63.5}).
 * @param neutralPassRate Overall neutral-script dropback pass rate ({@code 0.42..0.72}, NFL avg {@code 0.585}).
 * @param earlyDownPassRate Neutral early-down (1st &amp; 2nd down) pass rate ({@code 0.45..0.70}, NFL avg {@code 0.575}).
 * @param redZonePassRate Red-zone (inside the opponent's 20-yard line) dropback pass rate ({@code 0.35..0.75}, NFL avg {@code 0.555}).
 * @param redZoneTdRate Red-zone (inside the opponent's 20-yard line) touchdown conversion rate per trip ({@code 0.35..0.80}, NFL avg {@code 0.575}).
 * @param trailingPassBoost Increase in dropback pass rate per 7-point (one-touchdown) deficit ({@code 0.0..0.18}, NFL avg {@code 0.077}).
 * @param noHuddleRate Share of neutral offensive snaps run in no-huddle tempo ({@code 0.02..0.35}, NFL avg {@code 0.095}).
 * @param fourthDownGoRate Coaching 4th-down go-for-it rate in makeable situations ({@code 0.05..0.38}, NFL avg {@code 0.180}).
 * @param personnel Named offensive personnel grouping snap shares ({@code p11}, {@code p12}, {@code p21}, {@code p13}, {@code p22}, {@code no_rb}, {@code goal_line}).
 * @param oline Offensive line pass-block grade, run-block grade, and sack rate allowed.
 * @param defense Team defense pass rush, run defense, coverage grades, blitz rate, man coverage rate, and takeaways.
 * @param kicker Placekicker field goal make percentage, maximum range, and PAT make percentage.
 */
public record NFLSimTeam(
    Long teamId,
    String teamAbbr,
    Double expectedPlays,
    Double neutralPassRate,
    Double earlyDownPassRate,
    Double redZonePassRate,
    Double redZoneTdRate,
    Double trailingPassBoost,
    Double noHuddleRate,
    Double fourthDownGoRate,
    NFLSimPersonnel personnel,
    NFLSimOline oline,
    NFLSimDefense defense,
    NFLSimKicker kicker
) implements JsonSerializable {

    public static NFLSimTeam fromJson(JsonValue json) {
        return new NFLSimTeam(
            json.get("team_id").asLong(),
            json.get("team_abbr").asString(),
            json.get("expected_plays").asDouble(),
            json.get("neutral_pass_rate").asDouble(),
            json.get("early_down_pass_rate").asDouble(),
            json.get("red_zone_pass_rate").asDouble(),
            json.get("red_zone_td_rate").asDouble(),
            json.get("trailing_pass_boost").asDouble(),
            json.get("no_huddle_rate").asDouble(),
            json.get("fourth_down_go_rate").asDouble(),
            JsonIn.obj(json.get("personnel"), NFLSimPersonnel::fromJson),
            JsonIn.obj(json.get("oline"), NFLSimOline::fromJson),
            JsonIn.obj(json.get("defense"), NFLSimDefense::fromJson),
            JsonIn.obj(json.get("kicker"), NFLSimKicker::fromJson)
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "team_id", teamId);
        first = JsonOut.field(sb, first, "team_abbr", teamAbbr);
        first = JsonOut.field(sb, first, "expected_plays", expectedPlays);
        first = JsonOut.field(sb, first, "neutral_pass_rate", neutralPassRate);
        first = JsonOut.field(sb, first, "early_down_pass_rate", earlyDownPassRate);
        first = JsonOut.field(sb, first, "red_zone_pass_rate", redZonePassRate);
        first = JsonOut.field(sb, first, "red_zone_td_rate", redZoneTdRate);
        first = JsonOut.field(sb, first, "trailing_pass_boost", trailingPassBoost);
        first = JsonOut.field(sb, first, "no_huddle_rate", noHuddleRate);
        first = JsonOut.field(sb, first, "fourth_down_go_rate", fourthDownGoRate);
        first = JsonOut.field(sb, first, "personnel", personnel);
        first = JsonOut.field(sb, first, "oline", oline);
        first = JsonOut.field(sb, first, "defense", defense);
        first = JsonOut.field(sb, first, "kicker", kicker);
        sb.append('}');
        return sb.toString();
    }
}
