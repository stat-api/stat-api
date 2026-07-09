// Satisfy a required filter set — DFS
// Generated from schema/api/examples/required-filter-sets.yml — do not edit.
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

	// See the 400, then satisfy the set
	ctx := context.Background()
	// dfs.slates requires the [operator_id, date] set — a bare call is a 400.
	if _, err := client.DFS.Slates.List(ctx, &statapi.DFSSlatesListParams{Limit: statapi.Int(5)}); err != nil {
	    if ve, ok := err.(*statapi.ValidationError); ok {
	        fmt.Printf("rejected (%d): %s\n", ve.Status, ve.Body)
	    }
	}
	// Supply BOTH members of the set and the call is accepted.
	slates, err := client.DFS.Slates.List(ctx, &statapi.DFSSlatesListParams{OperatorID: statapi.Int64(1), Date: statapi.String("2026-07-02")})
	if err != nil {
	    log.Fatal(err)
	}
	fmt.Printf("operator 1 ran %d slates on 2026-07-02\n", len(slates.Rows))

}
