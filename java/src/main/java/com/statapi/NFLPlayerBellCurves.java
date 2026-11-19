// DO NOT EDIT — generated from schema/api/sim/nfl_sim.yml by generate-sim-sdks.ts
package com.statapi;

/**
 * Collection of Bell Curves for a single player inside output.distributions.players.
 *
 * @param playerId Canonical player ID.
 * @param teamId Canonical team ID.
 * @param position Skill position ({@code QB}, {@code RB}, {@code WR}, {@code TE}, {@code K}, {@code DST}).
 * @param depthRole Depth chart role ({@code QB1}, {@code RB1}, {@code WR1}, {@code TE1}, etc.).
 * @param passYd Passing yards bell curve (populated for QBs).
 * @param rushYd Rushing yards bell curve (populated for QBs, RBs, WRs).
 * @param recYd Receiving yards bell curve (populated for RBs, WRs, TEs).
 * @param receptions Discrete integer receptions bell curve.
 * @param targets Discrete integer targets bell curve.
 * @param carries Discrete integer rushing attempts bell curve.
 */
public record NFLPlayerBellCurves(
    Long playerId,
    Long teamId,
    String position,
    String depthRole,
    NFLSimBellCurve passYd,
    NFLSimBellCurve rushYd,
    NFLSimBellCurve recYd,
    NFLSimBellCurve receptions,
    NFLSimBellCurve targets,
    NFLSimBellCurve carries
) implements JsonSerializable {

    public static NFLPlayerBellCurves fromJson(JsonValue json) {
        return new NFLPlayerBellCurves(
            json.get("player_id").asLong(),
            json.get("team_id").asLong(),
            json.get("position").asString(),
            json.get("depth_role").asString(),
            JsonIn.obj(json.get("pass_yd"), NFLSimBellCurve::fromJson),
            JsonIn.obj(json.get("rush_yd"), NFLSimBellCurve::fromJson),
            JsonIn.obj(json.get("rec_yd"), NFLSimBellCurve::fromJson),
            JsonIn.obj(json.get("receptions"), NFLSimBellCurve::fromJson),
            JsonIn.obj(json.get("targets"), NFLSimBellCurve::fromJson),
            JsonIn.obj(json.get("carries"), NFLSimBellCurve::fromJson)
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "player_id", playerId);
        first = JsonOut.field(sb, first, "team_id", teamId);
        first = JsonOut.field(sb, first, "position", position);
        first = JsonOut.field(sb, first, "depth_role", depthRole);
        first = JsonOut.field(sb, first, "pass_yd", passYd);
        first = JsonOut.field(sb, first, "rush_yd", rushYd);
        first = JsonOut.field(sb, first, "rec_yd", recYd);
        first = JsonOut.field(sb, first, "receptions", receptions);
        first = JsonOut.field(sb, first, "targets", targets);
        first = JsonOut.field(sb, first, "carries", carries);
        sb.append('}');
        return sb.toString();
    }
}
