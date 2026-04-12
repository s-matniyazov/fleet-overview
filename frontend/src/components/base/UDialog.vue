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
    <div v-if="show" class="u-dialog-mask">
      <div
        class="u-dialog-container rounded-xl"
        :style="{ width }"
        role="dialog"
        aria-modal="true"
      >
        <div class="u-dialog-header">
          <slot name="header">
            <h5 class="u-dialog-title">Создание</h5>
            <v-btn type="button" icon variant="text" density="comfortable" aria-label="Close">
              <v-icon>mdi-close</v-icon>
            </v-btn>
          </slot>
        </div>

        <div class="u-dialog-body">
          <slot name="body"></slot>
        </div>
      </div>
    </div>
  </Transition>
</template>
<style>
.u-dialog-mask {
  position: fixed;
  z-index: 2006;
  inset: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 16px;
  padding-top: max(16px, env(safe-area-inset-top, 0px));
  padding-bottom: max(16px, env(safe-area-inset-bottom, 0px));
  background: rgba(15, 23, 42, 0.55);
  backdrop-filter: blur(8px);
  -webkit-backdrop-filter: blur(8px);
  transition: opacity 0.25s ease;
}

.u-dialog-container {
  max-width: calc(100vw - 32px);
  max-height: min(80vh, 100dvh - 32px);
  display: flex;
  flex-direction: column;
  overflow: hidden;
  padding: 0;
  /* Navy shell (matches nav drawer); forms keep existing text-white / light labels */
  background: linear-gradient(180deg, #06273d 0%, #071a2e 100%);
  color: #e2e8f0;
  border: 1px solid rgba(255, 255, 255, 0.08);
  box-shadow:
    0 24px 48px rgba(0, 0, 0, 0.35),
    0 8px 24px rgba(0, 0, 0, 0.25);
  transition: transform 0.22s cubic-bezier(0.32, 0.72, 0, 1), opacity 0.22s ease;
}

.u-dialog-header {
  flex-shrink: 0;
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 12px;
  padding: 16px 16px 12px;
  font-weight: 600;
  font-size: 1rem;
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
  background: rgba(0, 0, 0, 0.12);
}

.u-dialog-title {
  margin: 0;
  font-size: inherit;
  font-weight: inherit;
}

.u-dialog-body {
  flex: 1 1 auto;
  min-height: 0;
  overflow: auto;
  padding: 16px 20px 20px;
  -webkit-overflow-scrolling: touch;
}

.modal-enter-from .u-dialog-container,
.modal-leave-to .u-dialog-container {
  transform: scale(0.96) translateY(8px);
  opacity: 0;
}
</style>
