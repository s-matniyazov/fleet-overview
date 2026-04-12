<script setup>
import { computed } from "vue";
import { longToDate } from "@/util/utils.js";

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

const isActive = computed(
  () => props.file && props.file.status === "ACTIVE",
);

function getDataTitle(type) {
  switch (type) {
    case "HVUT_2290":
      return "Next Final Filing Date";
    case "W_9":
      return "Next Update Date";
    case "MCS_150":
      return "Next Filing Date";
    case "SSN":
      return "Social Security Number";
    case "CLEARING_HOUSE":
    case "MVR":
      return "Last Collected On";
    case "ARTICLES_OF_INCORPORATION":
    case "MC_CERTIFICATE":
    case "OWNER_OPERATOR_AGREEMENT":
    case "DRIVER_AGREEMENT":
    case "CORRECTION":
    case "CERTIFICATION":
      return "";
    case "DRUG_TEST_RESULT":
      return "Test date";
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
    case "MCS_150":
      return longToDate(props.file.nextUpdateDate || 1);
    case "SSN":
      return props.file.socialSecurityNumber;
    case "CLEARING_HOUSE":
    case "MVR":
      return longToDate(props.file.lastCollectedOn || 1);
    case "ARTICLES_OF_INCORPORATION":
    case "MC_CERTIFICATE":
    case "OWNER_OPERATOR_AGREEMENT":
    case "DRIVER_AGREEMENT":
    case "CORRECTION":
    case "CERTIFICATION":
      return props.file.expirationDate
        ? longToDate(props.file.expirationDate)
        : "—";
    default:
      return longToDate(props.file.expirationDate || 1);
  }
}

const metaLabel = computed(() => getDataTitle(props.type));
const metaValue = computed(() => getData(props.type));
</script>

<template>
  <div
    class="document-mini-card"
    :class="isActive ? 'document-mini-card--ok' : 'document-mini-card--na'"
  >
    <div class="document-mini-card__title nowrap-scroll">{{ name }}</div>

    <div class="document-mini-card__meta">
      <div
        v-if="metaLabel"
        class="document-mini-card__label nowrap-scroll"
      >
        {{ metaLabel }}
      </div>
      <div class="document-mini-card__value nowrap-scroll">{{ metaValue }}</div>
    </div>

    <div class="document-mini-card__row">
      <img
        v-if="isActive"
        src="@/assets/icons/file-check-md.svg"
        alt=""
        class="document-mini-card__icon"
      />
      <img
        v-else
        src="@/assets/icons/file-na-md.svg"
        alt=""
        class="document-mini-card__icon"
      />
      <div class="document-mini-card__stats">
        <div class="document-mini-card__stat">
          <div class="document-mini-card__stat-label">Completed by</div>
          <div class="document-mini-card__stat-value nowrap-scroll">
            {{ file?.createdBy?.name || "N/A" }}
          </div>
        </div>
        <div class="document-mini-card__stat">
          <div class="document-mini-card__stat-label">Completed date</div>
          <div class="document-mini-card__stat-value nowrap-scroll">
            {{ file ? longToDate(file?.created || 1) : "N/A" }}
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* FileMiniCard bilan bir xil fleet-dark outlined uslub (Driver/Truck/… Documents) */
.document-mini-card {
  box-sizing: border-box;
  width: 100%;
  min-width: 0;
  max-width: 100%;
  padding: 8px 10px;
  border-radius: 10px;
  border: 1px solid rgba(var(--v-theme-on-surface), 0.14);
  background: rgba(var(--v-theme-surface-variant), 0.45);
  cursor: pointer;
  transition: border-color 0.15s ease, background 0.15s ease;
}

.document-mini-card:hover {
  border-color: rgba(var(--v-theme-on-surface), 0.22);
  background: rgba(var(--v-theme-surface-variant), 0.65);
}

.document-mini-card--ok {
  border-color: rgba(var(--v-theme-success), 0.38);
  background: rgba(var(--v-theme-success), 0.09);
}

.document-mini-card--ok:hover {
  border-color: rgba(var(--v-theme-success), 0.5);
  background: rgba(var(--v-theme-success), 0.12);
}

.document-mini-card--na {
  border-color: rgba(var(--v-theme-error), 0.28);
  background: rgba(var(--v-theme-error), 0.06);
}

.document-mini-card--na:hover {
  border-color: rgba(var(--v-theme-error), 0.4);
  background: rgba(var(--v-theme-error), 0.09);
}

.document-mini-card__title {
  font-size: 13px;
  font-weight: 700;
  color: rgb(var(--v-theme-on-surface));
  margin-bottom: 6px;
  line-height: 1.35;
}

.document-mini-card__meta {
  margin-bottom: 8px;
  min-width: 0;
}

.document-mini-card__label {
  font-size: 10px;
  font-weight: 600;
  letter-spacing: 0.02em;
  text-transform: uppercase;
  color: rgba(var(--v-theme-on-surface), 0.55);
  line-height: 1.3;
}

.document-mini-card__value {
  font-size: 12px;
  font-weight: 600;
  color: rgb(var(--v-theme-on-surface));
  line-height: 1.35;
}

.document-mini-card__row {
  display: flex;
  align-items: flex-start;
  gap: 10px;
  min-width: 0;
}

.document-mini-card__icon {
  width: 22px;
  height: 22px;
  flex-shrink: 0;
  object-fit: contain;
  margin-top: 2px;
}

.document-mini-card__stats {
  flex: 1 1 auto;
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 8px 12px;
  min-width: 0;
}

.document-mini-card__stat-label {
  font-size: 10px;
  font-weight: 600;
  text-transform: uppercase;
  color: rgba(var(--v-theme-on-surface), 0.55);
  margin-bottom: 2px;
}

.document-mini-card__stat-value {
  font-size: 11px;
  font-weight: 600;
  color: rgb(var(--v-theme-on-surface));
}

.nowrap-scroll {
  white-space: nowrap;
  overflow-x: auto;
  overflow-y: hidden;
  max-width: 100%;
  scrollbar-width: thin;
}

.nowrap-scroll::-webkit-scrollbar {
  height: 3px;
}

.nowrap-scroll::-webkit-scrollbar-thumb {
  background: rgba(var(--v-theme-on-surface), 0.25);
  border-radius: 3px;
}
</style>
