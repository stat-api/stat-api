// DO NOT EDIT — generated from schema/api/sim/nfl_sim.yml by generate-sim-sdks.ts
package com.statapi;

import java.util.List;

/**
 * 30-bin empirical distribution with Gaussian + KDE smooth overlays, exact quantiles, Vegas threshold survival, and clickable sample_draw_idx per bin.
 *
 * @param metric Machine metric key ({@code total_points}, {@code home_points}, {@code pass_yd}, {@code rec_yd}, etc.).
 * @param label Human-readable chart title.
 * @param discrete True if the metric is integer-valued ({@code receptions}, {@code targets}, {@code carries}).
 * @param nDraws Number of draws summarized in the distribution.
 * @param mean Empirical mean across all draws.
 * @param sd Empirical standard deviation.
 * @param skew Empirical third standardized moment (skewness).
 * @param min Minimum observed value across all draws.
 * @param max Maximum observed value across all draws.
 * @param p05 5th percentile.
 * @param p10 10th percentile.
 * @param p25 25th percentile.
 * @param p50 50th percentile (median).
 * @param p75 75th percentile.
 * @param p90 90th percentile.
 * @param p95 95th percentile.
 * @param vegasLine Reference Vegas prop or game line for vertical chart overlay ({@code null} when unpriced).
 * @param pOverVegas Simulated probability of exceeding {@code vegas_line} ({@code 0.0..1.0}).
 * @param bins Histogram bins with empirical probability, CDF, Gaussian density, KDE density, and representative {@code sample_draw_idx}.
 */
public record NFLSimBellCurve(
    String metric,
    String label,
    Boolean discrete,
    Integer nDraws,
    Double mean,
    Double sd,
    Double skew,
    Double min,
    Double max,
    Double p05,
    Double p10,
    Double p25,
    Double p50,
    Double p75,
    Double p90,
    Double p95,
    Double vegasLine,
    Double pOverVegas,
    List<NFLHistogramBin> bins
) implements JsonSerializable {

    public static NFLSimBellCurve fromJson(JsonValue json) {
        return new NFLSimBellCurve(
            json.get("metric").asString(),
            json.get("label").asString(),
            json.get("discrete").asBoolean(),
            json.get("n_draws").asInt(),
            json.get("mean").asDouble(),
            json.get("sd").asDouble(),
            json.get("skew").asDouble(),
            json.get("min").asDouble(),
            json.get("max").asDouble(),
            json.get("p05").asDouble(),
            json.get("p10").asDouble(),
            json.get("p25").asDouble(),
            json.get("p50").asDouble(),
            json.get("p75").asDouble(),
            json.get("p90").asDouble(),
            json.get("p95").asDouble(),
            json.get("vegas_line").asDouble(),
            json.get("p_over_vegas").asDouble(),
            JsonIn.list(json.get("bins"), NFLHistogramBin::fromJson)
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
        first = JsonOut.field(sb, first, "p05", p05);
        first = JsonOut.field(sb, first, "p10", p10);
        first = JsonOut.field(sb, first, "p25", p25);
        first = JsonOut.field(sb, first, "p50", p50);
        first = JsonOut.field(sb, first, "p75", p75);
        first = JsonOut.field(sb, first, "p90", p90);
        first = JsonOut.field(sb, first, "p95", p95);
        first = JsonOut.field(sb, first, "vegas_line", vegasLine);
        first = JsonOut.field(sb, first, "p_over_vegas", pOverVegas);
        first = JsonOut.field(sb, first, "bins", bins);
        sb.append('}');
        return sb.toString();
    }
}
