package com.statapi;

/**
 * 202 — accepted, but the data is not ready yet.
 *
 * Raised when a paid caller asks for something the API has queued to load (for
 * example the lineups of a contest whose lineup file is not loaded yet). A 202
 * holds no data, so the SDK throws instead of handing the body back as the
 * route's data. Never retried inside the SDK: a 202 is not a transient failure,
 * the caller decides when to ask again. {@link #body()} is the parsed 202 body
 * verbatim.
 */
public final class NotReadyException extends StatApiException {

    private static final String DEFAULT_MESSAGE = "The data is not ready yet.";

    private final Long retryAfterSeconds;
    private final String estimatedReadyAt;
    private final String state;
    private final String serverMessage;

    public NotReadyException(int status, JsonValue body, String path) {
        this(status, body, path, null);
    }

    /**
     * @param retryAfterHeader the raw {@code Retry-After} response header, used
     *                         when the body carries no {@code retry_after_seconds};
     *                         {@code null} when the response had none
     */
    public NotReadyException(int status, JsonValue body, String path, String retryAfterHeader) {
        super(status, body, path);
        JsonValue envelope = body != null ? body : JsonValue.NULL;
        this.retryAfterSeconds = firstNonNull(
            nonNegative(envelope.get("retry_after_seconds").asLong()),
            parseSeconds(retryAfterHeader));
        this.estimatedReadyAt = nonEmpty(envelope.get("estimated_ready_at").asString());
        this.state = nonEmpty(envelope.get("status").asString());
        String sentence = nonEmpty(envelope.get("message").asString());
        this.serverMessage = sentence != null ? sentence : DEFAULT_MESSAGE;
    }

    /**
     * Seconds to wait before asking again: the body's {@code retry_after_seconds},
     * else the {@code Retry-After} header (seconds form), else {@code null}.
     */
    public Long retryAfterSeconds() {
        return retryAfterSeconds;
    }

    /**
     * ISO-8601 instant the data is expected, or {@code null} when the server
     * gives none (state {@code waiting_for_contest}).
     */
    public String estimatedReadyAt() {
        return estimatedReadyAt;
    }

    /** The body's {@code status}: {@code queued} or {@code waiting_for_contest}; {@code null} when absent. */
    public String state() {
        return state;
    }

    /** The server's plain sentence (a default sentence when the body has none). */
    public String message() {
        return serverMessage;
    }

    private static Long nonNegative(Long value) {
        return value != null && value >= 0 ? value : null;
    }

    private static Long firstNonNull(Long first, Long second) {
        return first != null ? first : second;
    }

    private static String nonEmpty(String value) {
        return value != null && !value.isEmpty() ? value : null;
    }

    /** Digits-only seconds (the delta-seconds form); an HTTP-date or junk is unusable and yields null. */
    private static Long parseSeconds(String raw) {
        if (raw == null) {
            return null;
        }
        try {
            return nonNegative(Long.valueOf(raw.trim()));
        } catch (NumberFormatException ex) {
            return null;
        }
    }
}
