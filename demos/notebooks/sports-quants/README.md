# sports-quants

**For sports quants and data scientists.**

- **What it teaches** — `player-trends.ipynb` charts a hitter's recent form: per-game DraftKings
  fantasy points and batting average, smoothed with a **10-game rolling window** (pandas). It
  collects the full game log in one loop with the SDK's **auto-paging `.iter()`**, handling the
  newest-first server sort and de-duping defensively.
- **Unique stat-api data it proves out** — deep **per-game player logs** served through
  **auto-paging iterators**, so a multi-season history is one `.iter()` away — the raw material for
  rolling averages, form models, and features.

See [`../README.md`](../README.md) for install, keys, and how to run.
