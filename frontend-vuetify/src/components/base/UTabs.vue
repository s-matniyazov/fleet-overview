<script setup>
import { computed, watch } from "vue";

const props = defineProps({
  tabs: {
    type: Array,
    required: true,
    default: () => [],
  },
});

const activeTab = defineModel();

if (!activeTab.value && props.tabs.length > 0) {
  activeTab.value = props.tabs[0].key;
}

watch(
  () => props.tabs,
  (newTabs) => {
    if (newTabs.length > 0 && !newTabs.find((t) => t.key === activeTab.value)) {
      activeTab.value = newTabs[0].key;
    }
  },
  { deep: true },
);

const activeComponent = computed(() => {
  return props.tabs.find((tab) => tab.key === activeTab.value)?.component;
});

const activeComponentProps = computed(() => {
  return props.tabs.find((tab) => tab.key === activeTab.value)?.props;
});
</script>

<template>
  <div class="ut-tabs">
    <div class="ut-tabs__list" role="tablist">
      <button
        v-for="tab in tabs"
        :key="tab.key"
        type="button"
        role="tab"
        :aria-selected="activeTab === tab.key"
        class="ut-tabs__trigger"
        :class="{ 'ut-tabs__trigger--active': activeTab === tab.key }"
        @click="activeTab = tab.key"
      >
        <span class="ut-tabs__label">{{ tab.name }}</span>
      </button>
    </div>
    <div class="ut-tabs__panel">
      <component
        :is="activeComponent"
        v-if="activeComponent"
        v-bind="activeComponentProps"
      />
    </div>
  </div>
</template>

<style scoped>
.ut-tabs {
  display: flex;
  flex-direction: column;
  flex: 1 1 auto;
  min-height: 0;
  width: 100%;
  min-width: 0;
  border-radius: 12px;
  overflow: hidden;
  border: thin solid rgba(var(--v-border-color), var(--v-border-opacity));
  background: rgb(var(--v-theme-surface));
  color: rgba(var(--v-theme-on-surface), var(--v-high-emphasis-opacity));
}

.ut-tabs__list {
  display: flex;
  flex-wrap: wrap;
  align-items: flex-end;
  gap: 2px 4px;
  padding: 8px 10px 0;
  border-bottom: thin solid rgba(var(--v-border-color), var(--v-border-opacity));
  background: rgba(var(--v-theme-surface-variant), 0.45);
}

.ut-tabs__trigger {
  position: relative;
  margin: 0;
  padding: 12px 14px;
  background: transparent;
  border: none;
  border-radius: 8px 8px 0 0;
  cursor: pointer;
  font-weight: 500;
  font-size: 0.875rem;
  letter-spacing: 0.02em;
  color: rgba(var(--v-theme-on-surface), 0.62);
  transition:
    color 0.2s ease,
    background 0.2s ease;
  white-space: nowrap;
  outline: none;
}

.ut-tabs__trigger:hover {
  color: rgba(var(--v-theme-on-surface), 0.92);
  background: rgba(var(--v-theme-on-surface), 0.06);
}

.ut-tabs__trigger:focus-visible {
  box-shadow: inset 0 0 0 2px rgba(var(--v-theme-primary), 0.45);
}

.ut-tabs__trigger--active {
  color: rgb(var(--v-theme-primary));
  font-weight: 600;
  background: rgba(var(--v-theme-surface), 0.95);
}

.ut-tabs__trigger--active:hover {
  color: rgb(var(--v-theme-primary));
  background: rgba(var(--v-theme-surface), 0.95);
}

.ut-tabs__trigger--active::after {
  content: "";
  position: absolute;
  left: 10px;
  right: 10px;
  bottom: 0;
  height: 3px;
  border-radius: 3px 3px 0 0;
  background: rgb(var(--v-theme-primary));
  box-shadow: 0 0 12px rgba(var(--v-theme-primary), 0.35);
}

.ut-tabs__label {
  display: inline-block;
  max-width: 18rem;
  overflow: hidden;
  text-overflow: ellipsis;
}

.ut-tabs__panel {
  padding: 16px;
  flex: 1 1 auto;
  min-height: 0;
  overflow: auto;
  width: 100%;
  min-width: 0;
  display: flex;
  flex-direction: column;
  background: rgb(var(--v-theme-surface));
}

.ut-tabs__panel > * {
  flex: 1 1 auto;
  min-height: 0;
  min-width: 0;
}

@media (max-width: 576px) {
  .ut-tabs__trigger {
    padding: 10px 12px;
    font-size: 0.8125rem;
  }

  .ut-tabs__label {
    max-width: 8rem;
  }
}

:deep(.tab-pane) {
  width: 100%;
  display: block;
}

:deep(.tab-pane.active) {
  animation: ut-tabs-fade 0.35s ease-out;
}

@keyframes ut-tabs-fade {
  from {
    opacity: 0;
    transform: translateY(-8px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>
