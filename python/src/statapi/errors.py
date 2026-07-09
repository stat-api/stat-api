"""Typed exceptions for the stat-api Python SDK.

Every non-2xx HTTP response raises a :class:`StatApiError` (or a subclass). The
raw server envelope — ``{"error": <code>, "message": <prose>, "status": <int>}``
— is preserved on ``.body`` so callers can introspect the machine-readable
``error`` code the API returns.

Hand-written core (not generated): the ``_`` prefix marks generated files in
this repo, so this file deliberately has none.
"""

from __future__ import annotations

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


_STATUS_EXCEPTIONS: dict[int, type[StatApiError]] = {
    400: ValidationError,
    401: AuthenticationError,
    402: PlanRequiredError,
    404: NotFoundError,
    429: QuotaExceededError,
}


def error_for_status(status: int, body: Any, path: str) -> StatApiError:
    """Map an HTTP status onto the matching exception class."""
    cls = _STATUS_EXCEPTIONS.get(status, StatApiError)
    return cls(status, body, path)


def _message_of(body: Any) -> str | None:
    if isinstance(body, dict):
        message = body.get("message")
        if isinstance(message, str):
            return message
    return None
