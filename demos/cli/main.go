// Command sports is a small, fast terminal client for the public stat-api,
// built on the statapi Go SDK as a dogfooding demo. It has zero third-party
// dependencies: stdlib flag + text/tabwriter only.
//
//	sports today                          today's slate across leagues
//	sports scores --league mlb --date …   one league's scoreboard
//	sports box --league nba <game-id>     a game's box score
//	sports standings [--league nba]       league standings
//	sports player <name>                  NBA player name search + season line
//	sports quota                          current API quota
//
// The client reads STAT_API_KEY (required) and STAT_API_BASE_URL (optional,
// defaults to https://api.stat-api.com) from the environment, exactly like the
// SDK.
package main

import (
	"context"
	"flag"
	"fmt"
	"io"
	"os"
	"strconv"
	"strings"
	"time"

	statapi "github.com/stat-api/stat-api/go"
)

const requestTimeout = 60 * time.Second

func main() {
	ctx, cancel := context.WithTimeout(context.Background(), requestTimeout)
	defer cancel()

	if err := run(ctx, os.Args[1:], os.Stdout); err != nil {
		fmt.Fprintln(os.Stderr, "sports: "+formatError(err))
		os.Exit(1)
	}
}

// run dispatches one invocation. It is the single seam the tests drive: they
// build a client against an httptest server and call the command functions
// directly, so run stays a thin argument-parsing + wiring layer.
func run(ctx context.Context, args []string, out io.Writer) error {
	if len(args) == 0 {
		usage(out)
		return nil
	}

	cmd, rest := args[0], args[1:]
	switch cmd {
	case "help", "-h", "--help":
		usage(out)
		return nil
	case "version", "--version":
		fmt.Fprintf(out, "sports (statapi-go %s)\n", statapi.Version)
		return nil
	}

	baseURL, apiKey := resolveEnv()
	client, err := statapi.NewWithOptions(statapi.Options{APIKey: apiKey, BaseURL: baseURL})
	if err != nil {
		return err
	}

	switch cmd {
	case "today":
		fs := flag.NewFlagSet("today", flag.ContinueOnError)
		fs.SetOutput(out)
		if err := fs.Parse(rest); err != nil {
			return err
		}
		return runToday(ctx, client, out, todayET())

	case "scores":
		fs := flag.NewFlagSet("scores", flag.ContinueOnError)
		fs.SetOutput(out)
		league := fs.String("league", "", "league: mlb, nba, or nfl")
		date := fs.String("date", "", "date as YYYYMMDD (mlb/nba; defaults to today)")
		week := fs.Int("week", 0, "week number (nfl; defaults to the current week)")
		if err := fs.Parse(rest); err != nil {
			return err
		}
		if *league == "" {
			return fmt.Errorf("scores requires --league (mlb, nba, or nfl)")
		}
		day, err := parseDate(*date)
		if err != nil {
			return err
		}
		return runScores(ctx, client, out, strings.ToLower(*league), day, *week)

	case "box":
		fs := flag.NewFlagSet("box", flag.ContinueOnError)
		fs.SetOutput(out)
		league := fs.String("league", "mlb", "league: mlb or nba")
		if err := fs.Parse(rest); err != nil {
			return err
		}
		ids := fs.Args()
		if len(ids) == 0 {
			return fmt.Errorf("box requires a game id, e.g. sports box --league nba 22025")
		}
		gameID, err := strconv.ParseInt(ids[0], 10, 64)
		if err != nil {
			return fmt.Errorf("invalid game id %q", ids[0])
		}
		return runBox(ctx, client, out, strings.ToLower(*league), gameID)

	case "standings":
		fs := flag.NewFlagSet("standings", flag.ContinueOnError)
		fs.SetOutput(out)
		league := fs.String("league", "nba", "league: nba")
		if err := fs.Parse(rest); err != nil {
			return err
		}
		return runStandings(ctx, client, out, strings.ToLower(*league))

	case "player":
		fs := flag.NewFlagSet("player", flag.ContinueOnError)
		fs.SetOutput(out)
		if err := fs.Parse(rest); err != nil {
			return err
		}
		name := strings.TrimSpace(strings.Join(fs.Args(), " "))
		if name == "" {
			return fmt.Errorf("player requires a name, e.g. sports player \"LeBron James\"")
		}
		return runPlayer(ctx, client, out, baseURL, apiKey, name)

	case "quota":
		return runQuota(ctx, client, out)

	default:
		return fmt.Errorf("unknown command %q (run 'sports help')", cmd)
	}
}

// resolveEnv mirrors the SDK's environment resolution so the raw player-search
// helper (which the generated SDK does not cover) shares one base URL + key.
func resolveEnv() (baseURL, apiKey string) {
	baseURL = os.Getenv("STAT_API_BASE_URL")
	if baseURL == "" {
		baseURL = "https://api.stat-api.com"
	}
	return baseURL, os.Getenv("STAT_API_KEY")
}

// parseDate turns a YYYYMMDD flag into the integer the games endpoints expect.
// An empty string yields 0, which callers treat as "use today".
func parseDate(s string) (int, error) {
	if s == "" {
		return 0, nil
	}
	if len(s) != 8 {
		return 0, fmt.Errorf("invalid --date %q (want YYYYMMDD)", s)
	}
	d, err := strconv.Atoi(s)
	if err != nil {
		return 0, fmt.Errorf("invalid --date %q (want YYYYMMDD)", s)
	}
	return d, nil
}

// todayET returns today's date as YYYYMMDD in US Eastern time — the timezone the
// games endpoints key their `day` filter on.
func todayET() int {
	now := time.Now()
	if loc, err := time.LoadLocation("America/New_York"); err == nil {
		now = now.In(loc)
	}
	y, m, d := now.Date()
	return y*10000 + int(m)*100 + d
}

func usage(out io.Writer) {
	fmt.Fprint(out, `sports — a terminal client for the stat-api sports data API

Usage:
  sports today
  sports scores --league mlb|nba|nfl [--date YYYYMMDD] [--week N]
  sports box --league mlb|nba <game-id>
  sports standings [--league nba]
  sports player <name>
  sports quota

Environment:
  STAT_API_KEY        API key (required)
  STAT_API_BASE_URL   API host (optional; default https://api.stat-api.com)
`)
}
