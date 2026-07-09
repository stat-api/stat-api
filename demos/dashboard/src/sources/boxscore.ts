// Box-score source: one MLB game's batting lines → contract `BoxLine[]`.
//
// The contract's `BoxLine` is a compact, batting-shaped set, so this covers MLB
// only — the one league in season through the summer. NBA/NFL box shapes would
// need a wider contract and are left empty (they are offseason anyway).
//
// One `game_player_batter_stats?game_id=<id>` list per game. Player names and
// positions come from team rosters, fetched once per team and cached across the
// whole run by `RosterCache` — so a busy MLB evening resolves every batter with
// at most one roster call per team, not one per box line.
import type { BoxLine } from '../contract.ts';
import type { StatApiClient } from '../sdk.ts';
import type { TeamMap } from './scoreboard.ts';

interface PlayerInfo {
  readonly name: string;
  readonly position: string;
}

/** Lazily-warmed id → name/position cache backed by team-roster lists. Held for
 *  the life of the poller so rosters are fetched at most once per team. */
export class RosterCache {
  private readonly players = new Map<number, PlayerInfo>();
  private readonly loadedTeams = new Set<number>();

  constructor(
    private readonly api: StatApiClient,
    private readonly limit: number,
  ) {}

  /** Ensure every given team's roster is loaded; a no-op for teams already cached. */
  async ensureTeams(teamIds: Iterable<number>): Promise<void> {
    for (const teamId of teamIds) {
      if (this.loadedTeams.has(teamId)) continue;
      const { players } = await this.api.mlb.players.list({ team_id: teamId, limit: this.limit });
      for (const p of players) {
        this.players.set(p.id, { name: p.full_name, position: p.primary_position });
      }
      this.loadedTeams.add(teamId);
    }
  }

  get(playerId: number): PlayerInfo | undefined {
    return this.players.get(playerId);
  }
}

/** Fetch one MLB game's batting box, resolving team abbreviations and player names. */
export async function fetchBox(
  api: StatApiClient,
  gameId: number,
  teams: TeamMap,
  rosters: RosterCache,
  limit: number,
): Promise<BoxLine[]> {
  const { game_player_batter_stats } = await api.mlb.game_player_batter_stats.list({
    game_id: gameId,
    limit,
  });
  const rows = game_player_batter_stats;
  await rosters.ensureTeams(new Set(rows.map(r => r.team_id)));

  return rows
    .map(r => {
      const player = rosters.get(r.player_id);
      return {
        team: teams.get(r.team_id)?.abbr ?? `T${r.team_id}`,
        player: player?.name ?? `Player ${r.player_id}`,
        position: player?.position ?? '',
        ab: r.at_bats,
        r: r.runs,
        h: r.hits,
        rbi: r.runs_batted_in,
        hr: r.home_runs,
        bb: r.walks,
        so: r.strikeouts,
        avg: r.batting_average,
      } satisfies BoxLine;
    })
    .sort(
      (a, b) => a.team.localeCompare(b.team) || b.ab - a.ab || b.h - a.h || a.player.localeCompare(b.player),
    );
}
