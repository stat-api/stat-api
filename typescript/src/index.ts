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

export { StatApi } from './generated/_index.js';
export type * from './generated/_index.js';

export {
  StatApiError,
  AuthenticationError,
  PlanRequiredError,
  NotFoundError,
  ValidationError,
  QuotaExceededError,
} from './core/error.js';

export type { StatApiOptions, Quota } from './core/client.js';
