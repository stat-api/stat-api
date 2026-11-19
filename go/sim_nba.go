// DO NOT EDIT — generated from schema/api/sim/nba_sim.yml by generate-sim-sdks.ts
package statapi

import (
	"context"
	"encoding/json"
	"net/url"
	"strconv"
)

// NBAWeeksParams are query parameters for GET /api/v1/nba/weeks.
type NBAWeeksParams struct {
	Season     *int
	Week       *int
	SeasonType *string
}

func (p *NBAWeeksParams) values() url.Values {
	v := url.Values{}
	if p == nil {
		return v
	}
	if p.Season != nil {
		v.Set("season", strconv.Itoa(*p.Season))
	}
	if p.Week != nil {
		v.Set("week", strconv.Itoa(*p.Week))
	}
	if p.SeasonType != nil {
		v.Set("season_type", *p.SeasonType)
	}
	return v
}

// NBAMatchupSummary: Matchup header and pre-tip Vegas line summary for a single NBA game.
type NBAMatchupSummary struct {
	GameID        int64   `json:"game_id"`
	Season        int     `json:"season"`
	Week          int     `json:"week"`
	SeasonType    string  `json:"season_type"`
	Kickoff       *string `json:"kickoff"`
	Day           *int    `json:"day"`
	HomeTeamID    int64   `json:"home_team_id"`
	HomeAbbr      string  `json:"home_abbr"`
	AwayTeamID    int64   `json:"away_team_id"`
	AwayAbbr      string  `json:"away_abbr"`
	SpreadHome    float64 `json:"spread_home"`
	GameTotal     float64 `json:"game_total"`
	HomeTeamTotal float64 `json:"home_team_total"`
	AwayTeamTotal float64 `json:"away_team_total"`
	MoneylineHome *int    `json:"moneyline_home"`
	MoneylineAway *int    `json:"moneyline_away"`
	IsFinal       bool    `json:"is_final"`
	RealHomePts   *int    `json:"real_home_pts"`
	RealAwayPts   *int    `json:"real_away_pts"`
	IsFree        bool    `json:"is_free"`
}

// NBASimWeeksResponse: Response payload for GET /api/v1/nba/weeks.
type NBASimWeeksResponse struct {
	Season           int                 `json:"season"`
	AvailableSeasons []int               `json:"available_seasons"`
	FreeGameID       int64               `json:"free_game_id"`
	Weeks            []int               `json:"weeks"`
	Games            []NBAMatchupSummary `json:"games"`
	Quota            Quota               `json:"-"`
}

// NBASimInclude: Output section selector (`include`) for POST /api/v1/sim/nba/run.
type NBASimInclude struct {
	Summary          *bool `json:"summary,omitempty"`
	Distributions    *bool `json:"distributions,omitempty"`
	Scorebox         *bool `json:"scorebox,omitempty"`
	Pbp              *bool `json:"pbp,omitempty"`
	Scenarios        *bool `json:"scenarios,omitempty"`
	ScenariosPbp     *bool `json:"scenarios_pbp,omitempty"`
	Simulations      *bool `json:"simulations,omitempty"`
	SimulationsPbp   *bool `json:"simulations_pbp,omitempty"`
	ScenariosLimit   *int  `json:"scenarios_limit,omitempty"`
	SimulationsLimit *int  `json:"simulations_limit,omitempty"`
	ScoreboxLimit    *int  `json:"scorebox_limit,omitempty"`
	PbpDraws         []int `json:"pbp_draws,omitempty"`
}

// NBASimTeamOverride: Simple v1 team overrides (out list, pace, offensive/defensive ratings, rest days) for `home` / `away` on public POST /api/v1/sim/nba/run.
type NBASimTeamOverride struct {
	Out       []int64  `json:"out,omitempty"`
	Pace      *float64 `json:"pace,omitempty"`
	OffRating *float64 `json:"off_rating,omitempty"`
	DefRating *float64 `json:"def_rating,omitempty"`
	RestDays  *int     `json:"rest_days,omitempty"`
}

// NBASimPlayerOverride: Simple v1 per-player availability, rotation role, minutes, and prop line overrides for public POST /api/v1/sim/nba/run.
type NBASimPlayerOverride struct {
	PlayerID         int64    `json:"player_id"`
	Out              *bool    `json:"out,omitempty"`
	Status           *string  `json:"status,omitempty"`
	PlayChancePct    *float64 `json:"play_chance_pct,omitempty"`
	Role             *string  `json:"role,omitempty"`
	Minutes          *float64 `json:"minutes,omitempty"`
	MinutesCap       *float64 `json:"minutes_cap,omitempty"`
	Points           *float64 `json:"points,omitempty"`
	Rebounds         *float64 `json:"rebounds,omitempty"`
	Assists          *float64 `json:"assists,omitempty"`
	Threes           *float64 `json:"threes,omitempty"`
	Pra              *float64 `json:"pra,omitempty"`
	Steals           *float64 `json:"steals,omitempty"`
	Blocks           *float64 `json:"blocks,omitempty"`
	DoubleDoubleProb *float64 `json:"double_double_prob,omitempty"`
}

// NBASimPublicRunRequest: Zero-input + simple v1 override request body for public POST /api/v1/sim/nba/run.
type NBASimPublicRunRequest struct {
	GameID           int64                  `json:"game_id"`
	NDraws           *int                   `json:"n_draws,omitempty"`
	Seed             *int64                 `json:"seed,omitempty"`
	Include          *NBASimInclude         `json:"include,omitempty"`
	Scenarios        *int                   `json:"scenarios,omitempty"`
	ScenariosPbp     *bool                  `json:"scenarios_pbp,omitempty"`
	Simulations      *bool                  `json:"simulations,omitempty"`
	SimulationsPbp   *bool                  `json:"simulations_pbp,omitempty"`
	SimulationsLimit *int                   `json:"simulations_limit,omitempty"`
	HomeScore        *float64               `json:"home_score,omitempty"`
	AwayScore        *float64               `json:"away_score,omitempty"`
	Total            *float64               `json:"total,omitempty"`
	HomeSpread       *float64               `json:"home_spread,omitempty"`
	HomeWinPct       *float64               `json:"home_win_pct,omitempty"`
	Mode             *string                `json:"mode,omitempty"`
	GamePacePct      *float64               `json:"game_pace_pct,omitempty"`
	RefWhistle       *float64               `json:"ref_whistle,omitempty"`
	BlowoutPulls     *float64               `json:"blowout_pulls,omitempty"`
	Home             *NBASimTeamOverride    `json:"home,omitempty"`
	Away             *NBASimTeamOverride    `json:"away,omitempty"`
	Players          []NBASimPlayerOverride `json:"players,omitempty"`
}

// NBASimQuantileProfile: Full non-parametric tail and median quantile profile.
type NBASimQuantileProfile struct {
	Min  float64 `json:"min"`
	P01  float64 `json:"p01"`
	P05  float64 `json:"p05"`
	P10  float64 `json:"p10"`
	P25  float64 `json:"p25"`
	P50  float64 `json:"p50"`
	P75  float64 `json:"p75"`
	P90  float64 `json:"p90"`
	P95  float64 `json:"p95"`
	P99  float64 `json:"p99"`
	P995 float64 `json:"p99_5"`
	Max  float64 `json:"max"`
	Skew float64 `json:"skew"`
}

// NBAVegasCalibrationDiagnostics: Pre-simulation CRN Vegas calibration diagnostics.
type NBAVegasCalibrationDiagnostics struct {
	VegasMode            string   `json:"vegas_mode"`
	IterationsRun        int      `json:"iterations_run"`
	TargetHomePts        *float64 `json:"target_home_pts"`
	TargetAwayPts        *float64 `json:"target_away_pts"`
	AchievedPilotHomePts float64  `json:"achieved_pilot_home_pts"`
	AchievedPilotAwayPts float64  `json:"achieved_pilot_away_pts"`
	HomeEfgTilt          float64  `json:"home_efg_tilt"`
	AwayEfgTilt          float64  `json:"away_efg_tilt"`
	RankInversionCount   int      `json:"rank_inversion_count"`
}

// NBASimTeamSummary: Team-level aggregated simulation statistics.
type NBASimTeamSummary struct {
	TeamCode            string                `json:"team_code"`
	TeamName            string                `json:"team_name"`
	IsHome              bool                  `json:"is_home"`
	VegasTeamTotal      *float64              `json:"vegas_team_total"`
	PointsMean          float64               `json:"points_mean"`
	PointsSd            float64               `json:"points_sd"`
	PointsP01           float64               `json:"points_p01"`
	PointsP05           float64               `json:"points_p05"`
	PointsP10           float64               `json:"points_p10"`
	PointsP25           float64               `json:"points_p25"`
	PointsP50           float64               `json:"points_p50"`
	PointsP75           float64               `json:"points_p75"`
	PointsP90           float64               `json:"points_p90"`
	PointsP95           float64               `json:"points_p95"`
	PointsP99           float64               `json:"points_p99"`
	PointsQuantiles     NBASimQuantileProfile `json:"points_quantiles"`
	Q1PointsMean        float64               `json:"q1_points_mean"`
	Q2PointsMean        float64               `json:"q2_points_mean"`
	Q3PointsMean        float64               `json:"q3_points_mean"`
	Q4PointsMean        float64               `json:"q4_points_mean"`
	OtPointsMean        float64               `json:"ot_points_mean"`
	WinProb             float64               `json:"win_prob"`
	PossessionsMean     float64               `json:"possessions_mean"`
	OffensiveRatingMean float64               `json:"offensive_rating_mean"`
	FgPctMean           float64               `json:"fg_pct_mean"`
	Fg2PctMean          float64               `json:"fg2_pct_mean"`
	Fg3PctMean          float64               `json:"fg3_pct_mean"`
	FtPctMean           float64               `json:"ft_pct_mean"`
	FgmMean             float64               `json:"fgm_mean"`
	FgaMean             float64               `json:"fga_mean"`
	Fg2mMean            float64               `json:"fg2m_mean"`
	Fg2aMean            float64               `json:"fg2a_mean"`
	Fg3mMean            float64               `json:"fg3m_mean"`
	Fg3aMean            float64               `json:"fg3a_mean"`
	FtmMean             float64               `json:"ftm_mean"`
	FtaMean             float64               `json:"fta_mean"`
	RebMean             float64               `json:"reb_mean"`
	OrebMean            float64               `json:"oreb_mean"`
	DrebMean            float64               `json:"dreb_mean"`
	AstMean             float64               `json:"ast_mean"`
	StlMean             float64               `json:"stl_mean"`
	BlkMean             float64               `json:"blk_mean"`
	BaMean              float64               `json:"ba_mean"`
	TovMean             float64               `json:"tov_mean"`
	PfMean              float64               `json:"pf_mean"`
	FdMean              float64               `json:"fd_mean"`
	FastbreakPtsMean    float64               `json:"fastbreak_pts_mean"`
	PaintPtsMean        float64               `json:"paint_pts_mean"`
	SecondChancePtsMean float64               `json:"second_chance_pts_mean"`
	PtsOffTurnoversMean float64               `json:"pts_off_turnovers_mean"`
	StarterPtsMean      float64               `json:"starter_pts_mean"`
	BenchPtsMean        float64               `json:"bench_pts_mean"`
	BiggestLeadMean     float64               `json:"biggest_lead_mean"`
	LeadChangesMean     float64               `json:"lead_changes_mean"`
	TimesTiedMean       float64               `json:"times_tied_mean"`
	PraMean             float64               `json:"pra_mean"`
	DkPtsMean           float64               `json:"dk_pts_mean"`
	FdPtsMean           float64               `json:"fd_pts_mean"`
}

// NBAScoreBinStatProfile: Conditional game-total quintile breakdown showing box-score scaling.
type NBAScoreBinStatProfile struct {
	QuintileLabel       string  `json:"quintile_label"`
	DrawCount           int     `json:"draw_count"`
	GamePtsMean         float64 `json:"game_pts_mean"`
	GameFgmMean         float64 `json:"game_fgm_mean"`
	GameFg3mMean        float64 `json:"game_fg3m_mean"`
	GameFtmMean         float64 `json:"game_ftm_mean"`
	GameRebMean         float64 `json:"game_reb_mean"`
	GameAstMean         float64 `json:"game_ast_mean"`
	GamePraMean         float64 `json:"game_pra_mean"`
	GameDkPtsMean       float64 `json:"game_dk_pts_mean"`
	GameFdPtsMean       float64 `json:"game_fd_pts_mean"`
	Players35plusDkMean float64 `json:"players_35plus_dk_mean"`
}

// NBASimPlayerSummary: Per-player mean, SD, quantiles, and DFS milestones across simulated draws.
type NBASimPlayerSummary struct {
	PlayerID                int64                 `json:"player_id"`
	Name                    string                `json:"name"`
	TeamCode                string                `json:"team_code"`
	IsHome                  bool                  `json:"is_home"`
	Position                string                `json:"position"`
	RotationRole            string                `json:"rotation_role"`
	RoleLabel               string                `json:"role_label"`
	RotationArchetypeLabel  string                `json:"rotation_archetype_label"`
	StarterProb             float64               `json:"starter_prob"`
	ActiveProb              float64               `json:"active_prob"`
	CoachDnpProb            float64               `json:"coach_dnp_prob"`
	DkSalary                *int                  `json:"dk_salary"`
	FdSalary                *int                  `json:"fd_salary"`
	ProjectedMinutes        float64               `json:"projected_minutes"`
	MinutesMean             float64               `json:"minutes_mean"`
	MinutesSd               float64               `json:"minutes_sd"`
	MinutesP10              float64               `json:"minutes_p10"`
	MinutesP50              float64               `json:"minutes_p50"`
	MinutesP90              float64               `json:"minutes_p90"`
	MinutesQuantiles        NBASimQuantileProfile `json:"minutes_quantiles"`
	Q1MinutesMean           float64               `json:"q1_minutes_mean"`
	Q2MinutesMean           float64               `json:"q2_minutes_mean"`
	Q3MinutesMean           float64               `json:"q3_minutes_mean"`
	Q4MinutesMean           float64               `json:"q4_minutes_mean"`
	OtMinutesMean           float64               `json:"ot_minutes_mean"`
	Q1Full12mProb           float64               `json:"q1_full_12m_prob"`
	StintCountMean          float64               `json:"stint_count_mean"`
	OnCourtOrtgMean         float64               `json:"on_court_ortg_mean"`
	OffCourtOrtgMean        float64               `json:"off_court_ortg_mean"`
	OnCourtDrtgMean         float64               `json:"on_court_drtg_mean"`
	OffCourtDrtgMean        float64               `json:"off_court_drtg_mean"`
	OnOffNetRatingDeltaMean float64               `json:"on_off_net_rating_delta_mean"`
	PtsMean                 float64               `json:"pts_mean"`
	PtsSd                   float64               `json:"pts_sd"`
	PtsP10                  float64               `json:"pts_p10"`
	PtsP50                  float64               `json:"pts_p50"`
	PtsP90                  float64               `json:"pts_p90"`
	PtsQuantiles            NBASimQuantileProfile `json:"pts_quantiles"`
	Q1PtsMean               float64               `json:"q1_pts_mean"`
	Q2PtsMean               float64               `json:"q2_pts_mean"`
	Q3PtsMean               float64               `json:"q3_pts_mean"`
	Q4PtsMean               float64               `json:"q4_pts_mean"`
	RebMean                 float64               `json:"reb_mean"`
	RebSd                   float64               `json:"reb_sd"`
	RebP10                  float64               `json:"reb_p10"`
	RebP50                  float64               `json:"reb_p50"`
	RebP90                  float64               `json:"reb_p90"`
	RebQuantiles            NBASimQuantileProfile `json:"reb_quantiles"`
	OrebMean                float64               `json:"oreb_mean"`
	DrebMean                float64               `json:"dreb_mean"`
	AstMean                 float64               `json:"ast_mean"`
	AstSd                   float64               `json:"ast_sd"`
	AstP10                  float64               `json:"ast_p10"`
	AstP50                  float64               `json:"ast_p50"`
	AstP90                  float64               `json:"ast_p90"`
	AstQuantiles            NBASimQuantileProfile `json:"ast_quantiles"`
	Fg3mMean                float64               `json:"fg3m_mean"`
	Fg3mP50                 float64               `json:"fg3m_p50"`
	Fg3mP90                 float64               `json:"fg3m_p90"`
	Fg3mQuantiles           NBASimQuantileProfile `json:"fg3m_quantiles"`
	Fg3aMean                float64               `json:"fg3a_mean"`
	Fg2mMean                float64               `json:"fg2m_mean"`
	Fg2aMean                float64               `json:"fg2a_mean"`
	FgmMean                 float64               `json:"fgm_mean"`
	FgaMean                 float64               `json:"fga_mean"`
	FtmMean                 float64               `json:"ftm_mean"`
	FtaMean                 float64               `json:"fta_mean"`
	StlMean                 float64               `json:"stl_mean"`
	BlkMean                 float64               `json:"blk_mean"`
	BaMean                  float64               `json:"ba_mean"`
	TovMean                 float64               `json:"tov_mean"`
	PfMean                  float64               `json:"pf_mean"`
	FdMean                  float64               `json:"fd_mean"`
	PaintPtsMean            float64               `json:"paint_pts_mean"`
	FastbreakPtsMean        float64               `json:"fastbreak_pts_mean"`
	SecondChancePtsMean     float64               `json:"second_chance_pts_mean"`
	UnassistedFgmMean       float64               `json:"unassisted_fgm_mean"`
	FoulTroubleRate         float64               `json:"foul_trouble_rate"`
	PlusMinusMean           float64               `json:"plus_minus_mean"`
	PraMean                 float64               `json:"pra_mean"`
	PraP50                  float64               `json:"pra_p50"`
	PraP90                  float64               `json:"pra_p90"`
	PraQuantiles            NBASimQuantileProfile `json:"pra_quantiles"`
	DoubleDoubleProb        float64               `json:"double_double_prob"`
	TripleDoubleProb        float64               `json:"triple_double_prob"`
	P20plusPts              float64               `json:"p_20plus_pts"`
	P30plusPts              float64               `json:"p_30plus_pts"`
	P40plusPts              float64               `json:"p_40plus_pts"`
	P50plusPts              float64               `json:"p_50plus_pts"`
	P10plusReb              float64               `json:"p_10plus_reb"`
	P10plusAst              float64               `json:"p_10plus_ast"`
	P50plusDk               float64               `json:"p_50plus_dk"`
	P60plusDk               float64               `json:"p_60plus_dk"`
	DkPtsMean               float64               `json:"dk_pts_mean"`
	DkPtsSd                 float64               `json:"dk_pts_sd"`
	DkPtsP10                float64               `json:"dk_pts_p10"`
	DkPtsP50                float64               `json:"dk_pts_p50"`
	DkPtsP75                float64               `json:"dk_pts_p75"`
	DkPtsP90                float64               `json:"dk_pts_p90"`
	DkPtsP95                float64               `json:"dk_pts_p95"`
	DkPtsQuantiles          NBASimQuantileProfile `json:"dk_pts_quantiles"`
	FdPtsMean               float64               `json:"fd_pts_mean"`
	FdPtsSd                 float64               `json:"fd_pts_sd"`
	FdPtsP50                float64               `json:"fd_pts_p50"`
	FdPtsP90                float64               `json:"fd_pts_p90"`
	FdPtsQuantiles          NBASimQuantileProfile `json:"fd_pts_quantiles"`
	VegasPtsLine            *float64              `json:"vegas_pts_line"`
	VegasRebLine            *float64              `json:"vegas_reb_line"`
	VegasAstLine            *float64              `json:"vegas_ast_line"`
	VegasThreesLine         *float64              `json:"vegas_threes_line"`
	VegasPraLine            *float64              `json:"vegas_pra_line"`
}

// NBACoMovementImpact: Conditional stat delta for a teammate when a focal player booms (`>= P80` DK points).
type NBACoMovementImpact struct {
	TeammatePlayerID         int64   `json:"teammate_player_id"`
	TeammateName             string  `json:"teammate_name"`
	TeamCode                 string  `json:"team_code"`
	Relation                 string  `json:"relation"`
	DkPtsDeltaWhenFocalBooms float64 `json:"dk_pts_delta_when_focal_booms"`
	PtsDeltaWhenFocalBooms   float64 `json:"pts_delta_when_focal_booms"`
	AstDeltaWhenFocalBooms   float64 `json:"ast_delta_when_focal_booms"`
	RebDeltaWhenFocalBooms   float64 `json:"reb_delta_when_focal_booms"`
}

// NBACoMovementSummary: Conditional teammate winners & losers when a focal player clears P80 DraftKings points.
type NBACoMovementSummary struct {
	FocalPlayerID      int64                 `json:"focal_player_id"`
	FocalPlayerName    string                `json:"focal_player_name"`
	TeamCode           string                `json:"team_code"`
	BoomThresholdDkPts float64               `json:"boom_threshold_dk_pts"`
	Winners            []NBACoMovementImpact `json:"winners"`
	Losers             []NBACoMovementImpact `json:"losers"`
}

// NBASimSummarySection: Macro game, team, and player summary statistics across all simulated draws (`output.summary`).
type NBASimSummarySection struct {
	GameID                  string                         `json:"game_id"`
	Season                  int                            `json:"season"`
	GameDate                string                         `json:"game_date"`
	SeasonType              string                         `json:"season_type"`
	NDraws                  int                            `json:"n_draws"`
	Seed                    uint64                         `json:"seed"`
	VegasMode               string                         `json:"vegas_mode"`
	VegasSpreadHome         *float64                       `json:"vegas_spread_home"`
	VegasTotal              *float64                       `json:"vegas_total"`
	SimTotalMean            float64                        `json:"sim_total_mean"`
	SimTotalSd              float64                        `json:"sim_total_sd"`
	TotalQuantiles          NBASimQuantileProfile          `json:"total_quantiles"`
	SimSpreadHomeMean       float64                        `json:"sim_spread_home_mean"`
	SimSpreadHomeSd         float64                        `json:"sim_spread_home_sd"`
	SpreadHomeQuantiles     NBASimQuantileProfile          `json:"spread_home_quantiles"`
	HomeWinProb             float64                        `json:"home_win_prob"`
	AwayWinProb             float64                        `json:"away_win_prob"`
	TieProb                 float64                        `json:"tie_prob"`
	HomeCoverProb           float64                        `json:"home_cover_prob"`
	OverProb                float64                        `json:"over_prob"`
	OvertimeRate            float64                        `json:"overtime_rate"`
	PossessionsPerTeamMean  float64                        `json:"possessions_per_team_mean"`
	SolveElapsedMs          float64                        `json:"solve_elapsed_ms"`
	SimElapsedMs            float64                        `json:"sim_elapsed_ms"`
	ConservationOk          bool                           `json:"conservation_ok"`
	C0MaxDiff               float64                        `json:"c0_max_diff"`
	CalibrationDiagnostics  NBAVegasCalibrationDiagnostics `json:"calibration_diagnostics"`
	HomeTeam                NBASimTeamSummary              `json:"home_team"`
	AwayTeam                NBASimTeamSummary              `json:"away_team"`
	GamePraMean             float64                        `json:"game_pra_mean"`
	GameDkPtsMean           float64                        `json:"game_dk_pts_mean"`
	GameFdPtsMean           float64                        `json:"game_fd_pts_mean"`
	ScoreConditionedScaling []NBAScoreBinStatProfile       `json:"score_conditioned_scaling"`
	Players                 []NBASimPlayerSummary          `json:"players"`
	CoMovement              []NBACoMovementSummary         `json:"co_movement"`
}

// NBAHistogramBin: One histogram bucket with empirical, Gaussian, and KDE densities.
type NBAHistogramBin struct {
	XMin            float64 `json:"x_min"`
	XMax            float64 `json:"x_max"`
	XMid            float64 `json:"x_mid"`
	Count           int     `json:"count"`
	Prob            float64 `json:"prob"`
	CumProb         float64 `json:"cum_prob"`
	GaussianDensity float64 `json:"gaussian_density"`
	KdeDensity      float64 `json:"kde_density"`
	SampleDrawIdx   *int    `json:"sample_draw_idx"`
}

// NBASimBellCurve: Histogram and summary statistics for a single metric.
type NBASimBellCurve struct {
	Metric     string            `json:"metric"`
	Label      string            `json:"label"`
	Discrete   bool              `json:"discrete"`
	NDraws     int               `json:"n_draws"`
	Mean       float64           `json:"mean"`
	Sd         float64           `json:"sd"`
	Skew       float64           `json:"skew"`
	Min        float64           `json:"min"`
	Max        float64           `json:"max"`
	P01        float64           `json:"p01"`
	P05        float64           `json:"p05"`
	P10        float64           `json:"p10"`
	P25        float64           `json:"p25"`
	P50        float64           `json:"p50"`
	P75        float64           `json:"p75"`
	P90        float64           `json:"p90"`
	P95        float64           `json:"p95"`
	P99        float64           `json:"p99"`
	P995       float64           `json:"p99_5"`
	VegasLine  *float64          `json:"vegas_line"`
	POverVegas *float64          `json:"p_over_vegas"`
	Bins       []NBAHistogramBin `json:"bins"`
}

// NBAPlayerBellCurves: Set of marginal bell curves for an active rotation player.
type NBAPlayerBellCurves struct {
	PlayerID     int64           `json:"player_id"`
	Name         string          `json:"name"`
	TeamCode     string          `json:"team_code"`
	IsHome       bool            `json:"is_home"`
	Position     string          `json:"position"`
	RotationRole string          `json:"rotation_role"`
	RoleLabel    string          `json:"role_label"`
	DkPts        NBASimBellCurve `json:"dk_pts"`
	FdPts        NBASimBellCurve `json:"fd_pts"`
	Minutes      NBASimBellCurve `json:"minutes"`
	Points       NBASimBellCurve `json:"points"`
	Rebounds     NBASimBellCurve `json:"rebounds"`
	Assists      NBASimBellCurve `json:"assists"`
	Threes       NBASimBellCurve `json:"threes"`
	Pra          NBASimBellCurve `json:"pra"`
}

// NBASimDistributionsSection: Full game and per-player empirical bell curve distributions (`output.distributions`).
type NBASimDistributionsSection struct {
	TotalPoints        NBASimBellCurve       `json:"total_points"`
	SpreadMarginHome   NBASimBellCurve       `json:"spread_margin_home"`
	HomePoints         NBASimBellCurve       `json:"home_points"`
	AwayPoints         NBASimBellCurve       `json:"away_points"`
	PossessionsPerTeam NBASimBellCurve       `json:"possessions_per_team"`
	HomeThrees         NBASimBellCurve       `json:"home_threes"`
	AwayThrees         NBASimBellCurve       `json:"away_threes"`
	HomeRebounds       NBASimBellCurve       `json:"home_rebounds"`
	AwayRebounds       NBASimBellCurve       `json:"away_rebounds"`
	Players            []NBAPlayerBellCurves `json:"players"`
}

// NBALineupStintBoxRow: 5-man lineup stint row inside `NBATeamBoxScore.lineup_stints`.
type NBALineupStintBoxRow struct {
	PlayerIDs      [5]int64 `json:"player_ids"`
	PackageTag     string   `json:"package_tag"`
	MinutesSeconds float64  `json:"minutes_seconds"`
	Possessions    int      `json:"possessions"`
	PtsFor         int      `json:"pts_for"`
	PtsAgainst     int      `json:"pts_against"`
	PlusMinus      int      `json:"plus_minus"`
}

// NBAPlayerBoxScore: Raw per-draw player box score inside `NBATeamBoxScore.players`.
type NBAPlayerBoxScore struct {
	PlayerID          int64      `json:"player_id"`
	MinutesSeconds    float64    `json:"minutes_seconds"`
	Pts               int        `json:"pts"`
	Fgm               int        `json:"fgm"`
	Fga               int        `json:"fga"`
	Fg3m              int        `json:"fg3m"`
	Fg3a              int        `json:"fg3a"`
	Ftm               int        `json:"ftm"`
	Fta               int        `json:"fta"`
	Oreb              int        `json:"oreb"`
	Dreb              int        `json:"dreb"`
	Reb               int        `json:"reb"`
	Ast               int        `json:"ast"`
	Stl               int        `json:"stl"`
	Blk               int        `json:"blk"`
	Ba                int        `json:"ba"`
	Tov               int        `json:"tov"`
	Pf                int        `json:"pf"`
	Fd                int        `json:"fd"`
	PlusMinus         int        `json:"plus_minus"`
	PaintPts          int        `json:"paint_pts"`
	FastbreakPts      int        `json:"fastbreak_pts"`
	SecondChancePts   int        `json:"second_chance_pts"`
	UnassistedFgm     int        `json:"unassisted_fgm"`
	StintCount        int        `json:"stint_count"`
	QuarterPts        [5]int     `json:"quarter_pts"`
	QuarterMinutesSec [5]float64 `json:"quarter_minutes_sec"`
	QuarterFga        [5]int     `json:"quarter_fga"`
	Started           bool       `json:"started"`
	FouledOut         bool       `json:"fouled_out"`
	InjuredOut        bool       `json:"injured_out"`
}

// NBATeamBoxScore: Single-draw team box score inside `NBASimGameScoreboxDraw`.
type NBATeamBoxScore struct {
	TeamCode         string                 `json:"team_code"`
	Pts              int                    `json:"pts"`
	QuarterPts       []int                  `json:"quarter_pts"`
	Possessions      int                    `json:"possessions"`
	Fgm              int                    `json:"fgm"`
	Fga              int                    `json:"fga"`
	Fg3m             int                    `json:"fg3m"`
	Fg3a             int                    `json:"fg3a"`
	Ftm              int                    `json:"ftm"`
	Fta              int                    `json:"fta"`
	Oreb             int                    `json:"oreb"`
	Dreb             int                    `json:"dreb"`
	Reb              int                    `json:"reb"`
	Ast              int                    `json:"ast"`
	Stl              int                    `json:"stl"`
	Blk              int                    `json:"blk"`
	Ba               int                    `json:"ba"`
	Tov              int                    `json:"tov"`
	TeamTov          int                    `json:"team_tov"`
	Pf               int                    `json:"pf"`
	Fd               int                    `json:"fd"`
	FastbreakPts     int                    `json:"fastbreak_pts"`
	PaintPts         int                    `json:"paint_pts"`
	SecondChancePts  int                    `json:"second_chance_pts"`
	PtsOffTurnovers  int                    `json:"pts_off_turnovers"`
	StarterPts       int                    `json:"starter_pts"`
	BenchPts         int                    `json:"bench_pts"`
	StarterReb       int                    `json:"starter_reb"`
	BenchReb         int                    `json:"bench_reb"`
	StarterAst       int                    `json:"starter_ast"`
	BenchAst         int                    `json:"bench_ast"`
	BiggestLead      int                    `json:"biggest_lead"`
	LeadChanges      int                    `json:"lead_changes"`
	TimesTied        int                    `json:"times_tied"`
	MissedTerminalFt int                    `json:"missed_terminal_ft"`
	TeamReb          int                    `json:"team_reb"`
	LineupStints     []NBALineupStintBoxRow `json:"lineup_stints"`
	Players          []NBAPlayerBoxScore    `json:"players"`
}

// NBAPlayerDrawBoxSummary: Formatted single-draw player box score inside `NBASimGameScoreboxDraw.players`.
type NBAPlayerDrawBoxSummary struct {
	PlayerID        int64   `json:"player_id"`
	Name            string  `json:"name"`
	TeamCode        string  `json:"team_code"`
	IsHome          bool    `json:"is_home"`
	Position        string  `json:"position"`
	Started         bool    `json:"started"`
	Minutes         float64 `json:"minutes"`
	MinutesMmSs     string  `json:"minutes_mm_ss"`
	DnpReason       *string `json:"dnp_reason"`
	StintCount      int     `json:"stint_count"`
	Pts             int     `json:"pts"`
	Reb             int     `json:"reb"`
	Oreb            int     `json:"oreb"`
	Dreb            int     `json:"dreb"`
	Ast             int     `json:"ast"`
	Stl             int     `json:"stl"`
	Blk             int     `json:"blk"`
	Ba              int     `json:"ba"`
	Tov             int     `json:"tov"`
	Pf              int     `json:"pf"`
	Fd              int     `json:"fd"`
	Fgm             int     `json:"fgm"`
	Fga             int     `json:"fga"`
	Fg2m            int     `json:"fg2m"`
	Fg2a            int     `json:"fg2a"`
	Fg3m            int     `json:"fg3m"`
	Fg3a            int     `json:"fg3a"`
	Ftm             int     `json:"ftm"`
	Fta             int     `json:"fta"`
	PaintPts        int     `json:"paint_pts"`
	FastbreakPts    int     `json:"fastbreak_pts"`
	SecondChancePts int     `json:"second_chance_pts"`
	UnassistedFgm   int     `json:"unassisted_fgm"`
	QuarterPts      [5]int  `json:"quarter_pts"`
	PlusMinus       int     `json:"plus_minus"`
	DoubleDouble    bool    `json:"double_double"`
	TripleDouble    bool    `json:"triple_double"`
	DkPts           float64 `json:"dk_pts"`
	FdPts           float64 `json:"fd_pts"`
}

// NBASimGameScoreboxDraw: Complete quarter linescore and box score for one simulated NBA draw.
type NBASimGameScoreboxDraw struct {
	DrawIdx         int                       `json:"draw_idx"`
	Archetype       *string                   `json:"archetype"`
	Label           string                    `json:"label"`
	AwayLinescore   [6]int                    `json:"away_linescore"`
	HomeLinescore   [6]int                    `json:"home_linescore"`
	TotalPoints     int                       `json:"total_points"`
	FinalMarginHome int                       `json:"final_margin_home"`
	OvertimePlayed  bool                      `json:"overtime_played"`
	PeriodsPlayed   int                       `json:"periods_played"`
	AwayTeam        NBATeamBoxScore           `json:"away_team"`
	HomeTeam        NBATeamBoxScore           `json:"home_team"`
	Players         []NBAPlayerDrawBoxSummary `json:"players"`
}

// NBASimScoreboxSection: Scoreboxes for canonical archetypes and sampled draws (`output.scorebox`).
type NBASimScoreboxSection struct {
	Archetypes []NBASimGameScoreboxDraw `json:"archetypes"`
	Draws      []NBASimGameScoreboxDraw `json:"draws"`
}

// NBAPossessionEvent: Chronological possession event inside `NBASimTracedGame.possessions`.
type NBAPossessionEvent struct {
	EventIdx            int      `json:"event_idx"`
	Period              int      `json:"period"`
	PeriodLabel         string   `json:"period_label"`
	ClockRemainingSec   float64  `json:"clock_remaining_sec"`
	ClockDisplay        string   `json:"clock_display"`
	OffenseTeam         string   `json:"offense_team"`
	DefenseTeam         string   `json:"defense_team"`
	PlayType            string   `json:"play_type"`
	PrimaryPlayerID     int64    `json:"primary_player_id"`
	PrimaryPlayerName   string   `json:"primary_player_name"`
	SecondaryPlayerID   *int64   `json:"secondary_player_id"`
	SecondaryPlayerName *string  `json:"secondary_player_name"`
	PointsScored        int      `json:"points_scored"`
	HomeScore           int      `json:"home_score"`
	AwayScore           int      `json:"away_score"`
	Description         string   `json:"description"`
	PlayFamily          *string  `json:"play_family"`
	ShotZone            *string  `json:"shot_zone"`
	HomeOnCourtIDs      [5]int64 `json:"home_on_court_ids"`
	AwayOnCourtIDs      [5]int64 `json:"away_on_court_ids"`
}

// NBASimTracedGame: Complete traced single-game NBA simulation returned in `output.pbp[i]` on `POST /api/v1/sim/nba/run`.
type NBASimTracedGame struct {
	DrawIdx     int                    `json:"draw_idx"`
	Archetype   *string                `json:"archetype"`
	Label       string                 `json:"label"`
	Scorebox    NBASimGameScoreboxDraw `json:"scorebox"`
	Possessions []NBAPossessionEvent   `json:"possessions"`
}

// NBATeamOffenseProfile: Pre-tip team offense ratings inside `output.scenarios.teams[id].offense`.
type NBATeamOffenseProfile struct {
	Pace           float64 `json:"pace"`
	OffRating      float64 `json:"off_rating"`
	OrebRate       float64 `json:"oreb_rate"`
	TransitionRate float64 `json:"transition_rate"`
}

// NBATeamDefenseProfile: Pre-tip team defense ratings inside `output.scenarios.teams[id].defense`.
type NBATeamDefenseProfile struct {
	DefRating               float64  `json:"def_rating"`
	RimProtection           *float64 `json:"rim_protection"`
	PerimeterDefense        *float64 `json:"perimeter_defense"`
	BallPressure            *float64 `json:"ball_pressure"`
	DefRebounding           *float64 `json:"def_rebounding"`
	FoulDiscipline          *float64 `json:"foul_discipline"`
	TransitionDefenseRating *float64 `json:"transition_defense_rating"`
}

// NBAScheduleContextProfile: Pre-tip rest and schedule context inside `output.scenarios.teams[id].schedule`.
type NBAScheduleContextProfile struct {
	RestDays int `json:"rest_days"`
}

// NBASimScenarioTeamProfile: Top-level normalized pre-tip team profile keyed by `team_id` in `output.scenarios.teams`.
type NBASimScenarioTeamProfile struct {
	TeamID   int64                     `json:"team_id"`
	TeamAbbr string                    `json:"team_abbr"`
	TeamName string                    `json:"team_name"`
	IsHome   bool                      `json:"is_home"`
	Offense  NBATeamOffenseProfile     `json:"offense"`
	Defense  NBATeamDefenseProfile     `json:"defense"`
	Schedule NBAScheduleContextProfile `json:"schedule"`
}

// NBAPlayerRotationProfile: Pre-tip player rotation & minutes parameters.
type NBAPlayerRotationProfile struct {
	Role       string   `json:"role"`
	Minutes    float64  `json:"minutes"`
	MinutesCap float64  `json:"minutes_cap"`
	FoulsPer36 float64  `json:"fouls_per_36"`
	DnpRate    *float64 `json:"dnp_rate"`
}

// NBAPlayerOffenseProfile: Pre-tip player usage and shooting splits.
type NBAPlayerOffenseProfile struct {
	UsageRate float64 `json:"usage_rate"`
	AstRate   float64 `json:"ast_rate"`
	ThreeRate float64 `json:"three_rate"`
	FtRate    float64 `json:"ft_rate"`
	TwoPct    float64 `json:"two_pct"`
	ThreePct  float64 `json:"three_pct"`
	FtPct     float64 `json:"ft_pct"`
	TovRate   float64 `json:"tov_rate"`
}

// NBAPlayerReboundDefenseProfile: Pre-tip player rebounding and defensive event rates.
type NBAPlayerReboundDefenseProfile struct {
	OrebRate float64 `json:"oreb_rate"`
	DrebRate float64 `json:"dreb_rate"`
	StlRate  float64 `json:"stl_rate"`
	BlkRate  float64 `json:"blk_rate"`
}

// NBAPlayerInjuryProfile: Pre-tip injury report and availability profile.
type NBAPlayerInjuryProfile struct {
	Status        string   `json:"status"`
	PlayChancePct float64  `json:"play_chance_pct"`
	ExitRate      *float64 `json:"exit_rate"`
}

// NBAPlayerPreTipSituationProfile: Recent workload trend context.
type NBAPlayerPreTipSituationProfile struct {
	Last10Minutes float64 `json:"last10_minutes"`
}

// NBASimPropInput: Pre-tip NBA player prop market inputs attached in `output.scenarios.players[id].props`.
type NBASimPropInput struct {
	Points           *float64 `json:"points"`
	Rebounds         *float64 `json:"rebounds"`
	Assists          *float64 `json:"assists"`
	Threes           *float64 `json:"threes"`
	Pra              *float64 `json:"pra"`
	Minutes          *float64 `json:"minutes"`
	Steals           *float64 `json:"steals"`
	Blocks           *float64 `json:"blocks"`
	DoubleDoubleProb *float64 `json:"double_double_prob"`
	PointsOverProb   *float64 `json:"points_over_prob"`
	ReboundsOverProb *float64 `json:"rebounds_over_prob"`
	AssistsOverProb  *float64 `json:"assists_over_prob"`
	ThreesOverProb   *float64 `json:"threes_over_prob"`
	PraOverProb      *float64 `json:"pra_over_prob"`
	CapturedAgeMin   *float64 `json:"captured_age_min"`
	StaleLine        bool     `json:"stale_line"`
}

// NBASimScenarioPlayerProfile: Top-level normalized pre-tip player profile keyed by `player_id` in `output.scenarios.players`.
type NBASimScenarioPlayerProfile struct {
	PlayerID      int64                           `json:"player_id"`
	PlayerName    string                          `json:"player_name"`
	TeamID        int64                           `json:"team_id"`
	TeamAbbr      string                          `json:"team_abbr"`
	Position      string                          `json:"position"`
	DepthRole     string                          `json:"depth_role"`
	IsStarter     bool                            `json:"is_starter"`
	Active        bool                            `json:"active"`
	DkSalary      *int                            `json:"dk_salary"`
	FdSalary      *int                            `json:"fd_salary"`
	Rotation      NBAPlayerRotationProfile        `json:"rotation"`
	Offense       NBAPlayerOffenseProfile         `json:"offense"`
	BoardsDefense NBAPlayerReboundDefenseProfile  `json:"boards_defense"`
	Injury        NBAPlayerInjuryProfile          `json:"injury"`
	Recent        NBAPlayerPreTipSituationProfile `json:"recent"`
	Props         *NBASimPropInput                `json:"props"`
}

// NBASimScenarioQuarters: Quarter linescore arrays for one NBA scenario.
type NBASimScenarioQuarters struct {
	Away []int `json:"away"`
	Home []int `json:"home"`
}

// NBASimScenarioScoreboard: Scenario and simulated-game final scoreboard.
type NBASimScenarioScoreboard struct {
	Quarters        NBASimScenarioQuarters `json:"quarters"`
	AwayScore       int                    `json:"away_score"`
	HomeScore       int                    `json:"home_score"`
	TotalPoints     int                    `json:"total_points"`
	HomeMargin      int                    `json:"home_margin"`
	Overtime        bool                   `json:"overtime"`
	OvertimePeriods int                    `json:"overtime_periods"`
}

// NBASimTeamBoxScoreStat: Sparse team box score inside `NBASimGameScenario.teams` and `NBASimSimulatedGame.teams`.
type NBASimTeamBoxScoreStat struct {
	Possessions     int     `json:"possessions"`
	Pace            float64 `json:"pace"`
	OffensiveRating float64 `json:"offensive_rating"`
	DefensiveRating float64 `json:"defensive_rating"`
	Pts             int     `json:"pts"`
	Fgm             int     `json:"fgm"`
	Fga             int     `json:"fga"`
	FgPct           float64 `json:"fg_pct"`
	Fg2m            int     `json:"fg2m"`
	Fg2a            int     `json:"fg2a"`
	Fg2Pct          float64 `json:"fg2_pct"`
	Fg3m            int     `json:"fg3m"`
	Fg3a            int     `json:"fg3a"`
	Fg3Pct          float64 `json:"fg3_pct"`
	Ftm             int     `json:"ftm"`
	Fta             int     `json:"fta"`
	FtPct           float64 `json:"ft_pct"`
	EfgPct          float64 `json:"efg_pct"`
	TsPct           float64 `json:"ts_pct"`
	Oreb            int     `json:"oreb"`
	Dreb            int     `json:"dreb"`
	Reb             int     `json:"reb"`
	Ast             int     `json:"ast"`
	Stl             int     `json:"stl"`
	Blk             int     `json:"blk"`
	Ba              int     `json:"ba"`
	Tov             int     `json:"tov"`
	TeamTov         int     `json:"team_tov"`
	TeamReb         int     `json:"team_reb"`
	Pf              int     `json:"pf"`
	Fd              int     `json:"fd"`
	PaintPts        int     `json:"paint_pts"`
	FastbreakPts    int     `json:"fastbreak_pts"`
	SecondChancePts int     `json:"second_chance_pts"`
	PtsOffTurnovers int     `json:"pts_off_turnovers"`
	StarterPts      int     `json:"starter_pts"`
	BenchPts        int     `json:"bench_pts"`
	BiggestLead     int     `json:"biggest_lead"`
	LeadChanges     int     `json:"lead_changes"`
	TimesTied       int     `json:"times_tied"`
	Pra             int     `json:"pra"`
	DkPts           float64 `json:"dk_pts"`
	FdPts           float64 `json:"fd_pts"`
}

// NBASimPlayerBoxScoreStat: Sparse active-player box score inside `NBASimGameScenario.players` and `NBASimSimulatedGame.players`.
type NBASimPlayerBoxScoreStat struct {
	Started         bool    `json:"started"`
	Minutes         float64 `json:"minutes"`
	StintCount      int     `json:"stint_count"`
	Q1Minutes       float64 `json:"q1_minutes"`
	Q2Minutes       float64 `json:"q2_minutes"`
	Q3Minutes       float64 `json:"q3_minutes"`
	Q4Minutes       float64 `json:"q4_minutes"`
	OtMinutes       float64 `json:"ot_minutes"`
	Pts             int     `json:"pts"`
	Q1Pts           int     `json:"q1_pts"`
	Q2Pts           int     `json:"q2_pts"`
	Q3Pts           int     `json:"q3_pts"`
	Q4Pts           int     `json:"q4_pts"`
	OtPts           int     `json:"ot_pts"`
	Fgm             int     `json:"fgm"`
	Fga             int     `json:"fga"`
	FgPct           float64 `json:"fg_pct"`
	Fg2m            int     `json:"fg2m"`
	Fg2a            int     `json:"fg2a"`
	Fg2Pct          float64 `json:"fg2_pct"`
	Fg3m            int     `json:"fg3m"`
	Fg3a            int     `json:"fg3a"`
	Fg3Pct          float64 `json:"fg3_pct"`
	Ftm             int     `json:"ftm"`
	Fta             int     `json:"fta"`
	FtPct           float64 `json:"ft_pct"`
	TsPct           float64 `json:"ts_pct"`
	Oreb            int     `json:"oreb"`
	Dreb            int     `json:"dreb"`
	Reb             int     `json:"reb"`
	Ast             int     `json:"ast"`
	Stl             int     `json:"stl"`
	Blk             int     `json:"blk"`
	Ba              int     `json:"ba"`
	Tov             int     `json:"tov"`
	Pf              int     `json:"pf"`
	Fd              int     `json:"fd"`
	PaintPts        int     `json:"paint_pts"`
	FastbreakPts    int     `json:"fastbreak_pts"`
	SecondChancePts int     `json:"second_chance_pts"`
	UnassistedFgm   int     `json:"unassisted_fgm"`
	PlusMinus       int     `json:"plus_minus"`
	FouledOut       bool    `json:"fouled_out"`
	DoubleDouble    bool    `json:"double_double"`
	TripleDouble    bool    `json:"triple_double"`
	Pra             int     `json:"pra"`
	DkPts           float64 `json:"dk_pts"`
	FdPts           float64 `json:"fd_pts"`
}

// NBASimPlayerCorrelations: Symmetric pairwise Pearson correlation matrix across top rotation players.
type NBASimPlayerCorrelations struct {
	Scope     string       `json:"scope"`
	PlayerIDs []string     `json:"player_ids"`
	Matrix    [][]*float64 `json:"matrix"`
}

// NBASimGamePossession: Possession summary inside `pbp.game_possessions`.
type NBASimGamePossession struct {
	PossessionNumber  int      `json:"possession_number"`
	Period            int      `json:"period"`
	StartClock        string   `json:"start_clock"`
	EndClock          string   `json:"end_clock"`
	DurationSeconds   int      `json:"duration_seconds"`
	OffenseTeamID     int64    `json:"offense_team_id"`
	DefenseTeamID     int64    `json:"defense_team_id"`
	StartType         string   `json:"start_type"`
	Result            string   `json:"result"`
	PointsScored      int      `json:"points_scored"`
	PlayCount         int      `json:"play_count"`
	Fga               int      `json:"fga"`
	Fgm               int      `json:"fgm"`
	Fg3a              int      `json:"fg3a"`
	Fg3m              int      `json:"fg3m"`
	Fta               int      `json:"fta"`
	Ftm               int      `json:"ftm"`
	Turnover          bool     `json:"turnover"`
	OffensiveRebounds int      `json:"offensive_rebounds"`
	AwayScoreEnd      int      `json:"away_score_end"`
	HomeScoreEnd      int      `json:"home_score_end"`
	AwayLineupIDs     [5]int64 `json:"away_lineup_ids"`
	HomeLineupIDs     [5]int64 `json:"home_lineup_ids"`
	FirstEventID      int64    `json:"first_event_id"`
	LastEventID       int64    `json:"last_event_id"`
}

// NBASimPlayStatCredit: Per-player atomic stat credit on an NBA play event.
type NBASimPlayStatCredit struct {
	PlayerID int64  `json:"player_id"`
	TeamID   int64  `json:"team_id"`
	Stat     string `json:"stat"`
	Value    int    `json:"value"`
	Points   int    `json:"points"`
}

// NBASimPlayByPlay: Atomic NBA play-by-play event inside `pbp.play_by_plays`.
type NBASimPlayByPlay struct {
	EventID                int64                  `json:"event_id"`
	PossessionNumber       int                    `json:"possession_number"`
	Period                 int                    `json:"period"`
	Clock                  string                 `json:"clock"`
	PeriodSecondsRemaining float64                `json:"period_seconds_remaining"`
	ShotClock              float64                `json:"shot_clock"`
	EventType              string                 `json:"event_type"`
	EventSubType           string                 `json:"event_sub_type"`
	TeamID                 int64                  `json:"team_id"`
	OpponentTeamID         int64                  `json:"opponent_team_id"`
	PlayerID               *int64                 `json:"player_id"`
	Player2ID              *int64                 `json:"player2_id"`
	Player3ID              *int64                 `json:"player3_id"`
	PointsScored           int                    `json:"points_scored"`
	ShotValue              *int                   `json:"shot_value"`
	ShotMade               *bool                  `json:"shot_made"`
	ShotDistanceFt         *float64               `json:"shot_distance_ft"`
	CoordX                 *int                   `json:"coord_x"`
	CoordY                 *int                   `json:"coord_y"`
	ShotZone               *string                `json:"shot_zone"`
	IsFastbreak            bool                   `json:"is_fastbreak"`
	IsSecondChance         bool                   `json:"is_second_chance"`
	IsPaint                bool                   `json:"is_paint"`
	InPenalty              bool                   `json:"in_penalty"`
	AwayScore              int                    `json:"away_score"`
	HomeScore              int                    `json:"home_score"`
	AwayFoulsQtr           int                    `json:"away_fouls_qtr"`
	HomeFoulsQtr           int                    `json:"home_fouls_qtr"`
	AwayTimeoutsRemaining  int                    `json:"away_timeouts_remaining"`
	HomeTimeoutsRemaining  int                    `json:"home_timeouts_remaining"`
	AwayLineupIDs          [5]int64               `json:"away_lineup_ids"`
	HomeLineupIDs          [5]int64               `json:"home_lineup_ids"`
	PlayStats              []NBASimPlayStatCredit `json:"play_stats"`
	Description            string                 `json:"description"`
}

// NBASimLineupStint: 5-man lineup stint summary inside `pbp.lineup_stints`.
type NBASimLineupStint struct {
	StintNumber     int      `json:"stint_number"`
	TeamID          int64    `json:"team_id"`
	IsHome          bool     `json:"is_home"`
	Period          int      `json:"period"`
	PlayerIDs       [5]int64 `json:"player_ids"`
	PackageTag      string   `json:"package_tag"`
	DurationSeconds float64  `json:"duration_seconds"`
	Possessions     int      `json:"possessions"`
	PointsFor       int      `json:"points_for"`
	PointsAgainst   int      `json:"points_against"`
}

// NBASimGamePbp: Nested official-format NBA play-by-play embedded when `scenarios_pbp = true` or `simulations_pbp = true`.
type NBASimGamePbp struct {
	GamePossessions []NBASimGamePossession `json:"game_possessions"`
	PlayByPlays     []NBASimPlayByPlay     `json:"play_by_plays"`
	LineupStints    []NBASimLineupStint    `json:"lineup_stints"`
}

// NBASimGameScenario: Single discrete probability-weighted NBA full-game scenario (`scenario_id: 1..K`).
type NBASimGameScenario struct {
	ScenarioID         int64                               `json:"scenario_id"`
	DrawIdx            int                                 `json:"draw_idx"`
	Probability        float64                             `json:"probability"`
	ClusterDrawCount   int                                 `json:"cluster_draw_count"`
	Scoreboard         NBASimScenarioScoreboard            `json:"scoreboard"`
	Teams              map[string]NBASimTeamBoxScoreStat   `json:"teams"`
	Players            map[string]NBASimPlayerBoxScoreStat `json:"players"`
	PlayerCorrelations NBASimPlayerCorrelations            `json:"player_correlations"`
	Pbp                *NBASimGamePbp                      `json:"pbp"`
}

// NBASimScenariosSection: Normalized `K`-scenario box-score and correlation output (`output.scenarios`).
type NBASimScenariosSection struct {
	Mode              string                                 `json:"mode"`
	NMonteCarloDraws  int                                    `json:"n_monte_carlo_draws"`
	ScenariosReturned int                                    `json:"scenarios_returned"`
	ProbabilitySum    float64                                `json:"probability_sum"`
	Teams             map[string]NBASimScenarioTeamProfile   `json:"teams"`
	Players           map[string]NBASimScenarioPlayerProfile `json:"players"`
	Scenarios         []NBASimGameScenario                   `json:"scenarios"`
}

// NBASimSimulatedGame: Individual simulated NBA game inside `output.simulations.games`.
type NBASimSimulatedGame struct {
	SimID      int64                               `json:"sim_id"`
	DrawIdx    int                                 `json:"draw_idx"`
	Scoreboard NBASimScenarioScoreboard            `json:"scoreboard"`
	Teams      map[string]NBASimTeamBoxScoreStat   `json:"teams"`
	Players    map[string]NBASimPlayerBoxScoreStat `json:"players"`
	Pbp        *NBASimGamePbp                      `json:"pbp"`
}

// NBASimSimulationsSection: Individual simulated NBA games (`output.simulations`).
type NBASimSimulationsSection struct {
	NMonteCarloDraws    int                                    `json:"n_monte_carlo_draws"`
	SimulationsReturned int                                    `json:"simulations_returned"`
	PbpIncluded         bool                                   `json:"pbp_included"`
	Teams               map[string]NBASimScenarioTeamProfile   `json:"teams"`
	Players             map[string]NBASimScenarioPlayerProfile `json:"players"`
	Games               []NBASimSimulatedGame                  `json:"games"`
}

// NBASimGranularOutput: Granular simulation output inside `NBASimPublicRunResponse.output`.
type NBASimGranularOutput struct {
	GameID         string                      `json:"game_id"`
	NDraws         int                         `json:"n_draws"`
	Seed           uint64                      `json:"seed"`
	SolveElapsedMs float64                     `json:"solve_elapsed_ms"`
	SimElapsedMs   float64                     `json:"sim_elapsed_ms"`
	Summary        *NBASimSummarySection       `json:"summary"`
	Distributions  *NBASimDistributionsSection `json:"distributions"`
	Scorebox       *NBASimScoreboxSection      `json:"scorebox"`
	Pbp            []NBASimTracedGame          `json:"pbp"`
	Scenarios      *NBASimScenariosSection     `json:"scenarios"`
	Simulations    *NBASimSimulationsSection   `json:"simulations"`
}

// NBASimPublicRunResponse: Stripped public v1 response returned by POST /api/v1/sim/nba/run.
type NBASimPublicRunResponse struct {
	Matchup     NBAMatchupSummary    `json:"matchup"`
	InputSha256 string               `json:"input_sha256"`
	Output      NBASimGranularOutput `json:"output"`
	Quota       Quota                `json:"-"`
}

// SimNBAService accesses the /api/v1/sim/nba/* and /api/v1/nba/weeks endpoints.
type SimNBAService struct {
	client *Client
}

// Weeks lists available NBA weeks, matchup summaries, and FreeGameID (6029936).
//
// Reference: https://stat-api.com/docs/api/nba/seasons/#endpoint-nba-seasons-weeks
func (s *SimNBAService) Weeks(ctx context.Context, params *NBAWeeksParams) (*NBASimWeeksResponse, error) {
	body, quota, err := s.client.get(ctx, "/api/v1/nba/weeks", params.values())
	if err != nil {
		return nil, err
	}
	var out NBASimWeeksResponse
	if err := json.Unmarshal(body, &out); err != nil {
		return nil, err
	}
	out.Quota = quota
	return &out, nil
}

// Run executes N parallel Monte Carlo simulations (POST /api/v1/sim/nba/run).
//
// Reference: https://stat-api.com/docs/api/sim/#endpoint-sim-nba-run
func (s *SimNBAService) Run(ctx context.Context, req *NBASimPublicRunRequest) (*NBASimPublicRunResponse, error) {
	body, quota, err := s.client.post(ctx, "/api/v1/sim/nba/run", req)
	if err != nil {
		return nil, err
	}
	var out NBASimPublicRunResponse
	if err := json.Unmarshal(body, &out); err != nil {
		return nil, err
	}
	out.Quota = quota
	return &out, nil
}
