// DO NOT EDIT — generated from schema/api/sim/nba_sim.yml by generate-sim-sdks.ts
package com.statapi;

/**
 * Pre-tip player rebounding and defensive event rates.
 *
 * @param orebRate Offensive rebound rate ({@code 0..100}).
 * @param drebRate Defensive rebound rate ({@code 0..100}).
 * @param stlRate Steal rate per 100 possessions ({@code 0..100}).
 * @param blkRate Block rate per 100 opponent 2PA ({@code 0..100}).
 */
public record NBAPlayerReboundDefenseProfile(
    Double orebRate,
    Double drebRate,
    Double stlRate,
    Double blkRate
) implements JsonSerializable {

    public static NBAPlayerReboundDefenseProfile fromJson(JsonValue json) {
        return new NBAPlayerReboundDefenseProfile(
            json.get("oreb_rate").asDouble(),
            json.get("dreb_rate").asDouble(),
            json.get("stl_rate").asDouble(),
            json.get("blk_rate").asDouble()
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "oreb_rate", orebRate);
        first = JsonOut.field(sb, first, "dreb_rate", drebRate);
        first = JsonOut.field(sb, first, "stl_rate", stlRate);
        first = JsonOut.field(sb, first, "blk_rate", blkRate);
        sb.append('}');
        return sb.toString();
    }
}
