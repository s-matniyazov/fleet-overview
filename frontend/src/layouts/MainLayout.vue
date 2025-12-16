<script setup>

import VerticalMenu from "@/components/VerticalMenu.vue";
import {onMounted, ref, watch} from "vue";
import router from "@/router/index.js";
import {useRouterStore} from "@/store/RouterStore.js";
import USelect from "@/components/base/USelect.vue";
import axiosIns from "@/plugins/axios.js";
import {showMessage} from "@/util/utils.js";
import {URIS} from "@/constants/UriConstants.js";
import {useFilterStore} from "@/store/FilterStore.js";

const routerStore = useRouterStore();
const filterStore = useFilterStore();

const companies = ref();
const companyId = ref(filterStore.globalFilter.companyId);

const sidebarWidth = ref(200);
const collapsed = ref(document.body.classList.toggle("collapsed-sidebar"))
const toggleSidebar = () => {
  collapsed.value = !collapsed.value;
  sidebarWidth.value = collapsed.value ? 80 : 200;

  document.body.classList.toggle('collapsed-sidebar', collapsed.value);
};

function pushPage(page) {
  router.push(page);
}

function getCompanies() {
  axiosIns.get(URIS.COMPANIES)
      .then(res => {
        companies.value = res.data.data;
      }).catch(e => {
    showMessage(e)
  });
}

onMounted(() => {
  getCompanies();
});

watch(
    companyId,
    (newVal) => {
      filterStore.setCompanyId(newVal)
      window.location.reload()
    },
    {deep: true}
)
</script>

<template>
  <div id="layout-wrapper">

    <header id="page-topbar">
      <div class="navbar-header" style="background: #06273d;">
        <div class="d-flex items-center">
          <div class="text-light" :style="collapsed ? 'padding: 0 0.5rem;' : 'padding: 0 1.5rem;'">
            <template v-if="!collapsed">
              <router-link to="/" class="logo logo-dark">
               <span class="logo-lg"> <img src="@/assets/icons/em_logo.png" alt="fleet logo" size="12"
                                           style="height: 50px">
               </span>
              </router-link>
            </template>
            <template v-else>
              <span class="logo-sm"> <img src="@/assets/icons/em_logo.png" alt="fleet logo" size="8"
                                          style="height: 50px"/> </span>
            </template>
          </div>
        </div>

        <button class="btn btn  mx-1" style="height: 50px; width: 50px;"
                @click="e => {toggleSidebar(); e.stopPropagation()}">
          <img src="@/assets/icons/resize.png" alt="resize" height="20"/>
        </button>

        <div class="d-flex px-3" style="justify-content: space-around">
          <span class="text-white" style="font-size: 18px; font-weight: 1000">{{
              routerStore.currentRouterName
            }}</span>
        </div>

        <div class="d-flex px-3" style="justify-content: space-around">
          <USelect :items="companies" v-model="companyId" option_name="name" option_value="id"
                   styles="width: 20rem; margin-left: calc(50vw - 520px)"/>
        </div>
      </div>
    </header>

    <vertical-menu :collapsed="collapsed" @toggle-sidebar="toggleSidebar"/>

    <div class="main-content flex-fill h-100">
      <div class="page-content">
        <router-view/>
      </div>
    </div>

  </div>
</template>

<style scoped>

</style>
