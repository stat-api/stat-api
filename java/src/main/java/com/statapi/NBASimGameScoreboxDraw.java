// DO NOT EDIT — generated from schema/api/sim/nba_sim.yml by generate-sim-sdks.ts
package com.statapi;

import java.util.List;

/**
 * Complete quarter linescore and box score for one simulated NBA draw.
 *
 * @param drawIdx 0-based Monte Carlo draw index.
 * @param archetype Canonical archetype slug if applicable.
 * @param label Human-readable draw label.
 * @param awayLinescore {@code [Q1, Q2, Q3, Q4, OT, Total]} points for away team.
 * @param homeLinescore {@code [Q1, Q2, Q3, Q4, OT, Total]} points for home team.
 * @param totalPoints Combined final points.
 * @param finalMarginHome Home minus away margin.
 * @param overtimePlayed True when overtime occurred.
 * @param periodsPlayed Total periods played ({@code 4} in regulation).
 * @param awayTeam Away team box score.
 * @param homeTeam Home team box score.
 * @param players Formatted per-player box rows (present on archetype/traced draws; omitted on wire when empty).
 */
public record NBASimGameScoreboxDraw(
    Integer drawIdx,
    String archetype,
    String label,
    List<Integer> awayLinescore,
    List<Integer> homeLinescore,
    Integer totalPoints,
    Integer finalMarginHome,
    Boolean overtimePlayed,
    Integer periodsPlayed,
    NBATeamBoxScore awayTeam,
    NBATeamBoxScore homeTeam,
    List<NBAPlayerDrawBoxSummary> players
) implements JsonSerializable {

    public static NBASimGameScoreboxDraw fromJson(JsonValue json) {
        return new NBASimGameScoreboxDraw(
            json.get("draw_idx").asInt(),
            json.get("archetype").asString(),
            json.get("label").asString(),
            JsonIn.list(json.get("away_linescore"), JsonValue::asInt),
            JsonIn.list(json.get("home_linescore"), JsonValue::asInt),
            json.get("total_points").asInt(),
            json.get("final_margin_home").asInt(),
            json.get("overtime_played").asBoolean(),
            json.get("periods_played").asInt(),
            JsonIn.obj(json.get("away_team"), NBATeamBoxScore::fromJson),
            JsonIn.obj(json.get("home_team"), NBATeamBoxScore::fromJson),
            JsonIn.list(json.get("players"), NBAPlayerDrawBoxSummary::fromJson)
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "draw_idx", drawIdx);
        first = JsonOut.field(sb, first, "archetype", archetype);
        first = JsonOut.field(sb, first, "label", label);
        first = JsonOut.field(sb, first, "away_linescore", awayLinescore);
        first = JsonOut.field(sb, first, "home_linescore", homeLinescore);
        first = JsonOut.field(sb, first, "total_points", totalPoints);
        first = JsonOut.field(sb, first, "final_margin_home", finalMarginHome);
        first = JsonOut.field(sb, first, "overtime_played", overtimePlayed);
        first = JsonOut.field(sb, first, "periods_played", periodsPlayed);
        first = JsonOut.field(sb, first, "away_team", awayTeam);
        first = JsonOut.field(sb, first, "home_team", homeTeam);
        first = JsonOut.field(sb, first, "players", players);
        sb.append('}');
        return sb.toString();
    }
}
