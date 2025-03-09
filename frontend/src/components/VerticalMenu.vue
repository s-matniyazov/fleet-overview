<script setup>
import {ref} from "vue";

const currentOpenMenu = ref([]);
const toggleSubmenu = (menu) => {
  if (currentOpenMenu.value.filter(it => it === menu).length) {
    currentOpenMenu.value = currentOpenMenu.value.filter(it => it !== menu);
  } else  {
    currentOpenMenu.value.push(menu);
  }
};
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
              <span data-key="t-dashboard">Fleet Compliance</span>
            </router-link>
            <router-link to="fleet-overview" class="btn w-100 text-start p-0">
              <i data-feather="map-pin"></i>
              <span data-key="t-projects">Fleet overview</span>
            </router-link>
            <router-link to="maintenance" class="btn w-100 text-start p-0">
              <i data-feather="tool"></i>
              <span data-key="t-projects">Maintenance</span>
            </router-link>
            <hr/>
          </li>

          <button @click="e => {toggleSubmenu('chleet'); e.stopPropagation()}"
                  class="btn w-100 text-start p-0">
            <li class="menu-title cursor-pointer flex align-items-center justify-content-between text-gray-light font-size-14">
              <i data-feather="command"></i>
              <span class="m-2">Fleet</span>
              <span class="ml-auto" v-if="currentOpenMenu.includes('chleet')">
                <i class="mdi mdi-arrow-down-drop-circle-outline"/>
              </span>
              <span class="ml-auto text-end" v-else style="margin-right: auto; left: 0">
                <i class="mdi mdi-arrow-up-drop-circle-outline"/>
              </span>
            </li>
          </button>
          <li v-show="currentOpenMenu.includes('chleet')">
            <router-link to="fleet-compliance" class="btn w-100 text-start p-0">
              <i data-feather="truck"></i>
              <span data-key="t-dashboard">Fleet Compliance</span>
            </router-link>
            <router-link to="fleet-overview" class="btn w-100 text-start p-0">
              <i data-feather="map-pin"></i>
              <span data-key="t-projects">Fleet overview</span>
            </router-link>
            <router-link to="maintenance" class="btn w-100 text-start p-0">
              <i data-feather="tool"></i>
              <span data-key="t-projects">Maintenance</span>
            </router-link>
            <hr/>
          </li>
        </ul>
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
