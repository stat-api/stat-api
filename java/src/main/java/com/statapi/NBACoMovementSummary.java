// DO NOT EDIT — generated from schema/api/sim/nba_sim.yml by generate-sim-sdks.ts
package com.statapi;

import java.util.List;

/**
 * Conditional teammate winners &amp; losers when a focal player clears P80 DraftKings points.
 *
 * @param focalPlayerId Focal player ID.
 * @param focalPlayerName Focal player name.
 * @param teamCode Team abbreviation.
 * @param boomThresholdDkPts P80 DraftKings fantasy points boom threshold.
 * @param winners Top positively correlated teammates when focal booms.
 * @param losers Top negatively correlated teammates when focal booms.
 */
public record NBACoMovementSummary(
    Long focalPlayerId,
    String focalPlayerName,
    String teamCode,
    Double boomThresholdDkPts,
    List<NBACoMovementImpact> winners,
    List<NBACoMovementImpact> losers
) implements JsonSerializable {

    public static NBACoMovementSummary fromJson(JsonValue json) {
        return new NBACoMovementSummary(
            json.get("focal_player_id").asLong(),
            json.get("focal_player_name").asString(),
            json.get("team_code").asString(),
            json.get("boom_threshold_dk_pts").asDouble(),
            JsonIn.list(json.get("winners"), NBACoMovementImpact::fromJson),
            JsonIn.list(json.get("losers"), NBACoMovementImpact::fromJson)
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "focal_player_id", focalPlayerId);
        first = JsonOut.field(sb, first, "focal_player_name", focalPlayerName);
        first = JsonOut.field(sb, first, "team_code", teamCode);
        first = JsonOut.field(sb, first, "boom_threshold_dk_pts", boomThresholdDkPts);
        first = JsonOut.field(sb, first, "winners", winners);
        first = JsonOut.field(sb, first, "losers", losers);
        sb.append('}');
        return sb.toString();
    }
}
