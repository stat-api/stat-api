// DO NOT EDIT — generated from schema/api/sim/nfl_sim.yml by generate-sim-sdks.ts
package com.statapi;

import java.util.List;

/**
 * The {@code distributions} section ({@code output.distributions}): macro game bell curves and per-player bell curves.
 *
 * @param totalPoints Bell curve of combined game points ({@code home_pts + away_pts}) with {@code vegas_line = total}.
 * @param spreadMarginHome Bell curve of home score margin ({@code home_pts - away_pts}) with {@code vegas_line = -spread_home}.
 * @param homePoints Bell curve of home team points scored.
 * @param awayPoints Bell curve of away team points scored.
 * @param combinedPlays Bell curve of combined offensive plays run by both teams.
 * @param homePassYd Bell curve of home team gross passing yards.
 * @param awayPassYd Bell curve of away team gross passing yards.
 * @param homeRushYd Bell curve of home team rushing yards.
 * @param awayRushYd Bell curve of away team rushing yards.
 * @param players Per-player bell curves across fantasy points and core stat categories.
 */
public record NFLSimDistributionsSection(
    NFLSimBellCurve totalPoints,
    NFLSimBellCurve spreadMarginHome,
    NFLSimBellCurve homePoints,
    NFLSimBellCurve awayPoints,
    NFLSimBellCurve combinedPlays,
    NFLSimBellCurve homePassYd,
    NFLSimBellCurve awayPassYd,
    NFLSimBellCurve homeRushYd,
    NFLSimBellCurve awayRushYd,
    List<NFLPlayerBellCurves> players
) implements JsonSerializable {

    public static NFLSimDistributionsSection fromJson(JsonValue json) {
        return new NFLSimDistributionsSection(
            JsonIn.obj(json.get("total_points"), NFLSimBellCurve::fromJson),
            JsonIn.obj(json.get("spread_margin_home"), NFLSimBellCurve::fromJson),
            JsonIn.obj(json.get("home_points"), NFLSimBellCurve::fromJson),
            JsonIn.obj(json.get("away_points"), NFLSimBellCurve::fromJson),
            JsonIn.obj(json.get("combined_plays"), NFLSimBellCurve::fromJson),
            JsonIn.obj(json.get("home_pass_yd"), NFLSimBellCurve::fromJson),
            JsonIn.obj(json.get("away_pass_yd"), NFLSimBellCurve::fromJson),
            JsonIn.obj(json.get("home_rush_yd"), NFLSimBellCurve::fromJson),
            JsonIn.obj(json.get("away_rush_yd"), NFLSimBellCurve::fromJson),
            JsonIn.list(json.get("players"), NFLPlayerBellCurves::fromJson)
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "total_points", totalPoints);
        first = JsonOut.field(sb, first, "spread_margin_home", spreadMarginHome);
        first = JsonOut.field(sb, first, "home_points", homePoints);
        first = JsonOut.field(sb, first, "away_points", awayPoints);
        first = JsonOut.field(sb, first, "combined_plays", combinedPlays);
        first = JsonOut.field(sb, first, "home_pass_yd", homePassYd);
        first = JsonOut.field(sb, first, "away_pass_yd", awayPassYd);
        first = JsonOut.field(sb, first, "home_rush_yd", homeRushYd);
        first = JsonOut.field(sb, first, "away_rush_yd", awayRushYd);
        first = JsonOut.field(sb, first, "players", players);
        sb.append('}');
        return sb.toString();
    }
}
