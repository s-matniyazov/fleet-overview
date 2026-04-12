import { inject, ref, computed } from "vue";

/**
 * UForm integration: register validation like the old USelect.
 * @param {string|null|undefined} name — field key for registerField; omit to skip
 * @param {() => *} getValue — current v-model value
 * @param {(val: *) => string|false|undefined|null} rules — return message if invalid
 */
export function useFormSelectField(name, getValue, rules) {
  const errorMessage = ref("");
  const registerField = inject("registerField", () => {});

  const validate = () => {
    const msg = rules ? rules(getValue()) : "";
    errorMessage.value =
      msg === false || msg === null || msg === undefined ? "" : String(msg);
    return errorMessage.value;
  };

  if (name != null && name !== "") {
    registerField(name, validate);
  }

  const errorMessages = computed(() =>
    errorMessage.value ? [errorMessage.value] : [],
  );

  return { errorMessages, validate };
}
