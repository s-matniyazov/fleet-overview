<script setup>
import Company from "@/components/safety/company/Company.vue";
</script>

<template>
  <v-container fluid class="company-layout-view pa-6 pa-md-10">
    <Company :layout="true" />
  </v-container>
</template>

<style scoped>
.company-layout-view {
  min-height: 100%;
  background: linear-gradient(145deg, #0f172a 0%, #152238 50%, #0b1220 100%);
}
</style>
