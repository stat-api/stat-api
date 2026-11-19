// DO NOT EDIT — generated from schema/api/sim/nba_sim.yml by generate-sim-sdks.ts
package com.statapi;

import java.util.List;

/**
 * Response payload for GET /api/v1/nba/weeks.
 *
 * @param season Requested NBA season year (default {@code 2026}).
 * @param availableSeasons Available NBA seasons in the database ({@code [2026, 2025, ...]}).
 * @param freeGameId Canonical free 2026 game ID ({@code 6029936}, {@code DET @ BOS}) unlocked in full for all tiers.
 * @param weeks Sorted list of modeled week numbers available in {@code season}.
 * @param games Matchup summaries across modeled NBA games in {@code season} (regular season, NBA Cup, play-in, and playoffs; never preseason).
 * @param quota the record quota of the call that returned this answer
 */
public record NBASimWeeksResponse(
    Integer season,
    List<Integer> availableSeasons,
    Long freeGameId,
    List<Integer> weeks,
    List<NBAMatchupSummary> games,
    Quota quota
) implements JsonSerializable {

    public static NBASimWeeksResponse fromJson(JsonValue json) {
        return fromJson(json, null);
    }

    public static NBASimWeeksResponse fromJson(JsonValue json, Quota quota) {
        return new NBASimWeeksResponse(
            json.get("season").asInt(),
            JsonIn.list(json.get("available_seasons"), JsonValue::asInt),
            json.get("free_game_id").asLong(),
            JsonIn.list(json.get("weeks"), JsonValue::asInt),
            JsonIn.list(json.get("games"), NBAMatchupSummary::fromJson),
            quota
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "season", season);
        first = JsonOut.field(sb, first, "available_seasons", availableSeasons);
        first = JsonOut.field(sb, first, "free_game_id", freeGameId);
        first = JsonOut.field(sb, first, "weeks", weeks);
        first = JsonOut.field(sb, first, "games", games);
        sb.append('}');
        return sb.toString();
    }
}
