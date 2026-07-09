// Handle errors by type — NBA
// Generated from schema/api/examples/error-handling.yml — do not edit.
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

	// Trigger a 404 and branch on the error
	ctx := context.Background()
	_, err = client.NBA.Teams.Get(ctx, 999999999)
	switch e := err.(type) {
	case nil:
	    fmt.Println("unexpectedly found a team")
	case *statapi.NotFoundError:
	    fmt.Printf("404 NotFoundError: no such team (%d)\n", e.Status)
	case *statapi.AuthenticationError:
	    fmt.Println("401 AuthenticationError: bad or missing API key")
	case *statapi.ValidationError:
	    fmt.Printf("400 ValidationError: %s\n", e.Body)
	case *statapi.QuotaExceededError:
	    fmt.Println("429 QuotaExceededError: monthly quota spent — never retried")
	default:
	    log.Fatal(err)
	}

}
