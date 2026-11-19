// DO NOT EDIT — generated from schema/api/sim/nba_sim.yml by generate-sim-sdks.ts
package com.statapi;

import java.util.List;

/**
 * 5-man lineup stint row inside {@code NBATeamBoxScore.lineup_stints}.
 *
 * @param playerIds Five on-court player IDs.
 * @param packageTag Rotation stint package label.
 * @param minutesSeconds Stint duration in seconds.
 * @param possessions Possessions played during stint.
 * @param ptsFor Team points scored.
 * @param ptsAgainst Opponent points allowed.
 * @param plusMinus Stint net point margin.
 */
public record NBALineupStintBoxRow(
    List<Long> playerIds,
    String packageTag,
    Double minutesSeconds,
    Integer possessions,
    Integer ptsFor,
    Integer ptsAgainst,
    Integer plusMinus
) implements JsonSerializable {

    public static NBALineupStintBoxRow fromJson(JsonValue json) {
        return new NBALineupStintBoxRow(
            JsonIn.list(json.get("player_ids"), JsonValue::asLong),
            json.get("package_tag").asString(),
            json.get("minutes_seconds").asDouble(),
            json.get("possessions").asInt(),
            json.get("pts_for").asInt(),
            json.get("pts_against").asInt(),
            json.get("plus_minus").asInt()
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "player_ids", playerIds);
        first = JsonOut.field(sb, first, "package_tag", packageTag);
        first = JsonOut.field(sb, first, "minutes_seconds", minutesSeconds);
        first = JsonOut.field(sb, first, "possessions", possessions);
        first = JsonOut.field(sb, first, "pts_for", ptsFor);
        first = JsonOut.field(sb, first, "pts_against", ptsAgainst);
        first = JsonOut.field(sb, first, "plus_minus", plusMinus);
        sb.append('}');
        return sb.toString();
    }
}
