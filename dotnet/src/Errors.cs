using System.Text.Json;

namespace StatApi;

/// <summary>
/// Base class for every error the SDK raises.
/// </summary>
/// <remarks>
/// Hand-written core (not generated). <see cref="Status"/> is the HTTP status
/// (<c>0</c> for a transport-level failure with no response), <see cref="Body"/>
/// is the raw server envelope, and <see cref="Path"/> is the request path that
/// produced it.
/// </remarks>
public class StatApiException : Exception
{
    /// <summary>HTTP status, or <c>0</c> for a transport-level failure.</summary>
    public int Status { get; }

    /// <summary>Raw server error envelope (empty when none).</summary>
    public string Body { get; }

    /// <summary>Request path that produced this error.</summary>
    public string Path { get; }

    public StatApiException(int status, string body, string path, string? message = null)
        : base(message ?? BuildMessage(status, path, body))
    {
        Status = status;
        Body = body;
        Path = path;
    }

    /// <summary>Map an HTTP status onto the matching typed exception.</summary>
    public static StatApiException ForStatus(int status, string body, string path) => status switch
    {
        400 => new ValidationException(status, body, path),
        401 => new AuthenticationException(status, body, path),
        402 => new PlanRequiredException(status, body, path),
        404 => new NotFoundException(status, body, path),
        429 => new QuotaExceededException(status, body, path),
        _ => new StatApiException(status, body, path),
    };

    private static string BuildMessage(int status, string path, string body)
    {
        var detail = TryReadString(body, "message") ?? $"HTTP {status}";
        return $"stat-api {status} on {path}: {detail}";
    }

    internal static string? TryReadString(string body, string name)
    {
        if (string.IsNullOrEmpty(body))
        {
            return null;
        }
        try
        {
            using var document = JsonDocument.Parse(body);
            if (document.RootElement.ValueKind == JsonValueKind.Object
                && document.RootElement.TryGetProperty(name, out var value)
                && value.ValueKind == JsonValueKind.String)
            {
                return value.GetString();
            }
        }
        catch (JsonException)
        {
            // Non-JSON error body — nothing to extract.
        }
        return null;
    }

    internal static long? TryReadLong(string body, string name)
    {
        if (string.IsNullOrEmpty(body))
        {
            return null;
        }
        try
        {
            using var document = JsonDocument.Parse(body);
            if (document.RootElement.ValueKind == JsonValueKind.Object
                && document.RootElement.TryGetProperty(name, out var value)
                && value.ValueKind == JsonValueKind.Number)
            {
                return value.GetInt64();
            }
        }
        catch (JsonException)
        {
            // Non-JSON error body — nothing to extract.
        }
        return null;
    }
}

/// <summary>401 — the API key is missing, malformed, or rejected.</summary>
public sealed class AuthenticationException : StatApiException
{
    public AuthenticationException(int status, string body, string path) : base(status, body, path)
    {
    }
}

/// <summary>402 — the endpoint requires a paid plan the caller does not hold.</summary>
public sealed class PlanRequiredException : StatApiException
{
    public PlanRequiredException(int status, string body, string path) : base(status, body, path)
    {
    }
}

/// <summary>404 — no resource exists at the requested path or id.</summary>
public sealed class NotFoundException : StatApiException
{
    public NotFoundException(int status, string body, string path) : base(status, body, path)
    {
    }
}

/// <summary>400 — malformed request or an unsatisfied required-filter set.</summary>
public sealed class ValidationException : StatApiException
{
    public ValidationException(int status, string body, string path) : base(status, body, path)
    {
    }
}

/// <summary>
/// 429 — the caller's monthly record quota is exhausted. Never retried (a
/// monthly budget, not a transient fault); inspect <see cref="ResetsAt"/> /
/// <see cref="UpgradeUrl"/> to recover.
/// </summary>
public sealed class QuotaExceededException : StatApiException
{
    public long? Limit { get; }
    public long? Used { get; }
    public string? ResetsAt { get; }
    public string? UpgradeUrl { get; }

    public QuotaExceededException(int status, string body, string path) : base(status, body, path)
    {
        Limit = TryReadLong(body, "limit");
        Used = TryReadLong(body, "used");
        ResetsAt = TryReadString(body, "resets_at");
        UpgradeUrl = TryReadString(body, "upgrade_url");
    }
}
