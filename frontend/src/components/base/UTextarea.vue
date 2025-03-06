<script setup>

defineProps({
  label: {
    type: String,
    required: false,
    default: 'Textarea'
  },
  placeholder: {
    type: String,
    required: false,
    default: 'Please type textarea'
  },
  rows: {
    type: Number,
    required: false,
    default: 3
  },
  classes: {
    type: String,
    required: false,
    default: ''
  },
  styles: {
    type: String,
    required: false,
    default: ''
  },
})

const model = defineModel({});

</script>

<template>
  <div :class="classes" :style="styles">
    <label for="for-description" class="form-label">{{ label }}</label>
    <textarea class="form-control font-size-12" id="for-description" v-model="model" :placeholder="placeholder" :rows="rows"></textarea>
  </div>
</template>

<style scoped>

</style>
