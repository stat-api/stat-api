// DO NOT EDIT — generated from schema/api/sim/nfl_sim.yml by generate-sim-sdks.ts
package com.statapi;

import java.util.HashMap;
import java.util.Map;

/**
 * Resource accessor for the {@code /api/v1/sim/nfl/*} and {@code /api/v1/nfl/weeks}
 * endpoints. Game {@code 43418} is free. Other games need a Pro or Elite plan, or the DFS add-on.
 */
public final class SimNFLResource {

    private final Http http;

    public SimNFLResource(Http http) {
        this.http = http;
    }

    /**
     * Free schedule and week discovery on {@code GET /api/v1/nfl/weeks}.
     *
     * @see <a href="https://stat-api.com/docs/api/nfl/seasons/#endpoint-nfl-seasons-weeks">API reference</a>
     * @see <a href="https://stat-api.com/docs/cookbook/sim/">Tutorial: recipes 1, 2, 3, 4, 5</a>
     */
    public NFLWeeksResponse weeks() {
        return weeks(null);
    }

    /**
     * Free schedule and week discovery on {@code GET /api/v1/nfl/weeks}.
     *
     * @see <a href="https://stat-api.com/docs/api/nfl/seasons/#endpoint-nfl-seasons-weeks">API reference</a>
     * @see <a href="https://stat-api.com/docs/cookbook/sim/">Tutorial: recipes 1, 2, 3, 4, 5</a>
     */
    public NFLWeeksResponse weeks(Integer season) {
        Map<String, String> query = new HashMap<>();
        if (season != null) query.put("season", String.valueOf(season));
        Http.Response res = http.get("/api/v1/nfl/weeks", query);
        return NFLWeeksResponse.fromJson(res.body(), res.quota());
    }

    /**
     * Runs N parallel Monte Carlo simulations ({@code POST /api/v1/sim/nfl/run}).
     *
     * @see <a href="https://stat-api.com/docs/api/sim/#endpoint-sim-nfl-run">API reference</a>
     * @see <a href="https://stat-api.com/docs/cookbook/sim/">Tutorial: recipes 1, 2, 3, 4, 5</a>
     */
    public NFLSimPublicRunResponse run(NFLSimPublicRunRequest request) {
        Http.Response res = http.post("/api/v1/sim/nfl/run", request.toJson());
        return NFLSimPublicRunResponse.fromJson(res.body(), res.quota());
    }
}
