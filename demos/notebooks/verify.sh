#!/usr/bin/env bash
# Full execution check — builds a fresh venv, installs the SDK + deps, and
# executes every notebook end-to-end. Fails on the first cell error.
#
# Requires a real key:
#   export STAT_API_KEY=sdb_xxxxxxxx_...
# To run against a local mirror instead of production:
#   export STAT_API_BASE_URL=http://localhost:3399
#
# Executed outputs are written back INTO the .ipynb files (--inplace), which is
# the committed deliverable. Usage:  ./verify.sh
set -euo pipefail
cd "$(dirname "$0")"

if [ -z "${STAT_API_KEY:-}" ]; then
  echo "verify.sh: STAT_API_KEY is required (export it first)" >&2
  exit 2
fi

VENV=".venv"
echo "· fresh venv at $VENV"
rm -rf "$VENV"
python3 -m venv "$VENV"
"$VENV/bin/python" -m pip install --quiet --upgrade pip
"$VENV/bin/python" -m pip install --quiet -e ../../../sdks/python
"$VENV/bin/python" -m pip install --quiet -r requirements.txt

# Register the venv's interpreter as a private kernel so nbconvert executes each
# notebook with THIS venv (which has statapi installed), not a global Python. A
# unique name avoids colliding with any system "python3" kernelspec.
"$VENV/bin/python" -m ipykernel install --sys-prefix --name statapi-nb --display-name "statapi (verify)"

# Notebooks live one level down, in audience folders (betting/, daily-fantasy/,
# prediction-markets/, sports-quants/). The glob only matches that layer, so the
# disposable dot-dirs (.venv, .ci-venv) are skipped.
for nb in */*.ipynb; do
  echo "· executing $nb"
  # `python -m nbconvert` (not `-m jupyter nbconvert`, which dispatches to the
  # first jupyter-nbconvert on PATH — possibly outside this venv).
  "$VENV/bin/python" -m nbconvert \
    --to notebook --execute --inplace \
    --ExecutePreprocessor.timeout=300 \
    --ExecutePreprocessor.kernel_name=statapi-nb \
    "$nb"
done

echo "verify: all notebooks executed with committed outputs"
