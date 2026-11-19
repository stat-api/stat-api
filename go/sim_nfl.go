// DO NOT EDIT — generated from schema/api/sim/nfl_sim.yml by generate-sim-sdks.ts
package statapi

import (
	"context"
	"encoding/json"
	"net/url"
	"strconv"
)

// NFLWeeksParams are query parameters for GET /api/v1/nfl/weeks.
type NFLWeeksParams struct {
	Season *int
}

func (p *NFLWeeksParams) values() url.Values {
	v := url.Values{}
	if p == nil {
		return v
	}
	if p.Season != nil {
		v.Set("season", strconv.Itoa(*p.Season))
	}
	return v
}

// NFLMatchupSummary: Matchup header and pre-kickoff Vegas line summary for a single NFL game.
type NFLMatchupSummary struct {
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

// NFLWeeksResponse: Response payload for GET /api/v1/nfl/weeks.
type NFLWeeksResponse struct {
	Season           int                 `json:"season"`
	AvailableSeasons []int               `json:"available_seasons"`
	FreeGameID       int64               `json:"free_game_id"`
	Weeks            []int               `json:"weeks"`
	Games            []NFLMatchupSummary `json:"games"`
	Quota            Quota               `json:"-"`
}

// NFLSimPersonnel: Offensive personnel shares of snaps. The engine rescales the seven shares so that they sum to 1. The first digit of a group is the RBs, the second is the TEs, and WRs fill the rest.
type NFLSimPersonnel struct {
	P11      float64 `json:"p11"`
	P12      float64 `json:"p12"`
	P21      float64 `json:"p21"`
	P13      float64 `json:"p13"`
	P22      float64 `json:"p22"`
	NoRb     float64 `json:"no_rb"`
	GoalLine float64 `json:"goal_line"`
}

// NFLSimOline: Offensive line pass-protection grade, run-blocking grade, and sack rate allowed.
type NFLSimOline struct {
	PassBlockGrade  float64 `json:"pass_block_grade"`
	RunBlockGrade   float64 `json:"run_block_grade"`
	SackRateAllowed float64 `json:"sack_rate_allowed"`
}

// NFLSimInjury: Pre-game injury report, practice participation, and snap limit. The engine derives its injury multipliers from the injury type, the practice level and the games missed.
type NFLSimInjury struct {
	PlayChance float64 `json:"play_chance"`
	SnapLimit  float64 `json:"snap_limit"`
	InjuryType string  `json:"injury_type"`
	Practice   string  `json:"practice"`
}

// NFLSimDefense: Football-native team defense pass rush, run defense, coverage grades, tackling, takeaways, and scheme tendencies.
type NFLSimDefense struct {
	PressureRate      float64 `json:"pressure_rate"`
	SackRate          float64 `json:"sack_rate"`
	StuffRate         float64 `json:"stuff_rate"`
	OutsideCbCoverage float64 `json:"outside_cb_coverage"`
	SlotCbCoverage    float64 `json:"slot_cb_coverage"`
	LbSafetyCoverage  float64 `json:"lb_safety_coverage"`
	MissedTackleRate  float64 `json:"missed_tackle_rate"`
	InterceptionRate  float64 `json:"interception_rate"`
	ForcedFumbleRate  float64 `json:"forced_fumble_rate"`
	ManCoverageRate   float64 `json:"man_coverage_rate"`
	BlitzRate         float64 `json:"blitz_rate"`
	TwoHighRate       float64 `json:"two_high_rate"`
	AvgBoxCount       float64 `json:"avg_box_count"`
}

// NFLSimKicker: Football-native placekicker field goal make rate, maximum range in yards, and extra point make rate.
type NFLSimKicker struct {
	PlayerID   int64   `json:"player_id"`
	FgMakeRate float64 `json:"fg_make_rate"`
	FgRangeYds float64 `json:"fg_range_yds"`
	XpMakeRate float64 `json:"xp_make_rate"`
}

// NFLSimTeam: Complete football-native team input profile (pace, play-calling, personnel, O-line, defense, and kicker). The same names and groups serve `inputs` and `changes.home_team` / `changes.away_team`.
type NFLSimTeam struct {
	TeamID            int64           `json:"team_id"`
	TeamAbbr          string          `json:"team_abbr"`
	ExpectedPlays     float64         `json:"expected_plays"`
	NeutralPassRate   float64         `json:"neutral_pass_rate"`
	EarlyDownPassRate float64         `json:"early_down_pass_rate"`
	RedZonePassRate   float64         `json:"red_zone_pass_rate"`
	RedZoneTdRate     float64         `json:"red_zone_td_rate"`
	TrailingPassBoost float64         `json:"trailing_pass_boost"`
	NoHuddleRate      float64         `json:"no_huddle_rate"`
	FourthDownGoRate  float64         `json:"fourth_down_go_rate"`
	Personnel         NFLSimPersonnel `json:"personnel"`
	Oline             NFLSimOline     `json:"oline"`
	Defense           NFLSimDefense   `json:"defense"`
	Kicker            NFLSimKicker    `json:"kicker"`
}

// NFLSimUsage: Football-native player snap share, route participation, slot alignment, and situational target/carry share profile.
type NFLSimUsage struct {
	SnapShare              float64 `json:"snap_share"`
	RouteParticipationRate float64 `json:"route_participation_rate"`
	SlotRate               float64 `json:"slot_rate"`
	TargetShare            float64 `json:"target_share"`
	TargetsPerRoute        float64 `json:"targets_per_route"`
	RedZoneTargetShare     float64 `json:"red_zone_target_share"`
	CarryShare             float64 `json:"carry_share"`
	ShortYardageCarryShare float64 `json:"short_yardage_carry_share"`
	RedZoneCarryShare      float64 `json:"red_zone_carry_share"`
	GoalLineCarryShare     float64 `json:"goal_line_carry_share"`
	FourMinuteCarryShare   float64 `json:"four_minute_carry_share"`
}

// NFLSimPassing: Football-native Quarterback passing accuracy, pocket timing, tight-window aggressiveness, sack avoidance, and scramble profile.
type NFLSimPassing struct {
	CompletionRate       float64 `json:"completion_rate"`
	TimeToThrowSec       float64 `json:"time_to_throw_sec"`
	BadThrowRate         float64 `json:"bad_throw_rate"`
	TightWindowRate      float64 `json:"tight_window_rate"`
	PressureSackRate     float64 `json:"pressure_sack_rate"`
	PressureScrambleRate float64 `json:"pressure_scramble_rate"`
	InterceptionRate     float64 `json:"interception_rate"`
	DesignedRunShare     float64 `json:"designed_run_share"`
}

// NFLSimReceiving: Football-native WR / TE / RB route-running, target depth, separation, contested catch, and Yards After Catch profile.
type NFLSimReceiving struct {
	CatchRate          float64 `json:"catch_rate"`
	YardsPerTarget     float64 `json:"yards_per_target"`
	Adot               float64 `json:"adot"`
	SeparationYds      float64 `json:"separation_yds"`
	ContestedCatchRate float64 `json:"contested_catch_rate"`
	DropRate           float64 `json:"drop_rate"`
	YacPerCatch        float64 `json:"yac_per_catch"`
	ZeroYacRate        float64 `json:"zero_yac_rate"`
	BrokenTackleRate   float64 `json:"broken_tackle_rate"`
	ManTargetFactor    float64 `json:"man_target_factor"`
	ZoneTargetFactor   float64 `json:"zone_target_factor"`
	ScreenTargetRate   float64 `json:"screen_target_rate"`
}

// NFLSimRushing: Football-native RB / QB / WR rushing efficiency, vision before contact, power after contact, and run-length profile.
type NFLSimRushing struct {
	YardsPerCarry      float64 `json:"yards_per_carry"`
	YardsBeforeContact float64 `json:"yards_before_contact"`
	YardsAfterContact  float64 `json:"yards_after_contact"`
	BrokenTackleRate   float64 `json:"broken_tackle_rate"`
	Run15plusRate      float64 `json:"run_15plus_rate"`
	InsideRunRate      float64 `json:"inside_run_rate"`
	FumbleRate         float64 `json:"fumble_rate"`
}

// NFLSimAthleticism: Football scouting ratings (`0..99` scale where `75.0` = positional NFL average).
type NFLSimAthleticism struct {
	SpeedRating       float64 `json:"speed_rating"`
	ElusivenessRating float64 `json:"elusiveness_rating"`
	ContactRating     float64 `json:"contact_rating"`
}

// NFLSimInclude: Output section selector for POST /api/v1/sim/nfl/run. The sections are all on by default.
type NFLSimInclude struct {
	Summary          *bool `json:"summary,omitempty"`
	Distributions    *bool `json:"distributions,omitempty"`
	Scorebox         *bool `json:"scorebox,omitempty"`
	Pbp              *bool `json:"pbp,omitempty"`
	ScoreboxLimit    *int  `json:"scorebox_limit,omitempty"`
	PbpDraws         []int `json:"pbp_draws,omitempty"`
	Scenarios        *bool `json:"scenarios,omitempty"`
	ScenariosPbp     *bool `json:"scenarios_pbp,omitempty"`
	ScenariosLimit   *int  `json:"scenarios_limit,omitempty"`
	Simulations      *bool `json:"simulations,omitempty"`
	SimulationsPbp   *bool `json:"simulations_pbp,omitempty"`
	SimulationsLimit *int  `json:"simulations_limit,omitempty"`
}

// NFLSimWeatherOverride: Simple v1 stadium and weather overrides for public POST /api/v1/sim/nfl/run.
type NFLSimWeatherOverride struct {
	Roof       *string  `json:"roof,omitempty"`
	TempF      *float64 `json:"temp_f,omitempty"`
	WindMph    *float64 `json:"wind_mph,omitempty"`
	PrecipInHr *float64 `json:"precip_in_hr,omitempty"`
}

// NFLSimTeamOverride: Simple v1 team overrides (out list, pace, neutral pass rate) for `home` / `away` on public POST /api/v1/sim/nfl/run.
type NFLSimTeamOverride struct {
	Out                []int64  `json:"out,omitempty"`
	ExpectedPlays      *float64 `json:"expected_plays,omitempty"`
	NeutralPassRatePct *float64 `json:"neutral_pass_rate_pct,omitempty"`
}

// NFLSimPlayerOverride: Simple v1 per-player status, availability, usage, and flat Vegas prop line overrides for public POST /api/v1/sim/nfl/run.
type NFLSimPlayerOverride struct {
	PlayerID      int64    `json:"player_id"`
	Out           *bool    `json:"out,omitempty"`
	Status        *string  `json:"status,omitempty"`
	PlayChancePct *float64 `json:"play_chance_pct,omitempty"`
	SnapSharePct  *float64 `json:"snap_share_pct,omitempty"`
	PassYd        *float64 `json:"pass_yd,omitempty"`
	PassTd        *float64 `json:"pass_td,omitempty"`
	RushYd        *float64 `json:"rush_yd,omitempty"`
	RecYd         *float64 `json:"rec_yd,omitempty"`
	Receptions    *float64 `json:"receptions,omitempty"`
	Carries       *float64 `json:"carries,omitempty"`
	Td            *float64 `json:"td,omitempty"`
}

// NFLSimPublicRunRequest: Zero-input + simple v1 override request body for public POST /api/v1/sim/nfl/run.
type NFLSimPublicRunRequest struct {
	GameID           int64                  `json:"game_id"`
	NDraws           *int                   `json:"n_draws,omitempty"`
	Seed             *int64                 `json:"seed,omitempty"`
	Include          *NFLSimInclude         `json:"include,omitempty"`
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
	Weather          *NFLSimWeatherOverride `json:"weather,omitempty"`
	Home             *NFLSimTeamOverride    `json:"home,omitempty"`
	Away             *NFLSimTeamOverride    `json:"away,omitempty"`
	Players          []NFLSimPlayerOverride `json:"players,omitempty"`
}

// NFLSimTeamSummary: Team-level offensive, drive, situational, and DST summary metrics across all n_draws.
type NFLSimTeamSummary struct {
	TeamID             int64   `json:"team_id"`
	IsHome             bool    `json:"is_home"`
	VegasTeamTotal     float64 `json:"vegas_team_total"`
	PointsMean         float64 `json:"points_mean"`
	PointsSd           float64 `json:"points_sd"`
	PointsP10          float64 `json:"points_p10"`
	PointsP50          float64 `json:"points_p50"`
	PointsP90          float64 `json:"points_p90"`
	Q1PointsMean       float64 `json:"q1_points_mean"`
	Q2PointsMean       float64 `json:"q2_points_mean"`
	Q3PointsMean       float64 `json:"q3_points_mean"`
	Q4PointsMean       float64 `json:"q4_points_mean"`
	OtPointsMean       float64 `json:"ot_points_mean"`
	WinProb            float64 `json:"win_prob"`
	PlaysMean          float64 `json:"plays_mean"`
	PassAttMean        float64 `json:"pass_att_mean"`
	PassCmpMean        float64 `json:"pass_cmp_mean"`
	PassYdMean         float64 `json:"pass_yd_mean"`
	PassTdMean         float64 `json:"pass_td_mean"`
	IntMean            float64 `json:"int_mean"`
	RushAttMean        float64 `json:"rush_att_mean"`
	RushYdMean         float64 `json:"rush_yd_mean"`
	RushTdMean         float64 `json:"rush_td_mean"`
	SacksTakenMean     float64 `json:"sacks_taken_mean"`
	DrivesMean         float64 `json:"drives_mean"`
	DriveTdRate        float64 `json:"drive_td_rate"`
	DriveFgRate        float64 `json:"drive_fg_rate"`
	DrivePuntRate      float64 `json:"drive_punt_rate"`
	DriveTurnoverRate  float64 `json:"drive_turnover_rate"`
	ThirdDownConvRate  float64 `json:"third_down_conv_rate"`
	FourthDownConvRate float64 `json:"fourth_down_conv_rate"`
	DstSacksMean       float64 `json:"dst_sacks_mean"`
	DstIntMean         float64 `json:"dst_int_mean"`
	DstFumblesRecMean  float64 `json:"dst_fumbles_rec_mean"`
	DstTdMean          float64 `json:"dst_td_mean"`
	DstPtsAllowedMean  float64 `json:"dst_pts_allowed_mean"`
}

// NFLSimPlayerSummary: Per-player Monte Carlo summary projections, quantiles, milestone probabilities, fantasy points, and Vegas input medians.
type NFLSimPlayerSummary struct {
	PlayerID           int64    `json:"player_id"`
	TeamID             int64    `json:"team_id"`
	IsHome             bool     `json:"is_home"`
	Position           string   `json:"position"`
	DepthRole          string   `json:"depth_role"`
	MarketTier         string   `json:"market_tier"`
	PActive            float64  `json:"p_active"`
	SnapsMean          float64  `json:"snaps_mean"`
	SnapsP50           float64  `json:"snaps_p50"`
	RoutesMean         float64  `json:"routes_mean"`
	PassCmpMean        float64  `json:"pass_cmp_mean"`
	PassAttMean        float64  `json:"pass_att_mean"`
	PassYdMean         float64  `json:"pass_yd_mean"`
	PassYdSd           float64  `json:"pass_yd_sd"`
	PassYdP10          float64  `json:"pass_yd_p10"`
	PassYdP50          float64  `json:"pass_yd_p50"`
	PassYdP90          float64  `json:"pass_yd_p90"`
	PassTdMean         float64  `json:"pass_td_mean"`
	IntMean            float64  `json:"int_mean"`
	LongestPassP50     float64  `json:"longest_pass_p50"`
	CarriesMean        float64  `json:"carries_mean"`
	DesignedRushesMean float64  `json:"designed_rushes_mean"`
	ScramblesMean      float64  `json:"scrambles_mean"`
	RushYdMean         float64  `json:"rush_yd_mean"`
	RushYdSd           float64  `json:"rush_yd_sd"`
	RushYdP10          float64  `json:"rush_yd_p10"`
	RushYdP50          float64  `json:"rush_yd_p50"`
	RushYdP90          float64  `json:"rush_yd_p90"`
	RushTdMean         float64  `json:"rush_td_mean"`
	LongestRushP50     float64  `json:"longest_rush_p50"`
	TargetsMean        float64  `json:"targets_mean"`
	ReceptionsMean     float64  `json:"receptions_mean"`
	RecYdMean          float64  `json:"rec_yd_mean"`
	RecYdSd            float64  `json:"rec_yd_sd"`
	RecYdP10           float64  `json:"rec_yd_p10"`
	RecYdP50           float64  `json:"rec_yd_p50"`
	RecYdP90           float64  `json:"rec_yd_p90"`
	RecTdMean          float64  `json:"rec_td_mean"`
	AirYdMean          float64  `json:"air_yd_mean"`
	YacYdMean          float64  `json:"yac_yd_mean"`
	LongestRecP50      float64  `json:"longest_rec_p50"`
	FgMadeMean         float64  `json:"fg_made_mean"`
	FgAttMean          float64  `json:"fg_att_mean"`
	XpMadeMean         float64  `json:"xp_made_mean"`
	KickPtsMean        float64  `json:"kick_pts_mean"`
	FumblesLostMean    float64  `json:"fumbles_lost_mean"`
	PAnytimeTd         float64  `json:"p_anytime_td"`
	P2plusTd           float64  `json:"p_2plus_td"`
	PPass300yd         float64  `json:"p_pass_300yd"`
	PRush100yd         float64  `json:"p_rush_100yd"`
	PRec100yd          float64  `json:"p_rec_100yd"`
	VegasPassYd        *float64 `json:"vegas_pass_yd"`
	VegasPassAtt       *float64 `json:"vegas_pass_att"`
	VegasRushYd        *float64 `json:"vegas_rush_yd"`
	VegasCarries       *float64 `json:"vegas_carries"`
	VegasRecYd         *float64 `json:"vegas_rec_yd"`
	VegasReceptions    *float64 `json:"vegas_receptions"`
	VegasTdProb        *float64 `json:"vegas_td_prob"`
}

// NFLSimSummarySection: Macro game, team, and player summary statistics across all n_draws (`output.summary`).
type NFLSimSummarySection struct {
	GameID            int64                 `json:"game_id"`
	Season            int                   `json:"season"`
	Week              int                   `json:"week"`
	NDraws            int                   `json:"n_draws"`
	Seed              uint64                `json:"seed"`
	HasVegasLine      bool                  `json:"has_vegas_line"`
	VegasSpreadHome   float64               `json:"vegas_spread_home"`
	VegasTotal        float64               `json:"vegas_total"`
	SimTotalMean      float64               `json:"sim_total_mean"`
	SimTotalSd        float64               `json:"sim_total_sd"`
	SimSpreadHomeMean float64               `json:"sim_spread_home_mean"`
	SimSpreadHomeSd   float64               `json:"sim_spread_home_sd"`
	HomeWinProb       float64               `json:"home_win_prob"`
	AwayWinProb       float64               `json:"away_win_prob"`
	TieProb           float64               `json:"tie_prob"`
	HomeCoverProb     float64               `json:"home_cover_prob"`
	OverProb          float64               `json:"over_prob"`
	OvertimeRate      float64               `json:"overtime_rate"`
	SolveElapsedMs    float64               `json:"solve_elapsed_ms"`
	SimElapsedMs      float64               `json:"sim_elapsed_ms"`
	ConservationOk    bool                  `json:"conservation_ok"`
	C0MaxDiff         float64               `json:"c0_max_diff"`
	HomeTeam          NFLSimTeamSummary     `json:"home_team"`
	AwayTeam          NFLSimTeamSummary     `json:"away_team"`
	Players           []NFLSimPlayerSummary `json:"players"`
}

// NFLHistogramBin: Single histogram bin inside SimBellCurve.bins.
type NFLHistogramBin struct {
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

// NFLSimBellCurve: 30-bin empirical distribution with Gaussian + KDE smooth overlays, exact quantiles, Vegas threshold survival, and clickable sample_draw_idx per bin.
type NFLSimBellCurve struct {
	Metric     string            `json:"metric"`
	Label      string            `json:"label"`
	Discrete   bool              `json:"discrete"`
	NDraws     int               `json:"n_draws"`
	Mean       float64           `json:"mean"`
	Sd         float64           `json:"sd"`
	Skew       float64           `json:"skew"`
	Min        float64           `json:"min"`
	Max        float64           `json:"max"`
	P05        float64           `json:"p05"`
	P10        float64           `json:"p10"`
	P25        float64           `json:"p25"`
	P50        float64           `json:"p50"`
	P75        float64           `json:"p75"`
	P90        float64           `json:"p90"`
	P95        float64           `json:"p95"`
	VegasLine  *float64          `json:"vegas_line"`
	POverVegas *float64          `json:"p_over_vegas"`
	Bins       []NFLHistogramBin `json:"bins"`
}

// NFLPlayerBellCurves: Collection of Bell Curves for a single player inside output.distributions.players.
type NFLPlayerBellCurves struct {
	PlayerID   int64            `json:"player_id"`
	TeamID     int64            `json:"team_id"`
	Position   string           `json:"position"`
	DepthRole  string           `json:"depth_role"`
	PassYd     *NFLSimBellCurve `json:"pass_yd"`
	RushYd     *NFLSimBellCurve `json:"rush_yd"`
	RecYd      *NFLSimBellCurve `json:"rec_yd"`
	Receptions *NFLSimBellCurve `json:"receptions"`
	Targets    *NFLSimBellCurve `json:"targets"`
	Carries    *NFLSimBellCurve `json:"carries"`
}

// NFLSimDistributionsSection: The `distributions` section (`output.distributions`): macro game bell curves and per-player bell curves.
type NFLSimDistributionsSection struct {
	TotalPoints      NFLSimBellCurve       `json:"total_points"`
	SpreadMarginHome NFLSimBellCurve       `json:"spread_margin_home"`
	HomePoints       NFLSimBellCurve       `json:"home_points"`
	AwayPoints       NFLSimBellCurve       `json:"away_points"`
	CombinedPlays    NFLSimBellCurve       `json:"combined_plays"`
	HomePassYd       NFLSimBellCurve       `json:"home_pass_yd"`
	AwayPassYd       NFLSimBellCurve       `json:"away_pass_yd"`
	HomeRushYd       NFLSimBellCurve       `json:"home_rush_yd"`
	AwayRushYd       NFLSimBellCurve       `json:"away_rush_yd"`
	Players          []NFLPlayerBellCurves `json:"players"`
}

// NFLTeamGameBoxScore: Single-draw team box score (`away_team` and `home_team` inside SimGameScoreboxDraw).
type NFLTeamGameBoxScore struct {
	Points                  int `json:"points"`
	Q1Points                int `json:"q1_points"`
	Q2Points                int `json:"q2_points"`
	Q3Points                int `json:"q3_points"`
	Q4Points                int `json:"q4_points"`
	OtPoints                int `json:"ot_points"`
	Plays                   int `json:"plays"`
	PassAtt                 int `json:"pass_att"`
	PassCmp                 int `json:"pass_cmp"`
	PassYd                  int `json:"pass_yd"`
	PassTd                  int `json:"pass_td"`
	InterceptionsThrown     int `json:"interceptions_thrown"`
	SacksTaken              int `json:"sacks_taken"`
	SackYdLost              int `json:"sack_yd_lost"`
	RushAtt                 int `json:"rush_att"`
	RushYd                  int `json:"rush_yd"`
	RushTd                  int `json:"rush_td"`
	FirstDowns              int `json:"first_downs"`
	ThirdDownAtt            int `json:"third_down_att"`
	ThirdDownConv           int `json:"third_down_conv"`
	FourthDownAtt           int `json:"fourth_down_att"`
	FourthDownConv          int `json:"fourth_down_conv"`
	Drives                  int `json:"drives"`
	DriveTds                int `json:"drive_tds"`
	DriveFgs                int `json:"drive_fgs"`
	DriveMissedFgs          int `json:"drive_missed_fgs"`
	DrivePunts              int `json:"drive_punts"`
	DriveTurnovers          int `json:"drive_turnovers"`
	DriveDowns              int `json:"drive_downs"`
	DriveEndHalf            int `json:"drive_end_half"`
	FgAtt                   int `json:"fg_att"`
	FgMade                  int `json:"fg_made"`
	XpAtt                   int `json:"xp_att"`
	XpMade                  int `json:"xp_made"`
	TwoPtAtt                int `json:"two_pt_att"`
	TwoPtMade               int `json:"two_pt_made"`
	Penalties               int `json:"penalties"`
	PenaltyYds              int `json:"penalty_yds"`
	TimeOfPossessionSec     int `json:"time_of_possession_sec"`
	Throwaways              int `json:"throwaways"`
	Spikes                  int `json:"spikes"`
	BattedPasses            int `json:"batted_passes"`
	OobUntackledPlays       int `json:"oob_untackled_plays"`
	SpecialTeamsReturns     int `json:"special_teams_returns"`
	KickoffsTotal           int `json:"kickoffs_total"`
	KickoffReturns          int `json:"kickoff_returns"`
	KickoffStartYardlineSum int `json:"kickoff_start_yardline_sum"`
	Trailing10plusDropbacks int `json:"trailing_10plus_dropbacks"`
	Trailing10plusPlays     int `json:"trailing_10plus_plays"`
	NeutralDropbacks        int `json:"neutral_dropbacks"`
	NeutralPlays            int `json:"neutral_plays"`
	P11Snaps                int `json:"p11_snaps"`
	P12Snaps                int `json:"p12_snaps"`
	P21Snaps                int `json:"p21_snaps"`
	NoHuddleSnaps           int `json:"no_huddle_snaps"`
	UnassistedTeamSacks     int `json:"unassisted_team_sacks"`
	DstPressures            int `json:"dst_pressures"`
	PickSixesScored         int `json:"pick_sixes_scored"`
	FumbleRetTdsScored      int `json:"fumble_ret_tds_scored"`
	DstSacks                int `json:"dst_sacks"`
	DstInterceptions        int `json:"dst_interceptions"`
	DstFumblesRecovered     int `json:"dst_fumbles_recovered"`
	DstDefensiveTds         int `json:"dst_defensive_tds"`
	DstSafeties             int `json:"dst_safeties"`
	DstPointsAllowed        int `json:"dst_points_allowed"`
}

// NFLPlayerDrawBoxSummary: Single-draw player box score row inside SimGameScoreboxDraw.players.
type NFLPlayerDrawBoxSummary struct {
	PlayerID       int64 `json:"player_id"`
	TeamID         int64 `json:"team_id"`
	Active         bool  `json:"active"`
	Snaps          int   `json:"snaps"`
	Routes         int   `json:"routes"`
	PassCmp        int   `json:"pass_cmp"`
	PassAtt        int   `json:"pass_att"`
	PassYd         int   `json:"pass_yd"`
	PassTd         int   `json:"pass_td"`
	Interception   int   `json:"interception"`
	SacksTaken     int   `json:"sacks_taken"`
	Carries        int   `json:"carries"`
	DesignedRushes int   `json:"designed_rushes"`
	Scrambles      int   `json:"scrambles"`
	RushYd         int   `json:"rush_yd"`
	RushTd         int   `json:"rush_td"`
	Targets        int   `json:"targets"`
	Receptions     int   `json:"receptions"`
	RecYd          int   `json:"rec_yd"`
	RecTd          int   `json:"rec_td"`
	AirYd          int   `json:"air_yd"`
	YacYd          int   `json:"yac_yd"`
	FumblesLost    int   `json:"fumbles_lost"`
	TwoPtConv      int   `json:"two_pt_conv"`
	FgMade         int   `json:"fg_made"`
	FgAtt          int   `json:"fg_att"`
	XpMade         int   `json:"xp_made"`
	XpAtt          int   `json:"xp_att"`
	LongestPass    int   `json:"longest_pass"`
	LongestRush    int   `json:"longest_rush"`
	LongestRec     int   `json:"longest_rec"`
}

// NFLSimGameScoreboxDraw: Complete single-game Scorebox for one simulated draw (`draw_idx`), including quarter linescores, team box scores, and player box scores.
type NFLSimGameScoreboxDraw struct {
	DrawIdx         int                       `json:"draw_idx"`
	Archetype       *string                   `json:"archetype"`
	Label           string                    `json:"label"`
	AwayLinescore   [6]int                    `json:"away_linescore"`
	HomeLinescore   [6]int                    `json:"home_linescore"`
	TotalPoints     int                       `json:"total_points"`
	FinalMarginHome int                       `json:"final_margin_home"`
	Q4MarginHome    int                       `json:"q4_margin_home"`
	OvertimePlayed  bool                      `json:"overtime_played"`
	AwayTeam        NFLTeamGameBoxScore       `json:"away_team"`
	HomeTeam        NFLTeamGameBoxScore       `json:"home_team"`
	Players         []NFLPlayerDrawBoxSummary `json:"players"`
}

// NFLSimScoreboxSection: The `scorebox` section (`output.scorebox`): 6 canonical game archetypes plus individual draw box scores.
type NFLSimScoreboxSection struct {
	Archetypes []NFLSimGameScoreboxDraw `json:"archetypes"`
	Draws      []NFLSimGameScoreboxDraw `json:"draws"`
}

// NFLSimDriveSummary: Possession summary row inside SimTracedGame.drives.
type NFLSimDriveSummary struct {
	DriveNumber      int    `json:"drive_number"`
	PosteamIsHome    bool   `json:"posteam_is_home"`
	StartQuarter     int    `json:"start_quarter"`
	StartClock       string `json:"start_clock"`
	StartYardline100 int    `json:"start_yardline_100"`
	EndYardline100   int    `json:"end_yardline_100"`
	Plays            int    `json:"plays"`
	NetYards         int    `json:"net_yards"`
	FirstDowns       int    `json:"first_downs"`
	Result           string `json:"result"`
	HomeScoreAfter   int    `json:"home_score_after"`
	AwayScoreAfter   int    `json:"away_score_after"`
}

// NFLSimPlayEvent: Snap-level play event state and outcome inside SimPlayRecord.event.
type NFLSimPlayEvent struct {
	GameID               int64  `json:"game_id"`
	DrawIdx              int    `json:"draw_idx"`
	DriveNumber          int    `json:"drive_number"`
	PlayNumber           int    `json:"play_number"`
	Quarter              int    `json:"quarter"`
	HalfSecondsRemaining int    `json:"half_seconds_remaining"`
	GameSecondsRemaining int    `json:"game_seconds_remaining"`
	PosteamIsHome        bool   `json:"posteam_is_home"`
	Down                 int    `json:"down"`
	Ydstogo              int    `json:"ydstogo"`
	Yardline100          int    `json:"yardline_100"`
	ScoreDiff            int    `json:"score_diff"`
	HomeScoreBefore      int    `json:"home_score_before"`
	AwayScoreBefore      int    `json:"away_score_before"`
	HomeScoreAfter       int    `json:"home_score_after"`
	AwayScoreAfter       int    `json:"away_score_after"`
	PlayType             string `json:"play_type"`
	Personnel            int    `json:"personnel"`
	PasserID             int64  `json:"passer_id"`
	RusherID             int64  `json:"rusher_id"`
	ReceiverID           int64  `json:"receiver_id"`
	YardsGained          int    `json:"yards_gained"`
	AirYards             int    `json:"air_yards"`
	YacYards             int    `json:"yac_yards"`
	IsFirstDown          bool   `json:"is_first_down"`
	IsTouchdown          bool   `json:"is_touchdown"`
	IsTurnover           bool   `json:"is_turnover"`
	IsSack               bool   `json:"is_sack"`
	IsComplete           bool   `json:"is_complete"`
	IsFgGood             bool   `json:"is_fg_good"`
	IsTwoPtGood          bool   `json:"is_two_pt_good"`
}

// NFLSimPlayRecord: Enriched play-by-play snap record inside SimTracedGame.plays.
type NFLSimPlayRecord struct {
	Event               NFLSimPlayEvent `json:"event"`
	ClockDisplay        string          `json:"clock_display"`
	DownDistanceDisplay string          `json:"down_distance_display"`
	YardlineDisplay     string          `json:"yardline_display"`
	PersonnelDisplay    string          `json:"personnel_display"`
	Description         string          `json:"description"`
}

// NFLSimTracedGame: Complete traced single-game simulation returned in `output.pbp[i]` on `POST /api/v1/sim/nfl/run`.
type NFLSimTracedGame struct {
	DrawIdx   int                    `json:"draw_idx"`
	Archetype *string                `json:"archetype"`
	Label     string                 `json:"label"`
	Scorebox  NFLSimGameScoreboxDraw `json:"scorebox"`
	Drives    []NFLSimDriveSummary   `json:"drives"`
	Plays     []NFLSimPlayRecord     `json:"plays"`
}

// NFLSimScenarioPlayerProfile: Top-level player identity and football model traits lookup entry (`output.scenarios.players[player_id]`). Contains zero simulated outcome stats.
type NFLSimScenarioPlayerProfile struct {
	PlayerID     int64             `json:"player_id"`
	PlayerName   string            `json:"player_name"`
	TeamID       int64             `json:"team_id"`
	TeamAbbr     string            `json:"team_abbr"`
	Position     string            `json:"position"`
	DepthRole    string            `json:"depth_role"`
	Active       bool              `json:"active"`
	InjuryStatus *string           `json:"injury_status"`
	Usage        NFLSimUsage       `json:"usage"`
	Passing      *NFLSimPassing    `json:"passing"`
	Receiving    NFLSimReceiving   `json:"receiving"`
	Rushing      NFLSimRushing     `json:"rushing"`
	Athleticism  NFLSimAthleticism `json:"athleticism"`
	Injury       NFLSimInjury      `json:"injury"`
}

// NFLSimScenarioQuarters: Quarter-by-quarter linescore arrays (`[Q1, Q2, Q3, Q4]` or `[Q1, Q2, Q3, Q4, OT]`).
type NFLSimScenarioQuarters struct {
	Away []int `json:"away"`
	Home []int `json:"home"`
}

// NFLSimScenarioScoreboard: Pure full-game scoreboard for one scenario (`SimGameScenario.scoreboard`).
type NFLSimScenarioScoreboard struct {
	Quarters    NFLSimScenarioQuarters `json:"quarters"`
	AwayScore   int                    `json:"away_score"`
	HomeScore   int                    `json:"home_score"`
	TotalPoints int                    `json:"total_points"`
	HomeMargin  int                    `json:"home_margin"`
	Overtime    *bool                  `json:"overtime"`
}

// NFLSimTeamBoxScoreStat: Sparse non-zero team offensive, drive, kicking, and DST box score stats (fields equal to `0` are omitted).
type NFLSimTeamBoxScoreStat struct {
	Points        *int `json:"points"`
	Plays         *int `json:"plays"`
	FirstDowns    *int `json:"first_downs"`
	TotalYd       *int `json:"total_yd"`
	PassCmp       *int `json:"pass_cmp"`
	PassAtt       *int `json:"pass_att"`
	PassYd        *int `json:"pass_yd"`
	PassTd        *int `json:"pass_td"`
	Int           *int `json:"int"`
	SacksTaken    *int `json:"sacks_taken"`
	SackYdLost    *int `json:"sack_yd_lost"`
	RushAtt       *int `json:"rush_att"`
	RushYd        *int `json:"rush_yd"`
	RushTd        *int `json:"rush_td"`
	FumblesLost   *int `json:"fumbles_lost"`
	Drives        *int `json:"drives"`
	DriveTds      *int `json:"drive_tds"`
	FgMade        *int `json:"fg_made"`
	FgAtt         *int `json:"fg_att"`
	XpMade        *int `json:"xp_made"`
	XpAtt         *int `json:"xp_att"`
	TwoPtMade     *int `json:"two_pt_made"`
	TwoPtAtt      *int `json:"two_pt_att"`
	DefSacks      *int `json:"def_sacks"`
	DefInt        *int `json:"def_int"`
	DefFumbleRec  *int `json:"def_fumble_rec"`
	DefTd         *int `json:"def_td"`
	Safety        *int `json:"safety"`
	PointsAllowed *int `json:"points_allowed"`
}

// NFLSimPlayerBoxScoreStat: Sparse non-zero player offensive, kicking, and Individual Defensive Player (IDP) box score stats (fields equal to `0` are omitted).
type NFLSimPlayerBoxScoreStat struct {
	Snaps             *int `json:"snaps"`
	Routes            *int `json:"routes"`
	PassCmp           *int `json:"pass_cmp"`
	PassAtt           *int `json:"pass_att"`
	PassYd            *int `json:"pass_yd"`
	PassTd            *int `json:"pass_td"`
	Int               *int `json:"int"`
	SacksTaken        *int `json:"sacks_taken"`
	SackYdLost        *int `json:"sack_yd_lost"`
	LongestPass       *int `json:"longest_pass"`
	RushAtt           *int `json:"rush_att"`
	DesignedRushes    *int `json:"designed_rushes"`
	Scrambles         *int `json:"scrambles"`
	RushYd            *int `json:"rush_yd"`
	RushTd            *int `json:"rush_td"`
	LongestRush       *int `json:"longest_rush"`
	Targets           *int `json:"targets"`
	Rec               *int `json:"rec"`
	RecYd             *int `json:"rec_yd"`
	RecTd             *int `json:"rec_td"`
	AirYd             *int `json:"air_yd"`
	YacYd             *int `json:"yac_yd"`
	LongestRec        *int `json:"longest_rec"`
	FumblesLost       *int `json:"fumbles_lost"`
	TwoPtConv         *int `json:"two_pt_conv"`
	FgMade            *int `json:"fg_made"`
	FgAtt             *int `json:"fg_att"`
	XpMade            *int `json:"xp_made"`
	XpAtt             *int `json:"xp_att"`
	SoloTackles       *int `json:"solo_tackles"`
	AstTackles        *int `json:"ast_tackles"`
	Tfl               *int `json:"tfl"`
	SoloSacks         *int `json:"solo_sacks"`
	SplitSacks        *int `json:"split_sacks"`
	SackYd            *int `json:"sack_yd"`
	QbHits            *int `json:"qb_hits"`
	Pressures         *int `json:"pressures"`
	TargetsCoverage   *int `json:"targets_coverage"`
	ReceptionsAllowed *int `json:"receptions_allowed"`
	RecYdAllowed      *int `json:"rec_yd_allowed"`
	PassesDefended    *int `json:"passes_defended"`
	DefInt            *int `json:"def_int"`
	ForcedFumbles     *int `json:"forced_fumbles"`
	FumbleRec         *int `json:"fumble_rec"`
	DefTd             *int `json:"def_td"`
}

// NFLSimPlayerCorrelations: Symmetric pairwise Pearson correlation matrix (`r_{i,j} in [-1.0, +1.0]`) for active players (resolved via top-level `players` lookup) and both team DST units (negative `-team_id` sentinels).
type NFLSimPlayerCorrelations struct {
	PlayerIDs []int64     `json:"player_ids"`
	Matrix    [][]float64 `json:"matrix"`
}

// NFLSimGameDrive: One offensive drive summary in `pbp.drives`.
type NFLSimGameDrive struct {
	DriveNumber      int    `json:"drive_number"`
	PossessionTeamID int64  `json:"possession_team_id"`
	QuarterStart     int    `json:"quarter_start"`
	QuarterEnd       int    `json:"quarter_end"`
	ClockStart       string `json:"clock_start"`
	ClockEnd         string `json:"clock_end"`
	TimeOfPossession string `json:"time_of_possession"`
	StartYardline    string `json:"start_yardline"`
	EndYardline      string `json:"end_yardline"`
	StartReason      string `json:"start_reason"`
	Result           string `json:"result"`
	PlayCount        int    `json:"play_count"`
	NetYards         int    `json:"net_yards"`
	FirstDowns       int    `json:"first_downs"`
	Inside20         bool   `json:"inside_20"`
	ScoringDrive     bool   `json:"scoring_drive"`
	FirstPlayID      int64  `json:"first_play_id"`
	LastPlayID       int64  `json:"last_play_id"`
}

// NFLSimPlaySituation: Pre-snap (`start_situation`) or post-snap (`end_situation`) field and clock situation on a play.
type NFLSimPlaySituation struct {
	Quarter                 int    `json:"quarter"`
	Clock                   string `json:"clock"`
	QuarterSecondsRemaining int    `json:"quarter_seconds_remaining"`
	PossessionTeamID        int64  `json:"possession_team_id"`
	DefensiveTeamID         int64  `json:"defensive_team_id"`
	Down                    *int   `json:"down"`
	Ydstogo                 int    `json:"ydstogo"`
	Yardline100             int    `json:"yardline_100"`
	Yardline                string `json:"yardline"`
	GoalToGo                bool   `json:"goal_to_go"`
}

// NFLSimPlayStat: One player's participation, role, and sparse non-zero stat attribution on a single play (`pbp.plays[j].play_stats[m]`).
type NFLSimPlayStat struct {
	Sequence          int      `json:"sequence"`
	PlayerID          int64    `json:"player_id"`
	TeamID            int64    `json:"team_id"`
	Category          string   `json:"category"`
	Role              string   `json:"role"`
	PassAtt           *int     `json:"pass_att"`
	PassCmp           *int     `json:"pass_cmp"`
	PassYd            *int     `json:"pass_yd"`
	AirYd             *int     `json:"air_yd"`
	PassTd            *int     `json:"pass_td"`
	IntThrown         *int     `json:"int_thrown"`
	SacksTaken        *int     `json:"sacks_taken"`
	SackYdLost        *int     `json:"sack_yd_lost"`
	RushAtt           *int     `json:"rush_att"`
	RushYd            *int     `json:"rush_yd"`
	RushTd            *int     `json:"rush_td"`
	IsScramble        *bool    `json:"is_scramble"`
	Targets           *int     `json:"targets"`
	Rec               *int     `json:"rec"`
	RecYd             *int     `json:"rec_yd"`
	YacYd             *int     `json:"yac_yd"`
	RecTd             *int     `json:"rec_td"`
	Fumbles           *int     `json:"fumbles"`
	FumblesLost       *int     `json:"fumbles_lost"`
	TwoPtConv         *int     `json:"two_pt_conv"`
	FgAtt             *int     `json:"fg_att"`
	FgMade            *int     `json:"fg_made"`
	XpAtt             *int     `json:"xp_att"`
	XpMade            *int     `json:"xp_made"`
	KickDistance      *int     `json:"kick_distance"`
	SoloTackles       *int     `json:"solo_tackles"`
	AstTackles        *int     `json:"ast_tackles"`
	SoloSacks         *int     `json:"solo_sacks"`
	SplitSacks        *int     `json:"split_sacks"`
	SackYd            *int     `json:"sack_yd"`
	Tfl               *int     `json:"tfl"`
	QbHits            *int     `json:"qb_hits"`
	Pressures         *int     `json:"pressures"`
	TargetsCoverage   *int     `json:"targets_coverage"`
	ReceptionsAllowed *int     `json:"receptions_allowed"`
	RecYdAllowed      *int     `json:"rec_yd_allowed"`
	PassesDefended    *int     `json:"passes_defended"`
	DefInt            *int     `json:"def_int"`
	ForcedFumbles     *int     `json:"forced_fumbles"`
	FumbleRec         *int     `json:"fumble_rec"`
	DefTd             *int     `json:"def_td"`
	DkPts             *float64 `json:"dk_pts"`
	FdPts             *float64 `json:"fd_pts"`
}

// NFLSimPlayByPlay: One play in `pbp.plays` with `start_situation`, `end_situation`, on-field personnel, route runners, and multi-player `play_stats`.
type NFLSimPlayByPlay struct {
	PlayID            int64               `json:"play_id"`
	DriveNumber       int                 `json:"drive_number"`
	PlayType          string              `json:"play_type"`
	Description       string              `json:"description"`
	StartSituation    NFLSimPlaySituation `json:"start_situation"`
	EndSituation      NFLSimPlaySituation `json:"end_situation"`
	OffensePersonnel  *string             `json:"offense_personnel"`
	DefensePackage    *string             `json:"defense_package"`
	DefendersInBox    *int                `json:"defenders_in_box"`
	Shotgun           bool                `json:"shotgun"`
	NoHuddle          bool                `json:"no_huddle"`
	QbDropback        bool                `json:"qb_dropback"`
	QbScramble        bool                `json:"qb_scramble"`
	WasPressure       bool                `json:"was_pressure"`
	PassLength        *string             `json:"pass_length"`
	PassLocation      *string             `json:"pass_location"`
	AirYards          *int                `json:"air_yards"`
	YardsAfterCatch   *int                `json:"yards_after_catch"`
	RunLocation       *string             `json:"run_location"`
	RunGap            *string             `json:"run_gap"`
	YardsGained       int                 `json:"yards_gained"`
	DriveNetYards     int                 `json:"drive_net_yards"`
	FirstDown         bool                `json:"first_down"`
	Touchdown         bool                `json:"touchdown"`
	Turnover          bool                `json:"turnover"`
	Sack              bool                `json:"sack"`
	QbHit             bool                `json:"qb_hit"`
	TackledForLoss    bool                `json:"tackled_for_loss"`
	AwayScoreBefore   int                 `json:"away_score_before"`
	HomeScoreBefore   int                 `json:"home_score_before"`
	AwayScoreAfter    int                 `json:"away_score_after"`
	HomeScoreAfter    int                 `json:"home_score_after"`
	OnFieldOffenseIDs []int64             `json:"on_field_offense_ids"`
	RouteRunnerIDs    []int64             `json:"route_runner_ids"`
	PlayStats         []NFLSimPlayStat    `json:"play_stats"`
}

// NFLSimGamePbp: Nested Play-by-Play (`output.simulations.games[i].pbp`) containing drive summaries (`drives`) and snap-by-snap plays (`plays`) with multi-player `play_stats`.
type NFLSimGamePbp struct {
	Drives []NFLSimGameDrive  `json:"drives"`
	Plays  []NFLSimPlayByPlay `json:"plays"`
}

// NFLSimGameScenario: One pure full-game box score scenario (`scenario_id: 1` is the highest-probability joint-median expected scenario).
type NFLSimGameScenario struct {
	ScenarioID         int64                               `json:"scenario_id"`
	DrawIdx            int                                 `json:"draw_idx"`
	Probability        float64                             `json:"probability"`
	ClusterDrawCount   int                                 `json:"cluster_draw_count"`
	Scoreboard         NFLSimScenarioScoreboard            `json:"scoreboard"`
	Teams              map[string]NFLSimTeamBoxScoreStat   `json:"teams"`
	Players            map[string]NFLSimPlayerBoxScoreStat `json:"players"`
	PlayerCorrelations NFLSimPlayerCorrelations            `json:"player_correlations"`
	Pbp                *NFLSimGamePbp                      `json:"pbp"`
}

// NFLSimScenariosSection: Normalized full-game box score scenarios (`output.scenarios`): top-level `teams` and `players` input lookups, plus probability-weighted scenarios starting with `scenario_id: 1` (the highest-probability joint-median expected outcome).
type NFLSimScenariosSection struct {
	Mode              string                                 `json:"mode"`
	NMonteCarloDraws  int                                    `json:"n_monte_carlo_draws"`
	ScenariosReturned int                                    `json:"scenarios_returned"`
	ProbabilitySum    float64                                `json:"probability_sum"`
	Teams             map[string]NFLSimTeam                  `json:"teams"`
	Players           map[string]NFLSimScenarioPlayerProfile `json:"players"`
	Scenarios         []NFLSimGameScenario                   `json:"scenarios"`
}

// NFLSimSimulatedGame: One complete simulated NFL game (`output.simulations.games[i]`).
type NFLSimSimulatedGame struct {
	SimID      int64                               `json:"sim_id"`
	DrawIdx    int                                 `json:"draw_idx"`
	Scoreboard NFLSimScenarioScoreboard            `json:"scoreboard"`
	Teams      map[string]NFLSimTeamBoxScoreStat   `json:"teams"`
	Players    map[string]NFLSimPlayerBoxScoreStat `json:"players"`
	Pbp        *NFLSimGamePbp                      `json:"pbp"`
}

// NFLSimSimulationsSection: Full-game simulation rollouts (`output.simulations`) containing all simulated games (`0..n_simulations`) and optional nested multi-player Play-by-Play (`pbp`).
type NFLSimSimulationsSection struct {
	NSimulations int                   `json:"n_simulations"`
	PbpIncluded  bool                  `json:"pbp_included"`
	Games        []NFLSimSimulatedGame `json:"games"`
}

// NFLSimGranularOutput: The sections of a run (`summary`, `distributions`, `scorebox`, `pbp`) that you chose in `include`, inside NFLSimPublicRunResponse.output.
type NFLSimGranularOutput struct {
	GameID         int64                       `json:"game_id"`
	NDraws         int                         `json:"n_draws"`
	Seed           uint64                      `json:"seed"`
	SolveElapsedMs float64                     `json:"solve_elapsed_ms"`
	SimElapsedMs   float64                     `json:"sim_elapsed_ms"`
	Summary        *NFLSimSummarySection       `json:"summary"`
	Distributions  *NFLSimDistributionsSection `json:"distributions"`
	Scorebox       *NFLSimScoreboxSection      `json:"scorebox"`
	Pbp            []NFLSimTracedGame          `json:"pbp"`
	Scenarios      *NFLSimScenariosSection     `json:"scenarios"`
	Simulations    *NFLSimSimulationsSection   `json:"simulations"`
}

// NFLSimPublicRunResponse: Stripped public v1 response returned by POST /api/v1/sim/nfl/run.
type NFLSimPublicRunResponse struct {
	Matchup     NFLMatchupSummary    `json:"matchup"`
	InputSha256 string               `json:"input_sha256"`
	Output      NFLSimGranularOutput `json:"output"`
	Quota       Quota                `json:"-"`
}

// SimNFLService accesses the /api/v1/sim/nfl/* and /api/v1/nfl/weeks endpoints.
type SimNFLService struct {
	client *Client
}

// Weeks lists available NFL weeks, matchup summaries, and FreeGameID (43418).
//
// Reference: https://stat-api.com/docs/api/nfl/seasons/#endpoint-nfl-seasons-weeks
// Tutorial: recipes 1, 2, 3, 4, 5 of https://stat-api.com/docs/cookbook/sim/
func (s *SimNFLService) Weeks(ctx context.Context, params *NFLWeeksParams) (*NFLWeeksResponse, error) {
	body, quota, err := s.client.get(ctx, "/api/v1/nfl/weeks", params.values())
	if err != nil {
		return nil, err
	}
	var out NFLWeeksResponse
	if err := json.Unmarshal(body, &out); err != nil {
		return nil, err
	}
	out.Quota = quota
	return &out, nil
}

// Run executes N parallel Monte Carlo simulations (POST /api/v1/sim/nfl/run).
//
// Reference: https://stat-api.com/docs/api/sim/#endpoint-sim-nfl-run
// Tutorial: recipes 1, 2, 3, 4, 5 of https://stat-api.com/docs/cookbook/sim/
func (s *SimNFLService) Run(ctx context.Context, req *NFLSimPublicRunRequest) (*NFLSimPublicRunResponse, error) {
	body, quota, err := s.client.post(ctx, "/api/v1/sim/nfl/run", req)
	if err != nil {
		return nil, err
	}
	var out NFLSimPublicRunResponse
	if err := json.Unmarshal(body, &out); err != nil {
		return nil, err
	}
	out.Quota = quota
	return &out, nil
}
