package main

import (
	"context"
	"fmt"
	"io"

	statapi "github.com/stat-api/stat-api/go"
)

// runQuota reports the caller's monthly record quota. It makes the cheapest
// possible metered call (a one-row list) and reads the X-Quota-* headers the
// SDK surfaces on every page.
func runQuota(ctx context.Context, c *statapi.Client, out io.Writer) error {
	page, err := c.NBA.Teams.List(ctx, &statapi.NBATeamsListParams{Limit: statapi.Int(1)})
	if err != nil {
		return err
	}
	q := page.Quota
	if !q.Present {
		fmt.Fprintln(out, "No quota headers on the response (unmetered key or local server).")
		return nil
	}
	tw := newTable(out)
	fmt.Fprintf(tw, "Limit\t%d\n", q.Limit)
	fmt.Fprintf(tw, "Used\t%d\n", q.Used)
	fmt.Fprintf(tw, "Remaining\t%d\n", q.Remaining)
	tw.Flush()
	return nil
}
