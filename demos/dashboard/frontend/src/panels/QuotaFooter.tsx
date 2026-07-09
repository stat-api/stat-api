import type { Quota } from '../lib/contract';
import { formatAgo, formatCount } from '../lib/format';
import styles from './QuotaFooter.module.css';

interface QuotaFooterProps {
  quota: Quota | null;
  generatedAt: string;
}

/**
 * Live record-quota readout. The dashboard's whole design goal is to keep this number small: one
 * poller feeds every viewer, so the bar moves at the poller's cadence no matter how many browsers
 * are watching.
 */
export function QuotaFooter({ quota, generatedAt }: QuotaFooterProps) {
  return (
    <footer className={styles.footer}>
      <div className={styles.left}>
        <span className={styles.tag}>records-metered · one poller feeds every viewer</span>
      </div>
      {quota ? <QuotaBar quota={quota} /> : <span className={styles.na}>quota unavailable (demo data)</span>}
      <div className={styles.stamp}>snapshot {formatAgo(generatedAt)}</div>
    </footer>
  );
}

function QuotaBar({ quota }: { quota: Quota }) {
  const usedPct = quota.limit > 0 ? Math.min(100, (quota.used / quota.limit) * 100) : 0;
  return (
    <div className={styles.quota}>
      <div className={styles.numbers}>
        <span className={styles.used}>{formatCount(quota.used)}</span>
        <span className={styles.sep}>/</span>
        <span className={styles.limit}>{formatCount(quota.limit)}</span>
        <span className={styles.word}>records this month</span>
      </div>
      <div className={styles.bar}>
        <div className={styles.barFill} style={{ width: `${usedPct}%` }} />
      </div>
      <div className={styles.remaining}>{formatCount(quota.remaining)} left</div>
    </div>
  );
}
