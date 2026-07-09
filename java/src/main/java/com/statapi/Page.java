package com.statapi;

import java.util.List;

/**
 * One page of list results plus the keyset cursor to the next page.
 *
 * Hand-written core (not generated). {@code nextFromId} is {@code null} on the
 * final page; a resource's {@code iterate()} walks these cursors for you.
 * {@code quota} reflects the {@code X-Quota-*} headers stamped on the response
 * (or {@code null} when the server omitted them).
 *
 * @param <T> the row type for this list endpoint
 */
public final class Page<T> {

    private final List<T> rows;
    private final Long limit;
    private final Long nextFromId;
    private final Quota quota;

    public Page(List<T> rows, Long limit, Long nextFromId, Quota quota) {
        this.rows = rows;
        this.limit = limit;
        this.nextFromId = nextFromId;
        this.quota = quota;
    }

    /** Rows on this page. */
    public List<T> rows() {
        return rows;
    }

    /** Effective page-size limit the server applied (or {@code null}). */
    public Long limit() {
        return limit;
    }

    /** Cursor to the next page, or {@code null} on the final page. */
    public Long nextFromId() {
        return nextFromId;
    }

    /** Quota snapshot from this response, or {@code null} when absent. */
    public Quota quota() {
        return quota;
    }
}
