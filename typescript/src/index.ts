// =============================================================================
// @stat-api/client — public entry point
// =============================================================================
//
// The `StatApi` class and every row / params / response type come from the
// generated barrel (`./generated/_index`, emitted by
// packages/codegen/src/generators/generate-ts-sdk-public.ts). The runtime core
// (HTTP, retries, quota, typed errors) is hand-written under `./core`.
//
//   import { StatApi } from '@stat-api/client';
//   const api = new StatApi(); // reads STAT_API_KEY from the environment
//   const { teams } = await api.nfl.teams.list({ limit: 10 });
// =============================================================================

export type { FileResult, Quota, StatApiOptions } from './core/client.js';
export type {
  BuiltLineupModel,
  ContestPayoutMetrics,
  ContestPayoutTierView,
  ContestSummaryView,
  CustomLineupInput,
  DiversityFrontierPoint,
  DrawSetCounts,
  ExcludedBreakdown,
  GroupBlock,
  LateSwapDiagnostics,
  LateSwapEntryDiff,
  LateSwapPortfolioSummary,
  LateSwapRequest,
  LateSwapResponse,
  LineupOwnershipMetrics,
  LineupRankingView,
  LineupScenarioFit,
  LineupSeatRef,
  LineupStackModel,
  LiveGameStateInput,
  OpponentFieldView,
  OptimizerDiagnostics,
  OptimizerLineupsRequest,
  OptimizerLineupsResponse,
  OptimizerLineupView,
  OptimizerMinExposureShortfallView,
  OptimizerPlayerControls,
  OptimizerPlayerExposureView,
  OptimizerPortfolioSummary,
  OptimizerSeatView,
  PlayerControls,
  PlayerSimRates,
  PortfolioDiversityMetrics,
  PortfolioExposuresBreakdown,
  PortfolioModel,
  PortfolioSelectDiagnostics,
  PortfolioSelectRequest,
  PortfolioSelectResponse,
  QuantileSummary,
  RequestWarning,
  ScenarioFilterRule,
  SimDiagnosticsView,
  SimJobPollResult,
  SimJobStatusResponse,
  SimLineupsRequest,
  SimLineupsResponse,
  SimRequestOptions,
  SimSummaryView,
  SimVersionKeysView,
  SlatePlayerModel,
  SlateView,
  SourceView,
  StackBlock,
} from './core/dfs_lineups.js';
export {
  AuthenticationError,
  NotFoundError,
  NotReadyError,
  PlanRequiredError,
  QuotaExceededError,
  StatApiError,
  ValidationError,
} from './core/error.js';
export { SimNBAResource, SimNFLResource } from './core/sim.js';
export type * from './generated/_index.js';
export { StatApi } from './generated/_index.js';
