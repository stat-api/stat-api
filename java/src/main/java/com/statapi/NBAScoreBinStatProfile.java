// DO NOT EDIT — generated from schema/api/sim/nba_sim.yml by generate-sim-sdks.ts
package com.statapi;

/**
 * Conditional game-total quintile breakdown showing box-score scaling.
 *
 * @param quintileLabel Quintile label ({@code Q1}..{@code Q5}).
 * @param drawCount Number of draws in this quintile.
 * @param gamePtsMean Mean combined game points.
 * @param gameFgmMean Mean combined field goals made.
 * @param gameFg3mMean Mean combined three-pointers made.
 * @param gameFtmMean Mean combined free throws made.
 * @param gameRebMean Mean combined rebounds.
 * @param gameAstMean Mean combined assists.
 * @param gamePraMean Mean combined PRA.
 * @param gameDkPtsMean Mean combined DraftKings fantasy points.
 * @param gameFdPtsMean Mean combined FanDuel fantasy points.
 * @param players35plusDkMean Mean count of players scoring {@code >= 35} DraftKings points.
 */
public record NBAScoreBinStatProfile(
    String quintileLabel,
    Integer drawCount,
    Double gamePtsMean,
    Double gameFgmMean,
    Double gameFg3mMean,
    Double gameFtmMean,
    Double gameRebMean,
    Double gameAstMean,
    Double gamePraMean,
    Double gameDkPtsMean,
    Double gameFdPtsMean,
    Double players35plusDkMean
) implements JsonSerializable {

    public static NBAScoreBinStatProfile fromJson(JsonValue json) {
        return new NBAScoreBinStatProfile(
            json.get("quintile_label").asString(),
            json.get("draw_count").asInt(),
            json.get("game_pts_mean").asDouble(),
            json.get("game_fgm_mean").asDouble(),
            json.get("game_fg3m_mean").asDouble(),
            json.get("game_ftm_mean").asDouble(),
            json.get("game_reb_mean").asDouble(),
            json.get("game_ast_mean").asDouble(),
            json.get("game_pra_mean").asDouble(),
            json.get("game_dk_pts_mean").asDouble(),
            json.get("game_fd_pts_mean").asDouble(),
            json.get("players_35plus_dk_mean").asDouble()
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "quintile_label", quintileLabel);
        first = JsonOut.field(sb, first, "draw_count", drawCount);
        first = JsonOut.field(sb, first, "game_pts_mean", gamePtsMean);
        first = JsonOut.field(sb, first, "game_fgm_mean", gameFgmMean);
        first = JsonOut.field(sb, first, "game_fg3m_mean", gameFg3mMean);
        first = JsonOut.field(sb, first, "game_ftm_mean", gameFtmMean);
        first = JsonOut.field(sb, first, "game_reb_mean", gameRebMean);
        first = JsonOut.field(sb, first, "game_ast_mean", gameAstMean);
        first = JsonOut.field(sb, first, "game_pra_mean", gamePraMean);
        first = JsonOut.field(sb, first, "game_dk_pts_mean", gameDkPtsMean);
        first = JsonOut.field(sb, first, "game_fd_pts_mean", gameFdPtsMean);
        first = JsonOut.field(sb, first, "players_35plus_dk_mean", players35plusDkMean);
        sb.append('}');
        return sb.toString();
    }
}
