<template>
  <Teleport to="body">
    <Transition name="u-ro">
      <div
        v-if="isOpen"
        class="u-right-overlay"
        role="presentation"
      >
        <div
          class="u-right-overlay__scrim"
          aria-hidden="true"
          @click="close"
        />
        <aside
          class="u-right-overlay__panel"
          :style="{ width }"
          role="dialog"
          aria-modal="true"
          @click.stop
        >
          <div class="u-right-overlay__header">
            <slot name="header">
              <div class="u-right-overlay__header-inner u-right-overlay__header-default">
                <v-btn
                  icon
                  variant="text"
                  density="comfortable"
                  class="u-right-overlay__close"
                  aria-label="Close"
                  @click="close"
                >
                  <v-icon>mdi-close</v-icon>
                </v-btn>
              </div>
            </slot>
          </div>
          <div class="u-right-overlay__body">
            <slot name="body" />
          </div>
        </aside>
      </div>
    </Transition>
  </Teleport>
</template>

<script setup>
import { defineProps, defineEmits } from "vue";

defineProps({
  isOpen: Boolean,
  width: {
    type: String,
    default: "35%",
  },
});
const emit = defineEmits(["close"]);

const close = () => emit("close");
</script>

<style scoped>
/* Root: enter/leave hooks target descendants (see .u-ro-* below) */
.u-right-overlay {
  position: fixed;
  inset: 0;
  z-index: 2004;
  display: flex;
  justify-content: flex-end;
  align-items: stretch;
  padding-top: env(safe-area-inset-top, 0px);
  padding-bottom: env(safe-area-inset-bottom, 0px);
}

.u-right-overlay__scrim {
  position: absolute;
  inset: 0;
  background: rgba(15, 23, 42, 0.58);
  backdrop-filter: blur(6px);
  -webkit-backdrop-filter: blur(6px);
}

.u-right-overlay__panel {
  position: relative;
  z-index: 1;
  display: flex;
  flex-direction: column;
  height: 100%;
  max-height: 100dvh;
  min-width: 0;
  min-height: 0;
  background: rgb(var(--v-theme-surface));
  color: rgb(var(--v-theme-on-surface));
  border-left: thin solid rgba(var(--v-border-color), var(--v-border-opacity));
  box-shadow:
    -12px 0 40px rgba(0, 0, 0, 0.18),
    -4px 0 12px rgba(0, 0, 0, 0.08);
}

.u-right-overlay__header {
  flex: 0 0 auto;
  border-bottom: thin solid rgba(var(--v-border-color), var(--v-border-opacity));
  background: rgba(var(--v-theme-surface-variant), 0.35);
}

.u-right-overlay__header-inner {
  padding: 12px 16px;
}

.u-right-overlay__header-default {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  min-height: 40px;
}

.u-right-overlay__close {
  flex-shrink: 0;
}

.u-right-overlay__body {
  flex: 1 1 auto;
  min-height: 0;
  min-width: 0;
  overflow: auto;
  padding: 16px 16px 20px;
  -webkit-overflow-scrolling: touch;
}

/* Vue transition: scrim fade + panel slide */
.u-ro-enter-active .u-right-overlay__scrim,
.u-ro-leave-active .u-right-overlay__scrim {
  transition: opacity 0.28s ease;
}

.u-ro-enter-from .u-right-overlay__scrim,
.u-ro-leave-to .u-right-overlay__scrim {
  opacity: 0;
}

.u-ro-enter-active .u-right-overlay__panel,
.u-ro-leave-active .u-right-overlay__panel {
  transition: transform 0.32s cubic-bezier(0.32, 0.72, 0, 1);
}

.u-ro-enter-from .u-right-overlay__panel,
.u-ro-leave-to .u-right-overlay__panel {
  transform: translateX(100%);
}
</style>
