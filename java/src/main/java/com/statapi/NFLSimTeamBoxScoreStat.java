// DO NOT EDIT — generated from schema/api/sim/nfl_sim.yml by generate-sim-sdks.ts
package com.statapi;

/**
 * Sparse non-zero team offensive, drive, kicking, and DST box score stats (fields equal to {@code 0} are omitted).
 *
 * @param points Final points scored by the team on this scenario draw.
 * @param plays Total offensive plays ({@code pass_att + rush_att + sacks_taken}).
 * @param firstDowns Total offensive first downs earned.
 * @param totalYd Total net offensive yards ({@code pass_yd + rush_yd - sack_yd_lost}).
 * @param passCmp Team pass completions.
 * @param passAtt Team pass attempts.
 * @param passYd Team gross passing yards.
 * @param passTd Team passing touchdowns.
 * @param int_ Interceptions thrown.
 * @param sacksTaken Times the quarterback was sacked.
 * @param sackYdLost Yards lost on sacks.
 * @param rushAtt Team rushing attempts.
 * @param rushYd Team rushing yards.
 * @param rushTd Team rushing touchdowns.
 * @param fumblesLost Team offensive fumbles lost.
 * @param drives Total offensive drives.
 * @param driveTds Offensive drives ending in a touchdown.
 * @param fgMade Field goals made.
 * @param fgAtt Field goals attempted.
 * @param xpMade Extra points (PATs) made.
 * @param xpAtt Extra points (PATs) attempted.
 * @param twoPtMade Two-point conversions made.
 * @param twoPtAtt Two-point conversions attempted.
 * @param defSacks Defensive sacks recorded against the opponent.
 * @param defInt Defensive interceptions recorded.
 * @param defFumbleRec Opponent fumbles recovered by the defense.
 * @param defTd Defensive or special-teams return touchdowns scored.
 * @param safety Safeties recorded by the defense.
 * @param pointsAllowed Points allowed by the defense.
 */
public record NFLSimTeamBoxScoreStat(
    Integer points,
    Integer plays,
    Integer firstDowns,
    Integer totalYd,
    Integer passCmp,
    Integer passAtt,
    Integer passYd,
    Integer passTd,
    Integer int_,
    Integer sacksTaken,
    Integer sackYdLost,
    Integer rushAtt,
    Integer rushYd,
    Integer rushTd,
    Integer fumblesLost,
    Integer drives,
    Integer driveTds,
    Integer fgMade,
    Integer fgAtt,
    Integer xpMade,
    Integer xpAtt,
    Integer twoPtMade,
    Integer twoPtAtt,
    Integer defSacks,
    Integer defInt,
    Integer defFumbleRec,
    Integer defTd,
    Integer safety,
    Integer pointsAllowed
) implements JsonSerializable {

    public static NFLSimTeamBoxScoreStat fromJson(JsonValue json) {
        return new NFLSimTeamBoxScoreStat(
            json.get("points").asInt(),
            json.get("plays").asInt(),
            json.get("first_downs").asInt(),
            json.get("total_yd").asInt(),
            json.get("pass_cmp").asInt(),
            json.get("pass_att").asInt(),
            json.get("pass_yd").asInt(),
            json.get("pass_td").asInt(),
            json.get("int").asInt(),
            json.get("sacks_taken").asInt(),
            json.get("sack_yd_lost").asInt(),
            json.get("rush_att").asInt(),
            json.get("rush_yd").asInt(),
            json.get("rush_td").asInt(),
            json.get("fumbles_lost").asInt(),
            json.get("drives").asInt(),
            json.get("drive_tds").asInt(),
            json.get("fg_made").asInt(),
            json.get("fg_att").asInt(),
            json.get("xp_made").asInt(),
            json.get("xp_att").asInt(),
            json.get("two_pt_made").asInt(),
            json.get("two_pt_att").asInt(),
            json.get("def_sacks").asInt(),
            json.get("def_int").asInt(),
            json.get("def_fumble_rec").asInt(),
            json.get("def_td").asInt(),
            json.get("safety").asInt(),
            json.get("points_allowed").asInt()
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "points", points);
        first = JsonOut.field(sb, first, "plays", plays);
        first = JsonOut.field(sb, first, "first_downs", firstDowns);
        first = JsonOut.field(sb, first, "total_yd", totalYd);
        first = JsonOut.field(sb, first, "pass_cmp", passCmp);
        first = JsonOut.field(sb, first, "pass_att", passAtt);
        first = JsonOut.field(sb, first, "pass_yd", passYd);
        first = JsonOut.field(sb, first, "pass_td", passTd);
        first = JsonOut.field(sb, first, "int", int_);
        first = JsonOut.field(sb, first, "sacks_taken", sacksTaken);
        first = JsonOut.field(sb, first, "sack_yd_lost", sackYdLost);
        first = JsonOut.field(sb, first, "rush_att", rushAtt);
        first = JsonOut.field(sb, first, "rush_yd", rushYd);
        first = JsonOut.field(sb, first, "rush_td", rushTd);
        first = JsonOut.field(sb, first, "fumbles_lost", fumblesLost);
        first = JsonOut.field(sb, first, "drives", drives);
        first = JsonOut.field(sb, first, "drive_tds", driveTds);
        first = JsonOut.field(sb, first, "fg_made", fgMade);
        first = JsonOut.field(sb, first, "fg_att", fgAtt);
        first = JsonOut.field(sb, first, "xp_made", xpMade);
        first = JsonOut.field(sb, first, "xp_att", xpAtt);
        first = JsonOut.field(sb, first, "two_pt_made", twoPtMade);
        first = JsonOut.field(sb, first, "two_pt_att", twoPtAtt);
        first = JsonOut.field(sb, first, "def_sacks", defSacks);
        first = JsonOut.field(sb, first, "def_int", defInt);
        first = JsonOut.field(sb, first, "def_fumble_rec", defFumbleRec);
        first = JsonOut.field(sb, first, "def_td", defTd);
        first = JsonOut.field(sb, first, "safety", safety);
        first = JsonOut.field(sb, first, "points_allowed", pointsAllowed);
        sb.append('}');
        return sb.toString();
    }
}
