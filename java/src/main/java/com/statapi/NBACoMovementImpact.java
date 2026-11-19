// DO NOT EDIT — generated from schema/api/sim/nba_sim.yml by generate-sim-sdks.ts
package com.statapi;

/**
 * Conditional stat delta for a teammate when a focal player booms ({@code >= P80} DK points).
 *
 * @param teammatePlayerId Teammate canonical player ID.
 * @param teammateName Teammate display name.
 * @param teamCode Team abbreviation.
 * @param relation Positional/rotation relationship ({@code beneficiary} / {@code cannibalized}).
 * @param dkPtsDeltaWhenFocalBooms DraftKings fantasy points delta when focal booms.
 * @param ptsDeltaWhenFocalBooms Points delta when focal booms.
 * @param astDeltaWhenFocalBooms Assists delta when focal booms.
 * @param rebDeltaWhenFocalBooms Rebounds delta when focal booms.
 */
public record NBACoMovementImpact(
    Long teammatePlayerId,
    String teammateName,
    String teamCode,
    String relation,
    Double dkPtsDeltaWhenFocalBooms,
    Double ptsDeltaWhenFocalBooms,
    Double astDeltaWhenFocalBooms,
    Double rebDeltaWhenFocalBooms
) implements JsonSerializable {

    public static NBACoMovementImpact fromJson(JsonValue json) {
        return new NBACoMovementImpact(
            json.get("teammate_player_id").asLong(),
            json.get("teammate_name").asString(),
            json.get("team_code").asString(),
            json.get("relation").asString(),
            json.get("dk_pts_delta_when_focal_booms").asDouble(),
            json.get("pts_delta_when_focal_booms").asDouble(),
            json.get("ast_delta_when_focal_booms").asDouble(),
            json.get("reb_delta_when_focal_booms").asDouble()
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "teammate_player_id", teammatePlayerId);
        first = JsonOut.field(sb, first, "teammate_name", teammateName);
        first = JsonOut.field(sb, first, "team_code", teamCode);
        first = JsonOut.field(sb, first, "relation", relation);
        first = JsonOut.field(sb, first, "dk_pts_delta_when_focal_booms", dkPtsDeltaWhenFocalBooms);
        first = JsonOut.field(sb, first, "pts_delta_when_focal_booms", ptsDeltaWhenFocalBooms);
        first = JsonOut.field(sb, first, "ast_delta_when_focal_booms", astDeltaWhenFocalBooms);
        first = JsonOut.field(sb, first, "reb_delta_when_focal_booms", rebDeltaWhenFocalBooms);
        sb.append('}');
        return sb.toString();
    }
}
