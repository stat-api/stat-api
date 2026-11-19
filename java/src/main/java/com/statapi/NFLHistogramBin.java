// DO NOT EDIT — generated from schema/api/sim/nfl_sim.yml by generate-sim-sdks.ts
package com.statapi;

/**
 * Single histogram bin inside SimBellCurve.bins.
 *
 * @param xMin Inclusive lower bound of the bin.
 * @param xMax Upper bound of the bin.
 * @param xMid Midpoint of the bin ({@code 0.5 * (x_min + x_max)}).
 * @param count Number of simulated draws falling into this bin.
 * @param prob Empirical bin probability mass ({@code count / n_draws}).
 * @param cumProb Cumulative distribution function {@code P(X <= x_max)} ({@code 0.0..1.0}).
 * @param gaussianDensity Moment-matched Gaussian probability mass over this bin width for normal-curve comparison.
 * @param kdeDensity Silverman-bandwidth Gaussian Kernel Density Estimate (KDE) probability mass over this bin width.
 * @param sampleDrawIdx Representative {@code draw_idx} closest to {@code x_mid} inside this bin (pass in {@code include.pbp_draws} on {@code POST /api/v1/sim/nfl/run} to replay its play-by-play).
 */
public record NFLHistogramBin(
    Double xMin,
    Double xMax,
    Double xMid,
    Integer count,
    Double prob,
    Double cumProb,
    Double gaussianDensity,
    Double kdeDensity,
    Integer sampleDrawIdx
) implements JsonSerializable {

    public static NFLHistogramBin fromJson(JsonValue json) {
        return new NFLHistogramBin(
            json.get("x_min").asDouble(),
            json.get("x_max").asDouble(),
            json.get("x_mid").asDouble(),
            json.get("count").asInt(),
            json.get("prob").asDouble(),
            json.get("cum_prob").asDouble(),
            json.get("gaussian_density").asDouble(),
            json.get("kde_density").asDouble(),
            json.get("sample_draw_idx").asInt()
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "x_min", xMin);
        first = JsonOut.field(sb, first, "x_max", xMax);
        first = JsonOut.field(sb, first, "x_mid", xMid);
        first = JsonOut.field(sb, first, "count", count);
        first = JsonOut.field(sb, first, "prob", prob);
        first = JsonOut.field(sb, first, "cum_prob", cumProb);
        first = JsonOut.field(sb, first, "gaussian_density", gaussianDensity);
        first = JsonOut.field(sb, first, "kde_density", kdeDensity);
        first = JsonOut.field(sb, first, "sample_draw_idx", sampleDrawIdx);
        sb.append('}');
        return sb.toString();
    }
}
