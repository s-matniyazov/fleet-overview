<script setup>
import {longToDate} from "@/util/utils.js";

const props = defineProps({
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

function getDataTitle(type) {
  switch (type) {
    case "HVUT_2290":
      return "Next Final Filing Date";
    case "W_9":
      return "Next Update Date";
    case "SSN":
      return "Social Security Number";
    case "MC_CERTIFICATE":
    case "OWNER_OPERATOR_AGREEMENT":
    case "DRIVER_AGREEMENT":
      return "File exist";
    case "DRUG_TEST_RESULT":
      return "Test date:";
    default:
      return "Expiration Date";
  }
}

function getData(type) {
  if (!props.file) return "N/A";
  switch (type) {
    case "HVUT_2290":
      return props.file.filedPeriod;
    case "W_9":
      return longToDate(props.file.nextUpdateDate || 1);
    case "SSN":
      return props.file.socialSecurityNumber;
    case "ARTICLES_OF_INCORPORATION":
    case "MC_CERTIFICATE":
    case "OWNER_OPERATOR_AGREEMENT":
    case "DRIVER_AGREEMENT":
      return "YES";
    case "CORRECTION":
    case "CERTIFICATION":
      return longToDate(props.file.expirationDate || 1);
    default:
      return longToDate(props.file.expirationDate || 1);
  }
}
</script>

<template>
  <div
    class="file-mini-card"
    :class="file ? 'file-mini-card--ok' : 'file-mini-card--missing'"
  >
    <div class="file-mini-card__meta">
      <div class="file-mini-card__label">{{ getDataTitle(type) }}</div>
      <div class="file-mini-card__value">{{ getData(type) }}</div>
    </div>
    <div class="file-mini-card__row">
      <img
        v-if="file"
        src="../assets/icons/file-check.svg"
        alt=""
        class="file-mini-card__icon"
      />
      <img
        v-else
        src="@/assets/icons/file-na.svg"
        alt=""
        class="file-mini-card__icon"
      />
      <span class="file-mini-card__doc">
        <strong>{{ name }}</strong>
        <span class="file-mini-card__muted"> · On file</span>
      </span>
    </div>
  </div>
</template>

<style scoped>
/* Jadval kataklarida barcha file ustunlari uchun bir xil ko‘rinish (fleet dark) */
.file-mini-card {
  box-sizing: border-box;
  /* Katak ichida havo — margin + kenglik jadval yig‘ilishini buzmaydi */
  margin: 4px 6px;
  width: calc(100% - 12px);
  min-width: 0;
  max-width: calc(100% - 12px);
  padding: 8px 10px;
  border-radius: 10px;
  border: 1px solid rgba(var(--v-theme-on-surface), 0.14);
  background: rgba(var(--v-theme-surface-variant), 0.45);
  cursor: pointer;
  transition: border-color 0.15s ease, background 0.15s ease;
}

.file-mini-card:hover {
  border-color: rgba(var(--v-theme-on-surface), 0.22);
  background: rgba(var(--v-theme-surface-variant), 0.65);
}

.file-mini-card--ok {
  border-color: rgba(var(--v-theme-success), 0.38);
  background: rgba(var(--v-theme-success), 0.09);
}

.file-mini-card--ok:hover {
  border-color: rgba(var(--v-theme-success), 0.5);
  background: rgba(var(--v-theme-success), 0.12);
}

.file-mini-card--missing {
  border-color: rgba(var(--v-theme-error), 0.28);
  background: rgba(var(--v-theme-error), 0.06);
}

.file-mini-card--missing:hover {
  border-color: rgba(var(--v-theme-error), 0.4);
  background: rgba(var(--v-theme-error), 0.09);
}

.file-mini-card__meta {
  margin-bottom: 6px;
  min-width: 0;
}

/* Uzun so‘z/qator pastga tushmasin — yon tomonga (gorizontal scroll) */
.file-mini-card__label,
.file-mini-card__value {
  white-space: nowrap;
  overflow-x: auto;
  overflow-y: hidden;
  max-width: 100%;
  scrollbar-width: thin;
}

.file-mini-card__label::-webkit-scrollbar,
.file-mini-card__value::-webkit-scrollbar,
.file-mini-card__doc::-webkit-scrollbar {
  height: 3px;
}

.file-mini-card__label::-webkit-scrollbar-thumb,
.file-mini-card__value::-webkit-scrollbar-thumb,
.file-mini-card__doc::-webkit-scrollbar-thumb {
  background: rgba(var(--v-theme-on-surface), 0.25);
  border-radius: 3px;
}

.file-mini-card__label {
  font-size: 10px;
  font-weight: 600;
  letter-spacing: 0.02em;
  text-transform: uppercase;
  color: rgba(var(--v-theme-on-surface), 0.55);
  line-height: 1.3;
}

.file-mini-card__value {
  font-size: 12px;
  font-weight: 600;
  color: rgb(var(--v-theme-on-surface));
  line-height: 1.35;
}

.file-mini-card__row {
  display: flex;
  align-items: center;
  gap: 8px;
  min-width: 0;
}

.file-mini-card__icon {
  width: 20px;
  height: 20px;
  flex-shrink: 0;
  object-fit: contain;
}

.file-mini-card__doc {
  font-size: 11px;
  font-weight: 500;
  color: rgb(var(--v-theme-on-surface));
  line-height: 1.3;
  flex: 1 1 auto;
  min-width: 0;
  white-space: nowrap;
  overflow-x: auto;
  overflow-y: hidden;
  scrollbar-width: thin;
}

.file-mini-card__muted {
  font-weight: 500;
  color: rgba(var(--v-theme-on-surface), 0.55);
}
</style>
