<script setup>
import { inject, ref, watch } from 'vue';

const props = defineProps({
  modelValue: String,
  label: String,
  name: String,
  rules: Function, // Custom validation function
});
const emit = defineEmits(['update:modelValue']);

const value = ref(props.modelValue);
const errorMessage = ref('');

const formState = inject('formState', null);
const registerField = inject('registerField', () => {});

watch(value, (newVal) => emit('update:modelValue', newVal));

const validate = () => {
  errorMessage.value = props.rules ? props.rules(value.value) : '';
  return errorMessage.value;
};

registerField(props.name, validate);
</script>

<template>
  <div class="input-group">
    <label v-if="label">{{ label }}</label>
    <input v-model="value" type="text" />
    <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
  </div>
</template>
<style scoped>
.input-group {
  margin-bottom: 10px;
}
.error {
  color: red;
  font-size: 12px;
}
</style>

