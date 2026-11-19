// DO NOT EDIT — generated from schema/api/sim/nfl_sim.yml by generate-sim-sdks.ts
package com.statapi;

import java.util.List;

/**
 * Simple v1 team overrides (out list, pace, neutral pass rate) for {@code home} / {@code away} on public POST /api/v1/sim/nfl/run.
 *
 * @param out Canonical {@code player_id} values on this team to mark OUT ({@code active_prob = 0.0}). At least 1 active offensive player must remain.
 * @param expectedPlays Expected offensive plays in regulation/game ({@code 48..82}, alias {@code projected_plays}). (unit: plays; bounds: 48..82; aliases: projected_plays)
 * @param neutralPassRatePct Neutral-script dropback pass rate in {@code 0..100} percentage points ({@code 35..78}). (unit: percent_0_100; bounds: 35..78)
 */
public record NFLSimTeamOverride(
    List<Long> out,
    Double expectedPlays,
    Double neutralPassRatePct
) implements JsonSerializable {

    /** A request with no member set. */
    public NFLSimTeamOverride() {
        this(null, null, null);
    }

    /** A copy of this request with {@code out} set. */
    public NFLSimTeamOverride out(List<Long> value) {
        return new NFLSimTeamOverride(value, expectedPlays, neutralPassRatePct);
    }

    /** A copy of this request with {@code expected_plays} set. */
    public NFLSimTeamOverride expectedPlays(Double value) {
        return new NFLSimTeamOverride(out, value, neutralPassRatePct);
    }

    /** A copy of this request with {@code neutral_pass_rate_pct} set. */
    public NFLSimTeamOverride neutralPassRatePct(Double value) {
        return new NFLSimTeamOverride(out, expectedPlays, value);
    }

    public static NFLSimTeamOverride fromJson(JsonValue json) {
        return new NFLSimTeamOverride(
            JsonIn.list(json.get("out"), JsonValue::asLong),
            json.get("expected_plays").asDouble(),
            json.get("neutral_pass_rate_pct").asDouble()
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "out", out);
        first = JsonOut.field(sb, first, "expected_plays", expectedPlays);
        first = JsonOut.field(sb, first, "neutral_pass_rate_pct", neutralPassRatePct);
        sb.append('}');
        return sb.toString();
    }
}
