// DO NOT EDIT — generated from schema/api/sim/nba_sim.yml by generate-sim-sdks.ts
package com.statapi;

import java.util.List;

/**
 * Nested official-format NBA play-by-play embedded when {@code scenarios_pbp = true} or {@code simulations_pbp = true}.
 *
 * @param gamePossessions Possession summaries.
 * @param playByPlays Atomic play-by-play events.
 * @param lineupStints 5-man lineup stints.
 */
public record NBASimGamePbp(
    List<NBASimGamePossession> gamePossessions,
    List<NBASimPlayByPlay> playByPlays,
    List<NBASimLineupStint> lineupStints
) implements JsonSerializable {

    public static NBASimGamePbp fromJson(JsonValue json) {
        return new NBASimGamePbp(
            JsonIn.list(json.get("game_possessions"), NBASimGamePossession::fromJson),
            JsonIn.list(json.get("play_by_plays"), NBASimPlayByPlay::fromJson),
            JsonIn.list(json.get("lineup_stints"), NBASimLineupStint::fromJson)
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "game_possessions", gamePossessions);
        first = JsonOut.field(sb, first, "play_by_plays", playByPlays);
        first = JsonOut.field(sb, first, "lineup_stints", lineupStints);
        sb.append('}');
        return sb.toString();
    }
}
