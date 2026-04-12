<script setup>
import { computed, inject, ref } from "vue";

const props = defineProps({
  name: {
    type: String,
    required: false,
    default: "input",
  },
  checked: {
    type: Boolean,
    required: false,
    default: false,
  },
  label: {
    type: String,
    required: false,
    default: "input",
  },
  classes: {
    type: String,
    required: false,
    default: "",
  },
  styles: {
    type: String,
    required: false,
    default: "",
  },
  rules: Function,
  readonly: {
    type: Boolean,
    required: false,
    default: false,
  },
});

const model = defineModel({});
const errorMessage = ref("");

const registerField = inject("registerField", () => {});

const validate = () => {
  errorMessage.value = props.rules ? props.rules(model.value) : "";
  return errorMessage.value;
};

registerField(props.name, validate);

const errorMessages = computed(() =>
  errorMessage.value ? [errorMessage.value] : [],
);
</script>

<template>
  <div :class="classes" :style="styles">
    <v-checkbox
      v-model="model"
      :label="label"
      :disabled="readonly"
      :error-messages="errorMessages"
      hide-details="auto"
      density="compact"
      color="primary"
    />
  </div>
</template>
