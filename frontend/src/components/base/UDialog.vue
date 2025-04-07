<script setup>
defineProps({
  show: Boolean
})
</script>
<template>
  <Transition name="modal">
    <div v-if="show" class="modal-mask">
      <div class="modal-container">
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
<style>
.modal-mask {
  position: fixed;
  z-index: 1002;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  transition: opacity 0.3s ease;
}

[data-bs-theme=dark] .modal-mask {
  position: fixed;
  z-index: 1002;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(255, 255, 255, 0.3);
  display: flex;
  transition: opacity 0.3s ease;
}

.modal-container {
  width: 550px;
  margin: auto;
  padding: 20px 20px;
  background-color: #fff;
  border-radius: 2px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.33);
  transition: all 0.2s ease;
}

[data-bs-theme=dark] .modal-container {
  width: 550px;
  margin: auto;
  padding: 20px 20px;
  background-color: rgb(0, 0, 0);
  border-radius: 2px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.33);
  transition: all 0.2s ease;
}

.modal-enter-from .modal-container,
.modal-leave-to .modal-container {
  transform: scale(0.7);
}
</style>
