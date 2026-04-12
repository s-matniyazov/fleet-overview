<script setup>
import { computed, inject, ref, watch } from "vue";
import { dateToLong, longToDate } from "@/util/utils.js";

const props = defineProps({
  name: { type: String, default: "dateInput" },
  label: { type: String, default: "dateInput" },
  hint: { type: String, default: "Please select item" },
  classes: { type: String, default: "" },
  styles: { type: String, default: "" },
  rules: Function,
  noLabel: { type: Boolean, default: false },
  readonly: { type: Boolean, default: false },
});

const model = defineModel({});
const errorMessage = ref("");
const display = ref("");

const registerField = inject("registerField", () => {});

const validate = () => {
  errorMessage.value = props.rules ? props.rules(model.value) : "";
  return errorMessage.value;
};
registerField(props.name, validate);

const errorMessages = computed(() =>
  errorMessage.value ? [errorMessage.value] : [],
);

function longToDisplay(m) {
  const d = longToDate(m, 1);
  if (!d) return "";
  const parts = d.split("-");
  return `${parts[1]}.${parts[2]}.${parts[0]}`;
}

watch(
  () => model.value,
  (m) => {
    display.value = longToDisplay(m);
  },
  { immediate: true },
);

function onType(raw) {
  const v = String(raw ?? "").replace(/\D/g, "").slice(0, 8);
  let formatted = v;
  if (v.length >= 5) {
    formatted = v.replace(/(\d{2})(\d{2})(\d{0,4})/, "$1.$2.$3");
  } else if (v.length >= 3) {
    formatted = v.replace(/(\d{2})(\d{0,2})/, "$1.$2");
  }
  display.value = formatted;
  if (v.length === 8) {
    const mm = v.slice(0, 2);
    const dd = v.slice(2, 4);
    const yyyy = v.slice(4, 8);
    model.value = dateToLong(`${yyyy}-${mm}-${dd}`);
  }
}
</script>

<template>
  <div :class="classes" :style="styles">
    <v-text-field
      :model-value="display"
      :label="noLabel ? undefined : label"
      placeholder="MM.DD.YYYY"
      :readonly="readonly"
      :error-messages="errorMessages"
      :hint="hint || undefined"
      persistent-hint
      variant="outlined"
      density="compact"
      hide-details="auto"
      @update:model-value="onType"
    />
  </div>
</template>
