import com.statapi.Http;
import com.statapi.JsonValue;
import com.statapi.NotFoundException;
import com.statapi.NotReadyException;
import com.statapi.StatApiException;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Zero-dependency test for the 202 Accepted rule of the hand-written {@link Http}
 * transport.
 *
 * No JUnit (the SDK has no test dependencies): a plain main class the golden gate
 * compiles against the SDK sources and runs, like {@code JsonTest}. A 202 holds no
 * data, so {@code Http.get} throws {@link NotReadyException} instead of returning
 * the body, and never retries it. It runs against a local JDK
 * {@link HttpServer} on 127.0.0.1 and an ephemeral port: no live API. Exit 0
 * means every check passed.
 */
public final class NotReadyTest {

    private static final String QUEUED_BODY = "{"
        + "\"status\": \"queued\","
        + "\"message\": \"The lineups of this contest are queued to load.\","
        + "\"contest_id\": 29647259,"
        + "\"external_id\": 196420231,"
        + "\"requested_at\": \"2026-10-12T02:26:11Z\","
        + "\"estimated_ready_at\": \"2026-10-12T02:42:00Z\","
        + "\"retry_after_seconds\": 120,"
        + "\"entry_count\": 340"
        + "}";

    private static int failures = 0;
    private static int checks = 0;

    /** One scripted reply. */
    private static final class Reply {
        final int status;
        final String body;
        final Map<String, String> headers;

        Reply(int status, String body, Map<String, String> headers) {
            this.status = status;
            this.body = body;
            this.headers = headers;
        }
    }

    /** A local fake API that serves scripted replies in order (then 200 {}). */
    private static final class FakeApi {
        private final HttpServer server;
        private final Deque<Reply> replies = new ArrayDeque<>();
        private final List<String> paths = new CopyOnWriteArrayList<>();
        private final List<String> bodies = new CopyOnWriteArrayList<>();

        FakeApi() throws IOException {
            server = HttpServer.create(new InetSocketAddress(InetAddress.getLoopbackAddress(), 0), 0);
            server.createContext("/", exchange -> {
                paths.add(exchange.getRequestURI().toString());
                bodies.add(new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8));
                Reply next;
                synchronized (replies) {
                    next = replies.poll();
                }
                if (next == null) {
                    next = new Reply(200, "{}", Map.of());
                }
                byte[] payload = next.body.getBytes(StandardCharsets.UTF_8);
                exchange.getResponseHeaders().add("Content-Type", "application/json");
                for (Map.Entry<String, String> header : next.headers.entrySet()) {
                    exchange.getResponseHeaders().add(header.getKey(), header.getValue());
                }
                exchange.sendResponseHeaders(next.status, payload.length);
                try (OutputStream out = exchange.getResponseBody()) {
                    out.write(payload);
                }
            });
            server.start();
        }

        FakeApi reply(int status, String body, Map<String, String> headers) {
            synchronized (replies) {
                replies.add(new Reply(status, body, headers));
            }
            return this;
        }

        FakeApi reply(int status, String body) {
            return reply(status, body, Map.of());
        }

        Http client() {
            return new Http("test-key", "http://127.0.0.1:" + server.getAddress().getPort(), null);
        }

        int requests() {
            return paths.size();
        }

        /** The body of the request at position {@code index} (0 is the first). */
        String body(int index) {
            return bodies.get(index);
        }

        void close() {
            server.stop(0);
        }
    }

    public static void main(String[] args) throws IOException {
        carriesEveryFieldAndIsNotRetried();
        waitingForContestHasNullEstimate();
        retryAfterSourceOrder();
        nonJsonBodyStillRaises();
        otherStatusesAreUnchanged();
        nflWeeksAndSimNFLEndpoints();

        if (failures > 0) {
            System.err.println("NotReadyTest: " + failures + " assertion(s) failed");
            System.exit(1);
        }
        System.out.println("NotReadyTest: OK (" + checks + " checks)");
    }

    private static void nflWeeksAndSimNFLEndpoints() throws IOException {
        String nflRunJson = "{\"matchup\":{\"game_id\":43418,\"season\":2026,\"week\":1,\"season_type\":\"REG\",\"home_team_id\":12,\"home_abbr\":\"KC\",\"away_team_id\":7,\"away_abbr\":\"DEN\",\"spread_home\":-3.0,\"game_total\":47.5,\"home_team_total\":25.25,\"away_team_total\":22.25,\"is_final\":true,\"is_free\":true},\"input_sha256\":\"sha256-nfl\",\"output\":{\"game_id\":43418,\"n_draws\":5000,\"seed\":42}}";
        String nbaRunJson = "{\"matchup\":{\"game_id\":6029936,\"season\":2025,\"week\":1,\"season_type\":\"REG\",\"kickoff\":\"2025-11-12T00:00:00Z\",\"day\":1,\"home_team_id\":2,\"home_abbr\":\"BOS\",\"away_team_id\":8,\"away_abbr\":\"DET\",\"spread_home\":-8.5,\"game_total\":228.5,\"home_team_total\":118.5,\"away_team_total\":110.0,\"moneyline_home\":-340,\"moneyline_away\":270,\"is_final\":true,\"is_free\":true},\"input_sha256\":\"sha256-nba\",\"output\":{\"game_id\":\"6029936\",\"n_draws\":5000,\"seed\":42}}";
        FakeApi api = new FakeApi()
            .reply(200, "{\"season\":2026,\"available_seasons\":[2026],\"free_game_id\":43418,\"weeks\":[1],\"games\":[{\"game_id\":43418,\"season\":2026,\"week\":1,\"season_type\":\"REG\",\"home_team_id\":12,\"home_abbr\":\"KC\",\"away_team_id\":7,\"away_abbr\":\"DEN\",\"spread_home\":-3.0,\"game_total\":47.5,\"home_team_total\":25.25,\"away_team_total\":22.25,\"is_final\":true,\"is_free\":true}]}")
            .reply(200, nflRunJson)
            .reply(200, "{\"season\":2025,\"available_seasons\":[2025],\"free_game_id\":6029936,\"weeks\":[1],\"games\":[]}")
            .reply(200, nbaRunJson);
        try {
            com.statapi.StatApi statApi = new com.statapi.StatApi("test-key", "http://127.0.0.1:" + api.server.getAddress().getPort());
            com.statapi.NFLWeeksResponse weeks = statApi.nfl().weeks(2026);
            check("nfl().weeks(2026) season", Integer.valueOf(2026), weeks.season());
            check("nfl().weeks(2026) freeGameId", Long.valueOf(43418L), weeks.freeGameId());
            check("nfl().weeks(2026) games size", Integer.valueOf(1), Integer.valueOf(weeks.games().size()));

            com.statapi.NFLSimPublicRunResponse runRes = statApi.sim().nfl().run(
                new com.statapi.NFLSimPublicRunRequest(43418L)
                    .nDraws(5000)
                    .seed(42L)
                    .include(new com.statapi.NFLSimInclude().summary(true).distributions(true).scorebox(false))
            );
            check(
                "sim().nfl().run request body",
                "{\"game_id\":43418,\"n_draws\":5000,\"seed\":42,\"include\":{\"summary\":true,\"distributions\":true,\"scorebox\":false}}",
                api.body(1));
            check("sim().nfl().run n_draws", Integer.valueOf(5000), runRes.output().nDraws());
            check("sim().nfl().run home abbr", "KC", runRes.matchup().homeAbbr());

            com.statapi.NBASimWeeksResponse nbaWeeks = statApi.nba().weeks(2025);
            check("nba().weeks(2025) freeGameId", Long.valueOf(6029936L), nbaWeeks.freeGameId());

            com.statapi.NBASimPublicRunResponse nbaRunRes = statApi.sim().nba().run(
                new com.statapi.NBASimPublicRunRequest(6029936L).nDraws(5000).seed(42L).total(228.5));
            check("sim().nba().run home abbr", "BOS", nbaRunRes.matchup().homeAbbr());

            com.statapi.NBASimPlayerCorrelations corrs = com.statapi.NBASimPlayerCorrelations.fromJson(
                com.statapi.Json.parse("{\"scope\":\"global_rollout\",\"player_ids\":[\"1\",\"2\"],\"matrix\":[[1.0,null],[null,1.0]]}"));
            check("NBASimPlayerCorrelations matrix[0][0]", Double.valueOf(1.0), corrs.matrix().get(0).get(0));
            check("NBASimPlayerCorrelations matrix[0][1] null", null, corrs.matrix().get(0).get(1));

            // A required member that is missing fails loud, and a nested record writes itself.
            try {
                new com.statapi.NFLSimPublicRunRequest().toJson();
                check("a run request without game_id throws", "IllegalArgumentException", "no exception");
            } catch (IllegalArgumentException expected) {
                check("a run request without game_id throws", "game_id is required", expected.getMessage());
            }
        } finally {
            api.close();
        }
    }

    private static void carriesEveryFieldAndIsNotRetried() throws IOException {
        FakeApi api = new FakeApi().reply(202, QUEUED_BODY, Map.of("Retry-After", "120"));
        try {
            NotReadyException err = expectNotReady(
                "202 throws NotReadyException", () -> api.client().get("/api/v1/dfs/contests/29647259/download", null));
            if (err == null) {
                return;
            }
            check("is a StatApiException", Boolean.TRUE, err instanceof StatApiException);
            check("status 202", Integer.valueOf(202), Integer.valueOf(err.status()));
            check("path", "/api/v1/dfs/contests/29647259/download", err.path());
            check("retryAfterSeconds", Long.valueOf(120L), err.retryAfterSeconds());
            check("estimatedReadyAt", "2026-10-12T02:42:00Z", err.estimatedReadyAt());
            check("state", "queued", err.state());
            check("message", "The lineups of this contest are queued to load.", err.message());
            check("body entry_count", Long.valueOf(340L), err.body().get("entry_count").asLong());
            check("body contest_id", Long.valueOf(29647259L), err.body().get("contest_id").asLong());
            check("getMessage names the status", Boolean.TRUE, err.getMessage().contains("202"));
            check("202 is not retried", Integer.valueOf(1), Integer.valueOf(api.requests()));
        } finally {
            api.close();
        }
    }

    private static void waitingForContestHasNullEstimate() throws IOException {
        FakeApi api = new FakeApi().reply(202,
            "{\"status\":\"waiting_for_contest\",\"message\":\"waiting\",\"estimated_ready_at\":null,"
                + "\"retry_after_seconds\":900}");
        try {
            NotReadyException err = expectNotReady(
                "waiting_for_contest throws", () -> api.client().get("/api/v1/dfs/contests/1/standings", null));
            if (err == null) {
                return;
            }
            check("waiting state", "waiting_for_contest", err.state());
            check("null estimatedReadyAt", null, err.estimatedReadyAt());
            check("waiting retryAfterSeconds", Long.valueOf(900L), err.retryAfterSeconds());
        } finally {
            api.close();
        }
    }

    private static void retryAfterSourceOrder() throws IOException {
        retryAfterCase("header used when the body has none", "{\"status\":\"queued\"}", "45", Long.valueOf(45L));
        retryAfterCase("body wins over the header", "{\"retry_after_seconds\":120}", "999", Long.valueOf(120L));
        retryAfterCase("zero in the body is kept", "{\"retry_after_seconds\":0}", "30", Long.valueOf(0L));
        retryAfterCase("HTTP-date header is unusable", "{\"status\":\"queued\"}",
            "Wed, 21 Oct 2026 07:28:00 GMT", null);
        retryAfterCase("negative header is unusable", "{\"status\":\"queued\"}", "-5", null);
        retryAfterCase("neither gives null", "{\"status\":\"queued\"}", null, null);
    }

    private static void retryAfterCase(String label, String body, String header, Long expected) throws IOException {
        Map<String, String> headers = header == null ? Map.of() : Map.of("Retry-After", header);
        FakeApi api = new FakeApi().reply(202, body, headers);
        try {
            NotReadyException err = expectNotReady(
                label, () -> api.client().get("/api/v1/dfs/contests/1/lineups", null));
            if (err != null) {
                check("retryAfterSeconds: " + label, expected, err.retryAfterSeconds());
            }
        } finally {
            api.close();
        }
    }

    private static void nonJsonBodyStillRaises() throws IOException {
        FakeApi api = new FakeApi().reply(202, "accepted");
        try {
            NotReadyException err = expectNotReady(
                "non-JSON 202 throws", () -> api.client().get("/api/v1/dfs/contests/1/download", null));
            if (err == null) {
                return;
            }
            check("non-JSON body is the null node", Boolean.TRUE, err.body().isNull());
            check("non-JSON state null", null, err.state());
            check("non-JSON estimatedReadyAt null", null, err.estimatedReadyAt());
            check("non-JSON retryAfterSeconds null", null, err.retryAfterSeconds());
            check("non-JSON message default", Boolean.TRUE, err.message() != null && !err.message().isEmpty());
        } finally {
            api.close();
        }
    }

    private static void otherStatusesAreUnchanged() throws IOException {
        // A 200 still returns data.
        FakeApi ok = new FakeApi().reply(200, "{\"id\":1,\"name\":\"Cardinals\"}");
        try {
            JsonValue body = ok.client().get("/api/v1/nfl/teams/1", null).body();
            check("200 returns data: id", Long.valueOf(1L), body.get("id").asLong());
            check("200 returns data: name", "Cardinals", body.get("name").asString());
            check("200 is one request", Integer.valueOf(1), Integer.valueOf(ok.requests()));
        } finally {
            ok.close();
        }

        // A 500 is still retried until a 200.
        FakeApi retried = new FakeApi()
            .reply(500, "{\"error\":\"internal\"}")
            .reply(500, "{\"error\":\"internal\"}")
            .reply(200, "{\"id\":1}");
        try {
            JsonValue body = retried.client().get("/api/v1/nfl/teams/1", null).body();
            check("500 retried then data", Long.valueOf(1L), body.get("id").asLong());
            check("500 retried: three requests", Integer.valueOf(3), Integer.valueOf(retried.requests()));
        } finally {
            retried.close();
        }

        // A 500 that never recovers is a plain StatApiException after three attempts.
        FakeApi failing = new FakeApi()
            .reply(500, "{\"error\":\"internal\"}")
            .reply(500, "{\"error\":\"internal\"}")
            .reply(500, "{\"error\":\"internal\"}");
        try {
            try {
                failing.client().get("/api/v1/nfl/teams/1", null);
                check("500 exhausted throws", Boolean.TRUE, Boolean.FALSE);
            } catch (NotReadyException ex) {
                check("500 is not NotReadyException", Boolean.TRUE, Boolean.FALSE);
            } catch (StatApiException ex) {
                check("500 exhausted status", Integer.valueOf(500), Integer.valueOf(ex.status()));
            }
            check("500 exhausted: three requests", Integer.valueOf(3), Integer.valueOf(failing.requests()));
        } finally {
            failing.close();
        }

        // A 404 is still NotFoundException and is not retried.
        FakeApi missing = new FakeApi().reply(404, "{\"error\":\"not-found\"}");
        try {
            try {
                missing.client().get("/api/v1/nfl/teams/99999", null);
                check("404 throws", Boolean.TRUE, Boolean.FALSE);
            } catch (NotFoundException ex) {
                check("404 status", Integer.valueOf(404), Integer.valueOf(ex.status()));
            }
            check("404 is one request", Integer.valueOf(1), Integer.valueOf(missing.requests()));
        } finally {
            missing.close();
        }
    }

    /** Runs the call; returns the NotReadyException it throws, or records a failure and returns null. */
    private static NotReadyException expectNotReady(String label, Runnable call) {
        checks++;
        try {
            call.run();
        } catch (NotReadyException ex) {
            return ex;
        } catch (RuntimeException ex) {
            System.err.println("MISMATCH [" + label + "] expected NotReadyException but got " + ex);
            failures++;
            return null;
        }
        System.err.println("MISMATCH [" + label + "] expected NotReadyException but the call returned data");
        failures++;
        return null;
    }

    private static void check(String label, Object expected, Object actual) {
        checks++;
        if (expected == null ? actual != null : !expected.equals(actual)) {
            System.err.println("MISMATCH [" + label + "] expected <" + expected + "> but got <" + actual + ">");
            failures++;
        }
    }
}
