// DO NOT EDIT — generated from schema/api/sim/nba_sim.yml by generate-sim-sdks.ts
package com.statapi;

/**
 * Matchup header and pre-tip Vegas line summary for a single NBA game.
 *
 * @param gameId Unique NBA game identifier ({@code 6029936} = {@code DET @ BOS}).
 * @param season NBA season year.
 * @param week NBA season week number.
 * @param seasonType Modeled NBA season phase ({@code regular_season}, {@code in_season_tournament}, {@code play_in}, {@code postseason}). (values: regular_season | in_season_tournament | play_in | postseason)
 * @param kickoff Scheduled UTC tipoff timestamp (ISO-8601).
 * @param day Game date integer in {@code YYYYMMDD} format.
 * @param homeTeamId Canonical home team ID.
 * @param homeAbbr Home team abbreviation (e.g., {@code BOS}).
 * @param awayTeamId Canonical away team ID.
 * @param awayAbbr Away team abbreviation (e.g., {@code DET}).
 * @param spreadHome Pre-tip Vegas home spread in points (negative = home favored).
 * @param gameTotal Pre-tip Vegas over/under game total points.
 * @param homeTeamTotal Implied home team total points ({@code (game_total - spread_home) / 2}).
 * @param awayTeamTotal Implied away team total points ({@code (game_total + spread_home) / 2}).
 * @param moneylineHome Pre-tip American moneyline for the home team.
 * @param moneylineAway Pre-tip American moneyline for the away team.
 * @param isFinal True if the real NBA game has completed.
 * @param realHomePts Actual final home points scored ({@code null} before game completion).
 * @param realAwayPts Actual final away points scored ({@code null} before game completion).
 * @param isFree True when unlocked for free full-depth simulation ({@code game_id == 6029936}).
 */
public record NBAMatchupSummary(
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

    public static NBAMatchupSummary fromJson(JsonValue json) {
        return new NBAMatchupSummary(
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
