<script setup>

import Projects from "@/components/project/Projects.vue";
import ProjectPriorities from "@/components/project/ProjectPriorities.vue";
import {ref} from "vue";
import Tabs from "@/components/base/UTabs.vue";
import TabPanels from "@/components/base/UTabPanels.vue";
import ProjectStatuses from "@/components/project/ProjectStatuses.vue";
import Clients from "@/components/project/Clients.vue";
import {useI18n} from "vue-i18n";

const {t} = useI18n();
const TABS = [
  {
    key: 'pr',
    name: t('projects'),
    description: '',
    component: Projects
  },
  {
    key: 'spr',
    name: t('priority_manual'),
    description: '',
    component: ProjectPriorities
  },
  {
    key: 'sps',
    name: t('status_manual'),
    description: '',
    component: ProjectStatuses
  },
  {
    key: 'cs',
    name: t('clients'),
    description: '',
    component: Clients
  }
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
            <tabs :tabs="TABS" v-model="activeTab"/>
          </div>
        </div>

        <div class="card-body">
          <tab-panels :tabs="TABS" v-model="activeTab"/>
        </div>

      </div>
    </div>
  </div>
</template>

<style scoped>

</style>
