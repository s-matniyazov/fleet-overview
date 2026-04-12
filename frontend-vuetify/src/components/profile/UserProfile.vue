<script setup>
import { ref } from 'vue';
import BaseInput from '@/components/base/BaseInput.vue';
import UForm from "@/components/base/UForm.vue";

const username = ref('');
const password = ref('');
const formRef = ref(null);

const submitForm = () => {
  console.log('Form submitted:', { username: username.value, password: password.value });
};

const requiredRule = (val) => (!val ? 'This field is required' : '');
</script>

<template>
  <UForm ref="formRef" @submit="submitForm">
    <BaseInput v-model="username" name="username" label="Username" :rules="requiredRule" />
    <BaseInput v-model="password" name="password" label="Password"/>
    <v-btn type="submit" color="primary" class="mt-2">Submit</v-btn>
  </UForm>
</template>
