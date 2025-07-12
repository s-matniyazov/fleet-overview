<script setup>

import {useI18n} from "vue-i18n";
import {ref} from "vue";
import UTabs from "@/components/base/UTabs.vue";
import TabPanels from "@/components/base/UTabPanels.vue";
import CompanyInfo from "@/components/safety/company/CompanyInfo.vue";
import CompanyDocuments from "@/components/safety/company/CompanyDocuments.vue";
import CompanyFiles from "@/components/safety/company/CompanyFiles.vue";

const {t} = useI18n();

const props = defineProps({
  data: {
    type: Object,
    required: true
  }
})

const TABS = [
  {
    key: 'co',
    name: t('Company'),
    description: '',
    component: CompanyInfo,
    props: {data: props.data}
  },
  {
    key: 'cof',
    name: t('company_files'),
    description: '',
    component: CompanyFiles,
    props: {data: props.data}
  },
  {
    key: 'cod',
    name: t('company_documents'),
    description: '',
    component: CompanyDocuments,
    props: {data: props.data}
  },
];
const activeTab = ref(TABS[0].key);
</script>

<template>
  <div class="row">
    <UTabs class="col-12 d-flex justify-content-center align-items-center" :tabs="TABS" v-model="activeTab"/>

    <tab-panels :tabs="TABS" v-model="activeTab"/>
  </div>
</template>

<style scoped>

</style>