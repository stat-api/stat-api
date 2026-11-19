// DO NOT EDIT — generated from schema/api/sim/nba_sim.yml by generate-sim-sdks.ts
package com.statapi;

import java.util.List;

/**
 * Single-draw team box score inside {@code NBASimGameScoreboxDraw}.
 *
 * @param teamCode Team abbreviation.
 * @param pts Total points scored.
 * @param quarterPts Points per period {@code [Q1, Q2, Q3, Q4, ...OT]}.
 * @param possessions Total offensive possessions.
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
 * @param teamTov Shot-clock / dead-ball team turnovers.
 * @param pf Personal fouls.
 * @param fd Fouls drawn.
 * @param fastbreakPts Fastbreak points.
 * @param paintPts Paint points.
 * @param secondChancePts Second-chance points.
 * @param ptsOffTurnovers Points off turnovers.
 * @param starterPts Starter points.
 * @param benchPts Bench points.
 * @param starterReb Starter rebounds.
 * @param benchReb Bench rebounds.
 * @param starterAst Starter assists.
 * @param benchAst Bench assists.
 * @param biggestLead Largest lead.
 * @param leadChanges Lead changes.
 * @param timesTied Times tied.
 * @param missedTerminalFt Missed terminal free throws.
 * @param teamReb Dead-ball team rebounds.
 * @param lineupStints Realized 5-man lineup stints.
 * @param players Raw player box scores.
 */
public record NBATeamBoxScore(
    String teamCode,
    Integer pts,
    List<Integer> quarterPts,
    Integer possessions,
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
    Integer teamTov,
    Integer pf,
    Integer fd,
    Integer fastbreakPts,
    Integer paintPts,
    Integer secondChancePts,
    Integer ptsOffTurnovers,
    Integer starterPts,
    Integer benchPts,
    Integer starterReb,
    Integer benchReb,
    Integer starterAst,
    Integer benchAst,
    Integer biggestLead,
    Integer leadChanges,
    Integer timesTied,
    Integer missedTerminalFt,
    Integer teamReb,
    List<NBALineupStintBoxRow> lineupStints,
    List<NBAPlayerBoxScore> players
) implements JsonSerializable {

    public static NBATeamBoxScore fromJson(JsonValue json) {
        return new NBATeamBoxScore(
            json.get("team_code").asString(),
            json.get("pts").asInt(),
            JsonIn.list(json.get("quarter_pts"), JsonValue::asInt),
            json.get("possessions").asInt(),
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
            json.get("team_tov").asInt(),
            json.get("pf").asInt(),
            json.get("fd").asInt(),
            json.get("fastbreak_pts").asInt(),
            json.get("paint_pts").asInt(),
            json.get("second_chance_pts").asInt(),
            json.get("pts_off_turnovers").asInt(),
            json.get("starter_pts").asInt(),
            json.get("bench_pts").asInt(),
            json.get("starter_reb").asInt(),
            json.get("bench_reb").asInt(),
            json.get("starter_ast").asInt(),
            json.get("bench_ast").asInt(),
            json.get("biggest_lead").asInt(),
            json.get("lead_changes").asInt(),
            json.get("times_tied").asInt(),
            json.get("missed_terminal_ft").asInt(),
            json.get("team_reb").asInt(),
            JsonIn.list(json.get("lineup_stints"), NBALineupStintBoxRow::fromJson),
            JsonIn.list(json.get("players"), NBAPlayerBoxScore::fromJson)
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "team_code", teamCode);
        first = JsonOut.field(sb, first, "pts", pts);
        first = JsonOut.field(sb, first, "quarter_pts", quarterPts);
        first = JsonOut.field(sb, first, "possessions", possessions);
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
        first = JsonOut.field(sb, first, "team_tov", teamTov);
        first = JsonOut.field(sb, first, "pf", pf);
        first = JsonOut.field(sb, first, "fd", fd);
        first = JsonOut.field(sb, first, "fastbreak_pts", fastbreakPts);
        first = JsonOut.field(sb, first, "paint_pts", paintPts);
        first = JsonOut.field(sb, first, "second_chance_pts", secondChancePts);
        first = JsonOut.field(sb, first, "pts_off_turnovers", ptsOffTurnovers);
        first = JsonOut.field(sb, first, "starter_pts", starterPts);
        first = JsonOut.field(sb, first, "bench_pts", benchPts);
        first = JsonOut.field(sb, first, "starter_reb", starterReb);
        first = JsonOut.field(sb, first, "bench_reb", benchReb);
        first = JsonOut.field(sb, first, "starter_ast", starterAst);
        first = JsonOut.field(sb, first, "bench_ast", benchAst);
        first = JsonOut.field(sb, first, "biggest_lead", biggestLead);
        first = JsonOut.field(sb, first, "lead_changes", leadChanges);
        first = JsonOut.field(sb, first, "times_tied", timesTied);
        first = JsonOut.field(sb, first, "missed_terminal_ft", missedTerminalFt);
        first = JsonOut.field(sb, first, "team_reb", teamReb);
        first = JsonOut.field(sb, first, "lineup_stints", lineupStints);
        first = JsonOut.field(sb, first, "players", players);
        sb.append('}');
        return sb.toString();
    }
}
