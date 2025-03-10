<script setup>

defineProps({
  name: {
    type: String,
    required: false,
    default: 'input'
  },
  label: {
    type: String,
    required: false,
    default: 'input'
  },
  placeholder: {
    type: String,
    required: false,
    default: 'Please type input'
  },
  hint: {
    type: String,
    required: false,
    default: 'Please type input'
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
  type: {
    type: String,
    required: false,
    default: 'text'
  },
})

const model = defineModel({});

</script>

<template>
  <div :class="classes" :style="styles">
    <label class="form-label">{{ label }}</label>
    <input class="form-control font-size-12" :placeholder="placeholder" :name="name"
           v-model="model" required :type="type">
    <div class="invalid-feedback">{{ hint }}</div>
  </div>
</template>

<style scoped>

</style>
