<script setup>

import {inject, ref} from "vue";

const props = defineProps({
  name: {
    type: String,
    required: false,
    default: 'input'
  },
  checked: {
    type: Boolean,
    required: false,
    default: false
  },
  label: {
    type: String,
    required: false,
    default: 'input'
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
    <input class="form-check-input" type="checkbox" v-model="model" :checked="checked" :readonly="readonly">
    <label class="form-check-label px-2">
      {{ label }}
    </label>
    <p v-if="errorMessage" class="pristine-error text-help">{{ errorMessage }}</p>
  </div>
</template>

<style scoped>

</style>
