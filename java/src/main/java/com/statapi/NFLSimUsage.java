// DO NOT EDIT — generated from schema/api/sim/nfl_sim.yml by generate-sim-sdks.ts
package com.statapi;

/**
 * Football-native player snap share, route participation, slot alignment, and situational target/carry share profile.
 *
 * @param snapShare Expected offensive snap share when active ({@code 0.0..1.0}).
 * @param routeParticipationRate Routes run per team pass play when on the field ({@code 0.0..1.0}).
 * @param slotRate Share of receiving snaps aligned in the slot ({@code 0.0..1.0}).
 * @param targetShare Overall team target share ({@code 0.0..0.55}).
 * @param targetsPerRoute Targets per route run (TPRR) on early downs ({@code 0.0..0.45}).
 * @param redZoneTargetShare Team target share inside the opponent's 20-yard line ({@code 0.0..0.65}).
 * @param carryShare Overall team rushing carry share ({@code 0.0..0.90}).
 * @param shortYardageCarryShare Carry share in short-yardage ({@code 1..2} yds to go) situations ({@code 0.0..0.95}).
 * @param redZoneCarryShare Team rushing carry share inside the opponent's 20-yard line ({@code 0.0..0.90}).
 * @param goalLineCarryShare Carry share inside the opponent's 5-yard line / green zone ({@code 0.0..0.95}).
 * @param fourMinuteCarryShare Carry share in 4-minute clock-burn situations when leading late ({@code 0.0..0.95}).
 */
public record NFLSimUsage(
    Double snapShare,
    Double routeParticipationRate,
    Double slotRate,
    Double targetShare,
    Double targetsPerRoute,
    Double redZoneTargetShare,
    Double carryShare,
    Double shortYardageCarryShare,
    Double redZoneCarryShare,
    Double goalLineCarryShare,
    Double fourMinuteCarryShare
) implements JsonSerializable {

    public static NFLSimUsage fromJson(JsonValue json) {
        return new NFLSimUsage(
            json.get("snap_share").asDouble(),
            json.get("route_participation_rate").asDouble(),
            json.get("slot_rate").asDouble(),
            json.get("target_share").asDouble(),
            json.get("targets_per_route").asDouble(),
            json.get("red_zone_target_share").asDouble(),
            json.get("carry_share").asDouble(),
            json.get("short_yardage_carry_share").asDouble(),
            json.get("red_zone_carry_share").asDouble(),
            json.get("goal_line_carry_share").asDouble(),
            json.get("four_minute_carry_share").asDouble()
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "snap_share", snapShare);
        first = JsonOut.field(sb, first, "route_participation_rate", routeParticipationRate);
        first = JsonOut.field(sb, first, "slot_rate", slotRate);
        first = JsonOut.field(sb, first, "target_share", targetShare);
        first = JsonOut.field(sb, first, "targets_per_route", targetsPerRoute);
        first = JsonOut.field(sb, first, "red_zone_target_share", redZoneTargetShare);
        first = JsonOut.field(sb, first, "carry_share", carryShare);
        first = JsonOut.field(sb, first, "short_yardage_carry_share", shortYardageCarryShare);
        first = JsonOut.field(sb, first, "red_zone_carry_share", redZoneCarryShare);
        first = JsonOut.field(sb, first, "goal_line_carry_share", goalLineCarryShare);
        first = JsonOut.field(sb, first, "four_minute_carry_share", fourMinuteCarryShare);
        sb.append('}');
        return sb.toString();
    }
}
