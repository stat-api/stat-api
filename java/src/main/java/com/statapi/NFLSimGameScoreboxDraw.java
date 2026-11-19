// DO NOT EDIT — generated from schema/api/sim/nfl_sim.yml by generate-sim-sdks.ts
package com.statapi;

import java.util.List;

/**
 * Complete single-game Scorebox for one simulated draw ({@code draw_idx}), including quarter linescores, team box scores, and player box scores.
 *
 * @param drawIdx 0-based Monte Carlo draw index ({@code 0 <= draw_idx < n_draws}).
 * @param archetype Archetype slug ({@code median}, {@code shootout}, {@code defensive_slugfest}, {@code home_blowout}, {@code away_blowout}, {@code overtime_thriller}, or {@code null}).
 * @param label Human-readable draw or archetype title.
 * @param awayLinescore Away team quarter linescore array {@code [Q1, Q2, Q3, Q4, OT, Total]}.
 * @param homeLinescore Home team quarter linescore array {@code [Q1, Q2, Q3, Q4, OT, Total]}.
 * @param totalPoints Combined final points ({@code home_team.points + away_team.points}).
 * @param finalMarginHome Final score margin from the home perspective ({@code home_team.points - away_team.points}).
 * @param q4MarginHome Score margin at the end of regulation ({@code 0} whenever {@code overtime_played = true}).
 * @param overtimePlayed True if regulation ended in a tie and overtime was played.
 * @param awayTeam Away team full offensive, drive, situational, and DST box score.
 * @param homeTeam Home team full offensive, drive, situational, and DST box score.
 * @param players Per-player single-draw box score rows for all players in the game.
 */
public record NFLSimGameScoreboxDraw(
    Integer drawIdx,
    String archetype,
    String label,
    List<Integer> awayLinescore,
    List<Integer> homeLinescore,
    Integer totalPoints,
    Integer finalMarginHome,
    Integer q4MarginHome,
    Boolean overtimePlayed,
    NFLTeamGameBoxScore awayTeam,
    NFLTeamGameBoxScore homeTeam,
    List<NFLPlayerDrawBoxSummary> players
) implements JsonSerializable {

    public static NFLSimGameScoreboxDraw fromJson(JsonValue json) {
        return new NFLSimGameScoreboxDraw(
            json.get("draw_idx").asInt(),
            json.get("archetype").asString(),
            json.get("label").asString(),
            JsonIn.list(json.get("away_linescore"), JsonValue::asInt),
            JsonIn.list(json.get("home_linescore"), JsonValue::asInt),
            json.get("total_points").asInt(),
            json.get("final_margin_home").asInt(),
            json.get("q4_margin_home").asInt(),
            json.get("overtime_played").asBoolean(),
            JsonIn.obj(json.get("away_team"), NFLTeamGameBoxScore::fromJson),
            JsonIn.obj(json.get("home_team"), NFLTeamGameBoxScore::fromJson),
            JsonIn.list(json.get("players"), NFLPlayerDrawBoxSummary::fromJson)
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
        first = JsonOut.field(sb, first, "q4_margin_home", q4MarginHome);
        first = JsonOut.field(sb, first, "overtime_played", overtimePlayed);
        first = JsonOut.field(sb, first, "away_team", awayTeam);
        first = JsonOut.field(sb, first, "home_team", homeTeam);
        first = JsonOut.field(sb, first, "players", players);
        sb.append('}');
        return sb.toString();
    }
}
