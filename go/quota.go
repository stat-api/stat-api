package statapi

import (
	"net/http"
	"strconv"
)

// Quota is a snapshot of the caller's monthly record quota, parsed from the
// X-Quota-* headers stamped on every API response. Present is false when the
// response carried no quota headers; the counts are zero in that case.
type Quota struct {
	Limit     int64
	Used      int64
	Remaining int64
	Present   bool
}

func parseQuota(h http.Header) Quota {
	limit, okLimit := int64Header(h, "X-Quota-Limit")
	used, okUsed := int64Header(h, "X-Quota-Used")
	remaining, okRemaining := int64Header(h, "X-Quota-Remaining")
	if !okLimit && !okUsed && !okRemaining {
		return Quota{}
	}
	return Quota{Limit: limit, Used: used, Remaining: remaining, Present: true}
}

func int64Header(h http.Header, name string) (int64, bool) {
	raw := h.Get(name)
	if raw == "" {
		return 0, false
	}
	n, err := strconv.ParseInt(raw, 10, 64)
	if err != nil {
		return 0, false
	}
	return n, true
}
