// DO NOT EDIT — generated from schema/api/sim/nfl_sim.yml & nba_sim.yml by generate-sim-sdks.ts
// =============================================================================
// sim — Typed NFL & NBA Simulator (SIM module) & free Weeks types
// =============================================================================

import { fetchJson, type ResolvedStatApiOptions, toQueryString } from './client.js';

export interface NFLWeeksParams {
  season?: number;
}

export interface NFLMatchupSummary {
  game_id: number;
  season: number;
  week: number;
  season_type: string;
  kickoff: string | null;
  day: number | null;
  home_team_id: number;
  home_abbr: string;
  away_team_id: number;
  away_abbr: string;
  spread_home: number;
  game_total: number;
  home_team_total: number;
  away_team_total: number;
  moneyline_home: number | null;
  moneyline_away: number | null;
  is_final: boolean;
  real_home_pts: number | null;
  real_away_pts: number | null;
  is_free: boolean;
}

export interface NFLWeeksResponse {
  season: number;
  available_seasons: number[];
  free_game_id: number;
  weeks: number[];
  games: NFLMatchupSummary[];
}

export interface NFLSimPersonnel {
  p11: number;
  p12: number;
  p21: number;
  p13: number;
  p22: number;
  no_rb: number;
  goal_line: number;
}

export interface NFLSimOline {
  pass_block_grade: number;
  run_block_grade: number;
  sack_rate_allowed: number;
}

export interface NFLSimInjury {
  play_chance: number;
  snap_limit: number;
  injury_type: string;
  practice: string;
}

export interface NFLSimDefense {
  pressure_rate: number;
  sack_rate: number;
  stuff_rate: number;
  outside_cb_coverage: number;
  slot_cb_coverage: number;
  lb_safety_coverage: number;
  missed_tackle_rate: number;
  interception_rate: number;
  forced_fumble_rate: number;
  man_coverage_rate: number;
  blitz_rate: number;
  two_high_rate: number;
  avg_box_count: number;
}

export interface NFLSimKicker {
  player_id: number;
  fg_make_rate: number;
  fg_range_yds: number;
  xp_make_rate: number;
}

export interface NFLSimTeam {
  team_id: number;
  team_abbr: string;
  expected_plays: number;
  neutral_pass_rate: number;
  early_down_pass_rate: number;
  red_zone_pass_rate: number;
  red_zone_td_rate: number;
  trailing_pass_boost: number;
  no_huddle_rate: number;
  fourth_down_go_rate: number;
  personnel: NFLSimPersonnel;
  oline: NFLSimOline;
  defense: NFLSimDefense;
  kicker: NFLSimKicker;
}

export interface NFLSimUsage {
  snap_share: number;
  route_participation_rate: number;
  slot_rate: number;
  target_share: number;
  targets_per_route: number;
  red_zone_target_share: number;
  carry_share: number;
  short_yardage_carry_share: number;
  red_zone_carry_share: number;
  goal_line_carry_share: number;
  four_minute_carry_share: number;
}

export interface NFLSimPassing {
  completion_rate: number;
  time_to_throw_sec: number;
  bad_throw_rate: number;
  tight_window_rate: number;
  pressure_sack_rate: number;
  pressure_scramble_rate: number;
  interception_rate: number;
  designed_run_share: number;
}

export interface NFLSimReceiving {
  catch_rate: number;
  yards_per_target: number;
  adot: number;
  separation_yds: number;
  contested_catch_rate: number;
  drop_rate: number;
  yac_per_catch: number;
  zero_yac_rate: number;
  broken_tackle_rate: number;
  man_target_factor: number;
  zone_target_factor: number;
  screen_target_rate: number;
}

export interface NFLSimRushing {
  yards_per_carry: number;
  yards_before_contact: number;
  yards_after_contact: number;
  broken_tackle_rate: number;
  run_15plus_rate: number;
  inside_run_rate: number;
  fumble_rate: number;
}

export interface NFLSimAthleticism {
  speed_rating: number;
  elusiveness_rating: number;
  contact_rating: number;
}

export interface NFLSimInclude {
  summary?: boolean;
  distributions?: boolean;
  scorebox?: boolean;
  pbp?: boolean;
  /** unit: count; bounds: 0..10000 */
  scorebox_limit?: number;
  pbp_draws?: number[];
  scenarios?: boolean;
  scenarios_pbp?: boolean;
  /** unit: count; bounds: 1..5000 */
  scenarios_limit?: number;
  simulations?: boolean;
  simulations_pbp?: boolean;
  /** unit: count; bounds: 1..200 */
  simulations_limit?: number | null;
}

export interface NFLSimWeatherOverride {
  /** values: outdoors | dome | retractable_closed | retractable_open */
  roof?: string | null;
  /** unit: deg_F; bounds: -10..115; aliases: temperature_f */
  temp_f?: number | null;
  /** unit: mph; bounds: 0..55; aliases: wind_speed_mph */
  wind_mph?: number | null;
  /** unit: in_hr; bounds: 0..0.5; aliases: precipitation_in_hr, precip_prob */
  precip_in_hr?: number | null;
}

export interface NFLSimTeamOverride {
  out?: number[];
  /** unit: plays; bounds: 48..82; aliases: projected_plays */
  expected_plays?: number | null;
  /** unit: percent_0_100; bounds: 35..78 */
  neutral_pass_rate_pct?: number | null;
}

export interface NFLSimPlayerOverride {
  player_id: number;
  out?: boolean | null;
  /** values: OUT | DOUBTFUL | QUESTIONABLE | PROBABLE | ACTIVE | HEALTHY | AVAILABLE | GTD */
  status?: string | null;
  /** unit: percent_0_100; bounds: 0..100 */
  play_chance_pct?: number | null;
  /** unit: percent_0_100; bounds: 2..100 */
  snap_share_pct?: number | null;
  /** unit: yards; bounds: >= 0 */
  pass_yd?: number | null;
  /** unit: count; bounds: >= 0 */
  pass_td?: number | null;
  /** unit: yards; bounds: >= 0 */
  rush_yd?: number | null;
  /** unit: yards; bounds: >= 0 */
  rec_yd?: number | null;
  /** unit: count; bounds: >= 0 */
  receptions?: number | null;
  /** unit: count; bounds: >= 0 */
  carries?: number | null;
  /** unit: count; bounds: >= 0 */
  td?: number | null;
}

export interface NFLSimPublicRunRequest {
  /** bounds: >= 1 */
  game_id: number;
  /** unit: draws; bounds: 50..10000; default: 2500; aliases: n_runs, n_sims */
  n_draws?: number;
  /** default: 20261028 */
  seed?: number;
  /** aliases: granularity */
  include?: NFLSimInclude;
  /** unit: count; bounds: 1..5000 */
  scenarios?: number | null;
  scenarios_pbp?: boolean | null;
  simulations?: boolean | null;
  simulations_pbp?: boolean | null;
  /** unit: count; bounds: 1..200 */
  simulations_limit?: number | null;
  /** unit: points; bounds: 6..48 */
  home_score?: number | null;
  /** unit: points; bounds: 6..48 */
  away_score?: number | null;
  /** unit: points; bounds: 24..76 */
  total?: number | null;
  /** unit: points; bounds: -28..28 */
  home_spread?: number | null;
  /** unit: percent_0_100; bounds: 2..98 */
  home_win_pct?: number | null;
  /** values: all | top_props | game_lines | none */
  mode?: string | null;
  weather?: NFLSimWeatherOverride | null;
  home?: NFLSimTeamOverride | null;
  away?: NFLSimTeamOverride | null;
  players?: NFLSimPlayerOverride[];
}

export interface NFLSimTeamSummary {
  team_id: number;
  is_home: boolean;
  vegas_team_total: number;
  points_mean: number;
  points_sd: number;
  points_p10: number;
  points_p50: number;
  points_p90: number;
  q1_points_mean: number;
  q2_points_mean: number;
  q3_points_mean: number;
  q4_points_mean: number;
  ot_points_mean: number;
  win_prob: number;
  plays_mean: number;
  pass_att_mean: number;
  pass_cmp_mean: number;
  pass_yd_mean: number;
  pass_td_mean: number;
  int_mean: number;
  rush_att_mean: number;
  rush_yd_mean: number;
  rush_td_mean: number;
  sacks_taken_mean: number;
  drives_mean: number;
  drive_td_rate: number;
  drive_fg_rate: number;
  drive_punt_rate: number;
  drive_turnover_rate: number;
  third_down_conv_rate: number;
  fourth_down_conv_rate: number;
  dst_sacks_mean: number;
  dst_int_mean: number;
  dst_fumbles_rec_mean: number;
  dst_td_mean: number;
  dst_pts_allowed_mean: number;
}

export interface NFLSimPlayerSummary {
  player_id: number;
  team_id: number;
  is_home: boolean;
  position: string;
  depth_role: string;
  market_tier: string;
  p_active: number;
  snaps_mean: number;
  snaps_p50: number;
  routes_mean: number;
  pass_cmp_mean: number;
  pass_att_mean: number;
  pass_yd_mean: number;
  pass_yd_sd: number;
  pass_yd_p10: number;
  pass_yd_p50: number;
  pass_yd_p90: number;
  pass_td_mean: number;
  int_mean: number;
  longest_pass_p50: number;
  carries_mean: number;
  designed_rushes_mean: number;
  scrambles_mean: number;
  rush_yd_mean: number;
  rush_yd_sd: number;
  rush_yd_p10: number;
  rush_yd_p50: number;
  rush_yd_p90: number;
  rush_td_mean: number;
  longest_rush_p50: number;
  targets_mean: number;
  receptions_mean: number;
  rec_yd_mean: number;
  rec_yd_sd: number;
  rec_yd_p10: number;
  rec_yd_p50: number;
  rec_yd_p90: number;
  rec_td_mean: number;
  air_yd_mean: number;
  yac_yd_mean: number;
  longest_rec_p50: number;
  fg_made_mean: number;
  fg_att_mean: number;
  xp_made_mean: number;
  kick_pts_mean: number;
  fumbles_lost_mean: number;
  p_anytime_td: number;
  p_2plus_td: number;
  p_pass_300yd: number;
  p_rush_100yd: number;
  p_rec_100yd: number;
  vegas_pass_yd: number | null;
  vegas_pass_att: number | null;
  vegas_rush_yd: number | null;
  vegas_carries: number | null;
  vegas_rec_yd: number | null;
  vegas_receptions: number | null;
  vegas_td_prob: number | null;
}

export interface NFLSimSummarySection {
  game_id: number;
  season: number;
  week: number;
  n_draws: number;
  seed: number;
  has_vegas_line: boolean;
  vegas_spread_home: number;
  vegas_total: number;
  sim_total_mean: number;
  sim_total_sd: number;
  sim_spread_home_mean: number;
  sim_spread_home_sd: number;
  home_win_prob: number;
  away_win_prob: number;
  tie_prob: number;
  home_cover_prob: number;
  over_prob: number;
  overtime_rate: number;
  solve_elapsed_ms: number;
  sim_elapsed_ms: number;
  conservation_ok: boolean;
  c0_max_diff: number;
  home_team: NFLSimTeamSummary;
  away_team: NFLSimTeamSummary;
  players: NFLSimPlayerSummary[];
}

export interface NFLHistogramBin {
  x_min: number;
  x_max: number;
  x_mid: number;
  count: number;
  prob: number;
  cum_prob: number;
  gaussian_density: number;
  kde_density: number;
  sample_draw_idx: number | null;
}

export interface NFLSimBellCurve {
  metric: string;
  label: string;
  discrete: boolean;
  n_draws: number;
  mean: number;
  sd: number;
  skew: number;
  min: number;
  max: number;
  p05: number;
  p10: number;
  p25: number;
  p50: number;
  p75: number;
  p90: number;
  p95: number;
  vegas_line: number | null;
  p_over_vegas: number | null;
  bins: NFLHistogramBin[];
}

export interface NFLPlayerBellCurves {
  player_id: number;
  team_id: number;
  position: string;
  depth_role: string;
  pass_yd: NFLSimBellCurve | null;
  rush_yd: NFLSimBellCurve | null;
  rec_yd: NFLSimBellCurve | null;
  receptions: NFLSimBellCurve | null;
  targets: NFLSimBellCurve | null;
  carries: NFLSimBellCurve | null;
}

export interface NFLSimDistributionsSection {
  total_points: NFLSimBellCurve;
  spread_margin_home: NFLSimBellCurve;
  home_points: NFLSimBellCurve;
  away_points: NFLSimBellCurve;
  combined_plays: NFLSimBellCurve;
  home_pass_yd: NFLSimBellCurve;
  away_pass_yd: NFLSimBellCurve;
  home_rush_yd: NFLSimBellCurve;
  away_rush_yd: NFLSimBellCurve;
  players: NFLPlayerBellCurves[];
}

export interface NFLTeamGameBoxScore {
  points: number;
  q1_points: number;
  q2_points: number;
  q3_points: number;
  q4_points: number;
  ot_points: number;
  plays: number;
  pass_att: number;
  pass_cmp: number;
  pass_yd: number;
  pass_td: number;
  interceptions_thrown: number;
  sacks_taken: number;
  sack_yd_lost: number;
  rush_att: number;
  rush_yd: number;
  rush_td: number;
  first_downs: number;
  third_down_att: number;
  third_down_conv: number;
  fourth_down_att: number;
  fourth_down_conv: number;
  drives: number;
  drive_tds: number;
  drive_fgs: number;
  drive_missed_fgs: number;
  drive_punts: number;
  drive_turnovers: number;
  drive_downs: number;
  drive_end_half: number;
  fg_att: number;
  fg_made: number;
  xp_att: number;
  xp_made: number;
  two_pt_att: number;
  two_pt_made: number;
  penalties: number;
  penalty_yds: number;
  time_of_possession_sec: number;
  throwaways: number;
  spikes: number;
  batted_passes: number;
  oob_untackled_plays: number;
  special_teams_returns: number;
  kickoffs_total: number;
  kickoff_returns: number;
  kickoff_start_yardline_sum: number;
  trailing_10plus_dropbacks: number;
  trailing_10plus_plays: number;
  neutral_dropbacks: number;
  neutral_plays: number;
  p11_snaps: number;
  p12_snaps: number;
  p21_snaps: number;
  no_huddle_snaps: number;
  unassisted_team_sacks: number;
  dst_pressures: number;
  pick_sixes_scored: number;
  fumble_ret_tds_scored: number;
  dst_sacks: number;
  dst_interceptions: number;
  dst_fumbles_recovered: number;
  dst_defensive_tds: number;
  dst_safeties: number;
  dst_points_allowed: number;
}

export interface NFLPlayerDrawBoxSummary {
  player_id: number;
  team_id: number;
  active: boolean;
  snaps: number;
  routes: number;
  pass_cmp: number;
  pass_att: number;
  pass_yd: number;
  pass_td: number;
  interception: number;
  sacks_taken: number;
  carries: number;
  designed_rushes: number;
  scrambles: number;
  rush_yd: number;
  rush_td: number;
  targets: number;
  receptions: number;
  rec_yd: number;
  rec_td: number;
  air_yd: number;
  yac_yd: number;
  fumbles_lost: number;
  two_pt_conv: number;
  fg_made: number;
  fg_att: number;
  xp_made: number;
  xp_att: number;
  longest_pass: number;
  longest_rush: number;
  longest_rec: number;
}

export interface NFLSimGameScoreboxDraw {
  draw_idx: number;
  archetype: string | null;
  label: string;
  away_linescore: number[];
  home_linescore: number[];
  total_points: number;
  final_margin_home: number;
  q4_margin_home: number;
  overtime_played: boolean;
  away_team: NFLTeamGameBoxScore;
  home_team: NFLTeamGameBoxScore;
  players: NFLPlayerDrawBoxSummary[];
}

export interface NFLSimScoreboxSection {
  archetypes: NFLSimGameScoreboxDraw[];
  draws: NFLSimGameScoreboxDraw[];
}

export interface NFLSimDriveSummary {
  drive_number: number;
  posteam_is_home: boolean;
  start_quarter: number;
  start_clock: string;
  start_yardline_100: number;
  end_yardline_100: number;
  plays: number;
  net_yards: number;
  first_downs: number;
  result: string;
  home_score_after: number;
  away_score_after: number;
}

export interface NFLSimPlayEvent {
  game_id: number;
  draw_idx: number;
  drive_number: number;
  play_number: number;
  quarter: number;
  half_seconds_remaining: number;
  game_seconds_remaining: number;
  posteam_is_home: boolean;
  down: number;
  ydstogo: number;
  yardline_100: number;
  score_diff: number;
  home_score_before: number;
  away_score_before: number;
  home_score_after: number;
  away_score_after: number;
  play_type: string;
  personnel: number;
  passer_id: number;
  rusher_id: number;
  receiver_id: number;
  yards_gained: number;
  air_yards: number;
  yac_yards: number;
  is_first_down: boolean;
  is_touchdown: boolean;
  is_turnover: boolean;
  is_sack: boolean;
  is_complete: boolean;
  is_fg_good: boolean;
  is_two_pt_good: boolean;
}

export interface NFLSimPlayRecord {
  event: NFLSimPlayEvent;
  clock_display: string;
  down_distance_display: string;
  yardline_display: string;
  personnel_display: string;
  description: string;
}

export interface NFLSimTracedGame {
  draw_idx: number;
  archetype: string | null;
  label: string;
  scorebox: NFLSimGameScoreboxDraw;
  drives: NFLSimDriveSummary[];
  plays: NFLSimPlayRecord[];
}

export interface NFLSimScenarioPlayerProfile {
  player_id: number;
  player_name: string;
  team_id: number;
  team_abbr: string;
  position: string;
  depth_role: string;
  active: boolean;
  injury_status: string | null;
  usage: NFLSimUsage;
  passing: NFLSimPassing | null;
  receiving: NFLSimReceiving;
  rushing: NFLSimRushing;
  athleticism: NFLSimAthleticism;
  injury: NFLSimInjury;
}

export interface NFLSimScenarioQuarters {
  away: number[];
  home: number[];
}

export interface NFLSimScenarioScoreboard {
  quarters: NFLSimScenarioQuarters;
  away_score: number;
  home_score: number;
  total_points: number;
  home_margin: number;
  overtime: boolean | null;
}

export interface NFLSimTeamBoxScoreStat {
  points: number | null;
  plays: number | null;
  first_downs: number | null;
  total_yd: number | null;
  pass_cmp: number | null;
  pass_att: number | null;
  pass_yd: number | null;
  pass_td: number | null;
  int: number | null;
  sacks_taken: number | null;
  sack_yd_lost: number | null;
  rush_att: number | null;
  rush_yd: number | null;
  rush_td: number | null;
  fumbles_lost: number | null;
  drives: number | null;
  drive_tds: number | null;
  fg_made: number | null;
  fg_att: number | null;
  xp_made: number | null;
  xp_att: number | null;
  two_pt_made: number | null;
  two_pt_att: number | null;
  def_sacks: number | null;
  def_int: number | null;
  def_fumble_rec: number | null;
  def_td: number | null;
  safety: number | null;
  points_allowed: number | null;
}

export interface NFLSimPlayerBoxScoreStat {
  snaps: number | null;
  routes: number | null;
  pass_cmp: number | null;
  pass_att: number | null;
  pass_yd: number | null;
  pass_td: number | null;
  int: number | null;
  sacks_taken: number | null;
  sack_yd_lost: number | null;
  longest_pass: number | null;
  rush_att: number | null;
  designed_rushes: number | null;
  scrambles: number | null;
  rush_yd: number | null;
  rush_td: number | null;
  longest_rush: number | null;
  targets: number | null;
  rec: number | null;
  rec_yd: number | null;
  rec_td: number | null;
  air_yd: number | null;
  yac_yd: number | null;
  longest_rec: number | null;
  fumbles_lost: number | null;
  two_pt_conv: number | null;
  fg_made: number | null;
  fg_att: number | null;
  xp_made: number | null;
  xp_att: number | null;
  solo_tackles: number | null;
  ast_tackles: number | null;
  tfl: number | null;
  solo_sacks: number | null;
  split_sacks: number | null;
  sack_yd: number | null;
  qb_hits: number | null;
  pressures: number | null;
  targets_coverage: number | null;
  receptions_allowed: number | null;
  rec_yd_allowed: number | null;
  passes_defended: number | null;
  def_int: number | null;
  forced_fumbles: number | null;
  fumble_rec: number | null;
  def_td: number | null;
}

export interface NFLSimPlayerCorrelations {
  player_ids: number[];
  matrix: number[][];
}

export interface NFLSimGameDrive {
  drive_number: number;
  possession_team_id: number;
  quarter_start: number;
  quarter_end: number;
  clock_start: string;
  clock_end: string;
  time_of_possession: string;
  start_yardline: string;
  end_yardline: string;
  start_reason: string;
  result: string;
  play_count: number;
  net_yards: number;
  first_downs: number;
  inside_20: boolean;
  scoring_drive: boolean;
  first_play_id: number;
  last_play_id: number;
}

export interface NFLSimPlaySituation {
  quarter: number;
  clock: string;
  quarter_seconds_remaining: number;
  possession_team_id: number;
  defensive_team_id: number;
  down: number | null;
  ydstogo: number;
  yardline_100: number;
  yardline: string;
  goal_to_go: boolean;
}

export interface NFLSimPlayStat {
  sequence: number;
  player_id: number;
  team_id: number;
  category: string;
  role: string;
  pass_att: number | null;
  pass_cmp: number | null;
  pass_yd: number | null;
  air_yd: number | null;
  pass_td: number | null;
  int_thrown: number | null;
  sacks_taken: number | null;
  sack_yd_lost: number | null;
  rush_att: number | null;
  rush_yd: number | null;
  rush_td: number | null;
  is_scramble: boolean | null;
  targets: number | null;
  rec: number | null;
  rec_yd: number | null;
  yac_yd: number | null;
  rec_td: number | null;
  fumbles: number | null;
  fumbles_lost: number | null;
  two_pt_conv: number | null;
  fg_att: number | null;
  fg_made: number | null;
  xp_att: number | null;
  xp_made: number | null;
  kick_distance: number | null;
  solo_tackles: number | null;
  ast_tackles: number | null;
  solo_sacks: number | null;
  split_sacks: number | null;
  sack_yd: number | null;
  tfl: number | null;
  qb_hits: number | null;
  pressures: number | null;
  targets_coverage: number | null;
  receptions_allowed: number | null;
  rec_yd_allowed: number | null;
  passes_defended: number | null;
  def_int: number | null;
  forced_fumbles: number | null;
  fumble_rec: number | null;
  def_td: number | null;
  dk_pts: number | null;
  fd_pts: number | null;
}

export interface NFLSimPlayByPlay {
  play_id: number;
  drive_number: number;
  play_type: string;
  description: string;
  start_situation: NFLSimPlaySituation;
  end_situation: NFLSimPlaySituation;
  offense_personnel: string | null;
  defense_package: string | null;
  defenders_in_box: number | null;
  shotgun: boolean;
  no_huddle: boolean;
  qb_dropback: boolean;
  qb_scramble: boolean;
  was_pressure: boolean;
  pass_length: string | null;
  pass_location: string | null;
  air_yards: number | null;
  yards_after_catch: number | null;
  run_location: string | null;
  run_gap: string | null;
  yards_gained: number;
  drive_net_yards: number;
  first_down: boolean;
  touchdown: boolean;
  turnover: boolean;
  sack: boolean;
  qb_hit: boolean;
  tackled_for_loss: boolean;
  away_score_before: number;
  home_score_before: number;
  away_score_after: number;
  home_score_after: number;
  on_field_offense_ids: number[];
  route_runner_ids: number[];
  play_stats: NFLSimPlayStat[];
}

export interface NFLSimGamePbp {
  drives: NFLSimGameDrive[];
  plays: NFLSimPlayByPlay[];
}

export interface NFLSimGameScenario {
  scenario_id: number;
  draw_idx: number;
  probability: number;
  cluster_draw_count: number;
  scoreboard: NFLSimScenarioScoreboard;
  teams: Record<string, NFLSimTeamBoxScoreStat>;
  players: Record<string, NFLSimPlayerBoxScoreStat>;
  player_correlations: NFLSimPlayerCorrelations;
  pbp: NFLSimGamePbp | null;
}

export interface NFLSimScenariosSection {
  mode: string;
  n_monte_carlo_draws: number;
  scenarios_returned: number;
  probability_sum: number;
  teams: Record<string, NFLSimTeam>;
  players: Record<string, NFLSimScenarioPlayerProfile>;
  scenarios: NFLSimGameScenario[];
}

export interface NFLSimSimulatedGame {
  sim_id: number;
  draw_idx: number;
  scoreboard: NFLSimScenarioScoreboard;
  teams: Record<string, NFLSimTeamBoxScoreStat>;
  players: Record<string, NFLSimPlayerBoxScoreStat>;
  pbp: NFLSimGamePbp | null;
}

export interface NFLSimSimulationsSection {
  n_simulations: number;
  pbp_included: boolean;
  games: NFLSimSimulatedGame[];
}

export interface NFLSimGranularOutput {
  game_id: number;
  n_draws: number;
  seed: number;
  solve_elapsed_ms: number;
  sim_elapsed_ms: number;
  summary?: NFLSimSummarySection | null;
  distributions?: NFLSimDistributionsSection | null;
  scorebox?: NFLSimScoreboxSection | null;
  pbp?: NFLSimTracedGame[] | null;
  scenarios?: NFLSimScenariosSection | null;
  simulations?: NFLSimSimulationsSection | null;
}

export interface NFLSimPublicRunResponse {
  matchup: NFLMatchupSummary;
  input_sha256: string;
  output: NFLSimGranularOutput;
}

/**
 * Typed client for `/api/v1/sim/nfl/*` and `/api/v1/nfl/weeks`.
 */
export class SimNFLClient {
  constructor(private readonly opts: ResolvedStatApiOptions) {}

  /**
   * List available NFL weeks, matchup summaries, and `free_game_id` (`43418`).
   * Free navigation endpoint (`GET /api/v1/nfl/weeks`).
   *
   * Reference: https://stat-api.com/docs/api/nfl/seasons/#endpoint-nfl-seasons-weeks
   * Tutorial: recipes 1, 2, 3, 4, 5 of https://stat-api.com/docs/cookbook/sim/
   */
  async weeks(params?: NFLWeeksParams): Promise<NFLWeeksResponse> {
    const qs = toQueryString(params as Record<string, unknown> | undefined);
    return (await fetchJson<NFLWeeksResponse>(this.opts, `/api/v1/nfl/weeks${qs}`)).data;
  }

  /**
   * Run N parallel Monte Carlo simulations (`POST /api/v1/sim/nfl/run`).
   * Free in full for `game_id = 43418` (`2026 Week 1 DEN @ KC`).
   *
   * Reference: https://stat-api.com/docs/api/sim/#endpoint-sim-nfl-run
   * Tutorial: recipes 1, 2, 3, 4, 5 of https://stat-api.com/docs/cookbook/sim/
   */
  async run(request: NFLSimPublicRunRequest): Promise<NFLSimPublicRunResponse> {
    return (
      await fetchJson<NFLSimPublicRunResponse>(this.opts, '/api/v1/sim/nfl/run', {
        method: 'POST',
        body: JSON.stringify(request),
      })
    ).data;
  }
}

export { SimNFLClient as SimNFLResource };

export interface NBAWeeksParams {
  season?: number;
  week?: number;
  season_type?: string;
}

export interface NBAMatchupSummary {
  game_id: number;
  season: number;
  week: number;
  /** values: regular_season | in_season_tournament | play_in | postseason */
  season_type: string;
  kickoff: string | null;
  day: number | null;
  home_team_id: number;
  home_abbr: string;
  away_team_id: number;
  away_abbr: string;
  spread_home: number;
  game_total: number;
  home_team_total: number;
  away_team_total: number;
  moneyline_home: number | null;
  moneyline_away: number | null;
  is_final: boolean;
  real_home_pts: number | null;
  real_away_pts: number | null;
  is_free: boolean;
}

export interface NBASimWeeksResponse {
  season: number;
  available_seasons: number[];
  free_game_id: number;
  weeks: number[];
  games: NBAMatchupSummary[];
}

export interface NBASimInclude {
  summary?: boolean;
  distributions?: boolean;
  scorebox?: boolean;
  pbp?: boolean;
  scenarios?: boolean;
  scenarios_pbp?: boolean;
  simulations?: boolean;
  simulations_pbp?: boolean;
  /** unit: count; bounds: 1..100 */
  scenarios_limit?: number;
  /** unit: count; bounds: 1..200 */
  simulations_limit?: number | null;
  /** unit: count; bounds: 0..10000 */
  scorebox_limit?: number;
  pbp_draws?: number[];
}

export interface NBASimTeamOverride {
  out?: number[];
  /** unit: possessions_per_48; bounds: 75..125 */
  pace?: number | null;
  /** unit: pts_per_100; bounds: 80..145 */
  off_rating?: number | null;
  /** unit: pts_per_100; bounds: 80..145 */
  def_rating?: number | null;
  /** unit: days; bounds: 0..14 */
  rest_days?: number | null;
}

export interface NBASimPlayerOverride {
  player_id: number;
  out?: boolean | null;
  /** values: OUT | DOUBTFUL | QUESTIONABLE | PROBABLE | ACTIVE | HEALTHY | AVAILABLE | GTD */
  status?: string | null;
  /** unit: percent_0_100; bounds: 0..100 */
  play_chance_pct?: number | null;
  /** values: starter | sixth_man | rotation | bench | out */
  role?: string | null;
  /** unit: minutes; bounds: 0..48 */
  minutes?: number | null;
  /** unit: minutes; bounds: 0..48 */
  minutes_cap?: number | null;
  /** unit: points; bounds: >= 0 */
  points?: number | null;
  /** unit: rebounds; bounds: >= 0 */
  rebounds?: number | null;
  /** unit: assists; bounds: >= 0 */
  assists?: number | null;
  /** unit: threes; bounds: >= 0 */
  threes?: number | null;
  /** unit: pra; bounds: >= 0 */
  pra?: number | null;
  /** unit: steals; bounds: >= 0 */
  steals?: number | null;
  /** unit: blocks; bounds: >= 0 */
  blocks?: number | null;
  /** unit: ratio_0_1; bounds: 0..1 */
  double_double_prob?: number | null;
}

export interface NBASimPublicRunRequest {
  /** bounds: >= 1 */
  game_id: number;
  /** unit: draws; bounds: 50..10000; default: 2500; aliases: n_runs, n_sims */
  n_draws?: number;
  /** default: 20261028 */
  seed?: number;
  /** aliases: granularity */
  include?: NBASimInclude;
  /** unit: count; bounds: 1..100 */
  scenarios?: number | null;
  scenarios_pbp?: boolean | null;
  simulations?: boolean | null;
  simulations_pbp?: boolean | null;
  /** unit: count; bounds: 1..200 */
  simulations_limit?: number | null;
  /** unit: points; bounds: 50..200 */
  home_score?: number | null;
  /** unit: points; bounds: 50..200 */
  away_score?: number | null;
  /** unit: points; bounds: 150..300 */
  total?: number | null;
  /** unit: points; bounds: -40..40 */
  home_spread?: number | null;
  /** unit: percent_0_100; bounds: 0.5..99.5 */
  home_win_pct?: number | null;
  /** values: all | top_props | game_lines | none */
  mode?: string | null;
  /** unit: percent_baseline_100; bounds: 50..150 */
  game_pace_pct?: number | null;
  /** unit: percent_baseline_100; bounds: 50..150 */
  ref_whistle?: number | null;
  /** unit: percent_baseline_100; bounds: 0..250 */
  blowout_pulls?: number | null;
  home?: NBASimTeamOverride | null;
  away?: NBASimTeamOverride | null;
  players?: NBASimPlayerOverride[];
}

export interface NBASimQuantileProfile {
  min: number;
  p01: number;
  p05: number;
  p10: number;
  p25: number;
  p50: number;
  p75: number;
  p90: number;
  p95: number;
  p99: number;
  p99_5: number;
  max: number;
  skew: number;
}

export interface NBAVegasCalibrationDiagnostics {
  vegas_mode: string;
  iterations_run: number;
  target_home_pts: number | null;
  target_away_pts: number | null;
  achieved_pilot_home_pts: number;
  achieved_pilot_away_pts: number;
  home_efg_tilt: number;
  away_efg_tilt: number;
  rank_inversion_count: number;
}

export interface NBASimTeamSummary {
  team_code: string;
  team_name: string;
  is_home: boolean;
  vegas_team_total: number | null;
  points_mean: number;
  points_sd: number;
  points_p01: number;
  points_p05: number;
  points_p10: number;
  points_p25: number;
  points_p50: number;
  points_p75: number;
  points_p90: number;
  points_p95: number;
  points_p99: number;
  points_quantiles: NBASimQuantileProfile;
  q1_points_mean: number;
  q2_points_mean: number;
  q3_points_mean: number;
  q4_points_mean: number;
  ot_points_mean: number;
  win_prob: number;
  possessions_mean: number;
  offensive_rating_mean: number;
  fg_pct_mean: number;
  fg2_pct_mean: number;
  fg3_pct_mean: number;
  ft_pct_mean: number;
  fgm_mean: number;
  fga_mean: number;
  fg2m_mean: number;
  fg2a_mean: number;
  fg3m_mean: number;
  fg3a_mean: number;
  ftm_mean: number;
  fta_mean: number;
  reb_mean: number;
  oreb_mean: number;
  dreb_mean: number;
  ast_mean: number;
  stl_mean: number;
  blk_mean: number;
  ba_mean: number;
  tov_mean: number;
  pf_mean: number;
  fd_mean: number;
  fastbreak_pts_mean: number;
  paint_pts_mean: number;
  second_chance_pts_mean: number;
  pts_off_turnovers_mean: number;
  starter_pts_mean: number;
  bench_pts_mean: number;
  biggest_lead_mean: number;
  lead_changes_mean: number;
  times_tied_mean: number;
  pra_mean: number;
  dk_pts_mean: number;
  fd_pts_mean: number;
}

export interface NBAScoreBinStatProfile {
  quintile_label: string;
  draw_count: number;
  game_pts_mean: number;
  game_fgm_mean: number;
  game_fg3m_mean: number;
  game_ftm_mean: number;
  game_reb_mean: number;
  game_ast_mean: number;
  game_pra_mean: number;
  game_dk_pts_mean: number;
  game_fd_pts_mean: number;
  players_35plus_dk_mean: number;
}

export interface NBASimPlayerSummary {
  player_id: number;
  name: string;
  team_code: string;
  is_home: boolean;
  position: string;
  rotation_role: string;
  role_label: string;
  rotation_archetype_label: string;
  starter_prob: number;
  active_prob: number;
  coach_dnp_prob: number;
  dk_salary: number | null;
  fd_salary: number | null;
  projected_minutes: number;
  minutes_mean: number;
  minutes_sd: number;
  minutes_p10: number;
  minutes_p50: number;
  minutes_p90: number;
  minutes_quantiles: NBASimQuantileProfile;
  q1_minutes_mean: number;
  q2_minutes_mean: number;
  q3_minutes_mean: number;
  q4_minutes_mean: number;
  ot_minutes_mean: number;
  q1_full_12m_prob: number;
  stint_count_mean: number;
  on_court_ortg_mean: number;
  off_court_ortg_mean: number;
  on_court_drtg_mean: number;
  off_court_drtg_mean: number;
  on_off_net_rating_delta_mean: number;
  pts_mean: number;
  pts_sd: number;
  pts_p10: number;
  pts_p50: number;
  pts_p90: number;
  pts_quantiles: NBASimQuantileProfile;
  q1_pts_mean: number;
  q2_pts_mean: number;
  q3_pts_mean: number;
  q4_pts_mean: number;
  reb_mean: number;
  reb_sd: number;
  reb_p10: number;
  reb_p50: number;
  reb_p90: number;
  reb_quantiles: NBASimQuantileProfile;
  oreb_mean: number;
  dreb_mean: number;
  ast_mean: number;
  ast_sd: number;
  ast_p10: number;
  ast_p50: number;
  ast_p90: number;
  ast_quantiles: NBASimQuantileProfile;
  fg3m_mean: number;
  fg3m_p50: number;
  fg3m_p90: number;
  fg3m_quantiles: NBASimQuantileProfile;
  fg3a_mean: number;
  fg2m_mean: number;
  fg2a_mean: number;
  fgm_mean: number;
  fga_mean: number;
  ftm_mean: number;
  fta_mean: number;
  stl_mean: number;
  blk_mean: number;
  ba_mean: number;
  tov_mean: number;
  pf_mean: number;
  fd_mean: number;
  paint_pts_mean: number;
  fastbreak_pts_mean: number;
  second_chance_pts_mean: number;
  unassisted_fgm_mean: number;
  foul_trouble_rate: number;
  plus_minus_mean: number;
  pra_mean: number;
  pra_p50: number;
  pra_p90: number;
  pra_quantiles: NBASimQuantileProfile;
  double_double_prob: number;
  triple_double_prob: number;
  p_20plus_pts: number;
  p_30plus_pts: number;
  p_40plus_pts: number;
  p_50plus_pts: number;
  p_10plus_reb: number;
  p_10plus_ast: number;
  p_50plus_dk: number;
  p_60plus_dk: number;
  dk_pts_mean: number;
  dk_pts_sd: number;
  dk_pts_p10: number;
  dk_pts_p50: number;
  dk_pts_p75: number;
  dk_pts_p90: number;
  dk_pts_p95: number;
  dk_pts_quantiles: NBASimQuantileProfile;
  fd_pts_mean: number;
  fd_pts_sd: number;
  fd_pts_p50: number;
  fd_pts_p90: number;
  fd_pts_quantiles: NBASimQuantileProfile;
  vegas_pts_line: number | null;
  vegas_reb_line: number | null;
  vegas_ast_line: number | null;
  vegas_threes_line: number | null;
  vegas_pra_line: number | null;
}

export interface NBACoMovementImpact {
  teammate_player_id: number;
  teammate_name: string;
  team_code: string;
  relation: string;
  dk_pts_delta_when_focal_booms: number;
  pts_delta_when_focal_booms: number;
  ast_delta_when_focal_booms: number;
  reb_delta_when_focal_booms: number;
}

export interface NBACoMovementSummary {
  focal_player_id: number;
  focal_player_name: string;
  team_code: string;
  boom_threshold_dk_pts: number;
  winners: NBACoMovementImpact[];
  losers: NBACoMovementImpact[];
}

export interface NBASimSummarySection {
  game_id: string;
  season: number;
  game_date: string;
  season_type: string;
  n_draws: number;
  seed: number;
  vegas_mode: string;
  vegas_spread_home: number | null;
  vegas_total: number | null;
  sim_total_mean: number;
  sim_total_sd: number;
  total_quantiles: NBASimQuantileProfile;
  sim_spread_home_mean: number;
  sim_spread_home_sd: number;
  spread_home_quantiles: NBASimQuantileProfile;
  home_win_prob: number;
  away_win_prob: number;
  tie_prob: number;
  home_cover_prob: number;
  over_prob: number;
  overtime_rate: number;
  possessions_per_team_mean: number;
  solve_elapsed_ms: number;
  sim_elapsed_ms: number;
  conservation_ok: boolean;
  c0_max_diff: number;
  calibration_diagnostics: NBAVegasCalibrationDiagnostics;
  home_team: NBASimTeamSummary;
  away_team: NBASimTeamSummary;
  game_pra_mean: number;
  game_dk_pts_mean: number;
  game_fd_pts_mean: number;
  score_conditioned_scaling?: NBAScoreBinStatProfile[];
  players: NBASimPlayerSummary[];
  co_movement: NBACoMovementSummary[];
}

export interface NBAHistogramBin {
  x_min: number;
  x_max: number;
  x_mid: number;
  count: number;
  prob: number;
  cum_prob: number;
  gaussian_density: number;
  kde_density: number;
  sample_draw_idx: number | null;
}

export interface NBASimBellCurve {
  metric: string;
  label: string;
  discrete: boolean;
  n_draws: number;
  mean: number;
  sd: number;
  skew: number;
  min: number;
  max: number;
  p01: number;
  p05: number;
  p10: number;
  p25: number;
  p50: number;
  p75: number;
  p90: number;
  p95: number;
  p99: number;
  p99_5: number;
  vegas_line: number | null;
  p_over_vegas: number | null;
  bins: NBAHistogramBin[];
}

export interface NBAPlayerBellCurves {
  player_id: number;
  name: string;
  team_code: string;
  is_home: boolean;
  position: string;
  rotation_role: string;
  role_label: string;
  dk_pts: NBASimBellCurve;
  fd_pts: NBASimBellCurve;
  minutes: NBASimBellCurve;
  points: NBASimBellCurve;
  rebounds: NBASimBellCurve;
  assists: NBASimBellCurve;
  threes: NBASimBellCurve;
  pra: NBASimBellCurve;
}

export interface NBASimDistributionsSection {
  total_points: NBASimBellCurve;
  spread_margin_home: NBASimBellCurve;
  home_points: NBASimBellCurve;
  away_points: NBASimBellCurve;
  possessions_per_team: NBASimBellCurve;
  home_threes: NBASimBellCurve;
  away_threes: NBASimBellCurve;
  home_rebounds: NBASimBellCurve;
  away_rebounds: NBASimBellCurve;
  players: NBAPlayerBellCurves[];
}

export interface NBALineupStintBoxRow {
  player_ids: number[];
  package_tag: string;
  minutes_seconds: number;
  possessions: number;
  pts_for: number;
  pts_against: number;
  plus_minus: number;
}

export interface NBAPlayerBoxScore {
  player_id: number;
  minutes_seconds: number;
  pts: number;
  fgm: number;
  fga: number;
  fg3m: number;
  fg3a: number;
  ftm: number;
  fta: number;
  oreb: number;
  dreb: number;
  reb: number;
  ast: number;
  stl: number;
  blk: number;
  ba: number;
  tov: number;
  pf: number;
  fd: number;
  plus_minus: number;
  paint_pts: number;
  fastbreak_pts: number;
  second_chance_pts: number;
  unassisted_fgm: number;
  stint_count: number;
  quarter_pts: number[];
  quarter_minutes_sec: number[];
  quarter_fga: number[];
  started: boolean;
  fouled_out: boolean;
  injured_out: boolean;
}

export interface NBATeamBoxScore {
  team_code: string;
  pts: number;
  quarter_pts: number[];
  possessions: number;
  fgm: number;
  fga: number;
  fg3m: number;
  fg3a: number;
  ftm: number;
  fta: number;
  oreb: number;
  dreb: number;
  reb: number;
  ast: number;
  stl: number;
  blk: number;
  ba: number;
  tov: number;
  team_tov: number;
  pf: number;
  fd: number;
  fastbreak_pts: number;
  paint_pts: number;
  second_chance_pts: number;
  pts_off_turnovers: number;
  starter_pts: number;
  bench_pts: number;
  starter_reb: number;
  bench_reb: number;
  starter_ast: number;
  bench_ast: number;
  biggest_lead: number;
  lead_changes: number;
  times_tied: number;
  missed_terminal_ft: number;
  team_reb: number;
  lineup_stints: NBALineupStintBoxRow[];
  players: NBAPlayerBoxScore[];
}

export interface NBAPlayerDrawBoxSummary {
  player_id: number;
  name: string;
  team_code: string;
  is_home: boolean;
  position: string;
  started: boolean;
  minutes: number;
  minutes_mm_ss: string;
  dnp_reason: string | null;
  stint_count: number;
  pts: number;
  reb: number;
  oreb: number;
  dreb: number;
  ast: number;
  stl: number;
  blk: number;
  ba: number;
  tov: number;
  pf: number;
  fd: number;
  fgm: number;
  fga: number;
  fg2m: number;
  fg2a: number;
  fg3m: number;
  fg3a: number;
  ftm: number;
  fta: number;
  paint_pts: number;
  fastbreak_pts: number;
  second_chance_pts: number;
  unassisted_fgm: number;
  quarter_pts: number[];
  plus_minus: number;
  double_double: boolean;
  triple_double: boolean;
  dk_pts: number;
  fd_pts: number;
}

export interface NBASimGameScoreboxDraw {
  draw_idx: number;
  archetype: string | null;
  label: string;
  away_linescore: number[];
  home_linescore: number[];
  total_points: number;
  final_margin_home: number;
  overtime_played: boolean;
  periods_played: number;
  away_team: NBATeamBoxScore;
  home_team: NBATeamBoxScore;
  players?: NBAPlayerDrawBoxSummary[];
}

export interface NBASimScoreboxSection {
  archetypes: NBASimGameScoreboxDraw[];
  draws: NBASimGameScoreboxDraw[];
}

export interface NBAPossessionEvent {
  event_idx: number;
  period: number;
  period_label: string;
  clock_remaining_sec: number;
  clock_display: string;
  offense_team: string;
  defense_team: string;
  play_type: string;
  primary_player_id: number;
  primary_player_name: string;
  secondary_player_id: number | null;
  secondary_player_name: string | null;
  points_scored: number;
  home_score: number;
  away_score: number;
  description: string;
  play_family: string | null;
  shot_zone: string | null;
  home_on_court_ids: number[];
  away_on_court_ids: number[];
}

export interface NBASimTracedGame {
  draw_idx: number;
  archetype: string | null;
  label: string;
  scorebox: NBASimGameScoreboxDraw;
  possessions: NBAPossessionEvent[];
}

export interface NBATeamOffenseProfile {
  pace: number;
  off_rating: number;
  oreb_rate: number;
  transition_rate: number;
}

export interface NBATeamDefenseProfile {
  def_rating: number;
  rim_protection: number | null;
  perimeter_defense: number | null;
  ball_pressure: number | null;
  def_rebounding: number | null;
  foul_discipline: number | null;
  transition_defense_rating: number | null;
}

export interface NBAScheduleContextProfile {
  rest_days: number;
}

export interface NBASimScenarioTeamProfile {
  team_id: number;
  team_abbr: string;
  team_name: string;
  is_home: boolean;
  offense: NBATeamOffenseProfile;
  defense: NBATeamDefenseProfile;
  schedule: NBAScheduleContextProfile;
}

export interface NBAPlayerRotationProfile {
  role: string;
  minutes: number;
  minutes_cap: number;
  fouls_per_36: number;
  dnp_rate: number | null;
}

export interface NBAPlayerOffenseProfile {
  usage_rate: number;
  ast_rate: number;
  three_rate: number;
  ft_rate: number;
  two_pct: number;
  three_pct: number;
  ft_pct: number;
  tov_rate: number;
}

export interface NBAPlayerReboundDefenseProfile {
  oreb_rate: number;
  dreb_rate: number;
  stl_rate: number;
  blk_rate: number;
}

export interface NBAPlayerInjuryProfile {
  status: string;
  play_chance_pct: number;
  exit_rate: number | null;
}

export interface NBAPlayerPreTipSituationProfile {
  last10_minutes: number;
}

export interface NBASimPropInput {
  points: number | null;
  rebounds: number | null;
  assists: number | null;
  threes: number | null;
  pra: number | null;
  minutes: number | null;
  steals: number | null;
  blocks: number | null;
  double_double_prob: number | null;
  points_over_prob?: number | null;
  rebounds_over_prob?: number | null;
  assists_over_prob?: number | null;
  threes_over_prob?: number | null;
  pra_over_prob?: number | null;
  captured_age_min?: number | null;
  stale_line: boolean;
}

export interface NBASimScenarioPlayerProfile {
  player_id: number;
  player_name: string;
  team_id: number;
  team_abbr: string;
  position: string;
  depth_role: string;
  is_starter: boolean;
  active: boolean;
  dk_salary?: number | null;
  fd_salary?: number | null;
  rotation: NBAPlayerRotationProfile;
  offense: NBAPlayerOffenseProfile;
  boards_defense: NBAPlayerReboundDefenseProfile;
  injury: NBAPlayerInjuryProfile;
  recent: NBAPlayerPreTipSituationProfile;
  props?: NBASimPropInput | null;
}

export interface NBASimScenarioQuarters {
  away: number[];
  home: number[];
}

export interface NBASimScenarioScoreboard {
  quarters: NBASimScenarioQuarters;
  away_score: number;
  home_score: number;
  total_points: number;
  home_margin: number;
  overtime?: boolean;
  overtime_periods?: number;
}

export interface NBASimTeamBoxScoreStat {
  possessions?: number;
  pace?: number;
  offensive_rating?: number;
  defensive_rating?: number;
  pts?: number;
  fgm?: number;
  fga?: number;
  fg_pct?: number;
  fg2m?: number;
  fg2a?: number;
  fg2_pct?: number;
  fg3m?: number;
  fg3a?: number;
  fg3_pct?: number;
  ftm?: number;
  fta?: number;
  ft_pct?: number;
  efg_pct?: number;
  ts_pct?: number;
  oreb?: number;
  dreb?: number;
  reb?: number;
  ast?: number;
  stl?: number;
  blk?: number;
  ba?: number;
  tov?: number;
  team_tov?: number;
  team_reb?: number;
  pf?: number;
  fd?: number;
  paint_pts?: number;
  fastbreak_pts?: number;
  second_chance_pts?: number;
  pts_off_turnovers?: number;
  starter_pts?: number;
  bench_pts?: number;
  biggest_lead?: number;
  lead_changes?: number;
  times_tied?: number;
  pra?: number;
  dk_pts?: number;
  fd_pts?: number;
}

export interface NBASimPlayerBoxScoreStat {
  started?: boolean;
  minutes?: number;
  stint_count?: number;
  q1_minutes?: number;
  q2_minutes?: number;
  q3_minutes?: number;
  q4_minutes?: number;
  ot_minutes?: number;
  pts?: number;
  q1_pts?: number;
  q2_pts?: number;
  q3_pts?: number;
  q4_pts?: number;
  ot_pts?: number;
  fgm?: number;
  fga?: number;
  fg_pct?: number;
  fg2m?: number;
  fg2a?: number;
  fg2_pct?: number;
  fg3m?: number;
  fg3a?: number;
  fg3_pct?: number;
  ftm?: number;
  fta?: number;
  ft_pct?: number;
  ts_pct?: number;
  oreb?: number;
  dreb?: number;
  reb?: number;
  ast?: number;
  stl?: number;
  blk?: number;
  ba?: number;
  tov?: number;
  pf?: number;
  fd?: number;
  paint_pts?: number;
  fastbreak_pts?: number;
  second_chance_pts?: number;
  unassisted_fgm?: number;
  plus_minus?: number;
  fouled_out?: boolean;
  double_double?: boolean;
  triple_double?: boolean;
  pra?: number;
  dk_pts?: number;
  fd_pts?: number;
}

export interface NBASimPlayerCorrelations {
  scope: string;
  player_ids: string[];
  matrix: (number | null)[][];
}

export interface NBASimGamePossession {
  possession_number: number;
  period: number;
  start_clock: string;
  end_clock: string;
  duration_seconds: number;
  offense_team_id: number;
  defense_team_id: number;
  start_type: string;
  result: string;
  points_scored: number;
  play_count: number;
  fga: number;
  fgm: number;
  fg3a: number;
  fg3m: number;
  fta: number;
  ftm: number;
  turnover: boolean;
  offensive_rebounds: number;
  away_score_end: number;
  home_score_end: number;
  away_lineup_ids: number[];
  home_lineup_ids: number[];
  first_event_id: number;
  last_event_id: number;
}

export interface NBASimPlayStatCredit {
  player_id: number;
  team_id: number;
  stat: string;
  value: number;
  points: number;
}

export interface NBASimPlayByPlay {
  event_id: number;
  possession_number: number;
  period: number;
  clock: string;
  period_seconds_remaining: number;
  shot_clock: number;
  event_type: string;
  event_sub_type: string;
  team_id: number;
  opponent_team_id: number;
  player_id: number | null;
  player2_id: number | null;
  player3_id: number | null;
  points_scored: number;
  shot_value: number | null;
  shot_made: boolean | null;
  shot_distance_ft: number | null;
  coord_x: number | null;
  coord_y: number | null;
  shot_zone: string | null;
  is_fastbreak: boolean;
  is_second_chance: boolean;
  is_paint: boolean;
  in_penalty: boolean;
  away_score: number;
  home_score: number;
  away_fouls_qtr: number;
  home_fouls_qtr: number;
  away_timeouts_remaining: number;
  home_timeouts_remaining: number;
  away_lineup_ids: number[];
  home_lineup_ids: number[];
  play_stats: NBASimPlayStatCredit[];
  description: string;
}

export interface NBASimLineupStint {
  stint_number: number;
  team_id: number;
  is_home: boolean;
  period: number;
  player_ids: number[];
  package_tag: string;
  duration_seconds: number;
  possessions: number;
  points_for: number;
  points_against: number;
}

export interface NBASimGamePbp {
  game_possessions: NBASimGamePossession[];
  play_by_plays: NBASimPlayByPlay[];
  lineup_stints: NBASimLineupStint[];
}

export interface NBASimGameScenario {
  scenario_id: number;
  draw_idx: number;
  probability: number;
  cluster_draw_count: number;
  scoreboard: NBASimScenarioScoreboard;
  teams: Record<string, NBASimTeamBoxScoreStat>;
  players: Record<string, NBASimPlayerBoxScoreStat>;
  player_correlations: NBASimPlayerCorrelations;
  pbp?: NBASimGamePbp | null;
}

export interface NBASimScenariosSection {
  mode: string;
  n_monte_carlo_draws: number;
  scenarios_returned: number;
  probability_sum: number;
  teams: Record<string, NBASimScenarioTeamProfile>;
  players: Record<string, NBASimScenarioPlayerProfile>;
  scenarios: NBASimGameScenario[];
}

export interface NBASimSimulatedGame {
  sim_id: number;
  draw_idx: number;
  scoreboard: NBASimScenarioScoreboard;
  teams: Record<string, NBASimTeamBoxScoreStat>;
  players: Record<string, NBASimPlayerBoxScoreStat>;
  pbp?: NBASimGamePbp | null;
}

export interface NBASimSimulationsSection {
  n_monte_carlo_draws: number;
  simulations_returned: number;
  pbp_included: boolean;
  teams: Record<string, NBASimScenarioTeamProfile>;
  players: Record<string, NBASimScenarioPlayerProfile>;
  games: NBASimSimulatedGame[];
}

export interface NBASimGranularOutput {
  game_id: string;
  n_draws: number;
  seed: number;
  solve_elapsed_ms: number;
  sim_elapsed_ms: number;
  summary?: NBASimSummarySection | null;
  distributions?: NBASimDistributionsSection | null;
  scorebox?: NBASimScoreboxSection | null;
  pbp?: NBASimTracedGame[] | null;
  scenarios?: NBASimScenariosSection | null;
  simulations?: NBASimSimulationsSection | null;
}

export interface NBASimPublicRunResponse {
  matchup: NBAMatchupSummary;
  input_sha256: string;
  output: NBASimGranularOutput;
}

/**
 * Typed client for `/api/v1/sim/nba/*` and `/api/v1/nba/weeks`.
 */
export class SimNBAClient {
  constructor(private readonly opts: ResolvedStatApiOptions) {}

  /**
   * List available NBA weeks, matchup summaries, and `free_game_id` (`6029936`).
   * Free navigation endpoint (`GET /api/v1/nba/weeks`).
   *
   * Reference: https://stat-api.com/docs/api/nba/seasons/#endpoint-nba-seasons-weeks
   */
  async weeks(params?: NBAWeeksParams): Promise<NBASimWeeksResponse> {
    const qs = toQueryString(params as Record<string, unknown> | undefined);
    return (await fetchJson<NBASimWeeksResponse>(this.opts, `/api/v1/nba/weeks${qs}`)).data;
  }

  /**
   * Run N parallel Monte Carlo simulations (`POST /api/v1/sim/nba/run`).
   * Free in full for `game_id = 6029936` (`DET @ BOS`).
   *
   * Reference: https://stat-api.com/docs/api/sim/#endpoint-sim-nba-run
   */
  async run(request: NBASimPublicRunRequest): Promise<NBASimPublicRunResponse> {
    return (
      await fetchJson<NBASimPublicRunResponse>(this.opts, '/api/v1/sim/nba/run', {
        method: 'POST',
        body: JSON.stringify(request),
      })
    ).data;
  }
}

export { SimNBAClient as SimNBAResource };
