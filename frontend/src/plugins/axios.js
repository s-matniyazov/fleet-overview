import axios from 'axios'
import {useAuthStore} from "@/store/UseAuthStore.js";
import {hideLoader, showLoader} from "@/util/utils.js";

const axiosIns = axios.create({
  baseURL: 'https://fleet-backend.tt64295.tw1.ru/api/',
//     baseURL: 'http://localhost:8088/api/',
    timeout: 5000,
})

axiosIns.interceptors.request.use(config => {
    const authStore = useAuthStore()
    if (authStore.token) {

        config.headers = {
            ...config.headers,
            Authorization: `Bearer ${authStore.token}`
        }

    }

    showLoader()

    return config
}, (error) => {
    hideLoader()
    return Promise.reject(error);
})

// ℹ️ Add response interceptor to handle 401 response
axiosIns.interceptors.response.use(response => {
    hideLoader()

    return response
}, error => {
    hideLoader()

    if (error.response && error.response.status === 401) {
        const authStore = useAuthStore()
        authStore.logout() // Clear token on 401
        window.location.href = '/login' // Redirect to login
    }

    return Promise.reject(error)
})
export default axiosIns
