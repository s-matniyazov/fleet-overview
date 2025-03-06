<template>
  <form @submit.prevent="submitForm" class="p-4 border rounded-lg">
    <slot name="fields" :formData="formData" :errors="errors"></slot>
    <button type="submit" class="mt-4 p-2 bg-blue-500 text-white rounded">Submit</button>
  </form>
</template>

<script setup>
import { defineProps, defineEmits, reactive } from 'vue';

const props = defineProps({
  modelValue: Object,
  rules: Object
});
const emit = defineEmits(['update:modelValue', 'submit']);

const formData = reactive({...props.modelValue});
const errors = reactive({});

const validate = () => {
  Object.keys(props.rules).forEach(field => {
    const rule = props.rules[field];
    errors[field] = rule(formData[field]) || '';
  });
  return Object.values(errors).every(error => !error);
};

const submitForm = () => {
  if (validate()) {
    emit('update:modelValue', formData);
    emit('submit', formData);
  }
};
</script>
