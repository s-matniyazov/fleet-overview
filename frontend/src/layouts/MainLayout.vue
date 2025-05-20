<script setup>

import VerticalMenu from "@/components/VerticalMenu.vue";
import {onMounted, ref, watch} from "vue";
import feather from "feather-icons";
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

  feather.replace();
});



watch(
    companyId,
    (newVal) => {
      filterStore.setCompanyId(newVal)
      window.location.reload()
    },
    { deep: true }
)
</script>

<template>
  <div id="layout-wrapper">

    <header id="page-topbar">
      <div class="navbar-header">
        <div class="d-flex items-center">
          <div class="navbar-brand-box">
            <router-link to="/" class="logo logo-dark">
              <span class="logo-lg"> <img src="../assets/icons/em_logo.png" alt="" size="12" style="height: 50px">
                <span class="logo-txt">EM Fleet</span>
                                </span>
            </router-link>
          </div>
        </div>

        <div class="d-flex px-3" style="justify-content: space-around">
          <span class="text-primary" style="font-size: 18px; font-weight: 1000">{{ routerStore.currentRouterName }}</span>
        </div>

        <div class="d-flex px-3" style="justify-content: space-around">
          <USelect :items="companies" v-model="companyId" option_name="name" option_value="id" styles="width: 20rem; margin-left: calc(50vw - 520px)"/>
        </div>
      </div>
    </header>

    <vertical-menu/>

    <div class="main-content">
      <div class="page-content">
        <div class="container-fluid"
             style="border: 1px dashed #eae1e1; border-radius: 5px; height: calc(100vh - 90px); overflow: hidden; overflow-y: auto">
          <router-view/>
        </div>

      </div>
    </div>

  </div>
</template>

<style scoped>

</style>
`
