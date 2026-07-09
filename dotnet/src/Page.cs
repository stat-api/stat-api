namespace StatApi;

/// <summary>
/// One page of list results plus the keyset cursor to the next page.
/// </summary>
/// <remarks>
/// Hand-written core (not generated). <see cref="NextFromId"/> is <c>null</c> on
/// the final page; a resource's <c>IterateAsync</c> walks these cursors for you.
/// <see cref="Quota"/> reflects the <c>X-Quota-*</c> response headers (or
/// <c>null</c> when the server omitted them).
/// </remarks>
/// <typeparam name="T">the row type for this list endpoint</typeparam>
public sealed class Page<T>
{
    public IReadOnlyList<T> Rows { get; }
    public long? Limit { get; }
    public long? NextFromId { get; }
    public Quota? Quota { get; }

    public Page(IReadOnlyList<T> rows, long? limit, long? nextFromId, Quota? quota)
    {
        Rows = rows;
        Limit = limit;
        NextFromId = nextFromId;
        Quota = quota;
    }
}
