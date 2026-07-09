// /player <name> — NBA player lookup: fuzzy name search + season averages.
import type { StatApi } from '@stat-api/client';
import { cache, cacheKey, TTL } from '../cache';
import { playerEmbed, playerNotFoundEmbed, statFooter, type Embed, type PlayerCard } from '../format';

export interface PlayerArgs {
  name: string;
}

export async function handlePlayer(api: StatApi, args: PlayerArgs): Promise<Embed> {
  const key = cacheKey('player', 'nba', { q: args.name.trim().toLowerCase() });
  const { value, ...meta } = await cache.fetch(key, TTL.player, () => fetchPlayer(api, args.name));
  const footer = statFooter(meta);
  return value ? playerEmbed(value, footer) : playerNotFoundEmbed(args.name, footer);
}

async function fetchPlayer(api: StatApi, name: string): Promise<PlayerCard | null> {
  const results = await api.nba.players.search({ q: name, limit: 5 });
  const player = results[0];
  if (!player) return null;

  const { season_player_stats } = await api.nba.season_player_stats.list({ player_id: player.id, limit: 5 });
  // Newest-first by default, but sort defensively so we always take the latest.
  const latest = [...season_player_stats].sort((a, b) => b.season_id - a.season_id)[0];

  const team = player.team_name ?? player.team_abbreviation ?? 'Free agent';
  const position = player.primary_position;

  if (!latest) {
    return { name: player.full_name, team, position, season: '', stats: [], note: 'No season stats available.' };
  }

  const season = await api.nba.seasons.get(latest.season_id);
  const gp = latest.games_played;
  return {
    name: player.full_name,
    team,
    position,
    season: `${seasonLabel(season.start_year)} season · ${gp} GP`,
    stats: [
      { label: 'PPG', value: perGame(latest.pts, gp) },
      { label: 'RPG', value: perGame(latest.rebounds, gp) },
      { label: 'APG', value: perGame(latest.assists, gp) },
      { label: 'SPG', value: perGame(latest.steals, gp) },
      { label: 'BPG', value: perGame(latest.blocks, gp) },
      { label: 'FG%', value: pct(latest.field_goal_percentage) },
      { label: '3P%', value: pct(latest.three_point_percentage) },
      { label: 'FT%', value: pct(latest.free_throw_percentage) },
    ],
  };
}

function perGame(total: number, gamesPlayed: number): string {
  return gamesPlayed > 0 ? (total / gamesPlayed).toFixed(1) : '—';
}

/** stat-api returns shooting percentages as decimal strings ("0.475"). */
function pct(raw: string): string {
  const n = Number(raw);
  if (!Number.isFinite(n)) return '—';
  return `${(n <= 1 ? n * 100 : n).toFixed(1)}%`;
}

function seasonLabel(startYear: number): string {
  return `${startYear}-${String((startYear + 1) % 100).padStart(2, '0')}`;
}
