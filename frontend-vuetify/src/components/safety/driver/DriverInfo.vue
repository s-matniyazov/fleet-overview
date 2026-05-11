<script setup>
import { useI18n } from "vue-i18n";
import { dataOrTire, longToDate, longToDateTime } from "@/util/utils.js";

const { t } = useI18n();

const props = defineProps({
  data: {
    type: Object,
    required: true,
  },
});
</script>

<template>
  <div class="driver-info">

    <!-- Employment row -->
    <div class="info-card mb-4">
      <div class="info-grid-2">
        <div class="info-field">
          <span class="info-label">Hired To</span>
          <span class="info-value">{{ dataOrTire(data?.companyName) }}</span>
        </div>
        <div class="info-field">
          <span class="info-label">Hired Date</span>
          <span class="info-value">{{ longToDate(dataOrTire(data?.hireDate)) }}</span>
        </div>
        <div class="info-field">
          <span class="info-label">Terminated Date</span>
          <span class="info-value">{{ dataOrTire(longToDateTime(data?.terminationDate)) }}</span>
        </div>
        <div class="info-field">
          <span class="info-label">Terminated Reason</span>
          <span class="info-value">{{ dataOrTire(data?.terminationReason) }}</span>
        </div>
      </div>
    </div>

    <!-- Detail cards row -->
    <div class="info-grid-3">

      <!-- 1 — Personal Details -->
      <div class="info-card">
        <div class="info-section-header">
          <span class="info-badge">1</span>
          <span class="info-section-title">Personal Details</span>
        </div>
        <div class="info-field">
          <span class="info-label">First Name</span>
          <span class="info-value">{{ dataOrTire(data?.firstName) }}</span>
        </div>
        <div class="info-field">
          <span class="info-label">Middle Name</span>
          <span class="info-value">{{ dataOrTire(data?.middleName) }}</span>
        </div>
        <div class="info-field">
          <span class="info-label">Last Name</span>
          <span class="info-value">{{ dataOrTire(data?.lastName) }}</span>
        </div>
        <div class="info-field">
          <span class="info-label">Phone</span>
          <span class="info-value">{{ dataOrTire(data?.phone) }}</span>
        </div>
        <div class="info-field">
          <span class="info-label">Email</span>
          <span class="info-value">{{ dataOrTire(data?.email) }}</span>
        </div>
        <div class="info-field">
          <span class="info-label">DOB</span>
          <span class="info-value">{{ longToDate(dataOrTire(data?.dateOfBirth)) }}</span>
        </div>
        <div class="info-field">
          <span class="info-label">Address</span>
          <span class="info-value">{{ dataOrTire(data?.address) }}</span>
        </div>
      </div>

      <!-- 2 — Contract -->
      <div class="info-card">
        <div class="info-section-header">
          <span class="info-badge">2</span>
          <span class="info-section-title">Contract</span>
        </div>
        <div class="info-field">
          <span class="info-label">Driver Role</span>
          <span class="info-value">{{ dataOrTire(data?.type) }}</span>
        </div>
      </div>

      <!-- 3 — Operation Equipment -->
      <div class="info-card">
        <div class="info-section-header">
          <span class="info-badge">3</span>
          <span class="info-section-title">Operation Equipment</span>
        </div>
        <div class="info-field">
          <span class="info-label">Truck</span>
          <span v-if="data?.truckId" class="info-tag">{{ dataOrTire(data?.truckName) }}</span>
          <span v-else class="info-value">{{ dataOrTire(data?.truckName) }}</span>
        </div>
        <div class="info-field">
          <span class="info-label">Trailer</span>
          <span v-if="data?.trailerId" class="info-tag">{{ dataOrTire(data?.trailerName) }}</span>
          <span v-else class="info-value">{{ dataOrTire(data?.trailerName) }}</span>
        </div>
      </div>

    </div>
  </div>
</template>

<style scoped>
.driver-info {
  width: 100%;
  padding: 4px 4px;
}

.info-card {
  background: rgb(var(--v-theme-surface-variant));
  border: thin solid rgba(var(--v-border-color), var(--v-border-opacity));
  border-radius: 10px;
  padding: 14px 16px;
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.info-grid-2 {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 10px 24px;
}

.info-grid-3 {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 12px;
}

.info-section-header {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-bottom: 6px;
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

.info-field {
  display: flex;
  flex-direction: column;
  gap: 2px;
}

.info-label {
  font-size: 11px;
  font-weight: 500;
  text-transform: uppercase;
  letter-spacing: 0.04em;
  color: rgba(var(--v-theme-on-surface), var(--v-medium-emphasis-opacity));
}

.info-value {
  font-size: 13.5px;
  color: rgba(var(--v-theme-on-surface), var(--v-high-emphasis-opacity));
}

.info-tag {
  display: inline-block;
  background: rgba(var(--v-theme-primary), 0.12);
  color: rgb(var(--v-theme-primary));
  border-radius: 5px;
  padding: 1px 8px;
  font-size: 13px;
  font-weight: 500;
  width: fit-content;
}

@media (max-width: 768px) {
  .info-grid-2 {
    grid-template-columns: 1fr;
  }
  .info-grid-3 {
    grid-template-columns: 1fr;
  }
}
</style>
