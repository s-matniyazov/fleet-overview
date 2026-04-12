<script setup>

import {useI18n} from "vue-i18n";
import TruckInfo from "@/components/fleet/truck/TruckInfo.vue";
import TruckFiles from "@/components/fleet/truck/TruckFiles.vue";
import UTabs from "@/components/base/UTabs.vue";
import TruckDocuments from "@/components/fleet/truck/TruckDocuments.vue";

const {t} = useI18n();

const props = defineProps({
  data: {
    type: Object,
    required: true
  }
})

const TABS = [
  {
    key: 'tr',
    name: t('truck'),
    description: '',
    component: TruckInfo,
    props: {data: props.data}
  },
  {
    key: 'trf',
    name: t('truck_files'),
    description: '',
    component: TruckFiles,
    props: {data: props.data}
  },
  {
    key: 'tra',
    name: t('truck_documents'),
    description: '',
    component: TruckDocuments,
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