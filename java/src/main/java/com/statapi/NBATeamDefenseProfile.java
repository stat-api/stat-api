// DO NOT EDIT — generated from schema/api/sim/nba_sim.yml by generate-sim-sdks.ts
package com.statapi;

/**
 * Pre-tip team defense ratings inside {@code output.scenarios.teams[id].defense}.
 *
 * @param defRating Defensive rating allowed per 100 possessions.
 * @param rimProtection Rim protection scouting rating ({@code 50..99}).
 * @param perimeterDefense Perimeter closeout rating ({@code 50..99}).
 * @param ballPressure On-ball steal pressure rating ({@code 50..99}).
 * @param defRebounding Defensive glass rating ({@code 50..99}).
 * @param foulDiscipline Foul discipline rating ({@code 50..99}).
 * @param transitionDefenseRating Transition defense rating ({@code 50..99}).
 */
public record NBATeamDefenseProfile(
    Double defRating,
    Double rimProtection,
    Double perimeterDefense,
    Double ballPressure,
    Double defRebounding,
    Double foulDiscipline,
    Double transitionDefenseRating
) implements JsonSerializable {

    public static NBATeamDefenseProfile fromJson(JsonValue json) {
        return new NBATeamDefenseProfile(
            json.get("def_rating").asDouble(),
            json.get("rim_protection").asDouble(),
            json.get("perimeter_defense").asDouble(),
            json.get("ball_pressure").asDouble(),
            json.get("def_rebounding").asDouble(),
            json.get("foul_discipline").asDouble(),
            json.get("transition_defense_rating").asDouble()
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "def_rating", defRating);
        first = JsonOut.field(sb, first, "rim_protection", rimProtection);
        first = JsonOut.field(sb, first, "perimeter_defense", perimeterDefense);
        first = JsonOut.field(sb, first, "ball_pressure", ballPressure);
        first = JsonOut.field(sb, first, "def_rebounding", defRebounding);
        first = JsonOut.field(sb, first, "foul_discipline", foulDiscipline);
        first = JsonOut.field(sb, first, "transition_defense_rating", transitionDefenseRating);
        sb.append('}');
        return sb.toString();
    }
}
