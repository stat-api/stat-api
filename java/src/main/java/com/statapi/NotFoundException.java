package com.statapi;

/** 404 — no resource exists at the requested path or id. */
public final class NotFoundException extends StatApiException {
    public NotFoundException(int status, JsonValue body, String path) {
        super(status, body, path);
    }
}
