<script setup>
import { computed, ref } from "vue";
import { useTheme } from "vuetify";
import axiosIns from "@/plugins/axios.js";
import { URIS } from "@/constants/UriConstants.js";
import { showMessage } from "@/util/utils.js";
import useUserStore from "@/store/UserStore";
import router from "@/router/index.js";
import { useAuthStore } from "@/store/UseAuthStore.js";
import { useStateStore } from "@/store/StateStore.js";
import { pinia } from "@/pinia.js";

const userStore = useUserStore(pinia);
const authStore = useAuthStore(pinia);
const stateStore = useStateStore(pinia);

const THEME_KEY = "fleet-theme";
const vuetifyTheme = useTheme();

try {
  const saved = localStorage.getItem(THEME_KEY);
  if (saved === "fleetDark" || saved === "fleetLight") {
    vuetifyTheme.global.name.value = saved;
  }
} catch { /* ignore */ }

const isDark = computed(() => vuetifyTheme.current.value.dark);

function toggleTheme() {
  const next = isDark.value ? "fleetLight" : "fleetDark";
  vuetifyTheme.global.name.value = next;
  try { localStorage.setItem(THEME_KEY, next); } catch { /* ignore */ }
}

const viewBg = computed(() =>
  isDark.value
    ? "linear-gradient(145deg, #0f172a 0%, #1a2744 45%, #0b1220 100%)"
    : "rgb(var(--v-theme-background))",
);
const cardBorder = computed(() =>
  isDark.value ? "rgba(255,255,255,0.12)" : "rgba(0,0,0,0.1)",
);
const cardBg = computed(() =>
  isDark.value ? "rgba(17,28,44,0.92)" : "#ffffff",
);

const username = ref("username");
const password = ref("password");

async function onLogin() {
  try {
    const res = await axiosIns.post(URIS.LOGIN, {
      login: username.value,
      password: password.value,
    });
    if (res.data.status === 200) {
      userStore.token = res.data.data;
      authStore.login(res.data.data);
      await stateStore.loadFromApi();
      router.push("/company-layout");
    } else {
      showMessage("Please check your login or password");
    }
  } catch (e) {
    showMessage(e);
  }
}
</script>

<template>
  <v-container fluid class="login-view fill-height pa-4">
    <v-btn
      icon
      variant="text"
      size="small"
      class="theme-toggle"
      :title="isDark ? 'Switch to light theme' : 'Switch to dark theme'"
      @click="toggleTheme"
    >
      <v-icon
        :icon="isDark ? 'mdi-weather-sunny' : 'mdi-weather-night'"
        color="primary"
        size="20"
      />
    </v-btn>

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
  background: v-bind(viewBg);
  min-height: 100vh;
  position: relative;
}

.login-card {
  border-color: v-bind(cardBorder) !important;
  background: v-bind(cardBg) !important;
  backdrop-filter: blur(10px);
}

.theme-toggle {
  position: absolute;
  top: 16px;
  right: 16px;
}
</style>
