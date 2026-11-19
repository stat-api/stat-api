"""Typed Python resources and TypedDict definitions for NFL & NBA Simulators (SIM module)."""

# DO NOT EDIT — generated from schema/api/sim/nfl_sim.yml & nba_sim.yml by generate-sim-sdks.ts

from __future__ import annotations

from collections.abc import Mapping, Sequence
from typing import Any, TypedDict

from .transport import HttpTransport

NFLMatchupSummary = TypedDict(
    "NFLMatchupSummary",
    {
        "game_id": int,
        "season": int,
        "week": int,
        "season_type": str,
        "kickoff": str | None,
        "day": int | None,
        "home_team_id": int,
        "home_abbr": str,
        "away_team_id": int,
        "away_abbr": str,
        "spread_home": float,
        "game_total": float,
        "home_team_total": float,
        "away_team_total": float,
        "moneyline_home": int | None,
        "moneyline_away": int | None,
        "is_final": bool,
        "real_home_pts": int | None,
        "real_away_pts": int | None,
        "is_free": bool,
    },
)

NFLWeeksResponse = TypedDict(
    "NFLWeeksResponse",
    {
        "season": int,
        "available_seasons": list[int],
        "free_game_id": int,
        "weeks": list[int],
        "games": list[NFLMatchupSummary],
    },
)

NFLSimPersonnel = TypedDict(
    "NFLSimPersonnel",
    {
        "p11": float,
        "p12": float,
        "p21": float,
        "p13": float,
        "p22": float,
        "no_rb": float,
        "goal_line": float,
    },
)

NFLSimOline = TypedDict(
    "NFLSimOline",
    {
        "pass_block_grade": float,
        "run_block_grade": float,
        "sack_rate_allowed": float,
    },
)

NFLSimInjury = TypedDict(
    "NFLSimInjury",
    {
        "play_chance": float,
        "snap_limit": float,
        "injury_type": str,
        "practice": str,
    },
)

NFLSimDefense = TypedDict(
    "NFLSimDefense",
    {
        "pressure_rate": float,
        "sack_rate": float,
        "stuff_rate": float,
        "outside_cb_coverage": float,
        "slot_cb_coverage": float,
        "lb_safety_coverage": float,
        "missed_tackle_rate": float,
        "interception_rate": float,
        "forced_fumble_rate": float,
        "man_coverage_rate": float,
        "blitz_rate": float,
        "two_high_rate": float,
        "avg_box_count": float,
    },
)

NFLSimKicker = TypedDict(
    "NFLSimKicker",
    {
        "player_id": int,
        "fg_make_rate": float,
        "fg_range_yds": float,
        "xp_make_rate": float,
    },
)

NFLSimTeam = TypedDict(
    "NFLSimTeam",
    {
        "team_id": int,
        "team_abbr": str,
        "expected_plays": float,
        "neutral_pass_rate": float,
        "early_down_pass_rate": float,
        "red_zone_pass_rate": float,
        "red_zone_td_rate": float,
        "trailing_pass_boost": float,
        "no_huddle_rate": float,
        "fourth_down_go_rate": float,
        "personnel": NFLSimPersonnel,
        "oline": NFLSimOline,
        "defense": NFLSimDefense,
        "kicker": NFLSimKicker,
    },
)

NFLSimUsage = TypedDict(
    "NFLSimUsage",
    {
        "snap_share": float,
        "route_participation_rate": float,
        "slot_rate": float,
        "target_share": float,
        "targets_per_route": float,
        "red_zone_target_share": float,
        "carry_share": float,
        "short_yardage_carry_share": float,
        "red_zone_carry_share": float,
        "goal_line_carry_share": float,
        "four_minute_carry_share": float,
    },
)

NFLSimPassing = TypedDict(
    "NFLSimPassing",
    {
        "completion_rate": float,
        "time_to_throw_sec": float,
        "bad_throw_rate": float,
        "tight_window_rate": float,
        "pressure_sack_rate": float,
        "pressure_scramble_rate": float,
        "interception_rate": float,
        "designed_run_share": float,
    },
)

NFLSimReceiving = TypedDict(
    "NFLSimReceiving",
    {
        "catch_rate": float,
        "yards_per_target": float,
        "adot": float,
        "separation_yds": float,
        "contested_catch_rate": float,
        "drop_rate": float,
        "yac_per_catch": float,
        "zero_yac_rate": float,
        "broken_tackle_rate": float,
        "man_target_factor": float,
        "zone_target_factor": float,
        "screen_target_rate": float,
    },
)

NFLSimRushing = TypedDict(
    "NFLSimRushing",
    {
        "yards_per_carry": float,
        "yards_before_contact": float,
        "yards_after_contact": float,
        "broken_tackle_rate": float,
        "run_15plus_rate": float,
        "inside_run_rate": float,
        "fumble_rate": float,
    },
)

NFLSimAthleticism = TypedDict(
    "NFLSimAthleticism",
    {
        "speed_rating": float,
        "elusiveness_rating": float,
        "contact_rating": float,
    },
)

NFLSimInclude = TypedDict(
    "NFLSimInclude",
    {
        "summary": bool,
        "distributions": bool,
        "scorebox": bool,
        "pbp": bool,
        "scorebox_limit": int,  # unit: count; bounds: 0..10000
        "pbp_draws": list[int],
        "scenarios": bool,
        "scenarios_pbp": bool,
        "scenarios_limit": int,  # unit: count; bounds: 1..5000
        "simulations": bool,
        "simulations_pbp": bool,
        "simulations_limit": int | None,  # unit: count; bounds: 1..200
    },
    total=False,
)

NFLSimWeatherOverride = TypedDict(
    "NFLSimWeatherOverride",
    {
        "roof": str | None,  # values: outdoors | dome | retractable_closed | retractable_open
        "temp_f": float | None,  # unit: deg_F; bounds: -10..115; aliases: temperature_f
        "wind_mph": float | None,  # unit: mph; bounds: 0..55; aliases: wind_speed_mph
        "precip_in_hr": float | None,  # unit: in_hr; bounds: 0..0.5; aliases: precipitation_in_hr, precip_prob
    },
    total=False,
)

NFLSimTeamOverride = TypedDict(
    "NFLSimTeamOverride",
    {
        "out": list[int],
        "expected_plays": float | None,  # unit: plays; bounds: 48..82; aliases: projected_plays
        "neutral_pass_rate_pct": float | None,  # unit: percent_0_100; bounds: 35..78
    },
    total=False,
)

NFLSimPlayerOverride = TypedDict(
    "NFLSimPlayerOverride",
    {
        "player_id": int,
        "out": bool | None,
        "status": str | None,  # values: OUT | DOUBTFUL | QUESTIONABLE | PROBABLE | ACTIVE | HEALTHY | AVAILABLE | GTD
        "play_chance_pct": float | None,  # unit: percent_0_100; bounds: 0..100
        "snap_share_pct": float | None,  # unit: percent_0_100; bounds: 2..100
        "pass_yd": float | None,  # unit: yards; bounds: >= 0
        "pass_td": float | None,  # unit: count; bounds: >= 0
        "rush_yd": float | None,  # unit: yards; bounds: >= 0
        "rec_yd": float | None,  # unit: yards; bounds: >= 0
        "receptions": float | None,  # unit: count; bounds: >= 0
        "carries": float | None,  # unit: count; bounds: >= 0
        "td": float | None,  # unit: count; bounds: >= 0
    },
    total=False,
)

NFLSimPublicRunRequest = TypedDict(
    "NFLSimPublicRunRequest",
    {
        "game_id": int,  # bounds: >= 1
        "n_draws": int,  # unit: draws; bounds: 50..10000; default: 2500; aliases: n_runs, n_sims
        "seed": int,  # default: 20261028
        "include": NFLSimInclude,  # aliases: granularity
        "scenarios": int | None,  # unit: count; bounds: 1..5000
        "scenarios_pbp": bool | None,
        "simulations": bool | None,
        "simulations_pbp": bool | None,
        "simulations_limit": int | None,  # unit: count; bounds: 1..200
        "home_score": float | None,  # unit: points; bounds: 6..48
        "away_score": float | None,  # unit: points; bounds: 6..48
        "total": float | None,  # unit: points; bounds: 24..76
        "home_spread": float | None,  # unit: points; bounds: -28..28
        "home_win_pct": float | None,  # unit: percent_0_100; bounds: 2..98
        "mode": str | None,  # values: all | top_props | game_lines | none
        "weather": NFLSimWeatherOverride | None,
        "home": NFLSimTeamOverride | None,
        "away": NFLSimTeamOverride | None,
        "players": list[NFLSimPlayerOverride],
    },
    total=False,
)

NFLSimTeamSummary = TypedDict(
    "NFLSimTeamSummary",
    {
        "team_id": int,
        "is_home": bool,
        "vegas_team_total": float,
        "points_mean": float,
        "points_sd": float,
        "points_p10": float,
        "points_p50": float,
        "points_p90": float,
        "q1_points_mean": float,
        "q2_points_mean": float,
        "q3_points_mean": float,
        "q4_points_mean": float,
        "ot_points_mean": float,
        "win_prob": float,
        "plays_mean": float,
        "pass_att_mean": float,
        "pass_cmp_mean": float,
        "pass_yd_mean": float,
        "pass_td_mean": float,
        "int_mean": float,
        "rush_att_mean": float,
        "rush_yd_mean": float,
        "rush_td_mean": float,
        "sacks_taken_mean": float,
        "drives_mean": float,
        "drive_td_rate": float,
        "drive_fg_rate": float,
        "drive_punt_rate": float,
        "drive_turnover_rate": float,
        "third_down_conv_rate": float,
        "fourth_down_conv_rate": float,
        "dst_sacks_mean": float,
        "dst_int_mean": float,
        "dst_fumbles_rec_mean": float,
        "dst_td_mean": float,
        "dst_pts_allowed_mean": float,
    },
)

NFLSimPlayerSummary = TypedDict(
    "NFLSimPlayerSummary",
    {
        "player_id": int,
        "team_id": int,
        "is_home": bool,
        "position": str,
        "depth_role": str,
        "market_tier": str,
        "p_active": float,
        "snaps_mean": float,
        "snaps_p50": float,
        "routes_mean": float,
        "pass_cmp_mean": float,
        "pass_att_mean": float,
        "pass_yd_mean": float,
        "pass_yd_sd": float,
        "pass_yd_p10": float,
        "pass_yd_p50": float,
        "pass_yd_p90": float,
        "pass_td_mean": float,
        "int_mean": float,
        "longest_pass_p50": float,
        "carries_mean": float,
        "designed_rushes_mean": float,
        "scrambles_mean": float,
        "rush_yd_mean": float,
        "rush_yd_sd": float,
        "rush_yd_p10": float,
        "rush_yd_p50": float,
        "rush_yd_p90": float,
        "rush_td_mean": float,
        "longest_rush_p50": float,
        "targets_mean": float,
        "receptions_mean": float,
        "rec_yd_mean": float,
        "rec_yd_sd": float,
        "rec_yd_p10": float,
        "rec_yd_p50": float,
        "rec_yd_p90": float,
        "rec_td_mean": float,
        "air_yd_mean": float,
        "yac_yd_mean": float,
        "longest_rec_p50": float,
        "fg_made_mean": float,
        "fg_att_mean": float,
        "xp_made_mean": float,
        "kick_pts_mean": float,
        "fumbles_lost_mean": float,
        "p_anytime_td": float,
        "p_2plus_td": float,
        "p_pass_300yd": float,
        "p_rush_100yd": float,
        "p_rec_100yd": float,
        "vegas_pass_yd": float | None,
        "vegas_pass_att": float | None,
        "vegas_rush_yd": float | None,
        "vegas_carries": float | None,
        "vegas_rec_yd": float | None,
        "vegas_receptions": float | None,
        "vegas_td_prob": float | None,
    },
)

NFLSimSummarySection = TypedDict(
    "NFLSimSummarySection",
    {
        "game_id": int,
        "season": int,
        "week": int,
        "n_draws": int,
        "seed": int,
        "has_vegas_line": bool,
        "vegas_spread_home": float,
        "vegas_total": float,
        "sim_total_mean": float,
        "sim_total_sd": float,
        "sim_spread_home_mean": float,
        "sim_spread_home_sd": float,
        "home_win_prob": float,
        "away_win_prob": float,
        "tie_prob": float,
        "home_cover_prob": float,
        "over_prob": float,
        "overtime_rate": float,
        "solve_elapsed_ms": float,
        "sim_elapsed_ms": float,
        "conservation_ok": bool,
        "c0_max_diff": float,
        "home_team": NFLSimTeamSummary,
        "away_team": NFLSimTeamSummary,
        "players": list[NFLSimPlayerSummary],
    },
)

NFLHistogramBin = TypedDict(
    "NFLHistogramBin",
    {
        "x_min": float,
        "x_max": float,
        "x_mid": float,
        "count": int,
        "prob": float,
        "cum_prob": float,
        "gaussian_density": float,
        "kde_density": float,
        "sample_draw_idx": int | None,
    },
)

NFLSimBellCurve = TypedDict(
    "NFLSimBellCurve",
    {
        "metric": str,
        "label": str,
        "discrete": bool,
        "n_draws": int,
        "mean": float,
        "sd": float,
        "skew": float,
        "min": float,
        "max": float,
        "p05": float,
        "p10": float,
        "p25": float,
        "p50": float,
        "p75": float,
        "p90": float,
        "p95": float,
        "vegas_line": float | None,
        "p_over_vegas": float | None,
        "bins": list[NFLHistogramBin],
    },
)

NFLPlayerBellCurves = TypedDict(
    "NFLPlayerBellCurves",
    {
        "player_id": int,
        "team_id": int,
        "position": str,
        "depth_role": str,
        "pass_yd": NFLSimBellCurve | None,
        "rush_yd": NFLSimBellCurve | None,
        "rec_yd": NFLSimBellCurve | None,
        "receptions": NFLSimBellCurve | None,
        "targets": NFLSimBellCurve | None,
        "carries": NFLSimBellCurve | None,
    },
)

NFLSimDistributionsSection = TypedDict(
    "NFLSimDistributionsSection",
    {
        "total_points": NFLSimBellCurve,
        "spread_margin_home": NFLSimBellCurve,
        "home_points": NFLSimBellCurve,
        "away_points": NFLSimBellCurve,
        "combined_plays": NFLSimBellCurve,
        "home_pass_yd": NFLSimBellCurve,
        "away_pass_yd": NFLSimBellCurve,
        "home_rush_yd": NFLSimBellCurve,
        "away_rush_yd": NFLSimBellCurve,
        "players": list[NFLPlayerBellCurves],
    },
)

NFLTeamGameBoxScore = TypedDict(
    "NFLTeamGameBoxScore",
    {
        "points": int,
        "q1_points": int,
        "q2_points": int,
        "q3_points": int,
        "q4_points": int,
        "ot_points": int,
        "plays": int,
        "pass_att": int,
        "pass_cmp": int,
        "pass_yd": int,
        "pass_td": int,
        "interceptions_thrown": int,
        "sacks_taken": int,
        "sack_yd_lost": int,
        "rush_att": int,
        "rush_yd": int,
        "rush_td": int,
        "first_downs": int,
        "third_down_att": int,
        "third_down_conv": int,
        "fourth_down_att": int,
        "fourth_down_conv": int,
        "drives": int,
        "drive_tds": int,
        "drive_fgs": int,
        "drive_missed_fgs": int,
        "drive_punts": int,
        "drive_turnovers": int,
        "drive_downs": int,
        "drive_end_half": int,
        "fg_att": int,
        "fg_made": int,
        "xp_att": int,
        "xp_made": int,
        "two_pt_att": int,
        "two_pt_made": int,
        "penalties": int,
        "penalty_yds": int,
        "time_of_possession_sec": int,
        "throwaways": int,
        "spikes": int,
        "batted_passes": int,
        "oob_untackled_plays": int,
        "special_teams_returns": int,
        "kickoffs_total": int,
        "kickoff_returns": int,
        "kickoff_start_yardline_sum": int,
        "trailing_10plus_dropbacks": int,
        "trailing_10plus_plays": int,
        "neutral_dropbacks": int,
        "neutral_plays": int,
        "p11_snaps": int,
        "p12_snaps": int,
        "p21_snaps": int,
        "no_huddle_snaps": int,
        "unassisted_team_sacks": int,
        "dst_pressures": int,
        "pick_sixes_scored": int,
        "fumble_ret_tds_scored": int,
        "dst_sacks": int,
        "dst_interceptions": int,
        "dst_fumbles_recovered": int,
        "dst_defensive_tds": int,
        "dst_safeties": int,
        "dst_points_allowed": int,
    },
)

NFLPlayerDrawBoxSummary = TypedDict(
    "NFLPlayerDrawBoxSummary",
    {
        "player_id": int,
        "team_id": int,
        "active": bool,
        "snaps": int,
        "routes": int,
        "pass_cmp": int,
        "pass_att": int,
        "pass_yd": int,
        "pass_td": int,
        "interception": int,
        "sacks_taken": int,
        "carries": int,
        "designed_rushes": int,
        "scrambles": int,
        "rush_yd": int,
        "rush_td": int,
        "targets": int,
        "receptions": int,
        "rec_yd": int,
        "rec_td": int,
        "air_yd": int,
        "yac_yd": int,
        "fumbles_lost": int,
        "two_pt_conv": int,
        "fg_made": int,
        "fg_att": int,
        "xp_made": int,
        "xp_att": int,
        "longest_pass": int,
        "longest_rush": int,
        "longest_rec": int,
    },
)

NFLSimGameScoreboxDraw = TypedDict(
    "NFLSimGameScoreboxDraw",
    {
        "draw_idx": int,
        "archetype": str | None,
        "label": str,
        "away_linescore": list[int],
        "home_linescore": list[int],
        "total_points": int,
        "final_margin_home": int,
        "q4_margin_home": int,
        "overtime_played": bool,
        "away_team": NFLTeamGameBoxScore,
        "home_team": NFLTeamGameBoxScore,
        "players": list[NFLPlayerDrawBoxSummary],
    },
)

NFLSimScoreboxSection = TypedDict(
    "NFLSimScoreboxSection",
    {
        "archetypes": list[NFLSimGameScoreboxDraw],
        "draws": list[NFLSimGameScoreboxDraw],
    },
)

NFLSimDriveSummary = TypedDict(
    "NFLSimDriveSummary",
    {
        "drive_number": int,
        "posteam_is_home": bool,
        "start_quarter": int,
        "start_clock": str,
        "start_yardline_100": int,
        "end_yardline_100": int,
        "plays": int,
        "net_yards": int,
        "first_downs": int,
        "result": str,
        "home_score_after": int,
        "away_score_after": int,
    },
)

NFLSimPlayEvent = TypedDict(
    "NFLSimPlayEvent",
    {
        "game_id": int,
        "draw_idx": int,
        "drive_number": int,
        "play_number": int,
        "quarter": int,
        "half_seconds_remaining": int,
        "game_seconds_remaining": int,
        "posteam_is_home": bool,
        "down": int,
        "ydstogo": int,
        "yardline_100": int,
        "score_diff": int,
        "home_score_before": int,
        "away_score_before": int,
        "home_score_after": int,
        "away_score_after": int,
        "play_type": str,
        "personnel": int,
        "passer_id": int,
        "rusher_id": int,
        "receiver_id": int,
        "yards_gained": int,
        "air_yards": int,
        "yac_yards": int,
        "is_first_down": bool,
        "is_touchdown": bool,
        "is_turnover": bool,
        "is_sack": bool,
        "is_complete": bool,
        "is_fg_good": bool,
        "is_two_pt_good": bool,
    },
)

NFLSimPlayRecord = TypedDict(
    "NFLSimPlayRecord",
    {
        "event": NFLSimPlayEvent,
        "clock_display": str,
        "down_distance_display": str,
        "yardline_display": str,
        "personnel_display": str,
        "description": str,
    },
)

NFLSimTracedGame = TypedDict(
    "NFLSimTracedGame",
    {
        "draw_idx": int,
        "archetype": str | None,
        "label": str,
        "scorebox": NFLSimGameScoreboxDraw,
        "drives": list[NFLSimDriveSummary],
        "plays": list[NFLSimPlayRecord],
    },
)

NFLSimScenarioPlayerProfile = TypedDict(
    "NFLSimScenarioPlayerProfile",
    {
        "player_id": int,
        "player_name": str,
        "team_id": int,
        "team_abbr": str,
        "position": str,
        "depth_role": str,
        "active": bool,
        "injury_status": str | None,
        "usage": NFLSimUsage,
        "passing": NFLSimPassing | None,
        "receiving": NFLSimReceiving,
        "rushing": NFLSimRushing,
        "athleticism": NFLSimAthleticism,
        "injury": NFLSimInjury,
    },
)

NFLSimScenarioQuarters = TypedDict(
    "NFLSimScenarioQuarters",
    {
        "away": list[int],
        "home": list[int],
    },
)

NFLSimScenarioScoreboard = TypedDict(
    "NFLSimScenarioScoreboard",
    {
        "quarters": NFLSimScenarioQuarters,
        "away_score": int,
        "home_score": int,
        "total_points": int,
        "home_margin": int,
        "overtime": bool | None,
    },
)

NFLSimTeamBoxScoreStat = TypedDict(
    "NFLSimTeamBoxScoreStat",
    {
        "points": int | None,
        "plays": int | None,
        "first_downs": int | None,
        "total_yd": int | None,
        "pass_cmp": int | None,
        "pass_att": int | None,
        "pass_yd": int | None,
        "pass_td": int | None,
        "int": int | None,
        "sacks_taken": int | None,
        "sack_yd_lost": int | None,
        "rush_att": int | None,
        "rush_yd": int | None,
        "rush_td": int | None,
        "fumbles_lost": int | None,
        "drives": int | None,
        "drive_tds": int | None,
        "fg_made": int | None,
        "fg_att": int | None,
        "xp_made": int | None,
        "xp_att": int | None,
        "two_pt_made": int | None,
        "two_pt_att": int | None,
        "def_sacks": int | None,
        "def_int": int | None,
        "def_fumble_rec": int | None,
        "def_td": int | None,
        "safety": int | None,
        "points_allowed": int | None,
    },
)

NFLSimPlayerBoxScoreStat = TypedDict(
    "NFLSimPlayerBoxScoreStat",
    {
        "snaps": int | None,
        "routes": int | None,
        "pass_cmp": int | None,
        "pass_att": int | None,
        "pass_yd": int | None,
        "pass_td": int | None,
        "int": int | None,
        "sacks_taken": int | None,
        "sack_yd_lost": int | None,
        "longest_pass": int | None,
        "rush_att": int | None,
        "designed_rushes": int | None,
        "scrambles": int | None,
        "rush_yd": int | None,
        "rush_td": int | None,
        "longest_rush": int | None,
        "targets": int | None,
        "rec": int | None,
        "rec_yd": int | None,
        "rec_td": int | None,
        "air_yd": int | None,
        "yac_yd": int | None,
        "longest_rec": int | None,
        "fumbles_lost": int | None,
        "two_pt_conv": int | None,
        "fg_made": int | None,
        "fg_att": int | None,
        "xp_made": int | None,
        "xp_att": int | None,
        "solo_tackles": int | None,
        "ast_tackles": int | None,
        "tfl": int | None,
        "solo_sacks": int | None,
        "split_sacks": int | None,
        "sack_yd": int | None,
        "qb_hits": int | None,
        "pressures": int | None,
        "targets_coverage": int | None,
        "receptions_allowed": int | None,
        "rec_yd_allowed": int | None,
        "passes_defended": int | None,
        "def_int": int | None,
        "forced_fumbles": int | None,
        "fumble_rec": int | None,
        "def_td": int | None,
    },
)

NFLSimPlayerCorrelations = TypedDict(
    "NFLSimPlayerCorrelations",
    {
        "player_ids": list[int],
        "matrix": list[list[float]],
    },
)

NFLSimGameDrive = TypedDict(
    "NFLSimGameDrive",
    {
        "drive_number": int,
        "possession_team_id": int,
        "quarter_start": int,
        "quarter_end": int,
        "clock_start": str,
        "clock_end": str,
        "time_of_possession": str,
        "start_yardline": str,
        "end_yardline": str,
        "start_reason": str,
        "result": str,
        "play_count": int,
        "net_yards": int,
        "first_downs": int,
        "inside_20": bool,
        "scoring_drive": bool,
        "first_play_id": int,
        "last_play_id": int,
    },
)

NFLSimPlaySituation = TypedDict(
    "NFLSimPlaySituation",
    {
        "quarter": int,
        "clock": str,
        "quarter_seconds_remaining": int,
        "possession_team_id": int,
        "defensive_team_id": int,
        "down": int | None,
        "ydstogo": int,
        "yardline_100": int,
        "yardline": str,
        "goal_to_go": bool,
    },
)

NFLSimPlayStat = TypedDict(
    "NFLSimPlayStat",
    {
        "sequence": int,
        "player_id": int,
        "team_id": int,
        "category": str,
        "role": str,
        "pass_att": int | None,
        "pass_cmp": int | None,
        "pass_yd": int | None,
        "air_yd": int | None,
        "pass_td": int | None,
        "int_thrown": int | None,
        "sacks_taken": int | None,
        "sack_yd_lost": int | None,
        "rush_att": int | None,
        "rush_yd": int | None,
        "rush_td": int | None,
        "is_scramble": bool | None,
        "targets": int | None,
        "rec": int | None,
        "rec_yd": int | None,
        "yac_yd": int | None,
        "rec_td": int | None,
        "fumbles": int | None,
        "fumbles_lost": int | None,
        "two_pt_conv": int | None,
        "fg_att": int | None,
        "fg_made": int | None,
        "xp_att": int | None,
        "xp_made": int | None,
        "kick_distance": int | None,
        "solo_tackles": int | None,
        "ast_tackles": int | None,
        "solo_sacks": int | None,
        "split_sacks": int | None,
        "sack_yd": int | None,
        "tfl": int | None,
        "qb_hits": int | None,
        "pressures": int | None,
        "targets_coverage": int | None,
        "receptions_allowed": int | None,
        "rec_yd_allowed": int | None,
        "passes_defended": int | None,
        "def_int": int | None,
        "forced_fumbles": int | None,
        "fumble_rec": int | None,
        "def_td": int | None,
        "dk_pts": float | None,
        "fd_pts": float | None,
    },
)

NFLSimPlayByPlay = TypedDict(
    "NFLSimPlayByPlay",
    {
        "play_id": int,
        "drive_number": int,
        "play_type": str,
        "description": str,
        "start_situation": NFLSimPlaySituation,
        "end_situation": NFLSimPlaySituation,
        "offense_personnel": str | None,
        "defense_package": str | None,
        "defenders_in_box": int | None,
        "shotgun": bool,
        "no_huddle": bool,
        "qb_dropback": bool,
        "qb_scramble": bool,
        "was_pressure": bool,
        "pass_length": str | None,
        "pass_location": str | None,
        "air_yards": int | None,
        "yards_after_catch": int | None,
        "run_location": str | None,
        "run_gap": str | None,
        "yards_gained": int,
        "drive_net_yards": int,
        "first_down": bool,
        "touchdown": bool,
        "turnover": bool,
        "sack": bool,
        "qb_hit": bool,
        "tackled_for_loss": bool,
        "away_score_before": int,
        "home_score_before": int,
        "away_score_after": int,
        "home_score_after": int,
        "on_field_offense_ids": list[int],
        "route_runner_ids": list[int],
        "play_stats": list[NFLSimPlayStat],
    },
)

NFLSimGamePbp = TypedDict(
    "NFLSimGamePbp",
    {
        "drives": list[NFLSimGameDrive],
        "plays": list[NFLSimPlayByPlay],
    },
)

NFLSimGameScenario = TypedDict(
    "NFLSimGameScenario",
    {
        "scenario_id": int,
        "draw_idx": int,
        "probability": float,
        "cluster_draw_count": int,
        "scoreboard": NFLSimScenarioScoreboard,
        "teams": dict[str, NFLSimTeamBoxScoreStat],
        "players": dict[str, NFLSimPlayerBoxScoreStat],
        "player_correlations": NFLSimPlayerCorrelations,
        "pbp": NFLSimGamePbp | None,
    },
)

NFLSimScenariosSection = TypedDict(
    "NFLSimScenariosSection",
    {
        "mode": str,
        "n_monte_carlo_draws": int,
        "scenarios_returned": int,
        "probability_sum": float,
        "teams": dict[str, NFLSimTeam],
        "players": dict[str, NFLSimScenarioPlayerProfile],
        "scenarios": list[NFLSimGameScenario],
    },
)

NFLSimSimulatedGame = TypedDict(
    "NFLSimSimulatedGame",
    {
        "sim_id": int,
        "draw_idx": int,
        "scoreboard": NFLSimScenarioScoreboard,
        "teams": dict[str, NFLSimTeamBoxScoreStat],
        "players": dict[str, NFLSimPlayerBoxScoreStat],
        "pbp": NFLSimGamePbp | None,
    },
)

NFLSimSimulationsSection = TypedDict(
    "NFLSimSimulationsSection",
    {
        "n_simulations": int,
        "pbp_included": bool,
        "games": list[NFLSimSimulatedGame],
    },
)

NFLSimGranularOutput = TypedDict(
    "NFLSimGranularOutput",
    {
        "game_id": int,
        "n_draws": int,
        "seed": int,
        "solve_elapsed_ms": float,
        "sim_elapsed_ms": float,
        "summary": NFLSimSummarySection | None,
        "distributions": NFLSimDistributionsSection | None,
        "scorebox": NFLSimScoreboxSection | None,
        "pbp": list[NFLSimTracedGame] | None,
        "scenarios": NFLSimScenariosSection | None,
        "simulations": NFLSimSimulationsSection | None,
    },
)

NFLSimPublicRunResponse = TypedDict(
    "NFLSimPublicRunResponse",
    {
        "matchup": NFLMatchupSummary,
        "input_sha256": str,
        "output": NFLSimGranularOutput,
    },
)


class SimNFLResource:
    """Client resource for `/api/v1/sim/nfl/*` and `/api/v1/nfl/weeks`."""

    def __init__(self, transport: HttpTransport) -> None:
        self._transport = transport

    def weeks(self, *, season: int | None = None) -> NFLWeeksResponse:
        """List available NFL weeks, matchup summaries, and `free_game_id` (`43418`).

        Reference: https://stat-api.com/docs/api/nfl/seasons/#endpoint-nfl-seasons-weeks
        Tutorial: recipes 1, 2, 3, 4, 5 of https://stat-api.com/docs/cookbook/sim/
        """
        body, _ = self._transport.request("/api/v1/nfl/weeks", {"season": season})
        resp: NFLWeeksResponse = body
        return resp

    def run(
        self,
        *,
        game_id: int,
        n_draws: int | None = None,
        seed: int | None = None,
        include: NFLSimInclude | Mapping[str, object] | None = None,
        scenarios: int | None = None,
        scenarios_pbp: bool | None = None,
        simulations: bool | None = None,
        simulations_pbp: bool | None = None,
        simulations_limit: int | None = None,
        home_score: float | None = None,
        away_score: float | None = None,
        total: float | None = None,
        home_spread: float | None = None,
        home_win_pct: float | None = None,
        mode: str | None = None,
        weather: NFLSimWeatherOverride | Mapping[str, object] | None = None,
        home: NFLSimTeamOverride | Mapping[str, object] | None = None,
        away: NFLSimTeamOverride | Mapping[str, object] | None = None,
        players: Sequence[NFLSimPlayerOverride | Mapping[str, object]] | None = None,
    ) -> NFLSimPublicRunResponse:
        """Run N parallel Monte Carlo simulations (`POST /api/v1/sim/nfl/run`).

        Reference: https://stat-api.com/docs/api/sim/#endpoint-sim-nfl-run
        Tutorial: recipes 1, 2, 3, 4, 5 of https://stat-api.com/docs/cookbook/sim/
        """
        payload: dict[str, object] = {}
        payload["game_id"] = int(game_id)
        if n_draws is not None:
            payload["n_draws"] = n_draws
        if seed is not None:
            payload["seed"] = seed
        if include is not None:
            payload["include"] = dict(include)
        if scenarios is not None:
            payload["scenarios"] = scenarios
        if scenarios_pbp is not None:
            payload["scenarios_pbp"] = scenarios_pbp
        if simulations is not None:
            payload["simulations"] = simulations
        if simulations_pbp is not None:
            payload["simulations_pbp"] = simulations_pbp
        if simulations_limit is not None:
            payload["simulations_limit"] = simulations_limit
        if home_score is not None:
            payload["home_score"] = home_score
        if away_score is not None:
            payload["away_score"] = away_score
        if total is not None:
            payload["total"] = total
        if home_spread is not None:
            payload["home_spread"] = home_spread
        if home_win_pct is not None:
            payload["home_win_pct"] = home_win_pct
        if mode is not None:
            payload["mode"] = mode
        if weather is not None:
            payload["weather"] = dict(weather)
        if home is not None:
            payload["home"] = dict(home)
        if away is not None:
            payload["away"] = dict(away)
        if players is not None:
            payload["players"] = [dict(item) for item in players]
        body, _ = self._transport.post("/api/v1/sim/nfl/run", payload)
        resp: NFLSimPublicRunResponse = body
        return resp

NBAMatchupSummary = TypedDict(
    "NBAMatchupSummary",
    {
        "game_id": int,
        "season": int,
        "week": int,
        "season_type": str,  # values: regular_season | in_season_tournament | play_in | postseason
        "kickoff": str | None,
        "day": int | None,
        "home_team_id": int,
        "home_abbr": str,
        "away_team_id": int,
        "away_abbr": str,
        "spread_home": float,
        "game_total": float,
        "home_team_total": float,
        "away_team_total": float,
        "moneyline_home": int | None,
        "moneyline_away": int | None,
        "is_final": bool,
        "real_home_pts": int | None,
        "real_away_pts": int | None,
        "is_free": bool,
    },
)

NBASimWeeksResponse = TypedDict(
    "NBASimWeeksResponse",
    {
        "season": int,
        "available_seasons": list[int],
        "free_game_id": int,
        "weeks": list[int],
        "games": list[NBAMatchupSummary],
    },
)

NBASimInclude = TypedDict(
    "NBASimInclude",
    {
        "summary": bool,
        "distributions": bool,
        "scorebox": bool,
        "pbp": bool,
        "scenarios": bool,
        "scenarios_pbp": bool,
        "simulations": bool,
        "simulations_pbp": bool,
        "scenarios_limit": int,  # unit: count; bounds: 1..100
        "simulations_limit": int | None,  # unit: count; bounds: 1..200
        "scorebox_limit": int,  # unit: count; bounds: 0..10000
        "pbp_draws": list[int],
    },
    total=False,
)

NBASimTeamOverride = TypedDict(
    "NBASimTeamOverride",
    {
        "out": list[int],
        "pace": float | None,  # unit: possessions_per_48; bounds: 75..125
        "off_rating": float | None,  # unit: pts_per_100; bounds: 80..145
        "def_rating": float | None,  # unit: pts_per_100; bounds: 80..145
        "rest_days": int | None,  # unit: days; bounds: 0..14
    },
    total=False,
)

NBASimPlayerOverride = TypedDict(
    "NBASimPlayerOverride",
    {
        "player_id": int,
        "out": bool | None,
        "status": str | None,  # values: OUT | DOUBTFUL | QUESTIONABLE | PROBABLE | ACTIVE | HEALTHY | AVAILABLE | GTD
        "play_chance_pct": float | None,  # unit: percent_0_100; bounds: 0..100
        "role": str | None,  # values: starter | sixth_man | rotation | bench | out
        "minutes": float | None,  # unit: minutes; bounds: 0..48
        "minutes_cap": float | None,  # unit: minutes; bounds: 0..48
        "points": float | None,  # unit: points; bounds: >= 0
        "rebounds": float | None,  # unit: rebounds; bounds: >= 0
        "assists": float | None,  # unit: assists; bounds: >= 0
        "threes": float | None,  # unit: threes; bounds: >= 0
        "pra": float | None,  # unit: pra; bounds: >= 0
        "steals": float | None,  # unit: steals; bounds: >= 0
        "blocks": float | None,  # unit: blocks; bounds: >= 0
        "double_double_prob": float | None,  # unit: ratio_0_1; bounds: 0..1
    },
    total=False,
)

NBASimPublicRunRequest = TypedDict(
    "NBASimPublicRunRequest",
    {
        "game_id": int,  # bounds: >= 1
        "n_draws": int,  # unit: draws; bounds: 50..10000; default: 2500; aliases: n_runs, n_sims
        "seed": int,  # default: 20261028
        "include": NBASimInclude,  # aliases: granularity
        "scenarios": int | None,  # unit: count; bounds: 1..100
        "scenarios_pbp": bool | None,
        "simulations": bool | None,
        "simulations_pbp": bool | None,
        "simulations_limit": int | None,  # unit: count; bounds: 1..200
        "home_score": float | None,  # unit: points; bounds: 50..200
        "away_score": float | None,  # unit: points; bounds: 50..200
        "total": float | None,  # unit: points; bounds: 150..300
        "home_spread": float | None,  # unit: points; bounds: -40..40
        "home_win_pct": float | None,  # unit: percent_0_100; bounds: 0.5..99.5
        "mode": str | None,  # values: all | top_props | game_lines | none
        "game_pace_pct": float | None,  # unit: percent_baseline_100; bounds: 50..150
        "ref_whistle": float | None,  # unit: percent_baseline_100; bounds: 50..150
        "blowout_pulls": float | None,  # unit: percent_baseline_100; bounds: 0..250
        "home": NBASimTeamOverride | None,
        "away": NBASimTeamOverride | None,
        "players": list[NBASimPlayerOverride],
    },
    total=False,
)

NBASimQuantileProfile = TypedDict(
    "NBASimQuantileProfile",
    {
        "min": float,
        "p01": float,
        "p05": float,
        "p10": float,
        "p25": float,
        "p50": float,
        "p75": float,
        "p90": float,
        "p95": float,
        "p99": float,
        "p99_5": float,
        "max": float,
        "skew": float,
    },
)

NBAVegasCalibrationDiagnostics = TypedDict(
    "NBAVegasCalibrationDiagnostics",
    {
        "vegas_mode": str,
        "iterations_run": int,
        "target_home_pts": float | None,
        "target_away_pts": float | None,
        "achieved_pilot_home_pts": float,
        "achieved_pilot_away_pts": float,
        "home_efg_tilt": float,
        "away_efg_tilt": float,
        "rank_inversion_count": int,
    },
)

NBASimTeamSummary = TypedDict(
    "NBASimTeamSummary",
    {
        "team_code": str,
        "team_name": str,
        "is_home": bool,
        "vegas_team_total": float | None,
        "points_mean": float,
        "points_sd": float,
        "points_p01": float,
        "points_p05": float,
        "points_p10": float,
        "points_p25": float,
        "points_p50": float,
        "points_p75": float,
        "points_p90": float,
        "points_p95": float,
        "points_p99": float,
        "points_quantiles": NBASimQuantileProfile,
        "q1_points_mean": float,
        "q2_points_mean": float,
        "q3_points_mean": float,
        "q4_points_mean": float,
        "ot_points_mean": float,
        "win_prob": float,
        "possessions_mean": float,
        "offensive_rating_mean": float,
        "fg_pct_mean": float,
        "fg2_pct_mean": float,
        "fg3_pct_mean": float,
        "ft_pct_mean": float,
        "fgm_mean": float,
        "fga_mean": float,
        "fg2m_mean": float,
        "fg2a_mean": float,
        "fg3m_mean": float,
        "fg3a_mean": float,
        "ftm_mean": float,
        "fta_mean": float,
        "reb_mean": float,
        "oreb_mean": float,
        "dreb_mean": float,
        "ast_mean": float,
        "stl_mean": float,
        "blk_mean": float,
        "ba_mean": float,
        "tov_mean": float,
        "pf_mean": float,
        "fd_mean": float,
        "fastbreak_pts_mean": float,
        "paint_pts_mean": float,
        "second_chance_pts_mean": float,
        "pts_off_turnovers_mean": float,
        "starter_pts_mean": float,
        "bench_pts_mean": float,
        "biggest_lead_mean": float,
        "lead_changes_mean": float,
        "times_tied_mean": float,
        "pra_mean": float,
        "dk_pts_mean": float,
        "fd_pts_mean": float,
    },
)

NBAScoreBinStatProfile = TypedDict(
    "NBAScoreBinStatProfile",
    {
        "quintile_label": str,
        "draw_count": int,
        "game_pts_mean": float,
        "game_fgm_mean": float,
        "game_fg3m_mean": float,
        "game_ftm_mean": float,
        "game_reb_mean": float,
        "game_ast_mean": float,
        "game_pra_mean": float,
        "game_dk_pts_mean": float,
        "game_fd_pts_mean": float,
        "players_35plus_dk_mean": float,
    },
)

NBASimPlayerSummary = TypedDict(
    "NBASimPlayerSummary",
    {
        "player_id": int,
        "name": str,
        "team_code": str,
        "is_home": bool,
        "position": str,
        "rotation_role": str,
        "role_label": str,
        "rotation_archetype_label": str,
        "starter_prob": float,
        "active_prob": float,
        "coach_dnp_prob": float,
        "dk_salary": int | None,
        "fd_salary": int | None,
        "projected_minutes": float,
        "minutes_mean": float,
        "minutes_sd": float,
        "minutes_p10": float,
        "minutes_p50": float,
        "minutes_p90": float,
        "minutes_quantiles": NBASimQuantileProfile,
        "q1_minutes_mean": float,
        "q2_minutes_mean": float,
        "q3_minutes_mean": float,
        "q4_minutes_mean": float,
        "ot_minutes_mean": float,
        "q1_full_12m_prob": float,
        "stint_count_mean": float,
        "on_court_ortg_mean": float,
        "off_court_ortg_mean": float,
        "on_court_drtg_mean": float,
        "off_court_drtg_mean": float,
        "on_off_net_rating_delta_mean": float,
        "pts_mean": float,
        "pts_sd": float,
        "pts_p10": float,
        "pts_p50": float,
        "pts_p90": float,
        "pts_quantiles": NBASimQuantileProfile,
        "q1_pts_mean": float,
        "q2_pts_mean": float,
        "q3_pts_mean": float,
        "q4_pts_mean": float,
        "reb_mean": float,
        "reb_sd": float,
        "reb_p10": float,
        "reb_p50": float,
        "reb_p90": float,
        "reb_quantiles": NBASimQuantileProfile,
        "oreb_mean": float,
        "dreb_mean": float,
        "ast_mean": float,
        "ast_sd": float,
        "ast_p10": float,
        "ast_p50": float,
        "ast_p90": float,
        "ast_quantiles": NBASimQuantileProfile,
        "fg3m_mean": float,
        "fg3m_p50": float,
        "fg3m_p90": float,
        "fg3m_quantiles": NBASimQuantileProfile,
        "fg3a_mean": float,
        "fg2m_mean": float,
        "fg2a_mean": float,
        "fgm_mean": float,
        "fga_mean": float,
        "ftm_mean": float,
        "fta_mean": float,
        "stl_mean": float,
        "blk_mean": float,
        "ba_mean": float,
        "tov_mean": float,
        "pf_mean": float,
        "fd_mean": float,
        "paint_pts_mean": float,
        "fastbreak_pts_mean": float,
        "second_chance_pts_mean": float,
        "unassisted_fgm_mean": float,
        "foul_trouble_rate": float,
        "plus_minus_mean": float,
        "pra_mean": float,
        "pra_p50": float,
        "pra_p90": float,
        "pra_quantiles": NBASimQuantileProfile,
        "double_double_prob": float,
        "triple_double_prob": float,
        "p_20plus_pts": float,
        "p_30plus_pts": float,
        "p_40plus_pts": float,
        "p_50plus_pts": float,
        "p_10plus_reb": float,
        "p_10plus_ast": float,
        "p_50plus_dk": float,
        "p_60plus_dk": float,
        "dk_pts_mean": float,
        "dk_pts_sd": float,
        "dk_pts_p10": float,
        "dk_pts_p50": float,
        "dk_pts_p75": float,
        "dk_pts_p90": float,
        "dk_pts_p95": float,
        "dk_pts_quantiles": NBASimQuantileProfile,
        "fd_pts_mean": float,
        "fd_pts_sd": float,
        "fd_pts_p50": float,
        "fd_pts_p90": float,
        "fd_pts_quantiles": NBASimQuantileProfile,
        "vegas_pts_line": float | None,
        "vegas_reb_line": float | None,
        "vegas_ast_line": float | None,
        "vegas_threes_line": float | None,
        "vegas_pra_line": float | None,
    },
)

NBACoMovementImpact = TypedDict(
    "NBACoMovementImpact",
    {
        "teammate_player_id": int,
        "teammate_name": str,
        "team_code": str,
        "relation": str,
        "dk_pts_delta_when_focal_booms": float,
        "pts_delta_when_focal_booms": float,
        "ast_delta_when_focal_booms": float,
        "reb_delta_when_focal_booms": float,
    },
)

NBACoMovementSummary = TypedDict(
    "NBACoMovementSummary",
    {
        "focal_player_id": int,
        "focal_player_name": str,
        "team_code": str,
        "boom_threshold_dk_pts": float,
        "winners": list[NBACoMovementImpact],
        "losers": list[NBACoMovementImpact],
    },
)

NBASimSummarySection = TypedDict(
    "NBASimSummarySection",
    {
        "game_id": str,
        "season": int,
        "game_date": str,
        "season_type": str,
        "n_draws": int,
        "seed": int,
        "vegas_mode": str,
        "vegas_spread_home": float | None,
        "vegas_total": float | None,
        "sim_total_mean": float,
        "sim_total_sd": float,
        "total_quantiles": NBASimQuantileProfile,
        "sim_spread_home_mean": float,
        "sim_spread_home_sd": float,
        "spread_home_quantiles": NBASimQuantileProfile,
        "home_win_prob": float,
        "away_win_prob": float,
        "tie_prob": float,
        "home_cover_prob": float,
        "over_prob": float,
        "overtime_rate": float,
        "possessions_per_team_mean": float,
        "solve_elapsed_ms": float,
        "sim_elapsed_ms": float,
        "conservation_ok": bool,
        "c0_max_diff": float,
        "calibration_diagnostics": NBAVegasCalibrationDiagnostics,
        "home_team": NBASimTeamSummary,
        "away_team": NBASimTeamSummary,
        "game_pra_mean": float,
        "game_dk_pts_mean": float,
        "game_fd_pts_mean": float,
        "score_conditioned_scaling": list[NBAScoreBinStatProfile],
        "players": list[NBASimPlayerSummary],
        "co_movement": list[NBACoMovementSummary],
    },
)

NBAHistogramBin = TypedDict(
    "NBAHistogramBin",
    {
        "x_min": float,
        "x_max": float,
        "x_mid": float,
        "count": int,
        "prob": float,
        "cum_prob": float,
        "gaussian_density": float,
        "kde_density": float,
        "sample_draw_idx": int | None,
    },
)

NBASimBellCurve = TypedDict(
    "NBASimBellCurve",
    {
        "metric": str,
        "label": str,
        "discrete": bool,
        "n_draws": int,
        "mean": float,
        "sd": float,
        "skew": float,
        "min": float,
        "max": float,
        "p01": float,
        "p05": float,
        "p10": float,
        "p25": float,
        "p50": float,
        "p75": float,
        "p90": float,
        "p95": float,
        "p99": float,
        "p99_5": float,
        "vegas_line": float | None,
        "p_over_vegas": float | None,
        "bins": list[NBAHistogramBin],
    },
)

NBAPlayerBellCurves = TypedDict(
    "NBAPlayerBellCurves",
    {
        "player_id": int,
        "name": str,
        "team_code": str,
        "is_home": bool,
        "position": str,
        "rotation_role": str,
        "role_label": str,
        "dk_pts": NBASimBellCurve,
        "fd_pts": NBASimBellCurve,
        "minutes": NBASimBellCurve,
        "points": NBASimBellCurve,
        "rebounds": NBASimBellCurve,
        "assists": NBASimBellCurve,
        "threes": NBASimBellCurve,
        "pra": NBASimBellCurve,
    },
)

NBASimDistributionsSection = TypedDict(
    "NBASimDistributionsSection",
    {
        "total_points": NBASimBellCurve,
        "spread_margin_home": NBASimBellCurve,
        "home_points": NBASimBellCurve,
        "away_points": NBASimBellCurve,
        "possessions_per_team": NBASimBellCurve,
        "home_threes": NBASimBellCurve,
        "away_threes": NBASimBellCurve,
        "home_rebounds": NBASimBellCurve,
        "away_rebounds": NBASimBellCurve,
        "players": list[NBAPlayerBellCurves],
    },
)

NBALineupStintBoxRow = TypedDict(
    "NBALineupStintBoxRow",
    {
        "player_ids": list[int],
        "package_tag": str,
        "minutes_seconds": float,
        "possessions": int,
        "pts_for": int,
        "pts_against": int,
        "plus_minus": int,
    },
)

NBAPlayerBoxScore = TypedDict(
    "NBAPlayerBoxScore",
    {
        "player_id": int,
        "minutes_seconds": float,
        "pts": int,
        "fgm": int,
        "fga": int,
        "fg3m": int,
        "fg3a": int,
        "ftm": int,
        "fta": int,
        "oreb": int,
        "dreb": int,
        "reb": int,
        "ast": int,
        "stl": int,
        "blk": int,
        "ba": int,
        "tov": int,
        "pf": int,
        "fd": int,
        "plus_minus": int,
        "paint_pts": int,
        "fastbreak_pts": int,
        "second_chance_pts": int,
        "unassisted_fgm": int,
        "stint_count": int,
        "quarter_pts": list[int],
        "quarter_minutes_sec": list[float],
        "quarter_fga": list[int],
        "started": bool,
        "fouled_out": bool,
        "injured_out": bool,
    },
)

NBATeamBoxScore = TypedDict(
    "NBATeamBoxScore",
    {
        "team_code": str,
        "pts": int,
        "quarter_pts": list[int],
        "possessions": int,
        "fgm": int,
        "fga": int,
        "fg3m": int,
        "fg3a": int,
        "ftm": int,
        "fta": int,
        "oreb": int,
        "dreb": int,
        "reb": int,
        "ast": int,
        "stl": int,
        "blk": int,
        "ba": int,
        "tov": int,
        "team_tov": int,
        "pf": int,
        "fd": int,
        "fastbreak_pts": int,
        "paint_pts": int,
        "second_chance_pts": int,
        "pts_off_turnovers": int,
        "starter_pts": int,
        "bench_pts": int,
        "starter_reb": int,
        "bench_reb": int,
        "starter_ast": int,
        "bench_ast": int,
        "biggest_lead": int,
        "lead_changes": int,
        "times_tied": int,
        "missed_terminal_ft": int,
        "team_reb": int,
        "lineup_stints": list[NBALineupStintBoxRow],
        "players": list[NBAPlayerBoxScore],
    },
)

NBAPlayerDrawBoxSummary = TypedDict(
    "NBAPlayerDrawBoxSummary",
    {
        "player_id": int,
        "name": str,
        "team_code": str,
        "is_home": bool,
        "position": str,
        "started": bool,
        "minutes": float,
        "minutes_mm_ss": str,
        "dnp_reason": str | None,
        "stint_count": int,
        "pts": int,
        "reb": int,
        "oreb": int,
        "dreb": int,
        "ast": int,
        "stl": int,
        "blk": int,
        "ba": int,
        "tov": int,
        "pf": int,
        "fd": int,
        "fgm": int,
        "fga": int,
        "fg2m": int,
        "fg2a": int,
        "fg3m": int,
        "fg3a": int,
        "ftm": int,
        "fta": int,
        "paint_pts": int,
        "fastbreak_pts": int,
        "second_chance_pts": int,
        "unassisted_fgm": int,
        "quarter_pts": list[int],
        "plus_minus": int,
        "double_double": bool,
        "triple_double": bool,
        "dk_pts": float,
        "fd_pts": float,
    },
)

NBASimGameScoreboxDraw = TypedDict(
    "NBASimGameScoreboxDraw",
    {
        "draw_idx": int,
        "archetype": str | None,
        "label": str,
        "away_linescore": list[int],
        "home_linescore": list[int],
        "total_points": int,
        "final_margin_home": int,
        "overtime_played": bool,
        "periods_played": int,
        "away_team": NBATeamBoxScore,
        "home_team": NBATeamBoxScore,
        "players": list[NBAPlayerDrawBoxSummary],
    },
)

NBASimScoreboxSection = TypedDict(
    "NBASimScoreboxSection",
    {
        "archetypes": list[NBASimGameScoreboxDraw],
        "draws": list[NBASimGameScoreboxDraw],
    },
)

NBAPossessionEvent = TypedDict(
    "NBAPossessionEvent",
    {
        "event_idx": int,
        "period": int,
        "period_label": str,
        "clock_remaining_sec": float,
        "clock_display": str,
        "offense_team": str,
        "defense_team": str,
        "play_type": str,
        "primary_player_id": int,
        "primary_player_name": str,
        "secondary_player_id": int | None,
        "secondary_player_name": str | None,
        "points_scored": int,
        "home_score": int,
        "away_score": int,
        "description": str,
        "play_family": str | None,
        "shot_zone": str | None,
        "home_on_court_ids": list[int],
        "away_on_court_ids": list[int],
    },
)

NBASimTracedGame = TypedDict(
    "NBASimTracedGame",
    {
        "draw_idx": int,
        "archetype": str | None,
        "label": str,
        "scorebox": NBASimGameScoreboxDraw,
        "possessions": list[NBAPossessionEvent],
    },
)

NBATeamOffenseProfile = TypedDict(
    "NBATeamOffenseProfile",
    {
        "pace": float,
        "off_rating": float,
        "oreb_rate": float,
        "transition_rate": float,
    },
)

NBATeamDefenseProfile = TypedDict(
    "NBATeamDefenseProfile",
    {
        "def_rating": float,
        "rim_protection": float | None,
        "perimeter_defense": float | None,
        "ball_pressure": float | None,
        "def_rebounding": float | None,
        "foul_discipline": float | None,
        "transition_defense_rating": float | None,
    },
)

NBAScheduleContextProfile = TypedDict(
    "NBAScheduleContextProfile",
    {
        "rest_days": int,
    },
)

NBASimScenarioTeamProfile = TypedDict(
    "NBASimScenarioTeamProfile",
    {
        "team_id": int,
        "team_abbr": str,
        "team_name": str,
        "is_home": bool,
        "offense": NBATeamOffenseProfile,
        "defense": NBATeamDefenseProfile,
        "schedule": NBAScheduleContextProfile,
    },
)

NBAPlayerRotationProfile = TypedDict(
    "NBAPlayerRotationProfile",
    {
        "role": str,
        "minutes": float,
        "minutes_cap": float,
        "fouls_per_36": float,
        "dnp_rate": float | None,
    },
)

NBAPlayerOffenseProfile = TypedDict(
    "NBAPlayerOffenseProfile",
    {
        "usage_rate": float,
        "ast_rate": float,
        "three_rate": float,
        "ft_rate": float,
        "two_pct": float,
        "three_pct": float,
        "ft_pct": float,
        "tov_rate": float,
    },
)

NBAPlayerReboundDefenseProfile = TypedDict(
    "NBAPlayerReboundDefenseProfile",
    {
        "oreb_rate": float,
        "dreb_rate": float,
        "stl_rate": float,
        "blk_rate": float,
    },
)

NBAPlayerInjuryProfile = TypedDict(
    "NBAPlayerInjuryProfile",
    {
        "status": str,
        "play_chance_pct": float,
        "exit_rate": float | None,
    },
)

NBAPlayerPreTipSituationProfile = TypedDict(
    "NBAPlayerPreTipSituationProfile",
    {
        "last10_minutes": float,
    },
)

NBASimPropInput = TypedDict(
    "NBASimPropInput",
    {
        "points": float | None,
        "rebounds": float | None,
        "assists": float | None,
        "threes": float | None,
        "pra": float | None,
        "minutes": float | None,
        "steals": float | None,
        "blocks": float | None,
        "double_double_prob": float | None,
        "points_over_prob": float | None,
        "rebounds_over_prob": float | None,
        "assists_over_prob": float | None,
        "threes_over_prob": float | None,
        "pra_over_prob": float | None,
        "captured_age_min": float | None,
        "stale_line": bool,
    },
)

NBASimScenarioPlayerProfile = TypedDict(
    "NBASimScenarioPlayerProfile",
    {
        "player_id": int,
        "player_name": str,
        "team_id": int,
        "team_abbr": str,
        "position": str,
        "depth_role": str,
        "is_starter": bool,
        "active": bool,
        "dk_salary": int | None,
        "fd_salary": int | None,
        "rotation": NBAPlayerRotationProfile,
        "offense": NBAPlayerOffenseProfile,
        "boards_defense": NBAPlayerReboundDefenseProfile,
        "injury": NBAPlayerInjuryProfile,
        "recent": NBAPlayerPreTipSituationProfile,
        "props": NBASimPropInput | None,
    },
)

NBASimScenarioQuarters = TypedDict(
    "NBASimScenarioQuarters",
    {
        "away": list[int],
        "home": list[int],
    },
)

NBASimScenarioScoreboard = TypedDict(
    "NBASimScenarioScoreboard",
    {
        "quarters": NBASimScenarioQuarters,
        "away_score": int,
        "home_score": int,
        "total_points": int,
        "home_margin": int,
        "overtime": bool,
        "overtime_periods": int,
    },
)

NBASimTeamBoxScoreStat = TypedDict(
    "NBASimTeamBoxScoreStat",
    {
        "possessions": int,
        "pace": float,
        "offensive_rating": float,
        "defensive_rating": float,
        "pts": int,
        "fgm": int,
        "fga": int,
        "fg_pct": float,
        "fg2m": int,
        "fg2a": int,
        "fg2_pct": float,
        "fg3m": int,
        "fg3a": int,
        "fg3_pct": float,
        "ftm": int,
        "fta": int,
        "ft_pct": float,
        "efg_pct": float,
        "ts_pct": float,
        "oreb": int,
        "dreb": int,
        "reb": int,
        "ast": int,
        "stl": int,
        "blk": int,
        "ba": int,
        "tov": int,
        "team_tov": int,
        "team_reb": int,
        "pf": int,
        "fd": int,
        "paint_pts": int,
        "fastbreak_pts": int,
        "second_chance_pts": int,
        "pts_off_turnovers": int,
        "starter_pts": int,
        "bench_pts": int,
        "biggest_lead": int,
        "lead_changes": int,
        "times_tied": int,
        "pra": int,
        "dk_pts": float,
        "fd_pts": float,
    },
    total=False,
)

NBASimPlayerBoxScoreStat = TypedDict(
    "NBASimPlayerBoxScoreStat",
    {
        "started": bool,
        "minutes": float,
        "stint_count": int,
        "q1_minutes": float,
        "q2_minutes": float,
        "q3_minutes": float,
        "q4_minutes": float,
        "ot_minutes": float,
        "pts": int,
        "q1_pts": int,
        "q2_pts": int,
        "q3_pts": int,
        "q4_pts": int,
        "ot_pts": int,
        "fgm": int,
        "fga": int,
        "fg_pct": float,
        "fg2m": int,
        "fg2a": int,
        "fg2_pct": float,
        "fg3m": int,
        "fg3a": int,
        "fg3_pct": float,
        "ftm": int,
        "fta": int,
        "ft_pct": float,
        "ts_pct": float,
        "oreb": int,
        "dreb": int,
        "reb": int,
        "ast": int,
        "stl": int,
        "blk": int,
        "ba": int,
        "tov": int,
        "pf": int,
        "fd": int,
        "paint_pts": int,
        "fastbreak_pts": int,
        "second_chance_pts": int,
        "unassisted_fgm": int,
        "plus_minus": int,
        "fouled_out": bool,
        "double_double": bool,
        "triple_double": bool,
        "pra": int,
        "dk_pts": float,
        "fd_pts": float,
    },
    total=False,
)

NBASimPlayerCorrelations = TypedDict(
    "NBASimPlayerCorrelations",
    {
        "scope": str,
        "player_ids": list[str],
        "matrix": list[list[float | None]],
    },
)

NBASimGamePossession = TypedDict(
    "NBASimGamePossession",
    {
        "possession_number": int,
        "period": int,
        "start_clock": str,
        "end_clock": str,
        "duration_seconds": int,
        "offense_team_id": int,
        "defense_team_id": int,
        "start_type": str,
        "result": str,
        "points_scored": int,
        "play_count": int,
        "fga": int,
        "fgm": int,
        "fg3a": int,
        "fg3m": int,
        "fta": int,
        "ftm": int,
        "turnover": bool,
        "offensive_rebounds": int,
        "away_score_end": int,
        "home_score_end": int,
        "away_lineup_ids": list[int],
        "home_lineup_ids": list[int],
        "first_event_id": int,
        "last_event_id": int,
    },
)

NBASimPlayStatCredit = TypedDict(
    "NBASimPlayStatCredit",
    {
        "player_id": int,
        "team_id": int,
        "stat": str,
        "value": int,
        "points": int,
    },
)

NBASimPlayByPlay = TypedDict(
    "NBASimPlayByPlay",
    {
        "event_id": int,
        "possession_number": int,
        "period": int,
        "clock": str,
        "period_seconds_remaining": float,
        "shot_clock": float,
        "event_type": str,
        "event_sub_type": str,
        "team_id": int,
        "opponent_team_id": int,
        "player_id": int | None,
        "player2_id": int | None,
        "player3_id": int | None,
        "points_scored": int,
        "shot_value": int | None,
        "shot_made": bool | None,
        "shot_distance_ft": float | None,
        "coord_x": int | None,
        "coord_y": int | None,
        "shot_zone": str | None,
        "is_fastbreak": bool,
        "is_second_chance": bool,
        "is_paint": bool,
        "in_penalty": bool,
        "away_score": int,
        "home_score": int,
        "away_fouls_qtr": int,
        "home_fouls_qtr": int,
        "away_timeouts_remaining": int,
        "home_timeouts_remaining": int,
        "away_lineup_ids": list[int],
        "home_lineup_ids": list[int],
        "play_stats": list[NBASimPlayStatCredit],
        "description": str,
    },
)

NBASimLineupStint = TypedDict(
    "NBASimLineupStint",
    {
        "stint_number": int,
        "team_id": int,
        "is_home": bool,
        "period": int,
        "player_ids": list[int],
        "package_tag": str,
        "duration_seconds": float,
        "possessions": int,
        "points_for": int,
        "points_against": int,
    },
)

NBASimGamePbp = TypedDict(
    "NBASimGamePbp",
    {
        "game_possessions": list[NBASimGamePossession],
        "play_by_plays": list[NBASimPlayByPlay],
        "lineup_stints": list[NBASimLineupStint],
    },
)

NBASimGameScenario = TypedDict(
    "NBASimGameScenario",
    {
        "scenario_id": int,
        "draw_idx": int,
        "probability": float,
        "cluster_draw_count": int,
        "scoreboard": NBASimScenarioScoreboard,
        "teams": dict[str, NBASimTeamBoxScoreStat],
        "players": dict[str, NBASimPlayerBoxScoreStat],
        "player_correlations": NBASimPlayerCorrelations,
        "pbp": NBASimGamePbp | None,
    },
)

NBASimScenariosSection = TypedDict(
    "NBASimScenariosSection",
    {
        "mode": str,
        "n_monte_carlo_draws": int,
        "scenarios_returned": int,
        "probability_sum": float,
        "teams": dict[str, NBASimScenarioTeamProfile],
        "players": dict[str, NBASimScenarioPlayerProfile],
        "scenarios": list[NBASimGameScenario],
    },
)

NBASimSimulatedGame = TypedDict(
    "NBASimSimulatedGame",
    {
        "sim_id": int,
        "draw_idx": int,
        "scoreboard": NBASimScenarioScoreboard,
        "teams": dict[str, NBASimTeamBoxScoreStat],
        "players": dict[str, NBASimPlayerBoxScoreStat],
        "pbp": NBASimGamePbp | None,
    },
)

NBASimSimulationsSection = TypedDict(
    "NBASimSimulationsSection",
    {
        "n_monte_carlo_draws": int,
        "simulations_returned": int,
        "pbp_included": bool,
        "teams": dict[str, NBASimScenarioTeamProfile],
        "players": dict[str, NBASimScenarioPlayerProfile],
        "games": list[NBASimSimulatedGame],
    },
)

NBASimGranularOutput = TypedDict(
    "NBASimGranularOutput",
    {
        "game_id": str,
        "n_draws": int,
        "seed": int,
        "solve_elapsed_ms": float,
        "sim_elapsed_ms": float,
        "summary": NBASimSummarySection | None,
        "distributions": NBASimDistributionsSection | None,
        "scorebox": NBASimScoreboxSection | None,
        "pbp": list[NBASimTracedGame] | None,
        "scenarios": NBASimScenariosSection | None,
        "simulations": NBASimSimulationsSection | None,
    },
)

NBASimPublicRunResponse = TypedDict(
    "NBASimPublicRunResponse",
    {
        "matchup": NBAMatchupSummary,
        "input_sha256": str,
        "output": NBASimGranularOutput,
    },
)


class SimNBAResource:
    """Client resource for `/api/v1/sim/nba/*` and `/api/v1/nba/weeks`."""

    def __init__(self, transport: HttpTransport) -> None:
        self._transport = transport

    def weeks(self, *, season: int | None = None, week: int | None = None, season_type: str | None = None) -> NBASimWeeksResponse:
        """List available NBA weeks, matchup summaries, and `free_game_id` (`6029936`).

        Reference: https://stat-api.com/docs/api/nba/seasons/#endpoint-nba-seasons-weeks
        """
        body, _ = self._transport.request("/api/v1/nba/weeks", {"season": season, "week": week, "season_type": season_type})
        resp: NBASimWeeksResponse = body
        return resp

    def run(
        self,
        *,
        game_id: int,
        n_draws: int | None = None,
        seed: int | None = None,
        include: NBASimInclude | Mapping[str, object] | None = None,
        scenarios: int | None = None,
        scenarios_pbp: bool | None = None,
        simulations: bool | None = None,
        simulations_pbp: bool | None = None,
        simulations_limit: int | None = None,
        home_score: float | None = None,
        away_score: float | None = None,
        total: float | None = None,
        home_spread: float | None = None,
        home_win_pct: float | None = None,
        mode: str | None = None,
        game_pace_pct: float | None = None,
        ref_whistle: float | None = None,
        blowout_pulls: float | None = None,
        home: NBASimTeamOverride | Mapping[str, object] | None = None,
        away: NBASimTeamOverride | Mapping[str, object] | None = None,
        players: Sequence[NBASimPlayerOverride | Mapping[str, object]] | None = None,
    ) -> NBASimPublicRunResponse:
        """Run N parallel Monte Carlo simulations (`POST /api/v1/sim/nba/run`).

        Reference: https://stat-api.com/docs/api/sim/#endpoint-sim-nba-run
        """
        payload: dict[str, object] = {}
        payload["game_id"] = int(game_id)
        if n_draws is not None:
            payload["n_draws"] = n_draws
        if seed is not None:
            payload["seed"] = seed
        if include is not None:
            payload["include"] = dict(include)
        if scenarios is not None:
            payload["scenarios"] = scenarios
        if scenarios_pbp is not None:
            payload["scenarios_pbp"] = scenarios_pbp
        if simulations is not None:
            payload["simulations"] = simulations
        if simulations_pbp is not None:
            payload["simulations_pbp"] = simulations_pbp
        if simulations_limit is not None:
            payload["simulations_limit"] = simulations_limit
        if home_score is not None:
            payload["home_score"] = home_score
        if away_score is not None:
            payload["away_score"] = away_score
        if total is not None:
            payload["total"] = total
        if home_spread is not None:
            payload["home_spread"] = home_spread
        if home_win_pct is not None:
            payload["home_win_pct"] = home_win_pct
        if mode is not None:
            payload["mode"] = mode
        if game_pace_pct is not None:
            payload["game_pace_pct"] = game_pace_pct
        if ref_whistle is not None:
            payload["ref_whistle"] = ref_whistle
        if blowout_pulls is not None:
            payload["blowout_pulls"] = blowout_pulls
        if home is not None:
            payload["home"] = dict(home)
        if away is not None:
            payload["away"] = dict(away)
        if players is not None:
            payload["players"] = [dict(item) for item in players]
        body, _ = self._transport.post("/api/v1/sim/nba/run", payload)
        resp: NBASimPublicRunResponse = body
        return resp
