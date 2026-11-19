// DO NOT EDIT — generated from schema/api/sim/nfl_sim.yml by generate-sim-sdks.ts
package com.statapi;

import java.util.List;

/**
 * Symmetric pairwise Pearson correlation matrix ({@code r_{i,j} in [-1.0, +1.0]}) for active players (resolved via top-level {@code players} lookup) and both team DST units (negative {@code -team_id} sentinels).
 *
 * @param playerIds Ordered canonical {@code player_id} values (and negative {@code -team_id} sentinels for away/home DST) corresponding to the rows and columns of {@code matrix}.
 * @param matrix Symmetric correlation matrix ({@code matrix[i][j]} is the correlation between {@code player_ids[i]} and {@code player_ids[j]}).
 */
public record NFLSimPlayerCorrelations(
    List<Long> playerIds,
    List<List<Double>> matrix
) implements JsonSerializable {

    public static NFLSimPlayerCorrelations fromJson(JsonValue json) {
        return new NFLSimPlayerCorrelations(
            JsonIn.list(json.get("player_ids"), JsonValue::asLong),
            JsonIn.list(json.get("matrix"), v1 -> JsonIn.list(v1, JsonValue::asDouble))
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "player_ids", playerIds);
        first = JsonOut.field(sb, first, "matrix", matrix);
        sb.append('}');
        return sb.toString();
    }
}
