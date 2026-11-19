// DO NOT EDIT — generated from schema/api/sim/nba_sim.yml by generate-sim-sdks.ts
package com.statapi;

/**
 * Stripped public v1 response returned by POST /api/v1/sim/nba/run.
 *
 * @param matchup Effective matchup summary reflecting any spread/total overrides.
 * @param inputSha256 SHA-256 fingerprint of the compiled simulation input pack.
 * @param output Monte Carlo simulation output holding the sections requested in {@code include}.
 * @param quota the record quota of the call that returned this answer
 */
public record NBASimPublicRunResponse(
    NBAMatchupSummary matchup,
    String inputSha256,
    NBASimGranularOutput output,
    Quota quota
) implements JsonSerializable {

    public static NBASimPublicRunResponse fromJson(JsonValue json) {
        return fromJson(json, null);
    }

    public static NBASimPublicRunResponse fromJson(JsonValue json, Quota quota) {
        return new NBASimPublicRunResponse(
            JsonIn.obj(json.get("matchup"), NBAMatchupSummary::fromJson),
            json.get("input_sha256").asString(),
            JsonIn.obj(json.get("output"), NBASimGranularOutput::fromJson),
            quota
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "matchup", matchup);
        first = JsonOut.field(sb, first, "input_sha256", inputSha256);
        first = JsonOut.field(sb, first, "output", output);
        sb.append('}');
        return sb.toString();
    }
}
