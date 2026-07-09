// /standings — NBA standings, grouped by conference (v1: NBA only).
import type { StatApi } from '@stat-api/client';
import { cache, cacheKey, TTL } from '../cache';
import { standingsEmbed, statFooter, type Embed, type StandingRow, type StandingsGroup } from '../format';

export interface StandingsArgs {
  league: 'nba';
}

export async function handleStandings(api: StatApi, _args: StandingsArgs): Promise<Embed> {
  const { value, ...meta } = await cache.fetch(cacheKey('standings', 'nba'), TTL.standings, () => fetchStandings(api));
  return standingsEmbed(value, statFooter(meta));
}

async function fetchStandings(api: StatApi): Promise<{ league: 'nba'; day: number; groups: StandingsGroup[] }> {
  // Unpaginated list is newest-first, so the current day's rows lead. Grab a
  // window comfortably larger than the 30-team slate, then keep only the most
  // recent day and de-dupe to one row per team.
  const { team_standings } = await api.nba.team_standings.list({ limit: 60 });
  const maxDay = team_standings.reduce((m, s) => Math.max(m, s.day), 0);
  const latest = new Map<number, (typeof team_standings)[number]>();
  for (const s of team_standings) {
    if (s.day === maxDay && !latest.has(s.team_id)) latest.set(s.team_id, s);
  }

  const { teams } = await api.nba.teams.list({ limit: 60 });
  const teamById = new Map(teams.map((t) => [t.id, t]));

  const byConference = new Map<string, StandingRow[]>();
  for (const s of latest.values()) {
    const team = teamById.get(s.team_id);
    const conference = team?.conference ?? 'League';
    const rows = byConference.get(conference) ?? [];
    rows.push({ rank: 0, abbrev: team?.abbreviation ?? `#${s.team_id}`, wins: s.wins, losses: s.losses, pct: s.win_pct });
    byConference.set(conference, rows);
  }

  const groups: StandingsGroup[] = [...byConference.entries()]
    .sort(([a], [b]) => a.localeCompare(b))
    .map(([conference, rows]) => {
      rows.sort((a, b) => Number(b.pct) - Number(a.pct) || b.wins - a.wins);
      rows.forEach((r, i) => (r.rank = i + 1));
      return { title: `${conference} Conference`, rows: rows.slice(0, 8) };
    });

  return { league: 'nba', day: maxDay, groups };
}
