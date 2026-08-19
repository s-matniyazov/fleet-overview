import axios from 'axios'
import { pinia } from '@/pinia.js'
import { useAuthStore } from '@/store/UseAuthStore.js'
import { hideLoader, showLoader } from '@/util/utils.js'

const BASE_URL = 'http://localhost:8080/api'
// const BASE_URL = 'https://fleet-backend.tt64295.tw1.ru/api/'

const axiosIns = axios.create({
    baseURL: BASE_URL,
    timeout: 5000,
})

const refreshIns = axios.create({ baseURL: BASE_URL, timeout: 5000 })
const LOGIN_PATH = '/auth/login'
const REFRESH_PATH = '/auth/refresh'

function isAuthEndpoint(url = '') {
    return url.includes(LOGIN_PATH) || url.includes(REFRESH_PATH)
}

// --- refresh ---------------------------------------------------------------

let refreshPromise = null

export function refreshAccessToken() {
    if (!refreshPromise) {
        const authStore = useAuthStore(pinia)
        refreshPromise = refreshIns
            .post(REFRESH_PATH, { refreshToken: authStore.refreshToken })
            .then(({ data }) => {
                const tokenData = data?.data

                const accessToken = tokenData?.accessToken
                const refreshToken = tokenData?.refreshToken ?? authStore.refreshToken

                if (!accessToken) {
                    console.log('access token not found')
                    throw new Error('Refresh response contained no access token')
                }

                authStore.setTokens(accessToken, refreshToken)
                return accessToken
            })
            .finally(() => {
                refreshPromise = null
            })
    }
    return refreshPromise
}

export async function ensureFreshSession() {
    const authStore = useAuthStore(pinia)
    if (authStore.tokenValid || !authStore.canRefresh) return

    try {
        await refreshAccessToken()
    } catch {
        await authStore.logout()
    }
}

async function forceLogout() {
    const authStore = useAuthStore(pinia)
    await authStore.logout()
    console.warn('forceLogout called', new Error().stack) // who triggered this?
    return;
    if (!window.location.pathname.startsWith('/login')) {
        window.location.replace('/login')
    }
}

// --- request ---------------------------------------------------------------

axiosIns.interceptors.request.use(
    async config => {
        showLoader()

        if (refreshPromise && !isAuthEndpoint(config.url)) {
            await refreshPromise.catch(() => {}) // failure is handled on response
        }

        const authStore = useAuthStore(pinia)
        if (authStore.token && !isAuthEndpoint(config.url)) {
            config.headers.Authorization = `Bearer ${authStore.token}`
        }

        return config
    },
    error => {
        hideLoader()
        return Promise.reject(error)
    },
)

// --- response --------------------------------------------------------------

axiosIns.interceptors.response.use(
    response => {
        hideLoader()
        return response
    },
    async error => {
        hideLoader()

        const original = error.config
        const status = error.response?.status

        // 403 means authenticated but not permitted — a new token won't help.
        if (status !== 401 || !original) {
            return Promise.reject(error)
        }

        // Bad credentials on login, or a dead refresh token, are terminal.
        if (isAuthEndpoint(original.url)) {
            return Promise.reject(error)
        }

        // Retry each request at most once, or an endpoint that always 401s loops.
        if (original._retried) {
            await forceLogout()
            return Promise.reject(error)
        }
        original._retried = true

        const authStore = useAuthStore(pinia)
        if (!authStore.canRefresh) {
            await forceLogout()
            return Promise.reject(error)
        }

        try {
            const token = await refreshAccessToken()
            original.headers = original.headers ?? {}
            original.headers.Authorization = `Bearer ${token}`
            return axiosIns(original) // replay the original request
        } catch (refreshError) {
            // Refresh token expired, revoked, or replayed — session is over.
            await forceLogout()
            return Promise.reject(refreshError)
        }
    },
)

export default axiosIns