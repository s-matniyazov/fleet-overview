<script setup>

import Company from "@/components/safety/company/Company.vue";
import UScrollArea from "@/components/base/UScrollArea.vue";
</script>

<template>
  <UScrollArea height="calc(100vh)" style="padding: 50px 100px;" class="main_with_gradient">
    <Company :layout="true"/>
  </UScrollArea>
</template>

<style scoped>
.main_with_gradient{
  background: linear-gradient(135deg, #24334b, rgb(2, 6, 23), #0f172a, #24334b);
}
</style>