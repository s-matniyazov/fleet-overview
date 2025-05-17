<script setup>
import {ref} from "vue";
import {useAuthStore} from "@/store/UseAuthStore.js";
import router from "@/router/index.js";

const currentOpenMenu = ref([]);
const toggleSubmenu = (menu) => {
  if (currentOpenMenu.value.filter(it => it === menu).length) {
    currentOpenMenu.value = currentOpenMenu.value.filter(it => it !== menu);
  } else  {
    currentOpenMenu.value.push(menu);
  }
};

const authStore = useAuthStore()

function pushPage(page) {
  router.push(page);
}

function onLogout() {
  authStore.logout();
  pushPage('/login');
}
</script>

<template>
  <div class="vertical-menu">
    <div class="h-100">
      <div id="sidebar-menu">
        <ul class="metismenu list-unstyled">
          <button @click="e => {toggleSubmenu('fleet'); e.stopPropagation()}"
                  class="btn w-100 text-start p-0">
            <li class="menu-title cursor-pointer flex align-items-center justify-content-between text-gray-light font-size-14">
              <i data-feather="command"></i>
              <span data-key="t-fleet" class="m-2">Fleet</span>
              <span class="ml-auto" v-if="currentOpenMenu.includes('fleet')">
                <i class="mdi mdi-arrow-down-drop-circle-outline"/>
              </span>
              <span class="ml-auto text-end" v-else style="margin-right: auto; left: 0">
                <i class="mdi mdi-arrow-up-drop-circle-outline"/>
              </span>
            </li>
          </button>
          <li v-show="currentOpenMenu.includes('fleet')">
            <router-link to="fleet-compliance" class="btn w-100 text-start p-0">
              <i data-feather="truck"></i>
              <span>Fleet Compliance</span>
            </router-link>
            <router-link to="fleet-overview" class="btn w-100 text-start p-0">
              <i data-feather="map-pin"></i>
              <span>Fleet overview</span>
            </router-link>
            <router-link to="maintenance" class="btn w-100 text-start p-0">
              <i data-feather="tool"></i>
              <span>Maintenance</span>
            </router-link>
            <hr/>
          </li>
          <button @click="e => {toggleSubmenu('drivers'); e.stopPropagation()}"
                  class="btn w-100 text-start p-0">
            <li class="menu-title cursor-pointer flex align-items-center justify-content-between text-gray-light font-size-14">
              <i data-feather="users"></i>
              <span data-key="t-driver" class="m-2">Drivers</span>
              <span class="ml-auto" v-if="currentOpenMenu.includes('drivers')">
                <i class="mdi mdi-arrow-down-drop-circle-outline"/>
              </span>
              <span class="ml-auto text-end" v-else style="margin-right: auto; left: 0">
                <i class="mdi mdi-arrow-up-drop-circle-outline"/>
              </span>
            </li>
          </button>
          <li v-show="currentOpenMenu.includes('drivers')">
            <router-link to="drivers" class="btn w-100 text-start p-0">
              <i data-feather="drivers"></i>
              <span>Drivers</span>
            </router-link>

            <hr/>
          </li>

          <button @click="e => {toggleSubmenu('references'); e.stopPropagation()}" class="btn w-100 text-start p-0">
            <li class="menu-title cursor-pointer flex align-items-center justify-content-between text-gray-light font-size-14">
              <i data-feather="settings"></i>
              <span class="m-2">References</span>
              <span class="ml-auto" v-if="currentOpenMenu.includes('references')">
                <i class="mdi mdi-arrow-down-drop-circle-outline"/>
              </span>
              <span class="ml-auto text-end" v-else style="margin-right: auto; left: 0">
                <i class="mdi mdi-arrow-up-drop-circle-outline"/>
              </span>
            </li>
          </button>
          <li v-show="currentOpenMenu.includes('references')">
<!--            <router-link to="country" class="btn w-100 text-start p-0">-->
<!--              <i data-feather="flag"></i>-->
<!--              <span>Country</span>-->
<!--            </router-link>-->
<!--            <router-link to="city" class="btn w-100 text-start p-0">-->
<!--              <i data-feather="map-pin"></i>-->
<!--              <span>City</span>-->
<!--            </router-link>-->
<!--            <router-link to="fuel-type" class="btn w-100 text-start p-0">-->
<!--              <i data-feather="tool"></i>-->
<!--              <span>Fuel type</span>-->
<!--            </router-link>-->
            <router-link to="model-maker" class="btn w-100 text-start p-0">
              <i data-feather="tool"></i>
              <span>Model maker</span>
            </router-link>
            <router-link to="owner-operator" class="btn w-100 text-start p-0">
              <i data-feather="tool"></i>
              <span>Owner operator</span>
            </router-link>
            <router-link to="ownership-type" class="btn w-100 text-start p-0">
              <i data-feather="tool"></i>
              <span>Ownership type</span>
            </router-link>
            <router-link to="purchase-type" class="btn w-100 text-start p-0">
              <i data-feather="tool"></i>
              <span>Purchase type</span>
            </router-link>
            <hr/>
          </li>
        </ul>
      </div>

      <div class="w-100 position-absolute d-flex align-items-center p-1" style="margin-top: auto; bottom: 0; background: #385a8a">
        <button class="btn btn btn-outline-light" style="height: 50px; width: 50px; /*margin-left: auto; right: 0*/"
                @click="e => {onLogout(); e.stopPropagation()}">
          <img src="@/assets/logout.png" alt="logout" height="24"/>
        </button>

        <span class="mx-1 text-white" style="font-size: 16px">Logout</span>
      </div>
    </div>
  </div>

</template>

<style scoped>
hr {
  margin-left: 0.1rem;
  margin-right: 0.1rem;
  border: 0;
  border-top: 1px solid rgba(56, 79, 137, 0.78);
}

#sidebar-menu ul li {
  padding-left: 2rem;
}

.menu-title {
  padding: 7px 18px !important;
  pointer-events: none;
  cursor: default;
  font-size: .9rem;
  color: var(--bs-sidebar-menu-item-icon-color);
  font-weight: 1000;
}

#sidebar-menu ul li a {
  display: block;
  padding: .20rem 1rem;
  color: var(--bs-sidebar-menu-item-color);
  position: relative;
  font-size: .9rem;
  -webkit-transition: all .4s;
  transition: all .4s;
  font-weight: 500;
}
</style>
