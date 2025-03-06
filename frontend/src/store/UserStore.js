import { defineStore } from "pinia";

export default defineStore("user-store", {
  state: () => ({
    user: [],
    token: ''
  }),
  actions: {
    checkToken() {
      return this.token || this.token !== '';
    }
  }
});
