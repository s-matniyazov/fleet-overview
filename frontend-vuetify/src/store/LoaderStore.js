import { defineStore } from "pinia";
import { ref } from "vue";

export const useLoaderStore = defineStore("loader", () => {
  const isLoading = ref(false);

  function show() {
    isLoading.value = true;
  }

  function hide() {
    isLoading.value = false;
  }

  return { isLoading, show, hide };
});
