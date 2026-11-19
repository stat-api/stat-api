// DO NOT EDIT — generated from schema/api/sim/nba_sim.yml by generate-sim-sdks.ts
package com.statapi;

/**
 * Top-level normalized pre-tip player profile keyed by {@code player_id} in {@code output.scenarios.players}.
 *
 * @param playerId Canonical player ID.
 * @param playerName Player display name.
 * @param teamId Canonical team ID.
 * @param teamAbbr Team abbreviation.
 * @param position Court position.
 * @param depthRole Depth chart / rotation role.
 * @param isStarter True for projected starter.
 * @param active True when active pre-tip.
 * @param dkSalary DraftKings Classic salary (omitted when null).
 * @param fdSalary FanDuel Classic salary (omitted when null).
 * @param rotation Rotation and minutes inputs.
 * @param offense Usage and shooting split inputs.
 * @param boardsDefense Rebound, steal, and block rates.
 * @param injury Injury report status and play chance.
 * @param recent Trailing 10-game minutes context.
 * @param props Effective Vegas player props when present (omitted when null).
 */
public record NBASimScenarioPlayerProfile(
    Long playerId,
    String playerName,
    Long teamId,
    String teamAbbr,
    String position,
    String depthRole,
    Boolean isStarter,
    Boolean active,
    Integer dkSalary,
    Integer fdSalary,
    NBAPlayerRotationProfile rotation,
    NBAPlayerOffenseProfile offense,
    NBAPlayerReboundDefenseProfile boardsDefense,
    NBAPlayerInjuryProfile injury,
    NBAPlayerPreTipSituationProfile recent,
    NBASimPropInput props
) implements JsonSerializable {

    public static NBASimScenarioPlayerProfile fromJson(JsonValue json) {
        return new NBASimScenarioPlayerProfile(
            json.get("player_id").asLong(),
            json.get("player_name").asString(),
            json.get("team_id").asLong(),
            json.get("team_abbr").asString(),
            json.get("position").asString(),
            json.get("depth_role").asString(),
            json.get("is_starter").asBoolean(),
            json.get("active").asBoolean(),
            json.get("dk_salary").asInt(),
            json.get("fd_salary").asInt(),
            JsonIn.obj(json.get("rotation"), NBAPlayerRotationProfile::fromJson),
            JsonIn.obj(json.get("offense"), NBAPlayerOffenseProfile::fromJson),
            JsonIn.obj(json.get("boards_defense"), NBAPlayerReboundDefenseProfile::fromJson),
            JsonIn.obj(json.get("injury"), NBAPlayerInjuryProfile::fromJson),
            JsonIn.obj(json.get("recent"), NBAPlayerPreTipSituationProfile::fromJson),
            JsonIn.obj(json.get("props"), NBASimPropInput::fromJson)
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
        first = JsonOut.field(sb, first, "is_starter", isStarter);
        first = JsonOut.field(sb, first, "active", active);
        first = JsonOut.field(sb, first, "dk_salary", dkSalary);
        first = JsonOut.field(sb, first, "fd_salary", fdSalary);
        first = JsonOut.field(sb, first, "rotation", rotation);
        first = JsonOut.field(sb, first, "offense", offense);
        first = JsonOut.field(sb, first, "boards_defense", boardsDefense);
        first = JsonOut.field(sb, first, "injury", injury);
        first = JsonOut.field(sb, first, "recent", recent);
        first = JsonOut.field(sb, first, "props", props);
        sb.append('}');
        return sb.toString();
    }
}
