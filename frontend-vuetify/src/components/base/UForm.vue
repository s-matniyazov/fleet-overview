<script setup>
import { provide, reactive, defineExpose } from 'vue';

defineProps({
  /** HTML `form` id — footer tugmalarda `form="..."` bilan bog‘lash uchun */
  id: {
    type: String,
    default: undefined,
  },
});

const emit = defineEmits(['submit']);
const formState = reactive({
  fields: {},
  errors: {},
});

const registerField = (name, validate) => {
  formState.fields[name] = validate;
};

const validateForm = () => {
  let isValid = true;
  formState.errors = {};

  for (const [name, validate] of Object.entries(formState.fields)) {
    const error = validate();
    if (error) {
      isValid = false;
      formState.errors[name] = error;
    }
  }

  return isValid;
};

const submitForm = () => {
  if (validateForm()) {
    emit('submit');
  }
};

provide('registerField', registerField);
provide('formState', formState);

defineExpose({ submitForm });
</script>

<template>
  <form :id="id" class="u-form" @submit.prevent="submitForm">
    <slot />
  </form>
</template>
