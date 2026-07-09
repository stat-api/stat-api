import { useMemo, useState } from 'react';
import type { GameCard } from './lib/contract';
import { LEAGUES } from './lib/contract';
import { formatAgo } from './lib/format';
import { useSnapshot } from './lib/useSnapshot';
import { Scoreboard } from './panels/Scoreboard';
import { GameDetail } from './panels/GameDetail';
import { MarketPulse } from './panels/MarketPulse';
import { QuotaFooter } from './panels/QuotaFooter';
import styles from './App.module.css';

const CONNECTION_LABEL = {
  fixture: 'demo data',
  connecting: 'connecting…',
  live: 'live',
  error: 'disconnected',
} as const;

export function App() {
  const { snapshot, connection } = useSnapshot();

  const allGames = useMemo(() => LEAGUES.flatMap((league) => snapshot.leagues[league].games), [snapshot]);
  const detailGames = useMemo(() => allGames.filter((game) => game.detail !== undefined), [allGames]);

  const [selectedId, setSelectedId] = useState<string | null>(() => detailGames[0]?.id ?? null);
  const selected: GameCard | null =
    allGames.find((game) => game.id === selectedId && game.detail) ?? detailGames[0] ?? null;

  return (
    <div className={styles.app}>
      <header className={styles.header}>
        <div className={styles.brand}>
          <span className={styles.logo}>stat-api</span>
          <span className={styles.product}>live dashboard</span>
        </div>
        <div className={styles.meta}>
          <span className={connection === 'live' ? styles.connLive : styles.connIdle}>
            <span className={styles.connDot} />
            {CONNECTION_LABEL[connection]}
          </span>
          <span className={styles.updated}>updated {formatAgo(snapshot.generatedAt)}</span>
        </div>
      </header>

      <main className={styles.layout}>
        <div className={styles.left}>
          <Scoreboard
            leagues={snapshot.leagues}
            selectedId={selected?.id ?? null}
            onSelect={setSelectedId}
          />
        </div>
        <div className={styles.right}>
          {selected ? (
            <>
              <GameDetail game={selected} />
              <MarketPulse game={selected} />
            </>
          ) : (
            <div className={styles.rightEmpty}>Select a game to see line movement and market pulse.</div>
          )}
        </div>
      </main>

      <QuotaFooter quota={snapshot.quota} generatedAt={snapshot.generatedAt} />
    </div>
  );
}
