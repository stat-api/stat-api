import type { GameCard, League, LeagueSnapshot, TeamSide } from '../lib/contract';
import { LEAGUES } from '../lib/contract';
import { formatClock } from '../lib/format';
import styles from './Scoreboard.module.css';

const LEAGUE_LABEL: Record<League, string> = { mlb: 'MLB', nba: 'NBA', nfl: 'NFL' };
const OFFSEASON_NOTE: Record<League, string> = {
  mlb: 'Season resumes in spring.',
  nba: 'Tips off in October.',
  nfl: 'Kicks off in September.',
};

interface ScoreboardProps {
  leagues: Record<League, LeagueSnapshot>;
  selectedId: string | null;
  onSelect: (id: string) => void;
}

export function Scoreboard({ leagues, selectedId, onSelect }: ScoreboardProps) {
  return (
    <div className={styles.scoreboard}>
      {LEAGUES.map((league) => (
        <LeagueSection
          key={league}
          league={league}
          snapshot={leagues[league]}
          selectedId={selectedId}
          onSelect={onSelect}
        />
      ))}
    </div>
  );
}

interface LeagueSectionProps {
  league: League;
  snapshot: LeagueSnapshot;
  selectedId: string | null;
  onSelect: (id: string) => void;
}

function LeagueSection({ league, snapshot, selectedId, onSelect }: LeagueSectionProps) {
  const inSeason = snapshot.status === 'in-season';
  const hasGames = inSeason && snapshot.games.length > 0;
  return (
    <section className={styles.section}>
      <header className={styles.sectionHead}>
        <h2 className={styles.leagueName}>{LEAGUE_LABEL[league]}</h2>
        <span className={inSeason ? styles.pillOn : styles.pillOff}>
          {inSeason ? `${snapshot.games.length} today` : 'Offseason'}
        </span>
      </header>
      {hasGames ? (
        <div className={styles.cards}>
          {snapshot.games.map((game) => (
            <GameCardView
              key={game.id}
              game={game}
              selected={game.id === selectedId}
              onSelect={onSelect}
            />
          ))}
        </div>
      ) : (
        <div className={styles.offseason}>
          <span className={styles.offseasonMark}>Offseason</span>
          {OFFSEASON_NOTE[league]}
        </div>
      )}
    </section>
  );
}

interface GameCardViewProps {
  game: GameCard;
  selected: boolean;
  onSelect: (id: string) => void;
}

function GameCardView({ game, selected, onSelect }: GameCardViewProps) {
  const interactive = game.detail !== undefined;
  const inner = (
    <>
      <div className={styles.cardTop}>
        <StatusBadge game={game} />
        {interactive ? <span className={styles.detailHint}>details ›</span> : null}
      </div>
      <TeamRow side={game.away} />
      <TeamRow side={game.home} />
    </>
  );

  if (!interactive) {
    return <div className={styles.card}>{inner}</div>;
  }
  return (
    <button
      type="button"
      className={`${styles.card} ${styles.cardInteractive} ${selected ? styles.cardSelected : ''}`}
      onClick={() => onSelect(game.id)}
      aria-pressed={selected}
    >
      {inner}
    </button>
  );
}

function TeamRow({ side }: { side: TeamSide }) {
  return (
    <div className={styles.teamRow}>
      <span className={styles.abbr}>{side.abbr}</span>
      <span className={styles.teamName}>{side.name}</span>
      <span className={styles.score}>{side.score ?? '—'}</span>
    </div>
  );
}

function StatusBadge({ game }: { game: GameCard }) {
  if (game.status === 'live') {
    return (
      <span className={styles.badgeLive}>
        <span className={styles.liveDot} />
        LIVE
      </span>
    );
  }
  if (game.status === 'final') {
    return <span className={styles.badgeFinal}>FINAL</span>;
  }
  return <span className={styles.badgeScheduled}>{formatClock(game.startTime)}</span>;
}
