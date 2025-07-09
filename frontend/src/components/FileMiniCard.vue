<script setup>
import {longToDate} from "@/util/utils.js";

const props = defineProps({
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

function getDataTitle(type) {
  switch (type) {
    case "HVUT_2290": return "Next Final Filing Date"
    case "W_9": return "Next Update Date"
    case "MCS_150": return "Next Filing Date"
    case "SSN": return "Social Security Number"
    case "CLEARING_HOUSE":
    case "MVR":
      return "Last Collected On"
    default: return "Expiration Date";
  }
}

function getData(type) {
  if(!props.file) return 'N/A'
  switch (type) {
    case "SSN": return props.file.socialSecurityNumber
    case "CLEARING_HOUSE":
    case "MVR":
      return longToDate(props.file.lastCollectedOn || 1)
    default: return longToDate(props.file.expirationDate || 1);
  }
}
</script>

<template>
  <div class="cursor-pointer custom-light-shadow qm-badge qm-badge"
       :class="file ? 'badge-soft-success' : 'badge-soft-danger'">
    <div>
      <div class="font-size-10 text-gray-dark fw-semibold">
        {{ getDataTitle(type) }}
      </div>
      <div class="text-gray-light fw-semibold">
        <span class="font-size-12">{{ getData(type) }}</span>
      </div>
    </div>
    <div class="d-flex justify-content-start align-items-center">
      <template v-if="file">
        <img src="../assets/icons/file-check.svg" alt="File checked icon"
             class="me-2 ng-star-inserted"/>
      </template>
      <template v-else>
        <img src="../assets/icons/file-na.svg" alt="File na icon"
             class="me-2 ng-star-inserted"/>
      </template>

      <span class="fw-semibold font-size-10 text-gray-light">
        <strong>{{ name }}</strong>
        On File
      </span>
    </div>
  </div>
</template>

<style scoped>

</style>