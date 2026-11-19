package com.statapi;

import java.util.List;
import java.util.Map;

/**
 * Writers for the request bodies of the SDK, used by the generated records.
 *
 * Hand-written core (not generated). Zero dependencies. A member that is {@code null} is left out,
 * except a required member, which fails loud.
 */
final class JsonOut {

    private JsonOut() {}

    /** Writes {@code "name":value} when value is not null. Returns false once a member is written. */
    static boolean field(StringBuilder sb, boolean first, String name, Object value) {
        if (value == null) {
            return first;
        }
        if (!first) {
            sb.append(',');
        }
        quote(sb, name);
        sb.append(':');
        value(sb, value);
        return false;
    }

    /** Like {@link #field} for a member that every request must send. */
    static boolean required(StringBuilder sb, boolean first, String name, Object value) {
        if (value == null) {
            throw new IllegalArgumentException(name + " is required");
        }
        return field(sb, first, name, value);
    }

    /** Writes one value: a string, number, boolean, record, JsonValue, list or map. */
    static void value(StringBuilder sb, Object value) {
        if (value == null) {
            sb.append("null");
        } else if (value instanceof String s) {
            quote(sb, s);
        } else if (value instanceof Double d) {
            number(sb, d);
        } else if (value instanceof Number n) {
            sb.append(n.longValue());
        } else if (value instanceof Boolean b) {
            sb.append(b.booleanValue());
        } else if (value instanceof JsonSerializable j) {
            sb.append(j.toJson());
        } else if (value instanceof JsonValue v) {
            sb.append(v.toJson());
        } else if (value instanceof List<?> list) {
            sb.append('[');
            boolean first = true;
            for (Object element : list) {
                if (!first) {
                    sb.append(',');
                }
                first = false;
                value(sb, element);
            }
            sb.append(']');
        } else if (value instanceof Map<?, ?> map) {
            sb.append('{');
            boolean first = true;
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                if (!first) {
                    sb.append(',');
                }
                first = false;
                quote(sb, String.valueOf(entry.getKey()));
                sb.append(':');
                value(sb, entry.getValue());
            }
            sb.append('}');
        } else {
            throw new IllegalArgumentException("cannot write " + value.getClass().getName() + " as JSON");
        }
    }

    /** A JSON number. NaN and infinity are not JSON, so they fail loud. */
    static void number(StringBuilder sb, double d) {
        if (Double.isNaN(d) || Double.isInfinite(d)) {
            throw new IllegalArgumentException("cannot write " + d + " as a JSON number");
        }
        sb.append(d);
    }

    /** A JSON string with the escapes the JSON grammar needs. */
    static void quote(StringBuilder sb, String s) {
        sb.append('"');
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '"' -> sb.append("\\\"");
                case '\\' -> sb.append("\\\\");
                case '\n' -> sb.append("\\n");
                case '\r' -> sb.append("\\r");
                case '\t' -> sb.append("\\t");
                case '\b' -> sb.append("\\b");
                case '\f' -> sb.append("\\f");
                default -> {
                    if (c < 0x20) {
                        sb.append(String.format("\\u%04x", (int) c));
                    } else {
                        sb.append(c);
                    }
                }
            }
        }
        sb.append('"');
    }
}
