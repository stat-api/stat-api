package com.statapi;

/** 400 — malformed request or an unsatisfied required-filter set. */
public final class ValidationException extends StatApiException {
    public ValidationException(int status, JsonValue body, String path) {
        super(status, body, path);
    }
}
