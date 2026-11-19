// DO NOT EDIT — generated from schema/api/sim/nba_sim.yml by generate-sim-sdks.ts
package com.statapi;

import java.util.List;

/**
 * Output section selector ({@code include}) for POST /api/v1/sim/nba/run.
 *
 * @param summary Include {@code output.summary} (game, team, and player projections, quantiles, and co-movement). Default {@code true}.
 * @param distributions Include {@code output.distributions} (histogram bell curves and quantiles). Default {@code true}.
 * @param scorebox Include {@code output.scorebox} (canonical game archetypes + individual draw box scores). Default {@code true}.
 * @param pbp Include {@code output.pbp} (possession-by-possession traces for {@code pbp_draws} or canonical archetypes). Default {@code true}.
 * @param scenarios Include {@code output.scenarios} (probability-weighted full-game box score scenarios and player correlations). Default {@code true}.
 * @param scenariosPbp Include nested official NBA play-by-play inside each scenario in {@code output.scenarios.scenarios}. Default {@code false}.
 * @param simulations Include {@code output.simulations} (individual simulated game box scores). Default {@code false}.
 * @param simulationsPbp Include nested official NBA play-by-play inside each game in {@code output.simulations.games}. Default {@code false}.
 * @param scenariosLimit Number of representative scenarios {@code K} to return ({@code 1..100}, default {@code 20}). (unit: count; bounds: 1..100)
 * @param simulationsLimit Maximum number of simulated games returned in {@code output.simulations.games} ({@code 1..200}). (unit: count; bounds: 1..200)
 * @param scoreboxLimit Maximum individual draw scoreboxes in {@code output.scorebox.draws} ({@code 0..10000}, default {@code 250}). (unit: count; bounds: 0..10000)
 * @param pbpDraws 0-based {@code draw_idx} values to trace when {@code pbp = true}.
 */
public record NBASimInclude(
    Boolean summary,
    Boolean distributions,
    Boolean scorebox,
    Boolean pbp,
    Boolean scenarios,
    Boolean scenariosPbp,
    Boolean simulations,
    Boolean simulationsPbp,
    Integer scenariosLimit,
    Integer simulationsLimit,
    Integer scoreboxLimit,
    List<Integer> pbpDraws
) implements JsonSerializable {

    /** A request with no member set. */
    public NBASimInclude() {
        this(null, null, null, null, null, null, null, null, null, null, null, null);
    }

    /** A copy of this request with {@code summary} set. */
    public NBASimInclude summary(Boolean value) {
        return new NBASimInclude(value, distributions, scorebox, pbp, scenarios, scenariosPbp, simulations, simulationsPbp, scenariosLimit, simulationsLimit, scoreboxLimit, pbpDraws);
    }

    /** A copy of this request with {@code distributions} set. */
    public NBASimInclude distributions(Boolean value) {
        return new NBASimInclude(summary, value, scorebox, pbp, scenarios, scenariosPbp, simulations, simulationsPbp, scenariosLimit, simulationsLimit, scoreboxLimit, pbpDraws);
    }

    /** A copy of this request with {@code scorebox} set. */
    public NBASimInclude scorebox(Boolean value) {
        return new NBASimInclude(summary, distributions, value, pbp, scenarios, scenariosPbp, simulations, simulationsPbp, scenariosLimit, simulationsLimit, scoreboxLimit, pbpDraws);
    }

    /** A copy of this request with {@code pbp} set. */
    public NBASimInclude pbp(Boolean value) {
        return new NBASimInclude(summary, distributions, scorebox, value, scenarios, scenariosPbp, simulations, simulationsPbp, scenariosLimit, simulationsLimit, scoreboxLimit, pbpDraws);
    }

    /** A copy of this request with {@code scenarios} set. */
    public NBASimInclude scenarios(Boolean value) {
        return new NBASimInclude(summary, distributions, scorebox, pbp, value, scenariosPbp, simulations, simulationsPbp, scenariosLimit, simulationsLimit, scoreboxLimit, pbpDraws);
    }

    /** A copy of this request with {@code scenarios_pbp} set. */
    public NBASimInclude scenariosPbp(Boolean value) {
        return new NBASimInclude(summary, distributions, scorebox, pbp, scenarios, value, simulations, simulationsPbp, scenariosLimit, simulationsLimit, scoreboxLimit, pbpDraws);
    }

    /** A copy of this request with {@code simulations} set. */
    public NBASimInclude simulations(Boolean value) {
        return new NBASimInclude(summary, distributions, scorebox, pbp, scenarios, scenariosPbp, value, simulationsPbp, scenariosLimit, simulationsLimit, scoreboxLimit, pbpDraws);
    }

    /** A copy of this request with {@code simulations_pbp} set. */
    public NBASimInclude simulationsPbp(Boolean value) {
        return new NBASimInclude(summary, distributions, scorebox, pbp, scenarios, scenariosPbp, simulations, value, scenariosLimit, simulationsLimit, scoreboxLimit, pbpDraws);
    }

    /** A copy of this request with {@code scenarios_limit} set. */
    public NBASimInclude scenariosLimit(Integer value) {
        return new NBASimInclude(summary, distributions, scorebox, pbp, scenarios, scenariosPbp, simulations, simulationsPbp, value, simulationsLimit, scoreboxLimit, pbpDraws);
    }

    /** A copy of this request with {@code simulations_limit} set. */
    public NBASimInclude simulationsLimit(Integer value) {
        return new NBASimInclude(summary, distributions, scorebox, pbp, scenarios, scenariosPbp, simulations, simulationsPbp, scenariosLimit, value, scoreboxLimit, pbpDraws);
    }

    /** A copy of this request with {@code scorebox_limit} set. */
    public NBASimInclude scoreboxLimit(Integer value) {
        return new NBASimInclude(summary, distributions, scorebox, pbp, scenarios, scenariosPbp, simulations, simulationsPbp, scenariosLimit, simulationsLimit, value, pbpDraws);
    }

    /** A copy of this request with {@code pbp_draws} set. */
    public NBASimInclude pbpDraws(List<Integer> value) {
        return new NBASimInclude(summary, distributions, scorebox, pbp, scenarios, scenariosPbp, simulations, simulationsPbp, scenariosLimit, simulationsLimit, scoreboxLimit, value);
    }

    public static NBASimInclude fromJson(JsonValue json) {
        return new NBASimInclude(
            json.get("summary").asBoolean(),
            json.get("distributions").asBoolean(),
            json.get("scorebox").asBoolean(),
            json.get("pbp").asBoolean(),
            json.get("scenarios").asBoolean(),
            json.get("scenarios_pbp").asBoolean(),
            json.get("simulations").asBoolean(),
            json.get("simulations_pbp").asBoolean(),
            json.get("scenarios_limit").asInt(),
            json.get("simulations_limit").asInt(),
            json.get("scorebox_limit").asInt(),
            JsonIn.list(json.get("pbp_draws"), JsonValue::asInt)
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
        first = JsonOut.field(sb, first, "scenarios", scenarios);
        first = JsonOut.field(sb, first, "scenarios_pbp", scenariosPbp);
        first = JsonOut.field(sb, first, "simulations", simulations);
        first = JsonOut.field(sb, first, "simulations_pbp", simulationsPbp);
        first = JsonOut.field(sb, first, "scenarios_limit", scenariosLimit);
        first = JsonOut.field(sb, first, "simulations_limit", simulationsLimit);
        first = JsonOut.field(sb, first, "scorebox_limit", scoreboxLimit);
        first = JsonOut.field(sb, first, "pbp_draws", pbpDraws);
        sb.append('}');
        return sb.toString();
    }
}
