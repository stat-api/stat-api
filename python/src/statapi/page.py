"""Pagination and quota value types for the stat-api Python SDK.

Hand-written core (not generated).
"""

from __future__ import annotations

from dataclasses import dataclass
from typing import Generic, TypeVar

RowT = TypeVar("RowT")


@dataclass(frozen=True)
class Quota:
    """Snapshot of the caller's monthly record quota.

    Parsed from the ``X-Quota-*`` response headers stamped on every API
    response. Any component the server omits is ``None``.
    """

    limit: int | None
    used: int | None
    remaining: int | None


@dataclass(frozen=True)
class Page(Generic[RowT]):
    """One page of list results plus the keyset cursor to the next page.

    ``next_from_id`` is ``None`` on the final page (and on endpoints that do
    not paginate). A resource's ``iter()`` walks these cursors for you.
    """

    rows: list[RowT]
    limit: int | None
    next_from_id: int | None
    quota: Quota | None
