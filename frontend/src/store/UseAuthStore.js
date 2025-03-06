import { defineStore } from 'pinia'

export const useAuthStore = defineStore('auth', {
  state: () => ({
    token: localStorage.getItem('token') || '', // Load token from local storage
  }),
  actions: {
    login(token) {
      this.token = token
      localStorage.setItem('token', token) // Save token
    },
    logout() {
      this.token = ''
      localStorage.removeItem('token') // Remove token
    },
    isAuthenticated() {
      return this.token && this.token !== '';
    }
  }
})
