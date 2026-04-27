<script setup>
import VerticalMenu from "@/components/VerticalMenu.vue";
import { onMounted, ref, watch } from "vue";
import { useRouterStore } from "@/store/RouterStore.js";
import axiosIns from "@/plugins/axios.js";
import { showMessage,filterString } from "@/util/utils.js";
import { URIS } from "@/constants/UriConstants.js";
import { useFilterStore } from "@/store/FilterStore.js";

const routerStore = useRouterStore();
const filterStore = useFilterStore();

const companies = ref();
const companyId = ref(filterStore.globalFilter.companyId);

const collapsed = ref(
  typeof document !== "undefined" &&
    document.body.classList.contains("collapsed-sidebar")
);

const toggleSidebar = () => {
  collapsed.value = !collapsed.value;
  document.body.classList.toggle("collapsed-sidebar", collapsed.value);
};

function getCompanies() {
  axiosIns
    .get(
    `${URIS.COMPANIES}${filterString({
      userId: localStorage.getItem("userId")
    })}`
  )
    .then((res) => {
      companies.value = res.data.data;
    })
    .catch((e) => {
      showMessage(e);
    });
}

onMounted(() => {
  getCompanies();
});

watch(
  companyId,
  (newVal) => {
    filterStore.setCompanyId(newVal);
    window.location.reload();
  },
  { deep: true }
);
</script>

<template>
  <v-navigation-drawer
    permanent
    :rail="collapsed"
    rail-width="72"
    width="240"
    elevation="0"
    border="end"
    class="nav-drawer text-white"
  >
    <VerticalMenu :collapsed="collapsed" />
  </v-navigation-drawer>

  <v-app-bar
    density="comfortable"
    height="56"
    elevation="0"
    border
    class="nav-app-bar"
  >
    <v-btn
      icon
      variant="text"
      density="comfortable"
      aria-label="Toggle sidebar"
      @click="toggleSidebar"
    >
      <v-img src="@/assets/icons/resize.png" width="20" height="20" alt="" cover />
    </v-btn>

    <v-toolbar-title class="text-subtitle-1 text-md-h6 font-weight-bold text-truncate ms-1 flex-grow-1">
      {{ routerStore.currentRouterName }}
    </v-toolbar-title>

    <v-select
      v-model="companyId"
      :items="companies ?? []"
      item-title="name"
      item-value="id"
      label="Company"
      hide-details
      density="compact"
      variant="outlined"
      class="company-toolbar-select me-1 me-md-2"
      bg-color="surface"
    />
  </v-app-bar>

  <v-main class="main-surface main-layout-main">
    <v-container fluid class="pa-4 pa-md-6 main-layout-container">
      <div class="page-shell">
        <router-view />
      </div>
    </v-container>
  </v-main>
</template>

<style scoped>
.nav-drawer {
  background: linear-gradient(180deg, #06273d 0%, #071a2e 100%) !important;
  border-color: rgba(255, 255, 255, 0.08) !important;
}

.nav-app-bar {
  background: rgb(var(--v-theme-surface)) !important;
  border-color: rgba(255, 255, 255, 0.08) !important;
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
</style>
