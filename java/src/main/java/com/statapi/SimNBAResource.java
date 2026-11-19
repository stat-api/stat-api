// DO NOT EDIT — generated from schema/api/sim/nba_sim.yml by generate-sim-sdks.ts
package com.statapi;

import java.util.HashMap;
import java.util.Map;

/**
 * Resource accessor for the {@code /api/v1/sim/nba/*} and {@code /api/v1/nba/weeks}
 * endpoints. Game {@code 6029936} is free. Other games need a Pro plan or the DFS add-on.
 */
public final class SimNBAResource {

    private final Http http;

    public SimNBAResource(Http http) {
        this.http = http;
    }

    /**
     * Free schedule and week discovery on {@code GET /api/v1/nba/weeks}.
     *
     * @see <a href="https://stat-api.com/docs/api/nba/seasons/#endpoint-nba-seasons-weeks">API reference</a>
     */
    public NBASimWeeksResponse weeks() {
        return weeks(null, null, null);
    }

    /**
     * Free schedule and week discovery on {@code GET /api/v1/nba/weeks?season=...}.
     *
     * @see <a href="https://stat-api.com/docs/api/nba/seasons/#endpoint-nba-seasons-weeks">API reference</a>
     */
    public NBASimWeeksResponse weeks(Integer season) {
        return weeks(season, null, null);
    }

    /**
     * Free schedule and week discovery on {@code GET /api/v1/nba/weeks}.
     *
     * @see <a href="https://stat-api.com/docs/api/nba/seasons/#endpoint-nba-seasons-weeks">API reference</a>
     */
    public NBASimWeeksResponse weeks(Integer season, Integer week, String seasonType) {
        Map<String, String> query = new HashMap<>();
        if (season != null) query.put("season", String.valueOf(season));
        if (week != null) query.put("week", String.valueOf(week));
        if (seasonType != null) query.put("season_type", String.valueOf(seasonType));
        Http.Response res = http.get("/api/v1/nba/weeks", query);
        return NBASimWeeksResponse.fromJson(res.body(), res.quota());
    }

    /**
     * Runs N parallel Monte Carlo simulations ({@code POST /api/v1/sim/nba/run}).
     *
     * @see <a href="https://stat-api.com/docs/api/sim/#endpoint-sim-nba-run">API reference</a>
     */
    public NBASimPublicRunResponse run(NBASimPublicRunRequest request) {
        Http.Response res = http.post("/api/v1/sim/nba/run", request.toJson());
        return NBASimPublicRunResponse.fromJson(res.body(), res.quota());
    }
}
