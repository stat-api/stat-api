// DO NOT EDIT — generated from schema/api/sim/nfl_sim.yml by generate-sim-sdks.ts
package com.statapi;

/**
 * Simple v1 stadium and weather overrides for public POST /api/v1/sim/nfl/run.
 *
 * @param roof Stadium roof state ({@code outdoors}, {@code dome}, {@code retractable_closed}, {@code retractable_open}; aliases: {@code outdoor}, {@code indoors}, {@code closed}, {@code retractable}, {@code open}). (values: outdoors | dome | retractable_closed | retractable_open)
 * @param tempF Ambient kickoff temperature in degrees Fahrenheit ({@code -10..115}, alias {@code temperature_f}). (unit: deg_F; bounds: -10..115; aliases: temperature_f)
 * @param windMph Sustained kickoff wind speed in mph ({@code 0..55}, alias {@code wind_speed_mph}). (unit: mph; bounds: 0..55; aliases: wind_speed_mph)
 * @param precipInHr Liquid precipitation rate in inches per hour ({@code 0.0..0.5}, aliases {@code precipitation_in_hr}, {@code precip_prob}). (unit: in_hr; bounds: 0..0.5; aliases: precipitation_in_hr, precip_prob)
 */
public record NFLSimWeatherOverride(
    String roof,
    Double tempF,
    Double windMph,
    Double precipInHr
) implements JsonSerializable {

    /** A request with no member set. */
    public NFLSimWeatherOverride() {
        this(null, null, null, null);
    }

    /** A copy of this request with {@code roof} set. */
    public NFLSimWeatherOverride roof(String value) {
        return new NFLSimWeatherOverride(value, tempF, windMph, precipInHr);
    }

    /** A copy of this request with {@code temp_f} set. */
    public NFLSimWeatherOverride tempF(Double value) {
        return new NFLSimWeatherOverride(roof, value, windMph, precipInHr);
    }

    /** A copy of this request with {@code wind_mph} set. */
    public NFLSimWeatherOverride windMph(Double value) {
        return new NFLSimWeatherOverride(roof, tempF, value, precipInHr);
    }

    /** A copy of this request with {@code precip_in_hr} set. */
    public NFLSimWeatherOverride precipInHr(Double value) {
        return new NFLSimWeatherOverride(roof, tempF, windMph, value);
    }

    public static NFLSimWeatherOverride fromJson(JsonValue json) {
        return new NFLSimWeatherOverride(
            json.get("roof").asString(),
            json.get("temp_f").asDouble(),
            json.get("wind_mph").asDouble(),
            json.get("precip_in_hr").asDouble()
        );
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        first = JsonOut.field(sb, first, "roof", roof);
        first = JsonOut.field(sb, first, "temp_f", tempF);
        first = JsonOut.field(sb, first, "wind_mph", windMph);
        first = JsonOut.field(sb, first, "precip_in_hr", precipInHr);
        sb.append('}');
        return sb.toString();
    }
}
