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
from .dfs_lineups import (
    BuiltLineupModel,
    ContestPayoutMetrics,
    ContestSummaryView,
    CustomLineupInput,
    DiversityFrontierPoint,
    GroupBlock,
    LateSwapRequest,
    LateSwapResponse,
    LiveGameStateInput,
    OptimizerLineupsRequest,
    OptimizerLineupsResponse,
    OptimizerPlayerControls,
    PlayerControls,
    PortfolioSelectRequest,
    PortfolioSelectResponse,
    ScenarioFilterRule,
    SimJobPollResult,
    SimJobStatusResponse,
    SimLineupsRequest,
    SimLineupsResponse,
    StackBlock,
)
from .errors import (
    AuthenticationError,
    NotFoundError,
    NotReadyError,
    PlanRequiredError,
    QuotaExceededError,
    StatApiError,
    ValidationError,
)
from .page import Page, Quota
from .sim import (
    NBAMatchupSummary,
    NBASimInclude,
    NBASimPlayerOverride,
    NBASimPropInput,
    NBASimPublicRunRequest,
    NBASimPublicRunResponse,
    NBASimTeamOverride,
    NBASimTracedGame,
    NBASimWeeksResponse,
    NFLMatchupSummary,
    NFLSimInclude,
    NFLSimPlayerOverride,
    NFLSimPublicRunRequest,
    NFLSimPublicRunResponse,
    NFLSimTeamOverride,
    NFLSimTracedGame,
    NFLSimWeatherOverride,
    NFLWeeksResponse,
    SimNBAResource,
    SimNFLResource,
)

__all__ = [
    "StatApi",
    "SimNFLResource",
    "SimNBAResource",
    "OptimizerLineupsRequest",
    "OptimizerLineupsResponse",
    "SimLineupsRequest",
    "SimLineupsResponse",
    "PortfolioSelectRequest",
    "PortfolioSelectResponse",
    "LateSwapRequest",
    "LateSwapResponse",
    "SimJobStatusResponse",
    "SimJobPollResult",
    "DiversityFrontierPoint",
    "ScenarioFilterRule",
    "CustomLineupInput",
    "LiveGameStateInput",
    "StackBlock",
    "GroupBlock",
    "PlayerControls",
    "OptimizerPlayerControls",
    "ContestPayoutMetrics",
    "ContestSummaryView",
    "BuiltLineupModel",
    "NFLWeeksResponse",
    "NFLMatchupSummary",
    "NFLSimInclude",
    "NFLSimTeamOverride",
    "NFLSimPlayerOverride",
    "NFLSimWeatherOverride",
    "NFLSimPublicRunRequest",
    "NFLSimPublicRunResponse",
    "NFLSimTracedGame",
    "NBASimWeeksResponse",
    "NBAMatchupSummary",
    "NBASimInclude",
    "NBASimTeamOverride",
    "NBASimPlayerOverride",
    "NBASimPropInput",
    "NBASimPublicRunRequest",
    "NBASimPublicRunResponse",
    "NBASimTracedGame",
    "StatApiError",
    "AuthenticationError",
    "PlanRequiredError",
    "NotFoundError",
    "NotReadyError",
    "ValidationError",
    "QuotaExceededError",
    "Page",
    "Quota",
    "__version__",
]
