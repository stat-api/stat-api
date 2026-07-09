package com.statapi;

import java.net.http.HttpHeaders;
import java.util.Optional;

/**
 * Snapshot of the caller's monthly record quota.
 *
 * Hand-written core (not generated). Parsed from the {@code X-Quota-*} response
 * headers stamped on every API response; any component the server omits is
 * {@code null}.
 */
public final class Quota {

    private final Long limit;
    private final Long used;
    private final Long remaining;

    public Quota(Long limit, Long used, Long remaining) {
        this.limit = limit;
        this.used = used;
        this.remaining = remaining;
    }

    /** Total records allowed this period, or {@code null} when omitted. */
    public Long limit() {
        return limit;
    }

    /** Records consumed this period, or {@code null} when omitted. */
    public Long used() {
        return used;
    }

    /** Records remaining this period, or {@code null} when omitted. */
    public Long remaining() {
        return remaining;
    }

    /** Read the {@code X-Quota-*} headers, or {@code null} when none present. */
    static Quota fromHeaders(HttpHeaders headers) {
        Long limit = header(headers, "x-quota-limit");
        Long used = header(headers, "x-quota-used");
        Long remaining = header(headers, "x-quota-remaining");
        if (limit == null && used == null && remaining == null) {
            return null;
        }
        return new Quota(limit, used, remaining);
    }

    private static Long header(HttpHeaders headers, String name) {
        Optional<String> raw = headers.firstValue(name);
        if (raw.isEmpty()) {
            return null;
        }
        try {
            return Long.valueOf(raw.get().trim());
        } catch (NumberFormatException ex) {
            return null;
        }
    }
}
