// =============================================================================
// client — HTTP runtime for the stat-api SDK
// =============================================================================
//
// Hand-written, zero-dependency core the generated per-table resources call.
// The generated barrel (`../generated/_index`) extends `StatApiBase`; each
// generated resource issues requests through `fetchJson`.
//
// Contract (frozen — the runtime package literals below are not derived from
// the codegen naming authority; they are the published API's stable facts):
//   - base URL          https://api.stat-api.com   (override: STAT_API_BASE_URL)
//   - auth              Authorization: Bearer <STAT_API_KEY>
//   - list envelope     { "<table>": [...], limit, next_from_id }
//   - quota headers     X-Quota-Limit / X-Quota-Used / X-Quota-Remaining
//   - typed errors      401/402/404/400/429 → error.ts subclasses
//
// Retries: GETs retry twice on network/timeout failures and 5xx, with jittered
// 250ms then 1s backoff. 4xx are NEVER retried — including 429, which is a
// monthly quota and cannot succeed on retry, so it fails loud.
// =============================================================================

import { errorForStatus, StatApiError } from './error.js';

/** Public constructor options. All optional — apiKey and baseUrl fall back to
 *  the environment, then to the frozen defaults. */
export interface StatApiOptions {
  /** API key. Defaults to `process.env.STAT_API_KEY`. */
  apiKey?: string;
  /** API base URL, no trailing slash. Defaults to `process.env.STAT_API_BASE_URL`
   *  then `https://api.stat-api.com`. */
  baseUrl?: string;
  /** Per-request timeout in milliseconds. Defaults to 30000. */
  timeoutMs?: number;
  /** Fetch implementation override (inject a mock in tests). Defaults to the
   *  global `fetch`. */
  fetch?: typeof globalThis.fetch;
}

/** Options after defaulting/validation — what resources actually run against. */
export interface ResolvedStatApiOptions {
  readonly apiKey: string;
  readonly baseUrl: string;
  readonly timeoutMs: number;
  readonly fetch: typeof globalThis.fetch;
}

/** Per-period record quota, parsed from the `X-Quota-*` response headers. A
 *  field is null when its header was absent or non-numeric. */
export interface Quota {
  readonly limit: number | null;
  readonly used: number | null;
  readonly remaining: number | null;
}

/** Internal fetchJson result: the parsed body plus the request's quota snapshot
 *  (null when the response carried no quota headers). Resources unwrap `.data`. */
export interface FetchResult<T> {
  readonly data: T;
  readonly quota: Quota | null;
}

const DEFAULT_BASE_URL = 'https://api.stat-api.com';
const DEFAULT_TIMEOUT_MS = 30_000;
const GET_RETRIES = 2; // total GET attempts = 1 + GET_RETRIES

/**
 * Resolve options once, failing loud if no API key is available. The generated
 * `StatApi` barrel extends this and passes `this.opts` to every resource.
 */
export class StatApiBase {
  protected readonly opts: ResolvedStatApiOptions;

  constructor(options: StatApiOptions = {}) {
    const apiKey = options.apiKey ?? envVar('STAT_API_KEY');
    if (!apiKey) {
      throw new Error(
        'stat-api: no API key. Pass `new StatApi({ apiKey })` or set the ' +
          'STAT_API_KEY environment variable. Get a key at https://stat-api.com.',
      );
    }
    const baseUrl = (
      options.baseUrl ??
      envVar('STAT_API_BASE_URL') ??
      DEFAULT_BASE_URL
    ).replace(/\/+$/, '');
    this.opts = {
      apiKey,
      baseUrl,
      timeoutMs: options.timeoutMs ?? DEFAULT_TIMEOUT_MS,
      fetch: options.fetch ?? globalThis.fetch,
    };
  }
}

/**
 * Issue a JSON request and return the parsed body plus quota. Throws a typed
 * `StatApiError` subclass on non-2xx. Retries GETs on transient failures.
 */
export async function fetchJson<T>(
  opts: ResolvedStatApiOptions,
  path: string,
  init: RequestInit = {},
): Promise<FetchResult<T>> {
  const url = `${opts.baseUrl}${path}`;
  const method = (init.method ?? 'GET').toUpperCase();
  const maxAttempts = method === 'GET' ? 1 + GET_RETRIES : 1;
  let lastError: StatApiError | null = null;

  for (let attempt = 0; attempt < maxAttempts; attempt++) {
    if (attempt > 0) await sleep(backoffMs(attempt));

    let resp: Response;
    try {
      resp = await withTimeout(opts, url, init, method);
    } catch (err) {
      // Network failure or timeout (AbortError). Retry GETs; otherwise fail.
      lastError = new StatApiError(
        0,
        null,
        path,
        `stat-api network error on ${path}: ${errMessage(err)}`,
      );
      if (attempt < maxAttempts - 1) continue;
      throw lastError;
    }

    const text = await resp.text();
    const body: unknown = text.length === 0 ? null : safeJsonParse(text);
    const quota = parseQuota(resp.headers);

    if (resp.ok) return { data: body as T, quota };

    // 5xx is retryable on a GET; every 4xx (429 included) fails immediately.
    if (resp.status >= 500 && attempt < maxAttempts - 1) {
      lastError = new StatApiError(resp.status, body, path);
      continue;
    }
    throw errorForStatus(resp.status, body, path, quota);
  }

  // Unreachable in practice: the loop either returns or throws. Guard anyway.
  throw lastError ?? new StatApiError(0, null, path, `stat-api request failed on ${path}`);
}

async function withTimeout(
  opts: ResolvedStatApiOptions,
  url: string,
  init: RequestInit,
  method: string,
): Promise<Response> {
  const controller = new AbortController();
  const timer = setTimeout(() => controller.abort(), opts.timeoutMs);
  try {
    const headers = new Headers(init.headers);
    headers.set('Authorization', `Bearer ${opts.apiKey}`);
    if (init.body && !headers.has('Content-Type')) {
      headers.set('Content-Type', 'application/json');
    }
    return await opts.fetch(url, { ...init, method, headers, signal: controller.signal });
  } finally {
    clearTimeout(timer);
  }
}

/** Parse the `X-Quota-*` headers. Returns null when none are present. */
export function parseQuota(headers: Headers): Quota | null {
  const limit = headers.get('X-Quota-Limit');
  const used = headers.get('X-Quota-Used');
  const remaining = headers.get('X-Quota-Remaining');
  if (limit === null && used === null && remaining === null) return null;
  return {
    limit: headerInt(limit),
    used: headerInt(used),
    remaining: headerInt(remaining),
  };
}

/**
 * Serialize a flat params object into a URL query string.
 * - undefined / null values are omitted.
 * - Numbers, booleans, strings are stringified via `String()`.
 * - Returns '' when nothing survives, so callers concatenate unconditionally.
 */
export function toQueryString(params: Record<string, unknown> | undefined): string {
  if (!params) return '';
  const search = new URLSearchParams();
  for (const [key, value] of Object.entries(params)) {
    if (value === undefined || value === null) continue;
    search.append(key, String(value));
  }
  const s = search.toString();
  return s.length === 0 ? '' : `?${s}`;
}

// ---------- internals ----------

function backoffMs(attempt: number): number {
  const base = attempt === 1 ? 250 : 1000;
  return base + Math.floor(Math.random() * base * 0.25);
}

function sleep(ms: number): Promise<void> {
  return new Promise(resolve => setTimeout(resolve, ms));
}

function safeJsonParse(text: string): unknown {
  try {
    return JSON.parse(text);
  } catch {
    return text;
  }
}

function headerInt(value: string | null): number | null {
  if (value === null) return null;
  const n = Number(value);
  return Number.isFinite(n) ? n : null;
}

function errMessage(err: unknown): string {
  return err instanceof Error ? err.message : String(err);
}

/** Read an env var without assuming a Node global type is in scope (the SDK
 *  runs under Node, Bun, Deno, and edge runtimes). */
function envVar(name: string): string | undefined {
  const env = (globalThis as { process?: { env?: Record<string, string | undefined> } }).process
    ?.env;
  return env?.[name];
}
