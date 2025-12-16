<script setup>
import { computed, watch } from 'vue'

const props = defineProps({
  tabs: {
    type: Array,
    required: true,
    default: []
  },
})

const activeTab = defineModel()

// Initialize activeTab with first tab if not set
if (!activeTab.value && props.tabs.length > 0) {
  activeTab.value = props.tabs[0].key
}

// Watch tabs changes and reset if needed
watch(() => props.tabs, (newTabs) => {
  if (newTabs.length > 0 && !newTabs.find(t => t.key === activeTab.value)) {
    activeTab.value = newTabs[0].key
  }
}, { deep: true })

// Get current active component
const activeComponent = computed(() => {
  return props.tabs.find(tab => tab.key === activeTab.value)?.component
})
// Get current active component props
const activeComponentProps = computed(() => {
  return props.tabs.find(tab => tab.key === activeTab.value)?.props
})
</script>

<template>
  <div class="tabs-container">
    <div class="tabs-wrapper">
      <button
        v-for="tab in tabs"
        :key="tab.key"
        @click="activeTab = tab.key"
        :class="['tab-button', { 'tab-active': activeTab === tab.key }]"
      >
        <span class="tab-label">{{ tab.name }}</span>
      </button>
    </div>
    <div class="tab-panels-container">
      <component 
        :is="activeComponent" 
         v-bind="activeComponentProps" 
        v-if="activeComponent"
      />
    </div>
  </div>
</template>

<style scoped>
.tabs-container {
  display: flex;
  flex-direction: column;
  width: 100%;
}

.tabs-wrapper {
  display: flex;
  gap: 0.25rem;
  position: relative;
  background-color: var(--bs-card-bg);
  border: 2px solid var(--bs-border-color, #e0e0e0);
  border-bottom: none;
  padding: 0.5rem;
  margin: 0;
  border-radius: 0.375rem 0.375rem 0 0;
}

.tab-button {
  position: relative;
  padding: 0.75rem 1.25rem;
  background-color: transparent;
  border: none;
  cursor: pointer;
  font-weight: 500;
  font-size: 0.95rem;
  color: var(--bs-body-color, #545a6d);
  transition: all 0.3s ease;
  white-space: nowrap;
  outline: none;
  border-radius: 0.375rem;
  margin-bottom: 0.5rem;
}

.tab-button:hover {
  color: var(--bs-primary, #385a8a);
  background-color: rgba(56, 90, 138, 0.06);
}

.tab-button.tab-active {
  color: var(--bs-primary, #385a8a);
  font-weight: 600;
  background-color: rgba(56, 90, 138, 0.1);
  border: none;
  border-bottom: 3px solid var(--bs-primary, #385a8a);
  margin-bottom: 0;
  padding-bottom: calc(0.75rem - 2px);
}

.tab-panels-container {
  background-color: var(--bs-card-bg);
  border: 2px solid var(--bs-border-color, #e0e0e0);
  /* border-top: 2px solid var(--bs-border-color, #e0e0e0); */
  border-radius: 0 0 0.5rem 0.5rem;
  /* padding: 1rem; */
  margin-top: -2px;
  color: var(--bs-body-color);
  min-height: 400px;
  width: 100%;
  display: block;
  background-color: var(--bs-card-bg);
}

@media (max-width: 576px) {
  .tab-button {
    padding: 0.75rem 1rem;
    font-size: 0.9rem;
  }

  .tab-label {
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    max-width: 100px;
  }
}

:deep(.tab-pane) {
  width: 100%;
  display: block;
}

:deep(.tab-pane.active) {
  animation: slideDownFade 0.6s ease-out;
}

@keyframes slideDownFade {
  from {
    opacity: 0;
    transform: translateY(-15px) scale(0.98);
  }
  to {
    opacity: 1;
    transform: translateY(0) scale(1);
  }
}
</style>
