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
  <ul class="nav justify-content-end nav-tabs-custom rounded card-header-tabs">
    <li class="nav-item" v-for="tab in tabs" @click="activeTab = tab.key">
      <button class="nav-link btn-default" :class="activeTab === tab.key && 'active'">
        <span class="d-block d-sm-none"><i class="fas fa-home"></i></span>
        <span class="d-none d-sm-block">{{ tab.name }}</span>
      </button>
    </li>
  </ul>
</template>

<style scoped>

</style>
