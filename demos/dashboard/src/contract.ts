// The server ⇄ browser wire contract, re-exported from the single source of
// truth that the DM0 frontend already renders: `frontend/src/lib/contract.ts`.
//
// The server distils stat-api responses into exactly these shapes; keeping one
// import site here means the poller, snapshot store, and HTTP layer can never
// drift from what the browser expects. This is a type-only re-export — it is
// erased at build time and adds no runtime coupling to the frontend project.
export type * from '../frontend/src/lib/contract.ts';
