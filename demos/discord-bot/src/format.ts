// =============================================================================
// format — pure embed builders (no discord.js, no SDK)
// =============================================================================
//
// Handlers hand these plain view-models; the builders return plain `Embed`
// JSON (a structural subset of discord.js `APIEmbed`). Keeping this layer free
// of discord.js and of the SDK is what makes the handlers snapshot-testable
// with nothing but a fixture object.
// =============================================================================

import { leagueMeta } from './leagues';

// ---- Embed shape (assignable to discord.js EmbedData / APIEmbed) ----

export interface EmbedField {
  name: string;
  value: string;
  inline?: boolean;
}

export interface EmbedFooter {
  text: string;
}

export interface Embed {
  title?: string;
  description?: string;
  url?: string;
  color?: number;
  fields?: EmbedField[];
  footer?: EmbedFooter;
}

// ---- view-models (builder inputs) ----

export interface ScoreLine {
  gameId: number;
  away: string;
  home: string;
  awayScore: number;
  homeScore: number;
  status: string;
  startTime: string;
}

export interface BoxTeam {
  name: string;
  score: number;
  leaders: string[];
}

export interface BoxScore {
  league: string;
  status: string;
  away: BoxTeam;
  home: BoxTeam;
}

export interface StatLine {
  label: string;
  value: string;
}

export interface PlayerCard {
  name: string;
  team: string;
  position: string;
  season: string;
  stats: StatLine[];
  note?: string;
}

export interface StandingRow {
  rank: number;
  abbrev: string;
  wins: number;
  losses: number;
  pct: string;
}

export interface StandingsGroup {
  title: string;
  rows: StandingRow[];
}

export interface StandingsView {
  league: string;
  day: number;
  groups: StandingsGroup[];
}

export interface NewsItem {
  title: string;
  source: string;
  when: string;
  url?: string;
}

// ---- footer ----

/** The provenance footer every embed carries. */
export function statFooter(meta: { fetchedAt: number; ageSeconds: number }): EmbedFooter {
  return { text: `data as of ${formatAsOf(meta.fetchedAt)} · cached ${meta.ageSeconds}s ago · stat-api.com` };
}

// ---- builders ----

export function scoresEmbed(league: string, date: string, games: ScoreLine[], footer: EmbedFooter): Embed {
  const meta = leagueMeta(league);
  const lines = games.map((g) => {
    const scored = /final|progress|live|end|half/i.test(g.status) || g.awayScore > 0 || g.homeScore > 0;
    const body = scored ? `${g.awayScore}–${g.homeScore}` : 'vs';
    const time = /final/i.test(g.status) ? '' : ` · ${hhmmUtc(g.startTime)}`;
    return `**${g.away}** ${body} **${g.home}** · ${g.status}${time}`.trimEnd();
  });
  return {
    title: `${meta.emoji} ${meta.name} scoreboard — ${prettyDate(date)}`,
    description: truncate(lines.join('\n'), 4096),
    color: meta.color,
    footer,
  };
}

export function offseasonEmbed(league: string, date: string, footer: EmbedFooter): Embed {
  const meta = leagueMeta(league);
  return {
    title: `${meta.emoji} ${meta.name} — no games ${prettyDate(date)}`,
    description:
      'Nothing on the schedule for this date — the league is between games or in its offseason. ' +
      'Try another date, or a league that is currently in season.',
    color: meta.color,
    footer,
  };
}

export function boxEmbed(box: BoxScore, footer: EmbedFooter): Embed {
  const meta = leagueMeta(box.league);
  const field = (team: BoxTeam): EmbedField => ({
    name: `${team.name} — ${team.score}`,
    value: truncate(codeBlock(team.leaders.length ? team.leaders.join('\n') : 'no box data'), 1024),
    inline: false,
  });
  return {
    title: `${meta.emoji} ${box.away.name} ${box.away.score}–${box.home.score} ${box.home.name} · ${box.status}`,
    color: meta.color,
    fields: [field(box.away), field(box.home)],
    footer,
  };
}

export function playerEmbed(card: PlayerCard, footer: EmbedFooter): Embed {
  const fields: EmbedField[] = card.stats.map((s) => ({ name: s.label, value: s.value, inline: true }));
  return {
    title: `🏀 ${card.name}`,
    description: [card.team, card.position, card.season].filter(Boolean).join(' · ') + (card.note ? `\n${card.note}` : ''),
    color: leagueMeta('nba').color,
    fields,
    footer,
  };
}

export function playerNotFoundEmbed(name: string, footer: EmbedFooter): Embed {
  return {
    title: '🏀 No player found',
    description: `No NBA player matched **${truncate(name, 100)}**. Check the spelling and try again.`,
    color: leagueMeta('nba').color,
    footer,
  };
}

export function standingsEmbed(view: StandingsView, footer: EmbedFooter): Embed {
  const meta = leagueMeta(view.league);
  const fields: EmbedField[] = view.groups.map((g) => ({
    name: g.title,
    value: truncate(codeBlock(standingsTable(g.rows)), 1024),
    inline: false,
  }));
  return {
    title: `${meta.emoji} ${meta.name} standings`,
    color: meta.color,
    fields,
    footer,
  };
}

export function newsEmbed(league: string, items: NewsItem[], footer: EmbedFooter): Embed {
  const meta = leagueMeta(league);
  if (items.length === 0) {
    return {
      title: `${meta.emoji} ${meta.name} news`,
      description: 'No recent headlines.',
      color: meta.color,
      footer,
    };
  }
  const fields: EmbedField[] = items.slice(0, 10).map((n, i) => ({
    name: truncate(`${i + 1}. ${n.title}`, 256),
    value: truncate(`${n.source} · ${n.when}${n.url ? ` · [read](${n.url})` : ''}`, 1024),
    inline: false,
  }));
  return {
    title: `${meta.emoji} ${meta.name} — latest news`,
    color: meta.color,
    fields,
    footer,
  };
}

export function errorEmbed(title: string, message: string): Embed {
  return { title: `⚠️ ${title}`, description: truncate(message, 4096), color: 0xed4245 };
}

// ---- internals ----

function standingsTable(rows: StandingRow[]): string {
  if (rows.length === 0) return 'no standings';
  return rows
    .map((r) => `${String(r.rank).padStart(2)}  ${r.abbrev.padEnd(4)} ${`${r.wins}-${r.losses}`.padEnd(7)} ${r.pct}`)
    .join('\n');
}

function codeBlock(body: string): string {
  return '```\n' + body + '\n```';
}

function truncate(text: string, max: number): string {
  return text.length <= max ? text : text.slice(0, max - 1) + '…';
}

/** "20260708" → "2026-07-08". Leaves non-8-digit input untouched. */
function prettyDate(yyyymmdd: string): string {
  return /^\d{8}$/.test(yyyymmdd)
    ? `${yyyymmdd.slice(0, 4)}-${yyyymmdd.slice(4, 6)}-${yyyymmdd.slice(6, 8)}`
    : yyyymmdd;
}

/** ISO timestamp → "HH:MM UTC". Empty string on unparseable input. */
function hhmmUtc(iso: string): string {
  const d = new Date(iso);
  if (Number.isNaN(d.getTime())) return '';
  const p = (n: number): string => String(n).padStart(2, '0');
  return `${p(d.getUTCHours())}:${p(d.getUTCMinutes())} UTC`;
}

function formatAsOf(ms: number): string {
  const d = new Date(ms);
  const p = (n: number): string => String(n).padStart(2, '0');
  return `${d.getUTCFullYear()}-${p(d.getUTCMonth() + 1)}-${p(d.getUTCDate())} ${p(d.getUTCHours())}:${p(d.getUTCMinutes())} UTC`;
}
