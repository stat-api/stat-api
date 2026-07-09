// Rank a DFS slate by salary and value
// Generated from schema/api/examples/slate-to-salaries.yml — do not edit.
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

	// Page the slate's players
	var players []statapi.DFSSlatePlayer
	for row, err := range client.DFS.SlatePlayers.All(ctx, &statapi.DFSSlatePlayersListParams{SlateID: statapi.Int64(int64(91396))}) {
		if err != nil {
			log.Fatal(err)
		}
		players = append(players, row)
	}

	// Rank by salary, highest first
	bysalary := append([]statapi.DFSSlatePlayer(nil), players...)
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

	// Fetch the top player's projection
	projPage, err := client.DFS.SlatePlayerProjections.List(ctx, &statapi.DFSSlatePlayerProjectionsListParams{SlatePlayerID: statapi.Int64(int64(top[0].ID))})
	if err != nil {
		log.Fatal(err)
	}
	proj := projPage.Rows

	// Compute projected points per $1000 of salary
	if len(proj) > 0 && len(top) > 0 {
	    value := (proj[0].Projection / float64(top[0].Salary)) * 1000
	    fmt.Printf("value of highest-salaried player = %.2f projected pts per $1000\n", value)
	}

	// Print the salary board
	fmt.Println("Highest-salaried players on the slate")
	fmt.Println(strings.Join([]string{"display_name", "position", "salary"}, "\t"))
	for _, row := range top {
		fmt.Println(strings.Join([]string{str(row.DisplayName), str(row.Position), str(row.Salary)}, "\t"))
	}

}
