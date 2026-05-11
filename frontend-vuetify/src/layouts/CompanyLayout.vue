<script setup>
import Company from "@/components/safety/company/Company.vue";
</script>

<template>
  <div class="company-layout">
    <Company :layout="true" />
  </div>
</template>

<style scoped>
.company-layout {
  display: flex;
  flex-direction: column;
  flex: 1 1 auto;
  min-height: 0;
  height: 100%;
}
</style>