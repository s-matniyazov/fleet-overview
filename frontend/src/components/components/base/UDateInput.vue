<script setup>
import {computed, inject, ref} from "vue";
import {dateToLong, longToDate} from "@/util/utils.js";

const props = defineProps({
  name: { type: String, default: 'dateInput' },
  label: { type: String, default: 'dateInput' },
  hint: { type: String, default: 'Please select item' },
  classes: { type: String, default: '' },
  styles: { type: String, default: '' },
  rules: Function,
  noLabel: { type: Boolean, default: false },
  readonly: { type: Boolean, default: false }
})

const model = defineModel({});
const errorMessage = ref('');

const formState = inject('formState', null);
const registerField = inject('registerField', () => {});

const validate = () => {
  errorMessage.value = props.rules ? props.rules(model.value) : '';
  return errorMessage.value;
};
registerField(props.name, validate);

// 🔹 inputda MM.DD.YYYY ko‘rsatish
const inputDate = computed({
  get() {
    const d = longToDate(model.value, 1); // YYYY-MM-DD
    if (!d) return "";
    const parts = d.split("-");
    return `${parts[1]}.${parts[2]}.${parts[0]}`; // MM.DD.YYYY
  },
  set(newValue) {
    // faqat raqamlar
    let v = newValue.replace(/\D/g, "").slice(0, 8);
    if (v.length === 8) {
      const mm = v.slice(0, 2);
      const dd = v.slice(2, 4);
      const yyyy = v.slice(4, 8);
      model.value = dateToLong(`${yyyy}-${mm}-${dd}`);
    }
  }
});

// 🔹 mask funksiya
const onInput = (e) => {
  let v = e.target.value.replace(/\D/g, '').slice(0, 8);
  if (v.length >= 5) {
    e.target.value = v.replace(/(\d{2})(\d{2})(\d{0,4})/, "$1.$2.$3");
  } else if (v.length >= 3) {
    e.target.value = v.replace(/(\d{2})(\d{0,2})/, "$1.$2");
  }
  inputDate.value = e.target.value;
};
</script>

<template>
  <div :class="[classes, errorMessage ? 'has-danger' : '', readonly ? 'readonly-mode' : '', 'p-1']" :style="styles">
    <label v-if="!noLabel" for="date-label" class="form-label">{{ label }}</label>

    <input
        id="date-label"
        class="form-control font-size-12"
        type="text"
        :readonly="readonly"
        v-model="inputDate"
        @input="onInput"
        placeholder="MM.DD.YYYY"
    >

    <div class="invalid-feedback">{{ hint }}</div>
    <p v-if="errorMessage" class="pristine-error text-help">{{ errorMessage }}</p>
  </div>
</template>
