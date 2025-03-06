<script setup>
import {computed} from "vue";
import {dateToLong, longToDate} from "@/util/utils.js";

defineProps({
  label: {
    type: String,
    required: false,
    default: 'Select'
  },
  hint: {
    type: String,
    required: false,
    default: 'Please select item'
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

const inputDate = computed({
  get() {
    return longToDate(model.value);
  },
  set(newValue) {
    model.value = dateToLong(newValue);
  }
})

</script>

<template>
  <div :class="classes" :style="styles">
    <label for="date-label" class="form-label">{{ label }}</label>
    <input class="form-control font-size-12" type="date" v-model="inputDate" id="date-label">
    <div class="invalid-feedback">{{ hint }}</div>
  </div>
</template>

<style scoped>

</style>
