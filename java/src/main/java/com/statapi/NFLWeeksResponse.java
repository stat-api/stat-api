// DO NOT EDIT — generated from schema/api/sim/nfl_sim.yml by generate-sim-sdks.ts
package com.statapi;

import java.util.List;

/**
 * Response payload for GET /api/v1/nfl/weeks.
 *
 * @param season Requested NFL season year (default {@code 2026}).
 * @param availableSeasons Available NFL seasons in the database ({@code [2026, 2025, ...]}).
 * @param freeGameId Canonical free 2026 game ID ({@code 43418}, {@code 2026 Week 1 DEN @ KC}) unlocked in full for all tiers.
 * @param weeks Sorted list of week numbers available in {@code season}.
 * @param games Matchup summaries across all weeks in {@code season}.
 * @param quota the record quota of the call that returned this answer
 */
public record NFLWeeksResponse(
    Integer season,
    List<Integer> availableSeasons,
    Long freeGameId,
    List<Integer> weeks,
    List<NFLMatchupSummary> games,
    Quota quota
) implements JsonSerializable {

    public static NFLWeeksResponse fromJson(JsonValue json) {
        return fromJson(json, null);
    }

    public static NFLWeeksResponse fromJson(JsonValue json, Quota quota) {
        return new NFLWeeksResponse(
            json.get("season").asInt(),
            JsonIn.list(json.get("available_seasons"), JsonValue::asInt),
            json.get("free_game_id").asLong(),
            JsonIn.list(json.get("weeks"), JsonValue::asInt),
            JsonIn.list(json.get("games"), NFLMatchupSummary::fromJson),
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
