// DO NOT EDIT — generated from schema/api/sim/nfl_sim.yml by generate-sim-sdks.ts
package com.statapi;

/**
 * Sparse non-zero player offensive, kicking, and Individual Defensive Player (IDP) box score stats (fields equal to {@code 0} are omitted).
 *
 * @param snaps Snaps played.
 * @param routes Pass routes run.
 * @param passCmp Pass completions.
 * @param passAtt Pass attempts.
 * @param passYd Passing yards.
 * @param passTd Passing touchdowns.
 * @param int_ Interceptions thrown.
 * @param sacksTaken Times sacked.
 * @param sackYdLost Yards lost on sacks.
 * @param longestPass Longest completion in yards.
 * @param rushAtt Total rushing attempts ({@code designed_rushes + scrambles + kneels}).
 * @param designedRushes Designed rushing attempts.
 * @param scrambles QB scramble rushing attempts.
 * @param rushYd Rushing yards.
 * @param rushTd Rushing touchdowns.
 * @param longestRush Longest rush in yards.
 * @param targets Receiving targets.
 * @param rec Receptions.
 * @param recYd Receiving yards.
 * @param recTd Receiving touchdowns.
 * @param airYd Total target air yards.
 * @param yacYd Total yards after catch.
 * @param longestRec Longest reception in yards.
 * @param fumblesLost Fumbles lost.
 * @param twoPtConv Successful two-point conversions.
 * @param fgMade Field goals made.
 * @param fgAtt Field goals attempted.
 * @param xpMade Extra points (PATs) made.
 * @param xpAtt Extra points (PATs) attempted.
 * @param soloTackles Solo tackles recorded by a defensive player.
 * @param astTackles Assisted tackles recorded by a defensive player.
 * @param tfl Tackles for loss.
 * @param soloSacks Solo sacks ({@code 1.0} each).
 * @param splitSacks Split sacks ({@code 0.5} each).
 * @param sackYd Defensive sack yards credited.
 * @param qbHits Quarterback hits.
 * @param pressures Quarterback pressures.
 * @param targetsCoverage Pass targets into this defender's primary coverage.
 * @param receptionsAllowed Completions allowed in coverage.
 * @param recYdAllowed Receiving yards allowed in coverage.
 * @param passesDefended Passes defended (pass breakups + interceptions).
 * @param defInt Defensive interceptions caught.
 * @param forcedFumbles Forced fumbles.
 * @param fumbleRec Defensive fumble recoveries.
 * @param defTd Defensive touchdowns scored (pick-six or fumble return TD).
 */
public record NFLSimPlayerBoxScoreStat(
    Integer snaps,
    Integer routes,
    Integer passCmp,
    Integer passAtt,
    Integer passYd,
    Integer passTd,
    Integer int_,
    Integer sacksTaken,
    Integer sackYdLost,
    Integer longestPass,
    Integer rushAtt,
    Integer designedRushes,
    Integer scrambles,
    Integer rushYd,
    Integer rushTd,
    Integer longestRush,
    Integer targets,
    Integer rec,
    Integer recYd,
    Integer recTd,
    Integer airYd,
    Integer yacYd,
    Integer longestRec,
    Integer fumblesLost,
    Integer twoPtConv,
    Integer fgMade,
    Integer fgAtt,
    Integer xpMade,
    Integer xpAtt,
    Integer soloTackles,
    Integer astTackles,
    Integer tfl,
    Integer soloSacks,
    Integer splitSacks,
    Integer sackYd,
    Integer qbHits,
    Integer pressures,
    Integer targetsCoverage,
    Integer receptionsAllowed,
    Integer recYdAllowed,
    Integer passesDefended,
    Integer defInt,
    Integer forcedFumbles,
    Integer fumbleRec,
    Integer defTd
) implements JsonSerializable {

    public static NFLSimPlayerBoxScoreStat fromJson(JsonValue json) {
        return new NFLSimPlayerBoxScoreStat(
            json.get("snaps").asInt(),
            json.get("routes").asInt(),
            json.get("pass_cmp").asInt(),
            json.get("pass_att").asInt(),
            json.get("pass_yd").asInt(),
            json.get("pass_td").asInt(),
            json.get("int").asInt(),
            json.get("sacks_taken").asInt(),
            json.get("sack_yd_lost").asInt(),
            json.get("longest_pass").asInt(),
            json.get("rush_att").asInt(),
            json.get("designed_rushes").asInt(),
            json.get("scrambles").asInt(),
            json.get("rush_yd").asInt(),
            json.get("rush_td").asInt(),
            json.get("longest_rush").asInt(),
            json.get("targets").asInt(),
            json.get("rec").asInt(),
            json.get("rec_yd").asInt(),
            json.get("rec_td").asInt(),
            json.get("air_yd").asInt(),
            json.get("yac_yd").asInt(),
            json.get("longest_rec").asInt(),
            json.get("fumbles_lost").asInt(),
            json.get("two_pt_conv").asInt(),
            json.get("fg_made").asInt(),
            json.get("fg_att").asInt(),
            json.get("xp_made").asInt(),
            json.get("xp_att").asInt(),
            json.get("solo_tackles").asInt(),
            json.get("ast_tackles").asInt(),
            json.get("tfl").asInt(),
            json.get("solo_sacks").asInt(),
            json.get("split_sacks").asInt(),
            json.get("sack_yd").asInt(),
            json.get("qb_hits").asInt(),
            json.get("pressures").asInt(),
            json.get("targets_coverage").asInt(),
            json.get("receptions_allowed").asInt(),
            json.get("rec_yd_allowed").asInt(),
            json.get("passes_defended").asInt(),
            json.get("def_int").asInt(),
            json.get("forced_fumbles").asInt(),
            json.get("fumble_rec").asInt(),
            json.get("def_td").asInt()
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "snaps", snaps);
        first = JsonOut.field(sb, first, "routes", routes);
        first = JsonOut.field(sb, first, "pass_cmp", passCmp);
        first = JsonOut.field(sb, first, "pass_att", passAtt);
        first = JsonOut.field(sb, first, "pass_yd", passYd);
        first = JsonOut.field(sb, first, "pass_td", passTd);
        first = JsonOut.field(sb, first, "int", int_);
        first = JsonOut.field(sb, first, "sacks_taken", sacksTaken);
        first = JsonOut.field(sb, first, "sack_yd_lost", sackYdLost);
        first = JsonOut.field(sb, first, "longest_pass", longestPass);
        first = JsonOut.field(sb, first, "rush_att", rushAtt);
        first = JsonOut.field(sb, first, "designed_rushes", designedRushes);
        first = JsonOut.field(sb, first, "scrambles", scrambles);
        first = JsonOut.field(sb, first, "rush_yd", rushYd);
        first = JsonOut.field(sb, first, "rush_td", rushTd);
        first = JsonOut.field(sb, first, "longest_rush", longestRush);
        first = JsonOut.field(sb, first, "targets", targets);
        first = JsonOut.field(sb, first, "rec", rec);
        first = JsonOut.field(sb, first, "rec_yd", recYd);
        first = JsonOut.field(sb, first, "rec_td", recTd);
        first = JsonOut.field(sb, first, "air_yd", airYd);
        first = JsonOut.field(sb, first, "yac_yd", yacYd);
        first = JsonOut.field(sb, first, "longest_rec", longestRec);
        first = JsonOut.field(sb, first, "fumbles_lost", fumblesLost);
        first = JsonOut.field(sb, first, "two_pt_conv", twoPtConv);
        first = JsonOut.field(sb, first, "fg_made", fgMade);
        first = JsonOut.field(sb, first, "fg_att", fgAtt);
        first = JsonOut.field(sb, first, "xp_made", xpMade);
        first = JsonOut.field(sb, first, "xp_att", xpAtt);
        first = JsonOut.field(sb, first, "solo_tackles", soloTackles);
        first = JsonOut.field(sb, first, "ast_tackles", astTackles);
        first = JsonOut.field(sb, first, "tfl", tfl);
        first = JsonOut.field(sb, first, "solo_sacks", soloSacks);
        first = JsonOut.field(sb, first, "split_sacks", splitSacks);
        first = JsonOut.field(sb, first, "sack_yd", sackYd);
        first = JsonOut.field(sb, first, "qb_hits", qbHits);
        first = JsonOut.field(sb, first, "pressures", pressures);
        first = JsonOut.field(sb, first, "targets_coverage", targetsCoverage);
        first = JsonOut.field(sb, first, "receptions_allowed", receptionsAllowed);
        first = JsonOut.field(sb, first, "rec_yd_allowed", recYdAllowed);
        first = JsonOut.field(sb, first, "passes_defended", passesDefended);
        first = JsonOut.field(sb, first, "def_int", defInt);
        first = JsonOut.field(sb, first, "forced_fumbles", forcedFumbles);
        first = JsonOut.field(sb, first, "fumble_rec", fumbleRec);
        first = JsonOut.field(sb, first, "def_td", defTd);
        sb.append('}');
        return sb.toString();
    }
}
