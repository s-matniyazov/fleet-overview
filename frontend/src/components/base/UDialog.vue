<script setup>
defineProps({
  show: Boolean,
  width: {
    type: String,
    default: "550px"
  }
})
</script>
<template>
  <Transition name="modal">
    <div v-if="show" class="modal-mask text-white modal-overlay" >
      <div style="background: #06273d" class="modal-container rounded-4 shadow-dark" :style="`width: ${width}`">
        <div class="modal-header pb-3 border-bottom-0 text-primary" style="font-weight: 1000; font-size: 16px">
          <slot name="header">
            <h5 class="modal-title">Создание</h5>

            <button type="button" class="btn-close"></button>
          </slot>
        </div>

        <div class="modal-body">
          <slot name="body"></slot>
        </div>

      </div>
    </div>
  </Transition>
</template>
<style>/* Overlay */
.modal-mask {
  position: fixed;
  z-index: 1002;
  inset: 0;
  display: flex;
  justify-content: center;
  align-items: center;

  background: var(--modal-overlay-bg);
  backdrop-filter: blur(3px);

  transition: opacity 0.25s ease;
}

/* Theme colors */
[data-bs-theme="light"] {
  --modal-overlay-bg: rgba(0, 0, 0, 0.5);
}

[data-bs-theme="dark"] {
  --modal-overlay-bg: rgba(255, 255, 255, 0.25);
}

/* Modal window */
.modal-container {
  padding: 20px;
  border-radius: 6px;

  background: var(--modal-bg);
  box-shadow: 0 8px 30px rgba(0, 0, 0, 0.35);

  transition: transform 0.2s ease, opacity 0.2s ease;

  max-height: 80vh; /* max height for modal */
  display: flex;
  flex-direction: column;
  overflow-y: auto;  /* enable vertical scroll */
  padding-right: 15px; /* optional: avoid scrollbar overlap */
}

/* Theme modal background */
[data-bs-theme="light"] {
  --modal-bg: #ffffff;
}

[data-bs-theme="dark"] {
  --modal-bg: #000000;
}

/* Vue transition */
.modal-enter-from .modal-container,
.modal-leave-to .modal-container {
  transform: scale(0.9);
  opacity: 0;
}
</style>
