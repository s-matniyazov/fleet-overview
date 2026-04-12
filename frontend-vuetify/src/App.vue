<script setup>
import UToaster from "@/components/base/UToast.vue";
import ULoader from "@/components/base/ULoader.vue";
</script>

<template>
  <v-app>
    <router-view />
    <UToaster />
    <ULoader />
  </v-app>
</template>

<style scoped>
</style>
