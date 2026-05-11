<script setup>
import VerticalMenu from "@/components/VerticalMenu.vue";
import { computed, onMounted, ref, watch } from "vue";
import { useTheme } from "vuetify";
import { pinia } from "@/pinia.js";
import { useRouterStore } from "@/store/RouterStore.js";
import { useStateStore } from "@/store/StateStore.js";
import { useFilterStore } from "@/store/FilterStore.js";

const routerStore = useRouterStore(pinia);
const stateStore = useStateStore(pinia);
const filterStore = useFilterStore(pinia);

const THEME_KEY = "fleet-theme";
const vuetifyTheme = useTheme();

// Sync theme from localStorage before first render
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

const companies = computed(() => stateStore.companies);

/** localStorage / Pinia string saqlashi mumkin; v-select `item-value` bilan qat'iy solishtiradi */
function parseCompanyId(raw) {
  if (raw === null || raw === undefined || raw === "") return null;
  const n = Number(raw);
  return Number.isFinite(n) ? n : null;
}

const companyId = ref(parseCompanyId(filterStore.globalFilter.companyId));

const NAV_RAIL_KEY = "fleet-nav-rail";

const collapsed = ref(false);

function readNavRailCollapsed() {
  try {
    const s = localStorage.getItem(NAV_RAIL_KEY);
    if (s === "true") return true;
    if (s === "false") return false;
  } catch {
    /* ignore */
  }
  return (
    typeof document !== "undefined" &&
    document.body.classList.contains("collapsed-sidebar")
  );
}

function applyNavRailToBody(value) {
  if (typeof document === "undefined") return;
  document.body.classList.toggle("collapsed-sidebar", value);
  try {
    localStorage.setItem(NAV_RAIL_KEY, value ? "true" : "false");
  } catch {
    /* ignore */
  }
}

const toggleSidebar = () => {
  collapsed.value = !collapsed.value;
  applyNavRailToBody(collapsed.value);
};

onMounted(() => {
  collapsed.value = readNavRailCollapsed();
  applyNavRailToBody(collapsed.value);
  void stateStore.init();
});

watch(companyId, (newVal) => {
  filterStore.setCompanyId(newVal);
  window.location.reload();
});
</script>

<template>
  <v-navigation-drawer
    permanent
    :rail="collapsed"
    rail-width="72"
    width="240"
    elevation="0"
    border="end"
    :class="['nav-drawer', isDark ? 'nav-drawer--dark' : 'nav-drawer--light']"
  >
    <VerticalMenu :collapsed="collapsed" />
  </v-navigation-drawer>

  <v-app-bar
    density="comfortable"
    height="56"
    elevation="0"
    border
    :class="['nav-app-bar', isDark ? 'nav-app-bar--dark' : 'nav-app-bar--light']"
  >
    <v-btn
      icon
      variant="text"
      density="comfortable"
      class="nav-collapse-btn"
      :aria-expanded="!collapsed"
      :aria-label="
        collapsed ? 'Expand navigation menu' : 'Collapse navigation menu'
      "
      :title="collapsed ? 'Expand menu' : 'Collapse menu'"
      @click="toggleSidebar"
    >
      <v-icon
        color="primary"
        :icon="
          collapsed
            ? 'mdi-page-layout-sidebar-left-expand'
            : 'mdi-page-layout-sidebar-left-collapse'
        "
        size="22"
      />
    </v-btn>

    <v-toolbar-title class="text-subtitle-1 text-md-h6 font-weight-bold text-truncate ms-1 flex-grow-1">
      {{ routerStore.currentRouterName }}
    </v-toolbar-title>

    <v-select
      v-model="companyId"
      :items="companies"
      item-title="name"
      item-value="id"
      label="Company"
      hide-details
      density="compact"
      variant="outlined"
      class="company-toolbar-select me-1 me-md-2"
      bg-color="surface"
    />

    <v-btn
      icon
      variant="text"
      density="comfortable"
      class="me-1"
      :title="isDark ? 'Switch to light theme' : 'Switch to dark theme'"
      @click="toggleTheme"
    >
      <v-icon
        :icon="isDark ? 'mdi-weather-sunny' : 'mdi-weather-night'"
        color="primary"
        size="22"
      />
    </v-btn>
  </v-app-bar>

  <v-main class="main-surface main-layout-main">
    <v-container fluid class="pa-3 pa-md-4 main-layout-container">
      <div class="page-shell">
        <router-view />
      </div>
    </v-container>
  </v-main>
</template>

<style scoped>
.nav-drawer--dark {
  background: linear-gradient(180deg, #06273d 0%, #071a2e 100%) !important;
  border-color: rgba(255, 255, 255, 0.08) !important;
}

.nav-drawer--light {
  background: #ffffff !important;
  border-color: rgba(0, 0, 0, 0.1) !important;
}

.nav-app-bar {
  background: rgb(var(--v-theme-surface)) !important;
}

.nav-app-bar--dark {
  border-color: rgba(255, 255, 255, 0.08) !important;
}

.nav-app-bar--light {
  border-color: rgba(0, 0, 0, 0.1) !important;
}

.main-surface {
  background: rgb(var(--v-theme-background));
}

.main-layout-main {
  display: flex;
  flex-direction: column;
  flex: 1 1 auto !important;
  min-height: 0;
  max-height: 100%;
  overflow: hidden;
}

.main-layout-container {
  flex: 1 1 auto;
  min-height: 0;
  display: flex;
  flex-direction: column;
  max-width: 100% !important;
  height: 100%;
}

.company-toolbar-select {
  flex: 0 1 auto;
  min-width: min(17.5rem, 72vw);
  max-width: 100%;
}

.nav-collapse-btn {
  flex-shrink: 0;
}

.page-shell {
  flex: 1 1 auto;
  min-height: 0;
  display: flex;
  flex-direction: column;
}
</style>
