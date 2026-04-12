/**
 * Coerce API / JSON scalar ids for v-select `item-value` (number|null).
 * String "12" vs number 12 otherwise breaks strict equality and labels stay blank.
 */
export function parseOptionalId(value) {
  if (value === null || value === undefined || value === "") return null;
  const n = Number(value);
  return Number.isFinite(n) ? n : null;
}

/**
 * Map API rows to Vuetify v-select items: { title, value }.
 * @param {Array} items
 * @param {string|Function} optionName — key or (row) => label
 * @param {string} optionValue — key for model value
 */
export function mapSelectItems(items, optionName = "name", optionValue = "id") {
  if (!items?.length) return [];
  return items.map((option) => {
    let value = option[optionValue];
    if (
      optionValue === "id" &&
      value !== null &&
      value !== undefined &&
      value !== ""
    ) {
      const n = Number(value);
      value = Number.isFinite(n) ? n : value;
    }
    return {
      title:
        typeof optionName === "function"
          ? optionName(option)
          : option[optionName],
      value,
    };
  });
}
