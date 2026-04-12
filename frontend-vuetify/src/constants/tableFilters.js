/**
 * Shared status filter for list tables (Active / Passive / All).
 * Empty string = no `status` query param (show all).
 */
export const TABLE_STATUS_FILTER_ITEMS = Object.freeze([
  { title: "Active", value: "ACTIVE" },
  { title: "Passive", value: "PASSIVE" },
  { title: "All", value: "" },
]);
