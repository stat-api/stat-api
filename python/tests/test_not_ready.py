"""Tests for the 202 Accepted rule of the statapi Python SDK.

A 202 holds NO data, so the transport raises NotReadyError instead of returning
the body as the route's data, and never retries it. The tests run against a
local fake HTTP server (stdlib only, ephemeral port, 127.0.0.1): no live API.

Run from sdks/python:  python3 -m unittest discover -s tests
                  or:  python3 -m pytest tests
"""

from __future__ import annotations

import json
import os
import sys
import threading
import unittest
from http.server import BaseHTTPRequestHandler, HTTPServer
from typing import Any
from unittest import mock

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "src"))

from statapi import NotReadyError, StatApi, StatApiError  # noqa: E402
from statapi.errors import NotFoundError  # noqa: E402

QUEUED_BODY: dict[str, Any] = {
    "status": "queued",
    "message": "The lineups of this contest are queued to load.",
    "contest_id": 29647259,
    "external_id": 196420231,
    "requested_at": "2026-10-12T02:26:11Z",
    "estimated_ready_at": "2026-10-12T02:42:00Z",
    "retry_after_seconds": 120,
    "entry_count": 340,
}

# One scripted reply: (status, body, headers). A str body is sent as raw text.
Reply = tuple[int, Any, dict[str, str]]


class _FakeServer(HTTPServer):
    def __init__(self) -> None:
        super().__init__(("127.0.0.1", 0), _Handler)
        self.replies: list[Reply] = []
        self.paths: list[str] = []

    @property
    def base_url(self) -> str:
        return f"http://127.0.0.1:{self.server_address[1]}"


class _Handler(BaseHTTPRequestHandler):
    server: _FakeServer  # type: ignore[assignment]

    def do_GET(self) -> None:  # noqa: N802 - http.server API
        self._respond()

    def do_POST(self) -> None:  # noqa: N802 - http.server API
        length = int(self.headers.get("Content-Length", "0"))
        if length > 0:
            _ = self.rfile.read(length)
        self._respond()

    def _respond(self) -> None:
        self.server.paths.append(self.path)
        status, body, headers = (
            self.server.replies.pop(0) if self.server.replies else (200, {}, {})
        )
        payload = (body if isinstance(body, str) else json.dumps(body)).encode("utf-8")
        self.send_response(status)
        self.send_header("Content-Type", "application/json")
        self.send_header("Content-Length", str(len(payload)))
        for name, value in headers.items():
            self.send_header(name, value)
        self.end_headers()
        self.wfile.write(payload)

    def log_message(self, format: str, *args: Any) -> None:  # noqa: A002
        return


class NotReadyTest(unittest.TestCase):
    def setUp(self) -> None:
        # Keep the fake server off any proxy configured in the environment.
        patcher = mock.patch.dict(
            os.environ, {"no_proxy": "127.0.0.1", "NO_PROXY": "127.0.0.1"}
        )
        patcher.start()
        self.addCleanup(patcher.stop)
        self.server = _FakeServer()
        thread = threading.Thread(
            target=self.server.serve_forever, kwargs={"poll_interval": 0.01}, daemon=True
        )
        thread.start()
        self.addCleanup(thread.join)
        self.addCleanup(self.server.server_close)
        self.addCleanup(self.server.shutdown)
        self.api = StatApi(api_key="test-key", base_url=self.server.base_url)
        sleeper = mock.patch("statapi.transport._sleep_backoff")
        self.sleep = sleeper.start()
        self.addCleanup(sleeper.stop)

    def reply(self, status: int, body: Any = None, **headers: str) -> None:
        self.server.replies.append((status, {} if body is None else body, headers))

    def test_202_raises_not_ready_with_every_field_and_is_not_retried(self) -> None:
        self.reply(202, QUEUED_BODY, **{"Retry-After": "120"})
        with self.assertRaises(NotReadyError) as caught:
            self.api.dfs.contests.get(29647259)
        err = caught.exception
        self.assertIsInstance(err, StatApiError)
        self.assertEqual(err.status, 202)
        self.assertEqual(err.path, "/api/v1/dfs/contests/29647259")
        self.assertEqual(err.retry_after_seconds, 120)
        self.assertEqual(err.estimated_ready_at, "2026-10-12T02:42:00Z")
        self.assertEqual(err.state, "queued")
        self.assertEqual(err.message, "The lineups of this contest are queued to load.")
        self.assertEqual(err.body, QUEUED_BODY)
        self.assertEqual(len(self.server.paths), 1)  # a 202 is not retried
        self.sleep.assert_not_called()

    def test_waiting_for_contest_has_null_estimated_ready_at(self) -> None:
        body = {
            **QUEUED_BODY,
            "status": "waiting_for_contest",
            "estimated_ready_at": None,
            "retry_after_seconds": 900,
        }
        self.reply(202, body)
        with self.assertRaises(NotReadyError) as caught:
            self.api._transport.request("/api/v1/dfs/contests/29647259/standings")
        err = caught.exception
        self.assertEqual(err.state, "waiting_for_contest")
        self.assertIsNone(err.estimated_ready_at)
        self.assertEqual(err.retry_after_seconds, 900)

    def test_retry_after_falls_back_to_the_header_when_the_body_has_none(self) -> None:
        self.reply(202, {"status": "queued", "message": "queued"}, **{"Retry-After": "45"})
        with self.assertRaises(NotReadyError) as caught:
            self.api._transport.request("/api/v1/dfs/contests/1/download")
        self.assertEqual(caught.exception.retry_after_seconds, 45)
        self.assertIsNone(caught.exception.estimated_ready_at)

    def test_body_retry_after_seconds_wins_over_the_header(self) -> None:
        self.reply(202, QUEUED_BODY, **{"Retry-After": "999"})
        with self.assertRaises(NotReadyError) as caught:
            self.api._transport.request("/api/v1/dfs/contests/1/download")
        self.assertEqual(caught.exception.retry_after_seconds, 120)

    def test_zero_seconds_is_kept_not_treated_as_missing(self) -> None:
        self.reply(202, {"status": "queued", "retry_after_seconds": 0}, **{"Retry-After": "30"})
        with self.assertRaises(NotReadyError) as caught:
            self.api._transport.request("/api/v1/dfs/contests/1/download")
        self.assertEqual(caught.exception.retry_after_seconds, 0)

    def test_no_body_value_and_no_usable_header_gives_none(self) -> None:
        self.reply(
            202,
            {"status": "queued"},
            **{"Retry-After": "Wed, 21 Oct 2026 07:28:00 GMT"},
        )
        with self.assertRaises(NotReadyError) as caught:
            self.api._transport.request("/api/v1/dfs/contests/1/download")
        self.assertIsNone(caught.exception.retry_after_seconds)

    def test_a_non_json_202_still_raises_with_a_default_message(self) -> None:
        self.reply(202, "accepted")
        with self.assertRaises(NotReadyError) as caught:
            self.api._transport.request("/api/v1/dfs/contests/1/download")
        err = caught.exception
        self.assertEqual(err.body, "accepted")
        self.assertIsNone(err.state)
        self.assertIsNone(err.estimated_ready_at)
        self.assertIsNone(err.retry_after_seconds)
        self.assertTrue(err.message)
        self.assertIn("202", str(err))

    def test_a_200_still_returns_data(self) -> None:
        data = {"id": 1, "name": "Cardinals"}
        self.reply(200, data)
        row = self.api.nfl.teams.get(1)
        self.assertEqual(row, data)
        self.assertEqual(len(self.server.paths), 1)

    def test_a_500_is_still_retried_until_a_200(self) -> None:
        self.reply(500, {"error": "internal"})
        self.reply(500, {"error": "internal"})
        self.reply(200, {"id": 1})
        row = self.api.nfl.teams.get(1)
        self.assertEqual(row, {"id": 1})
        self.assertEqual(len(self.server.paths), 3)
        self.assertEqual(self.sleep.call_count, 2)

    def test_a_500_that_never_recovers_raises_stat_api_error_after_three_attempts(self) -> None:
        for _ in range(3):
            self.reply(500, {"error": "internal"})
        with self.assertRaises(StatApiError) as caught:
            self.api.nfl.teams.get(1)
        self.assertNotIsInstance(caught.exception, NotReadyError)
        self.assertEqual(caught.exception.status, 500)
        self.assertEqual(len(self.server.paths), 3)

    def test_a_404_is_still_not_found_and_not_retried(self) -> None:
        self.reply(404, {"error": "not-found"})
        with self.assertRaises(NotFoundError):
            self.api.nfl.teams.get(99999)
        self.assertEqual(len(self.server.paths), 1)

    def test_nfl_nba_weeks_and_sim_public_endpoints(self) -> None:
        nfl_run_body = {
            "matchup": {"game_id": 43418},
            "input_sha256": "sha256-nfl",
            "output": {"game_id": 43418, "n_draws": 500, "seed": 20261028},
        }
        nba_run_body = {
            "matchup": {"game_id": 6029936},
            "input_sha256": "sha256-nba",
            "output": {"game_id": "6029936", "n_draws": 500, "seed": 20261028},
        }
        self.reply(
            200,
            {
                "season": 2026,
                "available_seasons": [2026],
                "free_game_id": 43418,
                "weeks": [1],
                "games": [],
            },
        )
        self.reply(200, nfl_run_body)
        self.reply(
            200,
            {
                "season": 2025,
                "available_seasons": [2025],
                "free_game_id": 6029936,
                "weeks": [1],
                "games": [],
            },
        )
        self.reply(200, nba_run_body)

        weeks = self.api.nfl.weeks(season=2026)
        self.assertEqual(weeks["free_game_id"], 43418)
        sim = self.api.sim.nfl.run(game_id=43418, n_draws=500, seed=20261028, total=46.5)
        self.assertEqual(sim["output"]["n_draws"], 500)

        nba_weeks = self.api.nba.weeks(season=2025)
        self.assertEqual(nba_weeks["free_game_id"], 6029936)
        nba_sim = self.api.sim.nba.run(game_id=6029936, n_draws=500, mode="all", total=228.5)
        self.assertEqual(nba_sim["output"]["n_draws"], 500)

        self.assertEqual(
            self.server.paths,
            [
                "/api/v1/nfl/weeks?season=2026",
                "/api/v1/sim/nfl/run",
                "/api/v1/nba/weeks?season=2025",
                "/api/v1/sim/nba/run",
            ],
        )


if __name__ == "__main__":
    unittest.main()
