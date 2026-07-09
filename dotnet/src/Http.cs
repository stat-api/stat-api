using System.Text.Json;

namespace StatApi;

/// <summary>
/// Authenticated GET transport against the stat-api surface.
/// </summary>
/// <remarks>
/// Hand-written core (not generated), built on <see cref="HttpClient"/> and
/// <c>System.Text.Json</c> from the net8.0 shared framework — zero package
/// dependencies. Resolves the API key / base URL from explicit arguments then
/// the environment, stamps a non-default <c>User-Agent</c>, retries transient
/// failures and 5xx responses (never a 4xx — a 429 is a monthly budget, not a
/// transient fault), parses the <c>X-Quota-*</c> headers, and maps non-2xx
/// statuses onto typed <see cref="StatApiException"/> subclasses. The advertised
/// version comes from the generated <c>Version</c> constant so the User-Agent
/// tracks <c>sdks/VERSION</c>.
/// </remarks>
public sealed class HttpTransport
{
    private const string EnvKey = "STAT_API_KEY";
    private const string EnvBaseUrl = "STAT_API_BASE_URL";
    private const string DefaultBaseUrl = "https://api.stat-api.com";
    private static readonly TimeSpan DefaultTimeout = TimeSpan.FromSeconds(30);
    private const int MaxAttempts = 3;
    // Base backoff per retry (ms), jittered. Matches the SDK family: 250ms, 1s.
    private static readonly int[] BackoffMs = { 250, 1000 };

    private readonly string _apiKey;
    private readonly string _baseUrl;
    private readonly HttpClient _client;
    private readonly string _userAgent;

    /// <param name="apiKey">explicit key, or <c>null</c> to read <c>STAT_API_KEY</c></param>
    /// <param name="baseUrl">explicit base URL, or <c>null</c> to read <c>STAT_API_BASE_URL</c> then the default host</param>
    /// <param name="timeout">per-request timeout, or <c>null</c> for 30s</param>
    public HttpTransport(string? apiKey = null, string? baseUrl = null, TimeSpan? timeout = null)
    {
        var key = apiKey ?? Environment.GetEnvironmentVariable(EnvKey);
        if (string.IsNullOrEmpty(key))
        {
            throw new InvalidOperationException($"stat-api: no API key — pass apiKey or set {EnvKey}");
        }
        _apiKey = key;
        var resolvedBase = baseUrl ?? Environment.GetEnvironmentVariable(EnvBaseUrl);
        if (string.IsNullOrEmpty(resolvedBase))
        {
            resolvedBase = DefaultBaseUrl;
        }
        _baseUrl = resolvedBase.TrimEnd('/');
        _client = new HttpClient { Timeout = timeout ?? DefaultTimeout };
        _userAgent = $"statapi-dotnet/{Version.Value}";
    }

    /// <summary>Perform a GET and return the parsed body root + quota, or throw on non-2xx.</summary>
    public async Task<(JsonElement Root, Quota? Quota)> GetAsync(
        string path,
        IEnumerable<KeyValuePair<string, string>>? query,
        CancellationToken cancellationToken)
    {
        var url = _baseUrl + path + BuildQuery(query);
        for (var attempt = 0; ; attempt++)
        {
            var last = attempt == MaxAttempts - 1;
            HttpResponseMessage response;
            try
            {
                using var request = new HttpRequestMessage(HttpMethod.Get, url);
                request.Headers.TryAddWithoutValidation("Authorization", $"Bearer {_apiKey}");
                request.Headers.TryAddWithoutValidation("User-Agent", _userAgent);
                request.Headers.TryAddWithoutValidation("Accept", "application/json");
                response = await _client.SendAsync(request, cancellationToken).ConfigureAwait(false);
            }
            catch (HttpRequestException ex)
            {
                if (last)
                {
                    throw new StatApiException(0, string.Empty, path,
                        $"stat-api connection error on {path}: {ex.Message}");
                }
                await BackoffAsync(attempt, cancellationToken).ConfigureAwait(false);
                continue;
            }
            catch (TaskCanceledException ex) when (!cancellationToken.IsCancellationRequested)
            {
                // A timeout (not a caller cancellation) surfaces as TaskCanceledException.
                if (last)
                {
                    throw new StatApiException(0, string.Empty, path,
                        $"stat-api request timed out on {path}: {ex.Message}");
                }
                await BackoffAsync(attempt, cancellationToken).ConfigureAwait(false);
                continue;
            }

            using (response)
            {
                var body = await response.Content.ReadAsStringAsync(cancellationToken).ConfigureAwait(false);
                var status = (int)response.StatusCode;
                if (status is >= 200 and < 300)
                {
                    return (Parse(body), Quota.FromHeaders(response));
                }
                // Retry 5xx until attempts run out; 4xx (incl. 429) fails immediately.
                if (status >= 500 && !last)
                {
                    await BackoffAsync(attempt, cancellationToken).ConfigureAwait(false);
                    continue;
                }
                throw StatApiException.ForStatus(status, body, path);
            }
        }
    }

    /// <summary>Read a nullable integral member off a list envelope.</summary>
    public static long? ReadLong(JsonElement root, string name)
    {
        if (root.ValueKind == JsonValueKind.Object
            && root.TryGetProperty(name, out var value)
            && value.ValueKind == JsonValueKind.Number)
        {
            return value.GetInt64();
        }
        return null;
    }

    private static JsonElement Parse(string body)
    {
        if (string.IsNullOrEmpty(body))
        {
            return default;
        }
        using var document = JsonDocument.Parse(body);
        // Clone detaches the element from the disposed document so callers can
        // keep reading it after this method returns.
        return document.RootElement.Clone();
    }

    private static string BuildQuery(IEnumerable<KeyValuePair<string, string>>? query)
    {
        if (query is null)
        {
            return string.Empty;
        }
        var pairs = new List<string>();
        foreach (var (key, value) in query)
        {
            if (value is null)
            {
                continue;
            }
            pairs.Add($"{Uri.EscapeDataString(key)}={Uri.EscapeDataString(value)}");
        }
        return pairs.Count == 0 ? string.Empty : "?" + string.Join("&", pairs);
    }

    private static async Task BackoffAsync(int attempt, CancellationToken cancellationToken)
    {
        var baseMs = BackoffMs[Math.Min(attempt, BackoffMs.Length - 1)];
        var jittered = (int)(baseMs * (0.5 + Random.Shared.NextDouble()));
        await Task.Delay(jittered, cancellationToken).ConfigureAwait(false);
    }
}
