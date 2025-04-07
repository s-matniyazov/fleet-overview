<script setup>
import {onMounted, onUnmounted, ref, computed} from "vue";

const props = defineProps({
  label: {type: String, default: "Select"},
  hint: {type: String, default: "Please select item"},
  option_name: {type: String, default: "id"},
  option_value: {type: String, default: "id"},
  classes: {type: String, default: ""},
  btn_classes: {type: String, default: "btn-info waves-effect waves-light"},
  wrapper_styles: {type: String, default: ""},
  styles: {type: String, default: ""},
  placement: {
    type: String,
    default: "right",
    validator: (value) => ["left", "right"].includes(value)
  }
});

const open = ref(false);
const dropdownRef = ref(null);

// Compute dropdown class based on placement
const dropdownClass = computed(() => props.placement === "left" ? "dropdown-menu-end dropdown-menu-lg-start" : "dropdown-menu-lg-end");

const handleClickOutside = (event) => {
  if (dropdownRef.value && !dropdownRef.value.contains(event.target)) {
    open.value = false;
  }
};

// Attach event listener
onMounted(() => {
  document.addEventListener("mousedown", handleClickOutside);
});

onUnmounted(() => {
  document.removeEventListener("mousedown", handleClickOutside);
});
</script>

<template>
  <div ref="dropdownRef" class="dropdown" @click.stop="open = !open">
    <button type="button" class="btn dropdown-toggle" :class="`${btn_classes} ${open && 'show'}`" :aria-expanded="open">
      <slot name="header">
        Dropdown <i class="mdi mdi-chevron-right"></i>
      </slot>
    </button>
    <div class="dropdown-menu" :class="`${dropdownClass} ${open && 'show'}`" :style="wrapper_styles" role="menu">
      <slot name="body">
        <a class="dropdown-item" role="menuitem">Action</a>
        <a class="dropdown-item" role="menuitem">Another action</a>
        <a class="dropdown-item" role="menuitem">Something else here</a>
      </slot>
    </div>
  </div>
</template>
