<script setup>
defineProps({
  show: Boolean,
  styles: {
    type: String,
    required: false
  },
  width: {
    type: String,
    required: false,
    default: '550px'
  }

})
</script>
<template>
  <Transition name="modal">
    <div v-if="show" class="modal-mask">
      <div class="modal-container" :style="`width: ${width}`">
        <div class="modal-header pb-3 border-bottom-0">
          <slot name="header">
            <h5 class="modal-title">Создание</h5>

            <button type="button" class="btn-close"></button>
          </slot>
        </div>

        <div class="modal-body" :style="styles">
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
  overflow-y:scroll;
  overflow-x:hidden;
}

.modal-container {
  margin: auto;
  padding: 20px 20px;
  background-color: #fff;
  border-radius: 2px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.33);
  transition: all 0.2s ease;
}

.modal-enter-from .modal-container,
.modal-leave-to .modal-container {
  transform: scale(0.7);
}
</style>
