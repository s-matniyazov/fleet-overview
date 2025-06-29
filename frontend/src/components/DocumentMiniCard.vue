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
  <div class="qm-badge custom-light-shadow ng-star-inserted" :class="file?.status === 'ACTIVE' ? 'badge-soft-success' : 'badge-soft-danger'">
    <div class="d-flex justify-content-between align-items-center mb-6">
      <div>
        <div class="font-size-16 fw-bold no-action">
          {{ name }}
          <span class="comment me-2"><i class="fa-solid fa-comment no-action text-gray-light"></i></span>
        </div>
        <div class="d-flex align-items-center">
          <span class="font-size-10 text-gray-dark me-1 fw-semibold">Exp:</span>
          <span v-if="!file" class="font-size-12 fw-semibold text-gray-light">N/A</span>
          <span v-else class="font-size-12">{{ longToDate(file?.expirationDate || 1) }}</span>
        </div>
      </div>
    </div>
    <div class="row">
      <div class="col-6 d-flex">
        <template v-if="file?.status === 'ACTIVE'">
          <img src="../assets/icons/file-check-md.svg" alt="File checked icon"
               class="me-2 ng-star-inserted"/>
        </template>
        <template v-else>
          <img src="../assets/icons/file-na-md.svg" alt="File na icon"
               class="me-2 ng-star-inserted"/>
        </template>

        <div>
          <div class="font-size-10 text-gray-dark fw-semibold">Completed by</div>
          <span v-if="file" class="font-size-12 fw-semibold text-gray-light">{{ file?.createdBy?.name }}</span>
          <span v-else class="font-size-12">N/A</span>
        </div>
      </div>
      <div class="col-6">
        <div class="font-size-10 text-gray-dark fw-semibold">Completed Date</div>
        <span v-if="!file" class="font-size-12 fw-semibold text-gray-light">N/A</span>
        <span v-else class="font-size-12">{{ longToDate(file?.created || 1) }}</span>
      </div>
    </div>
  </div>
</template>

<style scoped>

</style>