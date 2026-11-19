// DO NOT EDIT — generated from schema/api/sim/nfl_sim.yml by generate-sim-sdks.ts
package com.statapi;

/**
 * Simple v1 per-player status, availability, usage, and flat Vegas prop line overrides for public POST /api/v1/sim/nfl/run.
 *
 * @param playerId Canonical NFL player ID on the home or away roster for this game.
 * @param out Explicitly force player out ({@code true}) or available ({@code false}).
 * @param status Injury designation ({@code OUT}, {@code DOUBTFUL}, {@code QUESTIONABLE}, {@code PROBABLE}, {@code ACTIVE}, {@code HEALTHY}, {@code AVAILABLE}, {@code GTD}; Rule 3.3: {@code DOUBTFUL} counts as out alongside {@code OUT}). (values: OUT | DOUBTFUL | QUESTIONABLE | PROBABLE | ACTIVE | HEALTHY | AVAILABLE | GTD)
 * @param playChancePct Pre-kickoff probability of playing ({@code 0..100}). (unit: percent_0_100; bounds: 0..100)
 * @param snapSharePct Target offensive snap share when active ({@code 2..100}). (unit: percent_0_100; bounds: 2..100)
 * @param passYd Passing yards prop line target ({@code >= 0}). (unit: yards; bounds: &gt;= 0)
 * @param passTd Passing touchdowns prop line target ({@code >= 0}). (unit: count; bounds: &gt;= 0)
 * @param rushYd Rushing yards prop line target ({@code >= 0}). (unit: yards; bounds: &gt;= 0)
 * @param recYd Receiving yards prop line target ({@code >= 0}). (unit: yards; bounds: &gt;= 0)
 * @param receptions Receptions prop line target ({@code >= 0}). (unit: count; bounds: &gt;= 0)
 * @param carries Rushing attempts prop line target ({@code >= 0}). (unit: count; bounds: &gt;= 0)
 * @param td Anytime / total touchdowns prop line target ({@code >= 0}). (unit: count; bounds: &gt;= 0)
 */
public record NFLSimPlayerOverride(
    Long playerId,
    Boolean out,
    String status,
    Double playChancePct,
    Double snapSharePct,
    Double passYd,
    Double passTd,
    Double rushYd,
    Double recYd,
    Double receptions,
    Double carries,
    Double td
) implements JsonSerializable {

    /** A request with the required members only. */
    public NFLSimPlayerOverride(long playerId) {
        this(playerId, null, null, null, null, null, null, null, null, null, null, null);
    }

    /** A copy of this request with {@code player_id} set. */
    public NFLSimPlayerOverride playerId(Long value) {
        return new NFLSimPlayerOverride(value, out, status, playChancePct, snapSharePct, passYd, passTd, rushYd, recYd, receptions, carries, td);
    }

    /** A copy of this request with {@code out} set. */
    public NFLSimPlayerOverride out(Boolean value) {
        return new NFLSimPlayerOverride(playerId, value, status, playChancePct, snapSharePct, passYd, passTd, rushYd, recYd, receptions, carries, td);
    }

    /** A copy of this request with {@code status} set. */
    public NFLSimPlayerOverride status(String value) {
        return new NFLSimPlayerOverride(playerId, out, value, playChancePct, snapSharePct, passYd, passTd, rushYd, recYd, receptions, carries, td);
    }

    /** A copy of this request with {@code play_chance_pct} set. */
    public NFLSimPlayerOverride playChancePct(Double value) {
        return new NFLSimPlayerOverride(playerId, out, status, value, snapSharePct, passYd, passTd, rushYd, recYd, receptions, carries, td);
    }

    /** A copy of this request with {@code snap_share_pct} set. */
    public NFLSimPlayerOverride snapSharePct(Double value) {
        return new NFLSimPlayerOverride(playerId, out, status, playChancePct, value, passYd, passTd, rushYd, recYd, receptions, carries, td);
    }

    /** A copy of this request with {@code pass_yd} set. */
    public NFLSimPlayerOverride passYd(Double value) {
        return new NFLSimPlayerOverride(playerId, out, status, playChancePct, snapSharePct, value, passTd, rushYd, recYd, receptions, carries, td);
    }

    /** A copy of this request with {@code pass_td} set. */
    public NFLSimPlayerOverride passTd(Double value) {
        return new NFLSimPlayerOverride(playerId, out, status, playChancePct, snapSharePct, passYd, value, rushYd, recYd, receptions, carries, td);
    }

    /** A copy of this request with {@code rush_yd} set. */
    public NFLSimPlayerOverride rushYd(Double value) {
        return new NFLSimPlayerOverride(playerId, out, status, playChancePct, snapSharePct, passYd, passTd, value, recYd, receptions, carries, td);
    }

    /** A copy of this request with {@code rec_yd} set. */
    public NFLSimPlayerOverride recYd(Double value) {
        return new NFLSimPlayerOverride(playerId, out, status, playChancePct, snapSharePct, passYd, passTd, rushYd, value, receptions, carries, td);
    }

    /** A copy of this request with {@code receptions} set. */
    public NFLSimPlayerOverride receptions(Double value) {
        return new NFLSimPlayerOverride(playerId, out, status, playChancePct, snapSharePct, passYd, passTd, rushYd, recYd, value, carries, td);
    }

    /** A copy of this request with {@code carries} set. */
    public NFLSimPlayerOverride carries(Double value) {
        return new NFLSimPlayerOverride(playerId, out, status, playChancePct, snapSharePct, passYd, passTd, rushYd, recYd, receptions, value, td);
    }

    /** A copy of this request with {@code td} set. */
    public NFLSimPlayerOverride td(Double value) {
        return new NFLSimPlayerOverride(playerId, out, status, playChancePct, snapSharePct, passYd, passTd, rushYd, recYd, receptions, carries, value);
    }

    public static NFLSimPlayerOverride fromJson(JsonValue json) {
        return new NFLSimPlayerOverride(
            json.get("player_id").asLong(),
            json.get("out").asBoolean(),
            json.get("status").asString(),
            json.get("play_chance_pct").asDouble(),
            json.get("snap_share_pct").asDouble(),
            json.get("pass_yd").asDouble(),
            json.get("pass_td").asDouble(),
            json.get("rush_yd").asDouble(),
            json.get("rec_yd").asDouble(),
            json.get("receptions").asDouble(),
            json.get("carries").asDouble(),
            json.get("td").asDouble()
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
        first = JsonOut.field(sb, first, "snap_share_pct", snapSharePct);
        first = JsonOut.field(sb, first, "pass_yd", passYd);
        first = JsonOut.field(sb, first, "pass_td", passTd);
        first = JsonOut.field(sb, first, "rush_yd", rushYd);
        first = JsonOut.field(sb, first, "rec_yd", recYd);
        first = JsonOut.field(sb, first, "receptions", receptions);
        first = JsonOut.field(sb, first, "carries", carries);
        first = JsonOut.field(sb, first, "td", td);
        sb.append('}');
        return sb.toString();
    }
}
