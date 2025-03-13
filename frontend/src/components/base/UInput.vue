<script setup>

import {inject, ref} from "vue";

const props = defineProps({
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
  rules: Function,
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
  <div :class="`${classes} ${errorMessage && 'has-danger'}`" :style="styles">
    <label class="form-label">{{ label }}</label>
    <input class="form-control font-size-12 " :placeholder="placeholder" type="text" :name="name"
           v-model="model">
    <div class="invalid-feedback">{{ hint }}</div>
    <p v-if="errorMessage" class="pristine-error text-help">{{ errorMessage }}</p>
  </div>
</template>

<style scoped>

</style>
