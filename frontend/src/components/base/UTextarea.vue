<script setup>

import {inject, ref} from "vue";

const props = defineProps({
  label: {
    type: String,
    required: false,
    default: 'Textarea'
  },
  placeholder: {
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
    <textarea class="form-control font-size-12" id="for-description" v-model="model" :placeholder="placeholder"
              :rows="rows" :readonly="readonly"></textarea>
  </div>
</template>

<style scoped>
</style>
