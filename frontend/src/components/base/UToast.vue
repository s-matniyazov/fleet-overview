<template>
  <Teleport to="body">
    <Transition name="toast">
      <div v-if="toastStore.toasts.length" class="toaster__wrapper">
        <TransitionGroup name="toast" tag="ul">
          <div v-for="toast in toastStore.toasts" class="alert alert-border-left alert-dismissible fade show"
               :class="toastClassMap[toast.status]" style="max-width: 400px; min-width: 400px">
            <i class="mdi me-3 align-middle" :class="toastIconMap[toast.status]"></i><strong>{{toast.status.toUpperCase()}}</strong> - {{toast.text}}
          </div>
        </TransitionGroup>
      </div>
    </Transition>
  </Teleport>
</template>

<script setup lang="ts">
import useToasterStore from "@/store/ToastStore";

const toastClassMap: Record<string, string> = {
  warning: "alert-warning",
  error: "alert-danger",
  success: "alert-success",
};

const toastIconMap: Record<string, string> = {
  warning: "mdi-alert-outline",
  error: "mdi-block-helper",
  success: "mdi-check-all",
};

const toastStore = useToasterStore();
</script>

<style scoped>
.toast-enter-from,
.toast-leave-to {
  transform: translateY(-100%);
  opacity: 0;
}

.toast-enter-active,
.toast-leave-active {
  transition: 0.25s ease all;
}

.toaster__wrapper {
  position: fixed;
  top: 3%;
  right: 38vw;
  z-index: 99999;
  display: flex;
  flex-direction: column;
  gap: 1rem;
}
</style>
