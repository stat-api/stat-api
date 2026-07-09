package com.statapi;

/** 401 — the API key is missing, malformed, or rejected. */
public final class AuthenticationException extends StatApiException {
    public AuthenticationException(int status, JsonValue body, String path) {
        super(status, body, path);
    }
}
