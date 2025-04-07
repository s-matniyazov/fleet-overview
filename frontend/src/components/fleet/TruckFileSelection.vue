<script setup>
defineProps({
  name: {
    type: String,
    required: true
  }
})
</script>

<template>
  <div class="cursor-pointer custom-light-shadow qm-badge qm-badge">
    <div>
      <div class="font-size-10 text-gray-dark fw-semibold">Expiration Date</div>
      <div class="text-gray-light fw-semibold">
        N/A
        <span class=" font-size-12"></span>
      </div>
    </div>
    <div class="d-flex justify-content-start align-items-center">
      <img src="@/assets/file-na.svg" alt="File checked icon"
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