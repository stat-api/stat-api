// DO NOT EDIT — generated from schema/api/sim/nba_sim.yml by generate-sim-sdks.ts
package com.statapi;

/**
 * Set of marginal bell curves for an active rotation player.
 *
 * @param playerId Canonical player ID.
 * @param name Player display name.
 * @param teamCode Team abbreviation.
 * @param isHome True for home team.
 * @param position Court position.
 * @param rotationRole Rotation role.
 * @param roleLabel Display role label.
 * @param dkPts DraftKings fantasy points distribution.
 * @param fdPts FanDuel fantasy points distribution.
 * @param minutes Minutes distribution.
 * @param points Points distribution.
 * @param rebounds Rebounds distribution.
 * @param assists Assists distribution.
 * @param threes Three-pointers made distribution.
 * @param pra PRA distribution.
 */
public record NBAPlayerBellCurves(
    Long playerId,
    String name,
    String teamCode,
    Boolean isHome,
    String position,
    String rotationRole,
    String roleLabel,
    NBASimBellCurve dkPts,
    NBASimBellCurve fdPts,
    NBASimBellCurve minutes,
    NBASimBellCurve points,
    NBASimBellCurve rebounds,
    NBASimBellCurve assists,
    NBASimBellCurve threes,
    NBASimBellCurve pra
) implements JsonSerializable {

    public static NBAPlayerBellCurves fromJson(JsonValue json) {
        return new NBAPlayerBellCurves(
            json.get("player_id").asLong(),
            json.get("name").asString(),
            json.get("team_code").asString(),
            json.get("is_home").asBoolean(),
            json.get("position").asString(),
            json.get("rotation_role").asString(),
            json.get("role_label").asString(),
            JsonIn.obj(json.get("dk_pts"), NBASimBellCurve::fromJson),
            JsonIn.obj(json.get("fd_pts"), NBASimBellCurve::fromJson),
            JsonIn.obj(json.get("minutes"), NBASimBellCurve::fromJson),
            JsonIn.obj(json.get("points"), NBASimBellCurve::fromJson),
            JsonIn.obj(json.get("rebounds"), NBASimBellCurve::fromJson),
            JsonIn.obj(json.get("assists"), NBASimBellCurve::fromJson),
            JsonIn.obj(json.get("threes"), NBASimBellCurve::fromJson),
            JsonIn.obj(json.get("pra"), NBASimBellCurve::fromJson)
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "player_id", playerId);
        first = JsonOut.field(sb, first, "name", name);
        first = JsonOut.field(sb, first, "team_code", teamCode);
        first = JsonOut.field(sb, first, "is_home", isHome);
        first = JsonOut.field(sb, first, "position", position);
        first = JsonOut.field(sb, first, "rotation_role", rotationRole);
        first = JsonOut.field(sb, first, "role_label", roleLabel);
        first = JsonOut.field(sb, first, "dk_pts", dkPts);
        first = JsonOut.field(sb, first, "fd_pts", fdPts);
        first = JsonOut.field(sb, first, "minutes", minutes);
        first = JsonOut.field(sb, first, "points", points);
        first = JsonOut.field(sb, first, "rebounds", rebounds);
        first = JsonOut.field(sb, first, "assists", assists);
        first = JsonOut.field(sb, first, "threes", threes);
        first = JsonOut.field(sb, first, "pra", pra);
        sb.append('}');
        return sb.toString();
    }
}
