package com.statapi;

import java.util.List;
import java.util.Map;

/**
 * An immutable node in a parsed JSON tree.
 *
 * Hand-written core (not generated): the zero-dependency {@link Json} parser
 * produces these, generated record binders read fields off them, and it is the
 * exposed type for {@code jsonb} columns (so a caller can walk arbitrary JSON
 * without a third-party library). Accessors are null-safe: a wrong-typed or
 * absent value yields {@code null} (scalars) or an empty view (arrays/objects)
 * rather than throwing, matching wire truth where a present value always has
 * its declared type.
 */
public final class JsonValue {

    /** Shared sentinel for JSON {@code null} and absent object members. */
    public static final JsonValue NULL = new JsonValue(null);

    // One of: null | Boolean | Long | Double | String | List<JsonValue> |
    // Map<String, JsonValue>. Populated only by Json.
    private final Object value;

    JsonValue(Object value) {
        this.value = value;
    }

    /** True when this node is JSON {@code null} (or an absent member). */
    public boolean isNull() {
        return value == null;
    }

    /** String payload, or {@code null} when this node is not a JSON string. */
    public String asString() {
        return value instanceof String ? (String) value : null;
    }

    /** Integral payload as a {@code Long}, or {@code null} when not numeric. */
    public Long asLong() {
        if (value instanceof Long) {
            return (Long) value;
        }
        if (value instanceof Double) {
            return ((Double) value).longValue();
        }
        return null;
    }

    /** Integral payload narrowed to an {@code Integer}, or {@code null}. */
    public Integer asInt() {
        Long l = asLong();
        return l == null ? null : Integer.valueOf(l.intValue());
    }

    /** Floating payload as a {@code Double}, or {@code null} when not numeric. */
    public Double asDouble() {
        if (value instanceof Double) {
            return (Double) value;
        }
        if (value instanceof Long) {
            return ((Long) value).doubleValue();
        }
        return null;
    }

    /** Boolean payload, or {@code null} when this node is not a JSON boolean. */
    public Boolean asBoolean() {
        return value instanceof Boolean ? (Boolean) value : null;
    }

    /** Object members, or an empty map when this node is not a JSON object. */
    @SuppressWarnings("unchecked")
    public Map<String, JsonValue> asObject() {
        return value instanceof Map ? (Map<String, JsonValue>) value : Map.of();
    }

    /** Array elements, or an empty list when this node is not a JSON array. */
    @SuppressWarnings("unchecked")
    public List<JsonValue> asArray() {
        return value instanceof List ? (List<JsonValue>) value : List.of();
    }

    /** Member by key, or {@link #NULL} when absent / not an object. */
    public JsonValue get(String key) {
        if (value instanceof Map) {
            Object member = ((Map<?, ?>) value).get(key);
            return member == null ? NULL : (JsonValue) member;
        }
        return NULL;
    }

    /** True when this node is an object carrying {@code key}. */
    public boolean has(String key) {
        return value instanceof Map && ((Map<?, ?>) value).containsKey(key);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
