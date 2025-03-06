<template>

  <button @click="successToast">Click me!</button>
</template>

<script setup lang="ts">
import useToasterStore from "@/store/ToastStore";

const toasterStore = useToasterStore();

const successToast = () => toasterStore.success({ text: "Yahoooooo!" });
</script>

<style scoped lang="scss"></style>
