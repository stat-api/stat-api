// DO NOT EDIT — generated from schema/api/sim/nba_sim.yml by generate-sim-sdks.ts
package com.statapi;

/**
 * Sparse team box score inside {@code NBASimGameScenario.teams} and {@code NBASimSimulatedGame.teams}.
 *
 * @param possessions Offensive possessions.
 * @param pace Realized pace (possessions per 48 min).
 * @param offensiveRating Points scored per 100 possessions.
 * @param defensiveRating Points allowed per 100 possessions.
 * @param pts Total points scored.
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
 * @param efgPct Effective field goal percentage ({@code %}).
 * @param tsPct True shooting percentage ({@code %}).
 * @param oreb Offensive rebounds.
 * @param dreb Defensive rebounds.
 * @param reb Total rebounds.
 * @param ast Assists.
 * @param stl Steals.
 * @param blk Blocks.
 * @param ba Blocked attempts.
 * @param tov Turnovers.
 * @param teamTov Team turnovers.
 * @param teamReb Dead-ball team rebounds.
 * @param pf Personal fouls.
 * @param fd Fouls drawn.
 * @param paintPts Points in the paint.
 * @param fastbreakPts Fastbreak points.
 * @param secondChancePts Second-chance points.
 * @param ptsOffTurnovers Points off turnovers.
 * @param starterPts Starter points.
 * @param benchPts Bench points.
 * @param biggestLead Largest lead.
 * @param leadChanges Lead changes.
 * @param timesTied Times tied.
 * @param pra Combined PRA.
 * @param dkPts Combined DraftKings NBA fantasy points.
 * @param fdPts Combined FanDuel NBA fantasy points.
 */
public record NBASimTeamBoxScoreStat(
    Integer possessions,
    Double pace,
    Double offensiveRating,
    Double defensiveRating,
    Integer pts,
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
    Double efgPct,
    Double tsPct,
    Integer oreb,
    Integer dreb,
    Integer reb,
    Integer ast,
    Integer stl,
    Integer blk,
    Integer ba,
    Integer tov,
    Integer teamTov,
    Integer teamReb,
    Integer pf,
    Integer fd,
    Integer paintPts,
    Integer fastbreakPts,
    Integer secondChancePts,
    Integer ptsOffTurnovers,
    Integer starterPts,
    Integer benchPts,
    Integer biggestLead,
    Integer leadChanges,
    Integer timesTied,
    Integer pra,
    Double dkPts,
    Double fdPts
) implements JsonSerializable {

    public static NBASimTeamBoxScoreStat fromJson(JsonValue json) {
        return new NBASimTeamBoxScoreStat(
            json.get("possessions").asInt(),
            json.get("pace").asDouble(),
            json.get("offensive_rating").asDouble(),
            json.get("defensive_rating").asDouble(),
            json.get("pts").asInt(),
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
            json.get("efg_pct").asDouble(),
            json.get("ts_pct").asDouble(),
            json.get("oreb").asInt(),
            json.get("dreb").asInt(),
            json.get("reb").asInt(),
            json.get("ast").asInt(),
            json.get("stl").asInt(),
            json.get("blk").asInt(),
            json.get("ba").asInt(),
            json.get("tov").asInt(),
            json.get("team_tov").asInt(),
            json.get("team_reb").asInt(),
            json.get("pf").asInt(),
            json.get("fd").asInt(),
            json.get("paint_pts").asInt(),
            json.get("fastbreak_pts").asInt(),
            json.get("second_chance_pts").asInt(),
            json.get("pts_off_turnovers").asInt(),
            json.get("starter_pts").asInt(),
            json.get("bench_pts").asInt(),
            json.get("biggest_lead").asInt(),
            json.get("lead_changes").asInt(),
            json.get("times_tied").asInt(),
            json.get("pra").asInt(),
            json.get("dk_pts").asDouble(),
            json.get("fd_pts").asDouble()
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "possessions", possessions);
        first = JsonOut.field(sb, first, "pace", pace);
        first = JsonOut.field(sb, first, "offensive_rating", offensiveRating);
        first = JsonOut.field(sb, first, "defensive_rating", defensiveRating);
        first = JsonOut.field(sb, first, "pts", pts);
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
        first = JsonOut.field(sb, first, "efg_pct", efgPct);
        first = JsonOut.field(sb, first, "ts_pct", tsPct);
        first = JsonOut.field(sb, first, "oreb", oreb);
        first = JsonOut.field(sb, first, "dreb", dreb);
        first = JsonOut.field(sb, first, "reb", reb);
        first = JsonOut.field(sb, first, "ast", ast);
        first = JsonOut.field(sb, first, "stl", stl);
        first = JsonOut.field(sb, first, "blk", blk);
        first = JsonOut.field(sb, first, "ba", ba);
        first = JsonOut.field(sb, first, "tov", tov);
        first = JsonOut.field(sb, first, "team_tov", teamTov);
        first = JsonOut.field(sb, first, "team_reb", teamReb);
        first = JsonOut.field(sb, first, "pf", pf);
        first = JsonOut.field(sb, first, "fd", fd);
        first = JsonOut.field(sb, first, "paint_pts", paintPts);
        first = JsonOut.field(sb, first, "fastbreak_pts", fastbreakPts);
        first = JsonOut.field(sb, first, "second_chance_pts", secondChancePts);
        first = JsonOut.field(sb, first, "pts_off_turnovers", ptsOffTurnovers);
        first = JsonOut.field(sb, first, "starter_pts", starterPts);
        first = JsonOut.field(sb, first, "bench_pts", benchPts);
        first = JsonOut.field(sb, first, "biggest_lead", biggestLead);
        first = JsonOut.field(sb, first, "lead_changes", leadChanges);
        first = JsonOut.field(sb, first, "times_tied", timesTied);
        first = JsonOut.field(sb, first, "pra", pra);
        first = JsonOut.field(sb, first, "dk_pts", dkPts);
        first = JsonOut.field(sb, first, "fd_pts", fdPts);
        sb.append('}');
        return sb.toString();
    }
}
