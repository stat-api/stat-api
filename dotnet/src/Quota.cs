namespace StatApi;

/// <summary>
/// Snapshot of the caller's monthly record quota.
/// </summary>
/// <remarks>
/// Hand-written core (not generated). Parsed from the <c>X-Quota-*</c> response
/// headers stamped on every API response; any component the server omits is
/// <c>null</c>.
/// </remarks>
public sealed class Quota
{
    public long? Limit { get; }
    public long? Used { get; }
    public long? Remaining { get; }

    public Quota(long? limit, long? used, long? remaining)
    {
        Limit = limit;
        Used = used;
        Remaining = remaining;
    }

    internal static Quota? FromHeaders(HttpResponseMessage response)
    {
        var limit = Read(response, "x-quota-limit");
        var used = Read(response, "x-quota-used");
        var remaining = Read(response, "x-quota-remaining");
        if (limit is null && used is null && remaining is null)
        {
            return null;
        }
        return new Quota(limit, used, remaining);
    }

    private static long? Read(HttpResponseMessage response, string name)
    {
        if (response.Headers.TryGetValues(name, out var values))
        {
            foreach (var value in values)
            {
                if (long.TryParse(value, out var parsed))
                {
                    return parsed;
                }
            }
        }
        return null;
    }
}
