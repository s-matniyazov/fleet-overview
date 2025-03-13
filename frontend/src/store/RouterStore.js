import { defineStore } from "pinia";

export const useRouterStore = defineStore("router-store", {
  state: () => ({
    currentRouter: '',
    currentRouterName: ''
  }),
  actions: {}
});
