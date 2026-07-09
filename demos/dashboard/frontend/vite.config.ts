import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

// The dashboard frontend is a static SPA. In production the Bun+Hono server (DM1) serves the
// built assets and proxies /api/* to itself; in dev, point /api at that server once it exists.
export default defineConfig({
  plugins: [react()],
  server: {
    port: 4401,
    proxy: {
      '/api': 'http://localhost:4400',
    },
  },
  build: {
    outDir: 'dist',
    sourcemap: true,
  },
});
