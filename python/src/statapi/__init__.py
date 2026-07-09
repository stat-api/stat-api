"""stat-api — the official Python client for the stat-api sports data API.

    from statapi import StatApi

    api = StatApi()                      # reads STAT_API_KEY from the env
    page = api.nba.players.list(limit=25)
    for player in api.nba.players.iter(team_id=1):
        print(player["full_name"])

The ``StatApi`` entry point and the per-league resources it composes are
generated from the API schema; the transport, error, and pagination types below
are the hand-written core. See https://stat-api.com/docs for the full reference.
"""

from ._client import StatApi
from ._version import __version__
from .errors import (
    AuthenticationError,
    NotFoundError,
    PlanRequiredError,
    QuotaExceededError,
    StatApiError,
    ValidationError,
)
from .page import Page, Quota

__all__ = [
    "StatApi",
    "StatApiError",
    "AuthenticationError",
    "PlanRequiredError",
    "NotFoundError",
    "ValidationError",
    "QuotaExceededError",
    "Page",
    "Quota",
    "__version__",
]
