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
  <div class="d-flex justify-content-center align-items-center min-vh-100 main_with_gradient" >
    <div class="p-4 shadow-lg rounded-3 login" >
      <div class="text-center mb-4">
        <h2 class="mt-3 text-white">EFFICIENT MANAGEMENT</h2>
      </div>
      <form>
        <div class="mb-3">
          <UInput v-model="username" placeholder="Введите имя пользователя" label="Username" />
        </div>
        <div class="mb-3">
          <UInput v-model="password" placeholder="Введите пароль" label="Password" type="password" />
        </div>
        <div class="mb-3">
          <button style="background-color: #0891B2; "
              class="btn w-100 text-white"
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
.main_with_gradient{
  background: linear-gradient(135deg, #24334b, rgb(2, 6, 23), #0f172a, #24334b);
}
.login{
  width: 100%;max-width: 420px; background: rgba(30, 41, 59, 0.58);
  backdrop-filter: blur(6px);
  color: white;
  border: 1px solid rgba(255, 255, 255, 0.05);
}
</style>
