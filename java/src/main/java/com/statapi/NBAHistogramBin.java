// DO NOT EDIT — generated from schema/api/sim/nba_sim.yml by generate-sim-sdks.ts
package com.statapi;

/**
 * One histogram bucket with empirical, Gaussian, and KDE densities.
 *
 * @param xMin Inclusive lower bucket boundary.
 * @param xMax Upper bucket boundary.
 * @param xMid Bucket midpoint.
 * @param count Draws landing in this bucket.
 * @param prob Empirical probability mass ({@code count / n_draws}).
 * @param cumProb Cumulative probability {@code P(X <= x_max)}.
 * @param gaussianDensity Fitted Gaussian density at {@code x_mid}.
 * @param kdeDensity Kernel density estimate at {@code x_mid}.
 * @param sampleDrawIdx Representative 0-based {@code draw_idx} inside this bin.
 */
public record NBAHistogramBin(
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

    public static NBAHistogramBin fromJson(JsonValue json) {
        return new NBAHistogramBin(
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
