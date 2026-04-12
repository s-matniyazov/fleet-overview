<script setup>
import { ref } from 'vue';

const props = defineProps({
  position: {
    type: String,
    default: 'top',
    validator: (value) => ['top', 'right', 'bottom', 'left'].includes(value),
  }
});

const tooltipVisible = ref(false);
</script>

<template>
  <div class="tooltip-wrapper"
       @mouseenter="tooltipVisible = true"
       @mouseleave="tooltipVisible = false">

    <slot></slot>

    <div v-if="tooltipVisible" class="tooltip bg-primary-subtle text-primary" :class="position">
      <slot name="content"></slot>
    </div>
  </div>
</template>

<style scoped>
.tooltip-wrapper {
  position: relative;
  display: inline-block;
}

/* Base tooltip style */
.tooltip {
  position: absolute;
  background: black;
  color: white;
  padding: 6px 10px;
  border-radius: 5px;
  font-size: 13px;
  white-space: nowrap;
  opacity: 0;
  visibility: hidden;
  transition: opacity 0.2s ease-in-out, visibility 0.2s;
}

/* Show tooltip on hover */
.tooltip-wrapper:hover .tooltip {
  opacity: 1;
  visibility: visible;
}

/* Positioning */
.tooltip.top {
  bottom: 100%;
  left: 50%;
  transform: translateX(-50%);
  margin-bottom: 6px;
}

.tooltip.right {
  left: 100%;
  top: 50%;
  transform: translateY(-50%);
  margin-left: 6px;
}

.tooltip.bottom {
  top: 100%;
  left: 50%;
  transform: translateX(-50%);
  margin-top: 6px;
}

.tooltip.left {
  right: 100%;
  top: 50%;
  transform: translateY(-50%);
  margin-right: 6px;
}
</style>
