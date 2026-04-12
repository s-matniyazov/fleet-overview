import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vuetify from 'vite-plugin-vuetify'

// vite-plugin-vue-devtools pulls @vue/devtools-kit, which reads localStorage while the config loads in Node.
const _ls = globalThis.localStorage
if (
  typeof _ls === 'undefined' ||
  _ls === null ||
  typeof _ls.getItem !== 'function'
) {
  const memory = new Map()
  globalThis.localStorage = {
    getItem: (key) => (memory.has(key) ? memory.get(key) : null),
    setItem: (key, value) => {
      memory.set(String(key), String(value))
    },
    removeItem: (key) => {
      memory.delete(key)
    },
    clear: () => memory.clear(),
    key: (index) => [...memory.keys()][index] ?? null,
    get length() {
      return memory.size
    },
  }
}

// https://vite.dev/config/
export default defineConfig(async ({ command }) => {
  const plugins = [
    vue(),
    vuetify({ autoImport: true }),
  ]
  // Static import of vue-devtools pulls code that touches localStorage at load time and breaks `vite build`.
  if (command === 'serve') {
    const { default: vueDevTools } = await import('vite-plugin-vue-devtools')
    plugins.push(vueDevTools())
  }

  return {
    base: '',
    publicPath: '',
    define: {
      global: 'globalThis',
    },
    optimizeDeps: {
      include: ['vue-i18n', 'vue-router', 'pinia', 'axios'],
    },
    server: {
      port: 5173
    },
    plugins,
    resolve: {
      alias: {
        '@': fileURLToPath(new URL('./src', import.meta.url))
      },
    },
  }
})
