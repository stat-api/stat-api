import { describe, expect, test } from 'bun:test';
import { NotReadyError, StatApi } from '../src/index.js';

const SLATE_VIEW = {
  id: 1126128,
  league: 'nfl',
  operator: 'draftkings',
  name: 'Sun 1:00PM Classic',
  salary_cap: 50000,
  slots: ['QB', 'RB', 'RB', 'WR', 'WR', 'WR', 'TE', 'FLEX', 'DST'],
};

const CONTEST_VIEW = {
  contest_id: 91632452,
  name: 'Millionaire',
  archetype: 'gpp_mme',
  field_size: 1000,
  entry_fee_dollars: 20,
  prize_pool_dollars: 18000,
  first_place_prize_dollars: 5000,
  min_cash_dollars: 30,
  cash_line_rank: 200,
  paid_pct: 20,
  rake_pct: 10,
  payout_source: 'contest_payouts',
  payout_tiers: [],
};

describe('dfs.slates optimizer & SIM lineup methods', () => {
  test('optimizerLineups & optimizerLineupsCsv send response_format and parse JSON / CSV', async () => {
    const calls: Array<{ url: string; headers: Headers; body: string }> = [];
    const api = new StatApi({
      apiKey: 'test_key',
      baseUrl: 'https://example.test',
      fetch: (async (input, init) => {
        const headers = new Headers(init?.headers);
        calls.push({ url: String(input), headers, body: String(init?.body ?? '') });
        if (JSON.parse(String(init?.body)).response_format === 'csv') {
          return new Response('QB,RB\n1,2\n', {
            status: 200,
            headers: { 'content-type': 'text/csv' },
          });
        }
        return new Response(
          JSON.stringify({
            slate: SLATE_VIEW,
            source: { name: 'STAT_API_LATEST', version: null, updated_at: null, test_input: false },
            warnings: [],
            portfolio: {
              lineup_count: 1,
              unique_players: 9,
              mean_projected_points: 150,
              mean_salary: 49900,
              mean_pairwise_overlap: 0,
              diversity: {
                structural_overlap_diversity: 1,
                mean_pairwise_jaccard_distance: 1,
                max_pair_overlap: 0,
                overlap_histogram: [0],
                normalized_exposure_entropy: 1,
                effective_players_shannon: 9,
                effective_players_hhi: 9,
                mean_pairwise_points_corr: null,
                mean_pairwise_payout_corr: null,
                diversification_ratio_pts: null,
                composite_diversity_score: 0.9,
              },
              exposures: [],
              min_exposure_shortfalls: [],
            },
            lineups: [],
            diagnostics: {
              requested: 1,
              candidate_pool_size: 10,
              built: 1,
              max_overlap: 8,
              excluded: {
                no_projection: 0,
                zero_projection: 0,
                game_started: 0,
                no_game: 0,
                no_salary: 0,
                team_not_in_game: 0,
                by_caller: 0,
                below_depth: 0,
                refused: {},
              },
              optimizer_ms: 10,
              total_ms: 15,
            },
          }),
          { status: 200, headers: { 'content-type': 'application/json' } },
        );
      }) as typeof globalThis.fetch,
    });

    const jsonRes = await api.dfs.slates.optimizerLineups(1126128, { lineup_count: 1 });
    expect(jsonRes.slate.id).toBe(1126128);
    expect(calls[0].url).toBe('https://example.test/api/v1/dfs/slates/1126128/optimizer/lineups');

    const csvRes = await api.dfs.slates.optimizerLineupsCsv(1126128, { lineup_count: 1 });
    expect(csvRes).toContain('QB,RB');
    expect(calls[1].headers.get('Accept')).toContain('text/csv');
  });

  test('simLineups supports Prefer:wait and automatic 202 Location polling', async () => {
    let pollStep = 0;
    const seenPrefer: Array<string | null> = [];
    const seenUrls: string[] = [];
    const simPayload = {
      slate: SLATE_VIEW,
      contest: CONTEST_VIEW,
      source: { name: 'SIM', version: '1', updated_at: null, test_input: false },
      sim: {
        engine: 'nfl-play-sim/2.8.0',
        n_draws: 1000,
        draws: { building: 250, picking: 250, report: 500 },
        seed: 42,
        game_count: 10,
        scenarios_per_game: 5,
        simulated_field_lineups: 1000,
        sim_cache_hit: true,
        field_cache_hit: true,
        input_sha256: 'abc',
        versions: {
          engine_version: '1',
          draws_key: 'd',
          ownership_model_key: 'o',
          field_values_key: 'f',
          terms_hash: 't',
          rules_hash: 'r',
          pool_id: 'sk_1234',
        },
      },
      rules: {},
      games: [],
      player_pool: [],
      portfolio: {
        lineup_count: 1,
        total_entry_cost_dollars: 20,
        profitable_draws_pct: 50,
        payout: {
          ev_dollars: 25,
          net_ev_dollars: 5,
          roi_pct: 25,
          win_pct: 1,
          top_01_pct: 1.5,
          top_1_pct: 3,
          top_5_pct: 8,
          top_10_pct: 14,
          cash_pct: 25,
        },
        best_lineup_points: { mean: 150, sd: 10, p10: 130, p50: 150, p90: 170, p99: 190 },
        mean_lineup_points: 150,
        unique_players: 9,
        mean_pairwise_overlap: 0,
        mean_ownership_sum_pct: 90,
        mean_leverage_sum_pct: 10,
        exposures: { players: [], stacks: [], teams: [], games: [] },
        diversity: {
          structural_overlap_diversity: 1,
          mean_pairwise_jaccard_distance: 1,
          max_pair_overlap: 0,
          overlap_histogram: [0],
          normalized_exposure_entropy: 1,
          effective_players_shannon: 9,
          effective_players_hhi: 9,
          mean_pairwise_points_corr: null,
          mean_pairwise_payout_corr: null,
          diversification_ratio_pts: null,
          composite_diversity_score: 0.9,
        },
      },
      lineups: [],
      opponent_field: {
        summary: {},
        top_stacks: [],
        sample_lineups: [],
      },
      diagnostics: {
        requested: 1,
        candidate_pool_size: 10,
        built: 1,
        sort_by: 'roi',
        max_overlap: 8,
        excluded: {
          no_projection: 0,
          zero_projection: 0,
          game_started: 0,
          no_game: 0,
          no_salary: 0,
          team_not_in_game: 0,
          by_caller: 0,
          below_depth: 0,
          refused: {},
        },
        sim_ms: 10,
        field_ms: 10,
        optimizer_ms: 10,
        contest_eval_ms: 10,
        total_ms: 40,
      },
    };

    const api = new StatApi({
      apiKey: 'test_key',
      baseUrl: 'https://example.test',
      fetch: (async (input, init) => {
        const url = String(input);
        const hdrs = new Headers(init?.headers);
        seenUrls.push(url);
        seenPrefer.push(hdrs.get('Prefer'));
        if (hdrs.get('Prefer') === 'wait') {
          return new Response(JSON.stringify(simPayload), { status: 200 });
        }
        if (url.endsWith('/sim/lineups')) {
          return new Response(
            JSON.stringify({
              job_id: 'simjob_1',
              kind: 'sim_lineups',
              slate_id: 1126128,
              status: 'queued',
              queue_position: 1,
              retry_after_seconds: 0,
              poll_url: '/api/v1/dfs/slates/1126128/sim/jobs/simjob_1',
            }),
            {
              status: 202,
              headers: {
                Location: '/api/v1/dfs/slates/1126128/sim/jobs/simjob_1',
                'Retry-After': '0',
              },
            },
          );
        }
        pollStep++;
        if (pollStep === 1) {
          return new Response(
            JSON.stringify({
              job_id: 'simjob_1',
              kind: 'sim_lineups',
              slate_id: 1126128,
              status: 'running',
              queue_position: 0,
              retry_after_seconds: 0,
              poll_url: '/api/v1/dfs/slates/1126128/sim/jobs/simjob_1',
            }),
            { status: 202, headers: { 'Retry-After': '0' } },
          );
        }
        return new Response(JSON.stringify(simPayload), { status: 200 });
      }) as typeof globalThis.fetch,
    });

    const waitRes = await api.dfs.slates.simLineups(1126128, { lineup_count: 1 }, { wait: true });
    expect(waitRes.sim.versions.pool_id).toBe('sk_1234');
    expect(seenPrefer[0]).toBe('wait');

    const pollRes = await api.dfs.slates.simLineups(
      1126128,
      { lineup_count: 1 },
      { pollIntervalMs: 0 },
    );
    expect(pollRes.sim.versions.pool_id).toBe('sk_1234');
    expect(pollStep).toBe(2);
    expect(seenUrls.some((u) => u.endsWith('/sim/jobs/simjob_1'))).toBe(true);

    // Explicit manual start + poll + timeout guard
    pollStep = 0;
    const job = await api.dfs.slates.simLineupsStart(1126128, { lineup_count: 1 });
    expect(job.job_id).toBe('simjob_1');
    const pending = await api.dfs.slates.simJob(1126128, job.job_id);
    expect(pending.ready).toBe(false);
    const done = await api.dfs.slates.simJob(1126128, job.job_id);
    expect(done.ready).toBe(true);
    expect(done.result?.sim.versions.pool_id).toBe('sk_1234');

    // Immediate timeout throws NotReadyError on 202
    await expect(
      api.dfs.slates.simLineups(1126128, { lineup_count: 1 }, { timeoutMs: 0 }),
    ).rejects.toBeInstanceOf(NotReadyError);
  });
});
