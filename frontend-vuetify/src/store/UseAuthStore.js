import { defineStore } from 'pinia'
import { pinia } from '@/pinia.js'

export const useAuthStore = defineStore('auth', {
  state: () => ({
    token: localStorage.getItem('token') || '', // Load token from local storage
  }),
  actions: {
    login(token) {
      this.token = token
      localStorage.setItem('token', token)
      const payload = JSON.parse(atob(token.split('.')[1]));
      localStorage.setItem('userId', payload.id);
    },
    logout() {
      this.token = ''
      localStorage.removeItem('token') // Remove token
      void import('@/store/StateStore.js').then(({ useStateStore }) => {
        useStateStore(pinia).clear()
      })
    },
    isAuthenticated() {
      return this.token && this.token !== '';
    }
  }
})
