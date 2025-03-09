<script setup>
import {onMounted, onUnmounted, ref} from "vue";

defineProps({
  label: {
    type: String,
    required: false,
    default: 'Select'
  },
  hint: {
    type: String,
    required: false,
    default: 'Please select item'
  },
  option_name: {
    type: String,
    required: false,
    default: 'id'
  },
  option_value: {
    type: String,
    required: false,
    default: 'id'
  },
  classes: {
    type: String,
    required: false,
    default: ''
  },
  btn_classes: {
    type: String,
    required: false,
    default: 'btn-info waves-effect waves-light'
  },
  wrapper_styles: {
    type: String,
    required: false,
    default: ''
  },
  styles: {
    type: String,
    required: false,
    default: ''
  },
})

const open = ref(false);

const dropdownRef = ref(null)

const handleClickOutside = (event) => {
  if (dropdownRef.value && !dropdownRef.value.contains(event.target)) {
    open.value = false
  }
}

// Attach and remove global event listeners
onMounted(() => {
  document.addEventListener('click', handleClickOutside)
})

onUnmounted(() => {
  document.removeEventListener('click', handleClickOutside)
})
</script>

<template>
  <div ref="dropdownRef" class="btn-group dropend" @click="e => {e.stopPropagation(); open = !open;}">
    <button type="button" class="btn dropdown-toggle" :class="btn_classes">
      <slot name="header">
        Drop end <i class="mdi mdi-chevron-right"></i>
      </slot>
    </button>
    <div class="dropdown-menu" :class="open && 'show'" :style="wrapper_styles">
      <slot name="body">
        <a class="dropdown-item">Action</a>
        <a class="dropdown-item">Another action</a>
        <a class="dropdown-item">Something else here</a>
      </slot>
    </div>
  </div>
</template>

<style scoped>
</style>
