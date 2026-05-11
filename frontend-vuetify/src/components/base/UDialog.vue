<script setup>
import { computed, useSlots } from "vue";

defineProps({
  show: Boolean,
  width: {
    type: String,
    default: "550px",
  },
});

const emit = defineEmits(["close"]);

const slots = useSlots();
const hasActions = computed(() => !!slots.actions);

function onModelUpdate(open) {
  if (!open) emit("close");
}
</script>

<template>
  <v-dialog
    :model-value="show"
    class="u-dialog-v"
    :max-width="width"
    transition="dialog-transition"
    @update:model-value="onModelUpdate"
  >
    <v-card
      class="u-dialog-v-card d-flex flex-column rounded-xl"
      elevation="12"
    >
      <v-card-item class="u-dialog-v-header flex-shrink-0 py-3 px-4">
        <slot name="header">
          <span class="text-h6">Dialog</span>
        </slot>
      </v-card-item>

      <v-card-text class="u-dialog-v-body flex-grow-1 pa-0" style="min-height: 0">
        <div class="u-dialog-v-body-inner">
          <slot name="body" />
        </div>
      </v-card-text>

      <v-card-actions
        v-if="hasActions"
        class="u-dialog-v-footer flex-shrink-0 border-t px-4 py-3 d-flex flex-wrap justify-center ga-2"
      >
        <slot name="actions" />
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<style>
.u-dialog-v-card {
  max-height: min(88vh, 100dvh - 32px);
  background: rgb(var(--v-theme-surface)) !important;
  color: rgba(var(--v-theme-on-surface), var(--v-high-emphasis-opacity)) !important;
  border-color: rgba(var(--v-border-color), var(--v-border-opacity)) !important;
}

.u-dialog-v-header {
  border-bottom: 1px solid rgba(var(--v-border-color), var(--v-border-opacity));
  background: rgba(var(--v-theme-surface-variant), 0.6);
}

.u-dialog-v-body {
  overflow-y: auto;
  overflow-x: hidden;
  -webkit-overflow-scrolling: touch;
}

.u-dialog-v-body-inner {
  padding: 22px 24px 26px;
}

@media (max-width: 600px) {
  .u-dialog-v-body-inner {
    padding: 16px 16px 20px;
  }
}

.u-dialog-v-footer {
  border-top: 1px solid rgba(var(--v-border-color), var(--v-border-opacity)) !important;
  background: rgba(var(--v-theme-surface-variant), 0.4);
}
</style>
