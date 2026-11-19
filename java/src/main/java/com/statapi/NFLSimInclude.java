// DO NOT EDIT — generated from schema/api/sim/nfl_sim.yml by generate-sim-sdks.ts
package com.statapi;

import java.util.List;

/**
 * Output section selector for POST /api/v1/sim/nfl/run. The sections are all on by default.
 *
 * @param summary Include {@code output.summary} (macro game/team/player means, SDs, quantiles, milestones, and Vegas reconciliation). Default {@code true}.
 * @param distributions Include {@code output.distributions} (30-bin histograms with KDE + Gaussian density + Vegas survival for game &amp; player metrics). Default {@code true}.
 * @param scorebox Include {@code output.scorebox} (6 canonical game archetypes + up to {@code scorebox_limit} individual draw box scores). Default {@code true}.
 * @param pbp Include {@code output.pbp} (full Drive Charts and snap-by-snap Play-by-Play tapes for {@code pbp_draws} or the 6 archetypes). Default {@code true}.
 * @param scoreboxLimit Maximum number of individual draw box scores in {@code output.scorebox.draws} (default {@code 2500}, {@code 0..10000}, never more than {@code n_draws}). (unit: count; bounds: 0..10000)
 * @param pbpDraws 0-based {@code draw_idx} values to trace when {@code pbp = true} (empty array traces the 6 canonical archetype draws).
 * @param scenarios Include {@code output.scenarios} (probability-weighted full-game box score scenarios, {@code -} / {@code +} deltas vs. rollout median, and pairwise player correlation matrix). Default {@code false}.
 * @param scenariosPbp Include nested {@code pbp} ({@code drives} and {@code plays}) inside each scenario in {@code output.scenarios.scenarios}. Default {@code false}.
 * @param scenariosLimit Number of representative full-game box score scenarios to return in {@code output.scenarios.scenarios} ({@code 1..5000}, default {@code 1000}). (unit: count; bounds: 1..5000)
 * @param simulations Include {@code output.simulations} (every simulated game with final score, quarter linescore, sparse team/player box scores, and optional nested {@code pbp}). Default {@code false}.
 * @param simulationsPbp Include nested {@code pbp} ({@code game_drives} and {@code play_by_plays} replicating official NFL PBP) inside each simulated game in {@code output.simulations.games}. Default {@code false}.
 * @param simulationsLimit Optional cap on the number of simulated games returned in {@code output.simulations.games} ({@code 1..200}). (unit: count; bounds: 1..200)
 */
public record NFLSimInclude(
    Boolean summary,
    Boolean distributions,
    Boolean scorebox,
    Boolean pbp,
    Integer scoreboxLimit,
    List<Integer> pbpDraws,
    Boolean scenarios,
    Boolean scenariosPbp,
    Integer scenariosLimit,
    Boolean simulations,
    Boolean simulationsPbp,
    Integer simulationsLimit
) implements JsonSerializable {

    /** A request with no member set. */
    public NFLSimInclude() {
        this(null, null, null, null, null, null, null, null, null, null, null, null);
    }

    /** A copy of this request with {@code summary} set. */
    public NFLSimInclude summary(Boolean value) {
        return new NFLSimInclude(value, distributions, scorebox, pbp, scoreboxLimit, pbpDraws, scenarios, scenariosPbp, scenariosLimit, simulations, simulationsPbp, simulationsLimit);
    }

    /** A copy of this request with {@code distributions} set. */
    public NFLSimInclude distributions(Boolean value) {
        return new NFLSimInclude(summary, value, scorebox, pbp, scoreboxLimit, pbpDraws, scenarios, scenariosPbp, scenariosLimit, simulations, simulationsPbp, simulationsLimit);
    }

    /** A copy of this request with {@code scorebox} set. */
    public NFLSimInclude scorebox(Boolean value) {
        return new NFLSimInclude(summary, distributions, value, pbp, scoreboxLimit, pbpDraws, scenarios, scenariosPbp, scenariosLimit, simulations, simulationsPbp, simulationsLimit);
    }

    /** A copy of this request with {@code pbp} set. */
    public NFLSimInclude pbp(Boolean value) {
        return new NFLSimInclude(summary, distributions, scorebox, value, scoreboxLimit, pbpDraws, scenarios, scenariosPbp, scenariosLimit, simulations, simulationsPbp, simulationsLimit);
    }

    /** A copy of this request with {@code scorebox_limit} set. */
    public NFLSimInclude scoreboxLimit(Integer value) {
        return new NFLSimInclude(summary, distributions, scorebox, pbp, value, pbpDraws, scenarios, scenariosPbp, scenariosLimit, simulations, simulationsPbp, simulationsLimit);
    }

    /** A copy of this request with {@code pbp_draws} set. */
    public NFLSimInclude pbpDraws(List<Integer> value) {
        return new NFLSimInclude(summary, distributions, scorebox, pbp, scoreboxLimit, value, scenarios, scenariosPbp, scenariosLimit, simulations, simulationsPbp, simulationsLimit);
    }

    /** A copy of this request with {@code scenarios} set. */
    public NFLSimInclude scenarios(Boolean value) {
        return new NFLSimInclude(summary, distributions, scorebox, pbp, scoreboxLimit, pbpDraws, value, scenariosPbp, scenariosLimit, simulations, simulationsPbp, simulationsLimit);
    }

    /** A copy of this request with {@code scenarios_pbp} set. */
    public NFLSimInclude scenariosPbp(Boolean value) {
        return new NFLSimInclude(summary, distributions, scorebox, pbp, scoreboxLimit, pbpDraws, scenarios, value, scenariosLimit, simulations, simulationsPbp, simulationsLimit);
    }

    /** A copy of this request with {@code scenarios_limit} set. */
    public NFLSimInclude scenariosLimit(Integer value) {
        return new NFLSimInclude(summary, distributions, scorebox, pbp, scoreboxLimit, pbpDraws, scenarios, scenariosPbp, value, simulations, simulationsPbp, simulationsLimit);
    }

    /** A copy of this request with {@code simulations} set. */
    public NFLSimInclude simulations(Boolean value) {
        return new NFLSimInclude(summary, distributions, scorebox, pbp, scoreboxLimit, pbpDraws, scenarios, scenariosPbp, scenariosLimit, value, simulationsPbp, simulationsLimit);
    }

    /** A copy of this request with {@code simulations_pbp} set. */
    public NFLSimInclude simulationsPbp(Boolean value) {
        return new NFLSimInclude(summary, distributions, scorebox, pbp, scoreboxLimit, pbpDraws, scenarios, scenariosPbp, scenariosLimit, simulations, value, simulationsLimit);
    }

    /** A copy of this request with {@code simulations_limit} set. */
    public NFLSimInclude simulationsLimit(Integer value) {
        return new NFLSimInclude(summary, distributions, scorebox, pbp, scoreboxLimit, pbpDraws, scenarios, scenariosPbp, scenariosLimit, simulations, simulationsPbp, value);
    }

    public static NFLSimInclude fromJson(JsonValue json) {
        return new NFLSimInclude(
            json.get("summary").asBoolean(),
            json.get("distributions").asBoolean(),
            json.get("scorebox").asBoolean(),
            json.get("pbp").asBoolean(),
            json.get("scorebox_limit").asInt(),
            JsonIn.list(json.get("pbp_draws"), JsonValue::asInt),
            json.get("scenarios").asBoolean(),
            json.get("scenarios_pbp").asBoolean(),
            json.get("scenarios_limit").asInt(),
            json.get("simulations").asBoolean(),
            json.get("simulations_pbp").asBoolean(),
            json.get("simulations_limit").asInt()
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "summary", summary);
        first = JsonOut.field(sb, first, "distributions", distributions);
        first = JsonOut.field(sb, first, "scorebox", scorebox);
        first = JsonOut.field(sb, first, "pbp", pbp);
        first = JsonOut.field(sb, first, "scorebox_limit", scoreboxLimit);
        first = JsonOut.field(sb, first, "pbp_draws", pbpDraws);
        first = JsonOut.field(sb, first, "scenarios", scenarios);
        first = JsonOut.field(sb, first, "scenarios_pbp", scenariosPbp);
        first = JsonOut.field(sb, first, "scenarios_limit", scenariosLimit);
        first = JsonOut.field(sb, first, "simulations", simulations);
        first = JsonOut.field(sb, first, "simulations_pbp", simulationsPbp);
        first = JsonOut.field(sb, first, "simulations_limit", simulationsLimit);
        sb.append('}');
        return sb.toString();
    }
}
