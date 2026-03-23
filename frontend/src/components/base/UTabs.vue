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
  gap: 32px; 
  position: relative;
  background-color: #ffffff; /* теперь фон виден */
  border: none;
  border-bottom: 1px solid var(--bs-border-color, #e5e7eb);
  padding: 0 16px;
  margin: 0;
  border-radius: 12px 12px 0 0; /* скругляем верх */
}

.tab-button {
  position: relative;
  padding: 14px 8px;
  background: transparent; /* кнопка прозрачная, виден фон tabs-wrapper */
  border: none;
  cursor: pointer;
  font-weight: 500;
  font-size: 15px;
  color: var(--bs-body-color, #6b7280);
  transition: all 0.3s ease;
  white-space: nowrap;
  outline: none;
}

.tab-button.tab-active {
  color: var(--bs-primary, #1d4ed8);
  font-weight: 600;
}

/* Active tab indicator */
.tab-button.tab-active::after {
  content: "";
  position: absolute;
  left: 0;
  bottom: -1px;
  width: 100%;
  height: 3px;
  background-color: var(--bs-primary, #1d4ed8);
  border-radius: 2px 2px 0 0;
}

/* 🔽 content panel */
.tab-panels-container {
  background-color: #ffffff; /* фон панели */
  border: 1px solid var(--bs-border-color, #e5e7eb);
  border-top: none;
  border-radius: 0 0 12px 12px; 
  margin-top: 0;
  padding: 16px;
  min-height: 400px;
  width: 100%;
}

/* responsive */
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

/* tab pane animation */
:deep(.tab-pane) {
  width: 100%;
  display: block;
}

:deep(.tab-pane.active) {
  animation: slideDownFade 0.5s ease-out;
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
