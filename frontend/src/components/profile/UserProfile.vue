<script setup>
import { ref } from 'vue';
import BaseForm from '@/components/base/BaseForm.vue';
import BaseInput from '@/components/base/BaseInput.vue';

const username = ref('');
const password = ref('');
const formRef = ref(null);

const submitForm = () => {
  console.log('Form submitted:', { username: username.value, password: password.value });
};

const requiredRule = (val) => (!val ? 'This field is required' : '');
</script>

<template>
  <BaseForm ref="formRef" @submit="submitForm">
    <BaseInput v-model="username" name="username" label="Username" :rules="requiredRule" />
    <BaseInput v-model="password" name="password" label="Password" :rules="requiredRule" />
    <button type="submit">Submit</button>
  </BaseForm>
</template>
