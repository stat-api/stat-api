// DO NOT EDIT — generated from schema/api/sim/nba_sim.yml by generate-sim-sdks.ts
package com.statapi;

/**
 * Per-player atomic stat credit on an NBA play event.
 *
 * @param playerId Canonical player ID.
 * @param teamId Canonical team ID.
 * @param stat Stat category credited.
 * @param value Stat increment ({@code +1}, {@code +2}, {@code +3}).
 * @param points Points attributed.
 */
public record NBASimPlayStatCredit(
    Long playerId,
    Long teamId,
    String stat,
    Integer value,
    Integer points
) implements JsonSerializable {

    public static NBASimPlayStatCredit fromJson(JsonValue json) {
        return new NBASimPlayStatCredit(
            json.get("player_id").asLong(),
            json.get("team_id").asLong(),
            json.get("stat").asString(),
            json.get("value").asInt(),
            json.get("points").asInt()
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "player_id", playerId);
        first = JsonOut.field(sb, first, "team_id", teamId);
        first = JsonOut.field(sb, first, "stat", stat);
        first = JsonOut.field(sb, first, "value", value);
        first = JsonOut.field(sb, first, "points", points);
        sb.append('}');
        return sb.toString();
    }
}
