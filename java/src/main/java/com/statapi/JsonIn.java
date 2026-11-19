package com.statapi;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Readers for the nested members of a {@link JsonValue}, used by the generated record binders.
 *
 * Hand-written core (not generated). A member that is JSON {@code null} or absent reads as
 * {@code null}, never as an empty list, so a caller can tell "not sent" from "sent empty".
 */
final class JsonIn {

    private JsonIn() {}

    /** The node itself. For members typed as free-form JSON. */
    static JsonValue same(JsonValue json) {
        return json;
    }

    /** The object member read with {@code reader}, or {@code null} when it is absent or null. */
    static <T> T obj(JsonValue json, Function<JsonValue, T> reader) {
        return json.isNull() ? null : reader.apply(json);
    }

    /** The array member with each element read by {@code reader}, or {@code null} when absent or null. */
    static <T> List<T> list(JsonValue json, Function<JsonValue, T> reader) {
        if (json.isNull()) {
            return null;
        }
        List<T> out = new ArrayList<>();
        for (JsonValue element : json.asArray()) {
            out.add(element.isNull() ? null : reader.apply(element));
        }
        return out;
    }

    /** The object member with each value read by {@code reader}, or {@code null} when absent or null. */
    static <T> Map<String, T> map(JsonValue json, Function<JsonValue, T> reader) {
        if (json.isNull()) {
            return null;
        }
        Map<String, T> out = new LinkedHashMap<>();
        for (Map.Entry<String, JsonValue> entry : json.asObject().entrySet()) {
            out.put(entry.getKey(), entry.getValue().isNull() ? null : reader.apply(entry.getValue()));
        }
        return out;
    }
}
