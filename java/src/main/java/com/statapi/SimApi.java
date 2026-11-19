package com.statapi;

/**
 * Top-level {@code sim} module accessor on {@link StatApi} ({@code api.sim().nfl()}, {@code api.sim().nba()}).
 */
public final class SimApi {

    private final SimNFLResource nfl;
    private final SimNBAResource nba;

    public SimApi(Http http) {
        this.nfl = new SimNFLResource(http);
        this.nba = new SimNBAResource(http);
    }

    public SimNFLResource nfl() {
        return nfl;
    }

    public SimNBAResource nba() {
        return nba;
    }
}
