// DO NOT EDIT — generated from schema/api/sim/nfl_sim.yml by generate-sim-sdks.ts
package com.statapi;

/**
 * Single-draw player box score row inside SimGameScoreboxDraw.players.
 *
 * @param playerId Canonical player ID.
 * @param teamId Canonical team ID.
 * @param active True if the player was active on this draw.
 * @param snaps Offensive snaps played on this draw.
 * @param routes Pass routes run on this draw.
 * @param passCmp Pass completions.
 * @param passAtt Pass attempts.
 * @param passYd Passing yards.
 * @param passTd Passing touchdowns.
 * @param interception Interceptions thrown.
 * @param sacksTaken Times sacked.
 * @param carries Total rushing carries ({@code designed_rushes + scrambles + kneels}).
 * @param designedRushes Designed rushing carries.
 * @param scrambles QB scramble carries.
 * @param rushYd Rushing yards.
 * @param rushTd Rushing touchdowns.
 * @param targets Receiving targets.
 * @param receptions Receptions.
 * @param recYd Receiving yards.
 * @param recTd Receiving touchdowns.
 * @param airYd Total target air yards.
 * @param yacYd Total yards after catch.
 * @param fumblesLost Fumbles lost.
 * @param twoPtConv Successful 2-point conversions.
 * @param fgMade Field goals made.
 * @param fgAtt Field goals attempted.
 * @param xpMade Extra points made.
 * @param xpAtt Extra points attempted.
 * @param longestPass Longest completion in yards.
 * @param longestRush Longest rush in yards.
 * @param longestRec Longest reception in yards.
 */
public record NFLPlayerDrawBoxSummary(
    Long playerId,
    Long teamId,
    Boolean active,
    Integer snaps,
    Integer routes,
    Integer passCmp,
    Integer passAtt,
    Integer passYd,
    Integer passTd,
    Integer interception,
    Integer sacksTaken,
    Integer carries,
    Integer designedRushes,
    Integer scrambles,
    Integer rushYd,
    Integer rushTd,
    Integer targets,
    Integer receptions,
    Integer recYd,
    Integer recTd,
    Integer airYd,
    Integer yacYd,
    Integer fumblesLost,
    Integer twoPtConv,
    Integer fgMade,
    Integer fgAtt,
    Integer xpMade,
    Integer xpAtt,
    Integer longestPass,
    Integer longestRush,
    Integer longestRec
) implements JsonSerializable {

    public static NFLPlayerDrawBoxSummary fromJson(JsonValue json) {
        return new NFLPlayerDrawBoxSummary(
            json.get("player_id").asLong(),
            json.get("team_id").asLong(),
            json.get("active").asBoolean(),
            json.get("snaps").asInt(),
            json.get("routes").asInt(),
            json.get("pass_cmp").asInt(),
            json.get("pass_att").asInt(),
            json.get("pass_yd").asInt(),
            json.get("pass_td").asInt(),
            json.get("interception").asInt(),
            json.get("sacks_taken").asInt(),
            json.get("carries").asInt(),
            json.get("designed_rushes").asInt(),
            json.get("scrambles").asInt(),
            json.get("rush_yd").asInt(),
            json.get("rush_td").asInt(),
            json.get("targets").asInt(),
            json.get("receptions").asInt(),
            json.get("rec_yd").asInt(),
            json.get("rec_td").asInt(),
            json.get("air_yd").asInt(),
            json.get("yac_yd").asInt(),
            json.get("fumbles_lost").asInt(),
            json.get("two_pt_conv").asInt(),
            json.get("fg_made").asInt(),
            json.get("fg_att").asInt(),
            json.get("xp_made").asInt(),
            json.get("xp_att").asInt(),
            json.get("longest_pass").asInt(),
            json.get("longest_rush").asInt(),
            json.get("longest_rec").asInt()
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "player_id", playerId);
        first = JsonOut.field(sb, first, "team_id", teamId);
        first = JsonOut.field(sb, first, "active", active);
        first = JsonOut.field(sb, first, "snaps", snaps);
        first = JsonOut.field(sb, first, "routes", routes);
        first = JsonOut.field(sb, first, "pass_cmp", passCmp);
        first = JsonOut.field(sb, first, "pass_att", passAtt);
        first = JsonOut.field(sb, first, "pass_yd", passYd);
        first = JsonOut.field(sb, first, "pass_td", passTd);
        first = JsonOut.field(sb, first, "interception", interception);
        first = JsonOut.field(sb, first, "sacks_taken", sacksTaken);
        first = JsonOut.field(sb, first, "carries", carries);
        first = JsonOut.field(sb, first, "designed_rushes", designedRushes);
        first = JsonOut.field(sb, first, "scrambles", scrambles);
        first = JsonOut.field(sb, first, "rush_yd", rushYd);
        first = JsonOut.field(sb, first, "rush_td", rushTd);
        first = JsonOut.field(sb, first, "targets", targets);
        first = JsonOut.field(sb, first, "receptions", receptions);
        first = JsonOut.field(sb, first, "rec_yd", recYd);
        first = JsonOut.field(sb, first, "rec_td", recTd);
        first = JsonOut.field(sb, first, "air_yd", airYd);
        first = JsonOut.field(sb, first, "yac_yd", yacYd);
        first = JsonOut.field(sb, first, "fumbles_lost", fumblesLost);
        first = JsonOut.field(sb, first, "two_pt_conv", twoPtConv);
        first = JsonOut.field(sb, first, "fg_made", fgMade);
        first = JsonOut.field(sb, first, "fg_att", fgAtt);
        first = JsonOut.field(sb, first, "xp_made", xpMade);
        first = JsonOut.field(sb, first, "xp_att", xpAtt);
        first = JsonOut.field(sb, first, "longest_pass", longestPass);
        first = JsonOut.field(sb, first, "longest_rush", longestRush);
        first = JsonOut.field(sb, first, "longest_rec", longestRec);
        sb.append('}');
        return sb.toString();
    }
}
