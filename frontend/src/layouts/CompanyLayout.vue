<script setup>

import Company from "@/components/safety/company/Company.vue";
import UScrollArea from "@/components/base/UScrollArea.vue";
</script>

<template>
  <UScrollArea height="calc(100vh)" style="padding: 50px 100px;">
    <Company :layout="true"/>
  </UScrollArea>
</template>

<style scoped>

</style>