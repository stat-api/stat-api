# Pagination

stat-api uses **keyset pagination** everywhere. There is no offset paging —
`offset` parameters are rejected — so page depth never degrades query
performance.

## The envelope

Every list endpoint accepts `limit` and `from_id`, and returns rows keyed by
table name plus the paging fields:

```sh
curl -sS --compressed \
  -H "Authorization: Bearer $STAT_API_KEY" \
  "https://api.stat-api.com/api/v1/nba/players?limit=100&from_id=0"
```

```json
{
  "players": [ { "id": 1, "...": "..." } ],
  "limit": 100,
  "next_from_id": 101
}
```

Pass `next_from_id` back as the next request's `from_id`. When `next_from_id`
is `null`, you have the last page. Each endpoint caps `limit`; the envelope
echoes the value actually applied.

## Ordering

Two regimes, chosen by whether you pass `from_id`:

- **Without `from_id`** — lists return the *newest* rows first (descending
  id, or the table's documented default sort). Right for "latest N" reads.
- **With `from_id`** — the keyset walk is ascending by id. Right for full
  scans, which therefore arrive oldest-first.

## SDK iterators

Every SDK exposes an auto-paginating iterator on every table that handles the
`from_id` loop for you (ascending, complete, no duplicates):

```ts
for await (const p of api.nba.players.iter()) { ... }        // TypeScript
```
```python
for p in api.nba.players.iter(): ...                          # Python
```
```go
for p, err := range client.NBA.Players.All(ctx, nil) { ... }  // Go
```
```java
for (NBAPlayer p : api.nba().players().iterate(Map.of())) { } // Java
```
```csharp
await foreach (var p in client.Nba.Players.IterateAsync()) { }// C#
```

## Required filter sets

Some tables are too large to list unfiltered and require one of a set of
filter combinations — for example `dfs/slates` requires
`[operator_id, date]`. Calling without an accepted combination returns `400`
with the accepted sets in the body:

```json
{ "error": "missing_required_filters", "accepted": [["operator_id", "date"]] }
```

Filters compose with pagination: a filtered slice pages with
`limit`/`from_id` exactly like an unfiltered one.
