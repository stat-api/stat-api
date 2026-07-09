"""urllib-based HTTP transport for the stat-api Python SDK.

Zero third-party dependencies — stdlib ``urllib.request`` only. Handles auth,
a non-default User-Agent (Cloudflare blocks urllib's default UA — repo
precedent), bounded retries on transient failures, quota-header parsing, and
status-to-exception mapping.

Hand-written core (not generated). The version string it advertises comes from
the generated ``_version`` module so the User-Agent stays in lockstep with
``sdks/VERSION``.
"""

from __future__ import annotations

import json
import os
import random
import time
import urllib.error
import urllib.parse
import urllib.request
from collections.abc import Mapping
from email.message import Message
from typing import Any

from ._version import __version__
from .errors import StatApiError, error_for_status
from .page import Quota

_ENV_API_KEY = "STAT_API_KEY"
_ENV_BASE_URL = "STAT_API_BASE_URL"
_DEFAULT_BASE_URL = "https://api.stat-api.com"
_DEFAULT_TIMEOUT = 30.0
_USER_AGENT_PREFIX = "statapi-python"
# One initial attempt + two retries on connection errors / 5xx.
_MAX_ATTEMPTS = 3
# Base backoff per retry (seconds), jittered. Matches D3: 250ms then 1s.
_BACKOFFS = (0.25, 1.0)


class HttpTransport:
    """Issues authenticated ``GET`` requests against the stat-api surface."""

    def __init__(
        self,
        api_key: str | None = None,
        base_url: str | None = None,
        timeout: float | None = None,
    ) -> None:
        key = api_key if api_key is not None else os.environ.get(_ENV_API_KEY)
        if not key:
            raise ValueError(
                f"stat-api: no API key — pass api_key=... or set {_ENV_API_KEY}"
            )
        self._api_key: str = key
        base = base_url or os.environ.get(_ENV_BASE_URL) or _DEFAULT_BASE_URL
        self._base_url: str = base.rstrip("/")
        self._timeout: float = timeout if timeout is not None else _DEFAULT_TIMEOUT
        self._user_agent: str = f"{_USER_AGENT_PREFIX}/{__version__}"

    def request(
        self, path: str, params: Mapping[str, object] | None = None
    ) -> tuple[Any, Quota | None]:
        """Perform a GET and return ``(parsed_json, quota)``.

        Raises the mapped :class:`StatApiError` subclass on any non-2xx status.
        """
        url = f"{self._base_url}{path}{_encode_params(params)}"
        req = urllib.request.Request(
            url,
            method="GET",
            headers={
                "Authorization": f"Bearer {self._api_key}",
                "User-Agent": self._user_agent,
                "Accept": "application/json",
            },
        )
        return self._send(req, path)

    def _send(self, req: urllib.request.Request, path: str) -> tuple[Any, Quota | None]:
        for attempt in range(_MAX_ATTEMPTS):
            is_last = attempt == _MAX_ATTEMPTS - 1
            try:
                with urllib.request.urlopen(req, timeout=self._timeout) as resp:
                    body = _parse_json(resp.read())
                    return body, _parse_quota(resp.headers)
            except urllib.error.HTTPError as exc:
                body = _parse_json(exc.read())
                # 4xx is never retried (esp. 429 — a monthly budget, not a
                # transient fault). 5xx is retried until attempts run out.
                if exc.code < 500 or is_last:
                    raise error_for_status(exc.code, body, path) from None
            except urllib.error.URLError as exc:
                if is_last:
                    raise StatApiError(
                        0,
                        {"error": "connection_error", "message": str(exc.reason)},
                        path,
                    ) from exc
            _sleep_backoff(attempt)
        raise AssertionError("stat-api: retry loop exhausted without a result")


def _encode_params(params: Mapping[str, object] | None) -> str:
    if not params:
        return ""
    pairs = [(k, _stringify(v)) for k, v in params.items() if v is not None]
    if not pairs:
        return ""
    return "?" + urllib.parse.urlencode(pairs)


def _stringify(value: object) -> str:
    # Booleans go over the wire as lowercase JSON literals (matches the TS SDK's
    # String(value) behavior), not Python's "True"/"False".
    if isinstance(value, bool):
        return "true" if value else "false"
    return str(value)


def _parse_json(raw: bytes) -> Any:
    if not raw:
        return None
    text = raw.decode("utf-8")
    try:
        return json.loads(text)
    except json.JSONDecodeError:
        return text


def _parse_quota(headers: Message) -> Quota | None:
    limit = _int_header(headers, "x-quota-limit")
    used = _int_header(headers, "x-quota-used")
    remaining = _int_header(headers, "x-quota-remaining")
    if limit is None and used is None and remaining is None:
        return None
    return Quota(limit=limit, used=used, remaining=remaining)


def _int_header(headers: Message, name: str) -> int | None:
    raw = headers.get(name)
    if raw is None:
        return None
    try:
        return int(raw)
    except (TypeError, ValueError):
        return None


def _sleep_backoff(attempt: int) -> None:
    base = _BACKOFFS[min(attempt, len(_BACKOFFS) - 1)]
    time.sleep(base * (0.5 + random.random()))
