<script setup>

import {ref} from "vue";
import UTabs from "@/components/base/UTabs.vue";
import UTabPanels from "@/components/base/UTabPanels.vue";
import Users from "@/components/setting/Users.vue";
import Positions from "@/components/team/Positions.vue";
import Teams from "@/components/team/Teams.vue";
import {useI18n} from "vue-i18n";

const {t} = useI18n();
const TABS = [
  {
    key: 'tm',
    name: t('teams'),
    description: '',
    component: Teams
  },
  {
    key: 'tmu',
    name: t('employees'),
    description: '',
    component: Users
  },
  {
    key: 'tmp',
    name: t('position_manual'),
    description: '',
    component: Positions
  },
];
const activeTab = ref(TABS[0].key);
</script>

<template>
  <div class="row">
    <div class="col-12">
      <div class="card">
        <div class="card-header align-items-center d-flex">
          <h4 class="card-title mb-0 flex-grow-1">{{TABS[0].name}}</h4>
          <div class="flex-shrink-0">
            <u-tabs :tabs="TABS" v-model="activeTab"/>
          </div>
        </div>

        <div class="card-body">
          <u-tab-panels :tabs="TABS" v-model="activeTab"/>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>

</style>
