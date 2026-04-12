<script setup>
import { ref } from "vue";
import axiosIns from "@/plugins/axios.js";
import { URIS } from "@/constants/UriConstants.js";
import { showMessage } from "@/util/utils.js";
import useUserStore from "@/store/UserStore";
import router from "@/router/index.js";
import { useAuthStore } from "@/store/UseAuthStore.js";

const userStore = useUserStore();
const username = ref("username");
const password = ref("password");

function onLogin() {
  axiosIns
    .post(URIS.LOGIN, { login: username.value, password: password.value })
    .then((res) => {
      if (res.data.status === 200) {
        userStore.token = res.data.data;
        const authStore = useAuthStore();
        authStore.login(res.data.data);
        router.push("/company-layout");
      } else {
        showMessage("Please check your login or password");
      }
    })
    .catch((e) => {
      showMessage(e);
    });
}
</script>

<template>
  <v-container fluid class="login-view fill-height pa-4">
    <v-row align="center" justify="center" class="fill-height">
      <v-col cols="12" sm="10" md="5" lg="4" xl="3">
        <v-card variant="outlined" class="login-card pa-2">
          <v-card-item class="pb-2">
            <v-card-title class="text-h5 text-center font-weight-medium pt-4">
              Efficient Management
            </v-card-title>
            <v-card-subtitle class="text-center text-medium-emphasis pb-2">
              Sign in to continue
            </v-card-subtitle>
          </v-card-item>

          <v-card-text>
            <v-text-field
              v-model="username"
              label="Username"
              autocomplete="username"
              class="mb-3"
            />
            <v-text-field
              v-model="password"
              label="Password"
              type="password"
              autocomplete="current-password"
              class="mb-2"
              @keyup.enter="onLogin"
            />
            <v-btn block color="primary" size="large" class="mt-2" @click="onLogin">
              Log in
            </v-btn>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<style scoped>
.login-view {
  background: linear-gradient(145deg, #0f172a 0%, #1a2744 45%, #0b1220 100%);
  min-height: 100vh;
}

.login-card {
  border-color: rgba(255, 255, 255, 0.12) !important;
  background: rgba(17, 28, 44, 0.92) !important;
  backdrop-filter: blur(10px);
}
</style>
