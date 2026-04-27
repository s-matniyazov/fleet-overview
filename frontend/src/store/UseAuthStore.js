import { defineStore } from 'pinia'

export const useAuthStore = defineStore('auth', {
  state: () => ({
    token: localStorage.getItem('token') || '', // Load token from local storage
  }),
  actions: {
    login(token) {
      this.token = token
      localStorage.setItem('token', token); // Save token
      const payload = JSON.parse(atob(token.split('.')[1]));
      localStorage.userId = payload.id;
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
