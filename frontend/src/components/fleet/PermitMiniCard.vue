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
  <div class="row m-0 align-items-center ng-star-inserted" :class="file ? 'badge-soft-success' : 'badge-soft-danger'">
    <div class="col-1 p-0 me-1">
      <template v-if="file">
        <img src="@/assets/icons/file-check-sm.svg" alt="File checked icon"
             class="me-2 ng-star-inserted"/>
      </template>
      <template v-else>
        <img src="@/assets/icons/file-na-sm.svg" alt="File na icon"
             class="me-2 ng-star-inserted"/>
      </template>
    </div>
    <div class="col-2 p-0">
      <span class="text-secondary fw-semibold">{{ type }}</span></div>
    <div class="col-5 p-0 me-2 text-start">
      <span v-if="file" class="fw-semibold text text-gray-light"> {{ longToDate(file?.expirationDate || 1) }} </span>
      <span v-else class="fw-semibold text text-gray-light"> N/A </span>
    </div>
  </div>
</template>

<style scoped>

</style>