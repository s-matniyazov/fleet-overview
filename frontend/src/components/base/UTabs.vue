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
  <ul class="nav nav-tabs">
    <li class="nav-item" v-for="tab in tabs" @click="activeTab = tab.key">
      <button class="nav-link btn-default mx-1" :class="activeTab === tab.key ? 'active' : 'bg-light'">
        <span class="d-none d-sm-block fw-bold"> {{ tab.name }} </span>
      </button>
    </li>
  </ul>
</template>

<style scoped>
.nav-tabs .nav-item.show .nav-link,
.nav-tabs .nav-link.active {
  color: #fff;
  background-color: #0891B2;
}

.nav-tabs {
  border: none;
}

.nav-tabs .nav-link {
  border: none;
  color: #385a8a;
}

.nav-tabs .nav-link:hover {
  color: gray;
}

.sidebar-tab-shadow {
  border: 1px solid #dde2e5;
  border-radius: 6px;
}
.sidebar-tab-shadow :nth-child(1) button {
  border-radius: 6px;
}
.sidebar-tab-shadow :nth-child(2) button {
  border-radius: 6px;
}
</style>
