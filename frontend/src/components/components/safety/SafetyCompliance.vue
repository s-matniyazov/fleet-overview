<script setup>
import {onMounted} from "vue";

import {useI18n} from "vue-i18n";
import Tabs from "@/components/base/UTabs.vue";
import Driver from "@/components/safety/driver/Driver.vue";
import CompanyFiles from "@/components/safety/company/CompanyWithFiles.vue";

const {t} = useI18n();

const TABS = [
  {
    key: 'dr',
    name: t('drivers'),
    description: '',
    component: Driver
  },
  {
    key: 'com',
    name: t('company_files'),
    description: '',
    component: CompanyFiles
  },
];

// HOOKS
onMounted(() => {

})
</script>

<template>
  <div class="h-100 col-12 p-2">
    <tabs :tabs="TABS" />
  </div>
</template>

<style scoped>

</style>
