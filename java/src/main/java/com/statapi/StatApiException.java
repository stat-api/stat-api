package com.statapi;

/**
 * Base class for every error the SDK raises.
 *
 * Hand-written core (not generated). {@code status} is the HTTP status ({@code 0}
 * for a transport-level failure with no response), {@code body} is the parsed
 * JSON envelope (or {@link JsonValue#NULL} when none), and {@code path} is the
 * request path that produced it. Unchecked so callers opt into handling.
 */
public class StatApiException extends RuntimeException {

    private final int status;
    private final transient JsonValue body;
    private final String path;

    public StatApiException(int status, JsonValue body, String path) {
        this(status, body, path, null);
    }

    public StatApiException(int status, JsonValue body, String path, String message) {
        super(message != null ? message : buildMessage(status, path, body));
        this.status = status;
        this.body = body != null ? body : JsonValue.NULL;
        this.path = path;
    }

    /** HTTP status, or {@code 0} for a transport-level failure. */
    public int status() {
        return status;
    }

    /** Parsed server error envelope ({@link JsonValue#NULL} when none). */
    public JsonValue body() {
        return body;
    }

    /** Request path that produced this error. */
    public String path() {
        return path;
    }

    /** Map an HTTP status onto the matching typed exception subclass. */
    public static StatApiException forStatus(int status, JsonValue body, String path) {
        switch (status) {
            case 400:
                return new ValidationException(status, body, path);
            case 401:
                return new AuthenticationException(status, body, path);
            case 402:
                return new PlanRequiredException(status, body, path);
            case 404:
                return new NotFoundException(status, body, path);
            case 429:
                return new QuotaExceededException(status, body, path);
            default:
                return new StatApiException(status, body, path);
        }
    }

    private static String buildMessage(int status, String path, JsonValue body) {
        String detail = body != null ? body.get("message").asString() : null;
        if (detail == null) {
            detail = "HTTP " + status;
        }
        return "stat-api " + status + " on " + path + ": " + detail;
    }
}
