# daily-fantasy

**For daily-fantasy (DFS) players and lineup builders.**

- **What it teaches** — `dfs-value-screen.ipynb` builds a value screen for one slate: join each
  player's salary to their projection and rank by *points per $1,000 of salary*. Along the way it
  demonstrates a **required filter set** — `dfs.slates` must be queried with **both** `operator_id`
  and `date`, or it returns nothing.
- **Unique stat-api data it proves out** — DFS **slates, salaries, and projections** for a real
  operator/date, plus the required-filter discipline that makes those endpoints usable.

See [`../README.md`](../README.md) for install, keys, and how to run.
