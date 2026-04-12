<script setup>
import {longToDate} from "@/util/utils.js";

defineProps({
  name: {
    type: String,
    required: true,
  },
  type: {
    type: String,
    required: true,
  },
  file: {
    type: Object,
    required: false,
    default: undefined,
  },
});
</script>

<template>
  <div
    class="permit-mini-card"
    :class="file ? 'permit-mini-card--ok' : 'permit-mini-card--na'"
    :title="name"
  >
    <div class="permit-mini-card__icon" aria-hidden="true">
      <img
        v-if="file"
        src="../assets/icons/file-check-sm.svg"
        alt=""
        class="permit-mini-card__img"
      />
      <img
        v-else
        src="../assets/icons/file-na-sm.svg"
        alt=""
        class="permit-mini-card__img"
      />
    </div>
    <span class="permit-mini-card__code">{{ type }}</span>
    <span class="permit-mini-card__date">{{
      file ? longToDate(file?.expirationDate || 1) : "—"
    }}</span>
  </div>
</template>

<style scoped>
/* Single-line chip: barcha permitlar jadvalda scrollsiz bir ko‘rinishda */
.permit-mini-card {
  display: inline-flex;
  align-items: center;
  gap: 4px;
  min-width: 0;
  padding: 2px 6px;
  border-radius: 6px;
  font-size: 10px;
  line-height: 1.2;
  border: 1px solid rgba(var(--v-theme-on-surface), 0.12);
  background: rgba(var(--v-theme-surface-variant), 0.45);
  white-space: nowrap;
}

.permit-mini-card--ok {
  border-color: rgba(var(--v-theme-success), 0.38);
  background: rgba(var(--v-theme-success), 0.09);
}

.permit-mini-card--na {
  border-color: rgba(var(--v-theme-error), 0.25);
  background: rgba(var(--v-theme-error), 0.05);
}

.permit-mini-card__icon {
  flex: 0 0 auto;
  display: flex;
  align-items: center;
}

.permit-mini-card__img {
  display: block;
  width: 11px;
  height: 11px;
}

.permit-mini-card__code {
  font-weight: 700;
  color: rgb(var(--v-theme-on-surface));
  letter-spacing: 0.02em;
}

.permit-mini-card__date {
  font-weight: 600;
  font-size: 9px;
  color: rgba(var(--v-theme-on-surface), 0.8);
}
</style>
