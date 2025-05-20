<script setup>
import {longToDate} from "@/util/utils.js";

defineProps({
  name: {
    type: String,
    required: true
  },
  type: {
    type: String,
    required: true
  },
  file: {
    type: Object,
    required: true
  },
})
</script>

<template>
  <div class="cursor-pointer custom-light-shadow qm-badge qm-badge" :class="file ? 'badge-soft-success' : 'badge-soft-danger'">
    <div>
      <div class="font-size-10 text-gray-dark fw-semibold">
        {{ file ? longToDate(file?.expirationDate || 1): "Expiration Date" }}
      </div>
      <div class="text-gray-light fw-semibold">
        <span v-if="!file">N/A</span>
        <span v-else class="font-size-12">{{ file?.status }}</span>
      </div>
    </div>
    <div class="d-flex justify-content-start align-items-center">
      <img src="../../../assets/icons/file-na.svg" alt="File checked icon"
           class="me-2 ng-star-inserted"/>
      <span class="fw-semibold font-size-10 text-gray-light">
        <strong>{{ name }}</strong>
        On File
      </span>
    </div>
  </div>
</template>

<style scoped>

</style>