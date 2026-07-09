// Paginate by hand with from_id — NBA
// Generated from schema/api/examples/pagination-manual.yml — do not edit.
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

	// Follow next_from_id until it is null
	ctx := context.Background()
	var fromID *int64
	total := 0
	pageNum := 0
	for {
	    page, err := client.NBA.Teams.List(ctx, &statapi.NBATeamsListParams{Limit: statapi.Int(100), FromID: fromID})
	    if err != nil {
	        log.Fatal(err)
	    }
	    pageNum++
	    total += len(page.Rows)
	    fmt.Printf("page %d: %d rows, next cursor = %v\n", pageNum, len(page.Rows), page.NextFromID)
	    if page.NextFromID == nil {
	        break
	    }
	    fromID = page.NextFromID
	}
	fmt.Printf("walked %d teams across %d pages by hand\n", total, pageNum)

}
