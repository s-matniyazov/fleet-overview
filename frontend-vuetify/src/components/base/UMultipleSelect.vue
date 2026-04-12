<script setup>
import { computed, inject, ref } from "vue";

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
    default: "",
  },
  optionName: {
    type: String,
    required: false,
    default: "id",
  },
  optionValue: {
    type: String,
    required: false,
    default: "id",
  },
  items: {
    type: Array,
    required: true,
    default: () => [],
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
  placeholder: {
    type: String,
    default: "Select multiple",
  },
  readonly: {
    type: Boolean,
    required: false,
    default: false,
  },
});

const selected = defineModel({
  type: Array,
  default: () => [],
});
const errorMessage = ref("");

const registerField = inject("registerField", () => {});

const validate = () => {
  errorMessage.value = props.rules ? props.rules(selected.value) : "";
  return errorMessage.value;
};

registerField(props.name, validate);

const errorMessages = computed(() =>
  errorMessage.value ? [errorMessage.value] : [],
);

const mappedItems = computed(() =>
  (props.items || []).map((item) => ({
    title: item[props.optionName],
    value: item[props.optionValue],
  })),
);
</script>

<template>
  <div :class="classes" :style="styles">
    <v-select
      v-model="selected"
      :items="mappedItems"
      item-title="title"
      item-value="value"
      :label="label"
      :readonly="readonly"
      :placeholder="placeholder"
      multiple
      chips
      closable-chips
      :error-messages="errorMessages"
      :hint="hint || undefined"
      persistent-hint
      variant="outlined"
      density="compact"
      hide-details="auto"
    />
  </div>
</template>
