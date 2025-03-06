<script setup>
defineProps({
  label: {
    type: String,
    required: false,
  },
  hint: {
    type: String,
    required: false,
    default: 'Please select date'
  },
  option_name: {
    type: String,
    required: false,
    default: 'id'
  },
  option_value: {
    type: String,
    required: false,
    default: 'id'
  },
  items: {
    type: Array,
    required: true,
    default: []
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

const selected = defineModel({});

</script>

<template>
  <div :class="classes" :style="styles">
    <label v-if="label" class="form-label">{{ label }}</label>
    <select class="form-control form-select font-size-12" v-model="selected">
<!--      <option selected disabled> - </option>-->
      <option v-for="option in items" :value="option[option_value]">
        {{ option[option_name] }}
      </option>
    </select>
    <div class="invalid-feedback">{{ hint }}</div>
  </div>
</template>

<style scoped>

</style>
