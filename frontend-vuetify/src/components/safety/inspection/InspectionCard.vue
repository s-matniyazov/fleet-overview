<script setup>

import {useI18n} from "vue-i18n";
import InspectionInfo from "@/components/safety/inspection/InspectionInfo.vue";
import InspectionFiles from "@/components/safety/inspection/InspectionFiles.vue";
import UTabs from "@/components/base/UTabs.vue";
import InspectionDocuments from "@/components/safety/inspection/InspectionDocuments.vue";

const {t} = useI18n();

const props = defineProps({
  data: {
    type: Object,
    required: true
  }
})

const TABS = [
  {
    key: 'ins',
    name: t('inspection'),
    description: '',
    component: InspectionInfo,
    props: {data: props.data}
  },
  {
    key: 'insf',
    name: t('Files'),
    description: '',
    component: InspectionFiles,
    props: {data: props.data}
  },
  {
    key: 'insdoc',
    name: t('Documents'),
    description: '',
    component: InspectionDocuments,
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