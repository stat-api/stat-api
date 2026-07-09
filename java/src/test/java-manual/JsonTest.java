import com.statapi.Json;
import com.statapi.JsonValue;

import java.util.List;

/**
 * Zero-dependency fixture test for the hand-written {@link Json} parser.
 *
 * No JUnit (the SDK has no test dependencies): this is a plain main class the
 * golden gate compiles against the SDK sources and runs. It parses one fixture
 * exercising nested objects/arrays, every string escape (including {@code \\u}),
 * nulls, and both integral and floating numbers, then {@code System.exit(1)} on
 * the first mismatch. Exit 0 means the parser round-trips the fixture.
 */
public final class JsonTest {

    private static int failures = 0;

    public static void main(String[] args) {
        String fixture = "{"
            + "\"name\": \"stat-api\","
            + "\"active\": true,"
            + "\"disabled\": false,"
            + "\"missing\": null,"
            + "\"count\": 42,"
            + "\"negative\": -7,"
            + "\"ratio\": 3.14,"
            + "\"exp\": 1.5e3,"
            + "\"escapes\": \"tab\\tnewline\\nquote\\\"slash\\\\solidus\\/unicode\\u00e9\","
            + "\"nested\": {\"a\": 1, \"b\": {\"c\": [10, 20, 30]}},"
            + "\"rows\": ["
            + "  {\"id\": 1, \"label\": \"one\"},"
            + "  {\"id\": 2, \"label\": null}"
            + "],"
            + "\"empties\": {\"obj\": {}, \"arr\": []}"
            + "}";

        JsonValue root = Json.parse(fixture);

        check("string", "stat-api", root.get("name").asString());
        check("bool true", Boolean.TRUE, root.get("active").asBoolean());
        check("bool false", Boolean.FALSE, root.get("disabled").asBoolean());
        check("explicit null isNull", Boolean.TRUE, root.get("missing").isNull());
        check("absent member isNull", Boolean.TRUE, root.get("nope").isNull());
        check("integer as long", Long.valueOf(42L), root.get("count").asLong());
        check("integer as int", Integer.valueOf(42), root.get("count").asInt());
        check("negative integer", Long.valueOf(-7L), root.get("negative").asLong());
        check("double", Double.valueOf(3.14), root.get("ratio").asDouble());
        check("exponent double", Double.valueOf(1500.0), root.get("exp").asDouble());

        String escapes = root.get("escapes").asString();
        check("escape decode", "tab\tnewline\nquote\"slash\\solidus/unicodeé", escapes);

        check("nested scalar", Long.valueOf(1L), root.get("nested").get("a").asLong());
        List<JsonValue> deep = root.get("nested").get("b").get("c").asArray();
        check("deep array size", Integer.valueOf(3), Integer.valueOf(deep.size()));
        check("deep array[1]", Long.valueOf(20L), deep.get(1).asLong());

        List<JsonValue> rows = root.get("rows").asArray();
        check("rows size", Integer.valueOf(2), Integer.valueOf(rows.size()));
        check("rows[0].id", Long.valueOf(1L), rows.get(0).get("id").asLong());
        check("rows[0].label", "one", rows.get(0).get("label").asString());
        check("rows[1].label null", Boolean.TRUE, rows.get(1).get("label").isNull());

        check("empty object", Integer.valueOf(0), Integer.valueOf(root.get("empties").get("obj").asObject().size()));
        check("empty array", Integer.valueOf(0), Integer.valueOf(root.get("empties").get("arr").asArray().size()));

        // A malformed document must fail loudly.
        boolean threw = false;
        try {
            Json.parse("{\"broken\": ");
        } catch (RuntimeException ex) {
            threw = true;
        }
        check("malformed throws", Boolean.TRUE, threw);

        if (failures > 0) {
            System.err.println("JsonTest: " + failures + " assertion(s) failed");
            System.exit(1);
        }
        System.out.println("JsonTest: OK");
    }

    private static void check(String label, Object expected, Object actual) {
        if (expected == null ? actual != null : !expected.equals(actual)) {
            System.err.println("MISMATCH [" + label + "] expected <" + expected + "> but got <" + actual + ">");
            failures++;
        }
    }
}
