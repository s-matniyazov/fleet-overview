<template>
  <div ref="dropdownRef" class="dropdown-side" @click.stop="toggleDropdown">
    <button
        type="button"
        class="btn"
        :class="`${open ? 'show' : ''}`"
        :aria-expanded="open"
    >
      <slot name="header">
        {{ label }} <i class="mdi mdi-chevron-right"></i>
      </slot>
    </button>

    <div
        class="dropdown-menu-side rounded-4"
        :class="`${open ? 'show' : ''}`"
        :style="positionStyle"
        role="menu"
    >
      <slot name="body"></slot>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted, computed } from 'vue';

const props = defineProps({
  label: { type: String, default: 'Select' },
  position: {
    type: String,
    default: 'right',
    validator: (value) => ['left', 'right'].includes(value),
  },
});

const open = ref(false);
const dropdownRef = ref(null);

const toggleDropdown = () => {
  open.value = !open.value;
};

const handleClickOutside = (event) => {
  if (dropdownRef.value && !dropdownRef.value.contains(event.target)) {
    open.value = false;
  }
};

onMounted(() => {
  document.addEventListener('mousedown', handleClickOutside);
});

onUnmounted(() => {
  document.removeEventListener('mousedown', handleClickOutside);
});

// Computed style for left or right dropdown
const positionStyle = computed(() => {
  return props.position === 'left'
      ? { right: '90%', left: 'auto', top: '0' }
      : { left: '90%', right: 'auto', top: '0' };
});
</script>

<style scoped>
.dropdown-side {
  position: relative;
  display: inline-block;
}

.dropdown-menu-side {
  position: absolute;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  display: none;
  z-index: 1000;
  width: auto;
}

.dropdown-menu-side.show {
  display: block;
}

.dropdown-item {
  padding: 10px 20px;
  text-decoration: none;
  display: block;
  cursor: pointer;
}

.dropdown-item:hover {
  background-color: #0d3b5e;
  color: white;
}
</style>
