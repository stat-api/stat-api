// =============================================================================
// main — the composition root: gateway client, command wiring, error edge
// =============================================================================
//
// The only file that touches discord.js or constructs the SDK. It validates
// the two required env vars, registers the slash commands on ready, and routes
// each interaction to a pure handler. Handlers never see a discord.js type.
// =============================================================================

import {
  Client,
  GatewayIntentBits,
  Events,
  SlashCommandBuilder,
  EmbedBuilder,
  MessageFlags,
  type ChatInputCommandInteraction,
  type AutocompleteInteraction,
  type Interaction,
} from 'discord.js';
import {
  StatApi,
  StatApiError,
  AuthenticationError,
  QuotaExceededError,
  NotFoundError,
  ValidationError,
} from '@stat-api/client';

import { cache, scoresKey } from './cache';
import type { TeamLeague, NewsLeague } from './leagues';
import { errorEmbed, type Embed } from './format';
import { handleScores, type Slate } from './commands/scores';
import { handleBox } from './commands/box';
import { handlePlayer } from './commands/player';
import { handleStandings } from './commands/standings';
import { handleNews } from './commands/news';

/** A user-facing problem (bad input) — rendered as a gentle embed, not logged. */
class UserError extends Error {}

// ---- env ----

function requireEnv(): { token: string; apiKey: string } {
  const token = process.env['DISCORD_TOKEN'];
  const apiKey = process.env['STAT_API_KEY'];
  const missing = [
    ['DISCORD_TOKEN', token],
    ['STAT_API_KEY', apiKey],
  ]
    .filter(([, v]) => !v)
    .map(([k]) => k);
  if (missing.length > 0 || !token || !apiKey) {
    console.error(
      `stat-api discord bot: missing required environment variable(s): ${missing.join(', ')}.\n` +
        'Set DISCORD_TOKEN (Discord developer portal) and STAT_API_KEY (stat-api.com), then re-run. See README.',
    );
    process.exit(1);
  }
  return { token, apiKey };
}

// ---- slash command definitions ----

const TEAM_LEAGUE_CHOICES = [
  { name: 'MLB', value: 'mlb' },
  { name: 'NBA', value: 'nba' },
] as const;

const commands = [
  new SlashCommandBuilder()
    .setName('scores')
    .setDescription('Scoreboard for a league on a date')
    .addStringOption((o) => o.setName('league').setDescription('League (default MLB)').addChoices(...TEAM_LEAGUE_CHOICES))
    .addStringOption((o) => o.setName('date').setDescription('Date YYYYMMDD, ET (default today)')),
  new SlashCommandBuilder()
    .setName('box')
    .setDescription('Box score for a game')
    .addStringOption((o) =>
      o.setName('game').setDescription("Game — type to search today's slate").setRequired(true).setAutocomplete(true),
    )
    .addStringOption((o) => o.setName('league').setDescription('League (default MLB)').addChoices(...TEAM_LEAGUE_CHOICES)),
  new SlashCommandBuilder()
    .setName('player')
    .setDescription('NBA player season averages')
    .addStringOption((o) => o.setName('name').setDescription('Player name').setRequired(true)),
  new SlashCommandBuilder().setName('standings').setDescription('NBA standings by conference'),
  new SlashCommandBuilder()
    .setName('news')
    .setDescription('Latest player news for a league')
    .addStringOption((o) =>
      o
        .setName('league')
        .setDescription('League (default NBA)')
        .addChoices({ name: 'NBA', value: 'nba' }, { name: 'NFL', value: 'nfl' }, { name: 'MLB', value: 'mlb' }),
    ),
].map((c) => c.toJSON());

// ---- routing ----

async function route(api: StatApi, interaction: ChatInputCommandInteraction): Promise<Embed> {
  const opts = interaction.options;
  switch (interaction.commandName) {
    case 'scores':
      return handleScores(api, { league: (opts.getString('league') ?? 'mlb') as TeamLeague, date: opts.getString('date') ?? todayEastern() });
    case 'box': {
      const gameId = Number(opts.getString('game', true));
      if (!Number.isInteger(gameId)) throw new UserError('Pick a game from the autocomplete list.');
      return handleBox(api, { league: (opts.getString('league') ?? 'mlb') as TeamLeague, gameId });
    }
    case 'player':
      return handlePlayer(api, { name: opts.getString('name', true) });
    case 'standings':
      return handleStandings(api, { league: 'nba' });
    case 'news':
      return handleNews(api, { league: (opts.getString('league') ?? 'nba') as NewsLeague });
    default:
      throw new UserError(`Unknown command: ${interaction.commandName}`);
  }
}

async function handleAutocomplete(interaction: AutocompleteInteraction): Promise<void> {
  // Box game picker: serve choices from the already-cached slate — zero API
  // calls. If /scores hasn't populated today's slate yet, we return nothing.
  if (interaction.commandName !== 'box') {
    await interaction.respond([]);
    return;
  }
  const league = interaction.options.getString('league') ?? 'mlb';
  const typed = interaction.options.getFocused().toLowerCase();
  const slate = cache.peek<Slate>(scoresKey(league, todayEastern()));
  const choices = (slate?.value.games ?? [])
    .filter((g) => `${g.away} ${g.home}`.toLowerCase().includes(typed))
    .slice(0, 25)
    .map((g) => ({ name: `${g.away} @ ${g.home} (${g.status})`.slice(0, 100), value: String(g.gameId) }));
  await interaction.respond(choices);
}

function errorEmbedFor(err: unknown): Embed {
  if (err instanceof QuotaExceededError)
    return errorEmbed('Quota exceeded', 'This bot has hit its monthly stat-api record quota. Try again later.');
  if (err instanceof AuthenticationError) return errorEmbed('Auth error', "The bot's STAT_API_KEY is invalid or expired.");
  if (err instanceof NotFoundError) return errorEmbed('Not found', 'No data for that request.');
  if (err instanceof ValidationError) return errorEmbed('Bad request', err.message);
  if (err instanceof StatApiError) return errorEmbed('stat-api error', `The request failed (HTTP ${err.status}).`);
  if (err instanceof UserError) return errorEmbed('Try again', err.message);
  console.error('unexpected interaction error:', err);
  return errorEmbed('Unexpected error', 'Something went wrong handling that command.');
}

// ---- date helper (US Eastern, the API's scoreboard timezone) ----

function todayEastern(): string {
  // en-CA renders YYYY-MM-DD; strip the dashes for the API's YYYYMMDD `day`.
  const parts = new Intl.DateTimeFormat('en-CA', {
    timeZone: 'America/New_York',
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
  }).format(new Date());
  return parts.replaceAll('-', '');
}

// ---- boot ----

function main(): void {
  const { token, apiKey } = requireEnv();
  const api = new StatApi({ apiKey });
  const client = new Client({ intents: [GatewayIntentBits.Guilds] });

  client.once(Events.ClientReady, async (ready) => {
    await ready.application.commands.set(commands);
    console.log(`Logged in as ${ready.user.tag}; registered ${commands.length} slash commands.`);
  });

  client.on(Events.InteractionCreate, async (interaction: Interaction) => {
    try {
      if (interaction.isAutocomplete()) {
        await handleAutocomplete(interaction);
        return;
      }
      if (!interaction.isChatInputCommand()) return;
      await interaction.deferReply();
      const embed = await route(api, interaction);
      await interaction.editReply({ embeds: [new EmbedBuilder(embed)] });
    } catch (err) {
      if (interaction.isAutocomplete()) return; // can't reply with an embed
      const embed = new EmbedBuilder(errorEmbedFor(err));
      if (interaction.isChatInputCommand()) {
        if (interaction.deferred || interaction.replied) await interaction.editReply({ embeds: [embed] });
        else await interaction.reply({ embeds: [embed], flags: MessageFlags.Ephemeral });
      }
    }
  });

  void client.login(token);
}

main();
