// DO NOT EDIT — generated from schema/api/sim/nba_sim.yml by generate-sim-sdks.ts
package com.statapi;

import java.util.List;

/**
 * 5-man lineup stint summary inside {@code pbp.lineup_stints}.
 *
 * @param stintNumber Sequential stint number.
 * @param teamId Team ID.
 * @param isHome True for home team.
 * @param period Period number.
 * @param playerIds Five players in stint.
 * @param packageTag Rotation package tag.
 * @param durationSeconds Elapsed seconds.
 * @param possessions Possessions played.
 * @param pointsFor Points scored.
 * @param pointsAgainst Points allowed.
 */
public record NBASimLineupStint(
    Integer stintNumber,
    Long teamId,
    Boolean isHome,
    Integer period,
    List<Long> playerIds,
    String packageTag,
    Double durationSeconds,
    Integer possessions,
    Integer pointsFor,
    Integer pointsAgainst
) implements JsonSerializable {

    public static NBASimLineupStint fromJson(JsonValue json) {
        return new NBASimLineupStint(
            json.get("stint_number").asInt(),
            json.get("team_id").asLong(),
            json.get("is_home").asBoolean(),
            json.get("period").asInt(),
            JsonIn.list(json.get("player_ids"), JsonValue::asLong),
            json.get("package_tag").asString(),
            json.get("duration_seconds").asDouble(),
            json.get("possessions").asInt(),
            json.get("points_for").asInt(),
            json.get("points_against").asInt()
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "stint_number", stintNumber);
        first = JsonOut.field(sb, first, "team_id", teamId);
        first = JsonOut.field(sb, first, "is_home", isHome);
        first = JsonOut.field(sb, first, "period", period);
        first = JsonOut.field(sb, first, "player_ids", playerIds);
        first = JsonOut.field(sb, first, "package_tag", packageTag);
        first = JsonOut.field(sb, first, "duration_seconds", durationSeconds);
        first = JsonOut.field(sb, first, "possessions", possessions);
        first = JsonOut.field(sb, first, "points_for", pointsFor);
        first = JsonOut.field(sb, first, "points_against", pointsAgainst);
        sb.append('}');
        return sb.toString();
    }
}
