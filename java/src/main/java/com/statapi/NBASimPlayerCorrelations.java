// DO NOT EDIT — generated from schema/api/sim/nba_sim.yml by generate-sim-sdks.ts
package com.statapi;

import java.util.List;

/**
 * Symmetric pairwise Pearson correlation matrix across top rotation players.
 *
 * @param scope {@code global_rollout} for {@code scenario_id = 1} or {@code local_cluster_regime} for {@code scenario_id >= 2}.
 * @param playerIds Ordered {@code player_id} keys indexing rows and columns of {@code matrix}.
 * @param matrix Symmetric correlation matrix (elements with {@code |r| < 0.05} serialize as {@code null}).
 */
public record NBASimPlayerCorrelations(
    String scope,
    List<String> playerIds,
    List<List<Double>> matrix
) implements JsonSerializable {

    public static NBASimPlayerCorrelations fromJson(JsonValue json) {
        return new NBASimPlayerCorrelations(
            json.get("scope").asString(),
            JsonIn.list(json.get("player_ids"), JsonValue::asString),
            JsonIn.list(json.get("matrix"), v1 -> JsonIn.list(v1, JsonValue::asDouble))
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "scope", scope);
        first = JsonOut.field(sb, first, "player_ids", playerIds);
        first = JsonOut.field(sb, first, "matrix", matrix);
        sb.append('}');
        return sb.toString();
    }
}
