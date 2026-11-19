// DO NOT EDIT — generated from schema/api/sim/nba_sim.yml by generate-sim-sdks.ts
package com.statapi;

/**
 * Top-level normalized pre-tip team profile keyed by {@code team_id} in {@code output.scenarios.teams}.
 *
 * @param teamId Canonical team ID.
 * @param teamAbbr Team abbreviation.
 * @param teamName Full team name.
 * @param isHome True for home team.
 * @param offense Pre-tip team offense profile.
 * @param defense Pre-tip team defense profile.
 * @param schedule Rest days context.
 */
public record NBASimScenarioTeamProfile(
    Long teamId,
    String teamAbbr,
    String teamName,
    Boolean isHome,
    NBATeamOffenseProfile offense,
    NBATeamDefenseProfile defense,
    NBAScheduleContextProfile schedule
) implements JsonSerializable {

    public static NBASimScenarioTeamProfile fromJson(JsonValue json) {
        return new NBASimScenarioTeamProfile(
            json.get("team_id").asLong(),
            json.get("team_abbr").asString(),
            json.get("team_name").asString(),
            json.get("is_home").asBoolean(),
            JsonIn.obj(json.get("offense"), NBATeamOffenseProfile::fromJson),
            JsonIn.obj(json.get("defense"), NBATeamDefenseProfile::fromJson),
            JsonIn.obj(json.get("schedule"), NBAScheduleContextProfile::fromJson)
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "team_id", teamId);
        first = JsonOut.field(sb, first, "team_abbr", teamAbbr);
        first = JsonOut.field(sb, first, "team_name", teamName);
        first = JsonOut.field(sb, first, "is_home", isHome);
        first = JsonOut.field(sb, first, "offense", offense);
        first = JsonOut.field(sb, first, "defense", defense);
        first = JsonOut.field(sb, first, "schedule", schedule);
        sb.append('}');
        return sb.toString();
    }
}
