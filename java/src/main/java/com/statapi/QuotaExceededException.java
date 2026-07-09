package com.statapi;

/**
 * 429 — the caller's monthly record quota is exhausted.
 *
 * Never retried: the quota is a monthly budget, so retrying only burns latency.
 * Inspect {@link #resetsAt()} / {@link #upgradeUrl()} to recover.
 */
public final class QuotaExceededException extends StatApiException {

    private final Long limit;
    private final Long used;
    private final String resetsAt;
    private final String upgradeUrl;

    public QuotaExceededException(int status, JsonValue body, String path) {
        super(status, body, path);
        JsonValue envelope = body != null ? body : JsonValue.NULL;
        this.limit = envelope.get("limit").asLong();
        this.used = envelope.get("used").asLong();
        this.resetsAt = envelope.get("resets_at").asString();
        this.upgradeUrl = envelope.get("upgrade_url").asString();
    }

    /** Monthly record limit that was exceeded, or {@code null} when omitted. */
    public Long limit() {
        return limit;
    }

    /** Records consumed this period, or {@code null} when omitted. */
    public Long used() {
        return used;
    }

    /** ISO timestamp when the quota resets, or {@code null} when omitted. */
    public String resetsAt() {
        return resetsAt;
    }

    /** URL to upgrade the plan, or {@code null} when omitted. */
    public String upgradeUrl() {
        return upgradeUrl;
    }
}
