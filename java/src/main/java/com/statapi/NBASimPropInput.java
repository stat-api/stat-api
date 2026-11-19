// DO NOT EDIT — generated from schema/api/sim/nba_sim.yml by generate-sim-sdks.ts
package com.statapi;

/**
 * Pre-tip NBA player prop market inputs attached in {@code output.scenarios.players[id].props}.
 *
 * @param points Points prop line.
 * @param rebounds Rebounds prop line.
 * @param assists Assists prop line.
 * @param threes Three-pointers made prop line.
 * @param pra PRA combo prop line.
 * @param minutes Minutes prop line.
 * @param steals Implied steals Poisson mean.
 * @param blocks Implied blocks Poisson mean.
 * @param doubleDoubleProb Implied double-double probability ({@code 0.0..1.0}).
 * @param pointsOverProb De-vigged over probability on points (omitted when null).
 * @param reboundsOverProb De-vigged over probability on rebounds (omitted when null).
 * @param assistsOverProb De-vigged over probability on assists (omitted when null).
 * @param threesOverProb De-vigged over probability on threes (omitted when null).
 * @param praOverProb De-vigged over probability on PRA (omitted when null).
 * @param capturedAgeMin Prop quote age in minutes before tipoff (omitted when null).
 * @param staleLine True if line was captured before a key teammate scratch.
 */
public record NBASimPropInput(
    Double points,
    Double rebounds,
    Double assists,
    Double threes,
    Double pra,
    Double minutes,
    Double steals,
    Double blocks,
    Double doubleDoubleProb,
    Double pointsOverProb,
    Double reboundsOverProb,
    Double assistsOverProb,
    Double threesOverProb,
    Double praOverProb,
    Double capturedAgeMin,
    Boolean staleLine
) implements JsonSerializable {

    public static NBASimPropInput fromJson(JsonValue json) {
        return new NBASimPropInput(
            json.get("points").asDouble(),
            json.get("rebounds").asDouble(),
            json.get("assists").asDouble(),
            json.get("threes").asDouble(),
            json.get("pra").asDouble(),
            json.get("minutes").asDouble(),
            json.get("steals").asDouble(),
            json.get("blocks").asDouble(),
            json.get("double_double_prob").asDouble(),
            json.get("points_over_prob").asDouble(),
            json.get("rebounds_over_prob").asDouble(),
            json.get("assists_over_prob").asDouble(),
            json.get("threes_over_prob").asDouble(),
            json.get("pra_over_prob").asDouble(),
            json.get("captured_age_min").asDouble(),
            json.get("stale_line").asBoolean()
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "points", points);
        first = JsonOut.field(sb, first, "rebounds", rebounds);
        first = JsonOut.field(sb, first, "assists", assists);
        first = JsonOut.field(sb, first, "threes", threes);
        first = JsonOut.field(sb, first, "pra", pra);
        first = JsonOut.field(sb, first, "minutes", minutes);
        first = JsonOut.field(sb, first, "steals", steals);
        first = JsonOut.field(sb, first, "blocks", blocks);
        first = JsonOut.field(sb, first, "double_double_prob", doubleDoubleProb);
        first = JsonOut.field(sb, first, "points_over_prob", pointsOverProb);
        first = JsonOut.field(sb, first, "rebounds_over_prob", reboundsOverProb);
        first = JsonOut.field(sb, first, "assists_over_prob", assistsOverProb);
        first = JsonOut.field(sb, first, "threes_over_prob", threesOverProb);
        first = JsonOut.field(sb, first, "pra_over_prob", praOverProb);
        first = JsonOut.field(sb, first, "captured_age_min", capturedAgeMin);
        first = JsonOut.field(sb, first, "stale_line", staleLine);
        sb.append('}');
        return sb.toString();
    }
}
