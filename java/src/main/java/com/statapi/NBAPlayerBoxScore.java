// DO NOT EDIT — generated from schema/api/sim/nba_sim.yml by generate-sim-sdks.ts
package com.statapi;

import java.util.List;

/**
 * Raw per-draw player box score inside {@code NBATeamBoxScore.players}.
 *
 * @param playerId Canonical player ID.
 * @param minutesSeconds Seconds played.
 * @param pts Points.
 * @param fgm Field goals made.
 * @param fga Field goal attempts.
 * @param fg3m Three-pointers made.
 * @param fg3a Three-point attempts.
 * @param ftm Free throws made.
 * @param fta Free throw attempts.
 * @param oreb Offensive rebounds.
 * @param dreb Defensive rebounds.
 * @param reb Total rebounds.
 * @param ast Assists.
 * @param stl Steals.
 * @param blk Blocks.
 * @param ba Blocked attempts.
 * @param tov Turnovers.
 * @param pf Personal fouls.
 * @param fd Fouls drawn.
 * @param plusMinus Plus-minus.
 * @param paintPts Paint points.
 * @param fastbreakPts Fastbreak points.
 * @param secondChancePts Second-chance points.
 * @param unassistedFgm Unassisted field goals made.
 * @param stintCount Floor stint count.
 * @param quarterPts Quarter points {@code [Q1..Q4, OT]}.
 * @param quarterMinutesSec Quarter seconds played {@code [Q1..Q4, OT]}.
 * @param quarterFga Quarter FGA {@code [Q1..Q4, OT]}.
 * @param started True if started.
 * @param fouledOut True if disqualified with 6 fouls.
 * @param injuredOut True if exited injured during game.
 */
public record NBAPlayerBoxScore(
    Long playerId,
    Double minutesSeconds,
    Integer pts,
    Integer fgm,
    Integer fga,
    Integer fg3m,
    Integer fg3a,
    Integer ftm,
    Integer fta,
    Integer oreb,
    Integer dreb,
    Integer reb,
    Integer ast,
    Integer stl,
    Integer blk,
    Integer ba,
    Integer tov,
    Integer pf,
    Integer fd,
    Integer plusMinus,
    Integer paintPts,
    Integer fastbreakPts,
    Integer secondChancePts,
    Integer unassistedFgm,
    Integer stintCount,
    List<Integer> quarterPts,
    List<Double> quarterMinutesSec,
    List<Integer> quarterFga,
    Boolean started,
    Boolean fouledOut,
    Boolean injuredOut
) implements JsonSerializable {

    public static NBAPlayerBoxScore fromJson(JsonValue json) {
        return new NBAPlayerBoxScore(
            json.get("player_id").asLong(),
            json.get("minutes_seconds").asDouble(),
            json.get("pts").asInt(),
            json.get("fgm").asInt(),
            json.get("fga").asInt(),
            json.get("fg3m").asInt(),
            json.get("fg3a").asInt(),
            json.get("ftm").asInt(),
            json.get("fta").asInt(),
            json.get("oreb").asInt(),
            json.get("dreb").asInt(),
            json.get("reb").asInt(),
            json.get("ast").asInt(),
            json.get("stl").asInt(),
            json.get("blk").asInt(),
            json.get("ba").asInt(),
            json.get("tov").asInt(),
            json.get("pf").asInt(),
            json.get("fd").asInt(),
            json.get("plus_minus").asInt(),
            json.get("paint_pts").asInt(),
            json.get("fastbreak_pts").asInt(),
            json.get("second_chance_pts").asInt(),
            json.get("unassisted_fgm").asInt(),
            json.get("stint_count").asInt(),
            JsonIn.list(json.get("quarter_pts"), JsonValue::asInt),
            JsonIn.list(json.get("quarter_minutes_sec"), JsonValue::asDouble),
            JsonIn.list(json.get("quarter_fga"), JsonValue::asInt),
            json.get("started").asBoolean(),
            json.get("fouled_out").asBoolean(),
            json.get("injured_out").asBoolean()
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "player_id", playerId);
        first = JsonOut.field(sb, first, "minutes_seconds", minutesSeconds);
        first = JsonOut.field(sb, first, "pts", pts);
        first = JsonOut.field(sb, first, "fgm", fgm);
        first = JsonOut.field(sb, first, "fga", fga);
        first = JsonOut.field(sb, first, "fg3m", fg3m);
        first = JsonOut.field(sb, first, "fg3a", fg3a);
        first = JsonOut.field(sb, first, "ftm", ftm);
        first = JsonOut.field(sb, first, "fta", fta);
        first = JsonOut.field(sb, first, "oreb", oreb);
        first = JsonOut.field(sb, first, "dreb", dreb);
        first = JsonOut.field(sb, first, "reb", reb);
        first = JsonOut.field(sb, first, "ast", ast);
        first = JsonOut.field(sb, first, "stl", stl);
        first = JsonOut.field(sb, first, "blk", blk);
        first = JsonOut.field(sb, first, "ba", ba);
        first = JsonOut.field(sb, first, "tov", tov);
        first = JsonOut.field(sb, first, "pf", pf);
        first = JsonOut.field(sb, first, "fd", fd);
        first = JsonOut.field(sb, first, "plus_minus", plusMinus);
        first = JsonOut.field(sb, first, "paint_pts", paintPts);
        first = JsonOut.field(sb, first, "fastbreak_pts", fastbreakPts);
        first = JsonOut.field(sb, first, "second_chance_pts", secondChancePts);
        first = JsonOut.field(sb, first, "unassisted_fgm", unassistedFgm);
        first = JsonOut.field(sb, first, "stint_count", stintCount);
        first = JsonOut.field(sb, first, "quarter_pts", quarterPts);
        first = JsonOut.field(sb, first, "quarter_minutes_sec", quarterMinutesSec);
        first = JsonOut.field(sb, first, "quarter_fga", quarterFga);
        first = JsonOut.field(sb, first, "started", started);
        first = JsonOut.field(sb, first, "fouled_out", fouledOut);
        first = JsonOut.field(sb, first, "injured_out", injuredOut);
        sb.append('}');
        return sb.toString();
    }
}
