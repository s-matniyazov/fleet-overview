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
    default: "Please select",
  },
  option_name: {
    type: [String, Function],
    required: false,
    default: "id",
  },
  option_value: {
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
  readonly: {
    type: Boolean,
    required: false,
    default: false,
  },
  icon: {
    type: String,
    required: false,
    default: "",
  },
});

const selected = defineModel({});
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

const prependInnerIcon = computed(() =>
  props.icon && String(props.icon).startsWith("mdi") ? props.icon : undefined,
);

const mappedItems = computed(() =>
  (props.items || []).map((option) => ({
    title:
      typeof props.option_name === "function"
        ? props.option_name(option)
        : option[props.option_name],
    value: option[props.option_value],
    raw: option,
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
      :prepend-inner-icon="prependInnerIcon"
      :error-messages="errorMessages"
      :hint="hint || undefined"
      persistent-hint
      clearable
      variant="outlined"
      density="compact"
      hide-details="auto"
    />
  </div>
</template>
