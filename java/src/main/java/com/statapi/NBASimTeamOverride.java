// DO NOT EDIT — generated from schema/api/sim/nba_sim.yml by generate-sim-sdks.ts
package com.statapi;

import java.util.List;

/**
 * Simple v1 team overrides (out list, pace, offensive/defensive ratings, rest days) for {@code home} / {@code away} on public POST /api/v1/sim/nba/run.
 *
 * @param out Canonical {@code player_id} values on this team to mark OUT. At least 5 available players must remain.
 * @param pace Expected possessions per 48 minutes ({@code 75..125}). (unit: possessions_per_48; bounds: 75..125)
 * @param offRating Offensive efficiency rating in points per 100 possessions ({@code 80..145}). (unit: pts_per_100; bounds: 80..145)
 * @param defRating Defensive rating allowed per 100 possessions ({@code 80..145}, lower is stronger defense). (unit: pts_per_100; bounds: 80..145)
 * @param restDays Days of rest prior to tipoff ({@code 0} = back-to-back, {@code 0..14}). (unit: days; bounds: 0..14)
 */
public record NBASimTeamOverride(
    List<Long> out,
    Double pace,
    Double offRating,
    Double defRating,
    Integer restDays
) implements JsonSerializable {

    /** A request with no member set. */
    public NBASimTeamOverride() {
        this(null, null, null, null, null);
    }

    /** A copy of this request with {@code out} set. */
    public NBASimTeamOverride out(List<Long> value) {
        return new NBASimTeamOverride(value, pace, offRating, defRating, restDays);
    }

    /** A copy of this request with {@code pace} set. */
    public NBASimTeamOverride pace(Double value) {
        return new NBASimTeamOverride(out, value, offRating, defRating, restDays);
    }

    /** A copy of this request with {@code off_rating} set. */
    public NBASimTeamOverride offRating(Double value) {
        return new NBASimTeamOverride(out, pace, value, defRating, restDays);
    }

    /** A copy of this request with {@code def_rating} set. */
    public NBASimTeamOverride defRating(Double value) {
        return new NBASimTeamOverride(out, pace, offRating, value, restDays);
    }

    /** A copy of this request with {@code rest_days} set. */
    public NBASimTeamOverride restDays(Integer value) {
        return new NBASimTeamOverride(out, pace, offRating, defRating, value);
    }

    public static NBASimTeamOverride fromJson(JsonValue json) {
        return new NBASimTeamOverride(
            JsonIn.list(json.get("out"), JsonValue::asLong),
            json.get("pace").asDouble(),
            json.get("off_rating").asDouble(),
            json.get("def_rating").asDouble(),
            json.get("rest_days").asInt()
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "out", out);
        first = JsonOut.field(sb, first, "pace", pace);
        first = JsonOut.field(sb, first, "off_rating", offRating);
        first = JsonOut.field(sb, first, "def_rating", defRating);
        first = JsonOut.field(sb, first, "rest_days", restDays);
        sb.append('}');
        return sb.toString();
    }
}
