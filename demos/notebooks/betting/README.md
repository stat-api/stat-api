# betting

**For sports bettors and odds modelers.**

- **What it teaches** — `line-movement.ipynb` pulls the full `game_lines` history for one MLB game
  and turns each sportsbook's two-sided price into an implied **win-probability** curve. Books quote
  with a margin (the *vig*), so it de-vigs before plotting, then tabulates opener vs close.
- **Unique stat-api data it proves out** — `game_lines` **line movement**: per-book price history
  from opener to close across sportsbooks, converted to de-vigged implied probability. Not a
  single closing number — the whole path the market walked.

See [`../README.md`](../README.md) for install, keys, and how to run.
