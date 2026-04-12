<script setup>
import { computed, inject, ref } from "vue";
import { VueDatePicker } from "@vuepic/vue-datepicker";
import "@vuepic/vue-datepicker/dist/main.css";

const props = defineProps({
  name: {
    type: String,
    required: false,
    default: "",
  },
  label: {
    type: String,
    required: false,
    default: "",
  },
  placeholder: {
    type: String,
    required: false,
    default: "",
  },
  hint: {
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
  type: {
    type: String,
    required: false,
    default: "text",
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
  id: {
    type: String,
    required: false,
    default: "",
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

const prependInnerIcon = computed(() =>
  props.icon && String(props.icon).startsWith("mdi") ? props.icon : undefined,
);
</script>

<template>
  <div :class="classes" :style="styles">
    <!-- Checkbox type -->
    <template v-if="type === 'checkbox'">
      <v-checkbox
        v-model="model"
        :disabled="readonly"
        :error-messages="errorMessages"
        hide-details="auto"
        density="compact"
        color="primary"
      >
        <template #label>
          <span class="d-inline-flex align-center">
            <v-icon v-if="icon" :icon="icon" size="small" class="me-1" />
            {{ label }}
          </span>
        </template>
      </v-checkbox>
    </template>

    <!-- Date input: VueDatePicker (model often Date / ISO) -->
    <template v-else-if="type === 'date'">
      <div class="u-input-date">
        <div v-if="label" class="text-caption text-medium-emphasis mb-1">
          <v-icon v-if="icon" :icon="icon" size="x-small" class="me-1" />
          {{ label }}
        </div>
        <VueDatePicker
          v-model="model"
          auto-apply
          :enable-time-picker="false"
          :dark="true"
          class="u-input-date__picker"
        />
        <p v-if="errorMessage" class="text-error text-caption mt-1 mb-0">
          {{ errorMessage }}
        </p>
        <p v-else-if="hint" class="text-caption text-medium-emphasis mt-1 mb-0">
          {{ hint }}
        </p>
      </div>
    </template>

    <!-- Default: text, number, email, etc. -->
    <template v-else>
      <v-text-field
        class="u-form-field"
        v-model="model"
        :type="type"
        :label="label"
        :placeholder="placeholder"
        :readonly="readonly"
        :prepend-inner-icon="prependInnerIcon"
        :error-messages="errorMessages"
        :hint="hint || undefined"
        :persistent-hint="Boolean(hint)"
        variant="outlined"
        hide-details="auto"
      />
    </template>
  </div>
</template>

<style scoped>
.u-input-date__picker {
  width: 100%;
}

.u-input-date :deep(.dp__input) {
  border-radius: 10px;
  border: thin solid rgba(var(--v-border-color), var(--v-border-opacity));
  background: rgb(var(--v-theme-surface));
  color: rgba(var(--v-theme-on-surface), var(--v-high-emphasis-opacity));
  min-height: 40px;
  font-size: 0.875rem;
}

.u-input-date :deep(.dp__input_wrap) {
  width: 100%;
}
</style>
