import { defineStore } from 'pinia'
import { pinia } from '@/pinia.js'

const TOKEN_KEY = 'token'
const REFRESH_KEY = 'refreshToken'

const EXPIRY_SKEW_MS = 5_000

export const useAuthStore = defineStore('auth', {
  state: () => ({
    ...readStoredTokens(),
  }),

  getters: {
    payload: (state) => decodeJwt(state.token),
    userId() { return this.payload?.id ?? null },
    username() { return this.payload?.sub ?? null },
    roles() { return this.payload?.roles ?? [] },

    tokenValid() {
      return Boolean(this.token) && !isExpired(this.payload)
    },

    canRefresh() {
      return Boolean(this.refreshToken)
    },

    loggedIn() {
      return this.tokenValid || this.canRefresh
    },
  },

  actions: {
    isAuthenticated() {
      return this.loggedIn
    },

    hasRole(role) {
      return this.roles.includes(role) || this.roles.includes(`ROLE_${role}`)
    },

    login(accessToken, refreshToken) {
      const payload = decodeJwt(accessToken)
      if (!payload) throw new Error('Received a malformed token')

      // Committed only after the token is known to be parseable.
      this.token = accessToken
      localStorage.setItem(TOKEN_KEY, accessToken)

      if (refreshToken) {
        this.refreshToken = refreshToken
        localStorage.setItem(REFRESH_KEY, refreshToken)
      }
    },

    setTokens(accessToken, refreshToken) {
      this.login(accessToken, refreshToken)
    },

    async logout() {
      this.token = ''
      this.refreshToken = ''
      localStorage.removeItem(TOKEN_KEY)
      localStorage.removeItem(REFRESH_KEY)
      localStorage.removeItem('userId')

      const { useStateStore } = await import('@/store/StateStore.js')
      useStateStore(pinia).clear()
    },
  },
})

/** Decodes a JWT payload. Returns null for anything malformed. */
function decodeJwt(token) {
  try {
    const part = token?.split('.')[1]
    if (!part) return null

    const b64 = part.replace(/-/g, '+').replace(/_/g, '/')
    const padded = b64.padEnd(b64.length + ((4 - (b64.length % 4)) % 4), '=')

    const json = new TextDecoder().decode(
        Uint8Array.from(atob(padded), (c) => c.charCodeAt(0))
    )
    return JSON.parse(json)
  } catch {
    return null
  }
}

function isExpired(payload) {
  if (!payload?.exp) return false // no exp claim: let the server decide
  return payload.exp * 1000 <= Date.now() + EXPIRY_SKEW_MS
}

function readStoredTokens() {
  if (typeof localStorage === 'undefined') {
    return { token: '', refreshToken: '' } // SSR guard
  }

  const token = localStorage.getItem(TOKEN_KEY) || ''
  const refreshToken = localStorage.getItem(REFRESH_KEY) || ''

  if (token && isExpired(decodeJwt(token)) && !refreshToken) {
    localStorage.removeItem(TOKEN_KEY)
    return { token: '', refreshToken: '' }
  }

  return { token, refreshToken }
}