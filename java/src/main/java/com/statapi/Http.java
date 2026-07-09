package com.statapi;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Map;

/**
 * Authenticated GET transport against the stat-api surface.
 *
 * Hand-written core (not generated), built on the JDK 17 {@link HttpClient} with
 * zero third-party dependencies. Resolves the API key / base URL from explicit
 * arguments then the environment, stamps a non-default {@code User-Agent}
 * (Cloudflare rejects the JDK default), retries transient failures and 5xx
 * responses (never a 4xx — a 429 is a monthly budget, not a transient fault),
 * parses the {@code X-Quota-*} headers, and maps non-2xx statuses onto typed
 * {@link StatApiException} subclasses. The advertised version comes from the
 * generated {@link Version} constant so the User-Agent tracks {@code sdks/VERSION}.
 */
public final class Http {

    private static final String ENV_API_KEY = "STAT_API_KEY";
    private static final String ENV_BASE_URL = "STAT_API_BASE_URL";
    private static final String DEFAULT_BASE_URL = "https://api.stat-api.com";
    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(30);
    private static final int MAX_ATTEMPTS = 3;
    // Base backoff per retry (ms), jittered. Matches the SDK family: 250ms, 1s.
    private static final long[] BACKOFF_MS = {250L, 1000L};

    private final String apiKey;
    private final String baseUrl;
    private final Duration timeout;
    private final HttpClient client;
    private final String userAgent;

    /** Reads the API key + base URL entirely from the environment. */
    public Http() {
        this(null, null, null);
    }

    /**
     * @param apiKey  explicit key, or {@code null} to read {@code STAT_API_KEY}
     * @param baseUrl explicit base URL, or {@code null} to read
     *                {@code STAT_API_BASE_URL} then fall back to the default host
     * @param timeout per-request timeout, or {@code null} for 30s
     */
    public Http(String apiKey, String baseUrl, Duration timeout) {
        String key = apiKey != null ? apiKey : System.getenv(ENV_API_KEY);
        if (key == null || key.isEmpty()) {
            throw new IllegalStateException(
                "stat-api: no API key — pass apiKey or set " + ENV_API_KEY);
        }
        this.apiKey = key;
        String base = baseUrl != null ? baseUrl : System.getenv(ENV_BASE_URL);
        if (base == null || base.isEmpty()) {
            base = DEFAULT_BASE_URL;
        }
        this.baseUrl = stripTrailingSlash(base);
        this.timeout = timeout != null ? timeout : DEFAULT_TIMEOUT;
        this.client = HttpClient.newBuilder().connectTimeout(this.timeout).build();
        this.userAgent = "statapi-java/" + Version.VERSION;
    }

    /** A parsed response body paired with the request's quota snapshot. */
    public static final class Response {
        private final JsonValue body;
        private final Quota quota;

        Response(JsonValue body, Quota quota) {
            this.body = body;
            this.quota = quota;
        }

        public JsonValue body() {
            return body;
        }

        public Quota quota() {
            return quota;
        }
    }

    /** Perform a GET and return the parsed body + quota, or throw on non-2xx. */
    public Response get(String path, Map<String, String> query) {
        String url = baseUrl + path + encodeQuery(query);
        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
            .timeout(timeout)
            .header("Authorization", "Bearer " + apiKey)
            .header("User-Agent", userAgent)
            .header("Accept", "application/json")
            .GET()
            .build();

        for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
            boolean last = attempt == MAX_ATTEMPTS - 1;
            HttpResponse<String> response;
            try {
                response = client.send(request, HttpResponse.BodyHandlers.ofString());
            } catch (IOException ex) {
                if (last) {
                    throw new StatApiException(0, JsonValue.NULL, path,
                        "stat-api connection error on " + path + ": " + ex.getMessage());
                }
                sleepBackoff(attempt);
                continue;
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                throw new StatApiException(0, JsonValue.NULL, path,
                    "stat-api request interrupted on " + path);
            }

            int status = response.statusCode();
            if (status >= 200 && status < 300) {
                return new Response(Json.parse(response.body()), Quota.fromHeaders(response.headers()));
            }

            JsonValue body = safeParse(response.body());
            // Retry 5xx until attempts run out; 4xx (incl. 429) fails immediately.
            if (status >= 500 && !last) {
                sleepBackoff(attempt);
                continue;
            }
            throw StatApiException.forStatus(status, body, path);
        }
        throw new AssertionError("stat-api: retry loop exhausted without a result");
    }

    private static JsonValue safeParse(String body) {
        try {
            return Json.parse(body);
        } catch (RuntimeException ex) {
            return JsonValue.NULL;
        }
    }

    private static String encodeQuery(Map<String, String> query) {
        if (query == null || query.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder("?");
        boolean first = true;
        for (Map.Entry<String, String> entry : query.entrySet()) {
            if (entry.getValue() == null) {
                continue;
            }
            if (!first) {
                sb.append('&');
            }
            first = false;
            sb.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8))
                .append('=')
                .append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
        }
        return first ? "" : sb.toString();
    }

    private static String stripTrailingSlash(String url) {
        int end = url.length();
        while (end > 0 && url.charAt(end - 1) == '/') {
            end--;
        }
        return url.substring(0, end);
    }

    private static void sleepBackoff(int attempt) {
        long base = BACKOFF_MS[Math.min(attempt, BACKOFF_MS.length - 1)];
        long jittered = (long) (base * (0.5 + Math.random()));
        try {
            Thread.sleep(jittered);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}
