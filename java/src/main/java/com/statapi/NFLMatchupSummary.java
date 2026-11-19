// DO NOT EDIT — generated from schema/api/sim/nfl_sim.yml by generate-sim-sdks.ts
package com.statapi;

/**
 * Matchup header and pre-kickoff Vegas line summary for a single NFL game.
 *
 * @param gameId Unique NFL game identifier ({@code 43418} = {@code 2026 Week 1 DEN @ KC}).
 * @param season NFL season year.
 * @param week NFL week number.
 * @param seasonType Season phase ({@code regular_season} or {@code postseason}).
 * @param kickoff Scheduled UTC kickoff timestamp (ISO-8601).
 * @param day Game date integer in {@code YYYYMMDD} format.
 * @param homeTeamId Canonical home team ID.
 * @param homeAbbr Home team 2/3-letter abbreviation (e.g., {@code KC}).
 * @param awayTeamId Canonical away team ID.
 * @param awayAbbr Away team 2/3-letter abbreviation (e.g., {@code DEN}).
 * @param spreadHome Pre-kickoff Vegas home spread in points (negative means home is favored, e.g. {@code -3.0}).
 * @param gameTotal Pre-kickoff Vegas over/under total points (e.g., {@code 47.5}).
 * @param homeTeamTotal Implied pre-kickoff home team total points ({@code (game_total - spread_home) / 2}).
 * @param awayTeamTotal Implied pre-kickoff away team total points ({@code (game_total + spread_home) / 2}).
 * @param moneylineHome Pre-kickoff American moneyline for the home team (e.g., {@code -162}).
 * @param moneylineAway Pre-kickoff American moneyline for the away team (e.g., {@code +136}).
 * @param isFinal True if the real NFL game has completed.
 * @param realHomePts Actual final home points scored (populated when {@code is_final = true}).
 * @param realAwayPts Actual final away points scored (populated when {@code is_final = true}).
 * @param isFree True when this game is unlocked for free full-depth simulation ({@code game_id == 43418}).
 */
public record NFLMatchupSummary(
    Long gameId,
    Integer season,
    Integer week,
    String seasonType,
    String kickoff,
    Integer day,
    Long homeTeamId,
    String homeAbbr,
    Long awayTeamId,
    String awayAbbr,
    Double spreadHome,
    Double gameTotal,
    Double homeTeamTotal,
    Double awayTeamTotal,
    Integer moneylineHome,
    Integer moneylineAway,
    Boolean isFinal,
    Integer realHomePts,
    Integer realAwayPts,
    Boolean isFree
) implements JsonSerializable {

    public static NFLMatchupSummary fromJson(JsonValue json) {
        return new NFLMatchupSummary(
            json.get("game_id").asLong(),
            json.get("season").asInt(),
            json.get("week").asInt(),
            json.get("season_type").asString(),
            json.get("kickoff").asString(),
            json.get("day").asInt(),
            json.get("home_team_id").asLong(),
            json.get("home_abbr").asString(),
            json.get("away_team_id").asLong(),
            json.get("away_abbr").asString(),
            json.get("spread_home").asDouble(),
            json.get("game_total").asDouble(),
            json.get("home_team_total").asDouble(),
            json.get("away_team_total").asDouble(),
            json.get("moneyline_home").asInt(),
            json.get("moneyline_away").asInt(),
            json.get("is_final").asBoolean(),
            json.get("real_home_pts").asInt(),
            json.get("real_away_pts").asInt(),
            json.get("is_free").asBoolean()
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "game_id", gameId);
        first = JsonOut.field(sb, first, "season", season);
        first = JsonOut.field(sb, first, "week", week);
        first = JsonOut.field(sb, first, "season_type", seasonType);
        first = JsonOut.field(sb, first, "kickoff", kickoff);
        first = JsonOut.field(sb, first, "day", day);
        first = JsonOut.field(sb, first, "home_team_id", homeTeamId);
        first = JsonOut.field(sb, first, "home_abbr", homeAbbr);
        first = JsonOut.field(sb, first, "away_team_id", awayTeamId);
        first = JsonOut.field(sb, first, "away_abbr", awayAbbr);
        first = JsonOut.field(sb, first, "spread_home", spreadHome);
        first = JsonOut.field(sb, first, "game_total", gameTotal);
        first = JsonOut.field(sb, first, "home_team_total", homeTeamTotal);
        first = JsonOut.field(sb, first, "away_team_total", awayTeamTotal);
        first = JsonOut.field(sb, first, "moneyline_home", moneylineHome);
        first = JsonOut.field(sb, first, "moneyline_away", moneylineAway);
        first = JsonOut.field(sb, first, "is_final", isFinal);
        first = JsonOut.field(sb, first, "real_home_pts", realHomePts);
        first = JsonOut.field(sb, first, "real_away_pts", realAwayPts);
        first = JsonOut.field(sb, first, "is_free", isFree);
        sb.append('}');
        return sb.toString();
    }
}
