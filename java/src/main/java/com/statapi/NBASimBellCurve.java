// DO NOT EDIT — generated from schema/api/sim/nba_sim.yml by generate-sim-sdks.ts
package com.statapi;

import java.util.List;

/**
 * Histogram and summary statistics for a single metric.
 *
 * @param metric Canonical metric slug.
 * @param label Human-readable chart label.
 * @param discrete True if integer-valued metric.
 * @param nDraws Draw count.
 * @param mean Empirical mean.
 * @param sd Empirical standard deviation.
 * @param skew Sample skewness.
 * @param min Minimum.
 * @param max Maximum.
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
 * @param vegasLine Vegas market line when applicable.
 * @param pOverVegas Empirical probability strictly above {@code vegas_line}.
 * @param bins Histogram buckets.
 */
public record NBASimBellCurve(
    String metric,
    String label,
    Boolean discrete,
    Integer nDraws,
    Double mean,
    Double sd,
    Double skew,
    Double min,
    Double max,
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
    Double vegasLine,
    Double pOverVegas,
    List<NBAHistogramBin> bins
) implements JsonSerializable {

    public static NBASimBellCurve fromJson(JsonValue json) {
        return new NBASimBellCurve(
            json.get("metric").asString(),
            json.get("label").asString(),
            json.get("discrete").asBoolean(),
            json.get("n_draws").asInt(),
            json.get("mean").asDouble(),
            json.get("sd").asDouble(),
            json.get("skew").asDouble(),
            json.get("min").asDouble(),
            json.get("max").asDouble(),
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
            json.get("vegas_line").asDouble(),
            json.get("p_over_vegas").asDouble(),
            JsonIn.list(json.get("bins"), NBAHistogramBin::fromJson)
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "metric", metric);
        first = JsonOut.field(sb, first, "label", label);
        first = JsonOut.field(sb, first, "discrete", discrete);
        first = JsonOut.field(sb, first, "n_draws", nDraws);
        first = JsonOut.field(sb, first, "mean", mean);
        first = JsonOut.field(sb, first, "sd", sd);
        first = JsonOut.field(sb, first, "skew", skew);
        first = JsonOut.field(sb, first, "min", min);
        first = JsonOut.field(sb, first, "max", max);
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
        first = JsonOut.field(sb, first, "vegas_line", vegasLine);
        first = JsonOut.field(sb, first, "p_over_vegas", pOverVegas);
        first = JsonOut.field(sb, first, "bins", bins);
        sb.append('}');
        return sb.toString();
    }
}
