// Export NBA Classic DFS lineups as ready-to-upload CSV
// Generated from schema/api/examples/dfs-optimizer-nba-csv.yml — do not edit.
package main

import (
	"context"
	"fmt"
	statapi "github.com/stat-api/stat-api/go"
	"log"
	"strings"
)

func main() {
	client, err := statapi.New()
	if err != nil {
		log.Fatal(err)
	}
	ctx := context.Background()

	// Generate NBA Classic lineups in CSV format
	csvText, err := client.DFS.Slates.OptimizerLineupsCSV(ctx, 1243812, &statapi.OptimizerLineupsRequest{LineupCount: statapi.Int(3), MinUniquePlayers: statapi.Int(2)})
	if err != nil {
		log.Fatal(err)
	}

	// Preview exported CSV header and rows
	var csvLines []string
	for _, s := range strings.Split(csvText, "\n") {
		t := strings.TrimRight(s, "\r")
		if t != "" {
			csvLines = append(csvLines, t)
		}
	}
	fmt.Printf("CSV lines=%d\n", len(csvLines))
	limit := 4
	if len(csvLines) < limit {
		limit = len(csvLines)
	}
	for _, line := range csvLines[:limit] {
		fmt.Println(line)
	}

}
