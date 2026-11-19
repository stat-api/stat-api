// DO NOT EDIT — generated from schema/api/sim/nba_sim.yml by generate-sim-sdks.ts
package com.statapi;

/**
 * Sparse active-player box score inside {@code NBASimGameScenario.players} and {@code NBASimSimulatedGame.players}.
 *
 * @param started True if player started.
 * @param minutes Total minutes played.
 * @param stintCount Floor stint count.
 * @param q1Minutes Q1 minutes.
 * @param q2Minutes Q2 minutes.
 * @param q3Minutes Q3 minutes.
 * @param q4Minutes Q4 minutes.
 * @param otMinutes Overtime minutes.
 * @param pts Points.
 * @param q1Pts Q1 points.
 * @param q2Pts Q2 points.
 * @param q3Pts Q3 points.
 * @param q4Pts Q4 points.
 * @param otPts Overtime points.
 * @param fgm Field goals made.
 * @param fga Field goal attempts.
 * @param fgPct Field goal percentage ({@code %}).
 * @param fg2m Two-pointers made.
 * @param fg2a Two-point attempts.
 * @param fg2Pct Two-point percentage ({@code %}).
 * @param fg3m Three-pointers made.
 * @param fg3a Three-point attempts.
 * @param fg3Pct Three-point percentage ({@code %}).
 * @param ftm Free throws made.
 * @param fta Free throw attempts.
 * @param ftPct Free throw percentage ({@code %}).
 * @param tsPct True shooting percentage ({@code %}).
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
 * @param paintPts Points in the paint.
 * @param fastbreakPts Fastbreak points.
 * @param secondChancePts Second-chance points.
 * @param unassistedFgm Unassisted field goals made.
 * @param plusMinus On-court point differential.
 * @param fouledOut True if disqualified with 6 fouls.
 * @param doubleDouble True if double-double recorded.
 * @param tripleDouble True if triple-double recorded.
 * @param pra Points + Rebounds + Assists.
 * @param dkPts DraftKings Classic NBA fantasy points.
 * @param fdPts FanDuel Classic NBA fantasy points.
 */
public record NBASimPlayerBoxScoreStat(
    Boolean started,
    Double minutes,
    Integer stintCount,
    Double q1Minutes,
    Double q2Minutes,
    Double q3Minutes,
    Double q4Minutes,
    Double otMinutes,
    Integer pts,
    Integer q1Pts,
    Integer q2Pts,
    Integer q3Pts,
    Integer q4Pts,
    Integer otPts,
    Integer fgm,
    Integer fga,
    Double fgPct,
    Integer fg2m,
    Integer fg2a,
    Double fg2Pct,
    Integer fg3m,
    Integer fg3a,
    Double fg3Pct,
    Integer ftm,
    Integer fta,
    Double ftPct,
    Double tsPct,
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
    Integer paintPts,
    Integer fastbreakPts,
    Integer secondChancePts,
    Integer unassistedFgm,
    Integer plusMinus,
    Boolean fouledOut,
    Boolean doubleDouble,
    Boolean tripleDouble,
    Integer pra,
    Double dkPts,
    Double fdPts
) implements JsonSerializable {

    public static NBASimPlayerBoxScoreStat fromJson(JsonValue json) {
        return new NBASimPlayerBoxScoreStat(
            json.get("started").asBoolean(),
            json.get("minutes").asDouble(),
            json.get("stint_count").asInt(),
            json.get("q1_minutes").asDouble(),
            json.get("q2_minutes").asDouble(),
            json.get("q3_minutes").asDouble(),
            json.get("q4_minutes").asDouble(),
            json.get("ot_minutes").asDouble(),
            json.get("pts").asInt(),
            json.get("q1_pts").asInt(),
            json.get("q2_pts").asInt(),
            json.get("q3_pts").asInt(),
            json.get("q4_pts").asInt(),
            json.get("ot_pts").asInt(),
            json.get("fgm").asInt(),
            json.get("fga").asInt(),
            json.get("fg_pct").asDouble(),
            json.get("fg2m").asInt(),
            json.get("fg2a").asInt(),
            json.get("fg2_pct").asDouble(),
            json.get("fg3m").asInt(),
            json.get("fg3a").asInt(),
            json.get("fg3_pct").asDouble(),
            json.get("ftm").asInt(),
            json.get("fta").asInt(),
            json.get("ft_pct").asDouble(),
            json.get("ts_pct").asDouble(),
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
            json.get("paint_pts").asInt(),
            json.get("fastbreak_pts").asInt(),
            json.get("second_chance_pts").asInt(),
            json.get("unassisted_fgm").asInt(),
            json.get("plus_minus").asInt(),
            json.get("fouled_out").asBoolean(),
            json.get("double_double").asBoolean(),
            json.get("triple_double").asBoolean(),
            json.get("pra").asInt(),
            json.get("dk_pts").asDouble(),
            json.get("fd_pts").asDouble()
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "started", started);
        first = JsonOut.field(sb, first, "minutes", minutes);
        first = JsonOut.field(sb, first, "stint_count", stintCount);
        first = JsonOut.field(sb, first, "q1_minutes", q1Minutes);
        first = JsonOut.field(sb, first, "q2_minutes", q2Minutes);
        first = JsonOut.field(sb, first, "q3_minutes", q3Minutes);
        first = JsonOut.field(sb, first, "q4_minutes", q4Minutes);
        first = JsonOut.field(sb, first, "ot_minutes", otMinutes);
        first = JsonOut.field(sb, first, "pts", pts);
        first = JsonOut.field(sb, first, "q1_pts", q1Pts);
        first = JsonOut.field(sb, first, "q2_pts", q2Pts);
        first = JsonOut.field(sb, first, "q3_pts", q3Pts);
        first = JsonOut.field(sb, first, "q4_pts", q4Pts);
        first = JsonOut.field(sb, first, "ot_pts", otPts);
        first = JsonOut.field(sb, first, "fgm", fgm);
        first = JsonOut.field(sb, first, "fga", fga);
        first = JsonOut.field(sb, first, "fg_pct", fgPct);
        first = JsonOut.field(sb, first, "fg2m", fg2m);
        first = JsonOut.field(sb, first, "fg2a", fg2a);
        first = JsonOut.field(sb, first, "fg2_pct", fg2Pct);
        first = JsonOut.field(sb, first, "fg3m", fg3m);
        first = JsonOut.field(sb, first, "fg3a", fg3a);
        first = JsonOut.field(sb, first, "fg3_pct", fg3Pct);
        first = JsonOut.field(sb, first, "ftm", ftm);
        first = JsonOut.field(sb, first, "fta", fta);
        first = JsonOut.field(sb, first, "ft_pct", ftPct);
        first = JsonOut.field(sb, first, "ts_pct", tsPct);
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
        first = JsonOut.field(sb, first, "paint_pts", paintPts);
        first = JsonOut.field(sb, first, "fastbreak_pts", fastbreakPts);
        first = JsonOut.field(sb, first, "second_chance_pts", secondChancePts);
        first = JsonOut.field(sb, first, "unassisted_fgm", unassistedFgm);
        first = JsonOut.field(sb, first, "plus_minus", plusMinus);
        first = JsonOut.field(sb, first, "fouled_out", fouledOut);
        first = JsonOut.field(sb, first, "double_double", doubleDouble);
        first = JsonOut.field(sb, first, "triple_double", tripleDouble);
        first = JsonOut.field(sb, first, "pra", pra);
        first = JsonOut.field(sb, first, "dk_pts", dkPts);
        first = JsonOut.field(sb, first, "fd_pts", fdPts);
        sb.append('}');
        return sb.toString();
    }
}
