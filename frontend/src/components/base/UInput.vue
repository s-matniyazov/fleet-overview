<script setup>

import {inject, ref} from "vue";
const emit = defineEmits(['update:modelValue'])

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
  },
  icon:{
    type:String,
    required:false,
    default:''
  },
  id:{
    type:String,
    required:false,
    default:''
  },
  modelValue: [String, Boolean, Number],
  value: [String, Boolean, Number],


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
  <!-- Radio type -->
  <template v-if="type === 'radio'">
    <div class="form-check">
      <input
        class="form-check-input"
        type="radio"
        v-model="model"
        :name = "name"
        :id="id"
        :value="value"
        :disabled="readonly"
        @change="$emit('update:modelValue', value)"
      />
      <label class="form-check-label">
        <i v-if="icon" :class="['mdi', icon, 'me-1']"></i>
        {{ label }}
      </label>
    </div>
  </template>

  <!-- Checkbox type -->
  <template v-else-if="type === 'checkbox'">
    <div class="form-check">
      <input
        class="form-check-input rounded"
        type="checkbox"
        v-model="model"
        :name = "name"
        :id="id"
        :disabled="readonly"
      />
      <label class="form-check-label">
        <i v-if="icon" :class="['mdi', icon, 'me-1']"></i>
        {{ label }}
      </label>
    </div>
  </template>

  <!-- Default input type -->
  <template v-else>
    <label v-if="label" class="form-label">
      <i v-if="icon" :class="['mdi', icon, 'me-1']"></i>
      {{ label }}
    </label>
    <input
      class="form-control font-size-12"
      :placeholder="placeholder"
      :type="type"
      :name="name"
      v-model="model"
      :disabled="readonly"
    />
  </template>

  <div class="invalid-feedback">{{ hint }}</div>
  <p v-if="errorMessage" class="pristine-error text-help">{{ errorMessage }}</p>
</div>
</template>

<style scoped>
</style>
