import axios from 'axios'
import {useAuthStore} from "@/store/UseAuthStore.js";
import {hideLoader, showLoader} from "@/util/utils.js";

const axiosIns = axios.create({
  baseURL: '/api/',
    // baseURL: 'http://localhost:8099/api/',
    // baseURL: 'http://10.50.70.200:8089/taskwise/api/',
    timeout: 5000,
})


// ℹ️ Add request interceptor to send the authorization header on each subsequent request after login
axiosIns.interceptors.request.use(config => {
    const authStore = useAuthStore()
    if (authStore.token) {

        //   // Get request headers and if headers is undefined assign blank object
        config.headers = {
            ...config.headers,
            Authorization: `Bearer ${authStore.token}`
        }

    }

    showLoader()
    // // Return modified config
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
        window.location.href = '/fleet/login' // Redirect to login
    }

    return Promise.reject(error)
})
export default axiosIns
