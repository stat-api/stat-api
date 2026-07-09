#!/usr/bin/env bash
# Syntax-only CI for the analytics notebooks — NO secrets, NO live API calls.
#
# For each notebook: convert to a plain script, byte-compile it (py_compile),
# and lint it (ruff). This is what public CI runs; it never needs STAT_API_KEY.
#
# Usage:  ./ci-lint.sh
set -euo pipefail
cd "$(dirname "$0")"

VENV=".ci-venv"
if [ ! -x "$VENV/bin/python" ]; then
  echo "· creating $VENV (nbconvert + ruff)"
  python3 -m venv "$VENV"
  "$VENV/bin/python" -m pip install --quiet --upgrade pip
  "$VENV/bin/python" -m pip install --quiet nbconvert ruff
fi
PY="$VENV/bin/python"
RUFF="$VENV/bin/ruff"

WORK="$(mktemp -d)"
trap 'rm -rf "$WORK"' EXIT

fail=0
# Notebooks live one level down, in audience folders (betting/, daily-fantasy/,
# prediction-markets/, sports-quants/); the glob skips the disposable dot-dirs.
for nb in */*.ipynb; do
  script="$WORK/$(basename "${nb%.ipynb}").py"
  echo "· $nb"
  "$PY" -m nbconvert --to script --stdout "$nb" > "$script" 2>/dev/null
  "$PY" -m py_compile "$script"                     || { echo "  py_compile FAILED"; fail=1; }
  "$RUFF" check --select E9,F --quiet "$script"     || { echo "  ruff FAILED"; fail=1; }
done

if [ "$fail" -ne 0 ]; then
  echo "ci-lint: FAILED"
  exit 1
fi
echo "ci-lint: all notebooks compile and lint clean"
