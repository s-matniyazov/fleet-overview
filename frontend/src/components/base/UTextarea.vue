<script setup>
import { computed, inject, ref } from "vue";

const props = defineProps({
  name: {
    type: String,
    required: false,
    default: "",
  },
  label: {
    type: String,
    required: false,
    default: "Textarea",
  },
  placeholder: {
    type: String,
    required: false,
    default: "",
  },
  rows: {
    type: Number,
    required: false,
    default: 3,
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
</script>

<template>
  <div :class="classes" :style="styles">
    <v-textarea
      v-model="model"
      :label="noLabel ? undefined : label"
      :placeholder="placeholder"
      :readonly="readonly"
      :rows="rows"
      auto-grow
      :error-messages="errorMessages"
      variant="outlined"
      density="compact"
      hide-details="auto"
    />
  </div>
</template>
