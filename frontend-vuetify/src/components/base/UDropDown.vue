<script setup>
import { computed, useAttrs } from "vue";

defineOptions({ inheritAttrs: false });

const props = defineProps({
  label: { type: String, default: "Select" },
  /** Opens toward the left of the trigger (e.g. row ⋮) or toward the right (sidebar icons). */
  position: {
    type: String,
    default: "right",
    validator: (value) => ["left", "right"].includes(value),
  },
});

const attrs = useAttrs();

/** Vuetify overlay location: panel sits on this side of the activator. */
const menuLocation = computed(() =>
  props.position === "left" ? "left" : "right",
);
</script>

<template>
  <v-menu
    :location="menuLocation"
    transition="scale-transition"
    :offset="8"
    close-on-content-click
  >
    <template #activator="{ props: menuProps }">
      <v-btn
        v-bind="menuProps"
        variant="text"
        density="comfortable"
        class="udropdown-trigger text-none"
        :class="attrs.class"
        :style="attrs.style"
      >
        <slot name="header">
          <span class="d-inline-flex align-center">
            {{ label }}
            <v-icon icon="mdi-chevron-right" size="small" class="ms-1" />
          </span>
        </slot>
      </v-btn>
    </template>

    <v-sheet
      rounded="lg"
      elevation="8"
      border
      class="udropdown-sheet bg-surface"
    >
      <slot name="body" />
    </v-sheet>
  </v-menu>
</template>

<style scoped>
.udropdown-trigger {
  min-width: 0;
}

.udropdown-trigger :deep(svg) {
  display: block;
}

.udropdown-sheet {
  border-color: rgba(var(--v-border-color), var(--v-border-opacity));
}
</style>
