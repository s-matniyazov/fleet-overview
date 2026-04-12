<template>
  <Teleport to="body">
    <Transition name="u-ro">
      <div
        v-if="isOpen"
        class="u-resizable-overlay"
        role="presentation"
      >
        <div
          class="u-resizable-overlay__scrim"
          aria-hidden="true"
          @mousedown.self="close"
        />
        <aside
          class="u-resizable-overlay__panel"
          :style="{ width: currentWidth }"
          role="dialog"
          aria-modal="true"
          @click.stop
        >
          <div
            class="u-resizable-overlay__resize-handle"
            title="Drag to resize"
            @mousedown="startResize"
          />
          <div class="u-resizable-overlay__header">
            <slot name="header">
              <div class="u-resizable-overlay__header-inner u-resizable-overlay__header-default">
                <v-btn
                  icon
                  variant="text"
                  density="comfortable"
                  class="u-resizable-overlay__close"
                  aria-label="Close"
                  @click="close"
                >
                  <v-icon>mdi-close</v-icon>
                </v-btn>
              </div>
            </slot>
          </div>
          <div class="u-resizable-overlay__body">
            <slot name="body" />
          </div>
        </aside>
      </div>
    </Transition>
  </Teleport>
</template>

<script setup>
import {
  defineProps,
  defineEmits,
  ref,
  onMounted,
  onBeforeUnmount,
} from "vue";

const props = defineProps({
  isOpen: Boolean,
  width: {
    type: String,
    default: "35%",
  },
  minWidth: {
    type: String,
    default: "300px",
  },
  maxWidth: {
    type: String,
    default: "90%",
  },
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
  startWidth = parseInt(
    document.defaultView.getComputedStyle(e.target.parentElement).width,
    10,
  );
  document.addEventListener("mousemove", handleResize);
  document.addEventListener("mouseup", stopResize);
  e.preventDefault();
};

const handleResize = (e) => {
  if (!isResizing) return;

  let newWidth = startWidth + (startX - e.clientX);

  const minW = parseInt(props.minWidth) || 300;
  const maxW = parseInt(props.maxWidth) || window.innerWidth * 0.9;

  if (newWidth < minW) newWidth = minW;
  if (newWidth > maxW) newWidth = maxW;

  currentWidth.value = `${newWidth}px`;
  emit("resize", currentWidth.value);
};

const stopResize = () => {
  isResizing = false;
  document.removeEventListener("mousemove", handleResize);
  document.removeEventListener("mouseup", stopResize);
};

onMounted(() => {
  currentWidth.value = props.width;
});

onBeforeUnmount(() => {
  document.removeEventListener("mousemove", handleResize);
  document.removeEventListener("mouseup", stopResize);
});
</script>

<style scoped>
.u-resizable-overlay {
  position: fixed;
  inset: 0;
  z-index: 2004;
  display: flex;
  justify-content: flex-end;
  align-items: stretch;
  padding-top: env(safe-area-inset-top, 0px);
  padding-bottom: env(safe-area-inset-bottom, 0px);
}

.u-resizable-overlay__scrim {
  position: absolute;
  inset: 0;
  background: rgba(15, 23, 42, 0.58);
  backdrop-filter: blur(6px);
  -webkit-backdrop-filter: blur(6px);
}

.u-resizable-overlay__panel {
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

.u-resizable-overlay__resize-handle {
  position: absolute;
  left: 0;
  top: 0;
  bottom: 0;
  width: 10px;
  z-index: 2;
  cursor: col-resize;
  background: linear-gradient(
    90deg,
    rgba(var(--v-theme-primary), 0.12) 0%,
    transparent 100%
  );
  border-left: 2px solid rgba(var(--v-theme-primary), 0.35);
  transition: background 0.2s ease, border-color 0.2s ease;
}

.u-resizable-overlay__resize-handle:hover {
  background: linear-gradient(
    90deg,
    rgba(var(--v-theme-primary), 0.22) 0%,
    transparent 100%
  );
  border-left-color: rgba(var(--v-theme-primary), 0.55);
}

.u-resizable-overlay__header {
  flex: 0 0 auto;
  border-bottom: thin solid rgba(var(--v-border-color), var(--v-border-opacity));
  background: rgba(var(--v-theme-surface-variant), 0.35);
}

.u-resizable-overlay__header-inner {
  padding: 12px 16px 12px 24px;
}

.u-resizable-overlay__header-default {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  min-height: 40px;
}

.u-resizable-overlay__body {
  flex: 1 1 auto;
  min-height: 0;
  min-width: 0;
  overflow: auto;
  padding: 16px 16px 20px;
  -webkit-overflow-scrolling: touch;
}

.u-ro-enter-active .u-resizable-overlay__scrim,
.u-ro-leave-active .u-resizable-overlay__scrim {
  transition: opacity 0.28s ease;
}

.u-ro-enter-from .u-resizable-overlay__scrim,
.u-ro-leave-to .u-resizable-overlay__scrim {
  opacity: 0;
}

.u-ro-enter-active .u-resizable-overlay__panel,
.u-ro-leave-active .u-resizable-overlay__panel {
  transition: transform 0.32s cubic-bezier(0.32, 0.72, 0, 1);
}

.u-ro-enter-from .u-resizable-overlay__panel,
.u-ro-leave-to .u-resizable-overlay__panel {
  transform: translateX(100%);
}
</style>
