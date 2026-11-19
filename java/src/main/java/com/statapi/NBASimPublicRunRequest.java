// DO NOT EDIT — generated from schema/api/sim/nba_sim.yml by generate-sim-sdks.ts
package com.statapi;

import java.util.List;

/**
 * Zero-input + simple v1 override request body for public POST /api/v1/sim/nba/run.
 *
 * @param gameId Modeled NBA game ID to simulate ({@code 6029936} is free for all users). (bounds: &gt;= 1)
 * @param nDraws Number of Monte Carlo draws ({@code 50..10000}, default {@code 2500}, aliases {@code n_runs}, {@code n_sims}). (unit: draws; bounds: 50..10000; default: 2500; aliases: n_runs, n_sims)
 * @param seed Deterministic 64-bit CRN seed (default {@code 20261028}). (default: 20261028)
 * @param include Output sections to return ({@code summary}, {@code distributions}, {@code scorebox}, {@code pbp}, {@code scenarios}, {@code simulations}; alias {@code granularity}). (aliases: granularity)
 * @param scenarios Shorthand to enable {@code output.scenarios} and set {@code scenarios_limit} ({@code 1..100}). (unit: count; bounds: 1..100)
 * @param scenariosPbp Shorthand to embed official NBA play-by-play inside each scenario.
 * @param simulations Shorthand to enable {@code output.simulations}.
 * @param simulationsPbp Shorthand to embed official NBA play-by-play inside each simulated game.
 * @param simulationsLimit Maximum number of simulated games in {@code output.simulations.games} ({@code 1..200}). (unit: count; bounds: 1..200)
 * @param homeScore Override implied home team points ({@code 50..200}, must agree with {@code away_score}/{@code total}/{@code home_spread} within {@code 0.5}). (unit: points; bounds: 50..200)
 * @param awayScore Override implied away team points ({@code 50..200}, must agree with {@code home_score}/{@code total}/{@code home_spread} within {@code 0.5}). (unit: points; bounds: 50..200)
 * @param total Override game total points ({@code 150..300}). (unit: points; bounds: 150..300)
 * @param homeSpread Override home spread ({@code away_score - home_score}, {@code -40..40}; negative = home favored). (unit: points; bounds: -40..40)
 * @param homeWinPct Override pre-tip home win probability ({@code 0.5..99.5}%). (unit: percent_0_100; bounds: 0.5..99.5)
 * @param mode Calibration regime ({@code all}, {@code top_props}, {@code game_lines}, {@code none}). (values: all | top_props | game_lines | none)
 * @param gamePacePct Shared game pace multiplier ({@code 50..150}, {@code 100} = neutral). (unit: percent_baseline_100; bounds: 50..150)
 * @param refWhistle Shooting-foul whistle multiplier ({@code 50..150}, {@code 100} = neutral). (unit: percent_baseline_100; bounds: 50..150)
 * @param blowoutPulls Blowout starter-pull sensitivity ({@code 0..250}, {@code 100} = normal). (unit: percent_baseline_100; bounds: 0..250)
 * @param home Optional home team overrides ({@code out[]}, {@code pace}, {@code off_rating}, {@code def_rating}, {@code rest_days}).
 * @param away Optional away team overrides ({@code out[]}, {@code pace}, {@code off_rating}, {@code def_rating}, {@code rest_days}).
 * @param players Per-player availability, role, minutes, and prop line overrides.
 */
public record NBASimPublicRunRequest(
    Long gameId,
    Integer nDraws,
    Long seed,
    NBASimInclude include,
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
    Double gamePacePct,
    Double refWhistle,
    Double blowoutPulls,
    NBASimTeamOverride home,
    NBASimTeamOverride away,
    List<NBASimPlayerOverride> players
) implements JsonSerializable {

    /** An empty override request for path-bound {@code game_id} endpoints. */
    public NBASimPublicRunRequest() {
        this(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
    }

    /** A request with the required members only. */
    public NBASimPublicRunRequest(long gameId) {
        this(gameId, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
    }

    /** A copy of this request with {@code game_id} set. */
    public NBASimPublicRunRequest gameId(Long value) {
        return new NBASimPublicRunRequest(value, nDraws, seed, include, scenarios, scenariosPbp, simulations, simulationsPbp, simulationsLimit, homeScore, awayScore, total, homeSpread, homeWinPct, mode, gamePacePct, refWhistle, blowoutPulls, home, away, players);
    }

    /** A copy of this request with {@code n_draws} set. */
    public NBASimPublicRunRequest nDraws(Integer value) {
        return new NBASimPublicRunRequest(gameId, value, seed, include, scenarios, scenariosPbp, simulations, simulationsPbp, simulationsLimit, homeScore, awayScore, total, homeSpread, homeWinPct, mode, gamePacePct, refWhistle, blowoutPulls, home, away, players);
    }

    /** A copy of this request with {@code seed} set. */
    public NBASimPublicRunRequest seed(Long value) {
        return new NBASimPublicRunRequest(gameId, nDraws, value, include, scenarios, scenariosPbp, simulations, simulationsPbp, simulationsLimit, homeScore, awayScore, total, homeSpread, homeWinPct, mode, gamePacePct, refWhistle, blowoutPulls, home, away, players);
    }

    /** A copy of this request with {@code include} set. */
    public NBASimPublicRunRequest include(NBASimInclude value) {
        return new NBASimPublicRunRequest(gameId, nDraws, seed, value, scenarios, scenariosPbp, simulations, simulationsPbp, simulationsLimit, homeScore, awayScore, total, homeSpread, homeWinPct, mode, gamePacePct, refWhistle, blowoutPulls, home, away, players);
    }

    /** A copy of this request with {@code scenarios} set. */
    public NBASimPublicRunRequest scenarios(Integer value) {
        return new NBASimPublicRunRequest(gameId, nDraws, seed, include, value, scenariosPbp, simulations, simulationsPbp, simulationsLimit, homeScore, awayScore, total, homeSpread, homeWinPct, mode, gamePacePct, refWhistle, blowoutPulls, home, away, players);
    }

    /** A copy of this request with {@code scenarios_pbp} set. */
    public NBASimPublicRunRequest scenariosPbp(Boolean value) {
        return new NBASimPublicRunRequest(gameId, nDraws, seed, include, scenarios, value, simulations, simulationsPbp, simulationsLimit, homeScore, awayScore, total, homeSpread, homeWinPct, mode, gamePacePct, refWhistle, blowoutPulls, home, away, players);
    }

    /** A copy of this request with {@code simulations} set. */
    public NBASimPublicRunRequest simulations(Boolean value) {
        return new NBASimPublicRunRequest(gameId, nDraws, seed, include, scenarios, scenariosPbp, value, simulationsPbp, simulationsLimit, homeScore, awayScore, total, homeSpread, homeWinPct, mode, gamePacePct, refWhistle, blowoutPulls, home, away, players);
    }

    /** A copy of this request with {@code simulations_pbp} set. */
    public NBASimPublicRunRequest simulationsPbp(Boolean value) {
        return new NBASimPublicRunRequest(gameId, nDraws, seed, include, scenarios, scenariosPbp, simulations, value, simulationsLimit, homeScore, awayScore, total, homeSpread, homeWinPct, mode, gamePacePct, refWhistle, blowoutPulls, home, away, players);
    }

    /** A copy of this request with {@code simulations_limit} set. */
    public NBASimPublicRunRequest simulationsLimit(Integer value) {
        return new NBASimPublicRunRequest(gameId, nDraws, seed, include, scenarios, scenariosPbp, simulations, simulationsPbp, value, homeScore, awayScore, total, homeSpread, homeWinPct, mode, gamePacePct, refWhistle, blowoutPulls, home, away, players);
    }

    /** A copy of this request with {@code home_score} set. */
    public NBASimPublicRunRequest homeScore(Double value) {
        return new NBASimPublicRunRequest(gameId, nDraws, seed, include, scenarios, scenariosPbp, simulations, simulationsPbp, simulationsLimit, value, awayScore, total, homeSpread, homeWinPct, mode, gamePacePct, refWhistle, blowoutPulls, home, away, players);
    }

    /** A copy of this request with {@code away_score} set. */
    public NBASimPublicRunRequest awayScore(Double value) {
        return new NBASimPublicRunRequest(gameId, nDraws, seed, include, scenarios, scenariosPbp, simulations, simulationsPbp, simulationsLimit, homeScore, value, total, homeSpread, homeWinPct, mode, gamePacePct, refWhistle, blowoutPulls, home, away, players);
    }

    /** A copy of this request with {@code total} set. */
    public NBASimPublicRunRequest total(Double value) {
        return new NBASimPublicRunRequest(gameId, nDraws, seed, include, scenarios, scenariosPbp, simulations, simulationsPbp, simulationsLimit, homeScore, awayScore, value, homeSpread, homeWinPct, mode, gamePacePct, refWhistle, blowoutPulls, home, away, players);
    }

    /** A copy of this request with {@code home_spread} set. */
    public NBASimPublicRunRequest homeSpread(Double value) {
        return new NBASimPublicRunRequest(gameId, nDraws, seed, include, scenarios, scenariosPbp, simulations, simulationsPbp, simulationsLimit, homeScore, awayScore, total, value, homeWinPct, mode, gamePacePct, refWhistle, blowoutPulls, home, away, players);
    }

    /** A copy of this request with {@code home_win_pct} set. */
    public NBASimPublicRunRequest homeWinPct(Double value) {
        return new NBASimPublicRunRequest(gameId, nDraws, seed, include, scenarios, scenariosPbp, simulations, simulationsPbp, simulationsLimit, homeScore, awayScore, total, homeSpread, value, mode, gamePacePct, refWhistle, blowoutPulls, home, away, players);
    }

    /** A copy of this request with {@code mode} set. */
    public NBASimPublicRunRequest mode(String value) {
        return new NBASimPublicRunRequest(gameId, nDraws, seed, include, scenarios, scenariosPbp, simulations, simulationsPbp, simulationsLimit, homeScore, awayScore, total, homeSpread, homeWinPct, value, gamePacePct, refWhistle, blowoutPulls, home, away, players);
    }

    /** A copy of this request with {@code game_pace_pct} set. */
    public NBASimPublicRunRequest gamePacePct(Double value) {
        return new NBASimPublicRunRequest(gameId, nDraws, seed, include, scenarios, scenariosPbp, simulations, simulationsPbp, simulationsLimit, homeScore, awayScore, total, homeSpread, homeWinPct, mode, value, refWhistle, blowoutPulls, home, away, players);
    }

    /** A copy of this request with {@code ref_whistle} set. */
    public NBASimPublicRunRequest refWhistle(Double value) {
        return new NBASimPublicRunRequest(gameId, nDraws, seed, include, scenarios, scenariosPbp, simulations, simulationsPbp, simulationsLimit, homeScore, awayScore, total, homeSpread, homeWinPct, mode, gamePacePct, value, blowoutPulls, home, away, players);
    }

    /** A copy of this request with {@code blowout_pulls} set. */
    public NBASimPublicRunRequest blowoutPulls(Double value) {
        return new NBASimPublicRunRequest(gameId, nDraws, seed, include, scenarios, scenariosPbp, simulations, simulationsPbp, simulationsLimit, homeScore, awayScore, total, homeSpread, homeWinPct, mode, gamePacePct, refWhistle, value, home, away, players);
    }

    /** A copy of this request with {@code home} set. */
    public NBASimPublicRunRequest home(NBASimTeamOverride value) {
        return new NBASimPublicRunRequest(gameId, nDraws, seed, include, scenarios, scenariosPbp, simulations, simulationsPbp, simulationsLimit, homeScore, awayScore, total, homeSpread, homeWinPct, mode, gamePacePct, refWhistle, blowoutPulls, value, away, players);
    }

    /** A copy of this request with {@code away} set. */
    public NBASimPublicRunRequest away(NBASimTeamOverride value) {
        return new NBASimPublicRunRequest(gameId, nDraws, seed, include, scenarios, scenariosPbp, simulations, simulationsPbp, simulationsLimit, homeScore, awayScore, total, homeSpread, homeWinPct, mode, gamePacePct, refWhistle, blowoutPulls, home, value, players);
    }

    /** A copy of this request with {@code players} set. */
    public NBASimPublicRunRequest players(List<NBASimPlayerOverride> value) {
        return new NBASimPublicRunRequest(gameId, nDraws, seed, include, scenarios, scenariosPbp, simulations, simulationsPbp, simulationsLimit, homeScore, awayScore, total, homeSpread, homeWinPct, mode, gamePacePct, refWhistle, blowoutPulls, home, away, value);
    }

    public static NBASimPublicRunRequest fromJson(JsonValue json) {
        return new NBASimPublicRunRequest(
            json.get("game_id").asLong(),
            json.get("n_draws").asInt(),
            json.get("seed").asLong(),
            JsonIn.obj(json.get("include"), NBASimInclude::fromJson),
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
            json.get("game_pace_pct").asDouble(),
            json.get("ref_whistle").asDouble(),
            json.get("blowout_pulls").asDouble(),
            JsonIn.obj(json.get("home"), NBASimTeamOverride::fromJson),
            JsonIn.obj(json.get("away"), NBASimTeamOverride::fromJson),
            JsonIn.list(json.get("players"), NBASimPlayerOverride::fromJson)
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
        first = JsonOut.field(sb, first, "game_pace_pct", gamePacePct);
        first = JsonOut.field(sb, first, "ref_whistle", refWhistle);
        first = JsonOut.field(sb, first, "blowout_pulls", blowoutPulls);
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
        first = JsonOut.field(sb, first, "game_pace_pct", gamePacePct);
        first = JsonOut.field(sb, first, "ref_whistle", refWhistle);
        first = JsonOut.field(sb, first, "blowout_pulls", blowoutPulls);
        first = JsonOut.field(sb, first, "home", home);
        first = JsonOut.field(sb, first, "away", away);
        first = JsonOut.field(sb, first, "players", players);
        sb.append('}');
        return sb.toString();
    }
}
