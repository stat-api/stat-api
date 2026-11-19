// DO NOT EDIT — generated from schema/api/sim/nfl_sim.yml by generate-sim-sdks.ts
package com.statapi;

/**
 * Single-draw team box score ({@code away_team} and {@code home_team} inside SimGameScoreboxDraw).
 *
 * @param points Final points scored by the team on this draw.
 * @param q1Points 1st-quarter points.
 * @param q2Points 2nd-quarter points.
 * @param q3Points 3rd-quarter points.
 * @param q4Points 4th-quarter points.
 * @param otPoints Overtime points.
 * @param plays Total offensive plays ({@code pass_att + rush_att + sacks_taken}).
 * @param passAtt Team pass attempts.
 * @param passCmp Team pass completions.
 * @param passYd Team gross passing yards.
 * @param passTd Team passing touchdowns.
 * @param interceptionsThrown Interceptions thrown.
 * @param sacksTaken Times the quarterback was sacked.
 * @param sackYdLost Yards lost on sacks.
 * @param rushAtt Team rushing attempts.
 * @param rushYd Team rushing yards.
 * @param rushTd Team rushing touchdowns.
 * @param firstDowns Total offensive first downs earned.
 * @param thirdDownAtt 3rd-down attempts.
 * @param thirdDownConv 3rd-down conversions.
 * @param fourthDownAtt 4th-down go-for-it attempts.
 * @param fourthDownConv 4th-down conversions.
 * @param drives Total offensive drives.
 * @param driveTds Drives ending in an offensive touchdown.
 * @param driveFgs Drives ending in a made field goal.
 * @param driveMissedFgs Drives ending in a missed field goal.
 * @param drivePunts Drives ending in a punt.
 * @param driveTurnovers Drives ending in an interception or fumble lost.
 * @param driveDowns Drives ending in a turnover on downs.
 * @param driveEndHalf Drives ending by clock expiration at halftime or game end.
 * @param fgAtt Field goals attempted.
 * @param fgMade Field goals made.
 * @param xpAtt Extra points (PATs) attempted.
 * @param xpMade Extra points (PATs) made.
 * @param twoPtAtt Two-point conversions attempted.
 * @param twoPtMade Two-point conversions succeeded.
 * @param penalties Penalties committed.
 * @param penaltyYds Penalty yards assessed.
 * @param timeOfPossessionSec Offensive time of possession in seconds.
 * @param throwaways Passes thrown away on purpose.
 * @param spikes Spikes to stop the clock.
 * @param battedPasses Passes batted down at the line of scrimmage.
 * @param oobUntackledPlays Plays that end out of bounds with no tackler (for example a quarterback who runs out of bounds behind the line, or a return that ends out of bounds).
 * @param specialTeamsReturns Kickoffs and punts that the team returned.
 * @param kickoffsTotal Kickoffs that the team received.
 * @param kickoffReturns Received kickoffs that the team returned (the rest were touchbacks).
 * @param kickoffStartYardlineSum Sum of the team's own yard lines at the start of the drives after received kickoffs. Divide by {@code kickoffs_total} for the mean.
 * @param trailing10plusDropbacks Dropbacks while the team trailed by 10 or more points.
 * @param trailing10plusPlays Offensive plays while the team trailed by 10 or more points.
 * @param neutralDropbacks Dropbacks in the first three quarters while the score was within 6 points.
 * @param neutralPlays Offensive plays in the first three quarters while the score was within 6 points.
 * @param p11Snaps Offensive snaps aligned in 11 personnel (1 RB, 1 TE, 3 WR).
 * @param p12Snaps Offensive snaps aligned in 12 personnel (1 RB, 2 TE, 2 WR).
 * @param p21Snaps Offensive snaps aligned in 21 personnel (2 RB, 1 TE, 2 WR).
 * @param noHuddleSnaps Offensive snaps executed in no-huddle tempo.
 * @param unassistedTeamSacks Sacks credited to the team defense with no named player (the quarterback ran out of bounds behind the line).
 * @param dstPressures Quarterback pressures by the defense.
 * @param pickSixesScored Interceptions that the defense returned for a touchdown.
 * @param fumbleRetTdsScored Fumbles that the defense or special teams returned for a touchdown.
 * @param dstSacks Defensive sacks recorded.
 * @param dstInterceptions Defensive interceptions recorded.
 * @param dstFumblesRecovered Opponent fumbles recovered by the defense.
 * @param dstDefensiveTds Defensive or special-teams touchdowns scored.
 * @param dstSafeties Safeties recorded by the defense.
 * @param dstPointsAllowed Points allowed by the defense.
 */
public record NFLTeamGameBoxScore(
    Integer points,
    Integer q1Points,
    Integer q2Points,
    Integer q3Points,
    Integer q4Points,
    Integer otPoints,
    Integer plays,
    Integer passAtt,
    Integer passCmp,
    Integer passYd,
    Integer passTd,
    Integer interceptionsThrown,
    Integer sacksTaken,
    Integer sackYdLost,
    Integer rushAtt,
    Integer rushYd,
    Integer rushTd,
    Integer firstDowns,
    Integer thirdDownAtt,
    Integer thirdDownConv,
    Integer fourthDownAtt,
    Integer fourthDownConv,
    Integer drives,
    Integer driveTds,
    Integer driveFgs,
    Integer driveMissedFgs,
    Integer drivePunts,
    Integer driveTurnovers,
    Integer driveDowns,
    Integer driveEndHalf,
    Integer fgAtt,
    Integer fgMade,
    Integer xpAtt,
    Integer xpMade,
    Integer twoPtAtt,
    Integer twoPtMade,
    Integer penalties,
    Integer penaltyYds,
    Integer timeOfPossessionSec,
    Integer throwaways,
    Integer spikes,
    Integer battedPasses,
    Integer oobUntackledPlays,
    Integer specialTeamsReturns,
    Integer kickoffsTotal,
    Integer kickoffReturns,
    Integer kickoffStartYardlineSum,
    Integer trailing10plusDropbacks,
    Integer trailing10plusPlays,
    Integer neutralDropbacks,
    Integer neutralPlays,
    Integer p11Snaps,
    Integer p12Snaps,
    Integer p21Snaps,
    Integer noHuddleSnaps,
    Integer unassistedTeamSacks,
    Integer dstPressures,
    Integer pickSixesScored,
    Integer fumbleRetTdsScored,
    Integer dstSacks,
    Integer dstInterceptions,
    Integer dstFumblesRecovered,
    Integer dstDefensiveTds,
    Integer dstSafeties,
    Integer dstPointsAllowed
) implements JsonSerializable {

    public static NFLTeamGameBoxScore fromJson(JsonValue json) {
        return new NFLTeamGameBoxScore(
            json.get("points").asInt(),
            json.get("q1_points").asInt(),
            json.get("q2_points").asInt(),
            json.get("q3_points").asInt(),
            json.get("q4_points").asInt(),
            json.get("ot_points").asInt(),
            json.get("plays").asInt(),
            json.get("pass_att").asInt(),
            json.get("pass_cmp").asInt(),
            json.get("pass_yd").asInt(),
            json.get("pass_td").asInt(),
            json.get("interceptions_thrown").asInt(),
            json.get("sacks_taken").asInt(),
            json.get("sack_yd_lost").asInt(),
            json.get("rush_att").asInt(),
            json.get("rush_yd").asInt(),
            json.get("rush_td").asInt(),
            json.get("first_downs").asInt(),
            json.get("third_down_att").asInt(),
            json.get("third_down_conv").asInt(),
            json.get("fourth_down_att").asInt(),
            json.get("fourth_down_conv").asInt(),
            json.get("drives").asInt(),
            json.get("drive_tds").asInt(),
            json.get("drive_fgs").asInt(),
            json.get("drive_missed_fgs").asInt(),
            json.get("drive_punts").asInt(),
            json.get("drive_turnovers").asInt(),
            json.get("drive_downs").asInt(),
            json.get("drive_end_half").asInt(),
            json.get("fg_att").asInt(),
            json.get("fg_made").asInt(),
            json.get("xp_att").asInt(),
            json.get("xp_made").asInt(),
            json.get("two_pt_att").asInt(),
            json.get("two_pt_made").asInt(),
            json.get("penalties").asInt(),
            json.get("penalty_yds").asInt(),
            json.get("time_of_possession_sec").asInt(),
            json.get("throwaways").asInt(),
            json.get("spikes").asInt(),
            json.get("batted_passes").asInt(),
            json.get("oob_untackled_plays").asInt(),
            json.get("special_teams_returns").asInt(),
            json.get("kickoffs_total").asInt(),
            json.get("kickoff_returns").asInt(),
            json.get("kickoff_start_yardline_sum").asInt(),
            json.get("trailing_10plus_dropbacks").asInt(),
            json.get("trailing_10plus_plays").asInt(),
            json.get("neutral_dropbacks").asInt(),
            json.get("neutral_plays").asInt(),
            json.get("p11_snaps").asInt(),
            json.get("p12_snaps").asInt(),
            json.get("p21_snaps").asInt(),
            json.get("no_huddle_snaps").asInt(),
            json.get("unassisted_team_sacks").asInt(),
            json.get("dst_pressures").asInt(),
            json.get("pick_sixes_scored").asInt(),
            json.get("fumble_ret_tds_scored").asInt(),
            json.get("dst_sacks").asInt(),
            json.get("dst_interceptions").asInt(),
            json.get("dst_fumbles_recovered").asInt(),
            json.get("dst_defensive_tds").asInt(),
            json.get("dst_safeties").asInt(),
            json.get("dst_points_allowed").asInt()
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "points", points);
        first = JsonOut.field(sb, first, "q1_points", q1Points);
        first = JsonOut.field(sb, first, "q2_points", q2Points);
        first = JsonOut.field(sb, first, "q3_points", q3Points);
        first = JsonOut.field(sb, first, "q4_points", q4Points);
        first = JsonOut.field(sb, first, "ot_points", otPoints);
        first = JsonOut.field(sb, first, "plays", plays);
        first = JsonOut.field(sb, first, "pass_att", passAtt);
        first = JsonOut.field(sb, first, "pass_cmp", passCmp);
        first = JsonOut.field(sb, first, "pass_yd", passYd);
        first = JsonOut.field(sb, first, "pass_td", passTd);
        first = JsonOut.field(sb, first, "interceptions_thrown", interceptionsThrown);
        first = JsonOut.field(sb, first, "sacks_taken", sacksTaken);
        first = JsonOut.field(sb, first, "sack_yd_lost", sackYdLost);
        first = JsonOut.field(sb, first, "rush_att", rushAtt);
        first = JsonOut.field(sb, first, "rush_yd", rushYd);
        first = JsonOut.field(sb, first, "rush_td", rushTd);
        first = JsonOut.field(sb, first, "first_downs", firstDowns);
        first = JsonOut.field(sb, first, "third_down_att", thirdDownAtt);
        first = JsonOut.field(sb, first, "third_down_conv", thirdDownConv);
        first = JsonOut.field(sb, first, "fourth_down_att", fourthDownAtt);
        first = JsonOut.field(sb, first, "fourth_down_conv", fourthDownConv);
        first = JsonOut.field(sb, first, "drives", drives);
        first = JsonOut.field(sb, first, "drive_tds", driveTds);
        first = JsonOut.field(sb, first, "drive_fgs", driveFgs);
        first = JsonOut.field(sb, first, "drive_missed_fgs", driveMissedFgs);
        first = JsonOut.field(sb, first, "drive_punts", drivePunts);
        first = JsonOut.field(sb, first, "drive_turnovers", driveTurnovers);
        first = JsonOut.field(sb, first, "drive_downs", driveDowns);
        first = JsonOut.field(sb, first, "drive_end_half", driveEndHalf);
        first = JsonOut.field(sb, first, "fg_att", fgAtt);
        first = JsonOut.field(sb, first, "fg_made", fgMade);
        first = JsonOut.field(sb, first, "xp_att", xpAtt);
        first = JsonOut.field(sb, first, "xp_made", xpMade);
        first = JsonOut.field(sb, first, "two_pt_att", twoPtAtt);
        first = JsonOut.field(sb, first, "two_pt_made", twoPtMade);
        first = JsonOut.field(sb, first, "penalties", penalties);
        first = JsonOut.field(sb, first, "penalty_yds", penaltyYds);
        first = JsonOut.field(sb, first, "time_of_possession_sec", timeOfPossessionSec);
        first = JsonOut.field(sb, first, "throwaways", throwaways);
        first = JsonOut.field(sb, first, "spikes", spikes);
        first = JsonOut.field(sb, first, "batted_passes", battedPasses);
        first = JsonOut.field(sb, first, "oob_untackled_plays", oobUntackledPlays);
        first = JsonOut.field(sb, first, "special_teams_returns", specialTeamsReturns);
        first = JsonOut.field(sb, first, "kickoffs_total", kickoffsTotal);
        first = JsonOut.field(sb, first, "kickoff_returns", kickoffReturns);
        first = JsonOut.field(sb, first, "kickoff_start_yardline_sum", kickoffStartYardlineSum);
        first = JsonOut.field(sb, first, "trailing_10plus_dropbacks", trailing10plusDropbacks);
        first = JsonOut.field(sb, first, "trailing_10plus_plays", trailing10plusPlays);
        first = JsonOut.field(sb, first, "neutral_dropbacks", neutralDropbacks);
        first = JsonOut.field(sb, first, "neutral_plays", neutralPlays);
        first = JsonOut.field(sb, first, "p11_snaps", p11Snaps);
        first = JsonOut.field(sb, first, "p12_snaps", p12Snaps);
        first = JsonOut.field(sb, first, "p21_snaps", p21Snaps);
        first = JsonOut.field(sb, first, "no_huddle_snaps", noHuddleSnaps);
        first = JsonOut.field(sb, first, "unassisted_team_sacks", unassistedTeamSacks);
        first = JsonOut.field(sb, first, "dst_pressures", dstPressures);
        first = JsonOut.field(sb, first, "pick_sixes_scored", pickSixesScored);
        first = JsonOut.field(sb, first, "fumble_ret_tds_scored", fumbleRetTdsScored);
        first = JsonOut.field(sb, first, "dst_sacks", dstSacks);
        first = JsonOut.field(sb, first, "dst_interceptions", dstInterceptions);
        first = JsonOut.field(sb, first, "dst_fumbles_recovered", dstFumblesRecovered);
        first = JsonOut.field(sb, first, "dst_defensive_tds", dstDefensiveTds);
        first = JsonOut.field(sb, first, "dst_safeties", dstSafeties);
        first = JsonOut.field(sb, first, "dst_points_allowed", dstPointsAllowed);
        sb.append('}');
        return sb.toString();
    }
}
