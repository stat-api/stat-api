// Read a slate's salaries — DFS
// Generated from schema/api/examples/dfs-salary-board.yml — do not edit.
package main

import (
	"context"
	"fmt"
	statapi "github.com/stat-api/stat-api/go"
	"log"
	"reflect"
	"sort"
	"strings"
)

// str renders any value (dereferencing nil pointers to an empty cell).
func str(v any) string {
	if v == nil {
		return ""
	}
	rv := reflect.ValueOf(v)
	if rv.Kind() == reflect.Ptr {
		if rv.IsNil() {
			return ""
		}
		return fmt.Sprintf("%v", rv.Elem().Interface())
	}
	return fmt.Sprintf("%v", v)
}

func main() {
	client, err := statapi.New()
	if err != nil {
		log.Fatal(err)
	}
	ctx := context.Background()

	// Page the whole player pool
	var pool []statapi.DFSSlatePlayer
	for row, err := range client.DFS.SlatePlayers.All(ctx, &statapi.DFSSlatePlayersListParams{SlateID: statapi.Int64(int64(129036))}) {
		if err != nil {
			log.Fatal(err)
		}
		pool = append(pool, row)
	}

	// Rank by salary, most expensive first
	bysalary := append([]statapi.DFSSlatePlayer(nil), pool...)
	bysalaryKey := func(r statapi.DFSSlatePlayer) int {
		return r.Salary
	}
	sort.Slice(bysalary, func(i, j int) bool {
		return bysalaryKey(bysalary[j]) < bysalaryKey(bysalary[i])
	})

	// Take the ten priciest players
	top := bysalary
	if len(top) > 10 {
		top = top[:10]
	}

	// Print the salary board
	fmt.Println("Highest-salaried players on the slate")
	fmt.Println(strings.Join([]string{"display_name", "position", "salary", "avg_points"}, "\t"))
	for _, row := range top {
		fmt.Println(strings.Join([]string{str(row.DisplayName), str(row.Position), str(row.Salary), str(row.AvgPoints)}, "\t"))
	}

}
