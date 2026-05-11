<script setup>
import { useI18n } from "vue-i18n";
import { dataOrTire, longToDateTime } from "@/util/utils.js";

const { t } = useI18n();

const props = defineProps({
  data: {
    type: Object,
    required: true,
  },
});
</script>

<template>
  <div class="truck-info">

    <div class="truck-info__cols">

      <!-- Left: sections -->
      <div class="truck-info__left">

        <!-- Unit Details -->
        <div class="info-card mb-3">
          <div class="info-section-header">
            <span class="info-badge">1</span>
            <span class="info-section-title">Unit Details</span>
          </div>
          <div class="kv-row">
            <span class="kv-label">Operated By</span>
            <span class="kv-value">{{ dataOrTire(data?.companyName) }}</span>
          </div>
          <div class="kv-row">
            <span class="kv-label">Current Driver(s)</span>
            <span class="kv-value">{{ dataOrTire(data?.driverName) }}</span>
          </div>
          <div class="kv-row">
            <span class="kv-label">Carrier Responsibility #</span>
            <span class="kv-value">{{ dataOrTire(data?.carrierResponsibleForSafety) }}</span>
          </div>
          <div class="kv-row">
            <span class="kv-label">Unit #</span>
            <span class="kv-value">{{ dataOrTire(data?.unit) }}</span>
          </div>
          <div class="kv-row">
            <span class="kv-label">License Plate #</span>
            <span class="kv-value">{{ dataOrTire(data?.licensePlate) }}</span>
          </div>
          <div class="kv-row">
            <span class="kv-label">State</span>
            <span class="kv-value">{{ dataOrTire(data?.stateName) }}</span>
          </div>
          <div class="kv-row">
            <span class="kv-label">Make</span>
            <span class="kv-value">{{ dataOrTire(data?.modelMakerName) }}</span>
          </div>
          <div class="kv-row">
            <span class="kv-label">Year</span>
            <span class="kv-value">{{ dataOrTire(data?.year) }}</span>
          </div>
          <div class="kv-row">
            <span class="kv-label">Fuel Type</span>
            <span class="kv-value">{{ dataOrTire(data?.fuelTypeName) }}</span>
          </div>
          <div class="kv-row">
            <span class="kv-label">Gross Weight</span>
            <span class="kv-value">{{ dataOrTire(data?.grossWeight) }}</span>
          </div>
          <div class="kv-row">
            <span class="kv-label">Number of Axles</span>
            <span class="kv-value">{{ dataOrTire(data?.axles) }}</span>
          </div>
          <div class="kv-row">
            <span class="kv-label">VIN #</span>
            <span class="kv-value">{{ dataOrTire(data?.vin) }}</span>
          </div>
        </div>

        <!-- Ownership Details -->
        <div class="info-card mb-3">
          <div class="info-section-header">
            <span class="info-badge">2</span>
            <span class="info-section-title">Ownership Details</span>
          </div>
          <div class="kv-row">
            <span class="kv-label">Ownership</span>
            <span class="kv-value">{{ dataOrTire(data?.ownershipTypeName) }}</span>
          </div>
          <div class="kv-row">
            <span class="kv-label">Include to the IFTA</span>
            <span class="kv-value">{{ data?.includeIFTA ? "YES" : "NO" }}</span>
          </div>
        </div>

        <!-- Other Details -->
        <div class="info-card">
          <div class="info-section-header">
            <span class="info-badge">3</span>
            <span class="info-section-title">Other Details</span>
          </div>
          <div class="kv-row">
            <span class="kv-label">Purchase Type</span>
            <span class="kv-value">{{ dataOrTire(data?.purchaseTypeName) }}</span>
          </div>
          <div class="kv-row">
            <span class="kv-label">Status</span>
            <span class="kv-value">{{ dataOrTire(data?.status) }}</span>
          </div>
          <div class="kv-row">
            <span class="kv-label">Status From</span>
            <span class="kv-value">{{ dataOrTire(longToDateTime(data?.statusDate || 1)) }}</span>
          </div>
        </div>

      </div>

      <!-- Right: notes -->
      <div class="truck-info__right">
        <div class="info-card h-100">
          <div class="info-section-header">
            <span class="info-section-title">{{ t("additional_notes") }}</span>
          </div>
          <div class="notes-body">
            {{ dataOrTire(data?.description) }}
          </div>
        </div>
      </div>

    </div>
  </div>
</template>

<style scoped>
.truck-info {
  width: 100%;
  padding: 4px;
}

.truck-info__cols {
  display: flex;
  gap: 16px;
  align-items: flex-start;
}

.truck-info__left {
  flex: 1 1 55%;
  min-width: 0;
  display: flex;
  flex-direction: column;
}

.truck-info__right {
  flex: 1 1 45%;
  min-width: 0;
}

/* shared card */
.info-card {
  background: rgb(var(--v-theme-surface-variant));
  border: thin solid rgba(var(--v-border-color), var(--v-border-opacity));
  border-radius: 10px;
  padding: 14px 16px;
  display: flex;
  flex-direction: column;
  gap: 6px;
}

.info-section-header {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-bottom: 6px;
  padding-bottom: 8px;
  border-bottom: thin solid rgba(var(--v-border-color), var(--v-border-opacity));
}

.info-badge {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  width: 22px;
  height: 22px;
  border-radius: 50%;
  background: rgb(var(--v-theme-primary));
  color: rgb(var(--v-theme-on-primary));
  font-size: 11px;
  font-weight: 700;
  flex-shrink: 0;
}

.info-section-title {
  font-size: 0.9375rem;
  font-weight: 600;
  color: rgba(var(--v-theme-on-surface), var(--v-high-emphasis-opacity));
}

/* key-value row */
.kv-row {
  display: flex;
  align-items: baseline;
  gap: 8px;
  min-height: 24px;
}

.kv-label {
  flex: 0 0 44%;
  text-align: right;
  font-size: 12px;
  font-weight: 500;
  color: rgba(var(--v-theme-on-surface), var(--v-medium-emphasis-opacity));
  padding-right: 4px;
}

.kv-value {
  flex: 1 1 0;
  font-size: 13.5px;
  font-weight: 500;
  color: rgba(var(--v-theme-on-surface), var(--v-high-emphasis-opacity));
  word-break: break-word;
}

/* notes */
.notes-body {
  font-size: 13.5px;
  color: rgba(var(--v-theme-on-surface), var(--v-high-emphasis-opacity));
  line-height: 1.6;
  white-space: pre-wrap;
  word-wrap: break-word;
  overflow-y: auto;
}

@media (max-width: 768px) {
  .truck-info__cols {
    flex-direction: column;
  }
  .truck-info__left,
  .truck-info__right {
    flex: none;
    width: 100%;
  }
  .kv-label {
    flex: 0 0 40%;
  }
}
</style>
