// =============================================================================
// error — typed error hierarchy for the stat-api SDK
// =============================================================================
//
// The SDK maps HTTP status codes to error subclasses so callers can `catch`
// the specific failure they care about instead of switching on a status number.
// Every error carries the raw server `body` verbatim (the public API returns a
// JSON envelope like `{ "error": "...", "message": "..." }`) plus the request
// `path`, so the shape the server sent is always inspectable.
//
// fetchJson (core/client.ts) constructs these via `errorForStatus`. This module
// is runtime-import-free apart from the `Quota` TYPE, which is erased at compile
// time — so there is no runtime import cycle with core/client.ts.
// =============================================================================

import type { Quota } from './client.js';

/** Base class for every error the SDK throws on a non-2xx response (and for
 *  network/timeout failures, which use status 0). */
export class StatApiError extends Error {
  readonly status: number;
  /** Parsed JSON body the server returned (or the raw text if not JSON, or
   *  null on an empty body / network failure). */
  readonly body: unknown;
  /** Request path that produced the error, e.g. `/api/v1/nfl/teams/1`. */
  readonly path: string;

  constructor(status: number, body: unknown, path: string, message?: string) {
    super(message ?? `stat-api request failed: ${status} on ${path}`);
    this.name = 'StatApiError';
    this.status = status;
    this.body = body;
    this.path = path;
    // Restore the prototype chain so `instanceof` works across the ES5 target
    // downlevel that dual CJS/ESM builds emit.
    Object.setPrototypeOf(this, new.target.prototype);
  }
}

/** 401 — the API key is missing, malformed, or revoked. */
export class AuthenticationError extends StatApiError {
  constructor(body: unknown, path: string) {
    super(
      401,
      body,
      path,
      `stat-api authentication failed (401) on ${path} — check STAT_API_KEY`,
    );
    this.name = 'AuthenticationError';
  }
}

/** 402 — the endpoint or row set requires a paid plan the key does not hold. */
export class PlanRequiredError extends StatApiError {
  constructor(body: unknown, path: string) {
    super(
      402,
      body,
      path,
      `stat-api plan required (402) on ${path} — upgrade at https://stat-api.com`,
    );
    this.name = 'PlanRequiredError';
  }
}

/** 404 — no row matched the requested id. */
export class NotFoundError extends StatApiError {
  constructor(body: unknown, path: string) {
    super(404, body, path, `stat-api resource not found (404) on ${path}`);
    this.name = 'NotFoundError';
  }
}

/** 400 — the request was rejected. Covers the required-filter-set envelope
 *  (`{ error: "missing_required_filters", accepted: [...] }`); read `body`. */
export class ValidationError extends StatApiError {
  constructor(body: unknown, path: string) {
    super(400, body, path, `stat-api rejected the request (400) on ${path}`);
    this.name = 'ValidationError';
  }
}

/** 429 — the monthly record quota is exhausted. NEVER retried (a retry cannot
 *  succeed until the quota resets), so it always surfaces to the caller. */
export class QuotaExceededError extends StatApiError {
  /** Quota parsed from the `X-Quota-*` response headers, when present. */
  readonly quota: Quota | null;
  /** Monthly record limit for the plan (from the body, else the header). */
  readonly limit: number | null;
  /** Records used this period (from the body, else the header). */
  readonly used: number | null;
  /** ISO-8601 instant the quota window resets, when the server reports it. */
  readonly resetsAt: string | null;
  /** URL to upgrade the plan, when the server reports it. */
  readonly upgradeUrl: string | null;

  constructor(body: unknown, path: string, quota: Quota | null) {
    super(429, body, path, `stat-api monthly quota exceeded (429) on ${path}`);
    this.name = 'QuotaExceededError';
    this.quota = quota;
    const b = (body && typeof body === 'object' ? body : {}) as Record<string, unknown>;
    this.limit = numberOrNull(b.limit) ?? quota?.limit ?? null;
    this.used = numberOrNull(b.used) ?? quota?.used ?? null;
    this.resetsAt = stringOrNull(b.resets_at);
    this.upgradeUrl = stringOrNull(b.upgrade_url);
  }
}

/** Map an HTTP status to its error subclass. Unmapped statuses (403, 5xx that
 *  survived retries, …) fall back to the base `StatApiError`. */
export function errorForStatus(
  status: number,
  body: unknown,
  path: string,
  quota: Quota | null,
): StatApiError {
  switch (status) {
    case 400:
      return new ValidationError(body, path);
    case 401:
      return new AuthenticationError(body, path);
    case 402:
      return new PlanRequiredError(body, path);
    case 404:
      return new NotFoundError(body, path);
    case 429:
      return new QuotaExceededError(body, path, quota);
    default:
      return new StatApiError(status, body, path);
  }
}

function numberOrNull(value: unknown): number | null {
  if (typeof value === 'number' && Number.isFinite(value)) return value;
  if (typeof value === 'string' && value.trim() !== '') {
    const n = Number(value);
    if (Number.isFinite(n)) return n;
  }
  return null;
}

function stringOrNull(value: unknown): string | null {
  return typeof value === 'string' && value.length > 0 ? value : null;
}
