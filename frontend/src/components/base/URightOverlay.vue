<template>
  <Teleport to="body">
    <Transition name="slide">
      <div v-if="isOpen" class="dialog-overlay">
        <div class="dialog-content">
          <button @click="close" class="close-btn">×</button>
          <slot></slot>
        </div>
      </div>
    </Transition>
  </Teleport>
</template>

<script setup>
import {defineProps, defineEmits} from "vue";

defineProps({isOpen: Boolean});
const emit = defineEmits(["close"]);

const close = () => emit("close");
</script>

<style scoped>
/* Затемнённый, блюрнутый, но некликабельный фон */
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

/* Диалог */
.dialog-content {
  width: 35%;
  height: 100%;
  background: white;
  padding: 20px;
  box-shadow: -5px 0 15px rgba(0, 0, 0, 0.3);
  position: relative;
  display: flex;
  flex-direction: column;
  pointer-events: auto; /* Включает клики ТОЛЬКО на диалог */
}

/* Кнопка закрытия */
.close-btn {
  position: absolute;
  top: 15px;
  right: 15px;
  font-size: 24px;
  border: none;
  background: none;
  cursor: pointer;
}

/* Анимация появления */
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
