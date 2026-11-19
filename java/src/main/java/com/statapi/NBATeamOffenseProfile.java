// DO NOT EDIT — generated from schema/api/sim/nba_sim.yml by generate-sim-sdks.ts
package com.statapi;

/**
 * Pre-tip team offense ratings inside {@code output.scenarios.teams[id].offense}.
 *
 * @param pace Expected possessions per 48 minutes.
 * @param offRating Offensive rating (pts per 100 possessions).
 * @param orebRate Offensive rebound percentage ({@code 0..100}).
 * @param transitionRate Transition frequency ({@code 0..100}).
 */
public record NBATeamOffenseProfile(
    Double pace,
    Double offRating,
    Double orebRate,
    Double transitionRate
) implements JsonSerializable {

    public static NBATeamOffenseProfile fromJson(JsonValue json) {
        return new NBATeamOffenseProfile(
            json.get("pace").asDouble(),
            json.get("off_rating").asDouble(),
            json.get("oreb_rate").asDouble(),
            json.get("transition_rate").asDouble()
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "pace", pace);
        first = JsonOut.field(sb, first, "off_rating", offRating);
        first = JsonOut.field(sb, first, "oreb_rate", orebRate);
        first = JsonOut.field(sb, first, "transition_rate", transitionRate);
        sb.append('}');
        return sb.toString();
    }
}
