<script setup>
defineProps({
  show: Boolean
})
</script>
<template>
  <Transition name="modal">
    <div v-if="show" class="modal-mask">
      <div class="modal-container">
        <div class="modal-header py-3 border-bottom-0">
          <slot name="header">
            <h5 class="modal-title">Создание</h5>

            <button type="button" class="btn-close"></button>
          </slot>
        </div>

        <div class="modal-body">
          <slot name="body"></slot>
        </div>

        <div class="modal-footer">
          <slot name="footer">
            <div class="d-flex text-end align-items-end mt-2">
              <button type="submit" class="btn btn-success" @click="$emit('save')">Сахранить</button>
            </div>
          </slot>
        </div>
      </div>
    </div>
  </Transition>
</template>
<style>
.modal-mask {
  position: fixed;
  z-index: 99;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  transition: opacity 0.3s ease;
}

.modal-container {
  width: 550px;
  margin: auto;
  padding: 10px 20px;
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
