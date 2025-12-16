<script setup>

import {inject, ref} from "vue";

const props = defineProps({
  label: {
    type: String,
    required: false,
    default: 'Phone'
  },
  placeholder: {
    type: String,
    required: false,
    default: 'Please enter phone number'
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
  noLabel: {
    type: Boolean,
    required: false,
    default: false
  },
  readonly: {
    type: Boolean,
    required: false,
    default: false
  }
})

const model = defineModel({});
const errorMessage = ref('');

const formState = inject('formState', null);
const registerField = inject('registerField', () => {
});

const validate = () => {
  errorMessage.value = props.rules ? props.rules(model.value) : '';
  return errorMessage.value;
};

registerField(props.name, validate);

</script>

<template>
  <div :class="[classes, errorMessage ? 'has-danger' : '', readonly ? 'readonly-mode' : '', 'p-1']" :style="styles">
    <label v-if="!noLabel" for="for-description" class="form-label">{{ label }}</label>
    <input type="text" oninput="this.value = this.value.replace(/[^0-9]/g, '')" class="form-control font-size-12" id="for-phone" v-model="model" :placeholder="placeholder" :readonly="readonly">

  </div>
</template>

<style scoped>

</style>
