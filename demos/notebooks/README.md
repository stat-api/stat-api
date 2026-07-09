# stat-api analytics notebooks

Jupyter notebooks showing real analysis on top of the public **stat-api** Python SDK (`statapi`),
organized by **who they're for**. Each notebook is self-contained, reads only public endpoints, and
commits its executed outputs so the whole story is readable on GitHub without running anything.

## Pick your audience

| You are… | Folder | Notebook | What you'll learn |
|----------|--------|----------|-------------------|
| A **daily-fantasy** player / lineup builder | [`daily-fantasy/`](./daily-fantasy/) | `dfs-value-screen.ipynb` | Join slate salaries to projections and rank by points per $1k — plus the **required filter set** (`dfs.slates` needs `operator_id` + `date`). |
| A **sports bettor** / odds modeler | [`betting/`](./betting/) | `line-movement.ipynb` | Pull `game_lines` for one MLB game and plot each book's de-vigged implied **win-probability** curve, opener vs close. |
| A **prediction-market** trader / quant | [`prediction-markets/`](./prediction-markets/) | `kalshi-microstructure.ipynb` | Reconstruct a settled NBA game's minute-by-minute Kalshi implied win probability from candles, **joined to the game via `competition_id`**. |
| A **sports quant** / data scientist | [`sports-quants/`](./sports-quants/) | `player-trends.ipynb` | Page a full player game log with the SDK's **auto-paging `.iter()`**, then compute 10-game rolling averages in pandas. |

Each folder has its own README with the audience, the lesson, and the unique data it proves out.
**New notebooks go inside an audience folder** (add a row here and a line to that folder's README) —
the folder is the organizing principle, not a number prefix.

## Install

`statapi` is not on PyPI yet (publishes in S11 / DM5). Until then, install the local SDK editable
from the monorepo, into a fresh virtualenv:

```bash
cd apps/demos/notebooks
python3 -m venv .venv && source .venv/bin/activate
pip install -e ../../../sdks/python      # the statapi SDK
pip install -r requirements.txt          # pandas, matplotlib, jupyter
```

`requirements.txt` lists `statapi` so the file is release-ready; the editable install above
satisfies it today, and you swap in the published package once it lands.

Set your key once:

```bash
export STAT_API_KEY=sdb_xxxxxxxx_...
```

By default the SDK talks to production (`https://api.stat-api.com`). To run against a local mirror,
also export `STAT_API_BASE_URL` (for example `http://localhost:3399`).

## Run

Open any notebook in Jupyter:

```bash
jupyter lab betting/line-movement.ipynb
```

Or re-execute every notebook end-to-end and write outputs back into the files (this is what
regenerates the committed outputs before mirroring):

```bash
export STAT_API_KEY=sdb_...        # required; add STAT_API_BASE_URL for a local mirror
./verify.sh
```

`verify.sh` builds its own fresh `.venv`, installs the SDK + deps, registers a private Jupyter
kernel for that venv, and runs `nbconvert --execute --inplace` on every notebook (it walks the
audience folders), failing on the first cell error. Each notebook's final cell prints its remaining
monthly quota, so a run reports its own record burn.

## CI (syntax only, no key)

Public CI never has secrets and never calls the API. `ci-lint.sh` converts each notebook to a
script and checks it — `nbconvert --to script` → `py_compile` → `ruff check` — with no execution:

```bash
./ci-lint.sh
```

## Conventions

- **`requirements.txt` owns installation** — no `%pip install` magics inside cells.
- **Plain top-cell constants** for parameters (game id, player id, slate id) — no papermill, no
  hidden configuration.
- **Executed outputs are committed.** On GitHub these notebooks are read far more than they are
  run; the committed cell outputs (tables + plots) are the deliverable. Each notebook is budgeted
  to well under ~10k records.
- Disposable venvs (`.venv/`, `.ci-venv/`) and Jupyter scratch are git-ignored.
