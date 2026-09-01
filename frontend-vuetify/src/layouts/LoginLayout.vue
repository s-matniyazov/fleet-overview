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

const formBg = computed(() =>
  isDark.value ? "#0d1420" : "#ffffff",
);
const formFieldBg = computed(() =>
  isDark.value ? "rgba(255,255,255,0.06)" : "#f1f5f9",
);
const formFieldBorder = computed(() =>
  isDark.value ? "rgba(255,255,255,0.1)" : "rgba(0,0,0,0.1)",
);
const formTextColor = computed(() =>
  isDark.value ? "#f8fafc" : "#0f172a",
);
const formMutedColor = computed(() =>
  isDark.value ? "#8291a8" : "#64748b",
);

const email = ref("");
const password = ref("");
const showPassword = ref(false);
const remember = ref(true);
const currentYear = new Date().getFullYear();

async function onLogin() {
  try {
    const res = await axiosIns.post(URIS.LOGIN, {
      email: email.value,
      password: password.value,
    });

    const { accessToken, refreshToken } = res.data.data;

    userStore.token = accessToken;
    userStore.refreshToken = refreshToken;
    authStore.login(accessToken, refreshToken);

    await stateStore.loadFromApi();
    await router.push("/company-layout");
  } catch (e) {
    showMessage(e);
  }
}

const features = [
  { icon: "mdi-truck-outline", label: "Fleet Dispatch" },
  { icon: "mdi-cube-outline", label: "Load & Freight" },
  { icon: "mdi-map-marker-path", label: "Route Tracking" },
];

const checklist = ["Track Operations", "Improve Productivity", "Make Better Decisions"];
</script>

<template>
  <div class="login-view">
    <div class="split-wrap">
      <div class="brand-panel">
        <div class="brand-glow" />

        <div class="brand-content">
          <div class="brand-header">
            <div class="brand-badge">EM</div>
            <div>
              <div class="brand-title">Efficient Management</div>
              <div class="brand-subtitle">Logistics CRM</div>
            </div>
          </div>

          <div class="brand-body">
            <div class="eyebrow">Welcome to EfficientManagement</div>
            <h1 class="brand-heading">Efficient<br>Management</h1>
            <div class="heading-underline" />

            <p class="brand-tagline">Manage smarter. Work faster. Grow stronger.</p>
            <p class="brand-desc">
              Everything you need to manage your business efficiently — in one place.
            </p>

            <ul class="brand-checklist">
              <li v-for="item in checklist" :key="item">
                <span class="check-badge"><v-icon icon="mdi-check" size="12" /></span>
                {{ item }}
              </li>
            </ul>

            <div class="brand-divider" />

            <p class="brand-closing">
              Your business, your data, your workflow — <strong>efficiently managed.</strong>
            </p>
          </div>

          <div class="feature-row">
            <div v-for="(f, i) in features" :key="f.label" class="feature-item" :class="{ bordered: i > 0 }">
              <v-icon :icon="f.icon" size="20" class="feature-icon" />
              <span>{{ f.label }}</span>
            </div>
          </div>
        </div>
      </div>

      <div class="form-panel">
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

        <div class="form-wrap">
          <h2 class="form-title">Sign in to continue</h2>
          <p class="form-subtitle">Use your company account.</p>

          <label class="field-label">Email</label>
          <v-text-field
            v-model="email"
            placeholder="you@company.com"
            variant="solo"
            flat
            class="field-input mb-4"
            @keyup.enter="onLogin"
          />

          <label class="field-label">Password</label>
          <v-text-field
            v-model="password"
            :type="showPassword ? 'text' : 'password'"
            placeholder="••••••••"
            variant="solo"
            flat
            class="field-input mb-2"
            @keyup.enter="onLogin"
          >
            <template #append-inner>
              <span class="show-toggle" @click="showPassword = !showPassword">
                {{ showPassword ? "HIDE" : "SHOW" }}
              </span>
            </template>
          </v-text-field>

          <div class="form-row-between">
            <v-checkbox v-model="remember" label="Keep me signed in" density="compact" />
            <a class="forgot-link" @click.prevent>Forgot password?</a>
          </div>

          <v-btn block color="primary" size="large" class="login-btn" @click="onLogin">
            Log in
          </v-btn>
        </div>

        <div class="form-footer">
          © {{ currentYear }} Efficient Management · Terms · Privacy
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.login-view {
  min-height: 100vh;
  width: 100%;
}

.split-wrap {
  display: flex;
  min-height: 100vh;
}

/* ---------- Brand panel ---------- */
.brand-panel {
  flex: 1 1 60%;
  position: relative;
  overflow: hidden;
  background: linear-gradient(160deg, #0b1220 0%, #101a2c 55%, #0b1220 100%);
  display: flex;
  align-items: center;
  padding: 48px 56px;
}

.brand-glow {
  position: absolute;
  inset: 0;
  background: radial-gradient(circle at 85% 90%, rgba(var(--v-theme-primary), 0.22) 0%, transparent 55%);
  pointer-events: none;
}

.brand-content {
  position: relative;
  z-index: 1;
  width: 100%;
  max-width: 640px;
  display: flex;
  flex-direction: column;
  gap: 40px;
}

.brand-header {
  display: flex;
  align-items: center;
  gap: 14px;
}

.brand-badge {
  width: 44px;
  height: 44px;
  border-radius: 10px;
  background: rgb(var(--v-theme-primary));
  color: #fff;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: 700;
  font-size: 15px;
  flex-shrink: 0;
}

.brand-title {
  color: #f8fafc;
  font-weight: 600;
  font-size: 16px;
  line-height: 1.3;
}

.brand-subtitle {
  color: #7c8aa0;
  font-size: 13px;
}

.eyebrow {
  color: rgb(var(--v-theme-primary));
  font-size: 11px;
  font-weight: 600;
  letter-spacing: 0.18em;
  text-transform: uppercase;
  margin-bottom: 14px;
}

.brand-heading {
  color: #f8fafc;
  font-size: clamp(30px, 4vw, 42px);
  font-weight: 800;
  line-height: 1.1;
  margin: 0;
}

.heading-underline {
  width: 40px;
  height: 3px;
  border-radius: 2px;
  background: rgb(var(--v-theme-primary));
  margin-top: 16px;
}

.brand-tagline {
  color: #94a3b8;
  font-size: 16px;
  margin: 20px 0 0;
}

.brand-desc {
  color: #f1f5f9;
  font-weight: 600;
  font-size: 14px;
  margin: 12px 0 0;
  max-width: 460px;
}

.brand-checklist {
  list-style: none;
  margin: 20px 0 0;
  padding: 0;
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.brand-checklist li {
  display: flex;
  align-items: center;
  gap: 10px;
  color: #e2e8f0;
  font-size: 14px;
  font-weight: 500;
}

.check-badge {
  width: 22px;
  height: 22px;
  border-radius: 50%;
  background: rgba(var(--v-theme-primary), 0.22);
  color: rgb(var(--v-theme-primary));
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
}

.brand-divider {
  height: 1px;
  width: 100%;
  max-width: 420px;
  background: rgba(255, 255, 255, 0.12);
  margin: 22px 0 0;
}

.brand-closing {
  color: #94a3b8;
  font-size: 14px;
  margin: 16px 0 0;
}

.brand-closing strong {
  color: #f8fafc;
}

.feature-row {
  display: flex;
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 12px;
  overflow: hidden;
  width: fit-content;
}

.feature-item {
  display: flex;
  flex-direction: column;
  gap: 10px;
  padding: 16px 22px;
}

.feature-item.bordered {
  border-left: 1px solid rgba(255, 255, 255, 0.1);
}

.feature-icon {
  color: rgb(var(--v-theme-primary));
}

.feature-item span {
  color: #94a3b8;
  font-size: 11px;
  font-weight: 600;
  letter-spacing: 0.08em;
  text-transform: uppercase;
  white-space: nowrap;
}

/* ---------- Form panel ---------- */
.form-panel {
  flex: 1 1 40%;
  position: relative;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 48px 32px;
  background: v-bind(formBg);
}

.theme-toggle {
  position: absolute;
  top: 20px;
  right: 20px;
}

.form-wrap {
  width: 100%;
  max-width: 340px;
}

.form-title {
  color: v-bind(formTextColor);
  font-size: 24px;
  font-weight: 700;
  margin: 0;
}

.form-subtitle {
  color: v-bind(formMutedColor);
  font-size: 14px;
  margin: 8px 0 28px;
}

.field-label {
  display: block;
  color: v-bind(formMutedColor);
  font-size: 11px;
  font-weight: 600;
  letter-spacing: 0.08em;
  text-transform: uppercase;
  margin-bottom: 6px;
}

.field-input :deep(.v-field) {
  background: v-bind(formFieldBg) !important;
  border: 1px solid v-bind(formFieldBorder);
  border-radius: 8px;
  box-shadow: none;
}

.field-input :deep(input) {
  color: v-bind(formTextColor);
}

.show-toggle {
  color: rgb(var(--v-theme-primary));
  font-size: 11px;
  font-weight: 700;
  letter-spacing: 0.06em;
  cursor: pointer;
  user-select: none;
}

.form-row-between {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin: 4px 0 20px;
}

.forgot-link {
  color: rgb(var(--v-theme-primary));
  font-size: 13px;
  font-weight: 500;
  text-decoration: none;
  cursor: pointer;
}

.forgot-link:hover {
  text-decoration: underline;
}

.login-btn {
  font-weight: 700;
  letter-spacing: 0.04em;
}

.form-footer {
  position: absolute;
  bottom: 20px;
  color: v-bind(formMutedColor);
  font-size: 12px;
  text-align: center;
}

@media (max-width: 900px) {
  .split-wrap {
    flex-direction: column;
  }

  .brand-panel {
    padding: 36px 28px;
  }

  .feature-row {
    width: 100%;
  }

  .feature-item {
    flex: 1;
    padding: 14px;
  }

  .form-panel {
    padding: 40px 24px 72px;
  }
}
</style>
