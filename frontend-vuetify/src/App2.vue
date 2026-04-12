<template>
  <div class="d-flex vh-100">
    <!-- Sidebar -->
    <div
        class="bg-light sidebar d-flex flex-column align-items-center py-3"
        :style="{ width: sidebarWidth + 'px' }"
    >
      <!-- Collapse Button -->
      <v-btn variant="outlined" color="secondary" size="small" class="mb-3" @click="toggleSidebar">
        <v-icon :icon="collapsed ? 'mdi-menu' : 'mdi-close'" />
      </v-btn>

      <!-- Content or Icons only -->
      <div v-if="!collapsed" class="text-center px-2">
        <p>Menu 1</p>
        <p>Menu 2</p>
        <p>Menu 3</p>
      </div>
      <div v-else>
        <i class="bi bi-house mb-2"></i>
        <i class="bi bi-gear"></i>
      </div>
    </div>

    <!-- Resizer (faqat ochiq holatda ko‘rinadi) -->
    <div
        v-if="!collapsed"
        class="resizer"
        @mousedown="startResize"
    ></div>

    <!-- Main Content -->
    <div class="flex-fill bg-white p-4">
      <h5>Main Content</h5>
      <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';

const defaultWidth = 250;
const collapsedWidth = 60;

const sidebarWidth = ref(defaultWidth);
const collapsed = ref(false);

const toggleSidebar = () => {
  collapsed.value = !collapsed.value;
  sidebarWidth.value = collapsed.value ? collapsedWidth : defaultWidth;
};

// Resizing
let isResizing = false;

const startResize = (e) => {
  isResizing = true;
  document.addEventListener('mousemove', resizeSidebar);
  document.addEventListener('mouseup', stopResize);
};

const resizeSidebar = (e) => {
  if (isResizing && !collapsed.value) {
    sidebarWidth.value = Math.max(150, e.clientX);
  }
};

const stopResize = () => {
  isResizing = false;
  document.removeEventListener('mousemove', resizeSidebar);
  document.removeEventListener('mouseup', stopResize);
};
</script>

<style scoped>
.sidebar {
  transition: width 0.2s ease;
  min-width: 60px;
  max-width: 600px;
  overflow-x: hidden;
}

.resizer {
  width: 5px;
  cursor: ew-resize;
  background-color: #dee2e6;
}
</style>
