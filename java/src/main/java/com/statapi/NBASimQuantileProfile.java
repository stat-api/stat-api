// DO NOT EDIT — generated from schema/api/sim/nba_sim.yml by generate-sim-sdks.ts
package com.statapi;

/**
 * Full non-parametric tail and median quantile profile.
 *
 * @param min Minimum simulated value.
 * @param p01 1st percentile.
 * @param p05 5th percentile.
 * @param p10 10th percentile.
 * @param p25 25th percentile.
 * @param p50 50th percentile (median).
 * @param p75 75th percentile.
 * @param p90 90th percentile.
 * @param p95 95th percentile.
 * @param p99 99th percentile.
 * @param p995 99.5th percentile.
 * @param max Maximum simulated value.
 * @param skew Sample skewness.
 */
public record NBASimQuantileProfile(
    Double min,
    Double p01,
    Double p05,
    Double p10,
    Double p25,
    Double p50,
    Double p75,
    Double p90,
    Double p95,
    Double p99,
    Double p995,
    Double max,
    Double skew
) implements JsonSerializable {

    public static NBASimQuantileProfile fromJson(JsonValue json) {
        return new NBASimQuantileProfile(
            json.get("min").asDouble(),
            json.get("p01").asDouble(),
            json.get("p05").asDouble(),
            json.get("p10").asDouble(),
            json.get("p25").asDouble(),
            json.get("p50").asDouble(),
            json.get("p75").asDouble(),
            json.get("p90").asDouble(),
            json.get("p95").asDouble(),
            json.get("p99").asDouble(),
            json.get("p99_5").asDouble(),
            json.get("max").asDouble(),
            json.get("skew").asDouble()
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "min", min);
        first = JsonOut.field(sb, first, "p01", p01);
        first = JsonOut.field(sb, first, "p05", p05);
        first = JsonOut.field(sb, first, "p10", p10);
        first = JsonOut.field(sb, first, "p25", p25);
        first = JsonOut.field(sb, first, "p50", p50);
        first = JsonOut.field(sb, first, "p75", p75);
        first = JsonOut.field(sb, first, "p90", p90);
        first = JsonOut.field(sb, first, "p95", p95);
        first = JsonOut.field(sb, first, "p99", p99);
        first = JsonOut.field(sb, first, "p99_5", p995);
        first = JsonOut.field(sb, first, "max", max);
        first = JsonOut.field(sb, first, "skew", skew);
        sb.append('}');
        return sb.toString();
    }
}
