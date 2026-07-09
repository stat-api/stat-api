package com.statapi;

/** 402 — the endpoint requires a paid plan the caller does not hold. */
public final class PlanRequiredException extends StatApiException {
    public PlanRequiredException(int status, JsonValue body, String path) {
        super(status, body, path);
    }
}
