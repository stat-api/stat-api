// DO NOT EDIT — generated from schema/api/sim/nfl_sim.yml by generate-sim-sdks.ts
package com.statapi;

/**
 * Top-level player identity and football model traits lookup entry ({@code output.scenarios.players[player_id]}). Contains zero simulated outcome stats.
 *
 * @param playerId Canonical player ID.
 * @param playerName Player display name.
 * @param teamId Canonical team ID.
 * @param teamAbbr Team 2/3-letter abbreviation.
 * @param position Skill position ({@code QB}, {@code RB}, {@code WR}, {@code TE}, {@code K}).
 * @param depthRole Depth chart role ({@code QB1}, {@code RB1}, {@code WR1}, {@code TE1}, {@code K1}, etc.).
 * @param active True when the player is active for the game.
 * @param injuryStatus Game-day injury designation when present ({@code Questionable}, {@code Doubtful}, {@code Out}, etc.).
 * @param usage Snap, route, target, carry, and red-zone usage model inputs.
 * @param passing Quarterback passing and scramble skill model inputs ({@code null} for non-QBs).
 * @param receiving Receiving catch, air-yards, YAC, and broken-tackle model inputs.
 * @param rushing Rushing yards-before/after-contact and explosive-run model inputs.
 * @param athleticism Height, weight, speed, agility, and catch-radius athletic traits.
 * @param injury Games missed, pitch-count snap cap, and tissue-vulnerability model inputs.
 */
public record NFLSimScenarioPlayerProfile(
    Long playerId,
    String playerName,
    Long teamId,
    String teamAbbr,
    String position,
    String depthRole,
    Boolean active,
    String injuryStatus,
    NFLSimUsage usage,
    NFLSimPassing passing,
    NFLSimReceiving receiving,
    NFLSimRushing rushing,
    NFLSimAthleticism athleticism,
    NFLSimInjury injury
) implements JsonSerializable {

    public static NFLSimScenarioPlayerProfile fromJson(JsonValue json) {
        return new NFLSimScenarioPlayerProfile(
            json.get("player_id").asLong(),
            json.get("player_name").asString(),
            json.get("team_id").asLong(),
            json.get("team_abbr").asString(),
            json.get("position").asString(),
            json.get("depth_role").asString(),
            json.get("active").asBoolean(),
            json.get("injury_status").asString(),
            JsonIn.obj(json.get("usage"), NFLSimUsage::fromJson),
            JsonIn.obj(json.get("passing"), NFLSimPassing::fromJson),
            JsonIn.obj(json.get("receiving"), NFLSimReceiving::fromJson),
            JsonIn.obj(json.get("rushing"), NFLSimRushing::fromJson),
            JsonIn.obj(json.get("athleticism"), NFLSimAthleticism::fromJson),
            JsonIn.obj(json.get("injury"), NFLSimInjury::fromJson)
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "player_id", playerId);
        first = JsonOut.field(sb, first, "player_name", playerName);
        first = JsonOut.field(sb, first, "team_id", teamId);
        first = JsonOut.field(sb, first, "team_abbr", teamAbbr);
        first = JsonOut.field(sb, first, "position", position);
        first = JsonOut.field(sb, first, "depth_role", depthRole);
        first = JsonOut.field(sb, first, "active", active);
        first = JsonOut.field(sb, first, "injury_status", injuryStatus);
        first = JsonOut.field(sb, first, "usage", usage);
        first = JsonOut.field(sb, first, "passing", passing);
        first = JsonOut.field(sb, first, "receiving", receiving);
        first = JsonOut.field(sb, first, "rushing", rushing);
        first = JsonOut.field(sb, first, "athleticism", athleticism);
        first = JsonOut.field(sb, first, "injury", injury);
        sb.append('}');
        return sb.toString();
    }
}
