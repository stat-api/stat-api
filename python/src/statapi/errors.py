"""Typed exceptions for the stat-api Python SDK.

Every non-2xx HTTP response raises a :class:`StatApiError` (or a subclass), and
so does a ``202 Accepted`` (:class:`NotReadyError`): a 202 holds no data. The
raw server envelope — ``{"error": <code>, "message": <prose>, "status": <int>}``
— is preserved on ``.body`` so callers can introspect the machine-readable
``error`` code the API returns.

Hand-written core (not generated): the ``_`` prefix marks generated files in
this repo, so this file deliberately has none.
"""

from __future__ import annotations

import math
from typing import Any


class StatApiError(Exception):
    """Base class for every error surfaced by the SDK.

    ``status`` is the HTTP status (``0`` for a transport-level failure with no
    response), ``body`` is the parsed JSON envelope (or raw text on a non-JSON
    body), and ``path`` is the request path that produced it.
    """

    def __init__(self, status: int, body: Any, path: str) -> None:
        self.status = status
        self.body = body
        self.path = path
        detail = _message_of(body) or f"HTTP {status}"
        super().__init__(f"stat-api {status} on {path}: {detail}")


class AuthenticationError(StatApiError):
    """401 — the API key is missing, malformed, or rejected."""


class PlanRequiredError(StatApiError):
    """402 — the endpoint requires a paid plan the caller does not hold."""


class ValidationError(StatApiError):
    """400 — malformed request or an unsatisfied required-filter set."""


class NotFoundError(StatApiError):
    """404 — no resource exists at the requested path or id."""


class QuotaExceededError(StatApiError):
    """429 — the caller's monthly record quota is exhausted.

    Never retried: the quota is a monthly budget, so retrying only burns
    latency. Inspect :attr:`resets_at` / :attr:`upgrade_url` to recover.
    """

    def __init__(self, status: int, body: Any, path: str) -> None:
        super().__init__(status, body, path)
        fields = body if isinstance(body, dict) else {}
        self.limit = fields.get("limit")
        self.used = fields.get("used")
        self.resets_at = fields.get("resets_at")
        self.upgrade_url = fields.get("upgrade_url")


class NotReadyError(StatApiError):
    """202 — accepted, but the data is not ready yet.

    Raised when a paid caller asks for something the API has queued to load (for
    example the lineups of a contest whose lineup file is not loaded yet). A 202
    holds no data, so the SDK raises instead of handing the body back as the
    route's data. Never retried inside the SDK: a 202 is not a transient
    failure, the caller decides when to ask again. ``body`` is the parsed 202
    body verbatim.

    ``retry_after_seconds`` is the body's ``retry_after_seconds``, else the
    ``Retry-After`` header (seconds form), else ``None``. ``estimated_ready_at``
    is an ISO-8601 string, or ``None`` when the server gives none. ``state`` is
    the body's ``status`` (``"queued"`` or ``"waiting_for_contest"``). ``message``
    is the server's plain sentence.
    """

    def __init__(
        self, status: int, body: Any, path: str, retry_after: str | None = None
    ) -> None:
        super().__init__(status, body, path)
        fields = body if isinstance(body, dict) else {}
        self.retry_after_seconds: int | float | None = _seconds_of(
            fields.get("retry_after_seconds")
        )
        if self.retry_after_seconds is None:
            self.retry_after_seconds = _seconds_of(retry_after)
        self.estimated_ready_at: str | None = _text_of(fields.get("estimated_ready_at"))
        self.state: str | None = _text_of(fields.get("status"))
        self.message: str = _message_of(body) or _DEFAULT_NOT_READY_MESSAGE


_STATUS_EXCEPTIONS: dict[int, type[StatApiError]] = {
    400: ValidationError,
    401: AuthenticationError,
    402: PlanRequiredError,
    404: NotFoundError,
    429: QuotaExceededError,
}


def error_for_status(
    status: int, body: Any, path: str, retry_after: str | None = None
) -> StatApiError:
    """Map an HTTP status onto the matching exception class.

    ``retry_after`` is the raw ``Retry-After`` header; only a 202 reads it.
    """
    if status == 202:
        return NotReadyError(status, body, path, retry_after=retry_after)
    cls = _STATUS_EXCEPTIONS.get(status, StatApiError)
    return cls(status, body, path)


_DEFAULT_NOT_READY_MESSAGE = "The data is not ready yet."


def _seconds_of(value: object) -> int | float | None:
    """A non-negative finite number of seconds (or digits-only text), else None."""
    if isinstance(value, bool):
        return None
    if isinstance(value, (int, float)):
        return value if math.isfinite(value) and value >= 0 else None
    if isinstance(value, str):
        text = value.strip()
        if text.isascii() and text.isdigit():
            return int(text)
    return None


def _text_of(value: object) -> str | None:
    return value if isinstance(value, str) and value else None


def _message_of(body: Any) -> str | None:
    if isinstance(body, dict):
        message = body.get("message")
        if isinstance(message, str):
            return message
    return None
