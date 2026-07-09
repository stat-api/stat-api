package com.statapi;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * A zero-dependency, parse-only JSON reader.
 *
 * Hand-written core (not generated). A single recursive-descent pass turns a
 * response body into a {@link JsonValue} tree — objects as ordered maps, arrays
 * as lists, strings with full escape handling (including {@code \\uXXXX}),
 * numbers as {@code Long} when integral and {@code Double} otherwise, plus
 * booleans and null. It never serializes: the SDK only reads responses.
 */
public final class Json {

    private Json() {
    }

    /** Parse {@code text} into a {@link JsonValue} tree. */
    public static JsonValue parse(String text) {
        if (text == null) {
            return JsonValue.NULL;
        }
        Parser parser = new Parser(text);
        parser.skipWhitespace();
        JsonValue value = parser.parseValue();
        parser.skipWhitespace();
        if (!parser.atEnd()) {
            throw new JsonException("unexpected trailing content at position " + parser.position());
        }
        return value;
    }

    /** Thrown when the input is not well-formed JSON. */
    public static final class JsonException extends RuntimeException {
        JsonException(String message) {
            super(message);
        }
    }

    private static final class Parser {
        private final String src;
        private int pos;

        Parser(String src) {
            this.src = src;
        }

        int position() {
            return pos;
        }

        boolean atEnd() {
            return pos >= src.length();
        }

        void skipWhitespace() {
            while (pos < src.length()) {
                char c = src.charAt(pos);
                if (c == ' ' || c == '\t' || c == '\n' || c == '\r') {
                    pos++;
                } else {
                    break;
                }
            }
        }

        JsonValue parseValue() {
            if (atEnd()) {
                throw new JsonException("unexpected end of input");
            }
            char c = src.charAt(pos);
            switch (c) {
                case '{':
                    return parseObject();
                case '[':
                    return parseArray();
                case '"':
                    return new JsonValue(parseString());
                case 't':
                case 'f':
                    return parseBoolean();
                case 'n':
                    return parseNull();
                default:
                    return parseNumber();
            }
        }

        private JsonValue parseObject() {
            expect('{');
            skipWhitespace();
            Map<String, JsonValue> members = new LinkedHashMap<>();
            if (peek() == '}') {
                pos++;
                return new JsonValue(members);
            }
            while (true) {
                skipWhitespace();
                if (peek() != '"') {
                    throw new JsonException("expected string key at position " + pos);
                }
                String key = parseString();
                skipWhitespace();
                expect(':');
                skipWhitespace();
                members.put(key, parseValue());
                skipWhitespace();
                char c = next();
                if (c == '}') {
                    break;
                }
                if (c != ',') {
                    throw new JsonException("expected ',' or '}' at position " + (pos - 1));
                }
            }
            return new JsonValue(members);
        }

        private JsonValue parseArray() {
            expect('[');
            skipWhitespace();
            List<JsonValue> elements = new ArrayList<>();
            if (peek() == ']') {
                pos++;
                return new JsonValue(elements);
            }
            while (true) {
                skipWhitespace();
                elements.add(parseValue());
                skipWhitespace();
                char c = next();
                if (c == ']') {
                    break;
                }
                if (c != ',') {
                    throw new JsonException("expected ',' or ']' at position " + (pos - 1));
                }
            }
            return new JsonValue(elements);
        }

        private String parseString() {
            expect('"');
            StringBuilder sb = new StringBuilder();
            while (true) {
                if (atEnd()) {
                    throw new JsonException("unterminated string");
                }
                char c = src.charAt(pos++);
                if (c == '"') {
                    break;
                }
                if (c == '\\') {
                    if (atEnd()) {
                        throw new JsonException("unterminated escape sequence");
                    }
                    char esc = src.charAt(pos++);
                    switch (esc) {
                        case '"':
                            sb.append('"');
                            break;
                        case '\\':
                            sb.append('\\');
                            break;
                        case '/':
                            sb.append('/');
                            break;
                        case 'b':
                            sb.append('\b');
                            break;
                        case 'f':
                            sb.append('\f');
                            break;
                        case 'n':
                            sb.append('\n');
                            break;
                        case 'r':
                            sb.append('\r');
                            break;
                        case 't':
                            sb.append('\t');
                            break;
                        case 'u':
                            if (pos + 4 > src.length()) {
                                throw new JsonException("truncated \\u escape at position " + pos);
                            }
                            String hex = src.substring(pos, pos + 4);
                            pos += 4;
                            try {
                                sb.append((char) Integer.parseInt(hex, 16));
                            } catch (NumberFormatException ex) {
                                throw new JsonException("invalid \\u escape '" + hex + "'");
                            }
                            break;
                        default:
                            throw new JsonException("invalid escape '\\" + esc + "'");
                    }
                } else {
                    sb.append(c);
                }
            }
            return sb.toString();
        }

        private JsonValue parseNumber() {
            int start = pos;
            if (peek() == '-') {
                pos++;
            }
            while (!atEnd() && isNumberChar(src.charAt(pos))) {
                pos++;
            }
            String literal = src.substring(start, pos);
            if (literal.isEmpty() || "-".equals(literal)) {
                throw new JsonException("invalid number at position " + start);
            }
            boolean floating = literal.indexOf('.') >= 0
                || literal.indexOf('e') >= 0
                || literal.indexOf('E') >= 0;
            try {
                if (floating) {
                    return new JsonValue(Double.parseDouble(literal));
                }
                return new JsonValue(Long.parseLong(literal));
            } catch (NumberFormatException ex) {
                // Integer too wide for a long — fall back to a double so parsing
                // still succeeds (precision loss is acceptable for out-of-range
                // integers; the wire types the SDK exposes never rely on it).
                return new JsonValue(Double.parseDouble(literal));
            }
        }

        private boolean isNumberChar(char c) {
            return (c >= '0' && c <= '9') || c == '.' || c == 'e' || c == 'E' || c == '+' || c == '-';
        }

        private JsonValue parseBoolean() {
            if (src.startsWith("true", pos)) {
                pos += 4;
                return new JsonValue(Boolean.TRUE);
            }
            if (src.startsWith("false", pos)) {
                pos += 5;
                return new JsonValue(Boolean.FALSE);
            }
            throw new JsonException("invalid literal at position " + pos);
        }

        private JsonValue parseNull() {
            if (src.startsWith("null", pos)) {
                pos += 4;
                return JsonValue.NULL;
            }
            throw new JsonException("invalid literal at position " + pos);
        }

        private char peek() {
            if (atEnd()) {
                throw new JsonException("unexpected end of input");
            }
            return src.charAt(pos);
        }

        private char next() {
            if (atEnd()) {
                throw new JsonException("unexpected end of input");
            }
            return src.charAt(pos++);
        }

        private void expect(char expected) {
            char got = next();
            if (got != expected) {
                throw new JsonException("expected '" + expected + "' but got '" + got + "' at position " + (pos - 1));
            }
        }
    }
}
