<script setup>
import {computed, inject, ref} from "vue";
import {dateToLong, longToDate} from "@/util/utils.js";

const props = defineProps({
  name: {
    type: String,
    required: false,
    default: 'dateInput'
  },
  label: {
    type: String,
    required: false,
    default: 'dateInput'
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
  rules: Function,
})

const model = defineModel({});

const inputDate = computed({
  get() {
    return longToDate(model.value, 1);
  },
  set(newValue) {
    model.value = dateToLong(newValue);
  }
})

const errorMessage = ref('');

const formState = inject('formState', null);
const registerField = inject('registerField', () => {});

const validate = () => {
  errorMessage.value = props.rules ? props.rules(model.value) : '';
  return errorMessage.value;
};

registerField(props.name, validate);

</script>

<template>
  <div :class="`${classes} ${errorMessage && 'has-danger'}`" :style="styles">
    <label for="date-label" class="form-label">{{ label }}</label>
    <input class="form-control font-size-12" type="date" v-model="inputDate" id="date-label">
    <div class="invalid-feedback">{{ hint }}</div>
    <p v-if="errorMessage" class="pristine-error text-help">{{ errorMessage }}</p>
  </div>
</template>

<style scoped>

</style>
