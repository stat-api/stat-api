// The tiered poller — the only thing that spends API quota.
//
// Four tiers on independent cadences feed one in-memory snapshot:
//
//   scoreboard  60s   games?day per in-season league  (offseason → skip)
//   lines      120s   game_lines per in-window game   (→ sportsbook pulse)
//   box        180s   batter stats per in-window MLB game
//   kalshi     900s   kalshi markets per in-window NBA game
//
// Two mechanical guards keep the burn bounded:
//   - window gating: the per-game tiers touch only games inside their live
//     window (T-lead → final); far-future and finished games cost nothing.
//   - quota self-throttle: when X-Quota-Remaining falls below the floor, every
//     cadence is halved (interval doubled) and the drop is logged loudly.
//
// Each tier updates its slice of per-game detail and recomposes the affected
// league into the store, which emits an SSE patch only if something changed.
import { QuotaExceededError } from '@stat-api/client';

import type {
  BookLines,
  BoxLine,
  GameCard,
  GameDetail,
  League,
  LeagueSnapshot,
  MarketPulsePoint,
  Quota,
} from './contract.ts';
import type { Config } from './config.ts';
import type { StatApiClient } from './sdk.ts';
import type { SnapshotStore } from './snapshot.ts';
import { isInSeason, isInWindow, todayEtYyyymmdd } from './season.ts';
import {
  fetchLastFinalSlate,
  fetchScoreboard,
  fetchTeamMap,
  type Scoreboard,
  type ScoredGame,
  type TeamMap,
} from './sources/scoreboard.ts';
import { fetchLines, fetchOperatorMap, type OperatorMap } from './sources/lines.ts';
import { fetchBox, RosterCache } from './sources/boxscore.ts';
import { sportsbookImpliedPulse } from './sources/wp-source.ts';
import { kalshiSource } from './sources/kalshi.ts';

export interface PollerDeps {
  readonly api: StatApiClient;
  /** Latest quota seen by the SDK fetch wrapper; drives the self-throttle. */
  readonly quota: () => Quota | null;
  readonly store: SnapshotStore;
  readonly config: Config;
  /** Injectable clock — tests pin "now" to a known day/season. */
  readonly now?: () => Date;
  readonly logger?: (message: string) => void;
}

/** One league's latest scoreboard slice. */
interface BoardEntry {
  readonly status: LeagueSnapshot['status'];
  readonly games: ScoredGame[];
  readonly asOf: string;
}

/** Per-game detail, split by tier so each cadence writes only its own part. */
interface DetailState {
  lines: BookLines[];
  box: BoxLine[];
  sportsbookPulse: MarketPulsePoint[];
  kalshiPulse: MarketPulsePoint[];
}

interface Tier {
  readonly baseMs: number;
  readonly run: (now: Date) => Promise<void>;
  handle: ReturnType<typeof setTimeout> | null;
}

export class Poller {
  private readonly api: StatApiClient;
  private readonly quota: () => Quota | null;
  private readonly store: SnapshotStore;
  private readonly config: Config;
  private readonly now: () => Date;
  private readonly log: (message: string) => void;

  private readonly board = new Map<League, BoardEntry>();
  private readonly details = new Map<string, DetailState>();
  private readonly teamMaps = new Map<League, TeamMap>();
  /** Last-final slate per offseason league, resolved once and reused — the
   *  finals don't change while a league is out of season. */
  private readonly offseasonSlates = new Map<League, Scoreboard>();
  private readonly rosters: RosterCache;
  private operators: OperatorMap | null = null;

  private running = false;
  private throttled = false;
  private tiers: Tier[] = [];

  constructor(deps: PollerDeps) {
    this.api = deps.api;
    this.quota = deps.quota;
    this.store = deps.store;
    this.config = deps.config;
    this.now = deps.now ?? (() => new Date());
    this.log = deps.logger ?? ((m: string) => console.log(`[poller] ${m}`));
    this.rosters = new RosterCache(this.api, this.config.limits.box);
  }

  /** Run one full pass of every tier, then schedule the recurring tiers. The
   *  initial pass means the first `/api/snapshot` already carries live data. */
  async start(): Promise<void> {
    this.running = true;
    await this.pollOnce();
    const c = this.config.cadences;
    this.tiers = [
      { baseMs: c.scoreboardMs, run: n => this.runScoreboardCycle(n), handle: null },
      { baseMs: c.linesMs, run: n => this.runLinesCycle(n), handle: null },
      { baseMs: c.boxMs, run: n => this.runBoxCycle(n), handle: null },
      { baseMs: c.kalshiMs, run: n => this.runKalshiCycle(n), handle: null },
    ];
    for (const tier of this.tiers) this.scheduleNext(tier);
  }

  /** Run every tier exactly once, in dependency order (scoreboard first so the
   *  detail tiers see the current slate). Used by `start()` and the live smoke. */
  async pollOnce(): Promise<void> {
    const now = this.now();
    await this.runScoreboardCycle(now);
    await this.runLinesCycle(now);
    await this.runBoxCycle(now);
    await this.runKalshiCycle(now);
  }

  stop(): void {
    this.running = false;
    for (const tier of this.tiers) {
      if (tier.handle !== null) clearTimeout(tier.handle);
      tier.handle = null;
    }
  }

  /** Cadence multiplier: 2 (halved cadence) while quota is below the floor,
   *  1 otherwise. Public so the budget test can assert the throttle directly. */
  throttleMultiplier(): number {
    const q = this.quota();
    if (q !== null && q.remaining < this.config.quotaFloor) {
      if (!this.throttled) {
        this.throttled = true;
        this.log(
          `QUOTA LOW: remaining=${q.remaining} < floor=${this.config.quotaFloor} — halving all cadences`,
        );
      }
      return 2;
    }
    if (this.throttled) {
      this.throttled = false;
      this.log('quota recovered — restoring base cadences');
    }
    return 1;
  }

  // ---------- tiers ----------

  async runScoreboardCycle(now: Date): Promise<void> {
    for (const league of this.config.leagues) {
      if (!isInSeason(league, now)) {
        await this.updateOffseasonLeague(league, now);
        continue;
      }
      try {
        const teams = await this.ensureTeamMap(league);
        const day = this.config.dayOverride ?? todayEtYyyymmdd(now);
        const board = await fetchScoreboard(
          this.api,
          league,
          day,
          teams,
          this.config.limits.scoreboard,
        );
        this.board.set(league, { status: 'in-season', games: board.games, asOf: board.asOf });
        this.recomposeLeague(league);
      } catch (err) {
        this.handleError('scoreboard', league, err);
      }
    }
    this.publishQuota();
    // Refresh the footer quota + per-league asOf on connected clients (patches
    // carry only games). Payload is small; once per scoreboard cycle is enough.
    this.store.broadcastSnapshot();
  }

  /** Offseason league: show its most recent completed slate as final cards,
   *  resolved once and cached (the finals don't change out of season). */
  private async updateOffseasonLeague(league: League, now: Date): Promise<void> {
    if (!this.offseasonSlates.has(league)) {
      try {
        const teams = await this.ensureTeamMap(league);
        const slate = await fetchLastFinalSlate(
          this.api,
          league,
          teams,
          this.config.limits.scoreboard,
          now,
        );
        this.offseasonSlates.set(league, slate);
      } catch (err) {
        this.handleError('offseason', league, err);
        this.offseasonSlates.set(league, { games: [], asOf: new Date().toISOString() });
      }
    }
    const slate = this.offseasonSlates.get(league) as Scoreboard;
    this.board.set(league, { status: 'offseason', games: slate.games, asOf: slate.asOf });
    this.recomposeLeague(league);
  }

  async runLinesCycle(now: Date): Promise<void> {
    for (const league of this.config.leagues) {
      const games = this.inWindowGames(league, now);
      if (games.length === 0) continue;
      const operators = await this.ensureOperatorMap();
      let changed = false;
      for (const game of games) {
        try {
          const books = await fetchLines(
            this.api,
            league,
            game.gameId,
            operators,
            this.config.limits.lines,
          );
          const detail = this.detailState(game.card.id);
          detail.lines = books;
          detail.sportsbookPulse = sportsbookImpliedPulse(books);
          changed = true;
        } catch (err) {
          if (this.handleError('lines', league, err, game.card.id)) return;
        }
      }
      if (changed) this.recomposeLeague(league);
    }
    this.publishQuota();
  }

  async runBoxCycle(now: Date): Promise<void> {
    // The compact contract box is MLB-batting-shaped, so only MLB is polled.
    const league: League = 'mlb';
    const games = this.inWindowGames(league, now);
    if (games.length > 0) {
      const teams = await this.ensureTeamMap(league);
      let changed = false;
      for (const game of games) {
        try {
          const box = await fetchBox(
            this.api,
            game.gameId,
            teams,
            this.rosters,
            this.config.limits.box,
          );
          this.detailState(game.card.id).box = box;
          changed = true;
        } catch (err) {
          if (this.handleError('box', league, err, game.card.id)) break;
        }
      }
      if (changed) this.recomposeLeague(league);
    }
    this.publishQuota();
  }

  async runKalshiCycle(now: Date): Promise<void> {
    const league: League = 'nba';
    const games = this.inWindowGames(league, now);
    if (games.length > 0) {
      const source = kalshiSource();
      let changed = false;
      for (const game of games) {
        try {
          const pulse = await source.pulse({
            gameId: game.gameId,
            competitionId: game.gameId,
            homeTeamName: game.card.home.name,
            awayTeamName: game.card.away.name,
            books: this.detailState(game.card.id).lines,
            api: this.api,
            now,
          });
          this.detailState(game.card.id).kalshiPulse = pulse;
          changed = true;
        } catch (err) {
          if (this.handleError('kalshi', league, err, game.card.id)) break;
        }
      }
      if (changed) this.recomposeLeague(league);
    }
    this.publishQuota();
  }

  // ---------- helpers ----------

  private async ensureTeamMap(league: League): Promise<TeamMap> {
    const cached = this.teamMaps.get(league);
    if (cached) return cached;
    const map = await fetchTeamMap(this.api, league, this.config.limits.teams);
    this.teamMaps.set(league, map);
    return map;
  }

  private async ensureOperatorMap(): Promise<OperatorMap> {
    if (this.operators) return this.operators;
    this.operators = await fetchOperatorMap(this.api, this.config.limits.scoreboard);
    return this.operators;
  }

  /** In-season league games currently inside their detail-polling window. */
  private inWindowGames(league: League, now: Date): ScoredGame[] {
    const board = this.board.get(league);
    if (!board || board.status === 'offseason') return [];
    return board.games.filter(g =>
      isInWindow(
        new Date(g.card.startTime),
        g.card.status,
        now,
        this.config.windowLeadMinutes,
      ),
    );
  }

  private detailState(id: string): DetailState {
    let detail = this.details.get(id);
    if (!detail) {
      detail = { lines: [], box: [], sportsbookPulse: [], kalshiPulse: [] };
      this.details.set(id, detail);
    }
    return detail;
  }

  /** Rebuild a league's cards from its board + any captured detail, and hand it
   *  to the store (which emits a patch only if it changed). */
  private recomposeLeague(league: League): void {
    const board = this.board.get(league);
    if (!board) return;
    const games: GameCard[] = board.games.map(g => {
      const detail = this.details.get(g.card.id);
      if (!detail || isEmptyDetail(detail)) return g.card;
      return { ...g.card, detail: toGameDetail(detail) };
    });
    this.store.updateLeague(league, board.status, games, board.asOf);
  }

  private publishQuota(): void {
    this.store.setQuota(this.quota());
  }

  /** Log an error loudly and continue. Returns true when the caller should abort
   *  the rest of its cycle (quota exhausted — no point burning more calls). */
  private handleError(tier: string, league: League, err: unknown, gameId?: string): boolean {
    const where = gameId ? `${tier}/${league}/${gameId}` : `${tier}/${league}`;
    if (err instanceof QuotaExceededError) {
      this.log(`QUOTA EXHAUSTED at ${where}: ${err.message} — aborting cycle`);
      return true;
    }
    this.log(`error at ${where}: ${err instanceof Error ? err.message : String(err)}`);
    return false;
  }

  private scheduleNext(tier: Tier): void {
    const interval = tier.baseMs * this.throttleMultiplier();
    tier.handle = setTimeout(() => {
      void this.tick(tier);
    }, interval);
  }

  private async tick(tier: Tier): Promise<void> {
    if (!this.running) return;
    try {
      await tier.run(this.now());
    } catch (err) {
      this.log(`tier crashed: ${err instanceof Error ? err.message : String(err)}`);
    }
    if (this.running) this.scheduleNext(tier);
  }
}

function isEmptyDetail(d: DetailState): boolean {
  return (
    d.lines.length === 0 &&
    d.box.length === 0 &&
    d.sportsbookPulse.length === 0 &&
    d.kalshiPulse.length === 0
  );
}

function toGameDetail(d: DetailState): GameDetail {
  return {
    lines: d.lines,
    box: d.box,
    pulse: [...d.sportsbookPulse, ...d.kalshiPulse].sort((a, b) => a.t.localeCompare(b.t)),
  };
}
