<template>
  <div ref="dropdownRef" class="dropdown-side" @click.stop="toggleDropdown">
    <button
        type="button"
        class="btn"
        :class="`${btn_classes} ${open && 'show'}`"
        :aria-expanded="open"
    >
      <slot name="header">
        {{ label }} <i class="mdi mdi-chevron-right"></i>
      </slot>
    </button>

    <div
        class="dropdown-menu-side rounded-4"
        :class="`${open ? 'show' : ''}`"
        role="menu"
    >
      <slot name="body">
        <a class="dropdown-item" role="menuitem">Safety Compliance</a>
        <a class="dropdown-item" role="menuitem">DOT Inspections</a>
        <a class="dropdown-item" role="menuitem">Collisions & Claims</a>
      </slot>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue';

const props = defineProps({
  label: { type: String, default: 'Select' },
  hint: { type: String, default: 'Please select item' },
  option_name: { type: String, default: 'id' },
  option_value: { type: String, default: 'id' },
  classes: { type: String, default: '' },
  wrapper_styles: { type: String, default: '' },
  styles: { type: String, default: '' },
  position: {
    type: String,
    default: 'right',
    validator: (value) => ['left', 'right'].includes(value),
  },
});

const open = ref(false);
const dropdownRef = ref(null);

// Ekranda tashqariga bosilganda yopiladi
const handleClickOutside = (event) => {
  if (dropdownRef.value && !dropdownRef.value.contains(event.target)) {
    open.value = false;
  }
};

const toggleDropdown = () => {
  open.value = !open.value;
};

onMounted(() => {
  document.addEventListener('mousedown', handleClickOutside);
});

onUnmounted(() => {
  document.removeEventListener('mousedown', handleClickOutside);
});
</script>

<style scoped>
.dropdown-side {
  position: relative;
  display: inline-block;
}

.dropdown-menu-side {
  position: absolute;
  top: 5%;
  left: 90%;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  display: none;
  z-index: 1000;
}

.dropdown-menu-side.show {
  display: block;
}

.dropdown-item {
  padding: 10px 20px;
  text-decoration: none;
  display: block;
}

.dropdown-item:hover {
  background-color: #0d3b5e;
}
</style>