<script setup>

import {useI18n} from "vue-i18n";
import UTabs from "@/components/base/UTabs.vue";
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
</script>

<template>
  <div class="row g-0 mx-0 flex-grow-1 min-h-0 h-100">
    <UTabs class="col-12 d-flex flex-column min-h-0 h-100" :tabs="TABS" />
  </div>
</template>

<style scoped>

</style>