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
  <div class="tab-content text-muted">
    <div v-for="tab in tabs" class="tab-pane" :class="activeTab === tab.key && 'active'">
      <component :is="tab.component" v-bind="tab.props" v-if="activeTab === tab.key"/>
    </div>
  </div>
</template>

<style scoped>

</style>
