// DO NOT EDIT — generated from schema/api/sim/nfl_sim.yml by generate-sim-sdks.ts
package com.statapi;

import java.util.List;

/**
 * Zero-input + simple v1 override request body for public POST /api/v1/sim/nfl/run.
 *
 * @param gameId Game ID to simulate ({@code 43418} is free for all users). (bounds: &gt;= 1)
 * @param nDraws Number of Monte Carlo draws ({@code 50..10000}, default {@code 2500}, aliases {@code n_runs}, {@code n_sims}). (unit: draws; bounds: 50..10000; default: 2500; aliases: n_runs, n_sims)
 * @param seed Deterministic 64-bit CRN seed (default {@code 20261028}). (default: 20261028)
 * @param include Output sections to return ({@code summary}, {@code distributions}, {@code scorebox}, {@code pbp}, {@code scenarios}, {@code simulations}; alias {@code granularity}). (aliases: granularity)
 * @param scenarios Shorthand to enable {@code output.scenarios} and set {@code scenarios_limit} ({@code 1..5000}). (unit: count; bounds: 1..5000)
 * @param scenariosPbp Shorthand to include nested {@code pbp} inside each scenario.
 * @param simulations Shorthand to include {@code output.simulations}.
 * @param simulationsPbp Shorthand to include nested {@code pbp} inside each simulated game.
 * @param simulationsLimit Maximum number of simulated games in {@code output.simulations.games} ({@code 1..200}). (unit: count; bounds: 1..200)
 * @param homeScore Override implied home team points ({@code 6..48}, must agree with {@code away_score}/{@code total}/{@code home_spread} within {@code 0.5}). (unit: points; bounds: 6..48)
 * @param awayScore Override implied away team points ({@code 6..48}, must agree with {@code home_score}/{@code total}/{@code home_spread} within {@code 0.5}). (unit: points; bounds: 6..48)
 * @param total Override game total points ({@code 24..76}). (unit: points; bounds: 24..76)
 * @param homeSpread Override home spread ({@code away_score - home_score}, {@code -28..28}; negative = home favored). (unit: points; bounds: -28..28)
 * @param homeWinPct Override pre-kickoff home win probability in percent ({@code 2..98}). (unit: percent_0_100; bounds: 2..98)
 * @param mode Calibration regime ({@code all}, {@code top_props}, {@code game_lines}, {@code none}). (values: all | top_props | game_lines | none)
 * @param weather Optional stadium roof and weather overrides.
 * @param home Optional home team overrides ({@code out[]}, {@code expected_plays}, {@code neutral_pass_rate_pct}).
 * @param away Optional away team overrides ({@code out[]}, {@code expected_plays}, {@code neutral_pass_rate_pct}).
 * @param players Per-player status, play-chance, snap-share, and prop line overrides.
 */
public record NFLSimPublicRunRequest(
    Long gameId,
    Integer nDraws,
    Long seed,
    NFLSimInclude include,
    Integer scenarios,
    Boolean scenariosPbp,
    Boolean simulations,
    Boolean simulationsPbp,
    Integer simulationsLimit,
    Double homeScore,
    Double awayScore,
    Double total,
    Double homeSpread,
    Double homeWinPct,
    String mode,
    NFLSimWeatherOverride weather,
    NFLSimTeamOverride home,
    NFLSimTeamOverride away,
    List<NFLSimPlayerOverride> players
) implements JsonSerializable {

    /** An empty override request for path-bound {@code game_id} endpoints. */
    public NFLSimPublicRunRequest() {
        this(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
    }

    /** A request with the required members only. */
    public NFLSimPublicRunRequest(long gameId) {
        this(gameId, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
    }

    /** A copy of this request with {@code game_id} set. */
    public NFLSimPublicRunRequest gameId(Long value) {
        return new NFLSimPublicRunRequest(value, nDraws, seed, include, scenarios, scenariosPbp, simulations, simulationsPbp, simulationsLimit, homeScore, awayScore, total, homeSpread, homeWinPct, mode, weather, home, away, players);
    }

    /** A copy of this request with {@code n_draws} set. */
    public NFLSimPublicRunRequest nDraws(Integer value) {
        return new NFLSimPublicRunRequest(gameId, value, seed, include, scenarios, scenariosPbp, simulations, simulationsPbp, simulationsLimit, homeScore, awayScore, total, homeSpread, homeWinPct, mode, weather, home, away, players);
    }

    /** A copy of this request with {@code seed} set. */
    public NFLSimPublicRunRequest seed(Long value) {
        return new NFLSimPublicRunRequest(gameId, nDraws, value, include, scenarios, scenariosPbp, simulations, simulationsPbp, simulationsLimit, homeScore, awayScore, total, homeSpread, homeWinPct, mode, weather, home, away, players);
    }

    /** A copy of this request with {@code include} set. */
    public NFLSimPublicRunRequest include(NFLSimInclude value) {
        return new NFLSimPublicRunRequest(gameId, nDraws, seed, value, scenarios, scenariosPbp, simulations, simulationsPbp, simulationsLimit, homeScore, awayScore, total, homeSpread, homeWinPct, mode, weather, home, away, players);
    }

    /** A copy of this request with {@code scenarios} set. */
    public NFLSimPublicRunRequest scenarios(Integer value) {
        return new NFLSimPublicRunRequest(gameId, nDraws, seed, include, value, scenariosPbp, simulations, simulationsPbp, simulationsLimit, homeScore, awayScore, total, homeSpread, homeWinPct, mode, weather, home, away, players);
    }

    /** A copy of this request with {@code scenarios_pbp} set. */
    public NFLSimPublicRunRequest scenariosPbp(Boolean value) {
        return new NFLSimPublicRunRequest(gameId, nDraws, seed, include, scenarios, value, simulations, simulationsPbp, simulationsLimit, homeScore, awayScore, total, homeSpread, homeWinPct, mode, weather, home, away, players);
    }

    /** A copy of this request with {@code simulations} set. */
    public NFLSimPublicRunRequest simulations(Boolean value) {
        return new NFLSimPublicRunRequest(gameId, nDraws, seed, include, scenarios, scenariosPbp, value, simulationsPbp, simulationsLimit, homeScore, awayScore, total, homeSpread, homeWinPct, mode, weather, home, away, players);
    }

    /** A copy of this request with {@code simulations_pbp} set. */
    public NFLSimPublicRunRequest simulationsPbp(Boolean value) {
        return new NFLSimPublicRunRequest(gameId, nDraws, seed, include, scenarios, scenariosPbp, simulations, value, simulationsLimit, homeScore, awayScore, total, homeSpread, homeWinPct, mode, weather, home, away, players);
    }

    /** A copy of this request with {@code simulations_limit} set. */
    public NFLSimPublicRunRequest simulationsLimit(Integer value) {
        return new NFLSimPublicRunRequest(gameId, nDraws, seed, include, scenarios, scenariosPbp, simulations, simulationsPbp, value, homeScore, awayScore, total, homeSpread, homeWinPct, mode, weather, home, away, players);
    }

    /** A copy of this request with {@code home_score} set. */
    public NFLSimPublicRunRequest homeScore(Double value) {
        return new NFLSimPublicRunRequest(gameId, nDraws, seed, include, scenarios, scenariosPbp, simulations, simulationsPbp, simulationsLimit, value, awayScore, total, homeSpread, homeWinPct, mode, weather, home, away, players);
    }

    /** A copy of this request with {@code away_score} set. */
    public NFLSimPublicRunRequest awayScore(Double value) {
        return new NFLSimPublicRunRequest(gameId, nDraws, seed, include, scenarios, scenariosPbp, simulations, simulationsPbp, simulationsLimit, homeScore, value, total, homeSpread, homeWinPct, mode, weather, home, away, players);
    }

    /** A copy of this request with {@code total} set. */
    public NFLSimPublicRunRequest total(Double value) {
        return new NFLSimPublicRunRequest(gameId, nDraws, seed, include, scenarios, scenariosPbp, simulations, simulationsPbp, simulationsLimit, homeScore, awayScore, value, homeSpread, homeWinPct, mode, weather, home, away, players);
    }

    /** A copy of this request with {@code home_spread} set. */
    public NFLSimPublicRunRequest homeSpread(Double value) {
        return new NFLSimPublicRunRequest(gameId, nDraws, seed, include, scenarios, scenariosPbp, simulations, simulationsPbp, simulationsLimit, homeScore, awayScore, total, value, homeWinPct, mode, weather, home, away, players);
    }

    /** A copy of this request with {@code home_win_pct} set. */
    public NFLSimPublicRunRequest homeWinPct(Double value) {
        return new NFLSimPublicRunRequest(gameId, nDraws, seed, include, scenarios, scenariosPbp, simulations, simulationsPbp, simulationsLimit, homeScore, awayScore, total, homeSpread, value, mode, weather, home, away, players);
    }

    /** A copy of this request with {@code mode} set. */
    public NFLSimPublicRunRequest mode(String value) {
        return new NFLSimPublicRunRequest(gameId, nDraws, seed, include, scenarios, scenariosPbp, simulations, simulationsPbp, simulationsLimit, homeScore, awayScore, total, homeSpread, homeWinPct, value, weather, home, away, players);
    }

    /** A copy of this request with {@code weather} set. */
    public NFLSimPublicRunRequest weather(NFLSimWeatherOverride value) {
        return new NFLSimPublicRunRequest(gameId, nDraws, seed, include, scenarios, scenariosPbp, simulations, simulationsPbp, simulationsLimit, homeScore, awayScore, total, homeSpread, homeWinPct, mode, value, home, away, players);
    }

    /** A copy of this request with {@code home} set. */
    public NFLSimPublicRunRequest home(NFLSimTeamOverride value) {
        return new NFLSimPublicRunRequest(gameId, nDraws, seed, include, scenarios, scenariosPbp, simulations, simulationsPbp, simulationsLimit, homeScore, awayScore, total, homeSpread, homeWinPct, mode, weather, value, away, players);
    }

    /** A copy of this request with {@code away} set. */
    public NFLSimPublicRunRequest away(NFLSimTeamOverride value) {
        return new NFLSimPublicRunRequest(gameId, nDraws, seed, include, scenarios, scenariosPbp, simulations, simulationsPbp, simulationsLimit, homeScore, awayScore, total, homeSpread, homeWinPct, mode, weather, home, value, players);
    }

    /** A copy of this request with {@code players} set. */
    public NFLSimPublicRunRequest players(List<NFLSimPlayerOverride> value) {
        return new NFLSimPublicRunRequest(gameId, nDraws, seed, include, scenarios, scenariosPbp, simulations, simulationsPbp, simulationsLimit, homeScore, awayScore, total, homeSpread, homeWinPct, mode, weather, home, away, value);
    }

    public static NFLSimPublicRunRequest fromJson(JsonValue json) {
        return new NFLSimPublicRunRequest(
            json.get("game_id").asLong(),
            json.get("n_draws").asInt(),
            json.get("seed").asLong(),
            JsonIn.obj(json.get("include"), NFLSimInclude::fromJson),
            json.get("scenarios").asInt(),
            json.get("scenarios_pbp").asBoolean(),
            json.get("simulations").asBoolean(),
            json.get("simulations_pbp").asBoolean(),
            json.get("simulations_limit").asInt(),
            json.get("home_score").asDouble(),
            json.get("away_score").asDouble(),
            json.get("total").asDouble(),
            json.get("home_spread").asDouble(),
            json.get("home_win_pct").asDouble(),
            json.get("mode").asString(),
            JsonIn.obj(json.get("weather"), NFLSimWeatherOverride::fromJson),
            JsonIn.obj(json.get("home"), NFLSimTeamOverride::fromJson),
            JsonIn.obj(json.get("away"), NFLSimTeamOverride::fromJson),
            JsonIn.list(json.get("players"), NFLSimPlayerOverride::fromJson)
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.required(sb, first, "game_id", gameId);
        first = JsonOut.field(sb, first, "n_draws", nDraws);
        first = JsonOut.field(sb, first, "seed", seed);
        first = JsonOut.field(sb, first, "include", include);
        first = JsonOut.field(sb, first, "scenarios", scenarios);
        first = JsonOut.field(sb, first, "scenarios_pbp", scenariosPbp);
        first = JsonOut.field(sb, first, "simulations", simulations);
        first = JsonOut.field(sb, first, "simulations_pbp", simulationsPbp);
        first = JsonOut.field(sb, first, "simulations_limit", simulationsLimit);
        first = JsonOut.field(sb, first, "home_score", homeScore);
        first = JsonOut.field(sb, first, "away_score", awayScore);
        first = JsonOut.field(sb, first, "total", total);
        first = JsonOut.field(sb, first, "home_spread", homeSpread);
        first = JsonOut.field(sb, first, "home_win_pct", homeWinPct);
        first = JsonOut.field(sb, first, "mode", mode);
        first = JsonOut.field(sb, first, "weather", weather);
        first = JsonOut.field(sb, first, "home", home);
        first = JsonOut.field(sb, first, "away", away);
        first = JsonOut.field(sb, first, "players", players);
        sb.append('}');
        return sb.toString();
    }

    /** Serializes overrides for path-bound endpoints where {@code game_id} is in the URL. */
    String toJsonForPathGame() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "n_draws", nDraws);
        first = JsonOut.field(sb, first, "seed", seed);
        first = JsonOut.field(sb, first, "include", include);
        first = JsonOut.field(sb, first, "scenarios", scenarios);
        first = JsonOut.field(sb, first, "scenarios_pbp", scenariosPbp);
        first = JsonOut.field(sb, first, "simulations", simulations);
        first = JsonOut.field(sb, first, "simulations_pbp", simulationsPbp);
        first = JsonOut.field(sb, first, "simulations_limit", simulationsLimit);
        first = JsonOut.field(sb, first, "home_score", homeScore);
        first = JsonOut.field(sb, first, "away_score", awayScore);
        first = JsonOut.field(sb, first, "total", total);
        first = JsonOut.field(sb, first, "home_spread", homeSpread);
        first = JsonOut.field(sb, first, "home_win_pct", homeWinPct);
        first = JsonOut.field(sb, first, "mode", mode);
        first = JsonOut.field(sb, first, "weather", weather);
        first = JsonOut.field(sb, first, "home", home);
        first = JsonOut.field(sb, first, "away", away);
        first = JsonOut.field(sb, first, "players", players);
        sb.append('}');
        return sb.toString();
    }
}
