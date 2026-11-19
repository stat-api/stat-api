// DO NOT EDIT — generated from schema/api/sim/nba_sim.yml by generate-sim-sdks.ts
package com.statapi;

import java.util.List;

/**
 * Full game and per-player empirical bell curve distributions ({@code output.distributions}).
 *
 * @param totalPoints Combined game total points distribution.
 * @param spreadMarginHome Home margin distribution ({@code home_pts - away_pts}).
 * @param homePoints Home team points distribution.
 * @param awayPoints Away team points distribution.
 * @param possessionsPerTeam Average possessions per team distribution.
 * @param homeThrees Home 3PM distribution.
 * @param awayThrees Away 3PM distribution.
 * @param homeRebounds Home total rebounds distribution.
 * @param awayRebounds Away total rebounds distribution.
 * @param players Per-player stat distributions.
 */
public record NBASimDistributionsSection(
    NBASimBellCurve totalPoints,
    NBASimBellCurve spreadMarginHome,
    NBASimBellCurve homePoints,
    NBASimBellCurve awayPoints,
    NBASimBellCurve possessionsPerTeam,
    NBASimBellCurve homeThrees,
    NBASimBellCurve awayThrees,
    NBASimBellCurve homeRebounds,
    NBASimBellCurve awayRebounds,
    List<NBAPlayerBellCurves> players
) implements JsonSerializable {

    public static NBASimDistributionsSection fromJson(JsonValue json) {
        return new NBASimDistributionsSection(
            JsonIn.obj(json.get("total_points"), NBASimBellCurve::fromJson),
            JsonIn.obj(json.get("spread_margin_home"), NBASimBellCurve::fromJson),
            JsonIn.obj(json.get("home_points"), NBASimBellCurve::fromJson),
            JsonIn.obj(json.get("away_points"), NBASimBellCurve::fromJson),
            JsonIn.obj(json.get("possessions_per_team"), NBASimBellCurve::fromJson),
            JsonIn.obj(json.get("home_threes"), NBASimBellCurve::fromJson),
            JsonIn.obj(json.get("away_threes"), NBASimBellCurve::fromJson),
            JsonIn.obj(json.get("home_rebounds"), NBASimBellCurve::fromJson),
            JsonIn.obj(json.get("away_rebounds"), NBASimBellCurve::fromJson),
            JsonIn.list(json.get("players"), NBAPlayerBellCurves::fromJson)
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
        first = JsonOut.field(sb, first, "possessions_per_team", possessionsPerTeam);
        first = JsonOut.field(sb, first, "home_threes", homeThrees);
        first = JsonOut.field(sb, first, "away_threes", awayThrees);
        first = JsonOut.field(sb, first, "home_rebounds", homeRebounds);
        first = JsonOut.field(sb, first, "away_rebounds", awayRebounds);
        first = JsonOut.field(sb, first, "players", players);
        sb.append('}');
        return sb.toString();
    }
}
