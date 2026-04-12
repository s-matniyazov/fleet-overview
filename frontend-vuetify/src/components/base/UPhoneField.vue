<script setup>
import { computed, inject, ref } from "vue";

const props = defineProps({
  name: {
    type: String,
    required: false,
    default: "phone",
  },
  label: {
    type: String,
    required: false,
    default: "Phone",
  },
  placeholder: {
    type: String,
    required: false,
    default: "Please enter phone number",
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
  noLabel: {
    type: Boolean,
    required: false,
    default: false,
  },
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

const onDigitsOnly = (v) => {
  model.value = String(v ?? "").replace(/\D/g, "");
};
</script>

<template>
  <div :class="classes" :style="styles">
    <v-text-field
      :model-value="model"
      :label="noLabel ? undefined : label"
      :placeholder="placeholder"
      :readonly="readonly"
      :error-messages="errorMessages"
      type="text"
      inputmode="numeric"
      variant="outlined"
      density="compact"
      hide-details="auto"
      @update:model-value="onDigitsOnly"
    />
  </div>
</template>
