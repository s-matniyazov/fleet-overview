<script setup>

import {ref} from "vue";
import Tabs from "@/components/base/UTabs.vue";
import TabPanels from "@/components/base/UTabPanels.vue";
import {useI18n} from "vue-i18n";
import Users from "@/components/setting/Users.vue";
import Roles from "@/components/setting/Roles.vue";
import Actions from "@/components/setting/Actions.vue";

const {t} = useI18n();
const TABS = [
  {
    key: 'us',
    name: t('users'),
    description: '',
    component: Users
  },
  {
    key: 'ro',
    name: t('roles'),
    description: '',
    component: Roles
  },
  {
    key: 'rac',
    name: t('role_actions'),
    description: '',
    component: Actions
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
