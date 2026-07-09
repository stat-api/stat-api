# prediction-markets

**For prediction-market traders and quant researchers.**

- **What it teaches** — `kalshi-microstructure.ipynb` reconstructs a settled NBA game's
  minute-by-minute **implied win probability** from Kalshi 1-minute candles (the midpoint of the
  yes bid/ask, in cents ≈ %) and shows how it converged to the final result. It reads the 2025-26
  archive, so it runs year-round — no live game required.
- **Unique stat-api data it proves out** — Kalshi **candles/trades microstructure joined to the
  underlying game via `competition_id`**, so market pricing lines up with the box score. Nobody
  else ships prediction-market tape stitched to sports events.

See [`../README.md`](../README.md) for install, keys, and how to run.
