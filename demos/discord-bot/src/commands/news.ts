// /news [league] — latest player-news headlines for a league.
import type { StatApi } from '@stat-api/client';
import { cache, cacheKey, TTL } from '../cache';
import type { NewsLeague } from '../leagues';
import { newsEmbed, statFooter, type Embed, type NewsItem } from '../format';

export interface NewsArgs {
  league: NewsLeague;
}

export async function handleNews(api: StatApi, args: NewsArgs): Promise<Embed> {
  const { value, ...meta } = await cache.fetch(cacheKey('news', args.league), TTL.news, () => fetchNews(api, args.league));
  return newsEmbed(args.league, value, statFooter(meta));
}

interface NewsRow {
  title: string;
  source?: string | null;
  link?: string | null;
  news_time: string;
}

async function fetchNews(api: StatApi, league: NewsLeague): Promise<NewsItem[]> {
  // Unpaginated list is newest-first, so the freshest headlines lead.
  const rows: NewsRow[] =
    league === 'nba'
      ? (await api.nba.player_news.list({ limit: 6 })).player_news
      : league === 'nfl'
        ? (await api.nfl.player_news.list({ limit: 6 })).player_news
        : (await api.mlb.player_news.list({ limit: 6 })).player_news;

  return rows.map((n) => ({
    title: n.title,
    source: n.source ?? 'stat-api',
    when: hhmm(n.news_time),
    ...(n.link ? { url: n.link } : {}),
  }));
}

/** ISO timestamp → "YYYY-MM-DD HH:MM UTC". Raw input on unparseable dates. */
function hhmm(iso: string): string {
  const d = new Date(iso);
  if (Number.isNaN(d.getTime())) return iso;
  const p = (n: number): string => String(n).padStart(2, '0');
  return `${d.getUTCFullYear()}-${p(d.getUTCMonth() + 1)}-${p(d.getUTCDate())} ${p(d.getUTCHours())}:${p(d.getUTCMinutes())} UTC`;
}
