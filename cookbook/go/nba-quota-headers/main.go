// Read your quota off a response — NBA
// Generated from schema/api/examples/quota-headers.yml — do not edit.
package main

import (
	"context"
	"fmt"
	statapi "github.com/stat-api/stat-api/go"
	"log"
)

func main() {
	client, err := statapi.New()
	if err != nil {
		log.Fatal(err)
	}

	// List a page and read its quota
	ctx := context.Background()
	page, err := client.NBA.Teams.List(ctx, &statapi.NBATeamsListParams{Limit: statapi.Int(3)})
	if err != nil {
	    log.Fatal(err)
	}
	if page.Quota.Present {
	    fmt.Printf("quota: %d of %d records left this month\n", page.Quota.Remaining, page.Quota.Limit)
	} else {
	    fmt.Println("no quota headers on this response")
	}

}
