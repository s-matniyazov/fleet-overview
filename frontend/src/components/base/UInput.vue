<script setup>

import {inject, ref} from "vue";

const props = defineProps({
  name: {
    type: String,
    required: false,
    default: ''
  },
  label: {
    type: String,
    required: false,
    default: ''
  },
  placeholder: {
    type: String,
    required: false,
    default: ''
  },
  hint: {
    type: String,
    required: false,
    default: ''
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
  rules: Function,
  readonly: {
    type: Boolean,
    required: false,
    default: false
  }
})

const model = defineModel({});
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
  <div :class="[classes, errorMessage ? 'has-danger' : '', readonly ? 'readonly-mode' : '', 'p-1']" :style="styles">
    <label v-if="label" class="form-label">{{ label }}</label>
    <input class="form-control font-size-12" :placeholder="placeholder" :type="type" :name="name"
           v-model="model">
    <div class="invalid-feedback">{{ hint }}</div>
    <p v-if="errorMessage" class="pristine-error text-help">{{ errorMessage }}</p>
  </div>
</template>

<style scoped>
</style>
