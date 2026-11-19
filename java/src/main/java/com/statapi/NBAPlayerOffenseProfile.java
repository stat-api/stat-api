// DO NOT EDIT — generated from schema/api/sim/nba_sim.yml by generate-sim-sdks.ts
package com.statapi;

/**
 * Pre-tip player usage and shooting splits.
 *
 * @param usageRate Usage rate percentage ({@code 0..100}).
 * @param astRate Assist rate percentage ({@code 0..100}).
 * @param threeRate Three-point attempt share ({@code 0..100}).
 * @param ftRate Free throw attempt rate per FGA ({@code 0..100}).
 * @param twoPct Two-point field goal percentage ({@code 0..100}).
 * @param threePct Three-point field goal percentage ({@code 0..100}).
 * @param ftPct Free throw percentage ({@code 0..100}).
 * @param tovRate Turnover rate per 100 plays used ({@code 0..100}).
 */
public record NBAPlayerOffenseProfile(
    Double usageRate,
    Double astRate,
    Double threeRate,
    Double ftRate,
    Double twoPct,
    Double threePct,
    Double ftPct,
    Double tovRate
) implements JsonSerializable {

    public static NBAPlayerOffenseProfile fromJson(JsonValue json) {
        return new NBAPlayerOffenseProfile(
            json.get("usage_rate").asDouble(),
            json.get("ast_rate").asDouble(),
            json.get("three_rate").asDouble(),
            json.get("ft_rate").asDouble(),
            json.get("two_pct").asDouble(),
            json.get("three_pct").asDouble(),
            json.get("ft_pct").asDouble(),
            json.get("tov_rate").asDouble()
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "usage_rate", usageRate);
        first = JsonOut.field(sb, first, "ast_rate", astRate);
        first = JsonOut.field(sb, first, "three_rate", threeRate);
        first = JsonOut.field(sb, first, "ft_rate", ftRate);
        first = JsonOut.field(sb, first, "two_pct", twoPct);
        first = JsonOut.field(sb, first, "three_pct", threePct);
        first = JsonOut.field(sb, first, "ft_pct", ftPct);
        first = JsonOut.field(sb, first, "tov_rate", tovRate);
        sb.append('}');
        return sb.toString();
    }
}
