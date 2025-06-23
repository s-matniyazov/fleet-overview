<script setup>
import { ref } from "vue";
import axiosIns from "@/plugins/axios.js";
import { URIS } from "@/constants/UriConstants.js";
import { showMessage } from "@/util/utils.js";
import useUserStore from "@/store/UserStore";
import router from "@/router/index.js";
import { useAuthStore } from "@/store/UseAuthStore.js";
import UInput from "@/components/base/UInput.vue";

const userStore = useUserStore();
const username = ref("username");
const password = ref("password");

function onLogin() {
  axiosIns.post(URIS.LOGIN, { login: username.value, password: password.value })
      .then(res => {
        if (res.data.status === 200) {
          userStore.token = res.data.data;
          const authStore = useAuthStore();
          authStore.login(res.data.data);
          router.push("/company-layout");
        } else {
          showMessage("Please check your login or password");
        }
      })
      .catch(e => {
        showMessage(e);
      });
}
</script>

<template>
  <div class="d-flex justify-content-center align-items-center min-vh-100 bg-light rounded-4">
    <div class="p-4 shadow rounded bg-white" style="width: 100%; max-width: 420px">
      <div class="text-center mb-4">
        <img src="../assets/icons/em_logo.png" alt="EM Fleet Logo" style="height: 80px;" />
        <h2 class="mt-3">EFFICIENT MANAGEMENT FLEET</h2>
      </div>
      <div class="text-center mb-4">
        <h5>Welcome to EM Fleet!</h5>
        <p class="text-muted">Login to continue using EM Fleet..</p>
      </div>
      <form>
        <div class="mb-3">
          <UInput v-model="username" placeholder="Введите имя пользователя" label="Username" />
        </div>
        <div class="mb-3">
          <UInput v-model="password" placeholder="Введите пароль" label="Password" type="password" />
        </div>
        <div class="mb-3">
          <button
              class="btn btn-primary w-100"
              type="button"
              @click="e => { onLogin(); e.stopPropagation(); }"
          >
            Login
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<style scoped>
body {
  margin: 0;
  background-color: #4e749d;
}
</style>
