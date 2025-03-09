<script setup>

import VerticalMenu from "@/components/VerticalMenu.vue";
import {onMounted, ref} from "vue";
import feather from "feather-icons";
import router from "@/router/index.js";
import {useAuthStore} from "@/store/UseAuthStore.js";
import USelect from "@/components/base/USelect.vue";

const authStore = useAuthStore()

const company = ref(1);
const companies = ref([
  {
    id: 1,
    name: "COMPANY one"
  },
  {
    id: 2,
    name: "COMPANY two"
  },
  {
    id: 3,
    name: "COMPANY three"
  },
]);

function onLogout() {
  authStore.logout();
  pushPage('/login');
}

function pushPage(page) {
  router.push(page);
}

onMounted(() => {
  feather.replace();
});
</script>

<template>
  <div id="layout-wrapper">

    <header id="page-topbar">
      <div class="navbar-header">
        <div class="d-flex items-center">
          <div class="navbar-brand-box">
            <router-link to="/" class="logo logo-dark">
              <span class="logo-lg">
                                    <img src="@/assets/logo.png" alt="" size="12" style="height: 24px">
                <span class="logo-txt">Fleet</span>
                                </span>
            </router-link>
          </div>
        </div>

        <div class="flex">
          <USelect :items="companies" v-model="company" option_name="name" option_value="id" styles="width: 20rem"/>
        </div>

        <div class="d-flex">
          <button class="btn btn btn-outline-light" style="height: 50px; width: 50px"
                  @click="e => {onLogout(); e.stopPropagation()}">
            <img src="@/assets/logout.png" alt="logout" height="24"/>
          </button>
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
