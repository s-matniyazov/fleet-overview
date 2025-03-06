<script setup>
import UToaster from "@/components/base/UToast.vue";
import ULoader from "@/components/base/ULoader.vue";
</script>

<template>
  <router-view/>
  <UToaster />
  <ULoader />
</template>

<style scoped>
</style>
