// DO NOT EDIT — generated from schema/api/sim/nba_sim.yml by generate-sim-sdks.ts
package com.statapi;

import java.util.List;

/**
 * Formatted single-draw player box score inside {@code NBASimGameScoreboxDraw.players}.
 *
 * @param playerId Canonical player ID.
 * @param name Player display name.
 * @param teamCode Team abbreviation.
 * @param isHome True if home team.
 * @param position Court position.
 * @param started True if started.
 * @param minutes Minutes played.
 * @param minutesMmSs Formatted {@code MM:SS} minutes string.
 * @param dnpReason DNP reason if player did not play ({@code null} when active).
 * @param stintCount Number of floor stints.
 * @param pts Points.
 * @param reb Total rebounds.
 * @param oreb Offensive rebounds.
 * @param dreb Defensive rebounds.
 * @param ast Assists.
 * @param stl Steals.
 * @param blk Blocks.
 * @param ba Blocked field goal attempts.
 * @param tov Turnovers.
 * @param pf Personal fouls.
 * @param fd Fouls drawn.
 * @param fgm Field goals made.
 * @param fga Field goal attempts.
 * @param fg2m Two-pointers made.
 * @param fg2a Two-point attempts.
 * @param fg3m Three-pointers made.
 * @param fg3a Three-point attempts.
 * @param ftm Free throws made.
 * @param fta Free throw attempts.
 * @param paintPts Points in the paint.
 * @param fastbreakPts Fastbreak points.
 * @param secondChancePts Second-chance points.
 * @param unassistedFgm Unassisted field goals made.
 * @param quarterPts Points scored across {@code [Q1, Q2, Q3, Q4, OT]}.
 * @param plusMinus On-court point differential.
 * @param doubleDouble True if recorded a double-double.
 * @param tripleDouble True if recorded a triple-double.
 * @param dkPts DraftKings Classic fantasy points.
 * @param fdPts FanDuel Classic fantasy points.
 */
public record NBAPlayerDrawBoxSummary(
    Long playerId,
    String name,
    String teamCode,
    Boolean isHome,
    String position,
    Boolean started,
    Double minutes,
    String minutesMmSs,
    String dnpReason,
    Integer stintCount,
    Integer pts,
    Integer reb,
    Integer oreb,
    Integer dreb,
    Integer ast,
    Integer stl,
    Integer blk,
    Integer ba,
    Integer tov,
    Integer pf,
    Integer fd,
    Integer fgm,
    Integer fga,
    Integer fg2m,
    Integer fg2a,
    Integer fg3m,
    Integer fg3a,
    Integer ftm,
    Integer fta,
    Integer paintPts,
    Integer fastbreakPts,
    Integer secondChancePts,
    Integer unassistedFgm,
    List<Integer> quarterPts,
    Integer plusMinus,
    Boolean doubleDouble,
    Boolean tripleDouble,
    Double dkPts,
    Double fdPts
) implements JsonSerializable {

    public static NBAPlayerDrawBoxSummary fromJson(JsonValue json) {
        return new NBAPlayerDrawBoxSummary(
            json.get("player_id").asLong(),
            json.get("name").asString(),
            json.get("team_code").asString(),
            json.get("is_home").asBoolean(),
            json.get("position").asString(),
            json.get("started").asBoolean(),
            json.get("minutes").asDouble(),
            json.get("minutes_mm_ss").asString(),
            json.get("dnp_reason").asString(),
            json.get("stint_count").asInt(),
            json.get("pts").asInt(),
            json.get("reb").asInt(),
            json.get("oreb").asInt(),
            json.get("dreb").asInt(),
            json.get("ast").asInt(),
            json.get("stl").asInt(),
            json.get("blk").asInt(),
            json.get("ba").asInt(),
            json.get("tov").asInt(),
            json.get("pf").asInt(),
            json.get("fd").asInt(),
            json.get("fgm").asInt(),
            json.get("fga").asInt(),
            json.get("fg2m").asInt(),
            json.get("fg2a").asInt(),
            json.get("fg3m").asInt(),
            json.get("fg3a").asInt(),
            json.get("ftm").asInt(),
            json.get("fta").asInt(),
            json.get("paint_pts").asInt(),
            json.get("fastbreak_pts").asInt(),
            json.get("second_chance_pts").asInt(),
            json.get("unassisted_fgm").asInt(),
            JsonIn.list(json.get("quarter_pts"), JsonValue::asInt),
            json.get("plus_minus").asInt(),
            json.get("double_double").asBoolean(),
            json.get("triple_double").asBoolean(),
            json.get("dk_pts").asDouble(),
            json.get("fd_pts").asDouble()
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "player_id", playerId);
        first = JsonOut.field(sb, first, "name", name);
        first = JsonOut.field(sb, first, "team_code", teamCode);
        first = JsonOut.field(sb, first, "is_home", isHome);
        first = JsonOut.field(sb, first, "position", position);
        first = JsonOut.field(sb, first, "started", started);
        first = JsonOut.field(sb, first, "minutes", minutes);
        first = JsonOut.field(sb, first, "minutes_mm_ss", minutesMmSs);
        first = JsonOut.field(sb, first, "dnp_reason", dnpReason);
        first = JsonOut.field(sb, first, "stint_count", stintCount);
        first = JsonOut.field(sb, first, "pts", pts);
        first = JsonOut.field(sb, first, "reb", reb);
        first = JsonOut.field(sb, first, "oreb", oreb);
        first = JsonOut.field(sb, first, "dreb", dreb);
        first = JsonOut.field(sb, first, "ast", ast);
        first = JsonOut.field(sb, first, "stl", stl);
        first = JsonOut.field(sb, first, "blk", blk);
        first = JsonOut.field(sb, first, "ba", ba);
        first = JsonOut.field(sb, first, "tov", tov);
        first = JsonOut.field(sb, first, "pf", pf);
        first = JsonOut.field(sb, first, "fd", fd);
        first = JsonOut.field(sb, first, "fgm", fgm);
        first = JsonOut.field(sb, first, "fga", fga);
        first = JsonOut.field(sb, first, "fg2m", fg2m);
        first = JsonOut.field(sb, first, "fg2a", fg2a);
        first = JsonOut.field(sb, first, "fg3m", fg3m);
        first = JsonOut.field(sb, first, "fg3a", fg3a);
        first = JsonOut.field(sb, first, "ftm", ftm);
        first = JsonOut.field(sb, first, "fta", fta);
        first = JsonOut.field(sb, first, "paint_pts", paintPts);
        first = JsonOut.field(sb, first, "fastbreak_pts", fastbreakPts);
        first = JsonOut.field(sb, first, "second_chance_pts", secondChancePts);
        first = JsonOut.field(sb, first, "unassisted_fgm", unassistedFgm);
        first = JsonOut.field(sb, first, "quarter_pts", quarterPts);
        first = JsonOut.field(sb, first, "plus_minus", plusMinus);
        first = JsonOut.field(sb, first, "double_double", doubleDouble);
        first = JsonOut.field(sb, first, "triple_double", tripleDouble);
        first = JsonOut.field(sb, first, "dk_pts", dkPts);
        first = JsonOut.field(sb, first, "fd_pts", fdPts);
        sb.append('}');
        return sb.toString();
    }
}
