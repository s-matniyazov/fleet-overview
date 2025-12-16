<script setup>
defineProps({
  tabs: {
    type: Array,
    required: true,
    default: []
  },
})
const activeTab = defineModel()
</script>

<template>
  <div class="tab-panels-container">
    <div v-for="tab in tabs" :key="tab.key" class="tab-pane" :class="{ 'active': activeTab === tab.key }">
      <component :is="tab.component" v-bind="tab.props" v-if="activeTab === tab.key" />
    </div>
  </div>
</template>

<style scoped>
.tab-panels-container {
  background-color: var(--bs-card-bg);
  border: 2px solid var(--bs-border-color, #e0e0e0);
    border-top: 2px solid var(--bs-border-color, #e0e0e0);
      border-radius: 0 0 0.5rem 0.5rem;
        padding: 1.5rem;
        margin-top: -2px;
        color: var(--bs-body-color);

}
.tab-pane {
  display: none;
  color: var(--bs-body-color);
}

.tab-pane.active {
  display: block;
  animation: fadeIn 0.3s ease;
}

@keyframes fadeIn {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}
</style>
