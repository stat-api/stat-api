// Find the main slate for a day — DFS
// Generated from schema/api/examples/dfs-main-slate.yml — do not edit.
package main

import (
	"context"
	"fmt"
	statapi "github.com/stat-api/stat-api/go"
	"log"
	"reflect"
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

	// List one operator's slates for one day
	daysPage, err := client.DFS.Slates.List(ctx, &statapi.DFSSlatesListParams{OperatorID: statapi.Int64(int64(1)), Date: statapi.String("2025-11-30"), League: statapi.String("nfl")})
	if err != nil {
		log.Fatal(err)
	}
	days := daysPage.Rows

	// Keep the main slate
	mains := make([]statapi.DFSSlate, 0, len(days))
	for _, r := range days {
		if r.Main {
			mains = append(mains, r)
		}
	}

	// Read the slate id
	fmt.Println("Main slate")
	fmt.Println(strings.Join([]string{"id", "name", "date", "start_time"}, "\t"))
	for _, row := range mains {
		fmt.Println(strings.Join([]string{str(row.ID), str(row.Name), str(row.Date), str(row.StartTime)}, "\t"))
	}

}
