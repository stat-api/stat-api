// DO NOT EDIT — generated from schema/api/sim/nfl_sim.yml by generate-sim-sdks.ts
package com.statapi;

/**
 * One player's participation, role, and sparse non-zero stat attribution on a single play ({@code pbp.plays[j].play_stats[m]}).
 *
 * @param sequence 1-based participant sequence within {@code play_stats} ({@code 1..=k}).
 * @param playerId Canonical {@code player_id} (resolves via top-level {@code players} lookup).
 * @param teamId Canonical {@code team_id} of the player.
 * @param category Stat category ({@code passing}, {@code rushing}, {@code receiving}, {@code defense}, {@code kicking}).
 * @param role Player role on the play ({@code passer}, {@code receiver}, {@code rusher}, {@code kicker}, {@code coverage}, {@code pass_rusher}, {@code tackler}, {@code assist_tackler}, {@code interceptor}, {@code fumble_recovery}).
 * @param passAtt Pass attempts on the play.
 * @param passCmp Pass completions on the play.
 * @param passYd Passing yards on the play.
 * @param airYd Air yards on the play.
 * @param passTd Passing touchdowns on the play.
 * @param intThrown Interceptions thrown on the play.
 * @param sacksTaken Sacks taken on the play.
 * @param sackYdLost Sack yards lost on the play.
 * @param rushAtt Rushing attempts on the play.
 * @param rushYd Rushing yards on the play.
 * @param rushTd Rushing touchdowns on the play.
 * @param isScramble True when the rush was a QB scramble.
 * @param targets Receiving targets on the play.
 * @param rec Receptions on the play.
 * @param recYd Receiving yards on the play.
 * @param yacYd Yards after catch on the play.
 * @param recTd Receiving touchdowns on the play.
 * @param fumbles Fumbles on the play.
 * @param fumblesLost Fumbles lost on the play.
 * @param twoPtConv Successful two-point conversions on the play.
 * @param fgAtt Field goal attempts on the play.
 * @param fgMade Field goals made on the play.
 * @param xpAtt Extra point attempts on the play.
 * @param xpMade Extra points made on the play.
 * @param kickDistance Kick distance in yards.
 * @param soloTackles Solo tackles on the play.
 * @param astTackles Assisted tackles on the play.
 * @param soloSacks Solo sacks on the play.
 * @param splitSacks Split ({@code 0.5}) sacks on the play.
 * @param sackYd Defensive sack yards credited on the play.
 * @param tfl Tackles for loss on the play.
 * @param qbHits Quarterback hits on the play.
 * @param pressures Quarterback pressures on the play.
 * @param targetsCoverage Pass targets into this defender's primary coverage on the play.
 * @param receptionsAllowed Completions allowed in coverage on the play.
 * @param recYdAllowed Receiving yards allowed in coverage on the play.
 * @param passesDefended Passes defended on the play.
 * @param defInt Interceptions caught on the play.
 * @param forcedFumbles Forced fumbles on the play.
 * @param fumbleRec Fumble recoveries on the play.
 * @param defTd Defensive touchdowns scored on the play.
 * @param dkPts Single-play DraftKings fantasy points earned on the play.
 * @param fdPts Single-play FanDuel fantasy points earned on the play.
 */
public record NFLSimPlayStat(
    Integer sequence,
    Long playerId,
    Long teamId,
    String category,
    String role,
    Integer passAtt,
    Integer passCmp,
    Integer passYd,
    Integer airYd,
    Integer passTd,
    Integer intThrown,
    Integer sacksTaken,
    Integer sackYdLost,
    Integer rushAtt,
    Integer rushYd,
    Integer rushTd,
    Boolean isScramble,
    Integer targets,
    Integer rec,
    Integer recYd,
    Integer yacYd,
    Integer recTd,
    Integer fumbles,
    Integer fumblesLost,
    Integer twoPtConv,
    Integer fgAtt,
    Integer fgMade,
    Integer xpAtt,
    Integer xpMade,
    Integer kickDistance,
    Integer soloTackles,
    Integer astTackles,
    Integer soloSacks,
    Integer splitSacks,
    Integer sackYd,
    Integer tfl,
    Integer qbHits,
    Integer pressures,
    Integer targetsCoverage,
    Integer receptionsAllowed,
    Integer recYdAllowed,
    Integer passesDefended,
    Integer defInt,
    Integer forcedFumbles,
    Integer fumbleRec,
    Integer defTd,
    Double dkPts,
    Double fdPts
) implements JsonSerializable {

    public static NFLSimPlayStat fromJson(JsonValue json) {
        return new NFLSimPlayStat(
            json.get("sequence").asInt(),
            json.get("player_id").asLong(),
            json.get("team_id").asLong(),
            json.get("category").asString(),
            json.get("role").asString(),
            json.get("pass_att").asInt(),
            json.get("pass_cmp").asInt(),
            json.get("pass_yd").asInt(),
            json.get("air_yd").asInt(),
            json.get("pass_td").asInt(),
            json.get("int_thrown").asInt(),
            json.get("sacks_taken").asInt(),
            json.get("sack_yd_lost").asInt(),
            json.get("rush_att").asInt(),
            json.get("rush_yd").asInt(),
            json.get("rush_td").asInt(),
            json.get("is_scramble").asBoolean(),
            json.get("targets").asInt(),
            json.get("rec").asInt(),
            json.get("rec_yd").asInt(),
            json.get("yac_yd").asInt(),
            json.get("rec_td").asInt(),
            json.get("fumbles").asInt(),
            json.get("fumbles_lost").asInt(),
            json.get("two_pt_conv").asInt(),
            json.get("fg_att").asInt(),
            json.get("fg_made").asInt(),
            json.get("xp_att").asInt(),
            json.get("xp_made").asInt(),
            json.get("kick_distance").asInt(),
            json.get("solo_tackles").asInt(),
            json.get("ast_tackles").asInt(),
            json.get("solo_sacks").asInt(),
            json.get("split_sacks").asInt(),
            json.get("sack_yd").asInt(),
            json.get("tfl").asInt(),
            json.get("qb_hits").asInt(),
            json.get("pressures").asInt(),
            json.get("targets_coverage").asInt(),
            json.get("receptions_allowed").asInt(),
            json.get("rec_yd_allowed").asInt(),
            json.get("passes_defended").asInt(),
            json.get("def_int").asInt(),
            json.get("forced_fumbles").asInt(),
            json.get("fumble_rec").asInt(),
            json.get("def_td").asInt(),
            json.get("dk_pts").asDouble(),
            json.get("fd_pts").asDouble()
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "sequence", sequence);
        first = JsonOut.field(sb, first, "player_id", playerId);
        first = JsonOut.field(sb, first, "team_id", teamId);
        first = JsonOut.field(sb, first, "category", category);
        first = JsonOut.field(sb, first, "role", role);
        first = JsonOut.field(sb, first, "pass_att", passAtt);
        first = JsonOut.field(sb, first, "pass_cmp", passCmp);
        first = JsonOut.field(sb, first, "pass_yd", passYd);
        first = JsonOut.field(sb, first, "air_yd", airYd);
        first = JsonOut.field(sb, first, "pass_td", passTd);
        first = JsonOut.field(sb, first, "int_thrown", intThrown);
        first = JsonOut.field(sb, first, "sacks_taken", sacksTaken);
        first = JsonOut.field(sb, first, "sack_yd_lost", sackYdLost);
        first = JsonOut.field(sb, first, "rush_att", rushAtt);
        first = JsonOut.field(sb, first, "rush_yd", rushYd);
        first = JsonOut.field(sb, first, "rush_td", rushTd);
        first = JsonOut.field(sb, first, "is_scramble", isScramble);
        first = JsonOut.field(sb, first, "targets", targets);
        first = JsonOut.field(sb, first, "rec", rec);
        first = JsonOut.field(sb, first, "rec_yd", recYd);
        first = JsonOut.field(sb, first, "yac_yd", yacYd);
        first = JsonOut.field(sb, first, "rec_td", recTd);
        first = JsonOut.field(sb, first, "fumbles", fumbles);
        first = JsonOut.field(sb, first, "fumbles_lost", fumblesLost);
        first = JsonOut.field(sb, first, "two_pt_conv", twoPtConv);
        first = JsonOut.field(sb, first, "fg_att", fgAtt);
        first = JsonOut.field(sb, first, "fg_made", fgMade);
        first = JsonOut.field(sb, first, "xp_att", xpAtt);
        first = JsonOut.field(sb, first, "xp_made", xpMade);
        first = JsonOut.field(sb, first, "kick_distance", kickDistance);
        first = JsonOut.field(sb, first, "solo_tackles", soloTackles);
        first = JsonOut.field(sb, first, "ast_tackles", astTackles);
        first = JsonOut.field(sb, first, "solo_sacks", soloSacks);
        first = JsonOut.field(sb, first, "split_sacks", splitSacks);
        first = JsonOut.field(sb, first, "sack_yd", sackYd);
        first = JsonOut.field(sb, first, "tfl", tfl);
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
        first = JsonOut.field(sb, first, "dk_pts", dkPts);
        first = JsonOut.field(sb, first, "fd_pts", fdPts);
        sb.append('}');
        return sb.toString();
    }
}
