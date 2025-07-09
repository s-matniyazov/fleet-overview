<script setup>
import {ref} from "vue";
import {useAuthStore} from "@/store/UseAuthStore.js";
import router from "@/router/index.js";
import UTooltip from "@/components/base/UTooltip.vue";
import {useRouterStore} from "@/store/RouterStore.js";

const routerStore = useRouterStore();

const currentOpenMenu = ref([]);
const toggleSubmenu = (menu) => {
  if (currentOpenMenu.value.filter(it => it === menu).length) {
    currentOpenMenu.value = currentOpenMenu.value.filter(it => it !== menu);
  } else {
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

function getSelectedRouterColor(router) {
  if (routerStore.currentRouter === `/${router}`) return "rgb(87,215,255)";
  else return "currentColor";
}

defineProps({
  collapsed: {
    type: Boolean,
    required: true
  }
});
</script>

<template>
  <div class="vertical-menu">
    <div class="h-100">
      <div id="sidebar-menu" :class="{'sidebar-collapsed': collapsed}">
        <ul v-if="!collapsed" class="metismenu list-unstyled">
          <button @click="e => {toggleSubmenu('fleet'); e.stopPropagation()}"
                  class="btn w-100 text-start p-0">
            <li class="menu-title cursor-pointer flex align-items-center justify-content-between text-gray-light font-size-14">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                   stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
                   class="feather feather-command">
                <path
                    d="M18 3a3 3 0 0 0-3 3v12a3 3 0 0 0 3 3 3 3 0 0 0 3-3 3 3 0 0 0-3-3H6a3 3 0 0 0-3 3 3 3 0 0 0 3 3 3 3 0 0 0 3-3V6a3 3 0 0 0-3-3 3 3 0 0 0-3 3 3 3 0 0 0 3 3h12a3 3 0 0 0 3-3 3 3 0 0 0-3-3z"></path>
              </svg>
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
            <router-link to="fleet-compliance" class="btn w-100 text-start p-0"
                         :style="`color: ${getSelectedRouterColor('fleet-compliance')}`">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                   stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
                   class="feather feather-truck">
                <rect x="1" y="3" width="15" height="13"></rect>
                <polygon points="16 8 20 8 23 11 23 16 16 16 16 8"></polygon>
                <circle cx="5.5" cy="18.5" r="2.5"></circle>
                <circle cx="18.5" cy="18.5" r="2.5"></circle>
              </svg>
              <span>Fleet Compliance</span>
            </router-link>
            <router-link to="fleet-overview" class="btn w-100 text-start p-0"
                         :style="`color: ${getSelectedRouterColor('fleet-overview')}`">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                   stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
                   class="feather feather-map-pin">
                <path d="M21 10c0 7-9 13-9 13s-9-6-9-13a9 9 0 0 1 18 0z"></path>
                <circle cx="12" cy="10" r="3"></circle>
              </svg>
              <span>Fleet overview</span>
            </router-link>
            <router-link to="maintenance" class="btn w-100 text-start p-0"
                         :style="`color: ${getSelectedRouterColor('maintenance')}`">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                   stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
                   class="feather feather-tool">
                <path
                    d="M14.7 6.3a1 1 0 0 0 0 1.4l1.6 1.6a1 1 0 0 0 1.4 0l3.77-3.77a6 6 0 0 1-7.94 7.94l-6.91 6.91a2.12 2.12 0 0 1-3-3l6.91-6.91a6 6 0 0 1 7.94-7.94l-3.76 3.76z"></path>
              </svg>
              <span>Maintenance</span>
            </router-link>
            <hr/>
          </li>

          <button @click="e => {toggleSubmenu('safety'); e.stopPropagation()}"
                  class="btn w-100 text-start p-0">
            <li class="menu-title cursor-pointer flex align-items-center justify-content-between text-gray-light font-size-14">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                   stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
                   class="feather feather-briefcase">
                <rect x="2" y="7" width="20" height="14" rx="2" ry="2"></rect>
                <path d="M16 21V5a2 2 0 0 0-2-2h-4a2 2 0 0 0-2 2v16"></path>
              </svg>

              <span data-key="t-safety" class="m-2">Safety</span>
              <span class="ml-auto" v-if="currentOpenMenu.includes('safety')">
                <i class="mdi mdi-arrow-down-drop-circle-outline"/>
              </span>
              <span class="ml-auto text-end" v-else style="margin-right: auto; left: 0">
                <i class="mdi mdi-arrow-up-drop-circle-outline"/>
              </span>
            </li>
          </button>
          <li v-show="currentOpenMenu.includes('safety')">
            <router-link to="safety-compliance" class="btn w-100 text-start p-0"
                         :style="`color: ${getSelectedRouterColor('safety-compliance')}`">
              <i class="mdi mdi-account-circle-outline"/>
              <span>Safety Compliance</span>
            </router-link>
            <router-link to="dot-inspections" class="btn w-100 text-start p-0"
                         :style="`color: ${getSelectedRouterColor('dot-inspections')}`">
              <i class="mdi mdi-account-circle-outline"/>
              <span>Dot Inspections</span>
            </router-link>

            <hr/>
          </li>

          <button @click="e => {toggleSubmenu('references'); e.stopPropagation()}" class="btn w-100 text-start p-0">
            <li class="menu-title cursor-pointer flex align-items-center justify-content-between text-gray-light font-size-14">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                   stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
                   class="feather feather-sliders">
                <line x1="4" y1="21" x2="4" y2="14"></line>
                <line x1="4" y1="10" x2="4" y2="3"></line>
                <line x1="12" y1="21" x2="12" y2="12"></line>
                <line x1="12" y1="8" x2="12" y2="3"></line>
                <line x1="20" y1="21" x2="20" y2="16"></line>
                <line x1="20" y1="12" x2="20" y2="3"></line>
                <line x1="1" y1="14" x2="7" y2="14"></line>
                <line x1="9" y1="8" x2="15" y2="8"></line>
                <line x1="17" y1="16" x2="23" y2="16"></line>
              </svg>
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
            <router-link to="model-maker" class="btn w-100 text-start p-0"
                         :style="`color: ${getSelectedRouterColor('model-maker')}`">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                   stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
                   class="feather feather-tool">
                <path
                    d="M14.7 6.3a1 1 0 0 0 0 1.4l1.6 1.6a1 1 0 0 0 1.4 0l3.77-3.77a6 6 0 0 1-7.94 7.94l-6.91 6.91a2.12 2.12 0 0 1-3-3l6.91-6.91a6 6 0 0 1 7.94-7.94l-3.76 3.76z"></path>
              </svg>
              <span>Model maker</span>
            </router-link>
            <router-link to="ownership-type" class="btn w-100 text-start p-0"
                         :style="`color: ${getSelectedRouterColor('ownership-type')}`">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                   stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
                   class="feather feather-tool">
                <path
                    d="M14.7 6.3a1 1 0 0 0 0 1.4l1.6 1.6a1 1 0 0 0 1.4 0l3.77-3.77a6 6 0 0 1-7.94 7.94l-6.91 6.91a2.12 2.12 0 0 1-3-3l6.91-6.91a6 6 0 0 1 7.94-7.94l-3.76 3.76z"></path>
              </svg>
              <span>Ownership type</span>
            </router-link>
            <router-link to="purchase-type" class="btn w-100 text-start p-0"
                         :style="`color: ${getSelectedRouterColor('purchase-type')}`">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                   stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
                   class="feather feather-tool">
                <path
                    d="M14.7 6.3a1 1 0 0 0 0 1.4l1.6 1.6a1 1 0 0 0 1.4 0l3.77-3.77a6 6 0 0 1-7.94 7.94l-6.91 6.91a2.12 2.12 0 0 1-3-3l6.91-6.91a6 6 0 0 1 7.94-7.94l-3.76 3.76z"></path>
              </svg>
              <span>Purchase type</span>
            </router-link>
            <hr/>
          </li>
        </ul>
        <ul v-else class="metismenu list-unstyled">
          <UTooltip position="right" class="btn w-100 text-start p-0">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                 :stroke="getSelectedRouterColor('fleet-compliance')" stroke-width="2" stroke-linecap="round"
                 stroke-linejoin="round"
                 class="feather feather-truck">
              <rect x="1" y="3" width="15" height="13"></rect>
              <polygon points="16 8 20 8 23 11 23 16 16 16 16 8"></polygon>
              <circle cx="5.5" cy="18.5" r="2.5"></circle>
              <circle cx="18.5" cy="18.5" r="2.5"></circle>
            </svg>
            <template #content>
              <router-link to="fleet-compliance">Fleet Compliance</router-link><br>
              <router-link to="fleet-overview">Fleet Overview</router-link>
            </template>
          </UTooltip>
          <router-link to="fleet-overview" class="btn w-100 text-start p-0">
            <UTooltip position="right">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                   :stroke="getSelectedRouterColor('fleet-overview')" stroke-width="2" stroke-linecap="round"
                   stroke-linejoin="round"
                   class="feather feather-map-pin">
                <path d="M21 10c0 7-9 13-9 13s-9-6-9-13a9 9 0 0 1 18 0z"></path>
                <circle cx="12" cy="10" r="3"></circle>
              </svg>
              <template #content>
                <span>Fleet overview</span>
              </template>
            </UTooltip>
          </router-link>
          <router-link to="maintenance" class="btn w-100 text-start p-0">
            <UTooltip position="right">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                   :stroke="getSelectedRouterColor('maintenance')" stroke-width="2" stroke-linecap="round"
                   stroke-linejoin="round"
                   class="feather feather-tool">
                <path
                    d="M14.7 6.3a1 1 0 0 0 0 1.4l1.6 1.6a1 1 0 0 0 1.4 0l3.77-3.77a6 6 0 0 1-7.94 7.94l-6.91 6.91a2.12 2.12 0 0 1-3-3l6.91-6.91a6 6 0 0 1 7.94-7.94l-3.76 3.76z"></path>
              </svg>
              <template #content>
                <span>Maintenance</span>
              </template>
            </UTooltip>
          </router-link>
          <hr/>

          <router-link to="safety-compliance" class="btn w-100 text-start p-0">
            <UTooltip position="right">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                   :stroke="getSelectedRouterColor('safety-compliance')" stroke-width="2" stroke-linecap="round"
                   stroke-linejoin="round"
                   class="feather feather-tool">
                <g>
                  <path
                      d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm0 4c1.93 0 3.5 1.57 3.5 3.5S13.93 13 12 13s-3.5-1.57-3.5-3.5S10.07 6 12 6zm0 14c-2.03 0-4.43-.82-6.14-2.88C7.55 15.8 9.68 15 12 15s4.45.8 6.14 2.12C16.43 19.18 14.03 20 12 20z"/>
                </g>
              </svg>
              <template #content>
                <span>Safety Compliance</span>
              </template>
            </UTooltip>
          </router-link>
          <router-link to="dot-inspections" class="btn w-100 text-start p-0">
            <UTooltip position="right">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                   :stroke="getSelectedRouterColor('dot-inspections')" stroke-width="2" stroke-linecap="round"
                   stroke-linejoin="round"
                   class="feather feather-tool">
                <g>
                  <path
                      d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm0 4c1.93 0 3.5 1.57 3.5 3.5S13.93 13 12 13s-3.5-1.57-3.5-3.5S10.07 6 12 6zm0 14c-2.03 0-4.43-.82-6.14-2.88C7.55 15.8 9.68 15 12 15s4.45.8 6.14 2.12C16.43 19.18 14.03 20 12 20z"/>
                </g>
              </svg>
              <template #content>
                <span>Dot Inspections</span>
              </template>
            </UTooltip>
          </router-link>
          <hr/>

          <router-link to="model-maker" class="btn w-100 text-start p-0">
            <UTooltip position="right">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                   :stroke="getSelectedRouterColor('model-maker')" stroke-width="2" stroke-linecap="round"
                   stroke-linejoin="round"
                   class="feather feather-tool">
                <path
                    d="M14.7 6.3a1 1 0 0 0 0 1.4l1.6 1.6a1 1 0 0 0 1.4 0l3.77-3.77a6 6 0 0 1-7.94 7.94l-6.91 6.91a2.12 2.12 0 0 1-3-3l6.91-6.91a6 6 0 0 1 7.94-7.94l-3.76 3.76z"></path>
              </svg>
              <template #content>
                <span>Model maker</span>
              </template>
            </UTooltip>
          </router-link>
          <router-link to="ownership-type" class="btn w-100 text-start p-0">
            <UTooltip position="right">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                   :stroke="getSelectedRouterColor('ownership-type')" stroke-width="2" stroke-linecap="round"
                   stroke-linejoin="round"
                   class="feather feather-tool">
                <path
                    d="M14.7 6.3a1 1 0 0 0 0 1.4l1.6 1.6a1 1 0 0 0 1.4 0l3.77-3.77a6 6 0 0 1-7.94 7.94l-6.91 6.91a2.12 2.12 0 0 1-3-3l6.91-6.91a6 6 0 0 1 7.94-7.94l-3.76 3.76z"></path>
              </svg>
              <template #content>
                <span>Ownership type</span>
              </template>
            </UTooltip>
          </router-link>
          <router-link to="purchase-type" class="btn w-100 text-start p-0">
            <UTooltip position="right">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                   :stroke="getSelectedRouterColor('purchase-type')" stroke-width="2" stroke-linecap="round"
                   stroke-linejoin="round"
                   class="feather feather-tool">
                <path
                    d="M14.7 6.3a1 1 0 0 0 0 1.4l1.6 1.6a1 1 0 0 0 1.4 0l3.77-3.77a6 6 0 0 1-7.94 7.94l-6.91 6.91a2.12 2.12 0 0 1-3-3l6.91-6.91a6 6 0 0 1 7.94-7.94l-3.76 3.76z"></path>
              </svg>
              <template #content>
                <span>Purchase type</span>
              </template>
            </UTooltip>
          </router-link>
          <hr/>
        </ul>
      </div>

      <div class="w-100 position-absolute d-flex align-items-center p-1"
           style="margin-top: auto; bottom: 0; background: #385a8a">
        <button class="btn btn btn-outline-light" style="height: 50px; width: 50px; /*margin-left: auto; right: 0*/"
                @click="e => {onLogout(); e.stopPropagation()}">
          <img src="@/assets/icons/logout.png" alt="logout" height="24"/>
        </button>
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

.sidebar-collapsed .metismenu .btn {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 50px;
}


</style>
