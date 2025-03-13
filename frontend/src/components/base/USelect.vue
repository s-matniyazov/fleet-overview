<script setup>
import {inject, ref} from "vue";

const props = defineProps({
  name: {
    type: String,
    required: false,
  },
  label: {
    type: String,
    required: false,
  },
  hint: {
    type: String,
    required: false,
    default: 'Please select'
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
  rules: Function,
})

const selected = defineModel({});
const errorMessage = ref('');

const formState = inject('formState', null);
const registerField = inject('registerField', () => {});

const validate = () => {
  errorMessage.value = props.rules ? props.rules(selected.value) : '';
  return errorMessage.value;
};

registerField(props.name, validate);

</script>

<template>
  <div :class="`${classes} ${errorMessage && 'has-danger'}`" :style="styles">
    <label v-if="label" class="form-label">{{ label }}</label>
    <select class="form-control form-select font-size-12" v-model="selected">
<!--      <option selected disabled> - </option>-->
      <option v-for="option in items" :value="option[option_value]">
        {{ option[option_name] }}
      </option>
    </select>
    <div class="invalid-feedback">{{ hint }}</div>
    <p v-if="errorMessage" class="pristine-error text-help">{{ errorMessage }}</p>
  </div>
</template>

<style scoped>

</style>
