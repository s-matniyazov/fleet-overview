<template>
  <Teleport to="body">
    <Transition name="slide">
      <div v-if="isOpen" class="dialog-overlay" @mousedown.self="close">
        <div class="dialog-content" :style="{ width: currentWidth }">
          <div class="resize-handle" @mousedown="startResize"></div>
          <slot name="header">
            <button @click="close" class="close-btn">×</button>
          </slot>
          <slot name="body"></slot>
        </div>
      </div>
    </Transition>
  </Teleport>
</template>

<script setup>
import { defineProps, defineEmits, ref, onMounted, onBeforeUnmount } from "vue";

const props = defineProps({
  isOpen: Boolean,
  width: {
    type: String,
    default: "35%"
  },
  minWidth: {
    type: String,
    default: "300px"
  },
  maxWidth: {
    type: String,
    default: "90%"
  }
});

const emit = defineEmits(["close", "resize"]);

const currentWidth = ref(props.width);
let isResizing = false;
let startX = 0;
let startWidth = 0;

const close = () => emit("close");

const startResize = (e) => {
  isResizing = true;
  startX = e.clientX;
  startWidth = parseInt(document.defaultView.getComputedStyle(e.target.parentElement).width, 10);
  document.addEventListener('mousemove', handleResize);
  document.addEventListener('mouseup', stopResize);
  e.preventDefault();
};

const handleResize = (e) => {
  if (!isResizing) return;

  let newWidth = startWidth + (startX - e.clientX);

  // Apply min/max constraints
  const minWidth = parseInt(props.minWidth) || 300;
  const maxWidth = parseInt(props.maxWidth) || window.innerWidth * 0.9;

  if (newWidth < minWidth) newWidth = minWidth;
  if (newWidth > maxWidth) newWidth = maxWidth;

  currentWidth.value = `${newWidth}px`;
  emit('resize', currentWidth.value);
};

const stopResize = () => {
  isResizing = false;
  document.removeEventListener('mousemove', handleResize);
  document.removeEventListener('mouseup', stopResize);
};

onMounted(() => {
  currentWidth.value = props.width;
});

onBeforeUnmount(() => {
  document.removeEventListener('mousemove', handleResize);
  document.removeEventListener('mouseup', stopResize);
});
</script>

<style scoped>
.dialog-overlay {
  position: fixed;
  inset: 0;
  backdrop-filter: blur(1px);
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: flex-end;
  align-items: stretch;
  z-index: 9999;
}

.dialog-content {
  height: 100%;
  background: white;
  padding: 20px;
  box-shadow: -5px 0 15px rgba(0, 0, 0, 0.3);
  position: relative;
  display: flex;
  flex-direction: column;
  pointer-events: auto;
}

.resize-handle {
  position: absolute;
  left: 0;
  top: 0;
  bottom: 0;
  width: 8px;
  cursor: col-resize;
  background-color: rgba(0, 0, 0, 0.1);
  transition: background-color 0.2s;
}

.resize-handle:hover {
  background-color: rgba(0, 0, 0, 0.2);
}

.close-btn {
  position: absolute;
  top: 15px;
  right: 15px;
  font-size: 24px;
  border: none;
  background: none;
  cursor: pointer;
}

.slide-enter-active,
.slide-leave-active {
  transition: transform 0.3s ease, opacity 0.3s ease;
}

.slide-enter-from {
  transform: translateX(100%);
  opacity: 0;
}

.slide-leave-to {
  transform: translateX(100%);
  opacity: 0;
}
</style>