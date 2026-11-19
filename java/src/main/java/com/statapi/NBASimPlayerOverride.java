// DO NOT EDIT — generated from schema/api/sim/nba_sim.yml by generate-sim-sdks.ts
package com.statapi;

/**
 * Simple v1 per-player availability, rotation role, minutes, and prop line overrides for public POST /api/v1/sim/nba/run.
 *
 * @param playerId Canonical NBA player ID on the home or away roster for this game.
 * @param out Explicitly force player out ({@code true}) or active in rotation ({@code false}).
 * @param status Injury designation ({@code OUT}, {@code DOUBTFUL}, {@code QUESTIONABLE}, {@code PROBABLE}, {@code ACTIVE}, {@code HEALTHY}, {@code AVAILABLE}, {@code GTD}; shorthand {@code O}/{@code D}/{@code Q}/{@code P} accepted). (values: OUT | DOUBTFUL | QUESTIONABLE | PROBABLE | ACTIVE | HEALTHY | AVAILABLE | GTD)
 * @param playChancePct Pre-tip probability of playing ({@code 0..100}). (unit: percent_0_100; bounds: 0..100)
 * @param role Rotation role ({@code starter}, {@code sixth_man}, {@code rotation}, {@code bench}, {@code out}). (values: starter | sixth_man | rotation | bench | out)
 * @param minutes Competitive-script baseline minutes override ({@code 0..48}). (unit: minutes; bounds: 0..48)
 * @param minutesCap Hard medical/coaching minutes restriction cap ({@code 0..48}). (unit: minutes; bounds: 0..48)
 * @param points Points prop line target ({@code >= 0}). (unit: points; bounds: &gt;= 0)
 * @param rebounds Rebounds prop line target ({@code >= 0}). (unit: rebounds; bounds: &gt;= 0)
 * @param assists Assists prop line target ({@code >= 0}). (unit: assists; bounds: &gt;= 0)
 * @param threes Three-pointers made prop line target ({@code >= 0}). (unit: threes; bounds: &gt;= 0)
 * @param pra Points + Rebounds + Assists combo prop line ({@code >= 0}). (unit: pra; bounds: &gt;= 0)
 * @param steals Expected steals target ({@code >= 0}). (unit: steals; bounds: &gt;= 0)
 * @param blocks Expected blocks target ({@code >= 0}). (unit: blocks; bounds: &gt;= 0)
 * @param doubleDoubleProb Implied double-double probability target ({@code 0.0..1.0}). (unit: ratio_0_1; bounds: 0..1)
 */
public record NBASimPlayerOverride(
    Long playerId,
    Boolean out,
    String status,
    Double playChancePct,
    String role,
    Double minutes,
    Double minutesCap,
    Double points,
    Double rebounds,
    Double assists,
    Double threes,
    Double pra,
    Double steals,
    Double blocks,
    Double doubleDoubleProb
) implements JsonSerializable {

    /** A request with the required members only. */
    public NBASimPlayerOverride(long playerId) {
        this(playerId, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
    }

    /** A copy of this request with {@code player_id} set. */
    public NBASimPlayerOverride playerId(Long value) {
        return new NBASimPlayerOverride(value, out, status, playChancePct, role, minutes, minutesCap, points, rebounds, assists, threes, pra, steals, blocks, doubleDoubleProb);
    }

    /** A copy of this request with {@code out} set. */
    public NBASimPlayerOverride out(Boolean value) {
        return new NBASimPlayerOverride(playerId, value, status, playChancePct, role, minutes, minutesCap, points, rebounds, assists, threes, pra, steals, blocks, doubleDoubleProb);
    }

    /** A copy of this request with {@code status} set. */
    public NBASimPlayerOverride status(String value) {
        return new NBASimPlayerOverride(playerId, out, value, playChancePct, role, minutes, minutesCap, points, rebounds, assists, threes, pra, steals, blocks, doubleDoubleProb);
    }

    /** A copy of this request with {@code play_chance_pct} set. */
    public NBASimPlayerOverride playChancePct(Double value) {
        return new NBASimPlayerOverride(playerId, out, status, value, role, minutes, minutesCap, points, rebounds, assists, threes, pra, steals, blocks, doubleDoubleProb);
    }

    /** A copy of this request with {@code role} set. */
    public NBASimPlayerOverride role(String value) {
        return new NBASimPlayerOverride(playerId, out, status, playChancePct, value, minutes, minutesCap, points, rebounds, assists, threes, pra, steals, blocks, doubleDoubleProb);
    }

    /** A copy of this request with {@code minutes} set. */
    public NBASimPlayerOverride minutes(Double value) {
        return new NBASimPlayerOverride(playerId, out, status, playChancePct, role, value, minutesCap, points, rebounds, assists, threes, pra, steals, blocks, doubleDoubleProb);
    }

    /** A copy of this request with {@code minutes_cap} set. */
    public NBASimPlayerOverride minutesCap(Double value) {
        return new NBASimPlayerOverride(playerId, out, status, playChancePct, role, minutes, value, points, rebounds, assists, threes, pra, steals, blocks, doubleDoubleProb);
    }

    /** A copy of this request with {@code points} set. */
    public NBASimPlayerOverride points(Double value) {
        return new NBASimPlayerOverride(playerId, out, status, playChancePct, role, minutes, minutesCap, value, rebounds, assists, threes, pra, steals, blocks, doubleDoubleProb);
    }

    /** A copy of this request with {@code rebounds} set. */
    public NBASimPlayerOverride rebounds(Double value) {
        return new NBASimPlayerOverride(playerId, out, status, playChancePct, role, minutes, minutesCap, points, value, assists, threes, pra, steals, blocks, doubleDoubleProb);
    }

    /** A copy of this request with {@code assists} set. */
    public NBASimPlayerOverride assists(Double value) {
        return new NBASimPlayerOverride(playerId, out, status, playChancePct, role, minutes, minutesCap, points, rebounds, value, threes, pra, steals, blocks, doubleDoubleProb);
    }

    /** A copy of this request with {@code threes} set. */
    public NBASimPlayerOverride threes(Double value) {
        return new NBASimPlayerOverride(playerId, out, status, playChancePct, role, minutes, minutesCap, points, rebounds, assists, value, pra, steals, blocks, doubleDoubleProb);
    }

    /** A copy of this request with {@code pra} set. */
    public NBASimPlayerOverride pra(Double value) {
        return new NBASimPlayerOverride(playerId, out, status, playChancePct, role, minutes, minutesCap, points, rebounds, assists, threes, value, steals, blocks, doubleDoubleProb);
    }

    /** A copy of this request with {@code steals} set. */
    public NBASimPlayerOverride steals(Double value) {
        return new NBASimPlayerOverride(playerId, out, status, playChancePct, role, minutes, minutesCap, points, rebounds, assists, threes, pra, value, blocks, doubleDoubleProb);
    }

    /** A copy of this request with {@code blocks} set. */
    public NBASimPlayerOverride blocks(Double value) {
        return new NBASimPlayerOverride(playerId, out, status, playChancePct, role, minutes, minutesCap, points, rebounds, assists, threes, pra, steals, value, doubleDoubleProb);
    }

    /** A copy of this request with {@code double_double_prob} set. */
    public NBASimPlayerOverride doubleDoubleProb(Double value) {
        return new NBASimPlayerOverride(playerId, out, status, playChancePct, role, minutes, minutesCap, points, rebounds, assists, threes, pra, steals, blocks, value);
    }

    public static NBASimPlayerOverride fromJson(JsonValue json) {
        return new NBASimPlayerOverride(
            json.get("player_id").asLong(),
            json.get("out").asBoolean(),
            json.get("status").asString(),
            json.get("play_chance_pct").asDouble(),
            json.get("role").asString(),
            json.get("minutes").asDouble(),
            json.get("minutes_cap").asDouble(),
            json.get("points").asDouble(),
            json.get("rebounds").asDouble(),
            json.get("assists").asDouble(),
            json.get("threes").asDouble(),
            json.get("pra").asDouble(),
            json.get("steals").asDouble(),
            json.get("blocks").asDouble(),
            json.get("double_double_prob").asDouble()
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.required(sb, first, "player_id", playerId);
        first = JsonOut.field(sb, first, "out", out);
        first = JsonOut.field(sb, first, "status", status);
        first = JsonOut.field(sb, first, "play_chance_pct", playChancePct);
        first = JsonOut.field(sb, first, "role", role);
        first = JsonOut.field(sb, first, "minutes", minutes);
        first = JsonOut.field(sb, first, "minutes_cap", minutesCap);
        first = JsonOut.field(sb, first, "points", points);
        first = JsonOut.field(sb, first, "rebounds", rebounds);
        first = JsonOut.field(sb, first, "assists", assists);
        first = JsonOut.field(sb, first, "threes", threes);
        first = JsonOut.field(sb, first, "pra", pra);
        first = JsonOut.field(sb, first, "steals", steals);
        first = JsonOut.field(sb, first, "blocks", blocks);
        first = JsonOut.field(sb, first, "double_double_prob", doubleDoubleProb);
        sb.append('}');
        return sb.toString();
    }
}
